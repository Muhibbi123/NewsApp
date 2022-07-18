package com.mrt.newsapp.Models;

import java.io.Serializable;

public class Source implements Serializable { //data bolunebilir olsun diye serializable yaptik

    String id = ""; //postmandaki siraya göre bunlari teker teker koydum haberleri cekebileyim diye
    String name = "";

    public String getId() {
        return id;
    } //bunlar da getterlar ve setterlar burdakiler source icindekiler

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
