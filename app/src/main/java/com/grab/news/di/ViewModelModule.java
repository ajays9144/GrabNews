package com.grab.news.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.grab.news.ui.feature.news.NewsListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @ViewModelKey(NewsListViewModel.class)
    @Binds
    @IntoMap
    @NonNull
    public abstract ViewModel bindNotArrivedViewModel(@NonNull NewsListViewModel loginViewModel);

}
