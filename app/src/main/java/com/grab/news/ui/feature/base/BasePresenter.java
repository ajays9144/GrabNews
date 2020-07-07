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
}