package com.grab.news.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StoriesModel {

    @SerializedName("articles")
    @Expose
    private ArrayList<TopNewsModel> topNewsModels;

    public ArrayList<TopNewsModel> getTopNewsModels() {
        return topNewsModels;
    }
}
