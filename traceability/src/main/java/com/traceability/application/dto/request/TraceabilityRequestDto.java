package com.traceability.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceabilityRequestDto {

    @Schema(description = "Id del pedido", example = "1")
    @NotNull
    private Integer idOrder;

    @Schema(description = "Id del cliente", example = "1")
    @NotNull
    private Integer idClient;

    @Schema(description = "Email del cliente", example = "example@example.com")
    @NotBlank
    private String emailClient;

    @Schema(description = "Estado actual", example = "PENDING")
    @NotBlank
    private String statusCurrent;

    @Schema(description = "Id del empleado", example = "1")
    private Integer idEmployee;

    @Schema(description = "Email del empleado", example = "example@example.com")
    private String emailEmployee;

    @Schema(description = "Fecha de inicio", example = "2021-10-10T10:00:00")
    @NotNull
    private LocalDateTime dateStart;

    @Schema(description = "Fecha de fin", example = "2021-10-10T10:00:00")
    private LocalDateTime dateEnd;

    public TraceabilityRequestDto (Integer id, Integer clientId, String emailClient, String status, LocalDateTime dateTime) {
        this.idOrder = id;
        this.idClient = clientId;
        this.emailClient = emailClient;
        this.statusCurrent = status;
        this.dateStart = dateTime;
    }

}
