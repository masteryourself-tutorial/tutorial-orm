package pers.masteryourself.tutorial.mybatis.quickstart.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * <p>description : Employee
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/8/1 14:54
 */
@Data
@Alias("tbl_employee")
public class Employee {

    private Integer id;

    private String lastName;

}
