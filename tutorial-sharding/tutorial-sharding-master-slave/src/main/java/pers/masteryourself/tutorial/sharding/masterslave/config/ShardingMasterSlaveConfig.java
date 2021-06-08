package pers.masteryourself.tutorial.sharding.masterslave.config;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>description : ShardingMasterSlaveConfig
 * 仅读写分离, 一主二从
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 11:32
 */
@Configuration
@PropertySource("classpath:application-sharding-master-slave.properties")
@MapperScan(basePackages = {"pers.masteryourself.tutorial.sharding.masterslave.mapper"})
public class ShardingMasterSlaveConfig {

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
                new MasterSlaveRuleConfiguration("ds_master_slave", "ds_master", Arrays.asList("ds_master_slave_0", "ds_master_slave_1"))));
        /**
         * 这里可以优化下, 使用下面这种方式注入会过滤 {@link org.apache.shardingsphere.core.execute.metadata.TableMetaDataInitializer#load(org.apache.shardingsphere.core.rule.ShardingRule)} 方法
         * 从而加速启动速度, 但不知道会不会引入其他问题
         */
        /*shardingRuleConfig.setMasterSlaveRuleConfigs(Lists.newArrayList(
                new MasterSlaveRuleConfiguration("ds_master_slave_0", "ds_master", Collections.singletonList("ds_slave_0")),
                new MasterSlaveRuleConfiguration("ds_master_slave_1", "ds_master", Collections.singletonList("ds_slave_1"))));*/
        // 添加所有数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>(8);
        dataSourceMap.put("ds_master", masterDataSource());
        dataSourceMap.put("ds_master_slave_0", masterSlave0DataSource());
        dataSourceMap.put("ds_master_slave_1", masterSlave1DataSource());
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, this.init());
    }

    @Bean(name = "masterSlaveDataSourceTransactionManager")
    public DataSourceTransactionManager masterSlaveDataSourceTransactionManager() throws Exception {
        return new DataSourceTransactionManager(masterSlaveDataSource());
    }

    @Bean(name = "masterSlaveDataSourceSqlSessionFactory")
    public SqlSessionFactory masterSlaveDataSourceSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(masterSlaveDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "masterSlaveDataSourceSqlSessionTemplate")
    public SqlSessionTemplate masterSlaveDataSourceSqlSessionTemplate(SqlSessionFactory masterSlaveDataSourceSqlSessionFactory) {
        return new SqlSessionTemplate(masterSlaveDataSourceSqlSessionFactory);
    }

    /**
     * @return 主库
     */
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.master.datasource")
    public DataSource masterDataSource() {
        return new HikariDataSource();
    }

    /**
     * @return 从库0
     */
    @Bean(name = "masterSlave0DataSource")
    @ConfigurationProperties(prefix = "spring.master-slave0.datasource")
    public DataSource masterSlave0DataSource() {
        return new HikariDataSource();
    }

    /**
     * @return 从库1
     */
    @Bean(name = "masterSlave1DataSource")
    @ConfigurationProperties(prefix = "spring.master-slave1.datasource")
    public DataSource masterSlave1DataSource() {
        return new HikariDataSource();
    }

    private Properties init() {
        Properties props = new Properties();
        props.put(ShardingPropertiesConstant.SQL_SHOW.getKey(), true);
        props.put(ShardingPropertiesConstant.EXECUTOR_SIZE.getKey(), 10);
        return props;
    }

}
