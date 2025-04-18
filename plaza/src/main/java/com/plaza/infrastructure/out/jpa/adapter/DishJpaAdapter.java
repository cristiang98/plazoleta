package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.DishModel;
import com.plaza.domain.spi.IDishPersistencePort;
import com.plaza.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public DishModel saveDish(DishModel dishModel) {
        return dishEntityMapper.toDishModel(dishRepository.save(dishEntityMapper.toDishEntity(dishModel)));
    }

    @Override
    public DishModel findById(Integer id) {
        return dishEntityMapper.toDishModel(dishRepository.findById(id).orElse(null));
    }

    @Override
    public void updateDish(DishModel dishModel) {
        dishEntityMapper.toDishModel(dishRepository.save(dishEntityMapper.toDishEntity(dishModel)));
    }

    @Override
    public Page<DishModel> getDishes(Integer nit ,int page, int size, String categoryFilter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("category").ascending());

        if(categoryFilter == null){
            return dishRepository.findByRestaurantNit(nit,pageable).map(dishEntityMapper::toDishModel);
        }else {
            return dishRepository.findByCategoryName(nit,categoryFilter, pageable).map(dishEntityMapper::toDishModel);
        }
    }
}
