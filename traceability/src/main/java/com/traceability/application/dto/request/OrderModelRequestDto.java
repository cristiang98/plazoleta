package com.traceability.application.dto.request;

import java.time.LocalDateTime;

public class OrderModelRequestDto {

    private Integer id;

    private Integer idClient;

    private LocalDateTime date;

    private Integer idEmployee;

    public OrderModelRequestDto() {
    }

    public OrderModelRequestDto(Integer id, Integer idClient, LocalDateTime date, Integer idEmployee) {
        this.id = id;
        this.idClient = idClient;
        this.date = date;
        this.idEmployee = idEmployee;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
