package com.traceability.infrastructure.out.jpa.repository;

import com.traceability.infrastructure.out.jpa.entity.TraceabilityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ITraceabilityRepository extends MongoRepository<TraceabilityEntity, Integer> {

    TraceabilityEntity findByIdClient(Integer idClient);

    TraceabilityEntity findByIdOrderAndStatusCurrent(Integer idOrder, String statusCurrent);



}
