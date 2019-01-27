package com.zewail.dakrory.zccourses.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by A7med Al-Dakrory on 1/16/2019.
 */

public class UserData {


    @SerializedName("id")
    public Integer id;

    @SerializedName("fullName")
    public String fullName;

    /*
     * A7med Dakrory
     * 0 email
     * 1 facebook
     * 2 linkedin
     * 3 friends
     * 4 newsletter
     */
    @SerializedName("whereYknowAboutUs")
    public Integer whereYknowAboutUs;


    @SerializedName("email")
    public String email;


    @SerializedName("password")
    public String password;


    @SerializedName("mobile")
    public String mobile;


    @SerializedName("age")
    public String age;


    @SerializedName("nationalId")
    public String nationalId;

    /*
     * A7med Dakrory
     * 0 Mr
     * 1 Ms
     * 2 Eng
     * 3 Dr
     */
    @SerializedName("prefix")
    public Integer prefix;

    /*
     * 0 male
     * 1 female
     */
    @SerializedName("gender")
    public Integer gender;


    @SerializedName("university")
    public String university;

    @SerializedName("faculty")
    public String faculty;

    @SerializedName("major")
    public String major;

    @SerializedName("study_year")
    public String study_year;


    @SerializedName("graduation_experiences")
    public Integer graduation_experiences;

    /*
     * A7med Dakrory
     * 0 for yes
     * 1 for not
     */
    @SerializedName("zewailianeOrNot")
    public Integer zewailianeOrNot;

    @SerializedName("work")
    public String work;

    @SerializedName("residenceGovernorate")
    public String residenceGovernorate;

    @SerializedName("homeGovernorate")
    public String homeGovernorate;


    @SerializedName("active")
    public Integer active;

    @SerializedName("image")
    public String image;


}
