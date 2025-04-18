package com.user.application.mapper;

import com.user.application.dto.request.UserLoginRequestDto;
import com.user.application.dto.request.UserRequestDto;
import com.user.domain.model.UserLoginModel;
import com.user.domain.model.UserModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-16T19:39:38-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class IUserRequestMapperImpl implements IUserRequestMapper {

    @Override
    public UserModel toUserModel(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        UserModel userModel = new UserModel();

        if ( userRequestDto.getDni() != null ) {
            userModel.setDni( userRequestDto.getDni() );
        }
        userModel.setName( userRequestDto.getName() );
        userModel.setLastName( userRequestDto.getLastName() );
        userModel.setPhone( userRequestDto.getPhone() );
        userModel.setBirthDate( userRequestDto.getBirthDate() );
        userModel.setEmail( userRequestDto.getEmail() );
        userModel.setPassword( userRequestDto.getPassword() );

        return userModel;
    }

    @Override
    public UserLoginModel toUserLoginModel(UserLoginRequestDto userLoginRequestDto) {
        if ( userLoginRequestDto == null ) {
            return null;
        }

        UserLoginModel userLoginModel = new UserLoginModel();

        userLoginModel.setEmail( userLoginRequestDto.getEmail() );
        userLoginModel.setPassword( userLoginRequestDto.getPassword() );

        return userLoginModel;
    }
}
