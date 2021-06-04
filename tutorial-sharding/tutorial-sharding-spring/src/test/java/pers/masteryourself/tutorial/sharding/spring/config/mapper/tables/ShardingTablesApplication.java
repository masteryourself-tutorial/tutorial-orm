package pers.masteryourself.tutorial.sharding.spring.config.mapper.tables;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.masteryourself.tutorial.sharding.spring.domain.tables.Order;
import pers.masteryourself.tutorial.sharding.spring.domain.tables.OrderItem;
import pers.masteryourself.tutorial.sharding.spring.mapper.tables.OrderItemMapper;
import pers.masteryourself.tutorial.sharding.spring.mapper.tables.OrderMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>description : TablesApplication
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2021/6/4 15:23
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingTablesApplication {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

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
     * 查询所有数据
     * 会自动帮我们查询所有表
     */
    @Test
    public void testRead() {
        List<Order> orders = orderMapper.selectAll();
        System.out.println(orders);
        List<OrderItem> orderItems = orderItemMapper.selectAll();
        System.out.println(orderItems);
    }

}