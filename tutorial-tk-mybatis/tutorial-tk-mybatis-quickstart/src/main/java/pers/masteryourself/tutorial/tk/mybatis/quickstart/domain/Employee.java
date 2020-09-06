package pers.masteryourself.tutorial.tk.mybatis.quickstart.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * <p>description : Employee
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/9/5 17:57
 */
@Data
@ToString
@Builder
public class Employee {

    private Integer id;

    private String lastName;

}
