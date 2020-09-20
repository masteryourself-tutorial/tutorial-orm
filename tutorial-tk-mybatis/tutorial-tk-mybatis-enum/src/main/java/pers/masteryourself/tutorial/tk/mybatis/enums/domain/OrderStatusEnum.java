package pers.masteryourself.tutorial.tk.mybatis.enums.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>description : OrderStatusEnum
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/9/20 18:38
 */
@AllArgsConstructor
public enum OrderStatusEnum {

    /**
     * 订单状态
     */
    WAIT_PAY("待付款"),

    WAIT_DELIVERY("待发货"),

    WAIT_RECEIVE("待发货");

    @Getter
    private String desc;
}
