package com.plaza.domain.spi;
import com.plaza.domain.dto.UserResponseDto;

public interface IUserFeignClientPort {

    UserResponseDto findByDni(Integer userId);

}
