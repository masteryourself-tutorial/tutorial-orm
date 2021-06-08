package pers.masteryourself.tutorial.sharding.spring.config.mapper.databases_tables;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.masteryourself.tutorial.sharding.spring.domain.databasestables.Student;
import pers.masteryourself.tutorial.sharding.spring.mapper.databasestables.StudentMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>description : ShardingDatabasesTablesApplication
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 19:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingDatabasesTablesApplication {

    @Resource
    private StudentMapper studentMapper;

    /**
     * 查询所有数据
     * 会自动帮我们查询所有表
     */
    @Test
    public void testRead() {
        List<Student> students = studentMapper.selectAll();
        System.out.println(students);
    }

}
