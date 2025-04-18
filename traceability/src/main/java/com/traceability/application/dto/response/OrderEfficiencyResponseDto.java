package com.traceability.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class OrderEfficiencyResponseDto {

    @Schema(description = "Id de la eficiencia del pedido", example = "1")
    private Integer id;
    @Schema(description = "Id del empleado", example = "1")
    private Integer idEmployee;
    @Schema(description = "Fecha de inicio", example = "2021-10-10T10:00:00")
    private LocalDateTime dateStart;
    @Schema(description = "Fecha de fin", example = "2021-10-10T10:00:00")
    private LocalDateTime dateEnd;
    @Schema(description = "Tiempo en minutos", example = "10")
    private Long timeInMinutes;

    public OrderEfficiencyResponseDto(Integer id, Integer idEmployee, LocalDateTime dateStart, LocalDateTime dateEnd, Long timeInMinutes) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.timeInMinutes = timeInMinutes;
    }

    public OrderEfficiencyResponseDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }
}
