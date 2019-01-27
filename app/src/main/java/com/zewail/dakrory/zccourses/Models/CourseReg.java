package com.zewail.dakrory.zccourses.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by A7med Al-Dakrory on 1/16/2019.
 */

public class CourseReg {

    @SerializedName("id")
    public Integer id;
    @SerializedName("courseId")
    public Integer courseId;
    @SerializedName("date")
    public String date;
    @SerializedName("studentId")
    public Integer studentId;
}
