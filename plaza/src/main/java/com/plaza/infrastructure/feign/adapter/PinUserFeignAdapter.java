package com.plaza.infrastructure.feign.adapter;

import com.plaza.domain.dto.PinUserRequestDto;
import com.plaza.domain.dto.PinUserResponseDto;
import com.plaza.domain.spi.IPinUserFeignPort;
import com.plaza.infrastructure.out.jpa.feign.IPinUserFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PinUserFeignAdapter implements IPinUserFeignPort {

    private final IPinUserFeignClient pinUserFeignClient;

    @Override
    public void savePinUser(PinUserRequestDto pinUserRequestDto, String phone) {
        pinUserFeignClient.savePinUser(pinUserRequestDto, phone);
    }

    @Override
    public PinUserResponseDto findPinUser(Integer pin) {
        return null;
    }
}
