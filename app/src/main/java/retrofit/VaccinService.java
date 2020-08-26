package retrofit;

import java.util.List;

import extra.Vaccin;
import retrofit2.Call;
import retrofit2.http.GET;

public interface VaccinService {

    @GET("allVaccins/")
    Call<List<Vaccin>> getVaccins();

}
