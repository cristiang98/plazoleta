package com.user.infrastructure.out.jpa.adapter;

import com.user.domain.model.UserModel;
import com.user.domain.spi.IUserPersistencePort;
import com.user.infrastructure.out.jpa.entity.UserEntity;
import com.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.user.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;


    @Override
    public UserModel saveUser(UserModel userModel) {

        UserEntity userEntity = userRepository.save(userEntityMapper.toEntity(userModel));
        return userEntityMapper.toModel(userEntity);
    }

    @Override
    public Boolean existsUser(Integer dni) {
        return userRepository.existsById(dni);
    }

    @Override
    public UserModel getUserByDni(Integer dni) { // cambiar a model

        return userEntityMapper.toModel(userRepository.findById(dni).get());
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        return Optional.ofNullable(userEntityMapper.toModel(userEntity));
    }

}
