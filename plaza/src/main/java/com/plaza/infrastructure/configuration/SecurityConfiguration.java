package com.plaza.infrastructure.configuration;

import com.plaza.infrastructure.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)  // Deshabilitamos CSRF porque JWT ya lo maneja
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/plaza/save-restaurant").hasAuthority("ADMINISTRADOR") // Solo los administradores pueden guardar restaurantes
                        .requestMatchers("/api/v1/dish/save-dish",
                                "/api/v1/dish/updateDish{id}",
                                "/api/v1/dish/toggle").hasAuthority("PROPIETARIO")
                        .requestMatchers("/api/v1/order/update-order",
                                "/api/v1/order/orders",
                                "/api/v1/order/ready-order" ,
                                "/api/v1/order/delivery-order").hasAuthority("EMPLEADO")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Agrega el filtro JWT
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
