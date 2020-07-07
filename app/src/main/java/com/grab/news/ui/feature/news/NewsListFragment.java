package com.grab.news.ui.feature.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.grab.news.R;
import com.grab.news.di.ManagerComponent;
import com.grab.news.models.TopNewsModel;
import com.grab.news.ui.adapter.NewsListAdapter;
import com.grab.news.ui.feature.base.BaseViewModel;
import com.grab.news.ui.feature.base.BaseViewModelFragment;
import com.grab.news.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewsListFragment extends BaseViewModelFragment<NewsListView> implements NewsListView, NewsListAdapter.Callback {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    NewsListViewModel viewModel;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.view_switcher)
    ViewSwitcher viewSwitcher;
    @BindView(R.id.text_empty_view)
    TextView textEmptyView;

    private Unbinder unbinder;
    private NewsListAdapter newsListAdapter;
    private ArrayList<TopNewsModel> topNewsModels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        unbinder = ButterKnife.bind(this, view);

        topNewsModels = new ArrayList<>();

        initAdapter();

        viewModel.getTopNewsStories();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getTopNewsStories();
            }
        });
    }

    private void initAdapter() {
        newsListAdapter = new NewsListAdapter(getContext(), topNewsModels);
        recyclerView.setAdapter(newsListAdapter);
        newsListAdapter.setCallback(this);
    }

    @NonNull
    @Override
    public BaseViewModel<NewsListView> initializeViewModel(@NonNull ManagerComponent managerComponent) {
        managerComponent.inject(this);
        ViewModel viewModel1 = ViewModelProviders.of(this, viewModelFactory).get(NewsListViewModel.class);
        viewModel = (NewsListViewModel) viewModel1;
        return viewModel;
    }

    @Override
    public void getNewsTopStories(List<TopNewsModel> topNewsModels) {
        this.topNewsModels.clear();
        this.topNewsModels.addAll(topNewsModels);
        newsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        showMessage(error);
    }

    @Override
    public void showLoading(boolean state) {
        if (state) viewSwitcher.setDisplayedChild(0);
        else {
            viewSwitcher.setDisplayedChild(1);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showFailure() {
        showMessage(R.string.error_something_went_wrong);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onPositionSelect(int position) {
        String newsDetailURL = topNewsModels.get(position).getUrl();
        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        intent.putExtra(AppConstants.News_DETAILS_URL, newsDetailURL);
        startActivity(intent);
    }
}
