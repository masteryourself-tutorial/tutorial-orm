package pers.masteryourself.tutorial.orm.mybatis.log;

import org.apache.ibatis.logging.LogFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pers.masteryourself.tutorial.orm.mybatis.log.config.SpringConfig;
import pers.masteryourself.tutorial.orm.mybatis.log.domain.User;
import pers.masteryourself.tutorial.orm.mybatis.log.service.UserService;

/**
 * <p>description : LogApplication
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/4/11 16:07
 */
public class LogApplication {

    static {
        // 必须要在调用 MyBatis 任何方法之前调用这个方法，更改日志实现
        LogFactory.useLog4JLogging();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService = context.getBean(UserService.class);
        for (User user : userService.list()) {
            System.out.println(user);
        }
    }

}
