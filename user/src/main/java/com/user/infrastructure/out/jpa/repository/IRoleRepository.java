package com.user.infrastructure.out.jpa.repository;

import com.user.infrastructure.out.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByName(String name);

}
