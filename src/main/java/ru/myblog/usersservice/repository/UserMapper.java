package ru.myblog.usersservice.repository;

import ru.myblog.commonlib.user.Role;
import ru.myblog.commonlib.user.UserEntityDto;
import ru.myblog.commonlib.user.UserResponseDto;

public class UserMapper {
    public static UserEntityDto toUserEntityDto(UserEntity userEntity) {
        Long userEntityUserId = userEntity.getUserId();
        String userEntityUserName = userEntity.getUsername();
        String userEntityEmail = userEntity.getEmail();
        String userEntityPassword = userEntity.getPassword();
        String userEntityAboutMe = userEntity.getAboutMe();
        Role userEntityRole = userEntity.getRole();
        UserEntityDto userEntityDto = new UserEntityDto(
                userEntityUserId,
                userEntityUserName,
                userEntityEmail,
                userEntityPassword,
                userEntityAboutMe


        );
        return userEntityDto;
    }

    public static UserResponseDto toUserResponseDto(UserEntity userEntity) {
        return new UserResponseDto(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getAboutMe()
        );
    }

    public static UserEntity toEntity(UserEntityDto userEntityDto) {
        Long userEntityDto1UserId = userEntityDto.getUserId();
        String userEntityDto1UserName = userEntityDto.getUserName();
        String userEntityDto1Email = userEntityDto.getEmail();
        String userEntityDto1Password = userEntityDto.getPassword();
        String userEntityDto1AboutMe = userEntityDto.getAboutMe();

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userEntityDto1UserId);
        userEntity.setUsername(userEntityDto1UserName);
        userEntity.setEmail(userEntityDto1Email);
        userEntity.setPassword(userEntityDto1Password);
        userEntity.setAboutMe(userEntityDto1AboutMe);

        return userEntity;
    }
}