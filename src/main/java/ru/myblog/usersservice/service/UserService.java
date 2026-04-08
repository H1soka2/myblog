package ru.myblog.usersservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.myblog.commonlib.constant.ExceptionTextMessageConstant;
import ru.myblog.commonlib.exception.UserNotFoundException;
import ru.myblog.commonlib.user.Role;
import ru.myblog.commonlib.user.UserEntityDto;
import ru.myblog.commonlib.user.service.CreateUserService;
import ru.myblog.commonlib.user.service.GetUserService;
import ru.myblog.commonlib.user.service.UpdateUserService;
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
public class UserService implements GetUserService, CreateUserService, UpdateUserService {

    UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    //============================ GET REQUEST ==============================
    @Override
    public UserEntityDto findById(Long id) {
        UserEntityDto findAll = repository.findById(id)
                .map(UserMapper::toUserEntityDto)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));
        return findAll;
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
        log.info("Create user. Username: {}, Email: {}",
                createUser.getUserName(),
                createUser.getEmail()
        );
        UserEntity user = new UserEntity();
        user.setUsername(createUser.getUserName());
        user.setEmail(createUser.getEmail());
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        user.setAboutMe(createUser.getAboutMe());

        // Присваиваем роль напрямую
        user.setRole(Role.USER);

        UserEntity savedUser = repository.save(user);

        return new UserEntityDto(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                null,                       // пароль не возвращаем
                savedUser.getAboutMe()

        );
    }


    //============================ DELETE REQUEST ==============================


    public void deleteCurrentUser() {

        String currentUserEmail = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserEntity entity = repository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (entity.getRole().equals(Role.ADMIN)) {
            throw new IllegalStateException("Cannot delete ADMIN account");
        }

        repository.delete(entity);
    }


    //============================ PUT REQUEST ==============================
    @Override
    public UserEntityDto update(UserEntityDto updateUser) {
        return null;
    }
}
