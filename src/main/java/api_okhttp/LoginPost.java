package api_okhttp;

import dto.UserDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import Utils.BaseAPI;

import java.io.IOException;

import static Utils.PropertiesReader.getProperty;

public class LoginPost implements BaseAPI {
    public static void main(String[] args) throws IOException {
        UserDto qa_user = UserDto.builder()
                .username(getProperty("login.properties","email"))
                .password(getProperty("login.properties","password"))
                .build();
        Response response = login(qa_user);
        System.out.println(response.isSuccessful());
        System.out.println(response.body().string());
    }

    public static Response login(UserDto user){
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build()
                ;
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            //response.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("created exception POST LOGIN");
            return null;
        }
        return response;
    }
}