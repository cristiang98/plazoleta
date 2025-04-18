package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.DishModel;
import com.plaza.infrastructure.out.jpa.entity.DishEntity;
import com.plaza.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.IDishRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DishJpaAdapterTest {

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @InjectMocks
    private DishJpaAdapter dishJpaAdapter;

    public DishJpaAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveDish_ShouldReturnSavedDishModel() {
        DishModel dishModel = new DishModel(1, "Pizza", 10, "Delicious pizza", "http://example.com/pizza.jpg", true, null, null);
        DishEntity mockedDishEntity = Mockito.mock(DishEntity.class);

        Mockito.when(dishEntityMapper.toDishEntity(dishModel)).thenReturn(mockedDishEntity);
        Mockito.when(dishRepository.save(mockedDishEntity)).thenReturn(mockedDishEntity);
        Mockito.when(dishEntityMapper.toDishModel(mockedDishEntity)).thenReturn(dishModel);

        DishModel result = dishJpaAdapter.saveDish(dishModel);

        assertEquals(dishModel, result);
        Mockito.verify(dishEntityMapper).toDishEntity(dishModel);
        Mockito.verify(dishRepository).save(mockedDishEntity);
        Mockito.verify(dishEntityMapper).toDishModel(mockedDishEntity);
    }

    @Test
    public void testFindById_ShouldReturnDishModel() {
        Integer dishId = 1;
        DishModel expectedDishModel = new DishModel(dishId, "Pizza", 10, "Delicious pizza", "http://example.com/pizza.jpg", true, null, null);
        DishEntity mockedDishEntity = Mockito.mock(DishEntity.class);

        Mockito.when(dishRepository.findById(dishId)).thenReturn(java.util.Optional.of(mockedDishEntity));
        Mockito.when(dishEntityMapper.toDishModel(mockedDishEntity)).thenReturn(expectedDishModel);

        DishModel result = dishJpaAdapter.findById(dishId);

        assertEquals(expectedDishModel, result);
        Mockito.verify(dishRepository).findById(dishId);
        Mockito.verify(dishEntityMapper).toDishModel(mockedDishEntity);
    }

    @Test
    public void testFindById_ShouldReturnNull_WhenDishNotFound() {
        Integer dishId = 1;

        Mockito.when(dishRepository.findById(dishId)).thenReturn(java.util.Optional.empty());

        DishModel result = dishJpaAdapter.findById(dishId);

        assertEquals(null, result);
        Mockito.verify(dishRepository).findById(dishId);
    }

    @Test
    public void testSaveDish_ShouldHandleNullDishModel() {
        DishModel result = dishJpaAdapter.saveDish(null);

        assertNull(result);
    }

    @Test
    public void testSaveDish_ShouldHandleException_WhenRepositorySaveFails() {
        DishModel dishModel = new DishModel(1, "Pizza", 10, "Delicious pizza", "http://example.com/pizza.jpg", true, null, null);
        DishEntity mockedDishEntity = Mockito.mock(DishEntity.class);

        Mockito.when(dishEntityMapper.toDishEntity(dishModel)).thenReturn(mockedDishEntity);
        Mockito.when(dishRepository.save(mockedDishEntity)).thenThrow(new RuntimeException("Repository save failed"));

        RuntimeException exception = null;

        try {
            dishJpaAdapter.saveDish(dishModel);
        } catch (RuntimeException ex) {
            exception = ex;
        }

        assertEquals("Repository save failed", exception.getMessage());
        Mockito.verify(dishEntityMapper).toDishEntity(dishModel);
        Mockito.verify(dishRepository).save(mockedDishEntity);
        Mockito.verifyNoMoreInteractions(dishEntityMapper);
    }

    @Test
    public void testUpdateDish_ShouldUpdateAndReturnDishModel() {
        DishModel dishModel = new DishModel(1, "Pizza", 15, "Updated pizza", "http://example.com/pizza_updated.jpg", true, null, null);
        DishEntity mockedDishEntity = Mockito.mock(DishEntity.class);

        Mockito.when(dishEntityMapper.toDishEntity(dishModel)).thenReturn(mockedDishEntity);
        Mockito.when(dishRepository.save(mockedDishEntity)).thenReturn(mockedDishEntity);
        Mockito.when(dishEntityMapper.toDishModel(mockedDishEntity)).thenReturn(dishModel);

        dishJpaAdapter.updateDish(dishModel);

        Mockito.verify(dishEntityMapper).toDishEntity(dishModel);
        Mockito.verify(dishRepository).save(mockedDishEntity);
    }

    @Test
    public void testUpdateDish_ShouldHandleException_WhenRepositoryUpdateFails() {
        DishModel dishModel = new DishModel(1, "Pizza", 15, "Updated pizza", "http://example.com/pizza_updated.jpg", true, null, null);
        DishEntity mockedDishEntity = Mockito.mock(DishEntity.class);

        Mockito.when(dishEntityMapper.toDishEntity(dishModel)).thenReturn(mockedDishEntity);
        Mockito.when(dishRepository.save(mockedDishEntity)).thenThrow(new RuntimeException("Repository update failed"));

        RuntimeException exception = null;

        try {
            dishJpaAdapter.updateDish(dishModel);
        } catch (RuntimeException ex) {
            exception = ex;
        }

        assertEquals("Repository update failed", exception.getMessage());
        Mockito.verify(dishEntityMapper).toDishEntity(dishModel);
        Mockito.verify(dishRepository).save(mockedDishEntity);
        Mockito.verifyNoMoreInteractions(dishEntityMapper);
    }

    @Test
    public void testGetDishes_ShouldReturnPagedDishes() {
        Integer nit = 123;
        int page = 0;
        int size = 10;
        String categoryFilter = null;

        DishEntity mockedDishEntity = Mockito.mock(DishEntity.class);
        DishModel mockedDishModel = Mockito.mock(DishModel.class);
        Page<DishEntity> mockedPage = Mockito.mock(Page.class);

        Mockito.when(mockedPage.map(Mockito.any())).thenReturn(Mockito.mock(Page.class));
        Mockito.when(dishRepository.findByRestaurantNit(Mockito.eq(nit), Mockito.any(Pageable.class))).thenReturn(mockedPage);
        Mockito.when(mockedPage.map(dishEntityMapper::toDishModel)).thenReturn(Mockito.mock(Page.class));

        dishJpaAdapter.getDishes(nit, page, size, categoryFilter);

        Mockito.verify(dishRepository).findByRestaurantNit(Mockito.eq(nit), Mockito.any(Pageable.class));
    }

    @Test
    public void testGetDishes_ShouldReturnPagedDishesWithCategoryFilter() {
        Integer nit = 123;
        int page = 0;
        int size = 10;
        String categoryFilter = "Pizza";

        DishEntity mockedDishEntity = Mockito.mock(DishEntity.class);
        DishModel mockedDishModel = Mockito.mock(DishModel.class);
        Page<DishEntity> mockedPage = Mockito.mock(Page.class);

        Mockito.when(dishRepository.findByCategoryName(Mockito.eq(nit), Mockito.eq(categoryFilter), Mockito.any(Pageable.class))).thenReturn(mockedPage);
        Mockito.when(mockedPage.map(dishEntityMapper::toDishModel)).thenReturn(Mockito.mock(Page.class));

        dishJpaAdapter.getDishes(nit, page, size, categoryFilter);

        Mockito.verify(dishRepository).findByCategoryName(Mockito.eq(nit), Mockito.eq(categoryFilter), Mockito.any(Pageable.class));
    }


}
