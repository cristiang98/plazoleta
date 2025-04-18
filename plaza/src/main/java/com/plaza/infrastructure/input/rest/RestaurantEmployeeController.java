package com.plaza.infrastructure.input.rest;

import com.plaza.application.dto.request.RestaurantEmpRequestDto;
import com.plaza.application.handler.IRestaurantEmployeeHanlder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant-employee")
@RequiredArgsConstructor
public class RestaurantEmployeeController {

    private final IRestaurantEmployeeHanlder restaurantEmployeeHandler;

    @Operation(summary = "guardar empleado de restaurante", description = "Este endpoint permite guardar un empleado de restaurante.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El empleado de restaurante fue guardado exitosamente"
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PostMapping("/save")
    public void saveRestaurantEmployee(@Valid @RequestBody RestaurantEmpRequestDto restaurantEmpRequestDto, @CookieValue("token") String token) {
        restaurantEmployeeHandler.saveEmployeeRestaurant(restaurantEmpRequestDto, token);

    }

}
