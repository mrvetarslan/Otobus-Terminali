package com.example.safranboluterminali;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bilet {
    private IntegerProperty biletID;
    private StringProperty yolcuTC;
    private IntegerProperty seferID;
    private IntegerProperty biletFiyat;
    private IntegerProperty koltukNo;

    public Bilet(int biletID, String yolcuTC, int seferID, int biletFiyat, int koltukNo) {
        this.biletID = new SimpleIntegerProperty(biletID);
        this.yolcuTC = new SimpleStringProperty(yolcuTC);
        this.seferID = new SimpleIntegerProperty(seferID);
        this.biletFiyat = new SimpleIntegerProperty(biletFiyat);
        this.koltukNo = new SimpleIntegerProperty(koltukNo);
    }

    public int getBiletID() {
        return biletID.get();
    }

    public IntegerProperty biletIDProperty() {
        return biletID;
    }

    public void setBiletID(int biletID) {
        this.biletID.set(biletID);
    }

    public String getYolcuTC() {
        return yolcuTC.get();
    }

    public StringProperty yolcuTCProperty() {
        return yolcuTC;
    }

    public void setYolcuTC(String yolcuTC) {
        this.yolcuTC.set(yolcuTC);
    }

    public int getSeferID() {
        return seferID.get();
    }

    public IntegerProperty seferIDProperty() {
        return seferID;
    }

    public void setSeferID(int seferID) {
        this.seferID.set(seferID);
    }

    public int getBiletFiyat() {
        return biletFiyat.get();
    }

    public IntegerProperty biletFiyatProperty() {
        return biletFiyat;
    }

    public void setBiletFiyat(int biletFiyat) {
        this.biletFiyat.set(biletFiyat);
    }

    public int getKoltukNo() {
        return koltukNo.get();
    }

    public IntegerProperty koltukNoProperty() {
        return koltukNo;
    }

    public void setKoltukNo(int koltukNo) {
        this.koltukNo.set(koltukNo);
    }
}
