package com.mrt.newsapp;

import com.mrt.newsapp.Models.NewsHeadlines;

public interface SelectListener { //bir onclicklistener yarattim recycler view yeni activity icinde olsun diye
    void OnNewsClicked(NewsHeadlines headlines);
}
