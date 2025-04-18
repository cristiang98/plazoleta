package com.plaza.infrastructure.out.jpa.mapper;

import com.plaza.domain.model.CategoryModel;
import com.plaza.infrastructure.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ICategoryEntityMapper {

    CategoryEntity toCategoryEntity(CategoryModel categoryModel);
    CategoryModel toCategoryModel(CategoryEntity categoryEntity);

}