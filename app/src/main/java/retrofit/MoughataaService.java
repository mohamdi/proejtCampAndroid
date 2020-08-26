package retrofit;

import java.util.List;

import extra.Moughataa;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MoughataaService {

    @GET("allMoughataas/")
    Call<List<Moughataa>> getMoughataas();


}
