package com.grab.news.di;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grab.news.network.ApiCallInterface;
import com.grab.news.repository.NewsRepository;
import com.grab.news.utils.AppConstants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsModule {
    /**
     * Provide gson gson.
     *
     * @return the gson
     */
    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.setLenient().create();
    }

    /**
     * Provide retrofit retrofit.
     *
     * @param gson         the gson
     * @param okHttpClient the ok http client
     * @return the retrofit
     */
    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * Gets api call interface.
     *
     * @param retrofit the retrofit
     * @return the api call interface
     */
    @Provides
    @Singleton
    ApiCallInterface getApiCallInterface(Retrofit retrofit) {
        return retrofit.create(ApiCallInterface.class);
    }

    public static final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE(Application application) {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (isOnline(application)) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                } else {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);

                System.out.println("network: " + response.networkResponse());
                System.out.println("cache: " + response.cacheResponse());

                return response;
            }
        };
    }

    /**
     * Gets request header.
     *
     * @return the request header
     */
    @Provides
    @Singleton
    public OkHttpClient getRequestHeader(Application application) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        File httpCacheDirectory = new File(application.getCacheDir(), "offlineCache");
        //10 MB
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder().build();
            return chain.proceed(request);
        }).connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS);
//        httpClient.addNetworkInterceptor(provideCacheInterceptor());
        httpClient.addInterceptor(provideOfflineCacheInterceptor(application));

        /*httpClient.addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE(application));
        httpClient.addNetworkInterceptor(provideCacheInterceptor());*/
        httpClient.cache(cache);

        return httpClient.build();
    }

    public static boolean isOnline(Application application) {
        ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response originalResponse = chain.proceed(request);
                String cacheControl = originalResponse.header("Cache-Control");

                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")) {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + 10)
                            .build();
                } else {
                    return originalResponse;
                }
            }
        };
    }

    private Interceptor provideOfflineCacheInterceptor(Application application) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                /*if (!isOnline(application)) {

                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    request = request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }*/
                if (isOnline(application)) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
                } else {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);

                return response;
            }
        };
    }


    @Provides
    @Singleton
    NewsRepository providerNewsRepository(Application application, ApiCallInterface
            apiCallInterface) {
        return new NewsRepository(application.getApplicationContext(), apiCallInterface);
    }
}
