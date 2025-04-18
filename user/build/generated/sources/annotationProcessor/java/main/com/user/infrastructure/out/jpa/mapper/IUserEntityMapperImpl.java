package com.user.infrastructure.out.jpa.mapper;

import com.user.domain.model.RoleModel;
import com.user.domain.model.UserModel;
import com.user.infrastructure.out.jpa.entity.RoleEntity;
import com.user.infrastructure.out.jpa.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-16T19:03:42-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class IUserEntityMapperImpl implements IUserEntityMapper {

    @Override
    public UserModel toModel(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserModel userModel = new UserModel();

        if ( userEntity.getDni() != null ) {
            userModel.setDni( userEntity.getDni() );
        }
        userModel.setName( userEntity.getName() );
        userModel.setLastName( userEntity.getLastName() );
        userModel.setPhone( userEntity.getPhone() );
        userModel.setBirthDate( userEntity.getBirthDate() );
        userModel.setEmail( userEntity.getEmail() );
        userModel.setPassword( userEntity.getPassword() );
        userModel.setRole( roleEntityToRoleModel( userEntity.getRole() ) );

        return userModel;
    }

    @Override
    public UserEntity toEntity(UserModel userModel) {
        if ( userModel == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.dni( userModel.getDni() );
        userEntity.name( userModel.getName() );
        userEntity.lastName( userModel.getLastName() );
        userEntity.phone( userModel.getPhone() );
        userEntity.birthDate( userModel.getBirthDate() );
        userEntity.email( userModel.getEmail() );
        userEntity.password( userModel.getPassword() );
        userEntity.role( roleModelToRoleEntity( userModel.getRole() ) );

        return userEntity.build();
    }

    protected RoleModel roleEntityToRoleModel(RoleEntity roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        RoleModel roleModel = new RoleModel();

        roleModel.setId( roleEntity.getId() );
        roleModel.setName( roleEntity.getName() );
        roleModel.setDescription( roleEntity.getDescription() );

        return roleModel;
    }

    protected RoleEntity roleModelToRoleEntity(RoleModel roleModel) {
        if ( roleModel == null ) {
            return null;
        }

        RoleEntity.RoleEntityBuilder roleEntity = RoleEntity.builder();

        roleEntity.id( roleModel.getId() );
        roleEntity.name( roleModel.getName() );
        roleEntity.description( roleModel.getDescription() );

        return roleEntity.build();
    }
}
