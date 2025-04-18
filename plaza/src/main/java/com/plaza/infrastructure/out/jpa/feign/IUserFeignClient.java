package com.plaza.infrastructure.out.jpa.feign;
import com.plaza.domain.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "users", path = "/api/v1/user")
public interface IUserFeignClient {

    @GetMapping("/get-by-dni")
    UserResponseDto getUserByDni(@RequestParam("dni") Integer dni);


}
