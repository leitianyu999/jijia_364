package com.jijia.camunda.config.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis-plus配置
 *
 * @author fanglei
 * @date 2021/05/28 14:19
 **/

@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        // 阻断插件
//		interceptor.addInnerInterceptor(blockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 分页插件，自动识别数据库类型
     * https://baomidou.com/guide/interceptor-pagination.html
     */
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置数据库类型为mysql
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(-1L);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁插件
     * https://baomidou.com/guide/interceptor-optimistic-locker.html
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 如果是对全表的删除或更新操作，就会终止该操作
     * https://baomidou.com/guide/interceptor-block-attack.html
     */
//	public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
//		return new BlockAttackInnerInterceptor();
//	}

    /**
     * sql性能规范插件(垃圾SQL拦截)
     * 如有需要可以启用
     */
//	public IllegalSQLInnerInterceptor illegalSQLInnerInterceptor() {
//		return new IllegalSQLInnerInterceptor();
//	}


    /**
     * 自定义主键策略
     * https://baomidou.com/guide/id-generator.html
     */
//	@Bean
//	public IdentifierGenerator idGenerator() {
//		return new CustomIdGenerator();
//	}

    /**
     * 元对象字段填充控制器
     * https://baomidou.com/guide/auto-fill-metainfo.html
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new CreateAndUpdateMetaObjectHandler();
    }

    /**
     * sql注入器配置
     * https://baomidou.com/guide/sql-injector.html
     */
//	@Bean
//	public ISqlInjector sqlInjector() {
//		return new DefaultSqlInjector();
//	}

    /**
     * TenantLineInnerInterceptor 多租户插件
     * https://baomidou.com/guide/interceptor-tenant-line.html
     * DynamicTableNameInnerInterceptor 动态表名插件
     * https://baomidou.com/guide/interceptor-dynamic-table-name.html
     */
}
