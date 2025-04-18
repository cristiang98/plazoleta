package com.plaza.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDishRequestDto {

    @Schema(description = "descripcion del plato", example = "esto es un cambio de descripcion")
    @NotBlank(message = "La descripción del plato no puede estar vacía ni ser nula")
    private String description;

    @Schema(description = "Precio del plato", example = "15000")
    @NotNull(message = "El precio del plato es obligatorio")
    @Positive(message = "El precio del plato debe ser un número entero positivo y mayor que cero")
    private Integer price;

}
