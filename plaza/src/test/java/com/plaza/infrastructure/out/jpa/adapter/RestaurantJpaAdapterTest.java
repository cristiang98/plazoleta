package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.RestaurantModel;
import com.plaza.infrastructure.out.jpa.entity.RestaurantEntity;
import com.plaza.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantJpaAdapterTest {

    @Test
    void testFindAllSuccess() {
        // Preparar datos simulados
        RestaurantEntity restaurantEntityMock1 = new RestaurantEntity();
        RestaurantEntity restaurantEntityMock2 = new RestaurantEntity();

        // Configurar modelos con nombres en orden alfabético
        RestaurantModel restaurantModelMock1 = new RestaurantModel(2, "Restaurant 2", "Address 2", 987654321, "http://logo2.com", 2);
        RestaurantModel restaurantModelMock2 = new RestaurantModel(1, "Restaurant 1", "Address 1", 123456789, "http://logo1.com", 1);

        // Configurar el retorno del mock del repositorio en orden ascendente
        Page<RestaurantEntity> restaurantEntitiesPage = new PageImpl<>(List.of(restaurantEntityMock1, restaurantEntityMock2)); // Orden alfabético
        when(restaurantRepository.findAll(any(PageRequest.class))).thenReturn(restaurantEntitiesPage);

        // Configurar el mock del mapeador para devolver los modelos en el orden esperado
        when(restaurantEntityMapper.toRestaurantModel(restaurantEntityMock1)).thenReturn(restaurantModelMock1);
        when(restaurantEntityMapper.toRestaurantModel(restaurantEntityMock2)).thenReturn(restaurantModelMock2);

        // Llamar al método en prueba
        Page<RestaurantModel> result = restaurantJpaAdapter.findAll(0, 2);

        assertEquals(2, result.getTotalElements());

        // Validar contenido en orden ascendente
        assertEquals("Restaurant 1", result.getContent().get(0).getName());

        // Verificar interacciones con los mocks
        verify(restaurantRepository).findAll(any(PageRequest.class));
        verify(restaurantEntityMapper, times(2)).toRestaurantModel(any(RestaurantEntity.class));
    }

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    public RestaurantJpaAdapterTest() {
        MockitoAnnotations.openMocks(this); // Initializes mocks and injects them into the class
    }

    @Test
    void testSaveRestaurantSuccess() {
        // Prepare input and expected objects
        RestaurantModel restaurantModelInput = new RestaurantModel(123, "Test Restaurant", "123 Test St", 987654321, "http://logo.com", 1);
        RestaurantEntity restaurantEntityMock = new RestaurantEntity();
        RestaurantEntity savedRestaurantEntityMock = new RestaurantEntity();
        RestaurantModel restaurantModelOutput = new RestaurantModel(123, "Test Restaurant", "123 Test St", 987654321, "http://logo.com", 1);

        // Mock behavior of mapper and repository
        when(restaurantEntityMapper.toRestaurantEntity(restaurantModelInput)).thenReturn(restaurantEntityMock);
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(savedRestaurantEntityMock);
        when(restaurantEntityMapper.toRestaurantModel(savedRestaurantEntityMock)).thenReturn(restaurantModelOutput);

        // Call the method under test
        RestaurantModel result = restaurantJpaAdapter.saveRestaurant(restaurantModelInput);

        // Verify and assert results
        assertEquals(restaurantModelOutput, result);
        verify(restaurantEntityMapper).toRestaurantEntity(restaurantModelInput);
        verify(restaurantRepository).save(restaurantEntityMock);
        verify(restaurantEntityMapper).toRestaurantModel(savedRestaurantEntityMock);
    }

    @Test
    void testExistsRestaurantReturnsTrue() {
        // Prepare input
        Integer restaurantNit = 123;

        // Mock behavior of the repository
        when(restaurantRepository.existsById(restaurantNit)).thenReturn(true);

        // Call the method under test
        Boolean result = restaurantJpaAdapter.existsRestaurant(restaurantNit);

        // Verify and assert results
        assertEquals(true, result);
        verify(restaurantRepository).existsById(restaurantNit);
    }

    @Test
    void testExistsRestaurantReturnsFalse() {
        // Prepare input
        Integer restaurantNit = 456;

        // Mock behavior of the repository
        when(restaurantRepository.existsById(restaurantNit)).thenReturn(false);

        // Call the method under test
        Boolean result = restaurantJpaAdapter.existsRestaurant(restaurantNit);

        // Verify and assert results
        assertEquals(false, result);
        verify(restaurantRepository).existsById(restaurantNit);
    }

    @Test
    void testFindByNameSuccess() {
        // Prepare inputs and mock outputs
        String restaurantName = "Test Restaurant";
        RestaurantEntity restaurantEntityMock = new RestaurantEntity();
        RestaurantModel restaurantModelMock = new RestaurantModel(123, "Test Restaurant", "123 Test St", 987654321, "http://logo.com", 1);

        // Mock behavior of mapper and repository
        when(restaurantRepository.findByName(restaurantName)).thenReturn(restaurantEntityMock);
        when(restaurantEntityMapper.toRestaurantModel(restaurantEntityMock)).thenReturn(restaurantModelMock);

        // Call the method under test
        RestaurantModel result = restaurantJpaAdapter.findByName(restaurantName);

        // Verify and assert results
        assertEquals(restaurantModelMock, result);
        verify(restaurantRepository).findByName(restaurantName);
        verify(restaurantEntityMapper).toRestaurantModel(restaurantEntityMock);
    }

    @Test
    void testFindByNameNotFound() {
        // Prepare input
        String restaurantName = "Nonexistent Restaurant";

        // Mock behavior of the repository
        when(restaurantRepository.findByName(restaurantName)).thenReturn(null);

        // Call the method under test
        RestaurantModel result = restaurantJpaAdapter.findByName(restaurantName);

        // Verify and assert results
        assertEquals(null, result);
        verify(restaurantRepository).findByName(restaurantName);
    }

    @Test
    void testFindByIdOwnerSuccess() {
        // Prepare inputs and mock outputs
        Integer idOwner = 1;
        RestaurantEntity restaurantEntityMock = new RestaurantEntity();
        RestaurantModel restaurantModelMock = new RestaurantModel(123, "Test Restaurant", "123 Test St", 987654321, "http://logo.com", idOwner);

        // Mock behavior of mapper and repository
        when(restaurantRepository.findByIdOwner(idOwner)).thenReturn(restaurantEntityMock);
        when(restaurantEntityMapper.toRestaurantModel(restaurantEntityMock)).thenReturn(restaurantModelMock);

        // Call the method under test
        RestaurantModel result = restaurantJpaAdapter.findByIdOwner(idOwner);

        // Verify and assert results
        assertEquals(restaurantModelMock, result);
        verify(restaurantRepository).findByIdOwner(idOwner);
        verify(restaurantEntityMapper).toRestaurantModel(restaurantEntityMock);
    }

    @Test
    void testFindByIdOwnerNotFound() {
        // Prepare input
        Integer idOwner = 2;

        // Mock behavior of the repository
        when(restaurantRepository.findByIdOwner(idOwner)).thenReturn(null);

        // Call the method under test
        RestaurantModel result = restaurantJpaAdapter.findByIdOwner(idOwner);

        // Verify and assert results
        assertEquals(null, result);
        verify(restaurantRepository).findByIdOwner(idOwner);
    }
}