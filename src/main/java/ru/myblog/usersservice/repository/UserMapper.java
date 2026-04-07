package ru.myblog.usersservice.repository;



import ru.myblog.commonlib.user.Role;
import ru.myblog.commonlib.user.UserEntityDto;

public class UserMapper {
    public static UserEntityDto toUserEntityDto(UserEntity userEntity) {

        if (userEntity == null)return null;

        Long userEntityUserId = userEntity.getUserId();
        String userEntityUserName = userEntity.getUserName();
        String userEntityEmail = userEntity.getEmail();
        String userEntityPassword = userEntity.getPassword();
        String userEntityAboutMe = userEntity.getAboutMe();
        Role userEntityRole = userEntity.getRole();

        UserEntityDto userEntityDto = new UserEntityDto(
                userEntityUserName,
                userEntityEmail,
                userEntityPassword,
                userEntityAboutMe,
                userEntityRole
        );
        return userEntityDto;
    }

    public static UserEntity toEntity(UserEntityDto userDto) {
        if (userDto == null) return null;

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userDto.getUsername());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setAboutMe(userDto.getAboutMe());
        userEntity.setRole(userDto.getRole());
        return userEntity;
    }
}

