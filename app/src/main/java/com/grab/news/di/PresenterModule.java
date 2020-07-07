package com.grab.news.di;

import com.grab.news.repository.NewsRepository;
import com.grab.news.ui.feature.news.NewsListViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {
    @Provides
    NewsListViewModel providerShowBookPresenter(NewsRepository newsRepository) {
        return new NewsListViewModel(newsRepository);
    }
}
