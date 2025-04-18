package com.user.application.handler.impl;

import com.user.application.dto.request.UserLoginRequestDto;
import com.user.application.dto.response.TokenResponseDto;
import com.user.application.handler.IAuthHandler;
import com.user.application.mapper.ITokenResponseMapper;
import com.user.application.mapper.IUserRequestMapper;
import com.user.domain.api.IAuthServicePort;
import com.user.domain.model.UserLoginModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandler implements IAuthHandler {

    private final IAuthServicePort userLoginServicePort;
    @Qualifier("IUserRequestMapper")
    private final IUserRequestMapper userRequestMapper;
    private final ITokenResponseMapper tokenResponseMapper;


    @Override
    public TokenResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
        UserLoginModel userLoginModel = userRequestMapper.toUserLoginModel(userLoginRequestDto);
        return tokenResponseMapper.toTokenResponseDto(userLoginServicePort.loginUser(userLoginModel));
    }

}
