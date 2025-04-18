package com.courier.infrastructure.configuration;

import com.courier.domain.api.IPinUserServicePort;
import com.courier.domain.spi.IPinUserPersistencePort;
import com.courier.domain.usecase.PinUserUserCase;
import com.courier.infrastructure.out.jpa.adapter.PinUserJpaAdapter;
import com.courier.infrastructure.out.jpa.mapper.IPinUserEntityMapper;
import com.courier.infrastructure.out.jpa.repository.IPinUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IPinUserRepository pinUserRepository;
    private final IPinUserEntityMapper pinUserEntityMapper;


    @Bean
    public IPinUserPersistencePort pinUserPersistencePort() {
        return new PinUserJpaAdapter(pinUserRepository,pinUserEntityMapper);
    }

    @Bean
    public IPinUserServicePort pinUserServicePort() {
        return new PinUserUserCase(pinUserPersistencePort(), random());
    }

    @Bean
    public Random random() {
        return new Random();
    }

}
