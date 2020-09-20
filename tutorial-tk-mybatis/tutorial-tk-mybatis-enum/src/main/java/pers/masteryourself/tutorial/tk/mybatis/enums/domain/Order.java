package pers.masteryourself.tutorial.tk.mybatis.enums.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>description : Order
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/9/20 17:27
 */
@Data
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderNo;

    /**
     * 加上 {@link Column} 注解表明 tk-mybatis 需要为此字段进行映射
     */
    // @Column
    // @ColumnType(typeHandler = OrderStatusHandler.class)
    private OrderStatusEnum orderStatus;

}
