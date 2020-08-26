package retrofit;

import extra.AppUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthService {

    @GET("authentification/{login}/{password}")
    Call<AppUser> authenticateUser(@Path("login") String login, @Path("password") String password);
}
