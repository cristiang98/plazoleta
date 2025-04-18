package com.traceability.domain.usecase;

import com.traceability.domain.api.ITraceabilityServicePort;
import com.traceability.domain.model.OrderEfficiencyModel;
import com.traceability.domain.model.RestaurantEfficiencyModel;
import com.traceability.domain.model.TraceabilityModel;
import com.traceability.domain.spi.ITraceabilityPersistencePort;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    public TraceabilityUseCase(ITraceabilityPersistencePort traceabilityPersistencePort) {
        this.traceabilityPersistencePort = traceabilityPersistencePort;
    }

    @Override
    public void saveTraceability(TraceabilityModel traceabilityModel) {
        traceabilityPersistencePort.saveTraceability(traceabilityModel);
    }

    @Override
    public TraceabilityModel findTraceabilityByIdClient(Integer idClient) {
        return traceabilityPersistencePort.findTraceabilityByIdClient(idClient);
    }

    @Override
    public RestaurantEfficiencyModel getEfficiency(List<OrderEfficiencyModel> orderEfficiencyModels) {
        long totalDurationInMinutes = 0;

        // Crear respuesta inicial
        RestaurantEfficiencyModel restaurantEfficiencyModel = new RestaurantEfficiencyModel();
        List<OrderEfficiencyModel> orderEfficiencies = new ArrayList<>();

        for (OrderEfficiencyModel orderEfficiencyModel : orderEfficiencyModels) {
            OrderEfficiencyModel orderEfficiency = new OrderEfficiencyModel();
            orderEfficiency.setId(orderEfficiencyModel.getId());
            orderEfficiency.setIdEmployee(orderEfficiencyModel.getIdEmployee());

            // Verificar si la orden tiene trazabilidad

            TraceabilityModel traceabilityModel = traceabilityPersistencePort.findTraceabilityByIdOrderAndStatus(orderEfficiencyModel.getId(), "ENTREGADO");

            LocalDateTime orderStartDate = traceabilityModel.getDateStart();
            LocalDateTime orderEndDate = traceabilityModel.getDateEnd();

            if (orderEndDate == null) {
                orderEndDate = LocalDateTime.now();
            }

            orderEfficiency.setDateStart(orderStartDate);
            orderEfficiency.setDateEnd(orderEndDate);

            // Calculamos la duración en minutos y la asignamos al modelo
            long durationInMinutes = ChronoUnit.MINUTES.between(orderStartDate, orderEndDate);
            orderEfficiency.setTimeInMinutes(durationInMinutes);

            totalDurationInMinutes += durationInMinutes;

            orderEfficiencies.add(orderEfficiency);
        }


        // Calcular la eficiencia promedio
        Long averageDurationInMinutes = !orderEfficiencyModels.isEmpty() ? totalDurationInMinutes / orderEfficiencyModels.size() : 0;

        // Configurar el modelo de respuesta
        restaurantEfficiencyModel.setTimeInMinutes(totalDurationInMinutes);
        restaurantEfficiencyModel.setAverageEfficiency(averageDurationInMinutes);
        restaurantEfficiencyModel.setOrdersEfficency(orderEfficiencies);

        return restaurantEfficiencyModel;
    }
}
