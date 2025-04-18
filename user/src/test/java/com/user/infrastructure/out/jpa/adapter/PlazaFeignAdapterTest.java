package com.user.infrastructure.out.jpa.adapter;

import com.user.domain.dto.RestaurantEmpRequestDto;
import com.user.infrastructure.feign.IPlazaFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class PlazaFeignAdapterTest {


    @Mock
    private IPlazaFeignClient mockFeignClient;

    private PlazaFeignAdapter plazaFeignAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        plazaFeignAdapter = new PlazaFeignAdapter(mockFeignClient);
    }

    @Test
    void saveRestaurantEmployee_ShouldInvokeFeignClient() {
        // Arrange
        RestaurantEmpRequestDto requestDto = new RestaurantEmpRequestDto();

        // Act
        plazaFeignAdapter.saveRestaurantEmployee(requestDto);

        // Assert
        verify(mockFeignClient).saveRestaurantEmployee(requestDto);
    }
}