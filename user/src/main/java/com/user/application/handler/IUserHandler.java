package com.user.application.handler;
import com.user.application.dto.request.UserRequestDto;
import com.user.application.dto.response.UserResponseDto;
import jakarta.validation.Valid;

public interface IUserHandler {

    void saveUser(UserRequestDto userRequestDto);
    void saveEmployee(UserRequestDto userRequestDto, String token);
    UserResponseDto getUserByDni(Integer dni);

    void saveClient(@Valid UserRequestDto userRequestDto);
}
