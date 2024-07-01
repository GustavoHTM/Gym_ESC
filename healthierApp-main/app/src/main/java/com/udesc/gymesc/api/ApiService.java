package com.udesc.gymesc.api;

import com.udesc.gymesc.api.dto.CreateUserRequestDTO;
import com.udesc.gymesc.api.dto.UserInfoDTO;
import com.udesc.gymesc.api.dto.GetDietResponseDTO;
import com.udesc.gymesc.api.dto.GetUserInfoResponseDTO;
import com.udesc.gymesc.api.dto.GetWorkoutResponseDTO;
import com.udesc.gymesc.api.dto.LoginRequestDTO;
import com.udesc.gymesc.api.dto.LoginResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    @GET("/workout-plan")
    Call<GetWorkoutResponseDTO> getCurrentWorkoutPlan();

    @GET("/diet")
    Call<GetDietResponseDTO> getDiet();

    @PUT("workout-plan")
    Call<Void> requestWorkoutPlanUpdate();

    @PUT("/diet")
    Call<Void> requestDietUpdate();

    @PUT("user-info")
    Call<Void> sendForm(@Body UserInfoDTO userInfoDTO);

    @GET("user-info")
    Call<GetUserInfoResponseDTO> getInfo();

    @POST("user/login")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO loginRequest);

    @POST("/user")
    Call<Void> registry(@Body CreateUserRequestDTO createUserDTO);
}