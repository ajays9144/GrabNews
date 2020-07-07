package com.grab.news.di;

import com.grab.news.AppController;
import com.grab.news.ui.feature.news.NewsListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, UtilsModule.class, PresenterModule.class, ViewModelFactoryModule.class, ViewModelModule.class})
public interface ManagerComponent {
    void inject(AppController appController);

    void inject(NewsListFragment newsListFragment);
}
