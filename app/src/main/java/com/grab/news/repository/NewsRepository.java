package com.grab.news.repository;

import android.content.Context;

import com.grab.news.models.StandardResponse;
import com.grab.news.models.StoriesModel;
import com.grab.news.models.TopNewsModel;
import com.grab.news.network.ApiCallInterface;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

public class NewsRepository extends BaseRepository {
    private final ApiCallInterface apiCallInterface;

    /**
     * Instantiates a new Base repository.
     *
     * @param context the context
     */
    public NewsRepository(Context context, ApiCallInterface apiCallInterface) {
        super(context);
        this.apiCallInterface = apiCallInterface;
    }

    public Observable<StandardResponse<List<TopNewsModel>>> getTopNews(HashMap<String, String> parse) {
        return apiCallInterface.getTopNews(parse);
    }
}
