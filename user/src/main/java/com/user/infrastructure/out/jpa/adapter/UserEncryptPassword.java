package com.user.infrastructure.out.jpa.adapter;

import com.user.domain.spi.IUserEncryptPassword;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserEncryptPassword implements IUserEncryptPassword {

    private final PasswordEncoder passwordEncoder;

    public UserEncryptPassword(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Boolean checkPassword(String password, String hash) {
        return passwordEncoder.matches(password, hash);
    }
}
