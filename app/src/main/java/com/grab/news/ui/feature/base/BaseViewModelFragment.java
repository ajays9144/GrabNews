package com.grab.news.ui.feature.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.grab.news.AppController;
import com.grab.news.di.ManagerComponent;

import java.util.HashMap;

import butterknife.internal.Utils;

public abstract class BaseViewModelFragment<V extends Contract.View> extends Fragment implements MvpView {

    private HashMap _$_findViewCache;
    private ProgressDialog mProgressDialog;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public android.view.View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        android.view.View view = (android.view.View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            android.view.View view2 = getView();
            if (view2 == null) {
                return null;
            }
            view = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), view);
        }
        return view;
    }

    @NonNull
    public abstract BaseViewModel<V> initializeViewModel(@NonNull ManagerComponent managerComponent);

    public void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        Context applicationContext = activity != null ? activity.getApplicationContext() : null;
        if (applicationContext != null) {
            ManagerComponent managerComponent = ((AppController) applicationContext).getManagerComponent();
            BaseViewModel initializeViewModel = initializeViewModel(managerComponent);
            try {
                initializeViewModel.attachView((Contract.View) this);
            } catch (Exception unused) {
                StringBuilder sb = new StringBuilder();
                sb.append(getClass().getSimpleName());
                sb.append(" must implement View subclass as declared in ");
                sb.append(initializeViewModel.getClass().getSimpleName());
                throw new IllegalStateException(sb.toString());
            }
        } else {
            throw new ClassCastException("null cannot be cast to non-null type com.ratted.sitter.ui.feature.base.BaseApplication");
        }
    }

    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override
    public void showLoading() {
        hideKeyboard();
//        mProgressDialog = Utils.showLoadingDialog(getContext());
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void onError(String message) {
        showMessage(message);
    }

    @Override
    public void showMessage(String message) {
        showMessageDialog(message);
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

    private void showMessageDialog(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void showToastNow(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideKeyboard() {
        if (getActivity() != null) {
            android.view.View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    public void setupUI(android.view.View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new android.view.View.OnTouchListener() {
                public boolean onTouch(android.view.View v, MotionEvent event) {
                    hideKeyboard();
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                android.view.View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
