package com.plaza.domain.usecase;

import com.plaza.domain.dto.*;
import com.plaza.domain.exception.DishNotRestaurantException;
import com.plaza.domain.exception.StatusException;
import com.plaza.domain.exception.UserWithoutPermission;
import com.plaza.domain.model.*;
import com.plaza.domain.model.enums.OrderStatus;
import com.plaza.domain.spi.*;
import com.plaza.infrastructure.out.jpa.feign.IPinUserFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {



    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IJwtHanlderPort jwtHanlderPort;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;

    @Mock
    private  IUserFeignClientPort userFeignClientPort;

    @Mock
    private IPinUserFeignPort pinUserFeignClient;

    @Mock
    private ITraceabilityFeignClientPort traceabilityFeignClientPort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(orderPersistencePort, jwtHanlderPort, dishPersistencePort,
                restaurantPersistencePort, restaurantEmployeePersistencePort, userFeignClientPort,
                pinUserFeignClient,traceabilityFeignClientPort);
    }
    @Test
    void testSaveOrderSuccessfully() {
        // GIVEN
        String token = "test-token";
        Integer idClientCurrent = 1;
        Integer restaurantNit = 123;
        Integer dishId = 456;

        LocalDateTime now = LocalDateTime.now();

        // Mock del cliente
        UserResponseDto client = new UserResponseDto();
        client.setDni(idClientCurrent);
        client.setEmail("client@test.com");

        // Mock del restaurante
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit(restaurantNit);

        // Mock del platillo
        DishModel dishModel = new DishModel();
        dishModel.setId(dishId);
        dishModel.setRestaurant(restaurantModel);

        // Mock del OrderDishModel
        OrderDishModel orderDishModel = new OrderDishModel();
        orderDishModel.setDish(dishModel);

        // Mock del pedido
        OrderModel orderModel = new OrderModel();
        orderModel.setRestaurant(restaurantModel);
        orderModel.setOrderDishes(Collections.singletonList(orderDishModel));

        // Mock del TraceabilityRequestDto
        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();

        // Lista de status pendientes
        List<OrderStatus> statusList = List.of(OrderStatus.PENDIENTE, OrderStatus.EN_PREPARACION, OrderStatus.LISTO);

        // Configuración de mocks
        when(jwtHanlderPort.extractDni(token)).thenReturn(idClientCurrent);
        when(restaurantPersistencePort.findById(restaurantNit)).thenReturn(restaurantModel);
        when(dishPersistencePort.findById(dishId)).thenReturn(dishModel);
        when(orderPersistencePort.hasPendingOrders(idClientCurrent, statusList)).thenReturn(false);
        when(userFeignClientPort.findByDni(idClientCurrent)).thenReturn(client);

        // WHEN
        orderUseCase.saveOrder(orderModel, token);

        // THEN
        verify(jwtHanlderPort, times(1)).extractDni(token);
        verify(restaurantPersistencePort, times(1)).findById(restaurantNit);
        verify(dishPersistencePort, times(1)).findById(dishId);
        verify(orderPersistencePort, times(1)).hasPendingOrders(idClientCurrent, statusList);
        verify(orderPersistencePort, times(1)).saveOrder(orderModel);
        verify(userFeignClientPort, times(1)).findByDni(idClientCurrent);
        verify(traceabilityFeignClientPort, times(1)).saveTraceability(any(TraceabilityRequestDto.class));

        // Asegura que el pedido se creó correctamente
        assert Objects.equals(orderModel.getIdClient(), idClientCurrent);
        assert orderModel.getStatus() == OrderStatus.PENDIENTE;
        assert orderModel.getDate() != null;
    }

    @Test
    void testSaveOrderThrowsDishNotRestaurantException() {
        // Arrange
        OrderModel orderModel = mock(OrderModel.class);
        RestaurantModel restaurantModel = mock(RestaurantModel.class);
        OrderDishModel invalidDish = mock(OrderDishModel.class);
        DishModel invalidDishModel = mock(DishModel.class);
        RestaurantModel invalidRestaurantModel = mock(RestaurantModel.class); // Restaurante inválido

        // Configurar los mocks
        when(orderModel.getRestaurant()).thenReturn(restaurantModel); // Restaurante del pedido
        when(orderModel.getOrderDishes()).thenReturn(List.of(invalidDish)); // Lista de platos en el pedido
        when(invalidDish.getDish()).thenReturn(invalidDishModel); // Plato inválido asociado al pedido

        // Configuración del plato inválido
        when(invalidDishModel.getRestaurant()).thenReturn(invalidRestaurantModel); // Restaurante asociado al plato inválido
        when(invalidRestaurantModel.getNit()).thenReturn(2); // NIT del restaurante del plato inválido

        // Configuración del restaurante del pedido
        when(restaurantModel.getNit()).thenReturn(1); // NIT del restaurante del pedido

        // Otros mocks relevantes
        when(jwtHanlderPort.extractDni(anyString())).thenReturn(1);
        when(restaurantPersistencePort.findById(anyInt())).thenReturn(restaurantModel);
        when(dishPersistencePort.findById(anyInt())).thenReturn(invalidDishModel);

        // Act & Assert
        assertThrows(DishNotRestaurantException.class, () -> orderUseCase.saveOrder(orderModel, "token"));
    }

    @Test
    void testSaveOrderThrowsIllegalArgumentExceptionForPendingOrders() {
        // Arrange
        OrderModel orderModel = mock(OrderModel.class);
        RestaurantModel restaurantModel = mock(RestaurantModel.class);
        when(orderModel.getRestaurant()).thenReturn(restaurantModel);
        when(jwtHanlderPort.extractDni(anyString())).thenReturn(1);
        when(restaurantPersistencePort.findById(anyInt())).thenReturn(restaurantModel);

        // Client has pending orders
        when(orderPersistencePort.hasPendingOrders(anyInt(), anyList())).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> orderUseCase.saveOrder(orderModel, "token"));
    }

    @Test
    void testGetOrdersSuccessfully() {
        // Arrange
        String token = "token";
        RestaurantEmployeeModel employeeModel = mock(RestaurantEmployeeModel.class);
        UserResponseDto userResponse = mock(UserResponseDto.class);
        Page<OrderModel> expectedOrders = mock(Page.class);

        when(jwtHanlderPort.extractDni(token)).thenReturn(1);
        when(userFeignClientPort.findByDni(1)).thenReturn(userResponse);
        when(userResponse.getDni()).thenReturn(1);
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(1)).thenReturn(employeeModel);
        when(employeeModel.getRestaurantNit()).thenReturn(123);
        when(orderPersistencePort.getOrders(0, 10, "PENDIENTE", 123)).thenReturn(expectedOrders);

        // Act
        Page<OrderModel> result = orderUseCase.getOrders(0, 10, "PENDIENTE", token);

        // Assert
        assertSame(expectedOrders, result);
        verify(orderPersistencePort, times(1)).getOrders(0, 10, "PENDIENTE", 123);
    }

    @Test
    void testGetOrdersReturnsNoResults() {
        // Arrange
        String token = "token";
        RestaurantEmployeeModel employeeModel = mock(RestaurantEmployeeModel.class);
        UserResponseDto userResponse = mock(UserResponseDto.class);
        Page<OrderModel> emptyPage = Page.empty();

        when(jwtHanlderPort.extractDni(token)).thenReturn(1);
        when(userFeignClientPort.findByDni(1)).thenReturn(userResponse);
        when(userResponse.getDni()).thenReturn(1);
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(1)).thenReturn(employeeModel);
        when(employeeModel.getRestaurantNit()).thenReturn(123);
        when(orderPersistencePort.getOrders(0, 10, "PENDIENTE", 123)).thenReturn(emptyPage);

        // Act
        Page<OrderModel> result = orderUseCase.getOrders(0, 10, "PENDIENTE", token);

        // Assert
        assertTrue(result.isEmpty());
        verify(orderPersistencePort, times(1)).getOrders(0, 10, "PENDIENTE", 123);
    }

    @Test
    void testGetOrdersThrowsException() {
        // Arrange
        String token = "invalid_token";

        when(jwtHanlderPort.extractDni(token)).thenThrow(new IllegalArgumentException("Invalid token"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> orderUseCase.getOrders(0, 10, "PENDIENTE", token));
        verifyNoInteractions(userFeignClientPort, restaurantEmployeePersistencePort, orderPersistencePort);
    }

    @Test
    void testUpdateOrderSuccessfully() {
        // GIVEN
        String token = "test-token";
        Integer idEmployee = 1;
        Integer restaurantNit = 123;
        Integer orderId = 456;
        Integer idClient = 789;

        // Mock del empleado y restaurante
        RestaurantEmployeeModel restaurantEmployee = new RestaurantEmployeeModel();
        restaurantEmployee.setRestaurantNit(restaurantNit);

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit(restaurantNit);

        // Mock del pedido actual
        OrderModel orderModelCurrent = new OrderModel();
        orderModelCurrent.setId(orderId);
        orderModelCurrent.setRestaurant(restaurantModel);
        orderModelCurrent.setIdClient(idClient);
        orderModelCurrent.setDate(LocalDateTime.now());

        // Mock del pedido nuevo
        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setStatus(OrderStatus.EN_PREPARACION);

        // Mock del cliente
        UserResponseDto client = new UserResponseDto();
        client.setDni(idClient);
        client.setEmail("client@test.com");

        // Configuración de los mocks
        when(jwtHanlderPort.extractDni(token)).thenReturn(idEmployee);
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(idEmployee)).thenReturn(restaurantEmployee);
        when(restaurantPersistencePort.findById(restaurantNit)).thenReturn(restaurantModel);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModelCurrent);
        when(userFeignClientPort.findByDni(idClient)).thenReturn(client);

        // WHEN
        orderUseCase.updateOrder(orderModel, token);

        // THEN
        verify(jwtHanlderPort, times(1)).extractDni(token);
        verify(restaurantEmployeePersistencePort, times(1)).findEmployeeRestaurant(idEmployee);
        verify(restaurantPersistencePort, times(1)).findById(restaurantNit);
        verify(orderPersistencePort, times(1)).findById(orderId);
        verify(orderPersistencePort, times(1)).updateOrder(orderModelCurrent);
        verify(traceabilityFeignClientPort, times(1)).saveTraceability(any(TraceabilityRequestDto.class));

        assert orderModelCurrent.getStatus() == OrderStatus.EN_PREPARACION; // Verificar que el estado se haya actualizado
        assert orderModelCurrent.getIdEmployee().equals(idEmployee);        // Verificar que se haya asignado el empleado
    }

    @Test
    void testUpdateOrderThrowsUserWithoutPermissionException() {
        // GIVEN
        String token = "test-token";
        Integer idEmployee = 1;
        Integer restaurantNit = 123;
        Integer anotherRestaurantNit = 789;
        Integer orderId = 456;

        // Mock del empleado y restaurante
        RestaurantEmployeeModel restaurantEmployee = new RestaurantEmployeeModel();
        restaurantEmployee.setRestaurantNit(restaurantNit);

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit(restaurantNit);

        RestaurantModel anotherRestaurantModel = new RestaurantModel();
        anotherRestaurantModel.setNit(anotherRestaurantNit);

        // Mock del pedido actual
        OrderModel orderModelCurrent = new OrderModel();
        orderModelCurrent.setId(orderId);
        orderModelCurrent.setRestaurant(anotherRestaurantModel); // El pedido pertenece a otro restaurante

        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);

        // Configuración de los mocks
        when(jwtHanlderPort.extractDni(token)).thenReturn(idEmployee);
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(idEmployee)).thenReturn(restaurantEmployee);
        when(restaurantPersistencePort.findById(restaurantNit)).thenReturn(restaurantModel);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModelCurrent);

        // WHEN & THEN
        assertThrows(UserWithoutPermission.class, () -> orderUseCase.updateOrder(orderModel, token));

        verify(jwtHanlderPort, times(1)).extractDni(token);
        verify(restaurantEmployeePersistencePort, times(1)).findEmployeeRestaurant(idEmployee);
        verify(restaurantPersistencePort, times(1)).findById(restaurantNit);
        verify(orderPersistencePort, times(1)).findById(orderId);
        verify(orderPersistencePort, never()).updateOrder(any());
        verify(traceabilityFeignClientPort, never()).saveTraceability(any());
    }

    @Test
    void testUpdateOrderClientNotFound() {
        // GIVEN
        String token = "test-token";
        Integer idEmployee = 1;
        Integer restaurantNit = 123;
        Integer orderId = 456;
        Integer idClient = 789;

        // Mock del empleado y restaurante
        RestaurantEmployeeModel restaurantEmployee = new RestaurantEmployeeModel();
        restaurantEmployee.setRestaurantNit(restaurantNit);

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit(restaurantNit);

        // Mock del pedido actual
        OrderModel orderModelCurrent = new OrderModel();
        orderModelCurrent.setId(orderId);
        orderModelCurrent.setRestaurant(restaurantModel);
        orderModelCurrent.setIdClient(idClient);

        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);

        // Configuración de los mocks
        when(jwtHanlderPort.extractDni(token)).thenReturn(idEmployee);
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(idEmployee)).thenReturn(restaurantEmployee);
        when(restaurantPersistencePort.findById(restaurantNit)).thenReturn(restaurantModel);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModelCurrent);
        when(userFeignClientPort.findByDni(idClient)).thenReturn(null); // Cliente no encontrado

        // WHEN & THEN
        assertThrows(NullPointerException.class, () -> orderUseCase.updateOrder(orderModel, token));
    }

    @Test
    void testReadyOrderSuccessfully() {
        // Arrange
        String token = "valid_token";
        Integer employeeId = 1;
        Integer restaurantNit = 123;
        OrderModel orderModel = mock(OrderModel.class);
        OrderModel existingOrder = mock(OrderModel.class);
        RestaurantModel restaurantModel = mock(RestaurantModel.class);
        UserResponseDto userResponseDto = mock(UserResponseDto.class);

        when(jwtHanlderPort.extractDni(token)).thenReturn(employeeId);
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(employeeId)).thenReturn(mock(RestaurantEmployeeModel.class));
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(employeeId).getRestaurantNit()).thenReturn(restaurantNit);
        when(restaurantPersistencePort.findById(restaurantNit)).thenReturn(restaurantModel);
        when(restaurantModel.getNit()).thenReturn(restaurantNit);
        when(orderPersistencePort.findById(orderModel.getId())).thenReturn(existingOrder);
        when(existingOrder.getRestaurant()).thenReturn(restaurantModel);
        when(existingOrder.getStatus()).thenReturn(OrderStatus.EN_PREPARACION);
        when(orderModel.getStatus()).thenReturn(OrderStatus.LISTO);
        when(userFeignClientPort.findByDni(anyInt())).thenReturn(userResponseDto);
        when(userResponseDto.getPhone()).thenReturn("123456789");

        // Act
        orderUseCase.readyOrder(orderModel, token);

        // Assert
        verify(existingOrder, times(1)).setIdEmployee(employeeId);
        verify(existingOrder, times(1)).setStatus(OrderStatus.LISTO);
        verify(orderPersistencePort, times(1)).updateOrder(existingOrder);
        verify(pinUserFeignClient, times(1)).savePinUser(any(PinUserRequestDto.class), anyString());
    }

    @Test
    void testReadyOrderThrowsUserWithoutPermission() {
        // Arrange
        String token = "valid_token";
        Integer employeeId = 1;
        Integer restaurantNit1 = 123;
        Integer restaurantNit2 = 456;
        OrderModel orderModel = mock(OrderModel.class);
        OrderModel existingOrder = mock(OrderModel.class);
        RestaurantModel restaurantModel = mock(RestaurantModel.class);
        RestaurantModel differentRestaurantModel = mock(RestaurantModel.class);

        when(jwtHanlderPort.extractDni(token)).thenReturn(employeeId);
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(employeeId)).thenReturn(mock(RestaurantEmployeeModel.class));
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(employeeId).getRestaurantNit()).thenReturn(restaurantNit1);
        when(restaurantPersistencePort.findById(restaurantNit1)).thenReturn(restaurantModel);
        when(orderPersistencePort.findById(orderModel.getId())).thenReturn(existingOrder);
        when(existingOrder.getRestaurant()).thenReturn(differentRestaurantModel);
        when(differentRestaurantModel.getNit()).thenReturn(restaurantNit2);

        // Act & Assert
        assertThrows(UserWithoutPermission.class, () -> orderUseCase.readyOrder(orderModel, token));
    }

    @Test
    void testReadyOrderThrowsStatusExceptionForInvalidCurrentStatus() {
        // Arrange
        String token = "valid_token";
        Integer employeeId = 1;
        Integer restaurantNit = 123;
        OrderModel orderModel = mock(OrderModel.class);
        OrderModel existingOrder = mock(OrderModel.class);
        RestaurantModel restaurantModel = mock(RestaurantModel.class);

        when(jwtHanlderPort.extractDni(token)).thenReturn(employeeId);
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(employeeId)).thenReturn(mock(RestaurantEmployeeModel.class));
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(employeeId).getRestaurantNit()).thenReturn(restaurantNit);
        when(restaurantPersistencePort.findById(restaurantNit)).thenReturn(restaurantModel);
        when(restaurantModel.getNit()).thenReturn(restaurantNit);
        when(orderPersistencePort.findById(orderModel.getId())).thenReturn(existingOrder);
        when(existingOrder.getRestaurant()).thenReturn(restaurantModel);
        when(existingOrder.getStatus()).thenReturn(OrderStatus.PENDIENTE); // Invalid status

        // Act & Assert
        assertThrows(StatusException.class, () -> orderUseCase.readyOrder(orderModel, token));
    }

    @Test
    void testReadyOrderThrowsStatusExceptionForInvalidUpdatedStatus() {
        // Arrange
        String token = "valid_token";
        Integer employeeId = 1;
        Integer restaurantNit = 123;
        OrderModel orderModel = mock(OrderModel.class);
        OrderModel existingOrder = mock(OrderModel.class);
        RestaurantModel restaurantModel = mock(RestaurantModel.class);

        when(jwtHanlderPort.extractDni(token)).thenReturn(employeeId);
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(employeeId)).thenReturn(mock(RestaurantEmployeeModel.class));
        when(restaurantEmployeePersistencePort.findEmployeeRestaurant(employeeId).getRestaurantNit()).thenReturn(restaurantNit);
        when(restaurantPersistencePort.findById(restaurantNit)).thenReturn(restaurantModel);
        when(restaurantModel.getNit()).thenReturn(restaurantNit);
        when(orderPersistencePort.findById(orderModel.getId())).thenReturn(existingOrder);
        when(existingOrder.getRestaurant()).thenReturn(restaurantModel);
        when(existingOrder.getStatus()).thenReturn(OrderStatus.EN_PREPARACION);
        when(orderModel.getStatus()).thenReturn(OrderStatus.PENDIENTE); // Invalid updated status

        // Act & Assert
        assertThrows(StatusException.class, () -> orderUseCase.readyOrder(orderModel, token));
    }

    @Test
    void testDeliveryOrderSuccessfully() {
        // GIVEN
        String token = "test-token";
        Integer pin = 1234;
        Integer idClient = 1;
        Integer idOrder = 123;
        Integer idEmployee = 5;

        // Mock del PinUserResponseDto
        PinUserResponseDto pinUserResponseDto = new PinUserResponseDto();
        pinUserResponseDto.setUserId(idClient);
        pinUserResponseDto.setOrderId(idOrder);

        // Mock del pedido
        OrderModel orderModel = new OrderModel();
        orderModel.setId(idOrder);
        orderModel.setIdClient(idClient);
        orderModel.setStatus(OrderStatus.LISTO);
        orderModel.setDate(LocalDateTime.now());

        // Mock del cliente
        UserResponseDto client = new UserResponseDto();
        client.setDni(idClient);
        client.setEmail("client@test.com");

        // Configuración de los mocks
        when(pinUserFeignClient.findPinUser(pin)).thenReturn(pinUserResponseDto);
        when(orderPersistencePort.findById(idOrder)).thenReturn(orderModel);
        when(jwtHanlderPort.extractDni(token)).thenReturn(idEmployee);
        when(userFeignClientPort.findByDni(idClient)).thenReturn(client);

        // WHEN
        orderUseCase.deliveryOrder(pin, token);

        // THEN
        verify(pinUserFeignClient, times(1)).findPinUser(pin);
        verify(orderPersistencePort, times(1)).findById(idOrder);
        verify(orderPersistencePort, times(1)).deliveyOrder(orderModel);
        verify(jwtHanlderPort, times(1)).extractDni(token);
        verify(userFeignClientPort, times(1)).findByDni(idClient);
        verify(traceabilityFeignClientPort, times(1)).saveTraceability(any(TraceabilityRequestDto.class));

        assert orderModel.getStatus() == OrderStatus.ENTREGADO; // Verificar que el estado del pedido sea ENTREGADO
    }

    @Test
    void testDeliveryOrderThrowsUserWithoutPermission() {
        // Arrange
        String token = "valid_token";
        Integer pin = 12345;
        PinUserResponseDto pinUserResponseDto = mock(PinUserResponseDto.class);
        OrderModel orderModel = mock(OrderModel.class);

        when(pinUserFeignClient.findPinUser(pin)).thenReturn(pinUserResponseDto);
        when(pinUserResponseDto.getUserId()).thenReturn(1);
        when(orderPersistencePort.findById(pinUserResponseDto.getOrderId())).thenReturn(orderModel);
        when(orderModel.getIdClient()).thenReturn(2); // Different client id

        // Act & Assert
        assertThrows(UserWithoutPermission.class, () -> orderUseCase.deliveryOrder(pin, token));
        verify(orderPersistencePort, times(0)).deliveyOrder(any(OrderModel.class));
    }

    @Test
    void testDeliveryOrderThrowsStatusExceptionForInvalidOrderStatus() {
        // Arrange
        String token = "valid_token";
        Integer pin = 12345;
        PinUserResponseDto pinUserResponseDto = mock(PinUserResponseDto.class);
        OrderModel orderModel = mock(OrderModel.class);

        when(pinUserFeignClient.findPinUser(pin)).thenReturn(pinUserResponseDto);
        when(pinUserResponseDto.getUserId()).thenReturn(1);
        when(pinUserResponseDto.getOrderId()).thenReturn(1);
        when(orderPersistencePort.findById(1)).thenReturn(orderModel);
        when(orderModel.getIdClient()).thenReturn(1);
        when(orderModel.getStatus()).thenReturn(OrderStatus.EN_PREPARACION); // Invalid status

        // Act & Assert
        assertThrows(StatusException.class, () -> orderUseCase.deliveryOrder(pin, token));
        verify(orderPersistencePort, times(0)).deliveyOrder(orderModel);
    }

    @Test
    void testDeleteOrderSuccessfully() {
        // GIVEN
        String token = "test-token";
        Integer idClient = 1;
        Integer idOrder = 123;

        // Mock del pedido
        OrderModel orderModel = new OrderModel();
        orderModel.setId(idOrder);
        orderModel.setIdClient(idClient);
        orderModel.setStatus(OrderStatus.PENDIENTE);
        orderModel.setDate(LocalDateTime.now());

        // Mock del cliente
        UserResponseDto client = new UserResponseDto();
        client.setDni(idClient);
        client.setEmail("client@test.com");

        // Configuración de los mocks
        when(jwtHanlderPort.extractDni(token)).thenReturn(idClient);
        when(orderPersistencePort.findById(idOrder)).thenReturn(orderModel);
        when(userFeignClientPort.findByDni(idClient)).thenReturn(client);

        // WHEN
        orderUseCase.deleteOrder(idOrder, token);

        // THEN
        verify(jwtHanlderPort, times(1)).extractDni(token);
        verify(orderPersistencePort, times(1)).findById(idOrder);
        verify(orderPersistencePort, times(1)).deleteOrder(idOrder);
        verify(userFeignClientPort, times(1)).findByDni(idClient);
        verify(traceabilityFeignClientPort, times(1)).saveTraceability(any(TraceabilityRequestDto.class));

        assert orderModel.getStatus() == OrderStatus.CANCELADO; // Verificar que el estado del pedido sea CANCELADO
    }

    @Test
    void testDeleteOrderThrowsUserWithoutPermissionException() {
        // GIVEN
        String token = "test-token";
        Integer idClient = 1;
        Integer anotherIdClient = 2; // Otro cliente que no es el dueño del pedido
        Integer idOrder = 123;

        // Mock del pedido
        OrderModel orderModel = new OrderModel();
        orderModel.setId(idOrder);
        orderModel.setIdClient(anotherIdClient); // El cliente del pedido no coincide con el cliente autenticado

        // Configuración de los mocks
        when(jwtHanlderPort.extractDni(token)).thenReturn(idClient);
        when(orderPersistencePort.findById(idOrder)).thenReturn(orderModel);

        // WHEN & THEN
        assertThrows(UserWithoutPermission.class, () -> orderUseCase.deleteOrder(idOrder, token));

        verify(jwtHanlderPort, times(1)).extractDni(token);
        verify(orderPersistencePort, times(1)).findById(idOrder);
        verify(orderPersistencePort, never()).deleteOrder(any());
        verify(traceabilityFeignClientPort, never()).saveTraceability(any());
    }

    @Test
    void testDeleteOrderThrowsStatusException() {
        // GIVEN
        String token = "test-token";
        Integer idClient = 1;
        Integer idOrder = 123;

        // Mock del pedido
        OrderModel orderModel = new OrderModel();
        orderModel.setId(idOrder);
        orderModel.setIdClient(idClient);
        orderModel.setStatus(OrderStatus.EN_PREPARACION); // Estado que no permite cancelación

        // Configuración de los mocks
        when(jwtHanlderPort.extractDni(token)).thenReturn(idClient);
        when(orderPersistencePort.findById(idOrder)).thenReturn(orderModel);

        // WHEN & THEN
        assertThrows(StatusException.class, () -> orderUseCase.deleteOrder(idOrder, token));

        verify(jwtHanlderPort, times(1)).extractDni(token);
        verify(orderPersistencePort, times(1)).findById(idOrder);
        verify(orderPersistencePort, never()).deleteOrder(any());
        verify(traceabilityFeignClientPort, never()).saveTraceability(any());
    }

    @Test
    void testDeleteOrderThrowsStatusExceptionForNonPendingOrder() {
        // Arrange
        String token = "valid_token";
        Integer orderId = 1;
        Integer clientId = 101;

        OrderModel orderModel = mock(OrderModel.class);
        when(jwtHanlderPort.extractDni(token)).thenReturn(clientId);
        when(orderPersistencePort.findById(orderId)).thenReturn(orderModel);
        when(orderModel.getIdClient()).thenReturn(clientId);
        when(orderModel.getStatus()).thenReturn(OrderStatus.EN_PREPARACION);

        // Act & Assert
        assertThrows(StatusException.class, () -> orderUseCase.deleteOrder(orderId, token));
        verify(orderPersistencePort, times(0)).deleteOrder(anyInt());
    }

    @Test
    void testGetEfficiency() {
        // GIVEN
        String token = "test-token";
        Integer dniOwner = 12345;
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setNit(67890);

        OrderModel orderModel = new OrderModel();
        List<OrderModel> orderModelList = Collections.singletonList(orderModel);

        RestaurantEfficiencyResponseDto expectedResponse = new RestaurantEfficiencyResponseDto();

        // Configuramos el comportamiento de los mocks
        when(jwtHanlderPort.extractDni(token)).thenReturn(dniOwner);
        when(restaurantPersistencePort.findByIdOwner(dniOwner)).thenReturn(restaurantModel);
        when(orderPersistencePort.findAllByRestaurantNit(restaurantModel.getNit())).thenReturn(orderModelList);
        when(traceabilityFeignClientPort.getEfficiency(orderModelList)).thenReturn(expectedResponse);

        // WHEN
        RestaurantEfficiencyResponseDto actualResponse = orderUseCase.getEfficiency(token);

        // THEN
        verify(jwtHanlderPort, times(1)).extractDni(token);
        verify(restaurantPersistencePort, times(1)).findByIdOwner(dniOwner);
        verify(orderPersistencePort, times(1)).findAllByRestaurantNit(restaurantModel.getNit());
        verify(traceabilityFeignClientPort, times(1)).getEfficiency(orderModelList);

        assertEquals(expectedResponse, actualResponse);
    }
}
