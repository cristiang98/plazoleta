package com.user.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserResponseDto", description = "Dto de respuesta de usuario")
public class UserResponseDto {

    @Schema(description = "DNI del usuario", example = "12345678")
    private Integer dni;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String name;

    @Schema(description = "Apellido del usuario", example = "Perez")
    private String lastName;

    @Schema(description = "número telefónico del usuario", example = "+5491123456789")
    private String phone;

    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-01-01")
    private LocalDate birthDate; // mayor a 18 años, recordar hacer en el handler

    @Schema(description = "Correo electrónico del usuario", example = "example@example.com")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "password")
    private String password; // recordar encriptar con BCrypt

    @Schema(description = "Rol del usuario", example = "ROLE_USER")
    private RoleResponseDto role;

    public UserResponseDto(Integer dni, String name, String lastName) {
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
    }
}
