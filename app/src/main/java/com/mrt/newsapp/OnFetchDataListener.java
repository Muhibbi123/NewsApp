package com.mrt.newsapp;

import com.mrt.newsapp.Models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse>{ //main activity den gelen istekleri yönetmek icin
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);

}
