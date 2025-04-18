package com.plaza.infrastructure.out.jpa.feign;

import com.plaza.domain.dto.PinUserRequestDto;
import com.plaza.domain.dto.PinUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "couriers", path = "/api/v1/pin")
public interface IPinUserFeignClient {


    @PostMapping("/save-pin")
    void savePinUser(@RequestBody PinUserRequestDto pinUserRequestDto,
                     @RequestParam String phone);

    @GetMapping("/find-pin")
    PinUserResponseDto findPinUser(@RequestParam Integer pin);

}
