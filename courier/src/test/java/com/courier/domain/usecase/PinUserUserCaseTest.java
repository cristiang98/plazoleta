package com.courier.domain.usecase;

import com.courier.domain.exception.PinExistException;
import com.courier.domain.model.PinUserModel;
import com.courier.domain.spi.IPinUserPersistencePort;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class PinUserUserCaseTest {

    @Mock
    private IPinUserPersistencePort pinUserPersistencePort;

    @Mock
    private Random random;

    @InjectMocks
    private PinUserUserCase pinUserUserCase;

    @BeforeEach
    void setUp() {

        pinUserUserCase.accountSidTwilio = System.getenv().getOrDefault("TWILIO_ACCOUNT_SID", "buscarCredenciales");
        pinUserUserCase.authTokenTwilio = System.getenv().getOrDefault("TWILIO_AUTH_TOKEN", "buscarCredenciales");
        pinUserUserCase.myPhoneNumberTwilio = System.getenv().getOrDefault("TWILIO_PHONE_NUMBER", "+buscarCredenciales");

        // Interceptamos Twilio.init para evitar que se ejecute durante los tests
        try (var mockedTwilio = mockStatic(Twilio.class)) {
            mockedTwilio.when(() -> Twilio.init(anyString(), anyString())).thenAnswer(invocation -> null);
        }
    }



    @Test
    void testSavePinUser_PinAlreadyExists() {
        // Arrange
        PinUserModel existingModel = new PinUserModel();
        existingModel.setUserId(1);
        existingModel.setOrderId(1);
        existingModel.setPin(54321);

        when(pinUserPersistencePort.findByUserIdAndOrderId(1, 1)).thenReturn(existingModel);

        PinUserModel newPinUserModel = new PinUserModel();
        newPinUserModel.setUserId(1);
        newPinUserModel.setOrderId(1);

        String phone = "+1234567890";

        // Act & Assert
        PinExistException exception = assertThrows(PinExistException.class, () -> pinUserUserCase.savePinUser(newPinUserModel, phone));
        assertEquals("ya se se ha generado un pin para esta orden:54321", exception.getMessage());

        verify(pinUserPersistencePort, never()).savePinUser(any(PinUserModel.class));
    }


    @Test
    void testFindPinUser_ExistingPin() {
        // Arrange
        Integer pin = 12345;
        PinUserModel expectedPinUser = new PinUserModel(1, pin, 1, 1);

        when(pinUserPersistencePort.findByPin(pin)).thenReturn(expectedPinUser);

        // Act
        PinUserModel result = pinUserUserCase.findPinUser(pin);

        // Assert
        assertNotNull(result);
        assertEquals(pin, result.getPin());
        assertEquals(1, result.getUserId());
        assertEquals(1, result.getOrderId());
        verify(pinUserPersistencePort, times(1)).findByPin(pin);
    }

    @Test
    void testFindPinUser_NonExistentPin() {
        // Arrange
        Integer pin = 99999;

        when(pinUserPersistencePort.findByPin(pin)).thenReturn(null);

        // Act
        PinUserModel result = pinUserUserCase.findPinUser(pin);

        // Assert
        assertNull(result);
        verify(pinUserPersistencePort, times(1)).findByPin(pin);
    }


    @Test
    void testSavePinUser_SuccessfulSave() {
        // Arrange
        PinUserModel newPinUserModel = new PinUserModel();
        newPinUserModel.setUserId(1);
        newPinUserModel.setOrderId(2);

        String phone = "+573117077453";

        when(pinUserPersistencePort.findByUserIdAndOrderId(1, 2)).thenReturn(null);
        when(random.nextInt(90000)).thenReturn(12345);

        doNothing().when(pinUserPersistencePort).savePinUser(any(PinUserModel.class));
        // Act
        pinUserUserCase.savePinUser(newPinUserModel, phone);

        // Assert
        assertNotNull(newPinUserModel.getPin());
        verify(pinUserPersistencePort).savePinUser(newPinUserModel);
    }

    @Test
    void testSavePinUser_TwilioFailure() {
        // Arrange
        PinUserModel newPinUserModel = new PinUserModel();
        newPinUserModel.setUserId(1);
        newPinUserModel.setOrderId(2);

        String phone = "+1234567890";

        when(pinUserPersistencePort.findByUserIdAndOrderId(1, 2)).thenReturn(null);
        when(random.nextInt(90000)).thenReturn(12345);

        Twilio.init("invalidSid", "invalidToken");

        // Act & Assert
        assertThrows(Exception.class, () -> pinUserUserCase.savePinUser(newPinUserModel, phone));
    }

}
