package com.example.administrator.loadmore;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoadMoreListView.ILoadMoreListener {

    private Handler handler = new Handler();
    private List<String> data = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private LoadMoreListView loadMoreListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMoreListView = (LoadMoreListView) findViewById(R.id.lv_load_more);
        for (int i = 1; i <= 10; i++) {
            data.add("数据-->" + i);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        loadMoreListView.setAdapter(adapter);
        loadMoreListView.setLoadMoreListener(this);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMoreData();
                loadMoreListView.loadCompleted();
                adapter.notifyDataSetChanged();
            }
        }, 5000);
    }

    private void loadMoreData() {
        for (int i = data.size()+1,j=i+10; i < j; i++) {
            data.add("数据-->" + i);
        }
    }
}
