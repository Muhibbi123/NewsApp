package com.mrt.newsapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {  //haber basliklari icin
    TextView text_title, text_source;
    ImageView img_headline;
    CardView cardView;


    public CustomViewHolder(@NonNull View itemView) {  //constructor yazmayinca hata veriyor
        super(itemView);

        //burda da baslik kaynak resim yuklenmeyince cikacak resim ve o kart seklinde gorunum icin ugrastim
        text_title = itemView.findViewById(R.id.text_title);
        text_source = itemView.findViewById(R.id.text_source);
        img_headline = itemView.findViewById(R.id.img_headline);
        cardView = itemView.findViewById(R.id.main_container);

    }
}
