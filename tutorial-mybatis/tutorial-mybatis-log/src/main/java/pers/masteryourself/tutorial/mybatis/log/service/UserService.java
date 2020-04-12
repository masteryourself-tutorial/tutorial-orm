package pers.masteryourself.tutorial.mybatis.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.masteryourself.tutorial.mybatis.log.domain.User;
import pers.masteryourself.tutorial.mybatis.log.mapper.UserMapper;

import java.util.List;

/**
 * <p>description : UserService
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/4/11 16:26
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> list() {
        return userMapper.list();
    }

}
