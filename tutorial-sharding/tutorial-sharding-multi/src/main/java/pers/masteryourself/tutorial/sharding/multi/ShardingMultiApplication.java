package pers.masteryourself.tutorial.sharding.multi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>description : ShardingMultiApplication
 * 如果需要配置多个数据源, 每个数据源的 mybatis 配置需要隔离
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/9 16:06
 */
@SpringBootApplication
public class ShardingMultiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingMultiApplication.class, args);
    }

}
