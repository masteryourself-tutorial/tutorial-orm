package pers.masteryourself.tutorial.sharding.masterslave;

import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import pers.masteryourself.tutorial.sharding.masterslave.domain.User;
import pers.masteryourself.tutorial.sharding.masterslave.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>description : ShardingMasterSlaveApplicationTest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/8 15:33
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingMasterSlaveApplicationTest {

    @Resource
    private UserMapper userMapper;

    /**
     * 插入到主库 user 表
     * ds_master.user
     */
    @Test
    public void testWrite() {
        User user = new User();
        user.setName("新用户");
        userMapper.insertSelective(user);
    }

    /**
     * 交替查询从库的 user 表
     * ds_master_slave_0.user / ds_master_slave_1.user / ds_master_slave_0.user / ds_master_slave_1.user 交替查询
     */
    @Test
    @Repeat(value = 4)
    public void testRead() {
        List<User> users = userMapper.selectAll();
        System.out.println(users);
    }

    /**
     * 强制查询主库的 user 表
     * ds_master.user
     */
    @Test
    public void testHint() {
        try (HintManager hintManager = HintManager.getInstance()) {
            hintManager.setMasterRouteOnly();
            List<User> users = userMapper.selectAll();
            System.out.println(users);
        }
    }

}