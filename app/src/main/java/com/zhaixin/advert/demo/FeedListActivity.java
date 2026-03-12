package com.zhaixin.advert.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lockin.loock.R;
import com.zhaixin.advert.FeedAd;
import com.zhaixin.advert.FeedAdData;
import com.zhaixin.advert.Platform;
import com.zhaixin.listener.FeedLoadListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class FeedListActivity extends AppCompatActivity {
    private int mPage;
    private boolean mIsLoading;

    private SimpleAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE && !mIsLoading
                        && !recyclerView.canScrollVertically(1)) {
                    refreshOrLoad(mPage + 1);
                }
            }
        });
        DividerItemDecoration divider = new DividerItemDecoration(
                recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        adapter = new SimpleAdapter();
        recyclerView.setAdapter(adapter);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(()
                -> refreshOrLoad(1));
        swipeRefresh.setRefreshing(true);
        refreshOrLoad(1);
    }

    private void refreshOrLoad(int page) {
        mIsLoading = true;
        if (!swipeRefresh.isRefreshing()) {
            swipeRefresh.setEnabled(false);
        }
        FeedAd advert = new FeedAd(getString(R.string.posid_feed));
        if (getIntent().hasExtra("platforms")) {
            advert.enableDebug((Platform[]) getIntent().getSerializableExtra("platforms"));
        }
        advert.setAdLoadListener(new FeedLoadListener() {
            @Override
            public void onLoad(List<FeedAdData> list) {
                updateAdapter(page, list);
            }

            @Override
            public void onNoAd(int code, String message) {
                updateAdapter(page, Collections.emptyList());
            }
        });
        advert.load(this);
    }

    private void updateAdapter(int page, List<FeedAdData> ads) {
        mPage = page;
        int count = (page - 1) * 20;
        List<String> fakeData = new ArrayList<>();
        for (int i = count + 1; i <= count + 20; i++) {
            fakeData.add("Item " + i);
        }
        if (page <= 1) adapter.clearData();
        adapter.addData(fakeData, ads);
        mIsLoading = false;
        swipeRefresh.setEnabled(true);
        swipeRefresh.setRefreshing(false);
    }

    public static class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int VIEW_TYPE_DATA_ITEM = 1;
        private static final int VIEW_TYPE_ADVERT_ITEM = 2;
        private final List<Object> mData = new ArrayList<>();
        private final ViewGroup.LayoutParams mParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        @Override
        public int getItemViewType(int position) {
            if (mData.get(position) instanceof String)
                return VIEW_TYPE_DATA_ITEM;
            else
                return VIEW_TYPE_ADVERT_ITEM;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_DATA_ITEM) {
                View view = LayoutInflater.from(parent.getContext()).inflate(android
                        .R.layout.simple_list_item_1, parent, false);
                return new ViewHolder(view);
            } else {
                FrameLayout container = new FrameLayout(parent.getContext());
                return new AdvertHolder(container);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewHolder) {
                ((ViewHolder) holder).textView.setText(mData.get(position).toString());
                return;
            }
            if (holder instanceof AdvertHolder) {
                AdvertHolder advertHolder = (AdvertHolder) holder;
                advertHolder.container.removeAllViews();
                View advert = ((FeedAdData) mData.get(position)).getAdView();
                if (advert != null) {
                    if (advert.getParent() != null)
                        ((ViewGroup) advert.getParent()).removeView(advert);
                    advertHolder.container.addView(advert, mParams);
                }
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }

        public static class AdvertHolder extends RecyclerView.ViewHolder {
            FrameLayout container;

            public AdvertHolder(FrameLayout container) {
                super(container);
                this.container = container;
            }
        }

        public void clearData() {
            int preSize = getItemCount();
            mData.clear();
            notifyItemRangeRemoved(0, preSize);
        }

        public void addData(List<String> newData, List<FeedAdData> adverts) {
            if (adverts.isEmpty())
                return;
            int preSize = getItemCount();
            int perSpan = newData.size() / adverts.size();
            List<Object> combineList = new ArrayList<>(newData);
            for (int i = 0; i < adverts.size(); i++) {
                adverts.get(i).render();
                combineList.add((i + 1) * perSpan, adverts.get(i));
            }
            mData.addAll(combineList);
            notifyItemRangeInserted(preSize, combineList.size());
        }
    }
}
