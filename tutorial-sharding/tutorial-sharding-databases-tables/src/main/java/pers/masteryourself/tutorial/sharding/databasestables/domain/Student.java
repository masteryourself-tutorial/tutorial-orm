package pers.masteryourself.tutorial.sharding.databasestables.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * <p>description : Student, 学生信息表, 参与分库分表
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 19:30
 */
@Data
@Table
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分片健用于分库
     */
    @Column(name = "class_id")
    private Long classId;

    /**
     * 分片健用于分表
     */
    @Column(name = "student_id")
    private Long studentId;

    private String name;

}
