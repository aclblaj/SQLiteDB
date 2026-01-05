package ro.utbv.sqlitedb;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StudentApiService {
    @GET("adresa")
    Call<Address> getStudentAddress(@Query("zc") String zipCode);

    @POST("student")
    Call<String> createStudent(@Body Student student);
}
