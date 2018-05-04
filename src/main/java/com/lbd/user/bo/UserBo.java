package com.lbd.user.bo;

import com.lbd.common.model.UUser;

import java.io.Serializable;

/**
 * @Author 温枝达
 * @Email alabadazi@gmail.com
 * @Date 2017/2/8 15:58
 * @Description Session  + User Bo
 */
public class UserBo extends UUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单数量
     */
    private String orderQuantity;

    /**
     * 累计消费
     */
    private String totalExpenses;

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(String totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}
