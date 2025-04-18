package com.traceability.domain.api;
import com.traceability.domain.model.OrderEfficiencyModel;
import com.traceability.domain.model.RestaurantEfficiencyModel;
import com.traceability.domain.model.TraceabilityModel;

import java.util.List;

public interface ITraceabilityServicePort {

    void saveTraceability(TraceabilityModel  traceabilityModel);

    TraceabilityModel findTraceabilityByIdClient(Integer idClient);

    RestaurantEfficiencyModel getEfficiency(List<OrderEfficiencyModel> orderEfficiencyModels);

}
