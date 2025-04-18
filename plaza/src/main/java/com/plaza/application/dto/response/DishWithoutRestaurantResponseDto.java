package com.plaza.application.dto.response;

import com.plaza.domain.model.CategoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishWithoutRestaurantResponseDto {


    private Integer id;
    private String name;
    private Integer price;
    private String description;
    private String urlImage;
    private Boolean status;
    private CategoryModel category;
}
