package pers.masteryourself.tutorial.sharding.masterslave.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * <p>description : User
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 11:36
 */
@Data
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    
}
