package com.grab.news.ui.feature.base;

import androidx.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V extends Contract.View> implements Contract.Presenter<V> {
    private CompositeDisposable compositeDisposable;
    private V view;

    @CallSuper
    public void attachView(V v) {
        this.view = v;
        this.compositeDisposable = new CompositeDisposable();
    }

    @CallSuper
    public void detachView() {
        V v = this.view;
        if (v != null && (v instanceof Contract.PresenterView)) {
            ((Contract.PresenterView) v).clearPresenter();
        }
        this.view = null;
        this.compositeDisposable.clear();
        this.compositeDisposable = null;
    }

    public final boolean isViewAttached() {
        return this.view != null;
    }

    public final V getView() {
        return this.view;
    }

    /* access modifiers changed from: protected */
    public CompositeDisposable getCompositeDisposable() {
        return this.compositeDisposable;
    }

    /*public StandardResponse parseError(Throwable e) {
        try {
            return parseError(((HttpException) e).response());
        } catch (ClassCastException e1) {
            return handleApiError(e);
        }
    }

    public StandardResponse parseError(Response<?> response) {
        ManagerModule managerModule = new ManagerModule();
        Converter<ResponseBody, StandardResponse> converter = managerModule.provideRetrofit(managerModule.provideGson(),
                managerModule.getRequestHeader()).responseBodyConverter(StandardResponse.class, new Annotation[0]);
        StandardResponse error;
        try {
            error = converter.convert(response.errorBody());
        } catch (Exception e) {
            return StandardResponse.EMPTY_VIEW;
        }
        return error;
    }

    public StandardResponse handleApiError(Throwable error) {
        if (error instanceof UnknownHostException) {
            return StandardResponse.NO_INTERNET_CONNECTION;
        } else if (error instanceof HttpException) {
            switch (((HttpException) error).code()) {
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    return StandardResponse.UNAUTH_USER;
                case HttpsURLConnection.HTTP_FORBIDDEN:
                    return StandardResponse.FORBIDDEN_USER;
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                    return StandardResponse.INTERNAL_SERVER;
                case HttpsURLConnection.HTTP_BAD_REQUEST:
                    return StandardResponse.BAD_REQUEST;
                case API_STATUS_CODE_LOCAL_ERROR:
                    return StandardResponse.NO_INTERNET_CONNECTION;
                default:
                    return StandardResponse.EMPTY_VIEW;

            }
        }
        return StandardResponse.EMPTY_VIEW;
    }*/
}