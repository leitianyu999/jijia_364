package com.jijia.operational.service;

import com.jijia.common.core.exception.GlobalException;
import com.jijia.operational.domain.info.OpDeskInfo;
import com.jijia.operational.domain.vo.OpSettleVo;
import com.jijia.operational.mapper.OpSettleMapper;
import com.jijia.operational.service.impl.OpSettleServiceImpl;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/** 独立回归入口，无需连接数据库；运行 main 检查实际 Service 和 MyBatis 动态更新 SQL。 */
public class OpSettleServiceRegression {
    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void rejects(Runnable action) {
        try {
            action.run();
            throw new AssertionError("Expected invalid edit to be rejected");
        } catch (GlobalException expected) {
            // Expected validation failure.
        }
    }

    public static void main(String[] args) throws Exception {
        List<OpSettleVo> writes = new ArrayList<>();
        OpSettleMapper mapper = (OpSettleMapper) Proxy.newProxyInstance(
                OpSettleMapper.class.getClassLoader(), new Class<?>[]{OpSettleMapper.class}, (proxy, method, values) -> {
                    if ("selectOpDeskByDeskId".equals(method.getName())) {
                        Long id = (Long) values[0];
                        if (id == 999L) return null;
                        OpSettleVo existing = new OpSettleVo();
                        existing.setDeskId(id);
                        existing.setPublisher("original publisher");
                        existing.setSampleSender("original sender");
                        existing.setSampleReceiver("original receiver");
                        existing.setIsSettlement("1");
                        existing.setSettleAmount(100);
                        existing.setSettleType(1);
                        existing.setSettleRemark("original note");
                        return existing;
                    }
                    if ("updateOpDesk".equals(method.getName())) {
                        writes.add((OpSettleVo) values[0]);
                        return 1;
                    }
                    throw new AssertionError("Unexpected mapper call: " + method.getName());
                });
        OpSettleServiceImpl service = new OpSettleServiceImpl();
        Field field = OpSettleServiceImpl.class.getDeclaredField("settleMapper");
        field.setAccessible(true);
        field.set(service, mapper);

        Configuration configuration = new Configuration();
        configuration.getTypeAliasRegistry().registerAlias("OpSettleVo", OpSettleVo.class);
        configuration.getTypeAliasRegistry().registerAlias("OpDeskInfo", OpDeskInfo.class);
        String resource = "src/main/resources/mapper/operational/OpSettleMapper.xml";
        try (InputStream stream = Files.newInputStream(Paths.get(resource))) {
            new XMLMapperBuilder(stream, configuration, resource, configuration.getSqlFragments()).parse();
        }
        String statement = "com.jijia.operational.mapper.OpSettleMapper.updateOpDesk";

        OpSettleVo note = new OpSettleVo();
        note.setSettleRemark("note-only edit");
        note.setSampleSender("must not overwrite");
        note.setPublisher("must not overwrite");
        check(service.updateOpDeskAll(note, new Long[]{1L}) == 1, "note-only edit was ignored");
        OpSettleVo written = writes.get(0);
        check(written.getSampleSender() == null && written.getPublisher() == null, "front desk fields copied into update");
        check(written.getIsSettlement() == null && written.getSettleAmount() == null && written.getSettleType() == null, "unmodified settlement fields overwritten");
        check("note-only edit".equals(written.getSettleRemark()), "note missing from update");
        String sql = configuration.getMappedStatement(statement).getBoundSql(written).getSql().replaceAll("\\s+", " ");
        check(sql.contains("SET settle_remark = ?") && !sql.contains("settle_amount =") && !sql.contains("sample_sender"), "unsafe note-only SQL");

        OpSettleVo zero = new OpSettleVo();
        zero.setSettleAmount(0);
        zero.setSettleType(0);
        zero.setIsSettlement("0");
        check(service.updateOpDeskAll(zero, new Long[]{1L, 2L}) == 2, "batch zero edit skipped");
        for (OpSettleVo update : writes.subList(1, 3)) {
            check(update.getSettleAmount() == 0 && update.getSettleType() == 0 && "0".equals(update.getIsSettlement()), "zero values lost");
            check(update.getSettleRemark() == null && update.getSampleSender() == null && update.getSampleReceiver() == null, "batch overwrote names or notes");
        }
        OpSettleVo clear = new OpSettleVo();
        clear.setSettleRemark("");
        service.updateOpDeskAll(clear, new Long[]{1L});
        check("".equals(writes.get(3).getSettleRemark()), "cannot clear note");
        int count = writes.size();
        rejects(() -> service.updateOpDeskAll(new OpSettleVo(), new Long[]{1L}));
        rejects(() -> service.updateOpDeskAll(note, new Long[0]));
        rejects(() -> service.updateOpDeskAll(note, null));
        rejects(() -> service.updateOpDeskAll(null, new Long[]{1L}));
        rejects(() -> service.updateOpDeskAll(note, new Long[]{null}));
        rejects(() -> service.updateOpDeskAll(note, new Long[]{999L}));
        OpSettleVo invalid = new OpSettleVo();
        invalid.setSettleAmount(-1);
        rejects(() -> service.updateOpDeskAll(invalid, new Long[]{1L}));
        invalid.setSettleAmount(1);
        invalid.setSettleType(2);
        rejects(() -> service.updateOpDeskAll(invalid, new Long[]{1L}));
        invalid.setSettleType(1);
        invalid.setIsSettlement("yes");
        rejects(() -> service.updateOpDeskAll(invalid, new Long[]{1L}));
        check(writes.size() == count, "invalid edit reached update SQL");
        System.out.println("PASS: note-only, sparse updates, batch zero values, clear note, invalid edits, SQL field isolation");
    }
}
