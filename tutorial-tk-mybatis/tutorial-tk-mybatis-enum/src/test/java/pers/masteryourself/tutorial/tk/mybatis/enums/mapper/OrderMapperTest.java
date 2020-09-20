package pers.masteryourself.tutorial.tk.mybatis.enums.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.masteryourself.tutorial.tk.mybatis.enums.domain.Order;
import pers.masteryourself.tutorial.tk.mybatis.enums.domain.OrderStatusEnum;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>description : OrderMapperTest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/9/20 17:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMapperTest {

    @Resource
    private OrderMapper orderMapper;

    @Test
    public void testInsert() {
        Order order = new Order();
        order.setOrderNo("9527");
        order.setOrderStatus(OrderStatusEnum.WAIT_PAY);
        int result = orderMapper.insert(order);
        log.info("insert {} order id {}", result, order.getId());
    }

    @Test
    public void testSelectAll() {
        List<Order> orders = orderMapper.selectAll();
        orders.forEach(System.out::println);
    }

}
