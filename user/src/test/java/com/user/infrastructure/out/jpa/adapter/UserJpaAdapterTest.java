package com.user.infrastructure.out.jpa.adapter;

import com.user.domain.model.UserModel;
import com.user.infrastructure.exception.UserExistException;
import com.user.infrastructure.out.jpa.entity.UserEntity;
import com.user.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.user.infrastructure.out.jpa.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.InjectMocks;

import java.time.LocalDate;
import java.util.Optional;

public class UserJpaAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    private static final Integer TEST_DNI = 1;
    private static final String EXCEPTION_MESSAGE = "El usuario no existe";

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(IUserRepository.class);
        userEntityMapper = Mockito.mock(IUserEntityMapper.class);
        userJpaAdapter = new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Test
    public void testSaveUser_success() {
        UserModel userModel = new UserModel(1, "John", "Doe", "123456789", LocalDate.of(1990, 1, 1), "john.doe@example.com", "password", null);
        UserEntity userEntity = new UserEntity();

        when(userEntityMapper.toEntity(any(UserModel.class))).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userEntityMapper.toModel(any(UserEntity.class))).thenReturn(userModel);

        UserModel result = userJpaAdapter.saveUser(userModel);

        assertEquals(userModel, result);
    }

    @Test
    public void testExistsUser_userExists_returnsTrue() {
        Integer dni = 1;

        when(userRepository.existsById(dni)).thenReturn(true);

        Boolean result = userJpaAdapter.existsUser(dni);

        assertEquals(true, result);
        verify(userRepository, times(1)).existsById(dni);
    }

    @Test
    public void testExistsUser_userDoesNotExist_returnsFalse() {
        Integer dni = 1;

        when(userRepository.existsById(dni)).thenReturn(false);

        Boolean result = userJpaAdapter.existsUser(dni);

        assertEquals(false, result);
        verify(userRepository, times(1)).existsById(dni);
    }

    @Test
    public void testGetUserByDni_userExists_returnsUserEntity() {
        UserEntity userEntity = UserEntity.builder()
                .dni(TEST_DNI)
                .email("john.doe@example.com")
                .build();

        when(userRepository.findById(TEST_DNI)).thenReturn(Optional.of(userEntity));

        UserEntity result = userJpaAdapter.getUserByDni(TEST_DNI);

        assertEquals(userEntity, result);
        verify(userRepository, times(1)).findById(TEST_DNI);
    }

    @Test
    public void testGetUserByDni_userDoesNotExist_throwsException() {
        when(userRepository.findById(TEST_DNI)).thenReturn(Optional.empty());

        UserExistException exception = org.junit.jupiter.api.Assertions.assertThrows(
                UserExistException.class,
                () -> userJpaAdapter.getUserByDni(TEST_DNI)
        );

        assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
        verify(userRepository, times(1)).findById(TEST_DNI);
    }

    @Test
    public void testFindByEmail_userExists_returnsUserModel() {
        String email = "john.doe@example.com";
        UserEntity userEntity = UserEntity.builder()
                .dni(TEST_DNI)
                .email(email)
                .build();

        UserModel userModel = new UserModel(TEST_DNI, "John", "Doe", "123456789", LocalDate.of(1990, 1, 1), email, "password", null);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toModel(userEntity)).thenReturn(userModel);

        Optional<UserModel> result = userJpaAdapter.findByEmail(email);

        assertEquals(userModel, result.orElse(null));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testFindByEmail_userDoesNotExist_returnsEmptyOptional() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<UserModel> result = userJpaAdapter.findByEmail(email);

        assertEquals(Optional.empty(), result);
        verify(userRepository, times(1)).findByEmail(email);
    }
}