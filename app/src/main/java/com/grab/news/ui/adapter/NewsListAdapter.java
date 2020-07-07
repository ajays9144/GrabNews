package com.grab.news.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.grab.news.R;
import com.grab.news.models.TopNewsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {

    private Context context;
    private List<TopNewsModel> topNewsModels;
    private Callback callback;

    public NewsListAdapter(Context context, List<TopNewsModel> topNewsModels) {
        this.context = context;
        this.topNewsModels = topNewsModels;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public NewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListViewHolder holder, int position) {
        try {
            TopNewsModel topNewsModel = topNewsModels.get(position);

            holder.textNewsTitle.setText(topNewsModel.getTitle());
            holder.textNewsDescription.setText(topNewsModel.getDescription());
//            Glide.with(context).load(topNewsModel.getUrlToImage()).placeholder(R.color.colorAccent).error(R.color.colorAccent).into(holder.imageNewsViews);
        } catch (Exception e) {
            Log.e("Exception: ", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return topNewsModels.size();
    }

    public class NewsListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_news_view)
        ImageView imageNewsViews;
        @BindView(R.id.text_news_title)
        TextView textNewsTitle;
        @BindView(R.id.text_news_description)
        TextView textNewsDescription;

        public NewsListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callback != null) {
                        callback.onPositionSelect(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface Callback {
        void onPositionSelect(int position);
    }
}
