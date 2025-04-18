package com.plaza.infrastructure.input.rest;

import com.plaza.application.dto.request.DishRequestDto;
import com.plaza.application.dto.request.UpdateDishRequestDto;
import com.plaza.application.dto.response.DishResponseDto;
import com.plaza.application.handler.IDishHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dish")
public class DishController {

    private final IDishHandler dishHandler;

    @Operation(summary = "guardar plato", description = "Este endpoint permite guardar un plato. " +
            " Solo puede acceder a este endpoint un usuario con rol de propietario")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El plato fue guardado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DishRequestDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PostMapping("/save-dish")
    public void saveDish(@Valid @RequestBody DishRequestDto dishRequestDto,
                         @CookieValue String token) {
        dishHandler.saveDish(dishRequestDto, token);
    }

    @Operation(summary = "actualizar plato", description = "Este endpoint permite actualizar un plato."+
            " Solo puede acceder a este endpoint un usuario con rol de propietario")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El plato fue actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateDishRequestDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })
    @PutMapping("/update-dish/{id}")
    public void updateDish(@Valid @PathVariable Integer id ,
                           @Valid @RequestBody UpdateDishRequestDto updateDishRequestDto,
                           @CookieValue String token) {
        dishHandler.updateDish(id, updateDishRequestDto, token);
    }

    @Operation(
            summary = "cambia el estado de un plato",
            description = "Este endpoint permite cambiar el estado de un plato. " +
                    "Solo puede acceder a este endpoint un usuario con rol de propietario")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "El estado del plato fue cambiado exitosamente"
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })

    @PatchMapping("/toggle")
    public void toggleDish(@Valid @RequestParam Integer id,
                             @CookieValue String token) {
        dishHandler.toggleDish(id, token);
    }

    @Operation(
            summary = "obtener platos",
            description = "Este endpoint permite obtener los platos de un restaurante.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Los platos fueron obtenidos exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DishResponseDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "403", description = "Prohibido")
    })

    @GetMapping("/dishes")
    public Page<DishResponseDto> getRestaurants(
            @RequestParam Integer nit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String categoryFilter
    ) {
        return dishHandler.getDishes(nit,page, size, categoryFilter);
    }

}
