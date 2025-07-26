package Utils;

import dto.UserDto;

import static Utils.RandomUtils.generateEmail;

public class UserFabric {
    public static UserDto createValidUser(){
        return UserDto.builder()
                .username(generateEmail(5))
                .password("Password123!")
                .build();

    }
    public static UserDto createUser_invalidEmail(String email){
        return UserDto.builder()
                .username(email)
                .password("Password123!")
                .build();

    }
    public static UserDto createUser_invalidPassword(String password){
        return UserDto.builder()
                .username(generateEmail(5))
                .password(password)
                .build();

    }

}
