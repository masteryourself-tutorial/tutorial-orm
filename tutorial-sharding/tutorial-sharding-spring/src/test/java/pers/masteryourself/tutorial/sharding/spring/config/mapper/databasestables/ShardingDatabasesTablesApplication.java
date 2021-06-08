package pers.masteryourself.tutorial.sharding.spring.config.mapper.databasestables;

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
     * database: 1 % 2 == 1
     * table: 3 % 4 == 3
     */
    @Test
    public void testWriteDataBase_1Table_3() {
        Student student = new Student();
        student.setClassId(1L);
        student.setStudentId(3L);
        student.setName("1号库3号表");
        studentMapper.insert(student);
    }

    /**
     * database: 100L % 2 == 0
     * table: 100 % 4 == 0
     */
    @Test
    public void testWriteDataBase_0Table_0() {
        Student student = new Student();
        student.setClassId(100L);
        student.setStudentId(100L);
        student.setName("0号库0号表");
        studentMapper.insert(student);
    }

    /**
     * database: 1 % 2 == 1
     * table: 3 % 4 == 3
     */
    @Test
    public void testSelectDataBase_1Table_3() {
        Student student = new Student();
        student.setClassId(1L);
        student.setStudentId(3L);
        studentMapper.select(student);
    }

    /**
     * database: 100L % 2 == 0
     * table: 100 % 4 == 0
     */
    @Test
    public void testSelectDataBase_0Table_0() {
        Student student = new Student();
        student.setClassId(100L);
        student.setStudentId(100L);
        studentMapper.select(student);
    }

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
