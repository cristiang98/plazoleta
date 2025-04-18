package com.courier.infrastructure.input.rest;

import com.courier.application.dto.request.PinUserRequestDto;
import com.courier.application.dto.response.PinUserResponseDto;
import com.courier.application.handler.IPinUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/pin")
@RequiredArgsConstructor
@RestController
public class PinUserController {

    private final IPinUserHandler pinUserHandler;


    @Operation(summary = "Save pin user", description = "guardar el pin que se genera, se guarda tambien relacion con el usuario y orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pin guardado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al guardar el pin")
    })
    @PostMapping("/save-pin")
    public void  savePinUser(@Valid @RequestBody PinUserRequestDto pinUserRequestDto,
                             @RequestParam String phone) {
        pinUserHandler.savePinUser(pinUserRequestDto, phone);
    }

    @Operation(summary = "Find pin user", description = "buscar el pin que se genero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pin encontrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al encontrar el pin")
    })
    @GetMapping("/find-pin")
    public PinUserResponseDto findPinUser(@RequestParam Integer pin) {
        return pinUserHandler.findPinUser(pin);
    }

}
