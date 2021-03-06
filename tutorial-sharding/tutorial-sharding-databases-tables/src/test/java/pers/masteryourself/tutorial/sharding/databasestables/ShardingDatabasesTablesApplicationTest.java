package pers.masteryourself.tutorial.sharding.databasestables;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import pers.masteryourself.tutorial.sharding.databasestables.domain.Dict;
import pers.masteryourself.tutorial.sharding.databasestables.domain.Student;
import pers.masteryourself.tutorial.sharding.databasestables.mapper.DictMapper;
import pers.masteryourself.tutorial.sharding.databasestables.mapper.StudentMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>description : ShardingDatabasesTablesApplicationTest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/8 15:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingDatabasesTablesApplicationTest {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private DictMapper dictMapper;

    /**
     * 插入 master_1 库的 student_3 表
     * database: 1 % 2 == 1
     * table: 3 % 4 == 3
     * ds_master_1.student_3
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
     * 插入 master_0 库的 student_0 表
     * database: 100L % 2 == 0
     * table: 100 % 4 == 0
     * ds_master_0.student_0
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
     * 查询 master_1 库的 student_3 表
     * database: 1 % 2 == 1
     * table: 3 % 4 == 3
     * ds_master_1.student_3
     */
    @Test
    public void testSelectDataBase_1Table_3() {
        Student student = new Student();
        student.setClassId(1L);
        student.setStudentId(3L);
        System.out.println(studentMapper.select(student));
    }

    /**
     * 查询 master_0 库的 student_0 表
     * database: 100L % 2 == 0
     * table: 100 % 4 == 0
     * ds_master_0.student_0
     */
    @Test
    public void testSelectDataBase_0Table_0() {
        Student student = new Student();
        student.setClassId(100L);
        student.setStudentId(100L);
        System.out.println(studentMapper.select(student));
    }

    /**
     * 查询 master_0 库的所有表
     * database: 100L % 2 == 0
     * ds_master_0.student_0 + ds_master_0.student_1 + ds_master_0.student_2 + ds_master_0.student_3
     */
    @Test
    public void testSelectDataBase_0() {
        Student student = new Student();
        student.setClassId(100L);
        System.out.println(studentMapper.select(student));
    }

    /**
     * 查询所有库的 student_0 表
     * table: 100 % 4 == 0
     * ds_master_0.student_0 + ds_master_1.student_0
     */
    @Test
    @Repeat(value = 2)
    public void testSelectTable_0() {
        Student student = new Student();
        student.setStudentId(100L);
        System.out.println(studentMapper.select(student));
    }

    /**
     * 查询所有从库的 student 表
     * ds_master_0.student_0 + ds_master_0.student_1 + ds_master_0.student_2 + ds_master_0.student_3 +
     * ds_master_1.student_0 + ds_master_1.student_1 + ds_master_1.student_2 + ds_master_1.student_3
     */
    @Test
    public void testRead() {
        List<Student> students = studentMapper.selectAll();
        System.out.println(students);
    }

    /**
     * dict 表从默认数据源查找
     * ds_master_0.dict
     */
    @Test
    @Repeat(value = 4)
    public void testDict() {
        Dict dict = new Dict();
        dict.setId(1L);
        dictMapper.select(dict);
    }

}