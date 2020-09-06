package pers.masteryourself.tutorial.tk.mybatis.quickstart.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.masteryourself.tutorial.tk.mybatis.quickstart.domain.Employee;

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

}