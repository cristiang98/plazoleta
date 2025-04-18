package com.courier.infrastructure.out.jpa.repository;

import com.courier.infrastructure.out.jpa.entity.PinUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPinUserRepository extends JpaRepository<PinUserEntity, Integer> {

    PinUserEntity findByOrderId(Integer orderId);
    PinUserEntity findByUserIdAndOrderId(Integer userId, Integer orderId);
    PinUserEntity findByPin(Integer pin);

}
