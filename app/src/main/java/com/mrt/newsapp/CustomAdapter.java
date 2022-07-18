package com.mrt.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrt.newsapp.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<NewsHeadlines> headlines;
    private SelectListener listener; //SelectListener interface inde yaptigimiz seyi tanimladik


    public CustomAdapter(Context context, List<NewsHeadlines> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items,
                parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.text_title.setText(headlines.get(position).getTitle());
        holder.text_source.setText(headlines.get(position).getSource().getName());

        if(headlines.get(position).getUrlToImage()!=null){ //picasso yu implement ettik gradle a
            //picassoyla bi cesit onbellege attim resimleri
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.img_headline);
            //resim de url icinde oldugu icin url den resime cevirdim ki gorelim
            //burdan retrofit i implement ettim gradle a
            //retrofit icin gson diye bir sey daha implement ettim
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnNewsClicked(headlines.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {

        return headlines.size();
    }
}
