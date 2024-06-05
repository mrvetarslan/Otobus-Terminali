package com.example.safranboluterminali;

import javafx.beans.property.StringProperty;

public class Otobus {
    private StringProperty otobusPlaka;

    public String getOtobusPlaka() {
        return otobusPlaka.get();
    }

    public StringProperty otobusPlakaProperty() {
        return otobusPlaka;
    }

    public void setOtobusPlaka(String otobusPlaka) {
        this.otobusPlaka.set(otobusPlaka);
    }
}
