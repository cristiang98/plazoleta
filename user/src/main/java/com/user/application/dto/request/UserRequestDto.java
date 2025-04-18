package com.user.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @Schema(description = "DNI del usuario", example = "12345678")
    @NotNull(message = "El DNI no puede ser nulo")
    private Integer dni;

    @Schema(description = "Nombre del usuario", example = "Juan")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @Schema(description = "Apellido del usuario", example = "Perez")
    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

    @Schema(description = "número telefónico del usuario", example = "+5491123456789")
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "\\+?\\d{1,13}", message = "El teléfono debe contener un máximo de 13 caracteres y puede incluir el símbolo +")
    private String phone;

    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-01-01")
    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate birthDate; // mayor a 18 años, recordar hacer en el handler

    @Schema(description = "Correo electrónico del usuario", example = "example@example.com")
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe tener un formato válido")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "password")
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

}
