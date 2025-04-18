package com.plaza.domain.usecase;

import com.plaza.domain.api.ICategoryServicePort;
import com.plaza.domain.model.CategoryModel;
import com.plaza.domain.spi.ICategoryPersistencePort;


public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public CategoryModel findByName(String name) {
        return categoryPersistencePort.findByName(name);
    }
}
