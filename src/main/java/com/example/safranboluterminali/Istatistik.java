package com.example.safranboluterminali;public class Istatistik {
    private String seferID;
    private int satilanBiletSayisi;
    private int erkekBiletSayisi;
    private int kadinBiletSayisi;
    private String kalkisVarisSehir;


    public Istatistik(String seferID,String kalkisVarisSehir, int satilanBiletSayisi, int erkekBiletSayisi, int kadinBiletSayisi) {
        this.seferID = seferID;
        this.kalkisVarisSehir = kalkisVarisSehir;
        this.satilanBiletSayisi = satilanBiletSayisi;
        this.erkekBiletSayisi = erkekBiletSayisi;
        this.kadinBiletSayisi = kadinBiletSayisi;
    }

    public String getKalkisVarisSehir() {
        return kalkisVarisSehir;
    }



    // Getters ve Setters (İhtiyaca göre ekleme yapılabilir)
    public String getSeferID() {
        return seferID;
    }

    public void setSeferID(String seferID) {
        this.seferID = seferID;
    }

    public int getSatilanBiletSayisi() {
        return satilanBiletSayisi;
    }

    public void setSatilanBiletSayisi(int satilanBiletSayisi) {
        this.satilanBiletSayisi = satilanBiletSayisi;
    }

    public int getErkekBiletSayisi() {
        return erkekBiletSayisi;
    }

    public void setErkekBiletSayisi(int erkekBiletSayisi) {
        this.erkekBiletSayisi = erkekBiletSayisi;
    }

    public int getKadinBiletSayisi() {
        return kadinBiletSayisi;
    }

    public void setKadinBiletSayisi(int kadinBiletSayisi) {
        this.kadinBiletSayisi = kadinBiletSayisi;
    }
}
