package com.grab.news.ui.feature.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.grab.news.AppController;
import com.grab.news.di.ManagerComponent;


public abstract class BaseActivityViewModel<M extends Contract.Presenter> extends AppCompatActivity {

    private M presenter;

    /**
     * Inject presenter m.
     *
     * @param managerComponent the manager component
     * @return the m
     */
    public abstract M injectPresenter(ManagerComponent managerComponent);

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.presenter = injectPresenter(((AppController) getApplicationContext()).getManagerComponent());
        M m = this.presenter;
        if (m != null) {
            try {
                m.attachView((Contract.View) this);
            } catch (ClassCastException unused) {
                if (!(this.presenter instanceof NoPresenter)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(getClass().getSimpleName());
                    sb.append(" must implement View subclass as declared in ");
                    sb.append(this.presenter.getClass().getSimpleName());
                    throw new IllegalAccessError(sb.toString());
                }
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getClass().getSimpleName());
            sb2.append(" should return a valid view model");
            throw new IllegalStateException(sb2.toString());
        }
    }

    public void onDestroy() {
        super.onDestroy();
        M m = this.presenter;
        if (m != null) {
            m.detachView();
        }
        this.presenter = null;
    }

    /**
     * Gets presenter.
     *
     * @return the presenter
     */
    public M getPresenter() {
        return this.presenter;
    }
}