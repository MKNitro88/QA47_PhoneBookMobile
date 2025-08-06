package api_okhttp;

import dto.Contact;
import dto.TokenDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import Utils.BaseAPI;

import java.io.IOException;

public class AddNewContactPost implements BaseAPI {
    public static Response addNewContactPost(TokenDto token, Contact contact) {
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader("Authorization", token.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("created exception GET contacts");
            return null;
        }

    }

}