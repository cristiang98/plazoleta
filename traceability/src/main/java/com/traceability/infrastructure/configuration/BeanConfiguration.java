package com.traceability.infrastructure.configuration;

import com.traceability.domain.api.ITraceabilityServicePort;
import com.traceability.domain.spi.ITraceabilityPersistencePort;
import com.traceability.domain.usecase.TraceabilityUseCase;
import com.traceability.infrastructure.out.jpa.adapter.TraceabilityJpaAdapter;
import com.traceability.infrastructure.out.jpa.mapper.ITraceabilityEntityMapper;
import com.traceability.infrastructure.out.jpa.repository.ITraceabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ITraceabilityRepository traceabilityRepository;
    private final ITraceabilityEntityMapper traceabilityEntityMapper;


    @Bean
    public ITraceabilityPersistencePort traceabilityPersistencePort() {
        return new TraceabilityJpaAdapter(traceabilityRepository, traceabilityEntityMapper);
    }

    @Bean
    public ITraceabilityServicePort traceabilityServicePort() {
        return new TraceabilityUseCase(traceabilityPersistencePort());
    }

}
