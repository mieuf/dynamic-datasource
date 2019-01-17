package org.base.mieuf.platform.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 入口,启动时注入
 */
@Configuration
public class DataSourceConfigurer {

    /**
     * datasource1 DataSource
     * 默认主数据源
     */
    @Bean("datasource1")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.datasource1")
    public DataSource dataSource1() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("datasource2")
    @ConfigurationProperties(prefix = "spring.datasource.druid.datasource2")
    public DataSource dataSource2() {
        return DruidDataSourceBuilder.create().build();
    }


    @Bean("datasource3")
    @ConfigurationProperties(prefix = "spring.datasource.druid.datasource3")
    public DataSource dataSource3() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * Dynamic data source.
     *
     * @return the data source
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {


        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(3);
        dataSourceMap.put(DataSourceKey.DATA_SOURCE_1.name(), dataSource1());
        dataSourceMap.put(DataSourceKey.DATA_SOURCE_2.name(), dataSource2());
        dataSourceMap.put(DataSourceKey.DATA_SOURCE_3.name(), dataSource3());


        // Set ecar datasource as default
        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSource1());
        // Set ecar and slave datasource as target datasource
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        // To put datasource keys into DataSourceContextHolder to judge if the datasource is exist
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());

        // To put hive datasource keys into DataSourceContextHolder to load balance
        DynamicDataSourceContextHolder.dataSource1Keys.addAll(dataSourceMap.keySet());
        DynamicDataSourceContextHolder.dataSource1Keys.remove(DataSourceKey.DATA_SOURCE_2.name());
        DynamicDataSourceContextHolder.dataSource1Keys.remove(DataSourceKey.DATA_SOURCE_3.name());

        DynamicDataSourceContextHolder.dataSource2Keys.addAll(dataSourceMap.keySet());
        DynamicDataSourceContextHolder.dataSource2Keys.remove(DataSourceKey.DATA_SOURCE_1.name());
        DynamicDataSourceContextHolder.dataSource2Keys.remove(DataSourceKey.DATA_SOURCE_3.name());

        DynamicDataSourceContextHolder.dataSource3Keys.addAll(dataSourceMap.keySet());
        DynamicDataSourceContextHolder.dataSource3Keys.remove(DataSourceKey.DATA_SOURCE_1.name());
        DynamicDataSourceContextHolder.dataSource3Keys.remove(DataSourceKey.DATA_SOURCE_2.name());

        return dynamicRoutingDataSource;
    }

    /**
     * Sql session factory bean.
     * Here to config datasource for SqlSessionFactory
     * <p>
     * You need to add @{@code @ConfigurationProperties(prefix = "mybatis")}, if you are using *.xml file,
     * the {@code 'mybatis.type-aliases-package'} and {@code 'mybatis.mapper-locations'} should be set in
     * {@code 'application.properties'} file, or there will appear invalid bond statement exception
     *
     * @return the sql session factory bean
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // Here is very important, if don't config this, will can't switch datasource
        // put all datasource into SqlSessionFactoryBean, then will autoconfig SqlSessionFactory
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }

    /**
     * Transaction manager platform transaction manager.
     * Here should be config if using Transaction
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}