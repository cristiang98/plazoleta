package com.plaza.domain.usecase;

import com.plaza.domain.api.IOrderServicePort;
import com.plaza.domain.dto.*;
import com.plaza.domain.exception.DishNotRestaurantException;
import com.plaza.domain.exception.StatusException;
import com.plaza.domain.exception.UserWithoutPermission;
import com.plaza.domain.model.OrderDishModel;
import com.plaza.domain.model.OrderModel;
import com.plaza.domain.model.RestaurantEmployeeModel;
import com.plaza.domain.model.RestaurantModel;
import com.plaza.domain.model.enums.OrderStatus;
import com.plaza.domain.spi.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IJwtHanlderPort jwtHanlderPort;
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IUserFeignClientPort userFeignClientPort;
    private final IPinUserFeignPort pinUserFeignClient;
    private final ITraceabilityFeignClientPort traceabilityFeignClientPort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IJwtHanlderPort jwtHanlderPort, IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort, IUserFeignClientPort userFeignClientPort, IPinUserFeignPort pinUserFeignClient1, ITraceabilityFeignClientPort traceabilityFeignClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.jwtHanlderPort = jwtHanlderPort;
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.userFeignClientPort = userFeignClientPort;
        this.pinUserFeignClient = pinUserFeignClient1;
        this.traceabilityFeignClientPort = traceabilityFeignClientPort;
    }

    @Override
    public void saveOrder(OrderModel orderModel, String token) {

        Integer idClientCurrent = jwtHanlderPort.extractDni(token);

        RestaurantModel restaurantModel = restaurantPersistencePort.findById(orderModel.getRestaurant().getNit());

        for(OrderDishModel orderDishModel : orderModel.getOrderDishes()){
            if(!Objects.equals(dishPersistencePort.findById(orderDishModel
                    .getDish().getId()).getRestaurant().getNit(), restaurantModel.getNit())){
                throw new DishNotRestaurantException("El plato no pertenece al restaurante");
            }

        }

        List<OrderStatus> statusList = List.of(OrderStatus.PENDIENTE, OrderStatus.EN_PREPARACION, OrderStatus.LISTO);

        if (Boolean.TRUE.equals(orderPersistencePort.hasPendingOrders(idClientCurrent, statusList))) {
            throw new IllegalArgumentException("Ya tienes una orden pendiente");
        }

        orderModel.setIdClient(idClientCurrent);
        orderModel.setDate(LocalDateTime.now());
        orderModel.setStatus(OrderStatus.PENDIENTE);
        orderPersistencePort.saveOrder(orderModel);
        //se crea el traceability de la orden e informacion del empleado para el cliente
        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();
        UserResponseDto client = userFeignClientPort.findByDni(idClientCurrent);

        traceabilityRequestDto.setIdOrder(orderModel.getId());
        traceabilityRequestDto.setIdClient(client.getDni());
        traceabilityRequestDto.setEmailClient(client.getEmail());
        traceabilityRequestDto.setStatusCurrent(orderModel.getStatus().toString());
        traceabilityRequestDto.setDateStart(orderModel.getDate());
        traceabilityFeignClientPort.saveTraceability(traceabilityRequestDto);


    }

    @Override
    public Page<OrderModel> getOrders(int page, int size, String statusFilter, String token) {

        Integer idClientCurrent = jwtHanlderPort.extractDni(token);
        UserResponseDto userResponseDto = userFeignClientPort.findByDni(idClientCurrent);
        RestaurantEmployeeModel restaurantEmployeeModel = restaurantEmployeePersistencePort.findEmployeeRestaurant(userResponseDto.getDni());

        return orderPersistencePort.getOrders(page, size, statusFilter, restaurantEmployeeModel.getRestaurantNit());
    }

    @Override
    public void updateOrder(OrderModel orderModel, String token) {

        Integer idEmployee = jwtHanlderPort.extractDni(token);

        Integer idRestaurant = restaurantEmployeePersistencePort.findEmployeeRestaurant(idEmployee).getRestaurantNit();
        RestaurantModel restaurantModel = restaurantPersistencePort.findById(idRestaurant);

        OrderModel orderModelCurrent = orderPersistencePort.findById(orderModel.getId());
        if(!Objects.equals(orderModelCurrent.getRestaurant().getNit(), restaurantModel.getNit())){
            throw new UserWithoutPermission("No puedes permiso para modificar una orden de otro restaurante");
        }

        orderModelCurrent.setIdEmployee(idEmployee);
        orderModelCurrent.setStatus(orderModel.getStatus());
        orderPersistencePort.updateOrder(orderModelCurrent);

        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();

        Integer idClientCurrent = orderModelCurrent.getIdClient();

        UserResponseDto client = userFeignClientPort.findByDni(idClientCurrent);

        traceabilityRequestDto.setIdOrder(orderModelCurrent.getId());
        traceabilityRequestDto.setIdClient(client.getDni());
        traceabilityRequestDto.setEmailClient(client.getEmail());
        traceabilityRequestDto.setIdEmployee(idEmployee);
        traceabilityRequestDto.setEmailEmployee(jwtHanlderPort.extractUsername(token));
        traceabilityRequestDto.setStatusCurrent(orderModelCurrent.getStatus().toString());
        traceabilityRequestDto.setDateStart(orderModelCurrent.getDate());
        traceabilityFeignClientPort.saveTraceability(traceabilityRequestDto);

    }

    @Override
    public void readyOrder(OrderModel orderModel, String token) {
        Integer idEmployee = jwtHanlderPort.extractDni(token);

        Integer idRestaurant = restaurantEmployeePersistencePort.findEmployeeRestaurant(idEmployee).getRestaurantNit();
        RestaurantModel restaurantModel = restaurantPersistencePort.findById(idRestaurant);
        OrderModel orderModelCurrent = orderPersistencePort.findById(orderModel.getId());
        if(!Objects.equals(orderModelCurrent.getRestaurant().getNit(), restaurantModel.getNit())){
            throw new UserWithoutPermission("No TIENES permiso para modificar una orden de otro restaurante");
        }

        if(orderModelCurrent.getStatus() != OrderStatus.EN_PREPARACION){
            throw new StatusException("La orden no se encuentra en estado de preparación");
        }

        if (orderModel.getStatus() != OrderStatus.LISTO){
            throw new StatusException("El estado de la orden no es LISTO");
        }

        orderModelCurrent.setIdEmployee(idEmployee);
        orderModelCurrent.setStatus(OrderStatus.LISTO);

        String phone = userFeignClientPort.findByDni(orderModelCurrent.getIdClient()).getPhone();

        PinUserRequestDto pinUserRequestDto = new PinUserRequestDto();
        pinUserRequestDto.setOrderId(orderModelCurrent.getId());
        pinUserRequestDto.setUserId(orderModelCurrent.getIdClient());

        pinUserFeignClient.savePinUser(pinUserRequestDto,phone);
        orderPersistencePort.updateOrder(orderModelCurrent);

        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();

        Integer idClientCurrent = orderModelCurrent.getIdClient();

        UserResponseDto client = userFeignClientPort.findByDni(idClientCurrent);

        traceabilityRequestDto.setIdOrder(orderModelCurrent.getId());
        traceabilityRequestDto.setIdClient(client.getDni());
        traceabilityRequestDto.setEmailClient(client.getEmail());
        traceabilityRequestDto.setIdEmployee(idEmployee);
        traceabilityRequestDto.setEmailEmployee(jwtHanlderPort.extractUsername(token));
        traceabilityRequestDto.setStatusCurrent(orderModelCurrent.getStatus().toString());
        traceabilityRequestDto.setDateStart(orderModelCurrent.getDate());
        traceabilityFeignClientPort.saveTraceability(traceabilityRequestDto);


    }

    @Override
    public void deliveryOrder(Integer pin, String token) {
        PinUserResponseDto pinUserResponseDto = pinUserFeignClient.findPinUser(pin);

        Integer idClient = pinUserResponseDto.getUserId();
        OrderModel orderModel = orderPersistencePort.findById(pinUserResponseDto.getOrderId());

        if(!Objects.equals(idClient, orderModel.getIdClient())){
            throw new UserWithoutPermission("No eres el dueño de la orden");
        }

        if(orderModel.getStatus() != OrderStatus.LISTO){
            throw new StatusException("La orden no se encuentra en estado LISTO");
        }

        orderModel.setStatus(OrderStatus.ENTREGADO);
        orderPersistencePort.deliveyOrder(orderModel);

        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();

        Integer idEmployee = jwtHanlderPort.extractDni(token);
        UserResponseDto client = userFeignClientPort.findByDni(idClient);

        traceabilityRequestDto.setIdOrder(orderModel.getId());
        traceabilityRequestDto.setIdClient(client.getDni());
        traceabilityRequestDto.setEmailClient(client.getEmail());
        traceabilityRequestDto.setIdEmployee(idEmployee);
        traceabilityRequestDto.setEmailEmployee(jwtHanlderPort.extractUsername(token));
        traceabilityRequestDto.setStatusCurrent(orderModel.getStatus().toString());
        traceabilityRequestDto.setDateStart(orderModel.getDate());
        traceabilityRequestDto.setDateEnd(LocalDateTime.now());
        traceabilityFeignClientPort.saveTraceability(traceabilityRequestDto);

    }

    @Override
    public void deleteOrder(Integer idOrder, String token) {
        Integer idClient = jwtHanlderPort.extractDni(token);
        OrderModel orderModel = orderPersistencePort.findById(idOrder);

        if(!Objects.equals(idClient, orderModel.getIdClient())){
            throw new UserWithoutPermission("No eres el dueño de la orden");
        }

        if(orderModel.getStatus() != OrderStatus.PENDIENTE){
            throw new StatusException("Lo sentimos, tu pedido ya está en proceso de preparación y no puede cancelarse");
        }

        orderModel.setStatus(OrderStatus.CANCELADO);

        orderPersistencePort.deleteOrder(idOrder);

        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();

        UserResponseDto client = userFeignClientPort.findByDni(idClient);

        traceabilityRequestDto.setIdOrder(orderModel.getId());
        traceabilityRequestDto.setIdClient(client.getDni());
        traceabilityRequestDto.setEmailClient(client.getEmail());
        traceabilityRequestDto.setStatusCurrent(orderModel.getStatus().toString());
        traceabilityRequestDto.setDateStart(orderModel.getDate());
        traceabilityRequestDto.setDateEnd(LocalDateTime.now());
        traceabilityFeignClientPort.saveTraceability(traceabilityRequestDto);
    }

    @Override
    public RestaurantEfficiencyResponseDto getEfficiency(String token) {

        Integer dniOwner = jwtHanlderPort.extractDni(token);
        RestaurantModel restaurantModel = restaurantPersistencePort.findByIdOwner(dniOwner);
        List<OrderModel> orderModelList = orderPersistencePort.findAllByRestaurantNit(restaurantModel.getNit());
        return traceabilityFeignClientPort.getEfficiency(orderModelList);
    }
}
