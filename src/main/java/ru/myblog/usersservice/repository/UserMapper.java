package ru.myblog.usersservice.repository;

import ru.myblog.commonlib.user.Role;
import ru.myblog.commonlib.user.UserEntityDto;

public class UserMapper {
    public static UserEntityDto toUserEntityDto(UserEntity userEntity) {
        Long userEntityUserId = userEntity.getUserId();
        String userEntityUserName = userEntity.getUserName();
        String userEntityEmail = userEntity.getEmail();
        String userEntityPassword = userEntity.getPassword();
        String userEntityAboutMe = userEntity.getAboutMe();
        Role userEntityRole = userEntity.getRole();
        UserEntityDto userEntityDto1 = new UserEntityDto(
                userEntityUserId,
                userEntityUserName,
                userEntityEmail,
                userEntityPassword,
                userEntityAboutMe

        );
        return userEntityDto1;
    }

    public static UserEntity toEntity(UserEntityDto userEntityDto) {
        Long userEntityDto1UserId = userEntityDto.getUserId();
        String userEntityDto1UserName = userEntityDto.getUserName();
        String userEntityDto1Email = userEntityDto.getEmail();
        String userEntityDto1Password = userEntityDto.getPassword();
        String userEntityDto1AboutMe = userEntityDto.getAboutMe();

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userEntityDto1UserId);
        userEntity.setUserName(userEntityDto1UserName);
        userEntity.setEmail(userEntityDto1Email);
        userEntity.setPassword(userEntityDto1Password);
        userEntity.setAboutMe(userEntityDto1AboutMe);

        return userEntity;
    }
}