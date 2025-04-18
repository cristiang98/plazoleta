package com.traceability.infrastructure.out.jpa.adapter;

import com.traceability.domain.model.TraceabilityModel;
import com.traceability.domain.spi.ITraceabilityPersistencePort;
import com.traceability.infrastructure.out.jpa.mapper.ITraceabilityEntityMapper;
import com.traceability.infrastructure.out.jpa.repository.ITraceabilityRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class TraceabilityJpaAdapter implements ITraceabilityPersistencePort {

    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityEntityMapper traceabilityEntityMapper;

    @Override
    public void saveTraceability(TraceabilityModel traceabilityModel) {
        traceabilityRepository.save(traceabilityEntityMapper.toEntity(traceabilityModel));
    }

    @Override
    public TraceabilityModel findTraceabilityByIdClient(Integer idClient) {
        return traceabilityEntityMapper.toModel(traceabilityRepository.findByIdClient(idClient));
    }

    @Override
    public TraceabilityModel findTraceabilityByIdOrderAndStatus(Integer idOrder, String status) {
        return traceabilityEntityMapper.toModel(traceabilityRepository.findByIdOrderAndStatusCurrent(idOrder, status));
    }

}
