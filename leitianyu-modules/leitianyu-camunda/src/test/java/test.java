import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jijia.camunda.LeitianyuCamundaApplication;
import com.jijia.camunda.domain.CmdCategory;
import com.jijia.camunda.mapper.CmdCategoryMapper;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {LeitianyuCamundaApplication.class})
public class test {

    @Au
    CmdCategoryMapper cmdCategoryMapper;

    @Test
    public void test() {
        LambdaQueryWrapper<CmdCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CmdCategory::getCategoryId, 1L);
        CmdCategory cmdCategory = cmdCategoryMapper.selectList(wrapper).get(0);
        System.out.println(cmdCategory);
    }

}
