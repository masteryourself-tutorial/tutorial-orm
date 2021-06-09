package pers.masteryourself.tutorial.sharding.tables;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.masteryourself.tutorial.sharding.tables.domain.Order;
import pers.masteryourself.tutorial.sharding.tables.domain.OrderItem;
import pers.masteryourself.tutorial.sharding.tables.mapper.OrderItemMapper;
import pers.masteryourself.tutorial.sharding.tables.mapper.OrderMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>description : ShardingTablesApplicationTest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/8 15:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingTablesApplicationTest {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    /**
     * 分别插入 t_order_item_0、t_order_item_0、t_order_0 表
     */
    @Test
    public void testWriteEven() {
        Order order = new Order();
        order.setOrderId(998L);
        order.setName("order-998");
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrderId(998L);
        orderItem1.setItemName("order-998-item-1");
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrderId(998L);
        orderItem2.setItemName("order-998-item-2");
        orderItemMapper.insertSelective(orderItem1);
        orderItemMapper.insertSelective(orderItem2);
        orderMapper.insertSelective(order);
    }

    /**
     * 分别插入 t_order_item_1、t_order_item_1、t_order_1 表
     */
    @Test
    public void testWriteOdd() {
        Order order = new Order();
        order.setOrderId(999L);
        order.setName("order-999");
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrderId(999L);
        orderItem1.setItemName("order-999-item-1");
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrderId(999L);
        orderItem2.setItemName("order-999-item-2");
        orderItemMapper.insertSelective(orderItem1);
        orderItemMapper.insertSelective(orderItem2);
        orderMapper.insertSelective(order);
    }

    /**
     * 查询所有从表
     * t_order_0、t_order_1
     * t_order_item_0、t_order_item_1
     */
    @Test
    public void testRead() {
        List<Order> orders = orderMapper.selectAll();
        System.out.println(orders);
        List<OrderItem> orderItems = orderItemMapper.selectAll();
        System.out.println(orderItems);
    }

}