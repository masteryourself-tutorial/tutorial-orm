package pers.masteryourself.tutorial.sharding.spring.domain.tables;

import lombok.Data;

import javax.persistence.*;

/**
 * <p>description : OrderItem
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 15:34
 */
@Data
@Table(name = "t_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "item_name")
    private String itemName;

}
