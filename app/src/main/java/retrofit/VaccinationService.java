package retrofit;

import extra.Vaccination;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VaccinationService {

    @POST("addVaccination/")
    Call<Vaccination> addVaccination(@Body Vaccination vaccination);

}
