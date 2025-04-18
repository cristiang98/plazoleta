package com.courier.domain.model;

public class PinUserModel {

    private Integer id;
    private Integer pin;
    private Integer userId;
    private Integer orderId;

    public PinUserModel(Integer id, Integer pin, Integer userId, Integer orderId) {
        this.id = id;
        this.pin = pin;
        this.userId = userId;
        this.orderId = orderId;
    }

    public PinUserModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
