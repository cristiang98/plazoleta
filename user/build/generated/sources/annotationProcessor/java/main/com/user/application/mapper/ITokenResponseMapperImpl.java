package com.user.application.mapper;

import com.user.application.dto.response.TokenResponseDto;
import com.user.domain.model.TokenModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-16T19:03:42-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class ITokenResponseMapperImpl implements ITokenResponseMapper {

    @Override
    public TokenResponseDto toTokenResponseDto(TokenModel tokenModel) {
        if ( tokenModel == null ) {
            return null;
        }

        TokenResponseDto.TokenResponseDtoBuilder tokenResponseDto = TokenResponseDto.builder();

        tokenResponseDto.token( tokenModel.getToken() );

        return tokenResponseDto.build();
    }
}
