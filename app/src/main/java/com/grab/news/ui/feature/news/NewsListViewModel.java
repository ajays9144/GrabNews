package com.grab.news.ui.feature.news;

import com.grab.news.models.StandardResponse;
import com.grab.news.models.TopNewsModel;
import com.grab.news.repository.NewsRepository;
import com.grab.news.ui.feature.base.BaseViewModel;
import com.grab.news.utils.AppConstants;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsListViewModel extends BaseViewModel<NewsListView> {

    private NewsRepository newsRepository;

    @Inject
    public NewsListViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void getTopNewsStories() {
        HashMap<String, String> parse = new HashMap<>();
        parse.put(AppConstants.K_COUNTRY, AppConstants.NEWS_COUNTRY);
        parse.put(AppConstants.K_API_KEY, AppConstants.NEWS_API_KEY);
        getCompositeDisposable().add(newsRepository.getTopNews(parse).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<StandardResponse<List<TopNewsModel>>>() {
                    @Override
                    public void onNext(StandardResponse<List<TopNewsModel>> listStandardResponse) {
                        if (getView() != null) {
                            getView().showLoading(false);
                            getView().getNewsTopStories(listStandardResponse.getResponse());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().showLoading(false);
                            getView().showError(e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    protected void onStart() {
                        super.onStart();
                        if (getView() != null) {
                            getView().showLoading(true);
                        }
                    }
                }));
    }
}
