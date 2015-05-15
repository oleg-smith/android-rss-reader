package com.example.rss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.rss.util.FeedUtil;

import java.util.ArrayList;
import java.util.List;

public class RssItemListActivity extends Activity {

    public static final String DISPLAY_RSS_ITEM = "displayRssItem";

    public static RssItemDto selectedRssItem = null;

    private String feedUrl = "";
    private ListView rssListView = null;

    private ArrayList<RssItemDto> rssItems = new ArrayList<>();

    private ArrayAdapter<RssItemDto> itemListAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        final TextView rssURLTV = (TextView) findViewById(R.id.rssURL);

        Button fetchRss = (Button) findViewById(R.id.fetchRss);

        fetchRss.setOnClickListener(new View.OnClickListener() {

            //@Override
            public void onClick(View v) {
                feedUrl = rssURLTV.getText().toString();
                itemListAdapter.notifyDataSetChanged();
                refreshRssList();
            }
        });

        rssListView = (ListView) findViewById(R.id.rssListView);

        rssListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View view, int index, long arg3) {
                selectedRssItem = rssItems.get(index);

                Intent intent = new Intent(DISPLAY_RSS_ITEM);
                startActivity(intent);
            }
        });

        itemListAdapter = new ArrayAdapter<>(this, R.layout.list_item, rssItems);

        rssListView.setAdapter(itemListAdapter);
        feedUrl = rssURLTV.getText().toString();

        refreshRssList();
    }

    private void refreshRssList() {

        List<RssItemDto> newItems = FeedUtil.downloadRssItems(feedUrl);

        rssItems.clear();
        rssItems.addAll(newItems);

        itemListAdapter.notifyDataSetChanged();
    }

}