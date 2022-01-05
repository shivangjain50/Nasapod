package au.common.nasapod.repository;

import au.common.nasapod.model.APODModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APODService {

    @GET("apod")
    Call<APODModel> getAPOD(@Query("api_key") String apiKey, @Query("date") String date);
}
