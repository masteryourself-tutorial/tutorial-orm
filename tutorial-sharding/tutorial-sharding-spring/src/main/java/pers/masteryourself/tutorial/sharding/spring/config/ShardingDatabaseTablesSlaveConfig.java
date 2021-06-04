package pers.masteryourself.tutorial.sharding.spring.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
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

/**
 * <p>description : ShardingDatabaseTablesSlaveConfig
 * 分库分表, 共两库, 每库四张表
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 19:21
 */
@Configuration
@PropertySource("classpath:application-sharding-databases-tables.properties")
@MapperScan(basePackages = {"pers.masteryourself.tutorial.sharding.spring.mapper.databases_tables"}, sqlSessionTemplateRef = "databaseTablesDataSourceSqlSessionTemplate")
public class ShardingDatabaseTablesSlaveConfig {

    /**
     * 创建一个可以分表的 sharding 数据源
     *
     * @return {@link DataSource} 其本质是 ShardingDataSource
     * @throws SQLException 异常
     */
    @Bean(name = "databaseTablesDataSource")
    public DataSource databaseTablesDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 分库规则
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("class_id", new ShardingTablePreciseShardingAlgorithm()));
        // 分表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("student", "ds${0..1}.student_${0..4}");
        orderTableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("student_id", new ShardingTablePreciseShardingAlgorithm()));
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
        // 添加所有数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>(8);
        dataSourceMap.put("ds0", master1DataSource());
        dataSourceMap.put("ds1", master2DataSource());
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, ShardingConfig.init());
    }

    @Bean(name = "databaseTablesDataSourceTransactionManager")
    public DataSourceTransactionManager databaseTablesDataSourceTransactionManager() throws Exception {
        return new DataSourceTransactionManager(databaseTablesDataSource());
    }

    @Bean(name = "databaseTablesDataSourceSqlSessionFactory")
    public SqlSessionFactory databaseTablesDataSourceSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(databaseTablesDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "databaseTablesDataSourceSqlSessionTemplate")
    public SqlSessionTemplate databaseTablesDataSourceSqlSessionTemplate(SqlSessionFactory databaseTablesDataSourceSqlSessionFactory) {
        return new SqlSessionTemplate(databaseTablesDataSourceSqlSessionFactory);
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
     * @return master2 库
     */
    @Bean(name = "master2DataSource")
    @ConfigurationProperties(prefix = "spring.master2.datasource")
    public DataSource master2DataSource() {
        return new HikariDataSource();
    }

    /**
     * 自定义分片策略
     */
    private static class ShardingTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

        @Override
        public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
            String logicTableName = shardingValue.getLogicTableName();
            return shardingValue.getValue() % 2 == 0 ? logicTableName + "_0" : logicTableName + "_1";
        }

    }

}
