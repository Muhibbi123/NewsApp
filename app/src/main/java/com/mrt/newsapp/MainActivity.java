package com.mrt.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.mrt.newsapp.Models.NewsApiResponse;
import com.mrt.newsapp.Models.NewsHeadlines;

import java.util.List;

//genel olarak yaptigim baslik, kaynak ve yazar yazildi
//ilk once yaptigim newsapi de hesap actim ordan api key aldik
// sonra strings.xml sayfasina ordan aldigim newsapi key i attim
//apikey i aldigim yerde birkac farkli ulke var ordan tr yi sectim
//category lerin turkcesini yazamiyorum cunku onlar orada ingilizce bulunuyor
//turkce yazinca sikinti veriyor
//postman actim orda SIRASIYLA aldik sirasini kacirinca hata veriyor
//layout icine yeni xml sayfasi actik headline_list_items burada baslik ve kaynak adini aldik
//ilk gorunecek olan haber kaynaginin görüntüleri orada yapildi

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1, b2, b3, b4, b5, b6, b7; //butonlara basilinca ona ozel bir seyler cikmasi icin
    SearchView searchView; //searchbar icin


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("RemoteViewLayout") RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_main);
        remoteViews.setTextViewText(R.id.btn_1, "İsletme");

        searchView = findViewById(R.id.search_view); //seachbar tanimlandi

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { //sadece arandiginda o habere gideceginden burasi onemli

                dialog.setTitle("Şu konu hakkında yeni haberler aranıyor:  " + query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener, "general", query); //query yazdik cunku yazdigimizi bulsun diye
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        dialog = new ProgressDialog(this);//uygulamayi acinca pop up seklinde ciksin diye
        dialog.setTitle("Yeni Haberler Aranıyor");
        dialog.show();

        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this); //clicklistener lari da yazdim veriyi alsin diye

        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);

        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);

        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);

        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);

        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);

        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, "general", null);

    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()) {
                Toast.makeText(MainActivity.this, "Bir şey Bulunamadı.", Toast.LENGTH_SHORT).show();
                //searchbar da bi sey aradik ama bulamadi bunu yazsin
            }
            else {
                showNews(list);
                dialog.dismiss();//pop up sadece uygulama acilinca acilsin istedigim icin burda
                //dismissledim
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list) {

        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
        //internet izinlerini de verdim manifest e

    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {

        startActivity(new Intent(MainActivity.this, DetailsActivity.class) //yeni aktivite olusturdum
                //ordan intent ile veriyi cekecem
                .putExtra("data", headlines)); //eger models package icindekileri serializable yapmazsam
                //HATA VERIYOR headlines nesnesini yeni bir aktiviteye gecirdik
    }

    @Override
    public void onClick(View v) {

        Button button = (Button) v;
        String category = button.getText().toString();

        switch (category){
            case "piyasa":
                category = "business";
                break;

            case "magazin":
                category = "entertainment";
                break;

            case "genel":
                category = "general";
                break;

            case "sağlık":
                category = "health";
                break;

            case "bilim":
                category = "science";
                break;

            case "spor":
                category = "sport";
                break;

            case "teknoloji":
                category = "technology";
                break;

        }
        dialog.setTitle("Şu Konu Hakkında Yeni Haberler Bulunuyor: " + category);
        dialog.show();

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener, category, null);

    }
}