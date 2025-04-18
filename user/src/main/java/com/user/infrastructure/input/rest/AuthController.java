package com.user.infrastructure.input.rest;

import com.user.application.dto.request.UserLoginRequestDto;
import com.user.application.dto.response.TokenResponseDto;
import com.user.application.handler.IAuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthHandler authHandler;

    @Operation(
            summary = "Iniciar sesión",
            description = "Este endpoint permite iniciar sesión en la aplicación."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Inicio de sesión exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDto.class))
            ),
            @ApiResponse(responseCode = "400" , description = "Petición incorrecta"),
    })
    @PostMapping("/auth")
    public ResponseEntity<TokenResponseDto> loginUser(
            @Valid @RequestBody UserLoginRequestDto userLoginRequestDto,
            HttpServletResponse response
    ) {
        TokenResponseDto tokenResponseDto = authHandler.loginUser(userLoginRequestDto);
        // Crear una nueva cookie para almacenar el token
        Cookie cookie = new Cookie("token", tokenResponseDto.getToken());
        cookie.setPath("/"); // Configura el path para usarla en toda la aplicación
        response.addCookie(cookie); // Añadir cookie a la respuesta

        return ResponseEntity.ok(tokenResponseDto);
    }

}
