package retrofit;

import java.util.List;

import extra.Campagne;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CampagneService {

    @GET("allCampagnes/")
    Call<List<Campagne>> getCampagnes();
}
