package com.plaza.domain.dto;

import java.time.LocalDateTime;

public class OrderEfficiencyResponseDto {

    private Integer id;
    private Integer idEmployee;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Long timeInMinutes;

    public OrderEfficiencyResponseDto() {
    }

    public OrderEfficiencyResponseDto(Integer id, Integer idEmployee, LocalDateTime dateStart, LocalDateTime dateEnd, Long timeInMinutes) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.timeInMinutes = timeInMinutes;
    }

    public Long getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
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
}
