package com.courier.domain.usecase;

import com.courier.domain.api.IPinUserServicePort;
import com.courier.domain.exception.PinExistException;
import com.courier.domain.model.PinUserModel;
import com.courier.domain.spi.IPinUserPersistencePort;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

public class PinUserUserCase implements IPinUserServicePort {

    private final IPinUserPersistencePort pinUserPersistencePort;

    @Value("${account_sid_twilio}")
    String accountSidTwilio;

    @Value("${auth_token_twilio}")
    String authTokenTwilio;

    @Value("${my_phone_number_twilio}")
    String myPhoneNumberTwilio;

    private final Random random;

    public PinUserUserCase(IPinUserPersistencePort pinUserPersistencePort, Random random) {
        this.pinUserPersistencePort = pinUserPersistencePort;
        this.random = random;
    }


    @Override
    public void savePinUser(PinUserModel pinUserModel, String phone) {
        int randomNumber = 10000 + random.nextInt(90000);
        PinUserModel pinUserModelValidation = pinUserPersistencePort.findByUserIdAndOrderId(pinUserModel.getUserId(), pinUserModel.getOrderId());
        if (pinUserModelValidation != null) {
            Integer pin = pinUserModelValidation.getPin();
            throw new PinExistException("ya se se ha generado un pin para esta orden:" + pin);

        }
        pinUserModel.setPin(randomNumber);

        Twilio.init(accountSidTwilio, authTokenTwilio);
        Message.creator(
                new com.twilio.type.PhoneNumber(phone),
                new com.twilio.type.PhoneNumber(myPhoneNumberTwilio),
                 "Su pedido esta LISTO y Su pin de seguridad es " + randomNumber ).create();
        pinUserPersistencePort.savePinUser(pinUserModel);
    }

    @Override
    public PinUserModel findPinUser(Integer pin) {
        return pinUserPersistencePort.findByPin(pin);
    }

}
