package pers.masteryourself.tutorial.tk.mybatis.enums.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import pers.masteryourself.tutorial.tk.mybatis.enums.domain.OrderStatusEnum;
import tk.mybatis.mapper.util.StringUtil;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>description : OrderStatusHandler
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/9/20 18:06
 */
public class OrderStatusHandler extends BaseTypeHandler<OrderStatusEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OrderStatusEnum parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            return;
        }
        ps.setString(i, parameter.getDesc());
    }

    @Override
    public OrderStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return getOrderStatus(value);
    }

    @Override
    public OrderStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return getOrderStatus(value);
    }

    @Override
    public OrderStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return getOrderStatus(value);
    }

    private OrderStatusEnum getOrderStatus(String value) {
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        for (OrderStatusEnum orderStatus : OrderStatusEnum.values()) {
            if (orderStatus.getDesc().equals(value)) {
                return orderStatus;
            }
        }
        return null;
    }

}
