package pers.masteryourself.tutorial.sharding.spring.domain.tables;

import lombok.Data;

import javax.persistence.*;

/**
 * <p>description : Order
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 15:25
 */
@Data
@Table(name = "t_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column
    private String name;

}
