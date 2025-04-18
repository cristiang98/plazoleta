package com.traceability.domain.model;

import java.time.LocalDateTime;

public class TraceabilityModel {

    private String id;
    private Integer idOrder;
    private Integer idClient;
    private String emailClient;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private String statusCurrent;
    private Integer idEmployee;
    private String emailEmployee;


    public TraceabilityModel(String id, Integer idOrder, Integer idClient, String emailClient, LocalDateTime dateStart, LocalDateTime dateEnd, String statusCurrent, Integer idEmployee, String emailEmployee) {
        this.id = id;
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.emailClient = emailClient;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.statusCurrent = statusCurrent;
        this.idEmployee = idEmployee;
        this.emailEmployee = emailEmployee;
    }

    public TraceabilityModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
