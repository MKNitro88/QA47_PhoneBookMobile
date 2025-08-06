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
import static Utils.ContactFactory.*;
import static api_okhttp.UpdateContactPut.*;

public class UpdateContactTests {
    UserDto qa_user = UserDto.builder()
            .username("qa_user_qwerty@mail.com")
            .password("Password123!")
            .build();
    ContactsDto contactsDtoBeforeTest;
    TokenDto tokenDto;

    @BeforeMethod
    public void loginGetAllContact() throws IOException {
        Response response = login(qa_user);
        tokenDto = GSON.fromJson(response.body().string(), TokenDto.class);
        Response response1 = getAllUserContacts(tokenDto);
        contactsDtoBeforeTest = GSON.fromJson(response1.body().string(), ContactsDto.class);
    }

    @Test
    public void updateContactPositiveTest() throws IOException {
        Contact newContact = createPositiveContact();
        Contact contact = contactsDtoBeforeTest.getContacts()[0];
        newContact.setId(contact.getId());
        Response response = updateContactPut(tokenDto, newContact);
        if(response.code() == 200){
            ResponseMessageDto responseMessageDto = GSON.fromJson(response.body().string(), ResponseMessageDto.class);
            System.out.println(responseMessageDto.toString());
            Assert.assertTrue(responseMessageDto.getMessage().contains("Contact was updated"));
        }else
            Assert.fail("status code --> " + response.code());
    }

    @Test
    public void updateContactNegativeTest_wrongId_404() throws IOException {
        Contact newContact = createPositiveContact();
        newContact.setId("111111111111111111");
        Response response = updateContactPut(tokenDto, newContact);
        if(response.code() == 404){
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessageDto.toString());
            //Assert.assertTrue(errorMessageDto.getMessage().contains("Contact was updated"));
        }else
            Assert.fail("status code --> " + response.code());
    }

    @Test
    public void updateContactNegativeTest_wrongContact_400() throws IOException {
        Contact newContact = createNegativeContact_wrongName("                   ");
        Contact contact = contactsDtoBeforeTest.getContacts()[0];
        newContact.setId(contact.getId());
        Response response = updateContactPut(tokenDto, newContact);
        if(response.code() == 400){
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessageDto.toString());
            Assert.assertTrue(errorMessageDto.getMessage().toString().contains("must not be blank"));
        }else
            Assert.fail("status code --> " + response.code());
    }

    @Test
    public void updateContactNegativeTest_tokenAnotherUser_404() throws IOException {
        Contact newContact = createPositiveContact();
        Contact contact = contactsDtoBeforeTest.getContacts()[0];
        newContact.setId(contact.getId());
        TokenDto wrongToken = TokenDto.builder()
                .token("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoicWFfd3JvbmdfdG9rZW5AbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTc1NDg0MDY5MSwiaWF0IjoxNzU0MjQwNjkxfQ.VohiB_9ssWCfzBK-UB23MtSR4rQzAUglOahccww156E")
                .build();
        Response response = updateContactPut(wrongToken, newContact);
        if(response.code() == 404){
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessageDto.toString());
            Assert.assertTrue(errorMessageDto.getMessage().toString().contains("must not be blank"));
        }else
            Assert.fail("status code --> " + response.code());
    }
}