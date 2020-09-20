package pers.masteryourself.tutorial.tk.mybatis.enums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <p>description : EnumApplication
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/9/20 17:24
 */
@MapperScan("pers.masteryourself.tutorial.tk.mybatis.enums.mapper")
@SpringBootApplication
public class EnumApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnumApplication.class, args);
    }

}
