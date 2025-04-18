package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.OrderDishModel;
import com.plaza.domain.model.OrderModel;
import com.plaza.domain.model.enums.OrderStatus;
import com.plaza.infrastructure.out.jpa.entity.OrderDishEntity;
import com.plaza.infrastructure.out.jpa.entity.OrderEntity;
import com.plaza.infrastructure.out.jpa.entity.enums.OrderStatusI;
import com.plaza.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.plaza.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.plaza.infrastructure.out.jpa.repository.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderJpaAdapterTest {

    @Mock
    private IOrderDishRepository orderDishRepository;

    @Mock
    private IOrderDishEntityMapper orderDishEntityMapper;


    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderEntityMapper orderEntityMapper;

    @InjectMocks
    private OrderJpaAdapter orderJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrder_ShouldConvertAndSaveOrderEntityProperly() {
        // Arrange
        OrderModel orderModel = new OrderModel();
        OrderEntity orderEntity = mock(OrderEntity.class);
        List<OrderDishEntity> orderDishEntities = new ArrayList<>();
        OrderDishEntity dishEntity = new OrderDishEntity();
        orderDishEntities.add(dishEntity);
        when(orderEntity.getOrderDishes()).thenReturn(orderDishEntities);

        when(orderEntityMapper.toEntity(orderModel)).thenReturn(orderEntity);

        // Act
        orderJpaAdapter.saveOrder(orderModel);

        // Assert
        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(orderRepository, times(1)).save(captor.capture());
        OrderEntity capturedOrder = captor.getValue();

        verify(orderEntity, times(1)).addOrderDish(dishEntity);
    }

    @Test
    void hasPendingOrders_ShouldReturnTrue_WhenPendingOrdersExist() {
        // Arrange
        Integer clientId = 1;
        List<OrderStatus> statusList = List.of(OrderStatus.PENDIENTE, OrderStatus.EN_PREPARACION);
        List<OrderStatusI> entityStatusList = List.of(
                OrderStatusI.PENDIENTE,
                OrderStatusI.EN_PREPARACION
        );

        when(orderEntityMapper.toOrderStatus(statusList)).thenReturn(entityStatusList);
        when(orderRepository.countAllByIdClientAndStatusIn(clientId, entityStatusList)).thenReturn(2L);

        // Act
        Boolean result = orderJpaAdapter.hasPendingOrders(clientId, statusList);

        // Assert
        assertTrue(result, "Should return true when pending orders exist");
        verify(orderEntityMapper, times(1)).toOrderStatus(statusList);
        verify(orderRepository, times(1)).countAllByIdClientAndStatusIn(clientId, entityStatusList);
    }

    @Test
    void hasPendingOrders_ShouldReturnFalse_WhenNoPendingOrdersExist() {
        // Arrange
        Integer clientId = 2;
        List<OrderStatus> statusList = List.of(OrderStatus.PENDIENTE, OrderStatus.EN_PREPARACION);
        List<OrderStatusI> entityStatusList = List.of(
                OrderStatusI.PENDIENTE,
                OrderStatusI.EN_PREPARACION
        );

        when(orderEntityMapper.toOrderStatus(statusList)).thenReturn(entityStatusList);
        when(orderRepository.countAllByIdClientAndStatusIn(clientId, entityStatusList)).thenReturn(0L);

        // Act
        Boolean result = orderJpaAdapter.hasPendingOrders(clientId, statusList);

        // Assert
        assertTrue(!result, "Should return false when no pending orders exist");
        verify(orderEntityMapper, times(1)).toOrderStatus(statusList);
        verify(orderRepository, times(1)).countAllByIdClientAndStatusIn(clientId, entityStatusList);
    }

    @Test
    void getOrders_ShouldReturnOrders_WhenStatusFilterIsNull() {
        // Arrange
        int page = 0;
        int size = 10;
        String statusFilter = null;
        Integer restaurantNit = 12345;

        List<OrderEntity> orderEntities = List.of(mock(OrderEntity.class));
        List<OrderDishEntity> orderDishEntities = List.of(mock(OrderDishEntity.class));
        List<OrderModel> orderModels = List.of(mock(OrderModel.class));
        List<OrderDishModel> orderDishModels = List.of(mock(OrderDishModel.class));

        when(orderRepository.findByRestaurant_Nit(restaurantNit)).thenReturn(orderEntities);
        when(orderEntityMapper.toModelList(orderEntities)).thenReturn(orderModels);
        when(orderDishEntityMapper.toModelList(orderDishEntities)).thenReturn(orderDishModels);

        // Act
        Page<OrderModel> orders = orderJpaAdapter.getOrders(page, size, statusFilter, restaurantNit);

        // Assert
        assertTrue(orders.getContent().containsAll(orderModels));
        verify(orderRepository).findByRestaurant_Nit(restaurantNit);
        verify(orderEntityMapper).toModelList(orderEntities);
    }

    @Test
    void getOrders_ShouldReturnOrders_WhenStatusFilterIsProvided() {
        // Arrange
        int page = 0;
        int size = 10;
        String statusFilter = "PENDIENTE";
        Integer restaurantNit = 12345;

        Pageable pageable = PageRequest.of(page, size, Sort.by("status").ascending());
        List<OrderEntity> orderEntities = List.of(mock(OrderEntity.class));
        Page<OrderEntity> pagedOrderEntities = new PageImpl<>(orderEntities, pageable, 1);
        List<OrderModel> orderModels = List.of(mock(OrderModel.class));
        List<OrderDishEntity> orderDishEntities = List.of(mock(OrderDishEntity.class));
        List<OrderDishModel> orderDishModels = List.of(mock(OrderDishModel.class));

        when(orderRepository.findByStatusAndRestaurant_Nit(OrderStatusI.valueOf(statusFilter), restaurantNit, pageable))
                .thenReturn(pagedOrderEntities);
        when(orderEntityMapper.toModelList(orderEntities)).thenReturn(orderModels);
        when(orderDishEntityMapper.toModelList(orderDishEntities)).thenReturn(orderDishModels);
        when(orderRepository.countAllByStatusAndRestaurantNit(OrderStatusI.valueOf(statusFilter), restaurantNit)).thenReturn(1L);

        // Act
        Page<OrderModel> orders = orderJpaAdapter.getOrders(page, size, statusFilter, restaurantNit);

        // Assert
        assertTrue(orders.getContent().containsAll(orderModels));
        verify(orderRepository).findByStatusAndRestaurant_Nit(OrderStatusI.valueOf(statusFilter), restaurantNit, pageable);
        verify(orderEntityMapper).toModelList(orderEntities);
    }

    @Test
    void findById_ShouldReturnOrderModel_WhenOrderExists() {
        // Arrange
        Integer orderId = 1;
        OrderEntity orderEntity = mock(OrderEntity.class); // Simula el entity para retornar desde el repositorio
        OrderModel expectedOrderModel = mock(OrderModel.class); // Simula el modelo esperado después del mapeo

        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(orderEntity));
        when(orderEntityMapper.toModel(orderEntity)).thenReturn(expectedOrderModel);

        // Act
        OrderModel result = orderJpaAdapter.findById(orderId);

        // Assert
        assertTrue(result == expectedOrderModel, "Should return the mapped OrderModel when found");
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderEntityMapper, times(1)).toModel(orderEntity);
    }

    @Test
    void findById_ShouldReturnNull_WhenOrderDoesNotExist() {
        // Arrange
        Integer orderId = 1;
        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.empty());

        // Act
        OrderModel result = orderJpaAdapter.findById(orderId);

        // Assert
        assertTrue(result == null, "Should return null when order does not exist");
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderEntityMapper, never()).toModel(any());
    }

    @Test
    void updateOrder_ShouldSaveMappedOrderEntity() {
        // Arrange
        OrderModel orderModel = mock(OrderModel.class); // Simula el OrderModel de entrada
        OrderEntity orderEntity = mock(OrderEntity.class); // Simula la entidad mapeada para ser guardada

        when(orderEntityMapper.toEntity(orderModel)).thenReturn(orderEntity); // Simula el mapeo de OrderModel a OrderEntity

        // Act
        orderJpaAdapter.updateOrder(orderModel);

        // Assert
        verify(orderRepository, times(1)).save(orderEntity); // Verifica que se haya llamado al método save con la entidad mapeada
        verify(orderEntityMapper, times(1)).toEntity(orderModel); // Verifica que se haya llamado al mapper para convertir el modelo
    }

    @Test
    void deliveyOrder_ShouldSaveMappedOrderEntity() {
        // Arrange
        OrderModel orderModel = mock(OrderModel.class); // Simulate the OrderModel to be passed as input.
        OrderEntity orderEntity = mock(OrderEntity.class); // Simulate the mapped OrderEntity to be saved.

        when(orderEntityMapper.toEntity(orderModel)).thenReturn(orderEntity); // Mock the mapping.

        // Act
        orderJpaAdapter.deliveyOrder(orderModel);

        // Assert
        verify(orderRepository, times(1)).save(orderEntity); // Verify that save is called with the correct entity.
        verify(orderEntityMapper, times(1)).toEntity(orderModel); // Verify the mapping process.
    }

    @Test
    void deliveyOrder_ShouldNotInteractWithOtherRepositoryMethods() {
        // Arrange
        OrderModel orderModel = mock(OrderModel.class);
        OrderEntity orderEntity = mock(OrderEntity.class);

        when(orderEntityMapper.toEntity(orderModel)).thenReturn(orderEntity);

        // Act
        orderJpaAdapter.deliveyOrder(orderModel);

        // Assert
        verify(orderRepository, times(1)).save(orderEntity);
        verifyNoMoreInteractions(orderRepository); // Verify no other repository method was called.
    }

    @Test
    void deleteOrder_ShouldDeleteOrder_WhenOrderIdIsProvided() {
        // Arrange
        Integer orderId = 1;

        // Act
        orderJpaAdapter.deleteOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }


}