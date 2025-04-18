package com.courier.domain.api;

import com.courier.domain.model.PinUserModel;

public interface IPinUserServicePort {

    void savePinUser(PinUserModel pinUserModel, String phone);

    PinUserModel findPinUser(Integer pin);

}
