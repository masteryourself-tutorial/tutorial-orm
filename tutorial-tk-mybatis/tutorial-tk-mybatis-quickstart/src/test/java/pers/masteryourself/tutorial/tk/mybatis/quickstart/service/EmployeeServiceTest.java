package pers.masteryourself.tutorial.tk.mybatis.quickstart.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.masteryourself.tutorial.tk.mybatis.quickstart.domain.Employee;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * <p>description : EmployeeServiceTest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/9/6 11:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testSelectOne() {
        Employee query = Employee.builder().id(1).build();
        Employee employee = employeeService.selectOne(query);
        log.info("selectOne {}", employee);
    }

    @Test
    public void testSelectByPrimaryKey() {
        Employee employee = employeeService.selectByPrimaryKey(1L);
        log.info("selectByPrimaryKey {}", employee);
    }

    @Test
    public void testInsert() {
        Employee employee = Employee.builder().lastName("张三").build();
        int result = employeeService.insert(employee);
        log.info("insert {} employee id {}", result, employee.getId());
    }

    @Test
    public void testInsertSelective() {
        Employee employee = Employee.builder().build();
        int result = employeeService.insertSelective(employee);
        log.info("insertSelective {} employee id {}", result, employee.getId());
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        Employee employee = Employee.builder()
                .id(1)
                .lastName("我改名字了")
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        int result = employeeService.updateByPrimaryKeySelective(employee);
        log.info("updateByPrimaryKeySelective {} employee id {}", result, employee.getId());
    }

    @Test
    public void testSelectByExample() {
        // where (id < ? and create_time < ? ) or (id > ? and create_time > ?)
        Example example = new Example(Employee.class);
        // 设置排序
        example.orderBy("createTime").desc();
        // 设置去重
        example.setDistinct(true);
        // 设置 select 字段
        example.selectProperties("lastName", "createTime");
        // 创建查询条件
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andLessThan("id", 1000).andLessThan("createTime", "2019-09-19");
        Example.Criteria criteria2 = example.createCriteria();
        criteria2.andGreaterThan("id", 10000).andGreaterThan("createTime", "2020-09-19");
        // or 拼接
        example.or(criteria2);
        //查询
        List<Employee> employeeList = employeeService.selectByExample(example);
        log.info("selectByExample {}", employeeList);
    }

}