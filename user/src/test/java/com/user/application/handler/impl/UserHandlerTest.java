package com.user.application.handler.impl;

import com.user.application.dto.request.UserLoginRequestDto;
import com.user.application.dto.request.UserRequestDto;
import com.user.application.dto.response.TokenResponseDto;
import com.user.application.mapper.IUserRequestMapper;
import com.user.application.mapper.IUserResponseMapper;
import com.user.domain.api.IUserServicePort;
import com.user.domain.model.UserLoginModel;
import com.user.domain.model.UserModel;
import com.user.domain.spi.IUserPersistencePort;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    @Qualifier("IUserRequestMapper")
    private IUserRequestMapper userRequestMapper;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    @Qualifier("IUserResponseMapper")
    private IUserResponseMapper userResponseMapper;

    @InjectMocks
    private UserHandler userHandler;

    UserHandlerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_ShouldCallSaveUser_WhenUserDoesNotExist() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
                12345678,
                "John",
                "Doe",
                "+1234567890",
                LocalDate.of(1990, 1, 1),
                "john.doe@example.com",
                "password123"
        );

        // Act
        userHandler.saveUser(userRequestDto);

        // Assert
        verify(userRequestMapper).toUserModel(userRequestDto);
        verify(userServicePort).saveUser(any());
    }

    @Test
    void saveUser_ShouldThrowException_WhenUserAlreadyExists() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
                12345678,
                "John",
                "Doe",
                "+1234567890",
                LocalDate.of(1990, 1, 1),
                "john.doe@example.com",
                "password123"
        );

        verifyNoInteractions(userRequestMapper);
        verifyNoInteractions(userServicePort);
    }

    @Test
    void getUserByDni_ShouldCallMapperAndService() {
        // Arrange
        Integer dni = 12345678;

        // Act
        userHandler.getUserByDni(dni);

        // Assert
        verify(userServicePort).getUserByDni(dni);
        verify(userResponseMapper).toUserResponseDto(any());
    }

    @Test
    void loginUser_ShouldReturnTokenResponseDto_WhenLoginSuccessful() {
        // Arrange
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("john.doe@example.com", "password123");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        UserLoginModel userLoginModel = new UserLoginModel("john.doe@example.com", "password123");
        TokenResponseDto tokenResponseDto = new TokenResponseDto();

        when(userRequestMapper.toUserLoginModel(userLoginRequestDto)).thenReturn(userLoginModel);
        when(userServicePort.loginUser(userLoginModel, httpServletResponse)).thenReturn(tokenResponseDto);

        // Act
        TokenResponseDto result = userHandler.loginUser(userLoginRequestDto, httpServletResponse);

        // Assert
        verify(userRequestMapper).toUserLoginModel(userLoginRequestDto);
        verify(userServicePort).loginUser(userLoginModel, httpServletResponse);
        assertSame(tokenResponseDto, result);
    }

    @Test
    void loginUser_ShouldThrowException_WhenUserLoginFails() {
        // Arrange
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("john.doe@example.com", "wrongpassword");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        UserLoginModel userLoginModel = new UserLoginModel("john.doe@example.com", "wrongpassword");

        when(userRequestMapper.toUserLoginModel(userLoginRequestDto)).thenReturn(userLoginModel);
        when(userServicePort.loginUser(userLoginModel, httpServletResponse)).thenThrow(new RuntimeException("Login failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userHandler.loginUser(userLoginRequestDto, httpServletResponse));

        verify(userRequestMapper).toUserLoginModel(userLoginRequestDto);
        verify(userServicePort).loginUser(userLoginModel, httpServletResponse);
    }

    @Test
    void saveEmployee_ShouldCallSaveEmployee_WhenValidRequest() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
                12345678,
                "Jane",
                "Smith",
                "+9876543210",
                LocalDate.of(1995, 5, 10),
                "jane.smith@example.com",
                "securepassword"
        );
        String token = "valid-token";

        when(userRequestMapper.toUserModel(userRequestDto)).thenReturn(any());

        // Act
        userHandler.saveEmployee(userRequestDto, token);

        // Assert
        verify(userRequestMapper).toUserModel(userRequestDto);
        verify(userServicePort).saveEmployee(any(), eq(token));
    }

    @Test
    void saveEmployee_ShouldThrowException_WhenInvalidToken() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto(
                12345678,
                "Jane",
                "Smith",
                "+9876543210",
                LocalDate.of(1995, 5, 10),
                "jane.smith@example.com",
                "securepassword"
        );
        String token = "invalid-token";

        UserModel mockedUserModel = new UserModel(); // Puedes usar un mock aquí
        when(userRequestMapper.toUserModel(userRequestDto)).thenReturn(mockedUserModel);
        doThrow(new RuntimeException("Invalid token")).when(userServicePort).saveEmployee(any(), eq(token));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userHandler.saveEmployee(userRequestDto, token));

        verify(userRequestMapper).toUserModel(userRequestDto);
        verify(userServicePort).saveEmployee(any(), eq(token));
    }
}