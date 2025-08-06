package api_okhttp_test;

import dto.TokenDto;
import dto.UserDto;
import okhttp3.Response;
import org.testng.annotations.Test;

import java.io.IOException;

import static api_okhttp.LoginPost.*;

public class LoginTests {
    UserDto qa_user = UserDto.builder()
            .username("qa_user_qwerty@mail.com")
            .password("Password123!")
            .build();

    @Test
    public void loginPositiveTestApi() throws IOException {
        Response response = login(qa_user);
        if(response.isSuccessful()){
            TokenDto token = GSON.fromJson(response.body().string(), TokenDto.class);
            System.out.println(token.getToken());
        }
    }
}