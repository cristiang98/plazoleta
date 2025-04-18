package com.traceability.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraceabilityResponseDto {

    @Schema(description = "Id de la trazabilidad", example = "1")
    private String id;
    @Schema(description = "Id del pedido", example = "1")
    private Integer idOrder;
    @Schema(description = "Id del cliente", example = "1")
    private Integer idClient;
    @Schema(description = "Email del cliente", example = "example@example.com")
    private String emailClient;
    @Schema(description = "Estado actual", example = "PENDING")
    private String statusCurrent;
    @Schema(description = "Id del empleado", example = "1")
    private Integer idEmployee;
    @Schema(description = "Email del empleado", example = "example@example.com")
    private String emailEmployee;
    @Schema(description = "Fecha de inicio", example = "2021-10-10T10:00:00")
    private LocalDateTime dateStart;
    @Schema(description = "Fecha de fin", example = "2021-10-10T10:00:00")
    private LocalDateTime dateEnd;

}
