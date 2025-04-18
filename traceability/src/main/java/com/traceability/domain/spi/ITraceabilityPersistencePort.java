package com.traceability.domain.spi;

import com.traceability.domain.model.TraceabilityModel;

import java.util.List;

public interface ITraceabilityPersistencePort {

    void saveTraceability(TraceabilityModel traceabilityModel);

    TraceabilityModel findTraceabilityByIdClient(Integer idClient);

    TraceabilityModel findTraceabilityByIdOrderAndStatus(Integer idOrder, String status);
}
