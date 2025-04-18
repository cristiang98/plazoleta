package com.plaza.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequestDto {

    @NotNull
    private Integer nit;

    @NotBlank
    @Pattern(regexp = "^(?!\\d+$)[\\w\\s]+$", message = "El nombre de la plaza no puede ser solo números")
    private String name;

    @NotBlank
    private String address;

    @NotNull
    @Pattern(regexp = "\\+?\\d+", message = "El celular debe contener un máximo de 13 caracteres y puede contener el símbolo +")
    private String phone;

    @NotBlank
    private String urlLogo;

    @NotNull
    private Integer idOwner;

}
