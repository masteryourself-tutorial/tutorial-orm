package pers.masteryourself.tutorial.tk.mybatis.quickstart.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String lastName;

    @Column(name = "create_time", updatable = false, insertable = false)
    private Date createTime;

    @Column(name = "update_time", updatable = false, insertable = false)
    private Date updateTime;

    @Transient
    private String mark;

}
