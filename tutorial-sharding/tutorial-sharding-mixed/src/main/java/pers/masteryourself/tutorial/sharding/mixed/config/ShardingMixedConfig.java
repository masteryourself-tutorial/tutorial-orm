package pers.masteryourself.tutorial.sharding.mixed.config;

import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
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
import java.util.*;

/**
 * <p>description : ShardingMixedConfig
 * 分库分表, 共 2 个主库, 每库 4 张表
 * 读写分离, 每个主库有 2 个从库
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/8 19:53
 */
@Slf4j
@Configuration
@PropertySource("classpath:application-sharding-mixed.properties")
@MapperScan(basePackages = {"pers.masteryourself.tutorial.sharding.mixed.mapper"})
public class ShardingMixedConfig {

    /**
     * 创建一个可以分库分表 + 读写分离的 sharding 数据源
     *
     * @return {@link DataSource} 其本质是 ShardingDataSource
     * @throws SQLException 异常
     */
    @Bean(name = "mixedDataSource")
    public DataSource mixedDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 分库规则
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("class_id", new ShardingTablePreciseShardingAlgorithm()));
        // 分表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("student", "ds_master_slave_${0..1}.student_${0..3}");
        orderTableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("student_id", new ShardingTablePreciseShardingAlgorithm()));
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
        // 主从规则
        shardingRuleConfig.setMasterSlaveRuleConfigs(Lists.newArrayList(
                new MasterSlaveRuleConfiguration("ds_master_slave_0", "ds_master_0", Arrays.asList("ds_master_0_slave_0", "ds_master_0_slave_1")),
                new MasterSlaveRuleConfiguration("ds_master_slave_1", "ds_master_1", Arrays.asList("ds_master_1_slave_0", "ds_master_1_slave_1"))
        ));
        // 添加所有数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>(8);
        dataSourceMap.put("ds_master_0", master0DataSource());
        dataSourceMap.put("ds_master_1", master1DataSource());
        dataSourceMap.put("ds_master_0_slave_0", master0Slave0DataSource());
        dataSourceMap.put("ds_master_0_slave_1", master0Slave1DataSource());
        dataSourceMap.put("ds_master_1_slave_0", master1Slave0DataSource());
        dataSourceMap.put("ds_master_1_slave_1", master1Slave1DataSource());
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, this.init());
    }

    @Bean(name = "mixedDataSourceTransactionManager")
    public DataSourceTransactionManager mixedDataSourceTransactionManager() throws Exception {
        return new DataSourceTransactionManager(mixedDataSource());
    }

    @Bean(name = "mixedDataSourceSqlSessionFactory")
    public SqlSessionFactory mixedDataSourceSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mixedDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "mixedDataSourceSqlSessionTemplate")
    public SqlSessionTemplate mixedDataSourceSqlSessionTemplate(SqlSessionFactory mixedDataSourceSqlSessionFactory) {
        return new SqlSessionTemplate(mixedDataSourceSqlSessionFactory);
    }

    /**
     * @return master0 库
     */
    @Bean(name = "master0DataSource")
    @ConfigurationProperties(prefix = "spring.master0.datasource")
    public DataSource master0DataSource() {
        return new HikariDataSource();
    }

    /**
     * @return master1 库
     */
    @Bean(name = "master1DataSource")
    @ConfigurationProperties(prefix = "spring.master1.datasource")
    public DataSource master1DataSource() {
        return new HikariDataSource();
    }

    /**
     * @return master0-slave0 库
     */
    @Bean(name = "master0Slave0DataSource")
    @ConfigurationProperties(prefix = "spring.master0-slave0.datasource")
    public DataSource master0Slave0DataSource() {
        return new HikariDataSource();
    }

    /**
     * @return master0-slave1 库
     */
    @Bean(name = "master0Slave1DataSource")
    @ConfigurationProperties(prefix = "spring.master0-slave1.datasource")
    public DataSource master0Slave1DataSource() {
        return new HikariDataSource();
    }

    /**
     * @return master1-slave0 库
     */
    @Bean(name = "master1Slave0DataSource")
    @ConfigurationProperties(prefix = "spring.master1-slave0.datasource")
    public DataSource master1Slave0DataSource() {
        return new HikariDataSource();
    }

    /**
     * @return master1-slave1 库
     */
    @Bean(name = "master1Slave1DataSource")
    @ConfigurationProperties(prefix = "spring.master1-slave1.datasource")
    public DataSource master1Slave1DataSource() {
        return new HikariDataSource();
    }

    /**
     * 自定义分片策略
     */
    private static class ShardingTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

        @Override
        public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
            // 判断分库还是分表
            String columnName = shardingValue.getColumnName();
            Long value = shardingValue.getValue();
            if ("class_id".equals(columnName)) {
                // 分库字段
                for (String availableTargetName : availableTargetNames) {
                    // 截取最后一位数字(0..1) ds_master${0..1}
                    long suffix = Long.parseLong(availableTargetName.substring(availableTargetName.length() - 1));
                    if (suffix == value % 2) {
                        return availableTargetName;
                    }
                }
            } else if ("student_id".equals(columnName)) {
                // 分表字段
                for (String availableTargetName : availableTargetNames) {
                    // 截取最后一位数字(0..3) student_${0..3}
                    long suffix = Long.parseLong(availableTargetName.substring(availableTargetName.length() - 1));
                    if (suffix == value % 4) {
                        return availableTargetName;
                    }
                }
            }
            log.error("不知道是啥操作");
            return null;
        }

    }

    private Properties init() {
        Properties props = new Properties();
        props.put(ShardingPropertiesConstant.SQL_SHOW.getKey(), true);
        props.put(ShardingPropertiesConstant.EXECUTOR_SIZE.getKey(), 10);
        return props;
    }

}
