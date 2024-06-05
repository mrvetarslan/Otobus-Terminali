package com.example.safranboluterminali;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GirisController {
    Connection connection = DatabaseConnector.getConnection();
    Statement st = null;

    ResultSet set = null;
    @FXML
    private AnchorPane girisPane;
    @FXML
    private TextField kullaniciAdiText;
    @FXML
    private PasswordField kullaniciSifreText;

    @FXML
    private Label sqlBaglantiLabel;

    @FXML
    private void kapatButon(){
        Main.closeStage();
    }

    public void initialize(){
        String baglantiDurum = DatabaseConnector.getBaglantiDurumu();
        sqlBaglantiLabel.setText(baglantiDurum);
    } //acilista yapiliyor bu da sql bagli degilse yoruyor.

    private void closeStage() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(0.1), // Kapanma süresi
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = (Stage) girisPane.getScene().getWindow();
                        stage.close();
                    }
                }));
        timeline.play();
    }

    @FXML
    public void girisYapMethod(ActionEvent event){
        String kullaniciAd = kullaniciAdiText.getText();
        String sifre = kullaniciSifreText.getText();

        try{
            String query = "SELECT * FROM Yetkili WHERE KullaniciAdi='"+kullaniciAd+"'and KullaniciSifre='"+sifre+"'";
            st = connection.createStatement();
            set = st.executeQuery(query);
            if(set.next()){
                closeStage();
                int yazhane = set.getInt("yazhaneID");

                String yetkiliAd = set.getString("yetkiliAdi");
                String yetkiliSoyad = set.getString("yetkiliSoyadi");
                showSplashScreen(yetkiliAd, yetkiliSoyad, yazhane);

            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING); // Uyarı ekranı gösterir
                alert.setTitle("Uyarı");
                alert.setHeaderText(null);
                alert.setContentText("Kullanıcı adı veya şifre yanlış!");
                alert.showAndWait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showSplashScreen(String yetkiliAd, String yetkiliSoyad, int yazhane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("splash.fxml"));
            Parent root = loader.load();
            SplashController splashController = loader.getController();
            splashController.setYetkiliAdSoyad(yetkiliAd, yetkiliSoyad);
            splashController.setYazhane(yazhane);

            Scene splashScene = new Scene(root);
            Stage splashStage = new Stage();
            splashStage.initStyle(StageStyle.UNDECORATED);
            splashStage.setScene(splashScene);
            splashStage.show();

            // Splash ekranını bir süre gösterdikten sonra kapat
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                Platform.runLater(() -> {
                    splashStage.hide();
                    openYetkiliEkran(yetkiliAd, yetkiliSoyad, yazhane);
                });
            }, 5, TimeUnit.SECONDS);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openYetkiliEkran(String yetkiliAd, String yetkiliSoyad, int yazhane) {
        try {
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("yetkili-view.fxml"));
            Parent mainRoot = mainLoader.load();

            Scene mainScene = new Scene(mainRoot);
            Stage mainStage = new Stage();
            mainStage.setScene(mainScene);

            switch (yazhane) {
                case 1:
                    mainStage.setTitle("Yazhane 1 - " + yetkiliAd + " " + yetkiliSoyad);
                    break;
                case 2:
                    mainStage.setTitle("Yazhane 2 - " + yetkiliAd + " " + yetkiliSoyad);
                    break;
            }

            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}