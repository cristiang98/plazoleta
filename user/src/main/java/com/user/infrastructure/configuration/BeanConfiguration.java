package com.user.infrastructure.configuration;
import com.user.domain.api.IAuthServicePort;
import com.user.domain.spi.*;
import com.user.domain.api.IUserServicePort;
import com.user.domain.usecase.AuthUseCase;
import com.user.domain.usecase.UserUseCase;
import com.user.infrastructure.exception.UserExistException;
import com.user.infrastructure.feign.IPlazaFeignClient;
import com.user.infrastructure.out.jpa.adapter.*;
import com.user.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.user.infrastructure.out.jpa.repository.IRoleRepository;
import com.user.infrastructure.out.jpa.repository.IUserRepository;
import com.user.infrastructure.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUserRepository userRepository;
    @Qualifier("IUserEntityMapper")
    private final IUserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;
    @Qualifier("IRoleEntityMapper")
    private final IRoleEntityMapper roleEntityMapper;
    private final IJwtHandler jwtHandler;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final IPlazaFeignClient plazaFeignClient;

    @Bean
    public IPlazaFeignClientPort plazaFeignClientPort() {
        return new PlazaFeignAdapter(plazaFeignClient);
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper );
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IUserEncryptPassword userEncryptPassword() {
        return new UserEncryptPassword(encoder());
    }

    @Bean
    public IUserServicePort userServicePort() throws Exception {
        return new UserUseCase(userPersistencePort(),
                userEncryptPassword(),
                rolePersistencePort(),
                jwtHandler,
                plazaFeignClientPort()
        );
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userPersistencePort().findByEmail(username)
                .map(CustomUserDetails::new)  // Mapear a un objeto de tipo CustomUserDetails
                .orElseThrow(() -> new UserExistException("Usuario no encontrado"));
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
       return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public IAuthPersistencePort authPersistencePort() throws Exception {
        return new AuthJpaAdapter(authenticationManager(authenticationConfiguration), jwtHandler);
    }

    @Bean
    public IAuthServicePort authServicePort() throws Exception {
        return new AuthUseCase(authPersistencePort());
    }


}
