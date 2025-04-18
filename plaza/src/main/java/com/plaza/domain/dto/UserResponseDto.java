package com.plaza.domain.dto;

import java.time.LocalDate;

public class UserResponseDto {

    private int dni;
    private String name;
    private String lastName;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private RoleResponseDto role;

    public UserResponseDto(int dni, String name, String lastName, String phone, LocalDate birthDate, String email, RoleResponseDto role) {
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.birthDate = birthDate;
        this.email = email;
        this.role = role;
    }

    public UserResponseDto() {
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleResponseDto getRole() {
        return role;
    }

    public void setRole(RoleResponseDto role) {
        this.role = role;
    }
}
