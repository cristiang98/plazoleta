package com.user.domain.spi;

import com.user.domain.model.TokenModel;
import com.user.domain.model.UserLoginModel;

public interface IAuthPersistencePort {

    TokenModel login(UserLoginModel userLoginModel);

}
