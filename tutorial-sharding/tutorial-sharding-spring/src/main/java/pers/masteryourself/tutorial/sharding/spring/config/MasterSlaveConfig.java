package pers.masteryourself.tutorial.sharding.spring.config;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * <p>description : MasterSlaveConfig
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 11:32
 */
@Configuration
@PropertySource("classpath:application-sharding-master-slave.properties")
@MapperScan(basePackages = {"pers.masteryourself.tutorial.sharding.spring.mapper.master_slave"}, sqlSessionFactoryRef = "masterSlaveDataSourceSqlSessionFactory")
public class MasterSlaveConfig {

    /**
     * 创建一个可以读写分离的 sharding 数据源
     *
     * @return {@link DataSource} 其本质是 ShardingDataSource
     * @throws SQLException 异常
     */
    @Bean(name = "masterSlaveDataSource")
    public DataSource masterSlaveDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        /**
         * 这种方式是官方推荐的方式, 但会调用 {@link org.apache.shardingsphere.core.execute.metadata.TableMetaDataInitializer#load(org.apache.shardingsphere.core.rule.ShardingRule)} 方法
         * 如果在表数据量非常大的情况下, 启动会非常慢
         */
        shardingRuleConfig.setMasterSlaveRuleConfigs(Lists.newArrayList(
                new MasterSlaveRuleConfiguration("ds_ms0", "ds_master", Arrays.asList("ds_slave_0", "ds_slave_1"))));
        /**
         * 这里可以优化下, 使用下面这种方式注入会过滤 {@link org.apache.shardingsphere.core.execute.metadata.TableMetaDataInitializer#load(org.apache.shardingsphere.core.rule.ShardingRule)} 方法
         * 从而加速启动速度, 但不知道会不会引入其他问题
         */
        /*shardingRuleConfig.setMasterSlaveRuleConfigs(Lists.newArrayList(
                new MasterSlaveRuleConfiguration("ds_ms0", "ds_master", Collections.singletonList("ds_slave_0")),
                new MasterSlaveRuleConfiguration("ds_ms1", "ds_master", Collections.singletonList("ds_slave_1"))));*/
        Properties props = new Properties();
        props.put(ShardingPropertiesConstant.SQL_SHOW.getKey(), true);
        props.put(ShardingPropertiesConstant.EXECUTOR_SIZE.getKey(), 10);
        Map<String, DataSource> result = new HashMap<>(10);
        result.put("ds_master", masterDataSource());
        result.put("ds_slave_0", slave1DataSource());
        result.put("ds_slave_1", slave2DataSource());
        return ShardingDataSourceFactory.createDataSource(result, shardingRuleConfig, props);
    }

    /**
     * 为 masterSlaveDataSource 提供事务管理器
     *
     * @return {@link DataSourceTransactionManager}
     * @throws Exception 异常
     */
    @Bean(name = "masterSlaveDataSourceTransactionManager")
    public DataSourceTransactionManager masterSlaveDataSourceTransactionManager() throws Exception {
        return new DataSourceTransactionManager(masterSlaveDataSource());
    }

    /**
     * 为 masterSlaveDataSource 提供 SqlSessionFactory
     *
     * @return {@link SqlSessionFactory}
     * @throws Exception 异常
     */
    @Bean(name = "masterSlaveDataSourceSqlSessionFactory")
    public SqlSessionFactory masterSlaveDataSourceSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(masterSlaveDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * @return 返回主库数据源
     */
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.master.datasource")
    public DataSource masterDataSource() {
        return new HikariDataSource();
    }

    /**
     * @return 从库1
     */
    @Bean(name = "slave1DataSource")
    @ConfigurationProperties(prefix = "spring.slave1.datasource")
    public DataSource slave1DataSource() {
        return new HikariDataSource();
    }

    /**
     * @return 从库2
     */
    @Bean(name = "slave2DataSource")
    @ConfigurationProperties(prefix = "spring.slave2.datasource")
    public DataSource slave2DataSource() {
        return new HikariDataSource();
    }

}
