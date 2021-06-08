package pers.masteryourself.tutorial.sharding.tables.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>description : ShardingTablesConfig
 * 不分库, 只分表, 2 张表
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 15:27
 */
@Configuration
@PropertySource("classpath:application-sharding-tables.properties")
@MapperScan(basePackages = {"pers.masteryourself.tutorial.sharding.tables.mapper"})
public class ShardingTablesConfig {

    /**
     * 创建一个可以分表的 sharding 数据源
     *
     * @return {@link DataSource} 其本质是 ShardingDataSource
     * @throws SQLException 异常
     */
    @Bean(name = "tablesDataSource")
    public DataSource tablesDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // order 分表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("t_order", "ds.t_order_${0..1}");
        orderTableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new ShardingTablePreciseShardingAlgorithm()));
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
        // orderItem 分表规则
        TableRuleConfiguration orderItemTableRuleConfiguration = new TableRuleConfiguration("t_order_item", "ds.t_order_item_${0..1}");
        orderItemTableRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new ShardingTablePreciseShardingAlgorithm()));
        shardingRuleConfig.getTableRuleConfigs().add(orderItemTableRuleConfiguration);
        // 添加所有数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>(8);
        dataSourceMap.put("ds", dataSource());
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, this.init());
    }

    @Bean(name = "tablesDataSourceTransactionManager")
    public DataSourceTransactionManager tablesDataSourceTransactionManager() throws Exception {
        return new DataSourceTransactionManager(tablesDataSource());
    }

    @Bean(name = "tablesDataSourceSqlSessionFactory")
    public SqlSessionFactory tablesDataSourceSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(tablesDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "tablesDataSourceSqlSessionTemplate")
    public SqlSessionTemplate tablesDataSourceSqlSessionTemplate(SqlSessionFactory tablesDataSourceSqlSessionFactory) {
        return new SqlSessionTemplate(tablesDataSourceSqlSessionFactory);
    }

    /**
     * @return 单库
     */
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new HikariDataSource();
    }

    /**
     * 自定义分片策略
     */
    private static class ShardingTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

        @Override
        public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
            return shardingValue.getValue() % 2 == 0 ? shardingValue.getLogicTableName() + "_0" : shardingValue.getLogicTableName() + "_1";
        }

    }

    private Properties init() {
        Properties props = new Properties();
        props.put(ShardingPropertiesConstant.SQL_SHOW.getKey(), true);
        props.put(ShardingPropertiesConstant.EXECUTOR_SIZE.getKey(), 10);
        return props;
    }

}
