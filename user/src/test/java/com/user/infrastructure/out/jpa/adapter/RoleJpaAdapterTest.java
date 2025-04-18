package com.user.infrastructure.out.jpa.adapter;

import com.user.domain.model.RoleModel;
import com.user.infrastructure.out.jpa.entity.RoleEntity;
import com.user.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.user.infrastructure.out.jpa.repository.IRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleJpaAdapterTest {

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private IRoleEntityMapper roleEntityMapper;

    @InjectMocks
    private RoleJpaAdapter roleJpaAdapter;

    @Test
    void findRoleById_ShouldReturnRoleModel_WhenRoleEntityExists() {
        Integer id = 1;
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(id);
        roleEntity.setName("Admin");
        roleEntity.setDescription("Administrator role");

        RoleModel expectedRoleModel = new RoleModel(id, "Admin", "Administrator role");

        when(roleRepository.findById(id)).thenReturn(Optional.of(roleEntity));
        when(roleEntityMapper.toModel(roleEntity)).thenReturn(expectedRoleModel);

        RoleModel result = roleJpaAdapter.findRoleById(id);

        assertEquals(expectedRoleModel, result);
        verify(roleRepository, times(1)).findById(id);
        verify(roleEntityMapper, times(1)).toModel(roleEntity);
    }

    @Test
    void findRoleById_ShouldReturnNull_WhenRoleEntityDoesNotExist() {
        Integer id = 1;

        when(roleRepository.findById(id)).thenReturn(Optional.empty());
        when(roleEntityMapper.toModel(null)).thenReturn(null);

        RoleModel result = roleJpaAdapter.findRoleById(id);

        assertNull(result);
        verify(roleRepository, times(1)).findById(id);
        verify(roleEntityMapper, times(1)).toModel(null);
    }

    @Test
    void findRoleByName_ShouldReturnRoleModel_WhenRoleEntityExists() {
        String name = "Admin";
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1);
        roleEntity.setName(name);
        roleEntity.setDescription("Administrator role");

        RoleModel expectedRoleModel = new RoleModel(1, name, "Administrator role");

        when(roleRepository.findByName(name)).thenReturn(roleEntity);
        when(roleEntityMapper.toModel(roleEntity)).thenReturn(expectedRoleModel);

        RoleModel result = roleJpaAdapter.findRoleByName(name);

        assertEquals(expectedRoleModel, result);
        verify(roleRepository, times(1)).findByName(name);
        verify(roleEntityMapper, times(1)).toModel(roleEntity);
    }

    @Test
    void findRoleByName_ShouldReturnNull_WhenRoleEntityDoesNotExist() {
        String name = "Admin";

        when(roleRepository.findByName(name)).thenReturn(null);
        when(roleEntityMapper.toModel(null)).thenReturn(null);

        RoleModel result = roleJpaAdapter.findRoleByName(name);

        assertNull(result);
        verify(roleRepository, times(1)).findByName(name);
        verify(roleEntityMapper, times(1)).toModel(null);
    }
}