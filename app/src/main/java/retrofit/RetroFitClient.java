package retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {

    private static Retrofit retrofit = null;
    private static MoughataaService moughataaService;

    public static Retrofit getMoughataas(String url){
        if(retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(url)
                    .addConverterFactory(
                            GsonConverterFactory
                                    .create()
                    )
                    .build();
            moughataaService = retrofit.create(MoughataaService.class);
        }
        return retrofit;
    }

    public static MoughataaService getMoughataaService(){
        return moughataaService;
    }

}
