package com.plaza.infrastructure.out.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RestaurantEntity {

    @Id
    @Column(name = "nit")
    private Integer nit;
    private String name;
    private String address;
    private Integer phone;
    private String urlLogo;
    private Integer idOwner;

}
