package com.plaza.domain.exception;

public class DishNotRestaurantException extends RuntimeException{

    public DishNotRestaurantException(String message){
        super(message);
    }

}
