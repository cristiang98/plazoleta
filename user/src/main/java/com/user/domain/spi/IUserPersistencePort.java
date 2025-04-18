package com.user.domain.spi;

import com.user.domain.model.UserModel;

import java.util.Optional;

public interface IUserPersistencePort {

     UserModel saveUser(UserModel userModel);
     Boolean existsUser(Integer dni);
     UserModel getUserByDni(Integer dni);
     Optional<UserModel> findByEmail(String email);

}
