package com.traceability.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order_traceability")
public class TraceabilityEntity {

    @Id
    private String id;
    private Integer idOrder;
    private Integer idClient;
    private String emailClient;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private String statusCurrent;
    private Integer idEmployee;
    private String emailEmployee;

}
