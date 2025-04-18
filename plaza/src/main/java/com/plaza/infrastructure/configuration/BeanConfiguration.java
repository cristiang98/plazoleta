package com.plaza.infrastructure.configuration;

import com.plaza.domain.api.*;
import com.plaza.domain.spi.*;
import com.plaza.domain.usecase.*;
import com.plaza.infrastructure.feign.adapter.PinUserFeignAdapter;
import com.plaza.infrastructure.feign.adapter.TraceabilityFeignAdapter;
import com.plaza.infrastructure.feign.adapter.UserFeignAdapter;
import com.plaza.infrastructure.out.jpa.adapter.*;
import com.plaza.infrastructure.out.jpa.feign.IPinUserFeignClient;
import com.plaza.infrastructure.out.jpa.feign.ITraceabilityFeignClient;
import com.plaza.infrastructure.out.jpa.feign.IUserFeignClient;
import com.plaza.infrastructure.out.jpa.mapper.*;
import com.plaza.infrastructure.out.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository plazaRepository;
    private final ICategoryRepository categoryRepository;
    private final IDishRepository dishRepository;
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IOrderRepository orderRepository;
    private final IOrderDishRepository orderDishRepository;
    @Qualifier("IRestaurantEntityMapper")
    private final IRestaurantEntityMapper plazaEntityMapper;
    @Qualifier("ICategoryEntityMapper")
    private final ICategoryEntityMapper categoryEntityMapper;
    @Qualifier("IDishEntityMapper")
    private final IDishEntityMapper dishEntityMapper;
    @Qualifier("IRestaurantEmplEntityMapper")
    private final IRestaurantEmplEntityMapper restaurantEmployeeEntityMapper;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IUserFeignClient userFeignClient;
    private final IPinUserFeignClient pinUserFeignClient;
    private final ITraceabilityFeignClient traceabilityFeignClient;
    private final IJwtHanlderPort jwtHandler;

    @Bean
    public IUserFeignClientPort userFeignClientPort() {
        return new UserFeignAdapter(userFeignClient);
    }

    @Bean
    public ITraceabilityFeignClientPort traceabilityFeignClientPort() {
        return new TraceabilityFeignAdapter(traceabilityFeignClient);
    }

    @Bean
    public IPinUserFeignPort pinUserFeignPort() {
        return new PinUserFeignAdapter(pinUserFeignClient);
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(plazaRepository, plazaEntityMapper);
    }


    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userFeignClientPort());
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), restaurantPersistencePort(), jwtHandler);
    }

    @Bean
    public IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort() {
        return new RestaurantEmployeeJpaAdapter(restaurantEmployeeRepository, restaurantEmployeeEntityMapper);
    }

    @Bean
    public IRestaurantEmployeeServicePort restaurantEmployeeServicePort() {
        return new RestaurantEmployeeUseCase(restaurantEmployeePersistencePort(), restaurantPersistencePort(), jwtHandler);
    }


    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(orderRepository, orderEntityMapper, orderDishRepository, orderDishEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(
                orderPersistencePort(),
                jwtHandler,
                dishPersistencePort(),
                restaurantPersistencePort(),
                restaurantEmployeePersistencePort(),
                userFeignClientPort(),
                pinUserFeignPort(),
                traceabilityFeignClientPort()
        );
    }

}
