package api_okhttp;

import dto.TokenDto;
import okhttp3.Request;
import okhttp3.Response;
import Utils.BaseAPI;

import java.io.IOException;

public class DeleteContactById implements BaseAPI {

    public static Response deleteContactById(TokenDto tokenDto, String id){
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL + "/"+id)
                .addHeader("Authorization", tokenDto.getToken())
                .delete()
                .build()
                ;
        try{
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            return response;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}