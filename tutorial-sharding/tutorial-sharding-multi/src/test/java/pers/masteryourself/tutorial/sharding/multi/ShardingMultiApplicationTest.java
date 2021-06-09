package pers.masteryourself.tutorial.sharding.multi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import pers.masteryourself.tutorial.sharding.multi.domain.Dict;
import pers.masteryourself.tutorial.sharding.multi.domain.User;
import pers.masteryourself.tutorial.sharding.multi.mapper.masterslave.UserMapper;
import pers.masteryourself.tutorial.sharding.multi.mapper.simple.DictMapper;

import javax.annotation.Resource;

/**
 * <p>description : ShardingMultiApplicationTest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/9 16:42
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingMultiApplicationTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private DictMapper dictMapper;

    /**
     * ds_master_slave_0.user / ds_master_slave_1.user / ds_master_slave_0.user / ds_master_slave_1.user 交替查询
     */
    @Test
    @Repeat(value = 4)
    public void testUserSelect() {
        User user = new User();
        user.setId(1L);
        userMapper.select(user);
    }

    /**
     * 从 simple 库中查询
     */
    @Test
    @Repeat(value = 4)
    public void testDictSelect() {
        Dict dict = new Dict();
        dict.setId(1L);
        dictMapper.select(dict);
    }

}