package com.plaza.infrastructure.out.jpa.repository;

import com.plaza.domain.model.CategoryModel;
import com.plaza.infrastructure.out.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    CategoryEntity findByName(String name);

}
