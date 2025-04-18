package com.user.infrastructure.out.jpa.adapter;

import com.user.domain.model.TokenModel;
import com.user.domain.model.UserLoginModel;
import com.user.domain.spi.IAuthPersistencePort;
import com.user.domain.spi.IJwtHandler;
import com.user.infrastructure.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class AuthJpaAdapter implements IAuthPersistencePort {

    private final AuthenticationManager authenticationManager;
    private final IJwtHandler jwtHandler;

    @Override
    public TokenModel login(UserLoginModel userLoginModel) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginModel.getEmail(),
                        userLoginModel.getPassword()
                )
        );

        // Obtener detalles del usuario autenticado
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Generar el token JWT
        String token = jwtHandler.generateToken(userDetails);

        // Retornar un TokenModel con el JWT generado
        return new TokenModel(token);
    }

}
