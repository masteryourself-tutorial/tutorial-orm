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
import java.util.UUID;

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

    @Test
    @Repeat(value = 2)
    public void testWrite() {
        User user = new User();
        user.setName(UUID.randomUUID().toString().replaceAll("-", ""));
        userMapper.insertSelective(user);
    }

    /**
     * 这里为了看出分表效果, 循环四次, 打印的数据一会是李四/王五
     */
    @Test
    @Repeat(value = 4)
    public void testRead() {
        List<User> users = userMapper.selectAll();
        System.out.println(users);
    }

    @Test
    public void testHint() {
        try (HintManager hintManager = HintManager.getInstance()) {
            hintManager.setMasterRouteOnly();
            List<User> users = userMapper.selectAll();
            System.out.println(users);
        }
    }

}