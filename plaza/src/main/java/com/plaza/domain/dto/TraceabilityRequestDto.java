package com.plaza.domain.dto;

import java.time.LocalDateTime;

public class TraceabilityRequestDto {

    private Integer idOrder;
    private Integer idClient;
    private String emailClient;
    private String statusCurrent;
    private Integer idEmployee;
    private String emailEmployee;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;

    public TraceabilityRequestDto(Integer idOrder, Integer idClient, String emailClient, String statusCurrent, Integer idEmployee, String emailEmployee, LocalDateTime dateStart, LocalDateTime dateEnd) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.emailClient = emailClient;
        this.statusCurrent = statusCurrent;
        this.idEmployee = idEmployee;
        this.emailEmployee = emailEmployee;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public TraceabilityRequestDto() {
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getStatusCurrent() {
        return statusCurrent;
    }

    public void setStatusCurrent(String statusCurrent) {
        this.statusCurrent = statusCurrent;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getEmailEmployee() {
        return emailEmployee;
    }

    public void setEmailEmployee(String emailEmployee) {
        this.emailEmployee = emailEmployee;
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
