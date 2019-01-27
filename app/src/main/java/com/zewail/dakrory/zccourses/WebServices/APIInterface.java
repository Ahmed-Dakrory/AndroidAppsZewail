package com.zewail.dakrory.zccourses.WebServices;

import com.zewail.dakrory.zccourses.Models.Course;
import com.zewail.dakrory.zccourses.Models.CourseReg;
import com.zewail.dakrory.zccourses.Models.Program;
import com.zewail.dakrory.zccourses.Models.UserData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by A7med Al-Dakrory on 1/16/2019.
 */

public interface APIInterface {


    @FormUrlEncoded
    @POST("Api/userData?")
    Call<ResponseBody> getUserDataById(@Field("id") int id);

    @FormUrlEncoded
    @POST("Api/course?")
    Call<Course> getCourseById(@Field("id") int id);


    @FormUrlEncoded
    @POST("Api/coursesbyProgramId?")
    Call<List<Course>> getCoursesbyProgramId(@Field("id") int id);

    @FormUrlEncoded
    @POST("Api/program?")
    Call<Program> getProgrambyId(@Field("id") int id);

    @FormUrlEncoded
    @POST("Api/userData?")
    Call<UserData> getUserbyEmailAPassword(@Field("email") String email,@Field("password") String password);

    @FormUrlEncoded
    @POST("Api/setcourseRegData?")
    Call<CourseReg> setcourseRegData(@Field("idUser") int idUser,@Field("idCourse") int idCourse);


    @FormUrlEncoded
    @POST("Api/courseRegData?")
    Call<CourseReg> getcourseRegData(@Field("idUser") int idUser,@Field("idCourse") int idCourse);



    @GET("Api/programs?")
    Call<List<Program>> getAllPrograms();
}
