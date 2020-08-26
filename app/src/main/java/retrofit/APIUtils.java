package retrofit;

public class APIUtils {

    private APIUtils(){

    }

    public static final String API_URL = "http://192.168.1.115:8080/agent/";

    public static MoughataaService getMoughataaService(){
        return RetroFitClient.getMoughataas(API_URL).create(MoughataaService.class);
    }

}
