package com.mrt.newsapp;

import android.content.Context;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mrt.newsapp.Models.NewsApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager { //tum api cagirmalarini kontrol etmek icin
    Context context;

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/") //retrofit ile newsapi linkini
            //buraya yazdim
            .addConverterFactory(GsonConverterFactory.create()).build();

    public void getNewsHeadlines(OnFetchDataListener listener, String category, String query){ //api call ları yonetmek icin
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);

        //ulke degistirmek icin tr yi us veya ne istersen o yap listesi newsapi sayfasinda
        Call<NewsApiResponse> call = callNewsApi.callHeadlines("tr", category, query, context.getString(R.string.api_key));

        try{
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if(!response.isSuccessful()){ //api response basarisiz ise
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    }

                    listener.onFetchData(response.body().getArticles(), response.message());
                    //onFetchData da headlines ve message lar var
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Istek hatali oldu.");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public RequestManager(Context context) {
        this.context = context;
    }

    public interface CallNewsApi {
        @GET("top-headlines") //en guncel basliklari almak icin yazildi (apikey aldigim linkte de var)
        Call<NewsApiResponse> callHeadlines( // request parametlerini almak icin query olusturdum
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String api_key
        );
    }
}
