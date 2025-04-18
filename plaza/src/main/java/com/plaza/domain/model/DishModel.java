package com.plaza.domain.model;

public class DishModel {

    private Integer id;
    private String name;
    private Integer price;
    private String description;
    private String urlImage;
    private Boolean status;
    private CategoryModel category;
    private RestaurantModel restaurant;

    public DishModel(Integer id, String name, Integer price, String description, String urlImage, Boolean status, CategoryModel category, RestaurantModel restaurant) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.urlImage = urlImage;
        this.status = status;
        this.category = category;
        this.restaurant = restaurant;
    }

    public DishModel() {
    }

    public DishModel(String description, Integer price) {
        this.description = description;
        this.price = price;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }
}
