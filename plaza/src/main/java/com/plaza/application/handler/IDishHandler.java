package com.plaza.application.handler;

import com.plaza.application.dto.request.DishRequestDto;
import com.plaza.application.dto.request.UpdateDishRequestDto;
import com.plaza.application.dto.response.DishResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface IDishHandler {

    void saveDish(DishRequestDto dishRequestDto, String token);
    void updateDish(Integer id ,
                    UpdateDishRequestDto updateDishRequestDto,
                    String token);

    void toggleDish(@Valid Integer id, String token);

    Page<DishResponseDto> getDishes(Integer nit ,int page, int size, String categoryFilter);
}
