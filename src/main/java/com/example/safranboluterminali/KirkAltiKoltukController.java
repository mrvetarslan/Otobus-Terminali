package com.example.safranboluterminali;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KirkAltiKoltukController {
    private YetkiliController yetkiliController;

    @FXML
    private AnchorPane kirkAltiKoltukPane;
    @FXML
    private List<Button> koltukButtons = new ArrayList<>();

    Connection connection = DatabaseConnector.getConnection();

    public void setKullaniciController(YetkiliController yetkiliController) {
        this.yetkiliController = yetkiliController;
    }

    @FXML
    void initialize() {
        for (int i = 1; i <= 40; i++) {
            String buttonId = "koltuk" + i + "Button";
            Button button = (Button) kirkAltiKoltukPane.lookup("#" + buttonId);
            if (button != null) {
                koltukButtons.add(button);
            }

        }
    }

    void renklendirKoltuklar(int seferID, String fxmlDosyaAdi) {
        try {
            // Seçilen seferdeki bilet bilgilerini getiren sorgu
            String sql = "SELECT bilet.koltukNumarasi, yolcu.yolcuCinsiyet FROM Bilet bilet " +
                    "INNER JOIN Yolcu yolcu ON bilet.yolcuTC = yolcu.yolcuTC " +
                    "WHERE bilet.seferID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, seferID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int koltukNumarasi = resultSet.getInt("koltukNumarasi");
                        String cinsiyet = resultSet.getString("yolcuCinsiyet");

                        // Koltuğa ait JavaFX nesnesini bul
                        String buttonId = "koltuk" + koltukNumarasi + "Button";
                        Button koltukButton = (Button) kirkAltiKoltukPane.lookup("#" + buttonId);
                        // Cinsiyete göre koltuğu renklendir
                        if ("Kadin".equals(cinsiyet)) {
                            koltukButton.setStyle("-fx-background-color: #EAD8DE;");
                        } else if ("Erkek".equals(cinsiyet)) {
                            koltukButton.setStyle("-fx-background-color: #C7E2FB;");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void butonDoluMu(Button button) {
        String backgroundColor = button.getStyle();
        if (backgroundColor.contains("-fx-background-color: #EAD8DE") || backgroundColor.contains("-fx-background-color: #C7E2FB")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText("Bu koltuk dolu");
            alert.setContentText("Lütfen farklı bir koltuk seçin.");
            alert.showAndWait();
        }
    }

    @FXML
    void koltukMethod(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String koltukNo = clickedButton.getText(); // Veya başka bir şekilde koltuk numarasını alabilirsiniz
        yetkiliController.setSecilenKoltukNo(koltukNo);
        butonDoluMu(clickedButton);

        if (yetkiliController.getCinsiyet().equals("Kadın")) {
            clickedButton.setStyle("-fx-background-color: #EAD8DE;");
        } else {
            clickedButton.setStyle("-fx-background-color: #C7E2FB;");
        }

        closeStage();
    }
    private void closeStage() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(1), // Kapanma süresi
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = (Stage) kirkAltiKoltukPane.getScene().getWindow();
                        stage.close();
                    }
                }));
        timeline.play();
    }



}
