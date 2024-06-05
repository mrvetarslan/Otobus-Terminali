package com.example.safranboluterminali;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    // FXML dosyasındaki Label öğeleri

    // initData metoduna gelen verileri saklamak için değişkenler
    @FXML
    private ProgressBar loadingProgressBar;
    private String yetkiliAd;
    private String yetkiliSoyad;
    private int yazhane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        simulateLoading();
    }

    private void simulateLoading() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                double durationSeconds = 5.0; // İşlemin süresi (saniye cinsinden)
                double updateInterval = 0.1;  // ProgressBar'ın kaç saniyede bir güncelleneceği

                for (double elapsedSeconds = 0; elapsedSeconds <= durationSeconds; elapsedSeconds += updateInterval) {
                    double progress = elapsedSeconds / durationSeconds;
                    updateProgress(progress, 1);

                    // Gerçek bir işlemi temsil eden kodu buraya ekleyebilirsiniz.
                    try {
                        Thread.sleep((long) (updateInterval * 1000)); // Saniyeye çevirip uyku süresi ekleyerek ProgressBar'ı yavaşlatma
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };

        loadingProgressBar.progressProperty().bind(task.progressProperty());

        // Task'i başlat
        new Thread(task).start();
    }

    // Yetkili Ad ve Soyad'ı ayarlamak için metod
    public void setYetkiliAdSoyad(String yetkiliAd, String yetkiliSoyad) {
        this.yetkiliAd = yetkiliAd;
        this.yetkiliSoyad = yetkiliSoyad;
    }

    // Yazhane'yi ayarlamak için metod
    public void setYazhane(int yazhane) {
        this.yazhane = yazhane;
    }

    // Label'ları güncelleyen özel bir metod

}
