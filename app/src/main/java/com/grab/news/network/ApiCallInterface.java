package com.grab.news.network;

import com.grab.news.models.StandardResponse;
import com.grab.news.models.TopNewsModel;
import com.grab.news.utils.AppConstants;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiCallInterface {

    @GET(AppConstants.U_TOP_HEADLINES)
    Observable<StandardResponse<List<TopNewsModel>>> getTopNews(@QueryMap HashMap<String, String> parse);

}
