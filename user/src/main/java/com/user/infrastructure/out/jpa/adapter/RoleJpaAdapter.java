package com.user.infrastructure.out.jpa.adapter;

import com.user.domain.model.RoleModel;
import com.user.domain.spi.IRolePersistencePort;
import com.user.infrastructure.out.jpa.entity.RoleEntity;
import com.user.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.user.infrastructure.out.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public RoleModel findRoleById(Integer id) {
        RoleEntity roleEntity = roleRepository.findById(id).orElse(null);
        return roleEntityMapper.toModel(roleEntity);
    }

    @Override
    public RoleModel findRoleByName(String name) {
        RoleEntity roleEntity = roleRepository.findByName(name);
        return roleEntityMapper.toModel(roleEntity);
    }
}
