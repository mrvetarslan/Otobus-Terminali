package com.example.safranboluterminali;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sefer {
    private IntegerProperty seferID;
    private StringProperty plaka;
    private StringProperty kalkisSaat;
    private StringProperty kalkisSehir;
    private StringProperty varisSehir;

    private StringProperty soforAdSoyad; //bunları ekle



    public Sefer(int seferID, String plaka,String soforAdSoyad,String kalkisSaat, String kalkisSehir, String varisSehir) {
        this.seferID = new SimpleIntegerProperty(seferID);
        this.soforAdSoyad=new SimpleStringProperty(soforAdSoyad);
        this.plaka = new SimpleStringProperty(plaka);
        this.kalkisSaat = new SimpleStringProperty(kalkisSaat);
        this.kalkisSehir = new SimpleStringProperty(kalkisSehir);
        this.varisSehir = new SimpleStringProperty(varisSehir);
    }
    public Sefer(int seferID, String plaka,String kalkisSaat, String kalkisSehir, String varisSehir) {
        this.seferID = new SimpleIntegerProperty(seferID);
        this.plaka = new SimpleStringProperty(plaka);
        this.kalkisSaat = new SimpleStringProperty(kalkisSaat);
        this.kalkisSehir = new SimpleStringProperty(kalkisSehir);
        this.varisSehir = new SimpleStringProperty(varisSehir);
    }


    public String getSoforAdSoyad() {
        return soforAdSoyad.get();
    }

    public StringProperty soforAdSoyadProperty() {
        return soforAdSoyad;
    }

    public void setSoforAdSoyad(String soforAdSoyad) {
        this.soforAdSoyad.set(String.valueOf(soforAdSoyad));
    }

    // Getter ve Setter metotları

    public int getSeferID() {
        return seferID.get();
    }

    public IntegerProperty seferIDProperty() {
        return seferID;
    }

    public void setSeferID(int seferID) {
        this.seferID.set(seferID);
    }

    public String getPlaka() {
        return plaka.get();
    }

    public StringProperty plakaProperty() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka.set(plaka);
    }

    public String getKalkisSaat() {
        return kalkisSaat.get();
    }

    public StringProperty kalkisSaatProperty() {
        return kalkisSaat;
    }

    public void setKalkisSaat(String kalkisSaat) {
        this.kalkisSaat.set(kalkisSaat);
    }

    public String getKalkisSehir() {
        return kalkisSehir.get();
    }

    public StringProperty kalkisSehirProperty() {
        return kalkisSehir;
    }

    public void setKalkisSehir(String kalkisSehir) {
        this.kalkisSehir.set(kalkisSehir);
    }

    public String getVarisSehir() {
        return varisSehir.get();
    }

    public StringProperty varisSehirProperty() {
        return varisSehir;
    }

    public void setVarisSehir(String varisSehir) {
        this.varisSehir.set(varisSehir);
    }


}
