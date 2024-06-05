package com.example.safranboluterminali;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class YetkiliController {
    Connection connection = DatabaseConnector.getConnection();
    String secilenKalkis;
    String secilenVaris;
    String secilenPlaka;
    private ObservableList<Yolcu> yolcuList = FXCollections.observableArrayList();
    private ObservableList<Bilet> biletList = FXCollections.observableArrayList();
    private ObservableList<Sefer> seferList = FXCollections.observableArrayList();
    private ObservableList<Sefer> seferSoforList = FXCollections.observableArrayList();

    private ObservableList<Object> mixedList = FXCollections.observableArrayList();
    private ObservableList<Istatistik> istatistikList = FXCollections.observableArrayList();

    @FXML
    private String  secilenKoltukNo;
    @FXML private AnchorPane yetkiliAnaPane, silmePane, biletOlusturPane, istatistikPane, seferlerPane, seferEklePane, yolculariGoruntulePane, biletleriGoruntulePane;
    @FXML private TextField  biletKoltukNo, biletFiyat, yolcuAd, yolcuSoyad, yolcuTc, yolcuYas, yolcuTel, biletId, seferTextField, kalkisSaatPane;
    @FXML private ComboBox<String> secilenTC, yolcuCinsiyet, biletSeferId, otobusIdPane, kalkisSehirPane, varisSehirPane;
    @FXML private TableView<Sefer> seferTable;
    @FXML private TableColumn<Sefer, Integer> seferID;
    @FXML private TableColumn<Sefer, String> plaka, kalkisSaat, kalkisSehir, varisSehir,soforAdSoyad;
    @FXML private TableView<Yolcu> yolcuTable;
    @FXML private TableColumn<Otobus, String> plakaColumn, telefonColumn;
    @FXML private TableColumn<Yolcu, String> tcColumn, adColumn, soyadColumn, cinsiyetColumn, yasColumn;
    @FXML private TableView<Bilet> biletTable;
    @FXML private TableColumn<Bilet, Integer> biletIDPane, seferIDPane, biletFiyatPane, koltukNoPane;
    @FXML private TableColumn<Bilet, String> yolcuTcPane;
    @FXML private TableView<Istatistik> istatistikTable ;
    @FXML private TableColumn<Istatistik, String> seferIDSutun;
    @FXML private TableColumn<Istatistik, String> seferYol;
    @FXML private TableColumn<Istatistik, Integer> satilanBiletSutun;
    @FXML private TableColumn<Istatistik, Integer> erkekBiletSutun;
    @FXML private TableColumn<Istatistik, Integer> kadinBiletSutun;
    @FXML private Label sonucGosterenLabel;
    @FXML private Text kayitDurumu;

    public void initialize(){
        ObservableList<String> cinsiyet = FXCollections.observableArrayList(
                "Kadın",
                "Erkek"
        );
        yolcuCinsiyet.setItems(cinsiyet);
        initializeSeferColumns();
        baslangicMethod();
        seferTable.setItems(seferSoforList);
        initializeYolcuColumns();
        yolcuTable.setItems(yolcuList);
        yolcuTable.setItems(getYolcuListFromDatabase());
        initializeBiletColumns();
        biletTable.setItems(biletList);
        mixedList.addAll(getBiletListFromDatabase());
        loadYolcuTCs();
        initializeIstatistikColumns();
        istatistikTable.setItems(istatistikList);

        try{
            String query = "SELECT * FROM Otobus";
            try(PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
                ObservableList<String> plakalarOptions = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    String plaka = resultSet.getString("otobusPlaka");
                    plakalarOptions.add(plaka);
                }
                otobusIdPane.setItems(plakalarOptions);
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        ObservableList<String> sehirler = FXCollections.observableArrayList( //Sefer eklerken sehir combobox içini doldurduk
                "Adana",
                "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir",
                "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli",
                "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane",
                "Hakkâri", "Hatay", "Isparta", "İçel (Mersin)", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri",
                "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin",
                "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas",
                "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray",
                "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük",
                "Kilis", "Osmaniye", "Düzce"
        );
        kalkisSehirPane.setItems(sehirler); //ObservableList'i ComboBox'a atadık
        varisSehirPane.setItems(sehirler); //ObservableList'i ComboBox'a atadık

        ObservableList<String> kalkisVarisSehirler = getkalkisVarisSehirlerFromDatabase();
        biletSeferId.setItems(kalkisVarisSehirler); //Sefer Seç Comboboxına vt.daki Sefer tablosundaki sehirleri attık
    }


    @FXML
    public void cikisYapMethod(ActionEvent event) {
        closeStage();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("giris-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 380));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void closeStage() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(0.1), // Kapanma süresi
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage stage = (Stage) yetkiliAnaPane.getScene().getWindow();
                        stage.close();
                    }
                }));
        timeline.play();
    }
    @FXML
    public void seferEkleMethod(ActionEvent event) {
        istatistikPane.setVisible(false);
        seferEklePane.setVisible(true);
        seferlerPane.setVisible(true);
        silmePane.setVisible(false);
        yolculariGoruntulePane.setVisible(false);
        biletleriGoruntulePane.setVisible(false);
    }
    @FXML
    public void yolcuGoruntuleveSil(ActionEvent event) {
        istatistikPane.setVisible(false);
        yolculariGoruntulePane.setVisible(true);
        biletOlusturPane.setVisible(false);
        seferlerPane.setVisible(false);
        seferEklePane.setVisible(false);
        biletleriGoruntulePane.setVisible(false);
        silmePane.setVisible(false);

    }
    @FXML
    public void yolculariGosterMethod(ActionEvent event){
        istatistikPane.setVisible(false);
        yolculariGoruntulePane.setVisible(false);
        biletOlusturPane.setVisible(false);
        biletTable.setVisible(true);
        seferlerPane.setVisible(false);
        seferEklePane.setVisible(false);
        biletleriGoruntulePane.setVisible(true);
    }
    @FXML
    public void biletleriGosterMethod(ActionEvent event){
        istatistikPane.setVisible(false);
        biletleriGoruntulePane.setVisible(true);
        biletTable.setVisible(false);
        biletOlusturPane.setVisible(true);
        silmePane.setVisible(true);
        seferlerPane.setVisible(false);
        seferEklePane.setVisible(false);
        yolculariGoruntulePane.setVisible(false);
    }
    private void loadYolcuTCs() {
        try{
            String query = "SELECT yolcuTC FROM Yolcu";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // ComboBox'ı temizle
                secilenTC.getItems().clear();

                // ResultSet'taki her bir kaydı ComboBox'a ekle
                while (resultSet.next()) {
                    String yolcuTC = resultSet.getString("yolcuTC");
                    secilenTC.getItems().add(yolcuTC);
                }



            } catch (SQLException e) {
                e.printStackTrace();
                // SQL hatası oluştuğunda yapılacak işlemler
            }
        }catch ( Exception e) {
            e.printStackTrace();
            // SQL hatası oluştuğunda yapılacak işlemler
        }
    }

    @FXML
    public void istatistikMethod() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sqlQuery = "SELECT * FROM v_SatilanBiletCinsiyetAnalizi";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Istatistik> istatistikList = FXCollections.observableArrayList(); // Veri listesi

            // Sonuçları işle
            while (resultSet.next()) {
                String seferID = resultSet.getString("seferID");
                String kalkis = resultSet.getString("kalkisSehir");
                String varis = resultSet.getString("varisSehir");
                int satilanBiletSayisi = resultSet.getInt("SatilanBiletSayisi");
                int erkekBiletSayisi = resultSet.getInt("ErkekBiletSayisi");
                int kadinBiletSayisi = resultSet.getInt("KadinBiletSayisi");
                String kalkisVaris = kalkis+ " - " + varis;
                // Gerekli işlemler ve veri yapılarını oluşturma
                // Örnek olarak:
                String bilgi = "Sefer ID: " + seferID + ", Satılan Bilet Sayısı: " + satilanBiletSayisi +
                        ", Erkek Bilet Sayısı: " + erkekBiletSayisi + ", Kadın Bilet Sayısı: " + kadinBiletSayisi;
                // Verileri istatistik nesnelerine ekleme
                // Burada Istatistik sınıfınızın yapısına göre ekleyebilirsiniz
                Istatistik istatistik = new Istatistik(seferID, kalkisVaris, satilanBiletSayisi, erkekBiletSayisi, kadinBiletSayisi);
                istatistikList.add(istatistik);
            }

            // Sütunları ve veri listesini tablo görünümüne ekleme
            seferIDSutun.setCellValueFactory(new PropertyValueFactory<>("seferID")); // Örnek sütun adı
            seferYol.setCellValueFactory(new PropertyValueFactory<>("kalkisVarisSehir")); // Örnek sütun adı
            satilanBiletSutun.setCellValueFactory(new PropertyValueFactory<>("satilanBiletSayisi")); // Örnek sütun adı
            erkekBiletSutun.setCellValueFactory(new PropertyValueFactory<>("erkekBiletSayisi")); // Örnek sütun adı
            kadinBiletSutun.setCellValueFactory(new PropertyValueFactory<>("kadinBiletSayisi")); // Örnek sütun adı

            istatistikTable.setItems(istatistikList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void istatistikButton(ActionEvent event){
        istatistikPane.setVisible(true);
        seferEklePane.setVisible(false);
        seferlerPane.setVisible(false);
        silmePane.setVisible(false);
        yolculariGoruntulePane.setVisible(false);
        biletleriGoruntulePane.setVisible(false);
        istatistikMethod();
    }
    @FXML
    public void silMethod(ActionEvent event){ //Sil butonuna basıldığında olacak şeyler
        try{
            String silinecekTc = String.valueOf(secilenTC.getValue());
            String yolcuSilQuery = "DELETE FROM Yolcu WHERE yolcuTC = ?";
            String biletSilQuery = "DELETE FROM Bilet WHERE yolcuTC = ?";

            try (PreparedStatement biletStatement = connection.prepareStatement(biletSilQuery)) {
                biletStatement.setString(1, silinecekTc);
                int affectedRowsBilet = biletStatement.executeUpdate();

                if (affectedRowsBilet > 0) {
                    sonucGosterenLabel.setText("Bilet kaydı silindi.");
                    biletList.clear();
                    getBiletListFromDatabase();
                    biletTable.refresh();
                    yolcuList.clear();
                    getYolcuListFromDatabase();
                    yolcuTable.refresh();
                    secilenTC.getSelectionModel().clearSelection();
                } else {
                    sonucGosterenLabel.setText("Bilet kaydı silinemedi.");
                }
            }
            try(PreparedStatement yolcuStatement = connection.prepareStatement(yolcuSilQuery)){
                yolcuStatement.setString(1, silinecekTc);
                int affectedRowsYolcu = yolcuStatement.executeUpdate();

                if (affectedRowsYolcu > 0) {
                    yolcuTable.refresh();
                } else {
                    sonucGosterenLabel.setText("Bilet silindi. Yolcu silinemedi");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void seferSilButtonAction(ActionEvent event) {
        Sefer selectedSefer = seferTable.getSelectionModel().getSelectedItem(); // Seçilen seferi al

        if (selectedSefer != null) {
            try {
                Connection conn = DatabaseConnector.getConnection();
                String sqlQuery = "DELETE FROM Sefer WHERE seferID = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
                preparedStatement.setInt(1, selectedSefer.getSeferID()); // Seçilen seferin ID'sini parametre olarak ekle
                int result = preparedStatement.executeUpdate();

                if (result > 0) {
                    kayitDurumu.setText("Sefer başarıyla silindi.");
                    seferSoforList.clear();
                    baslangicMethod();
                    seferTable.refresh();
                } else {
                    kayitDurumu.setText("Sefer silinemedi");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            kayitDurumu.setText("Sefer seçin.");
        }
    }

    @FXML
    public void seferKaydetMethod(ActionEvent event){
        try{
            if (connection != null){
                int secilenId = Integer.parseInt(seferTextField.getText());
                secilenPlaka = otobusIdPane.getValue();
                String secilenZaman = kalkisSaatPane.getText();
                secilenKalkis = kalkisSehirPane.getValue();
                secilenVaris = varisSehirPane.getValue();
                String query = "INSERT INTO Sefer (seferID,otobusPlaka,kalkisSaat,kalkisSehir,varisSehir) VALUES (?, ?, ?, ?, ?)";

                try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setInt(1,secilenId);
                    preparedStatement.setString(2,secilenPlaka);
                    preparedStatement.setString(3,secilenZaman);
                    preparedStatement.setString(4,secilenKalkis);
                    preparedStatement.setString(5,secilenVaris);

                    int result = preparedStatement.executeUpdate();
                    if (result > 0) {
                        System.out.println("Veri başarıyla eklendi."); //label'e yazdirilacak. // zaten var diye uyari verecek.
                        ObservableList<String> kalkisVarisSehirler = getkalkisVarisSehirlerFromDatabase();
                        biletSeferId.setItems(kalkisVarisSehirler);
                        kayitDurumu.setText("başarılı");
                        seferSoforList.clear();
                        baslangicMethod();
                        seferTable.refresh();
                    }
                    else {
                        kayitDurumu.setText("başarısız");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Veri Eklenemedi.");
            }
            seferTable.setItems(seferSoforList);
            seferTable.refresh();
        }catch(Exception e){
            e.printStackTrace();
        }


    }


    @FXML
    public int getSeferID(String kalkisVarisString){
        int seferID = 0;

        String query = "SELECT seferID FROM Sefer WHERE CONCAT(kalkisSehir, ' - ', varisSehir) = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, kalkisVarisString);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    seferID = resultSet.getInt("seferID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seferID;
    }

    public String getCinsiyet(){
        String secilenCinsiyet = yolcuCinsiyet.getValue();
        return secilenCinsiyet;
    }

    // Seferden otobus kapasitesini döndürüyor
    private int getOtobusKapasitesi(String seferId) {
        int kapasite = 0;

        String query = "SELECT o.otobusKapasite FROM Sefer s JOIN Otobus o ON s.otobusPlaka = o.otobusPlaka WHERE CONCAT(s.kalkisSehir, ' - ', s.varisSehir) = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, seferId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                kapasite = resultSet.getInt("otobusKapasite");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kapasite;
    }

    private String getOtobusPlakasi(String secilenSeferId) {
        String otobusPlakasi = null;

        String query = "SELECT otobusPlaka FROM Sefer WHERE CONCAT(kalkisSehir, ' - ', varisSehir) = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, secilenSeferId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    otobusPlakasi = resultSet.getString("otobusPlaka");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return otobusPlakasi;
    }

    @FXML
    public void koltukSecMethod(ActionEvent event) { //Koltuk Seç butonuna basıldığında olacaklar

        try {
            String selectedSeferId =biletSeferId.getValue();
            if (selectedSeferId != null) {
                int kapasite = getOtobusKapasitesi(selectedSeferId);
                if (kapasite == 40) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("kirkKoltuk-view.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    KirkKoltukController kirkKoltukController = loader.getController();
                    kirkKoltukController.setKullaniciController(this);

                    int secilenSeferID = getSeferID(selectedSeferId);
                    kirkKoltukController.renklendirKoltuklar(secilenSeferID,"kirkKoltuk-view.fxml");
                }
                else{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("kirkAltiKoltuk-view.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                    KirkAltiKoltukController kirkAltiKoltukController = loader.getController();
                    kirkAltiKoltukController.setKullaniciController(this);
                    int secilenSeferID = getSeferID(selectedSeferId);
                    kirkAltiKoltukController.renklendirKoltuklar(secilenSeferID,"kirkAltiKoltuk-view.fxml");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean biletIdKontrolu(int biletId) {
        boolean exist = false;

        try {
            String sql = "SELECT COUNT(*) FROM Bilet WHERE biletId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, biletId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        exist = (count > 0);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }


    @FXML
    public void yolcuKaydetMethod(ActionEvent event) throws IOException { //Yolcu Kaydet Butonuna basıldığında olacaklar
        String secilenAd = yolcuAd.getText();
        String secilenSoyad = yolcuSoyad.getText();
        String secilenKimlik = yolcuTc.getText();
        String secilenCinsiyet = yolcuCinsiyet.getValue();
        int secilenYas = 0;
        String secilenTel = yolcuTel.getText();
        int secilenBiletId = 0;
        String secilenSefer = biletSeferId.getValue();
        int secilenBiletKoltuk = 0;
        int secilenBiletFiyat = 0;
        String plaka = getOtobusPlakasi(secilenSefer);
        int comboBoxseferId = getSeferID(secilenSefer);
        try {
            if (!yolcuYas.getText().isEmpty()) {
                secilenYas = Integer.parseInt(yolcuYas.getText());
                if(secilenYas<18){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Uyarı");
                    alert.setHeaderText(null);
                    alert.setContentText("Yolcu yaş kısıtlamalara uymalıdır");
                    alert.showAndWait();
                }
            } else {
                throw new NumberFormatException();
            }

            if (!biletId.getText().isEmpty()) {
                secilenBiletId = Integer.parseInt(biletId.getText());
            } else {
                throw new NumberFormatException();
            }

            if (!biletKoltukNo.getText().isEmpty()) {
                secilenBiletKoltuk = Integer.parseInt(biletKoltukNo.getText());
            } else {
                throw new NumberFormatException();
            }

            if (!biletFiyat.getText().isEmpty()) {
                secilenBiletFiyat = Integer.parseInt(biletFiyat.getText());
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen geçerli bir veri girin.");
            alert.showAndWait();
            return;
        }

        if (biletIdKontrolu(secilenBiletId)) {
            // Aynı ID'ye sahip bilet var, uyarı göster
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText("Aynı Bilet ID'sine Sahip Bilet Var");
            alert.setContentText("Lütfen farklı bir Bilet ID'si seçin.");
            alert.showAndWait();
            return;
        }
        try {
            String query = "INSERT INTO Yolcu (otobusPlaka,yolcuTC,yolcuAd,yolcuSoyad,yolcuCinsiyet,yolcuYas,yolcuTel) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, plaka);
            preparedStatement.setString(2, secilenKimlik);
            preparedStatement.setString(3, secilenAd);
            preparedStatement.setString(4, secilenSoyad);
            preparedStatement.setString(5, secilenCinsiyet);
            preparedStatement.setString(6, String.valueOf(secilenYas));
            preparedStatement.setString(7, secilenTel);


            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                sonucGosterenLabel.setText("Bilet oluşturuldu");
                Yolcu yeniYolcu = new Yolcu(plaka, secilenKimlik, secilenAd, secilenSoyad, secilenCinsiyet, secilenYas, secilenTel);
                yolcuList.add(yeniYolcu);
                yolcuTable.setItems(yolcuList);
                yolcuTable.refresh();
            } else {
                System.out.println("Bilet oluşturulamadı.");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String query = "INSERT INTO Bilet (biletID,yolcuTC,seferID,biletFiyat,koltukNumarasi) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(secilenBiletId));
            preparedStatement.setString(2, secilenKimlik);
            preparedStatement.setString(3, String.valueOf(comboBoxseferId));
            preparedStatement.setString(4, String.valueOf(secilenBiletFiyat));
            preparedStatement.setString(5, String.valueOf(secilenBiletKoltuk));
            biletTable.setItems(biletList);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Veri basariyla eklendi.");
                sonucGosterenLabel.setText("Veri başarıyla eklendi.");
                Bilet yeniBilet = new Bilet(secilenBiletId, secilenKimlik, comboBoxseferId, secilenBiletFiyat, secilenBiletKoltuk);
                biletList.add(yeniBilet);
                biletTable.refresh();
            } else {
                sonucGosterenLabel.setText("Veri eklenemedi.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Connection conn = DatabaseConnector.getConnection(); // getConnection mevcut bağlantı metodu adıdır

        String firmaAdi = null;
        String seferTarih = null;
        if (conn != null) {
            try {
                // Sorgu için PreparedStatement oluşturma
                String sorgu = "SELECT OS.otobusSirketAd FROM Otobus O INNER JOIN OtobusSirketi OS ON O.otobusSirketID = OS.otobusSirketID WHERE O.otobusPlaka = ?";
                String sorgu2 = "SELECT kalkisSaat FROM Sefer WHERE seferID = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
                preparedStatement.setString(1, plaka);
                ResultSet resultSet = preparedStatement.executeQuery();

                PreparedStatement preparedStatement2 = conn.prepareStatement(sorgu2);
                preparedStatement2.setInt(1, comboBoxseferId);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                // Sorguyu çalıştırma ve sonuçları alıp işleme

                // Eğer sorgu sonucu varsa
                if (resultSet.next() && resultSet2.next()) {
                    firmaAdi = resultSet.getString("otobusSirketAd");
                    seferTarih = resultSet2.getString("kalkisSaat");
                } else {
                    firmaAdi = "bilinmiyor";
                    seferTarih = "bilinmiyor";
                }

                // Bağlantıyı kapatmayı unutmayın
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("biletEkrani.fxml"));
        Parent root = loader.load();
        BiletEkraniController biletGoruntulemeController = loader.getController();
        biletGoruntulemeController.setData(firmaAdi, secilenAd, secilenSoyad, secilenSefer, secilenBiletId, secilenBiletKoltuk, seferTarih);

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 765, 275));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

    @FXML
    private ObservableList<Yolcu> getYolcuListFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Yolcu";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Yolcu yolcu = new Yolcu(
                        resultSet.getString("otobusPlaka"),
                        resultSet.getString("yolcuTC"),
                        resultSet.getString("yolcuAd"),
                        resultSet.getString("yolcuSoyad"),
                        resultSet.getString("yolcuCinsiyet"),
                        resultSet.getInt("yolcuYas"),
                        resultSet.getString("yolcuTel")
                );
                yolcuList.add(yolcu);
                yolcuTable.refresh();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return yolcuList;
    }

    @FXML
    private ObservableList<Yolcu> getBiletListFromDatabase() {

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Bilet";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Bilet bilet = new Bilet(
                        resultSet.getInt("biletID"),
                        resultSet.getString("yolcuTC"),
                        resultSet.getInt("seferID"),
                        resultSet.getInt("biletFiyat"),
                        resultSet.getInt("koltukNumarasi")
                );
                biletList.add(bilet);
                biletTable.refresh();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return yolcuList;
    }

    private void initializeIstatistikColumns(){
        seferIDSutun.setCellValueFactory(new PropertyValueFactory<>("seferID")); // Istatistik sınıfının seferID alanı
        seferYol.setCellValueFactory(new PropertyValueFactory<>("kalkisVarisSehir")); // Istatistik sınıfının kalkisVaris alanı
        satilanBiletSutun.setCellValueFactory(new PropertyValueFactory<>("satilanBiletSayisi")); // Istatistik sınıfının satilanBiletSayisi alanı
        erkekBiletSutun.setCellValueFactory(new PropertyValueFactory<>("erkekBiletSayisi")); // Istatistik sınıfının erkekBiletSayisi alanı
        kadinBiletSutun.setCellValueFactory(new PropertyValueFactory<>("kadinBiletSayisi")); // Istatistik sınıfının kadinBiletSayisi alanı

    }

    private void initializeYolcuColumns() {
        plakaColumn.setCellValueFactory(new PropertyValueFactory<>("plakaPane"));
        tcColumn.setCellValueFactory(new PropertyValueFactory<>("tcPane"));
        adColumn.setCellValueFactory(new PropertyValueFactory<>("adPane"));
        soyadColumn.setCellValueFactory(new PropertyValueFactory<>("soyadPane"));
        cinsiyetColumn.setCellValueFactory(new PropertyValueFactory<>("cinsiyetPane"));
        yasColumn.setCellValueFactory(new PropertyValueFactory<>("yasPane"));
        telefonColumn.setCellValueFactory(new PropertyValueFactory<>("telefonPane"));
    }

    private void initializeBiletColumns(){
        biletIDPane.setCellValueFactory(cellData -> cellData.getValue().biletIDProperty().asObject());
        yolcuTcPane.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYolcuTC()));
        seferIDPane.setCellValueFactory(cellData -> cellData.getValue().seferIDProperty().asObject());
        biletFiyatPane.setCellValueFactory(cellData -> cellData.getValue().biletFiyatProperty().asObject());
        koltukNoPane.setCellValueFactory(cellData -> cellData.getValue().koltukNoProperty().asObject());

    }

    //Giriş Yapıldıktan sonra ilk ekran için oluşturulan method

    @FXML
    public void yenileMethod(){
        seferSoforList.clear();
        baslangicMethod();
        seferTable.refresh();
    }
    private void baslangicMethod() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            CallableStatement statement = conn.prepareCall("{call getSeferVeSofor}");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Sefer sefer = new Sefer(
                        resultSet.getInt("seferID"),
                        resultSet.getString("otobusPlaka"),
                        resultSet.getString("soforAd") + " " + resultSet.getString("soforSoyad"),
                        resultSet.getString("kalkisSaat"),
                        resultSet.getString("kalkisSehir"),
                        resultSet.getString("varisSehir")
                );

                seferSoforList.add(sefer);
            }

            // Tabloya eklemek için TableView'in setItems metodunu kullanabilirsiniz
            // Örnek:
            seferTable.setItems(seferSoforList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void initializeSeferColumns() {  //Sefer sütunlarını başlatır
        seferID.setCellValueFactory(cellData -> cellData.getValue().seferIDProperty().asObject());
        plaka.setCellValueFactory(cellData -> cellData.getValue().plakaProperty());
        kalkisSaat.setCellValueFactory(cellData -> cellData.getValue().kalkisSaatProperty());
        kalkisSehir.setCellValueFactory(cellData -> cellData.getValue().kalkisSehirProperty());
        varisSehir.setCellValueFactory(cellData -> cellData.getValue().varisSehirProperty());
        soforAdSoyad.setCellValueFactory(cellData -> cellData.getValue().soforAdSoyadProperty());
    }

    //Uygulamanın başlangıcında arayüz ögelerini başlatıyor

    // Sefer tablosundaki sehirleri vt den çekmek için kullanılan method sefer combobox
    private ObservableList<String> getkalkisVarisSehirlerFromDatabase() {
        ObservableList<String> kalkisVarisSehirler = FXCollections.observableArrayList();

        String query = "SELECT DISTINCT CONCAT(kalkisSehir, ' - ', varisSehir) AS seferId FROM Sefer";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String seferId = resultSet.getString("seferId");
                kalkisVarisSehirler.add(seferId);
                seferTable.refresh();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kalkisVarisSehirler;
    }

    public void setSecilenKoltukNo(String biletKoltukNo) {
        this.secilenKoltukNo = biletKoltukNo;
        this.biletKoltukNo.setText(biletKoltukNo); // TextField'ın adını doğru şekilde güncelledik
    }

}
