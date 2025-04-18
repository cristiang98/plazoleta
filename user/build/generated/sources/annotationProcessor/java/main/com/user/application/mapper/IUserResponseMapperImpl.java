package com.user.application.mapper;

import com.user.application.dto.response.RoleResponseDto;
import com.user.application.dto.response.UserResponseDto;
import com.user.domain.model.RoleModel;
import com.user.domain.model.UserModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-16T19:03:42-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class IUserResponseMapperImpl implements IUserResponseMapper {

    @Override
    public UserResponseDto toUserResponseDto(UserModel userModel) {
        if ( userModel == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setDni( userModel.getDni() );
        userResponseDto.setName( userModel.getName() );
        userResponseDto.setLastName( userModel.getLastName() );
        userResponseDto.setPhone( userModel.getPhone() );
        userResponseDto.setBirthDate( userModel.getBirthDate() );
        userResponseDto.setEmail( userModel.getEmail() );
        userResponseDto.setPassword( userModel.getPassword() );
        userResponseDto.setRole( roleModelToRoleResponseDto( userModel.getRole() ) );

        return userResponseDto;
    }

    protected RoleResponseDto roleModelToRoleResponseDto(RoleModel roleModel) {
        if ( roleModel == null ) {
            return null;
        }

        RoleResponseDto roleResponseDto = new RoleResponseDto();

        roleResponseDto.setId( roleModel.getId() );
        roleResponseDto.setName( roleModel.getName() );
        roleResponseDto.setDescription( roleModel.getDescription() );

        return roleResponseDto;
    }
}
