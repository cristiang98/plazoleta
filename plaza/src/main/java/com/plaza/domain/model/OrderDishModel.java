package com.plaza.domain.model;

public class OrderDishModel {


    private Integer id;

    private OrderModel order;

    private DishModel dish;

    private Integer quantity;

    public OrderDishModel() {
    }

    public OrderDishModel(Integer id, OrderModel order, DishModel dish, Integer quantity) {
        this.id = id;
        this.order = order;
        this.dish = dish;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public DishModel getDish() {
        return dish;
    }

    public void setDish(DishModel dish) {
        this.dish = dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
