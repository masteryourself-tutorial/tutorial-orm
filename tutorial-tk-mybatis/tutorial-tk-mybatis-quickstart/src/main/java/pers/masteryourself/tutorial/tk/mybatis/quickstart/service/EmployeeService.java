package pers.masteryourself.tutorial.tk.mybatis.quickstart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.masteryourself.tutorial.tk.mybatis.quickstart.domain.Employee;
import pers.masteryourself.tutorial.tk.mybatis.quickstart.mapper.EmployeeMapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * <p>description : EmployeeService
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/9/5 17:57
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public Employee selectOne(Employee query) {
        return employeeMapper.selectOne(query);
    }

    public Employee selectByPrimaryKey(long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    public int insert(Employee employee){
        return employeeMapper.insert(employee);
    }

    public int insertSelective(Employee employee){
        return employeeMapper.insertSelective(employee);
    }

    public int updateByPrimaryKeySelective(Employee employee){
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public List<Employee> selectByExample(Example example ){
        return employeeMapper.selectByExample(example);
    }
}
