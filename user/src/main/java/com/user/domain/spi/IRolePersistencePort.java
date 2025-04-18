package com.user.domain.spi;

import com.user.domain.model.RoleModel;

public interface IRolePersistencePort {

    RoleModel findRoleById(Integer id);
    RoleModel findRoleByName(String name);
}
