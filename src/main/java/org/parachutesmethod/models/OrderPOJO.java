package org.parachutesmethod.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderPOJO {

    private Customer customer;
    private List<OrderItem> orderItems;
    private Date orderDate;

    public OrderPOJO() {
        orderItems = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}