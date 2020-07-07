package com.grab.news.ui.feature.base;

import android.util.Log;

import androidx.lifecycle.ViewModel;


import com.grab.news.di.UtilsModule;
import com.grab.news.models.StandardResponse;

import java.lang.annotation.Annotation;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.grab.news.AppController.API_STATUS_CODE_LOCAL_ERROR;


public class BaseViewModel<V extends Contract.View> extends ViewModel {
    public CompositeDisposable compositeDisposable;

    private V view;

    public final V getView() {
        return this.view;
    }

    public void setView(V view) {
        this.view = view;
    }

    public final CompositeDisposable getCompositeDisposable() {
        CompositeDisposable compositeDisposable2 = this.compositeDisposable;
        if (compositeDisposable2 == null) {
            Log.e(BaseViewModel.class.getSimpleName(), "missing");
        }
        return compositeDisposable2;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public final void attachView(V v) {
        this.view = v;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        CompositeDisposable compositeDisposable2 = this.compositeDisposable;
        if (compositeDisposable2 == null) {
            Log.e(BaseViewModel.class.getSimpleName(), "missing");
        }
        compositeDisposable2.clear();
        this.view = null;
    }
}
