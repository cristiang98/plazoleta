package com.plaza.domain.spi;

import com.plaza.domain.dto.PinUserRequestDto;
import com.plaza.domain.dto.PinUserResponseDto;

public interface IPinUserFeignPort {

    void savePinUser(PinUserRequestDto pinUserRequestDto, String phone);
    PinUserResponseDto findPinUser(Integer pin);

}
