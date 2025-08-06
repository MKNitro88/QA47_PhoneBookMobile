package Utils;

import dto.ContactsDto;
import dto.TokenDto;
import dto.UserDto;
import okhttp3.Response;

import java.io.IOException;

import static api_okhttp.LoginPost.login;
import static api_okhttp.GetAllUserContacts.*;

public class GetAllUserContactsApi implements BaseAPI {

    public static void main(String[] args) {
        System.out.println(getAllUserContactsApi(UserDto.builder()
                .username("qa_user_qwerty@mail.com")
                .password("Password123!")
                .build()).getContacts()[0]);
    }

    public static ContactsDto getAllUserContactsApi(UserDto user){
        Response response = login(user);
        if (response.isSuccessful()) {
            TokenDto token = null;
            try {
                token = GSON.fromJson(response.body().string(), TokenDto.class);
                response.close();
                Response response1 = getAllUserContacts(token);
                ContactsDto contacts = GSON.fromJson(response1.body().string(), ContactsDto.class);
                return contacts;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("created exeption getAllUserContactsApi");
            }
        }
        return null;
    }
}