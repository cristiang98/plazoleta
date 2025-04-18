package com.plaza.infrastructure.out.jpa.adapter;

import com.plaza.domain.model.OrderDishModel;
import com.plaza.domain.model.OrderModel;
import com.plaza.domain.model.enums.OrderStatus;
import com.plaza.domain.spi.IOrderPersistencePort;
import com.plaza.infrastructure.out.jpa.entity.OrderDishEntity;
import com.plaza.infrastructure.out.jpa.entity.OrderEntity;
import com.plaza.infrastructure.out.jpa.entity.enums.OrderStatusI;
import com.plaza.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.plaza.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.plaza.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.plaza.infrastructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;


    @Override
    public void saveOrder(OrderModel orderModel) {

        OrderEntity orderEntity = orderEntityMapper.toEntity(orderModel);

        List<OrderDishEntity> dishEntities = new ArrayList<>(orderEntity.getOrderDishes());
        orderEntity.getOrderDishes().clear();

        for (OrderDishEntity dish : dishEntities) {
            orderEntity.addOrderDish(dish);
        }

        orderRepository.save(orderEntity);
    }

    @Override
    public Boolean hasPendingOrders(Integer clientId, List<OrderStatus> statusList) {

        List<OrderStatusI> orderStatusIS = orderEntityMapper.toOrderStatus(statusList);

        return orderRepository.countAllByIdClientAndStatusIn(clientId, orderStatusIS) > 0;
    }

    @Override
    public Page<OrderModel> getOrders(int page, int size, String statusFilter, Integer restaurantNit) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("status").ascending());
        if (statusFilter == null) {
            List<OrderEntity> orderEntities = orderRepository.findByRestaurant_Nit(restaurantNit);// bueno
            List<OrderDishEntity> orderDishEntities = new ArrayList<>();
            for (OrderEntity orderEntity : orderEntities) {
                orderDishEntities.addAll(orderEntity.getOrderDishes());
            }

            orderEntities.forEach(orderEntity -> { // bueno
                orderEntity.setOrderDishes(null);
            });

            List<OrderModel> orderModels = orderEntityMapper.toModelList(orderEntities);//bueno
            List<OrderDishModel> orderDishModels = orderDishEntityMapper.toModelList(orderDishEntities);//bueno

            for (OrderModel orderModel : orderModels) {
                List<OrderDishModel> orderDishModels1 = new ArrayList<>();
                int cont = 0;
                for (OrderDishModel orderDishModel : orderDishModels) {
                    if (orderDishEntities.get(cont).getOrder().getId().equals(orderModel.getId())) {
                        orderDishModels1.add(orderDishModel);
                        orderDishModel.setOrder(null);
                    }
                    cont = cont + 1;
                }
                orderModel.setOrderDishes(orderDishModels1);

            }

            return new PageImpl<>(orderModels);

        } else {
            List<OrderEntity> orderEntities = orderRepository
                    .findByStatusAndRestaurant_Nit(OrderStatusI.valueOf(statusFilter), restaurantNit, pageable)
                    .toList();

            List<OrderDishEntity> orderDishEntities = new ArrayList<>();

            for (OrderEntity orderEntity : orderEntities) {
                orderDishEntities.addAll(orderEntity.getOrderDishes());
            }

            orderEntities.forEach(orderEntity -> {
                orderEntity.setOrderDishes(null); // Eliminamos las relaciones de OrderDishes
            });

            List<OrderModel> orderModels = orderEntityMapper.toModelList(orderEntities);
            List<OrderDishModel> orderDishModels = orderDishEntityMapper.toModelList(orderDishEntities);

            for (OrderModel orderModel : orderModels) {
                List<OrderDishModel> orderDishModels1 = new ArrayList<>();
                for (OrderDishModel orderDishModel : orderDishModels) {
                    int cont = 0;
                    if (orderDishEntities.get(cont).getOrder().getId().equals(orderModel.getId())) {
                        orderDishModels1.add(orderDishModel);
                        orderDishModel.setOrder(null); // Eliminamos la referencia circular
                    }
                }
                orderModel.setOrderDishes(orderDishModels1); // Agregamos los platos asociados a la orden
            }

            return new PageImpl<>(orderModels, pageable, orderRepository.countAllByStatusAndRestaurantNit(OrderStatusI.valueOf(statusFilter), restaurantNit));

        }


    }

    @Override
    public OrderModel findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderEntityMapper::toModel)
                .orElse(null);
    }

    @Override
    public void updateOrder(OrderModel orderModelCurrent) {
        orderRepository.save(orderEntityMapper.toEntity(orderModelCurrent));
    }

    @Override
    public void deliveyOrder(OrderModel orderModel) {
        orderRepository.save(orderEntityMapper.toEntity(orderModel));
    }

    @Override
    public void deleteOrder(Integer idOrder) {
        orderRepository.deleteById(idOrder);
    }

    @Override
    public List<OrderModel> findAllByRestaurantNit(Integer restaurantNit) {
        return orderRepository.findAllByRestaurant_Nit(restaurantNit)
                .stream().map(orderEntityMapper::toModel)
                .toList();
    }

}
