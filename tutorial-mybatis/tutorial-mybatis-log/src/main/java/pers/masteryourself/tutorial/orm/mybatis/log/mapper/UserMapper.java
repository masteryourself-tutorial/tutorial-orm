package pers.masteryourself.tutorial.orm.mybatis.log.mapper;

import org.apache.ibatis.annotations.Select;
import pers.masteryourself.tutorial.orm.mybatis.log.domain.User;

import java.util.List;

/**
 * <p>description : UserMapper
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/4/11 16:14
 */
public interface UserMapper {

    @Select("select * from user")
    List<User> list();

}
