package pers.masteryourself.tutorial.sharding.multi.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
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

/**
 * <p>description : SimpleDatasourceConfig
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/9 16:28
 */
@Configuration
@PropertySource("classpath:application-sharding-multi-simple.properties")
@MapperScan(basePackages = {"pers.masteryourself.tutorial.sharding.multi.mapper.simple"}, sqlSessionFactoryRef = "simpleDataSourceSqlSessionFactory")
public class SimpleDatasourceConfig {

    /**
     * 创建一个可以读写分离的 sharding 数据源
     *
     * @return {@link DataSource} 其本质是 ShardingDataSource
     * @throws SQLException 异常
     */
    @Bean(name = "simpleDataSource")
    @ConfigurationProperties(prefix = "spring.multi.simple.datasource")
    public DataSource simpleDataSource() throws SQLException {
        return new HikariDataSource();
    }

    @Bean(name = "simpleDataSourceTransactionManager")
    public DataSourceTransactionManager simpleDataSourceTransactionManager() throws Exception {
        return new DataSourceTransactionManager(simpleDataSource());
    }

    @Bean(name = "simpleDataSourceSqlSessionFactory")
    public SqlSessionFactory simpleDataSourceSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(simpleDataSource());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "simpleDataSourceSqlSessionTemplate")
    public SqlSessionTemplate simpleDataSourceSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(simpleDataSourceSqlSessionFactory());
    }

}
