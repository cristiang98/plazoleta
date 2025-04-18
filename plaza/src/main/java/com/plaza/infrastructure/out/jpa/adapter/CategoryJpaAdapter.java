package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.CategoryModel;
import com.plaza.domain.spi.ICategoryPersistencePort;
import com.plaza.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public CategoryModel findByName(String name) {
        return categoryEntityMapper.toCategoryModel(categoryRepository.findByName(name));
    }
}
