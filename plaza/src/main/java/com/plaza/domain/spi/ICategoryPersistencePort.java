package com.plaza.domain.spi;

import com.plaza.domain.model.CategoryModel;

public interface ICategoryPersistencePort {

    CategoryModel findByName(String name);

}
