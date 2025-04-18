package com.user.domain.spi;

public interface IUserEncryptPassword {

    String encryptPassword(String password);

    Boolean checkPassword(String password, String hash);

}
