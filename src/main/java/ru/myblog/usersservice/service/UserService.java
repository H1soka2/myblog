package ru.myblog.usersservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.myblog.commonlib.constant.ExceptionTextMessageConstant;
import ru.myblog.commonlib.exception.UserNotFoundException;
import ru.myblog.commonlib.user.Role;
import ru.myblog.commonlib.user.UserEntityDto;
import ru.myblog.commonlib.user.service.CreateUserService;
import ru.myblog.commonlib.user.service.DeleteUserService;
import ru.myblog.commonlib.user.service.GetUserService;
import ru.myblog.commonlib.user.service.UpdateUserService;
import ru.myblog.usersservice.repository.RoleRepository;
import ru.myblog.usersservice.repository.UserEntity;
import ru.myblog.usersservice.repository.UserMapper;
import ru.myblog.usersservice.repository.UserRepository;

import static ru.myblog.commonlib.constant.LoggerTextMessageConstant.*;
import static ru.myblog.commonlib.constant.ExceptionTextMessageConstant.*;

import java.util.List;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class UserService implements GetUserService, CreateUserService, DeleteUserService, UpdateUserService {

    RoleRepository roleRepository;
    UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    //============================ GET REQUEST ==============================
    @Override
    public UserEntityDto findById(Long id) {
        UserEntityDto findAll = repository.findById(id)
                .map(UserMapper::toUserEntityDto)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));
        return null;
    }

    @Override
    public UserEntityDto findByEmail(String email) {
        log.info(FIND_USER_BY_EMAIL_MESSAGE_LOGGER_SERVICE, email);
        UserEntityDto findEmail = repository.findByEmail(email)
                .map(UserMapper :: toUserEntityDto)
                .orElseThrow(() -> new UserNotFoundException(ExceptionTextMessageConstant.USER_NOT_FOUND_EXCEPTION));
        return findEmail;
    }

    @Override
    public UserEntityDto findByUsername(String username) {
        log.info(FIND_USER_BY_USERNAME_MESSAGE_LOGGER_SERVICE,username);
        UserEntityDto findUsername = repository.findByUsername(username)
                .map(UserMapper :: toUserEntityDto)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));
        return findUsername;
    }

    @Override
    public List<UserEntityDto> findByAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toUserEntityDto)
                .toList();
    }

    //============================ POST REQUEST ==============================
    
    @Override
    public UserEntityDto createUser(UserEntityDto createUser) {
        log.info(SAVE_USER_MESSAGE_LOGGER_SERVICE, createUser);
        UserEntity user = new UserEntity();
        user.setUserName(createUser.getUserName());
        user.setEmail(createUser.getEmail());
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        user.setAboutMe(createUser.getAboutMe());

        Role defaultRole = roleRepository.findByRole(Role.USER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRole(defaultRole);

        UserEntity savedUser = repository.save(user);

        return new UserEntityDto(
                savedUser.getUserId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                null,                       // пароль не возвращаем
                savedUser.getAboutMe()
        );
    }


    //============================ DELETE REQUEST ==============================
    @Override
    public UserEntityDto userDeleteLogin(String email) {
        log.info(FIND_USER_BY_EMAIL_DELETE_MESSAGE_LOGGER_SERVICE);
        UserEntity entity = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));
        repository.delete(entity);
        return UserMapper.toUserEntityDto(entity);
    }


    //============================ PUT REQUEST ==============================
    @Override
    public UserEntityDto update(UserEntityDto updateUser) {
        return null;
    }
}
