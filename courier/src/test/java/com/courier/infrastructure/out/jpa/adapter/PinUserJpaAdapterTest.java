package com.courier.infrastructure.out.jpa.adapter;

import com.courier.domain.model.PinUserModel;
import com.courier.infrastructure.out.jpa.entity.PinUserEntity;
import com.courier.infrastructure.out.jpa.mapper.IPinUserEntityMapper;
import com.courier.infrastructure.out.jpa.repository.IPinUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PinUserJpaAdapterTest {

    @Mock
    private IPinUserRepository pinUserRepository;

    @Mock
    private IPinUserEntityMapper pinUserEntityMapper;

    @InjectMocks
    private PinUserJpaAdapter pinUserJpaAdapter;

    /**
     * Tests the `savePinUser` method of the `PinUserJpaAdapter` class.
     * Verifies that the repository's `save` method is invoked with the
     * correct entity that is mapped from the provided model.
     */
    @Test
    void testSavePinUser() {
        // Arrange
        PinUserModel pinUserModel = new PinUserModel(1, 1234, 100, 200);
        PinUserEntity entity = new PinUserEntity(); // Placeholder for the entity representation from the mapper

        when(pinUserEntityMapper.toEntity(pinUserModel)).thenReturn(entity);

        // Act
        pinUserJpaAdapter.savePinUser(pinUserModel);

        // Assert
        verify(pinUserEntityMapper).toEntity(pinUserModel);
        verify(pinUserRepository).save(entity);
    }

    /**
     * Tests the `findByUserIdAndOrderId` method of the `PinUserJpaAdapter` class.
     * Verifies that the repository's `findByUserIdAndOrderId` method is invoked with the
     * correct parameters and the resulting entity is properly mapped to a model.
     */
    @Test
    void testFindByUserIdAndOrderId() {
        // Arrange
        Integer userId = 100;
        Integer orderId = 200;
        PinUserEntity entity = new PinUserEntity(); // Placeholder for the returned entity
        PinUserModel expectedModel = new PinUserModel(1, 1234, userId, orderId);

        when(pinUserRepository.findByUserIdAndOrderId(userId, orderId)).thenReturn(entity);
        when(pinUserEntityMapper.toModel(entity)).thenReturn(expectedModel);

        // Act
        PinUserModel actualModel = pinUserJpaAdapter.findByUserIdAndOrderId(userId, orderId);

        // Assert
        verify(pinUserRepository).findByUserIdAndOrderId(userId, orderId);
        verify(pinUserEntityMapper).toModel(entity);
    }

    /**
     * Tests the `findByPin` method of the `PinUserJpaAdapter` class.
     * Verifies that the repository's `findByPin` method is invoked with the
     * correct parameter and the resulting entity is properly mapped to a model.
     */
    @Test
    void testFindByPin() {
        // Arrange
        Integer pin = 1234;
        PinUserEntity entity = new PinUserEntity(); // Placeholder for the returned entity
        PinUserModel expectedModel = new PinUserModel(1, pin, 100, 200);

        when(pinUserRepository.findByPin(pin)).thenReturn(entity);
        when(pinUserEntityMapper.toModel(entity)).thenReturn(expectedModel);

        // Act
        PinUserModel actualModel = pinUserJpaAdapter.findByPin(pin);

        // Assert
        verify(pinUserRepository).findByPin(pin);
        verify(pinUserEntityMapper).toModel(entity);
    }
}