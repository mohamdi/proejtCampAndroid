package retrofit;

import java.util.List;

import extra.Moughataa;
import extra.Wilaya;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WilayaService {

    @GET("allWilayas/")
    Call<List<Wilaya>> getWilayas();


}
