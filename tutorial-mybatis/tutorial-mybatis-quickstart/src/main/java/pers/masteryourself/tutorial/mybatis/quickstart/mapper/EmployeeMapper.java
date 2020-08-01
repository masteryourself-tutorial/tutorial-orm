package pers.masteryourself.tutorial.mybatis.quickstart.mapper;

import pers.masteryourself.tutorial.mybatis.quickstart.domain.Employee;

/**
 * <p>description : EmployeeMapper
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/8/1 14:59
 */
public interface EmployeeMapper {

    Employee getById(Integer id);

}
