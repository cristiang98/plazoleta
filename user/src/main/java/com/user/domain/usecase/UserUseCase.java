package com.user.domain.usecase;

import com.user.domain.exception.UserExistException;
import com.user.domain.dto.RestaurantEmpRequestDto;
import com.user.domain.exception.NotOwnerException;
import com.user.domain.exception.InvalidTokenException;
import com.user.domain.spi.*;
import com.user.domain.api.IUserServicePort;
import com.user.domain.exception.UserUnderageException;
import com.user.domain.model.RoleModel;
import com.user.domain.model.UserModel;

import java.time.LocalDate;
import java.time.Period;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IUserEncryptPassword userEncryptPassword;
    private final IJwtHandler jwtHandler;
    private final IPlazaFeignClientPort plazaFeignClientPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IUserEncryptPassword userEncryptPassword,
                       IRolePersistencePort rolePersistencePort, IJwtHandler jwtHandler, IPlazaFeignClientPort plazaFeignClientPort) {
        this.userPersistencePort = userPersistencePort;
        this.userEncryptPassword = userEncryptPassword;
        this.rolePersistencePort = rolePersistencePort;
        this.jwtHandler = jwtHandler;
        this.plazaFeignClientPort = plazaFeignClientPort;
    }

    @Override
    public UserModel saveUser(UserModel userModel) {

        if(Period.between(userModel.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new UserUnderageException("El usuario debe ser mayor de edad");
        }
        userModel.setPassword(userEncryptPassword.encryptPassword(userModel.getPassword()));

        RoleModel roleModel = rolePersistencePort.findRoleByName("PROPIETARIO");
        userModel.setRole(roleModel);
        return userPersistencePort.saveUser(userModel);


    }

    @Override
    public void saveEmployee(UserModel userModel, String token) {

        if(token == null || token.isEmpty()) {
            throw new InvalidTokenException("Token invalido");

        }
        if (Period.between(userModel.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new UserUnderageException("El usuario debe ser mayor de edad");
        }
        UserModel currentUser = userPersistencePort.getUserByDni(jwtHandler.extractDni(token));

        if (!currentUser.getRole().getName().equals("PROPIETARIO")) {
            throw new NotOwnerException("No eres propietario");
        }
        userModel.setPassword(userEncryptPassword.encryptPassword(userModel.getPassword()));
        RoleModel roleModel = rolePersistencePort.findRoleByName("EMPLEADO");
        userModel.setRole(roleModel);
        userPersistencePort.saveUser(userModel);
        RestaurantEmpRequestDto restaurantEmpRequestDto = new RestaurantEmpRequestDto();
        restaurantEmpRequestDto.setEmployeeId(userModel.getDni());
        plazaFeignClientPort.saveRestaurantEmployee(restaurantEmpRequestDto);
    }

    @Override
    public void saveClient(UserModel userModel) {

        if(Boolean.TRUE.equals(userPersistencePort.existsUser(userModel.getDni()))) {
            throw new UserExistException("El usuario ya existe");
        }

        RoleModel roleModel = rolePersistencePort.findRoleByName("CLIENTE");
        userModel.setPassword(userEncryptPassword.encryptPassword(userModel.getPassword()));
        userModel.setRole(roleModel);
        userPersistencePort.saveUser(userModel);
    }

    @Override
    public UserModel getUserByDni(Integer dni) {
        return userPersistencePort.getUserByDni(dni);
    }

}
