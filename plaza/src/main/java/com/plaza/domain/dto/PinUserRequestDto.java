package com.plaza.domain.dto;


public class PinUserRequestDto {


    private Integer userId;
    private Integer orderId;


    public PinUserRequestDto(Integer userId, Integer orderId) {
        this.userId = userId;
        this.orderId = orderId;
    }

    public PinUserRequestDto() {
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
