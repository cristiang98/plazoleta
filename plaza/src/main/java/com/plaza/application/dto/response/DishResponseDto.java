package com.plaza.application.dto.response;

import com.plaza.domain.model.CategoryModel;
import com.plaza.domain.model.RestaurantModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishResponseDto {

    @Schema(description = "Id del plato", example = "1")
    private Integer id;
    @Schema(description = "Nombre del plato", example = "Hamburguesa")
    private String name;
    @Schema(description = "Precio del plato", example = "15000")
    private Integer price;
    @Schema(description = "Descripción del plato", example = "Hamburguesa de carne con queso")
    private String description;
    @Schema(description = "Url de la imagen del plato", example = "https://www.google.com")
    private String urlImage;
    @Schema(description = "Estado del plato", example = "true")
    private Boolean status;
    @Schema(description = "Categoria del plato", example = "clase categoria")
    private CategoryModel category;
    @Schema(description = "Restaurante del plato", example = "clase restaurante")
    private RestaurantModel restaurant;
}
