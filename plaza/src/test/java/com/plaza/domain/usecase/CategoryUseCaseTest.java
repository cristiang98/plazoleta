package com.plaza.domain.usecase;

import com.plaza.domain.model.CategoryModel;
import com.plaza.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class CategoryUseCaseTest {



    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    CategoryUseCaseTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByName_Found() {
        // Arrange
        String categoryName = "Electronics";
        CategoryModel expectedCategory = new CategoryModel(1, "Electronics", "Electronics category description");
        when(categoryPersistencePort.findByName(categoryName)).thenReturn(expectedCategory);

        // Act
        CategoryModel result = categoryUseCase.findByName(categoryName);

        // Assert
        assertEquals(expectedCategory, result);
    }

    @Test
    void testFindByName_NotFound() {
        // Arrange
        String categoryName = "NonExistentCategory";
        when(categoryPersistencePort.findByName(categoryName)).thenReturn(null);

        // Act
        CategoryModel result = categoryUseCase.findByName(categoryName);

        // Assert
        assertNull(result);
    }
}