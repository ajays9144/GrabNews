package com.grab.news.ui.feature.news;

import com.grab.news.models.TopNewsModel;
import com.grab.news.ui.feature.base.Contract;

import java.util.List;

public interface NewsListView extends Contract.View
{
    void getNewsTopStories(List<TopNewsModel> topNewsModels);
}
