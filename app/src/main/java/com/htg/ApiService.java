package com.htg;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("android_login.php") // relative to your BASE_URL
    Call<LoginResponse> loginUser(@Body LoginRequest request);
}
