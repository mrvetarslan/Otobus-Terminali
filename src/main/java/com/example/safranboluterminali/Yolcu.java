package com.example.safranboluterminali;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Yolcu{
    private StringProperty plakaPane;
    private StringProperty tcPane;
    private StringProperty adPane;
    private StringProperty soyadPane;
    private StringProperty cinsiyetPane;
    private IntegerProperty yasPane;
    private StringProperty telefonPane;

    public Yolcu(String otobusPlaka, String yolcuTc, String yolcuAd, String yolcuSoyad, String yolcuCinsiyet,int yolcuYas,String yolcuTel) {
        this.plakaPane = new SimpleStringProperty(otobusPlaka);
        this.tcPane = new SimpleStringProperty(yolcuTc);
        this.adPane = new SimpleStringProperty(yolcuAd);
        this.soyadPane = new SimpleStringProperty(yolcuSoyad);
        this.cinsiyetPane = new SimpleStringProperty(yolcuCinsiyet);
        this.yasPane =  new SimpleIntegerProperty(yolcuYas);
        this.telefonPane = new SimpleStringProperty(yolcuTel);
    }

    public String getPlakaPane() {
        return plakaPane.get();
    }

    public StringProperty plakaPaneProperty() {
        return plakaPane;
    }

    public void setPlakaPane(String plakaPane) {
        this.plakaPane.set(plakaPane);
    }

    public String getTcPane() {
        return tcPane.get();
    }

    public StringProperty tcPaneProperty() {
        return tcPane;
    }

    public void setTcPane(String tcPane) {
        this.tcPane.set(tcPane);
    }

    public String getAdPane() {
        return adPane.get();
    }

    public StringProperty adPaneProperty() {
        return adPane;
    }

    public void setAdPane(String adPane) {
        this.adPane.set(adPane);
    }

    public String getSoyadPane() {
        return soyadPane.get();
    }

    public StringProperty soyadPaneProperty() {
        return soyadPane;
    }

    public void setSoyadPane(String soyadPane) {
        this.soyadPane.set(soyadPane);
    }

    public String getCinsiyetPane() {
        return cinsiyetPane.get();
    }

    public StringProperty cinsiyetPaneProperty() {
        return cinsiyetPane;
    }

    public void setCinsiyetPane(String cinsiyetPane) {
        this.cinsiyetPane.set(cinsiyetPane);
    }

    public int getYasPane() {
        return yasPane.get();
    }

    public IntegerProperty yasPaneProperty() {
        return yasPane;
    }

    public void setYasPane(int yasPane) {
        this.yasPane.set(yasPane);
    }

    public String getTelefonPane() {
        return telefonPane.get();
    }

    public StringProperty telefonPaneProperty() {
        return telefonPane;
    }

    public void setTelefonPane(String telefonPane) {
        this.telefonPane.set(telefonPane);
    }
}
