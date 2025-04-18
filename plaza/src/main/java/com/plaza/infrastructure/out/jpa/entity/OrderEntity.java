package com.plaza.infrastructure.out.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.plaza.infrastructure.out.jpa.entity.enums.OrderStatusI;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer idClient;

    private LocalDateTime date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusI status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nit",
            nullable = false)
    private RestaurantEntity restaurant;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonIgnore
    List<OrderDishEntity> orderDishes = new ArrayList<>();

    @Column(name = "id_employee")
    private Integer idEmployee;

    public void addOrderDish(OrderDishEntity dish) {
        orderDishes.add(dish);
        dish.setOrder(this);
    }
}
