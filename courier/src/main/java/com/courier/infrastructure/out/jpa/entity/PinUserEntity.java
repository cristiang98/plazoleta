package com.courier.infrastructure.out.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "pin_user")
@AllArgsConstructor
@NoArgsConstructor
public class PinUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer pin;
    private Integer userId;
    private Integer orderId;

}
