package com.example.safranboluterminali;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
private static Stage primaryStage; // Stage referansı

    @Override
    public void start(Stage primaryStage) {
        try {
            Main.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("giris-view.fxml"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setTitle("Giriş Ekranı");
            primaryStage.setScene(new Scene(root, 650, 400));
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeStage() {
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

