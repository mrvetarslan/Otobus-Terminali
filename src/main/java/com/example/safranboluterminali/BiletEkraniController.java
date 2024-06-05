package com.example.safranboluterminali;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BiletEkraniController {

    @FXML
    Button kapatButton;
    @FXML
    Label seferLabel,adSoyadLabel,firmaLabel,biletLabel,koltukLabel,tarihLabel;

    public void setData(String firma, String ad, String soyad, String sefer, int bilet, int koltuk,String tarih) {
        firmaLabel.setText(firma);
        adSoyadLabel.setText(ad + " "+ soyad);
        seferLabel.setText(sefer);
        biletLabel.setText(String.valueOf(bilet));
        koltukLabel.setText(String.valueOf(koltuk));
        tarihLabel.setText(tarih.substring(0, tarih.length() - 8));
    }

    public void biletKapat(){
        kapatButton.setOnAction(event -> {
            Stage stage = (Stage) kapatButton.getScene().getWindow();
            stage.close();
        });
    }



}
