package api_okhttp_test;

import dto.*;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static api_okhttp.GetAllUserContacts.getAllUserContacts;
import static api_okhttp.LoginPost.login;
import static Utils.BaseAPI.GSON;
import static api_okhttp.AddNewContactPost.*;
import static Utils.ContactFactory.*;

public class AddNewContactTests {
    UserDto qa_user = UserDto.builder()
            .username("qa_user_qwerty@mail.com")
            .password("Password123!")
            .build();
    ContactsDto contactsDtoBeforeTes;
    TokenDto tokenDto;

    @BeforeMethod
    public void loginGetAllContact() throws IOException {
        Response response = login(qa_user);
        tokenDto = GSON.fromJson(response.body().string(), TokenDto.class);
        Response response1 = getAllUserContacts(tokenDto);
        contactsDtoBeforeTes = GSON.fromJson(response1.body().string(), ContactsDto.class);
    }

    @Test
    public void addNewContactPositiveTest() throws IOException {
        Contact contact = createPositiveContact();
        Response response = addNewContactPost(tokenDto, contact);
        if (response.code() == 200) {
            Response response1 = getAllUserContacts(tokenDto);
            ContactsDto contactsDtoAfter = GSON.fromJson(response1.body().string(), ContactsDto.class);
            for (Contact contact1 : contactsDtoAfter.getContacts()) {
                if (contact1.equals(contact))
                    Assert.assertTrue(true);
                else
                    Assert.assertTrue(false);
            }
        } else
            Assert.fail("status code --> " + response.code());
    }

    @Test
    public void addNewContactNegativeTest_emptyPhone_400() throws IOException {
        Contact contact = createNegativeContact_wrongPhone("");
        Response response = addNewContactPost(tokenDto, contact);
        if (response.code() == 400) {
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessageDto.toString());
            Assert.assertTrue(errorMessageDto.getMessage().toString().contains("Phone number must contain only digits!"));
        }else
            Assert.fail("status code --> " + response.code());
    }

    @Test
    public void addNewContactNegativeTest_unauthorized_401() throws IOException {
        Contact contact = createNegativeContact_wrongPhone("");
        TokenDto wrongToken = TokenDto.builder()
                .token("11111111111111111111111111111111111111")
                .build();
        Response response = addNewContactPost(wrongToken, contact);
        if (response.code() == 401) {
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessageDto.toString());
            Assert.assertTrue(errorMessageDto.getMessage().toString().contains("strings must contain exactly 2 period characters"));
        }else
            Assert.fail("status code --> " + response.code());
    }

    @Test
    public void addNewContactNegativeTest_duplicateContact_409() throws IOException {
        Contact contact = contactsDtoBeforeTes.getContacts()[0];
        contact.setId("");
        Response response = addNewContactPost(tokenDto, contact);
        if (response.code() == 409) {
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessageDto.toString());
            Assert.assertTrue(errorMessageDto.getMessage().toString().contains("duplicate contact"));
        }else
            Assert.fail("status code --> " + response.code());
    }
}