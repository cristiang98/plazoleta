package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.CategoryModel;
import com.plaza.infrastructure.out.jpa.entity.CategoryEntity;
import com.plaza.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryJpaAdapterTest {


    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    public CategoryJpaAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByName_ShouldReturnCategoryModel_WhenCategoryExists() {
        // Arrange
        String categoryName = "Electronics";
        CategoryEntity mockCategoryEntity = new CategoryEntity(); // Example mock entity
        CategoryModel expectedCategoryModel = new CategoryModel(1, "Electronics", "Category for electronics");

        when(categoryRepository.findByName(categoryName)).thenReturn(mockCategoryEntity);
        when(categoryEntityMapper.toCategoryModel(mockCategoryEntity)).thenReturn(expectedCategoryModel);

        // Act
        CategoryModel result = categoryJpaAdapter.findByName(categoryName);

        // Assert
        assertNotNull(result);
        assertEquals(expectedCategoryModel.getId(), result.getId());
        assertEquals(expectedCategoryModel.getName(), result.getName());
        assertEquals(expectedCategoryModel.getDescription(), result.getDescription());
        verify(categoryRepository).findByName(categoryName);
        verify(categoryEntityMapper).toCategoryModel(mockCategoryEntity);
    }



    @Test
    void findByName_ShouldThrowException_WhenRepositoryThrowsError() {
        // Arrange
        String categoryName = "FaultyCategory";

        when(categoryRepository.findByName(categoryName)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> categoryJpaAdapter.findByName(categoryName));
        assertEquals("Database error", exception.getMessage());
        verify(categoryRepository).findByName(categoryName);
        verify(categoryEntityMapper, never()).toCategoryModel(any());
    }
}