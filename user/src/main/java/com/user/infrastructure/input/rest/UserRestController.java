package com.user.infrastructure.input.rest;

import com.user.application.dto.request.UserRequestDto;
import com.user.application.dto.response.UserResponseDto;
import com.user.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(
            summary = "Guardar un propietario",
            description = "Este endpoint permite guardar un dueño."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El dueño fue guardado exitosamente solo puede acceder a este endpoint un usuario con rol de administrador",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PostMapping("/save-owner")
    public void saveUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveUser(userRequestDto);
    }

    @Operation(
            summary = "Guardar un empleado",
            description = "Este endpoint permite guardar un cliente." +
                    " Solo puede acceder a este endpoint un usuario con rol de propietario"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El cliente fue guardado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })

    @PostMapping("/save-employee")
    public void saveUserEmployee(@Valid @RequestBody UserRequestDto userRequestDto,
                                 @CookieValue("token") String token) {
        userHandler.saveEmployee(userRequestDto, token);
    }

    @Operation(
            summary = "Guardar un cliente",
            description = "Este endpoint permite guardar un cliente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El cliente fue guardado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRequestDto.class))
            )
    })

    @PostMapping("/save-client")
    public void saveUserClient(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveClient(userRequestDto);
    }


    @Operation(
            summary = "Obtener un usuario por DNI",
            description = "Este endpoint permite buscar un usuario utilizando un número de DNI."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El usuario fue encontrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "DNI inválido"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/get-by-dni")
    public UserResponseDto getUserByDni(@Valid @RequestParam Integer dni) {
        return userHandler.getUserByDni(dni);
    }

}
