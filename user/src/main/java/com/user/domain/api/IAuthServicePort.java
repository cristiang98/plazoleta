package com.user.domain.api;

import com.user.domain.model.TokenModel;
import com.user.domain.model.UserLoginModel;

public interface IAuthServicePort {

    TokenModel loginUser(UserLoginModel userLoginModel);

}
