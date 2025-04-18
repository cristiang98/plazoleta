package com.user.infrastructure.security;

import com.user.domain.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final UserModel userModel;

    public CustomUserDetails(UserModel userModel) {
        this.userModel = userModel;
    }

    // Retorna los roles del usuario.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userModel.getRole().getName()));
    }

        // Retorna la contraseña del usuario.
    @Override
    public String getPassword() {
        return userModel.getPassword();
    }

        // Retorna el correo del usuario.
    @Override
    public String getUsername() {
        return userModel.getEmail();
    }

    // Personalizado: Retorna el DNI del usuario
    public Integer getDni() {
        return userModel.getDni();
    }

    // Personalizado: Retorna el nombre del usuario
    public String getName() {
        return userModel.getName();
    }

    // Personalizado: Retorna el apellido del usuario
    public String getLastName() {
        return userModel.getLastName();
    }

    // Personalizado: Retorna el teléfono del usuario
    public String getPhone() {
        return userModel.getPhone();
    }

    /*
     * Verifica si la cuenta no está expirada.
     * Actualmente, todas las cuentas están activas permanentemente.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
     * Verifica si la cuenta no está bloqueada.
     * Actualmente, todas las cuentas están desbloqueadas permanentemente.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
     * Verifica si las credenciales no están expiradas.
     * Actualmente, todas las credenciales son válidas permanentemente.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
     * Verifica si el usuario está habilitado.
     * Actualmente, todos los usuarios están habilitados por defecto.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
