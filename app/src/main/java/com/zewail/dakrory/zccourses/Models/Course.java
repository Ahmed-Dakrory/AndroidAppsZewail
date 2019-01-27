package com.zewail.dakrory.zccourses.Models;


import com.google.gson.annotations.SerializedName;


/**
 * Created by A7med Al-Dakrory on 1/17/2019.
 */

public class Course {

    @SerializedName("id")
    private  Integer id;

    @SerializedName("name")
    private  String name;

    @SerializedName("description")
    private  String description;

    @SerializedName("price")
    private  Integer price;

    @SerializedName("image")
    private  String image;

    @SerializedName("idProgram")
    private  Integer idProgram;

    @SerializedName("categories")
    private  String categories;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(Integer idProgram) {
        this.idProgram = idProgram;
    }

    public String getCategories() {
        return categories;
    }



}
