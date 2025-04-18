package com.plaza.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishRequestDto {

    @Schema(description = "Nombre del plato", example = "Hamburguesa")
    @NotBlank(message = "El nombre del plato no puede estar vacío ni ser nulo")
    private String name; // Nombre del plato

    @Schema(description = "Precio del plato", example = "5000")
    @NotNull(message = "El precio del plato es obligatorio")
    @Positive(message = "El precio del plato debe ser un número entero positivo y mayor que cero")
    private Integer price; // Precio del plato

    @Schema(description = "Descripción del plato", example = "Hamburguesa de carne con queso y lechuga")
    @NotBlank(message = "La descripción del plato no puede estar vacía ni ser nula")
    private String description; // Descripción del plato

    @Schema(description = "URL de la imagen del plato", example = "https://www.google.com")
    @NotBlank(message = "La URL de la imagen no puede estar vacía ni ser nula")
    private String urlImage; // URL de la imagen del plato

    @Schema(description = "Nombre de la categoría a la que pertenece el plato", example = "Comida rápida")
    @NotBlank(message = "El nombre de la categoría no puede estar vacío ni ser nulo")
    private String categoryName;

    @Schema(description = "Nombre del restaurante al que pertenece el plato", example = "McDonald's")
    @NotBlank(message = "El nombre del restaurante no puede estar vacío ni ser nulo")
    private String restaurantName;

}
