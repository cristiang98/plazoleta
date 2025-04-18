package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.RestaurantModel;
import com.plaza.domain.spi.IRestaurantPersistencePort;
import com.plaza.infrastructure.out.jpa.entity.RestaurantEntity;
import com.plaza.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public RestaurantModel saveRestaurant(RestaurantModel restaurantModel) {

        RestaurantEntity restaurantEntity = restaurantRepository.save(restaurantEntityMapper.toRestaurantEntity(restaurantModel));

        return restaurantEntityMapper.toRestaurantModel(restaurantEntity);
    }

    @Override
    public Boolean existsRestaurant(Integer nit) {
        return restaurantRepository.existsById(nit);
    }

    @Override
    public RestaurantModel findByIdOwner(Integer idOwner) {
        return restaurantEntityMapper.toRestaurantModel(restaurantRepository.findByIdOwner(idOwner));
    }

    @Override
    public RestaurantModel findByName(String name) {
        return restaurantEntityMapper.toRestaurantModel(restaurantRepository.findByName(name));
    }

    @Override
    public RestaurantModel findById(Integer id) {
        return restaurantRepository.findById(id).map(restaurantEntityMapper::toRestaurantModel).get(); // optional arreglar despues
    }

    @Override
    public Page<RestaurantModel> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return restaurantRepository.findAll(pageable).map(restaurantEntityMapper::toRestaurantModel);
    }
}
