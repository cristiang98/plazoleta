package com.plaza.domain.api;

import com.plaza.domain.model.CategoryModel;

public interface ICategoryServicePort {

    CategoryModel findByName(String name);

}
