package com.plaza.infrastructure.feign.adapter;
import com.plaza.domain.dto.UserResponseDto;
import com.plaza.domain.spi.IUserFeignClientPort;
import com.plaza.infrastructure.out.jpa.feign.IUserFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFeignAdapter implements IUserFeignClientPort {

    private final IUserFeignClient userFeignClient;

    @Override
    public UserResponseDto findByDni(Integer userId) {
        return userFeignClient.getUserByDni(userId);
    }
}
