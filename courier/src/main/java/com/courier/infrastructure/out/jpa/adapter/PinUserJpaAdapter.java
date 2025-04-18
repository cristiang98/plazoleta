package com.courier.infrastructure.out.jpa.adapter;

import com.courier.domain.model.PinUserModel;
import com.courier.domain.spi.IPinUserPersistencePort;
import com.courier.infrastructure.out.jpa.mapper.IPinUserEntityMapper;
import com.courier.infrastructure.out.jpa.repository.IPinUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PinUserJpaAdapter implements IPinUserPersistencePort {

    private final IPinUserRepository pinUserRepository;
    private final IPinUserEntityMapper pinUserEntityMapper;

    @Override
    public void savePinUser(PinUserModel pinUserModel) {
        pinUserRepository.save(pinUserEntityMapper.toEntity(pinUserModel));
    }

    @Override
    public PinUserModel findByUserIdAndOrderId(Integer userId, Integer orderId) {
        return pinUserEntityMapper.toModel(pinUserRepository.findByUserIdAndOrderId(userId, orderId));
    }

    @Override
    public PinUserModel findByPin(Integer pin) {
        return pinUserEntityMapper.toModel(pinUserRepository.findByPin(pin));
    }
}
