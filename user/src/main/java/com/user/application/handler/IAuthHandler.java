package com.user.application.handler;

import com.user.application.dto.request.UserLoginRequestDto;
import com.user.application.dto.response.TokenResponseDto;
import com.user.domain.model.TokenModel;

public interface IAuthHandler {

    TokenResponseDto loginUser(UserLoginRequestDto userLoginRequestDto);

}
