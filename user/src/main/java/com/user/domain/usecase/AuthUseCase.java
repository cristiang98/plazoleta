package com.user.domain.usecase;

import com.user.domain.api.IAuthServicePort;
import com.user.domain.model.TokenModel;
import com.user.domain.model.UserLoginModel;
import com.user.domain.spi.IAuthPersistencePort;

public class AuthUseCase implements IAuthServicePort {

    private final IAuthPersistencePort authPersistencePort;

    public AuthUseCase(IAuthPersistencePort authPersistencePort) {
        this.authPersistencePort = authPersistencePort;
    }

    @Override
    public TokenModel loginUser(UserLoginModel userLoginModel) {
        return authPersistencePort.login(userLoginModel);
    }
}
