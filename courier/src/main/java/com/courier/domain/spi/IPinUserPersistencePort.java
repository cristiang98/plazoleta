package com.courier.domain.spi;

import com.courier.domain.model.PinUserModel;

public interface IPinUserPersistencePort {

    void savePinUser(PinUserModel pinUserModel);

    PinUserModel findByUserIdAndOrderId(Integer userId, Integer orderId);

    PinUserModel findByPin(Integer pin);

}
