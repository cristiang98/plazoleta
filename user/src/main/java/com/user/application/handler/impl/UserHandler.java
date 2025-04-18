package com.user.application.handler.impl;

import com.user.application.dto.request.UserRequestDto;
import com.user.application.dto.response.UserResponseDto;
import com.user.application.handler.IUserHandler;
import com.user.application.mapper.IUserRequestMapper;
import com.user.application.mapper.IUserResponseMapper;
import com.user.domain.api.IUserServicePort;
import com.user.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    @Qualifier("IUserRequestMapper")
    private final IUserRequestMapper userRequestMapper;
    @Qualifier("IUserResponseMapper")
    private final IUserResponseMapper userResponseMapper;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        userServicePort.saveUser(userRequestMapper.toUserModel(userRequestDto));
    }

    @Override
    public void saveEmployee(UserRequestDto userRequestDto, String token) {
        userServicePort.saveEmployee(userRequestMapper.toUserModel(userRequestDto), token);
    }

    @Override
    public UserResponseDto getUserByDni(Integer dni) {
        return userResponseMapper.toUserResponseDto(userServicePort.getUserByDni(dni));
    }

    @Override
    public void saveClient(UserRequestDto userRequestDto) {
        userServicePort.saveClient(userRequestMapper.toUserModel(userRequestDto));
    }

}
