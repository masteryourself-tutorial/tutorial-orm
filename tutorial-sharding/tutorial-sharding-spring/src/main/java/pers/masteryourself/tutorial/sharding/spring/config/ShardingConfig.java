package pers.masteryourself.tutorial.sharding.spring.config;

import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;

import java.util.Properties;

/**
 * <p>description : ShardingConfig
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 15:49
 */
public class ShardingConfig {

    public static Properties init() {
        Properties props = new Properties();
        props.put(ShardingPropertiesConstant.SQL_SHOW.getKey(), true);
        props.put(ShardingPropertiesConstant.EXECUTOR_SIZE.getKey(), 10);
        return props;
    }

}
