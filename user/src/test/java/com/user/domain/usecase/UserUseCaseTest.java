package com.user.domain.usecase;


import  com.user.domain.exception.UserExistException;
import com.user.domain.exception.InvalidTokenException;
import com.user.domain.exception.UserUnderageException;
import com.user.domain.model.RoleModel;
import com.user.domain.model.UserModel;
import com.user.domain.spi.*;
import com.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IUserEncryptPassword userEncryptPassword;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private IJwtHandler jwtHandler;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private IPlazaFeignClientPort plazaFeignClientPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private UserModel userModel;
    private RoleModel roleModel;

    @BeforeEach
    void setUp() {
        userModel = new UserModel();
        roleModel = new RoleModel(2, "PROPIETARIO", "Propietario");
        userModel.setDni(123456789);
        userModel.setName("John");
        userModel.setLastName("Beamer");
        userModel.setPhone("123456");
        userModel.setBirthDate(LocalDate.of(2015, 1, 1));
        userModel.setEmail("example@gmail.com");
        userModel.setPassword("123456");
        userModel.setRole(roleModel);
    }

    @Test
    void saveUser_ShouldThrowException_WhenUserIsUnderage() {
        // Act & Assert
        assertThrows(UserUnderageException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void saveUser_ShouldSetRoleAndEncryptPassword_WhenUserIsValid() {
        // Arranque
        userModel.setBirthDate(LocalDate.of(2005, 1, 1)); // Usuario mayor de edad
        String encryptedPassword = "encryptedpassword";

        // Simulamos el comportamiento de encriptación
        when(userEncryptPassword.encryptPassword(userModel.getPassword())).thenReturn(encryptedPassword);

        // Simulamos el comportamiento del persistence port
        UserModel savedUser = new UserModel();
        savedUser.setDni(userModel.getDni());
        savedUser.setName(userModel.getName());
        savedUser.setLastName(userModel.getLastName());
        savedUser.setPhone(userModel.getPhone());
        savedUser.setBirthDate(userModel.getBirthDate());
        savedUser.setEmail(userModel.getEmail());
        savedUser.setPassword(encryptedPassword);
        savedUser.setRole(userModel.getRole());


        when(userPersistencePort.saveUser(any(UserModel.class))).thenReturn(savedUser);
        when(rolePersistencePort.findRoleByName("PROPIETARIO")).thenReturn(roleModel);

        // Act
        UserModel result = userUseCase.saveUser(userModel);

        // Assert
        assertNotNull(result);
        assertEquals(userModel.getRole(), result.getRole());
        assertEquals(encryptedPassword, result.getPassword());

        // Verificar que encryptPassword se llamó con la contraseña inicial
        verify(userEncryptPassword).encryptPassword("123456");

        verify(userPersistencePort).saveUser(any(UserModel.class));
    }

    @Test
    void saveEmployee_ShouldThrowException_WhenTokenIsInvalid() {
        // Arrange
        String invalidToken = "";
        UserModel employeeModel = new UserModel();
        employeeModel.setBirthDate(LocalDate.of(2005, 1, 1)); // Employee is of valid age

        // Assert
        assertThrows(InvalidTokenException.class,
                () -> userUseCase.saveEmployee(employeeModel, invalidToken));
    }

    @Test
    void saveEmployee_ShouldThrowException_WhenUserIsUnderage() {
        // Arrange
        String validToken = "valid-token";
        UserModel underageEmployeeModel = new UserModel();
        underageEmployeeModel.setBirthDate(LocalDate.of(2015, 1, 1)); // Employee is underage

        // Assert
        assertThrows(UserUnderageException.class,
                () -> userUseCase.saveEmployee(underageEmployeeModel, validToken));
    }

    @Test
    void saveClient_ShouldThrowException_WhenUserExists() {
        // Arrange
        int existingDni = userModel.getDni();
        when(userPersistencePort.existsUser(existingDni)).thenReturn(true);

        // Act & Assert
        assertThrows(UserExistException.class,
                () -> userUseCase.saveClient(userModel));

        // Verify
        verify(userPersistencePort).existsUser(existingDni);
        verifyNoInteractions(rolePersistencePort, userEncryptPassword);
    }

    @Test
    void saveClient_ShouldSaveUserWithClientRole() {
        // Arrange
        int newDni = userModel.getDni();
        String encryptedPassword = "encrypted-password";
        RoleModel clientRole = new RoleModel(4, "CLIENTE", "Cliente");

        when(userPersistencePort.existsUser(newDni)).thenReturn(false);
        when(userEncryptPassword.encryptPassword(userModel.getPassword())).thenReturn(encryptedPassword);
        when(rolePersistencePort.findRoleByName("CLIENTE")).thenReturn(clientRole);

        // Act
        userUseCase.saveClient(userModel);

        // Assert
        assertEquals("CLIENTE", userModel.getRole().getName());
        assertEquals(encryptedPassword, userModel.getPassword());
        verify(userPersistencePort).existsUser(newDni);
        verify(userEncryptPassword).encryptPassword("123456");
        verify(rolePersistencePort).findRoleByName("CLIENTE");
        verify(userPersistencePort).saveUser(userModel);
    }
}