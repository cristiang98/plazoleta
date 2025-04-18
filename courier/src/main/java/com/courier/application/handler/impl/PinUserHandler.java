package com.courier.application.handler.impl;

import com.courier.application.dto.request.PinUserRequestDto;
import com.courier.application.dto.response.PinUserResponseDto;
import com.courier.application.handler.IPinUserHandler;
import com.courier.application.mapper.IPinUserRequestMapper;
import com.courier.application.mapper.IPinUserResponseMapper;
import com.courier.domain.api.IPinUserServicePort;
import com.courier.domain.model.PinUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PinUserHandler implements IPinUserHandler{

    private final IPinUserServicePort pinUserServicePort;
    private final IPinUserRequestMapper pinUserRequestMapper;
    private final IPinUserResponseMapper pinUserResponseMapper;

    @Override
    public void savePinUser(PinUserRequestDto pinUserRequestDto, String phone) {
        PinUserModel pinUserModel = pinUserRequestMapper.toModel(pinUserRequestDto);
        pinUserServicePort.savePinUser(pinUserModel,phone);
    }

    @Override
    public PinUserResponseDto findPinUser(Integer pin) {
        PinUserModel pinUserModel = pinUserServicePort.findPinUser(pin);
        return pinUserResponseMapper.toResponseDto(pinUserModel);
    }
}
