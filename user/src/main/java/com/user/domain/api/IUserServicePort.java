package com.user.domain.api;

import com.user.domain.model.UserModel;

public interface IUserServicePort {

    UserModel saveUser(UserModel userModel);
    UserModel getUserByDni(Integer dni);

    void saveEmployee(UserModel userModel, String token);

    void saveClient(UserModel userModel);
}
