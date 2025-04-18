package com.user.infrastructure.out.jpa.mapper;

import com.user.domain.model.RoleModel;
import com.user.infrastructure.out.jpa.entity.RoleEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-16T19:03:42-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class IRoleEntityMapperImpl implements IRoleEntityMapper {

    @Override
    public RoleModel toModel(RoleEntity roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        RoleModel roleModel = new RoleModel();

        roleModel.setId( roleEntity.getId() );
        roleModel.setName( roleEntity.getName() );
        roleModel.setDescription( roleEntity.getDescription() );

        return roleModel;
    }

    @Override
    public RoleEntity toEntity(RoleModel roleModel) {
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
