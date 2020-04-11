package pers.masteryourself.tutorial.orm.mybatis.log;

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

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserService userService = context.getBean(UserService.class);
        for (User user : userService.list()) {
            System.out.println(user);
        }
    }

}
