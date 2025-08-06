package api_okhttp_test;

import dto.ContactsDto;
import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.UserDto;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static Utils.GetAllUserContactsApi.*;
import static api_okhttp.LoginPost.*;
import static api_okhttp.GetAllUserContacts.*;
import static api_okhttp.DeleteContactById.*;

public class DeleteContactByIdTests {
    UserDto qa_user = UserDto.builder()
            .username("qa_user_qwerty@mail.com")
            .password("Password123!")
            .build();
    ContactsDto contactsDto;
    TokenDto tokenDto;

    @BeforeMethod
    public void loginGetAllContact() throws IOException {
        Response response = login(qa_user);
        tokenDto = GSON.fromJson(response.body().string(), TokenDto.class);
        Response response1 = getAllUserContacts(tokenDto);
        contactsDto = GSON.fromJson(response1.body().string(), ContactsDto.class);
    }

    @Test
    public void deleteContactPositiveTest() {
        String id = contactsDto.getContacts()[0].getId();
        Response response = deleteContactById(tokenDto, id);
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void deleteContactNegativeTest_emptyId() throws IOException {
        String id = "";
        Response response = deleteContactById(tokenDto, id);
        System.out.println(response.code());
        System.out.println(response.message());
        response.close();
    }

    @Test
    public void deleteContactNegativeTest_wrongId() throws IOException {
        String id = "1";
        Response response = deleteContactById(tokenDto, id);
        System.out.println(response.message());
        System.out.println(response.code());
        if (response.code() == 400) {
            System.out.println(response.body().contentLength());
            ErrorMessageDto errorMessageDto =
                    GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessageDto.getMessage().toString());
            Assert.assertTrue(errorMessageDto.getMessage().toString()
                    .contains("not found in your contacts!"));
        }

    }
}