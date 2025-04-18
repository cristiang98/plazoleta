package com.courier.application.handler;

import com.courier.application.dto.request.PinUserRequestDto;
import com.courier.application.dto.response.PinUserResponseDto;

public interface IPinUserHandler {

    void savePinUser(PinUserRequestDto pinUserRequestDto, String phone);

    PinUserResponseDto findPinUser(Integer pin);

}
