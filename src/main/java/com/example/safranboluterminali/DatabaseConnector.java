package com.example.safranboluterminali;
import java.sql.*;

public class DatabaseConnector {
    private static String baglantiDurumu = " ";
    static Connection connection;
    public static Connection getConnection() {

        String ConnectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=SafranboluTerminali;user=SA;password=reallyStrongPwd123;encrypt=true;trustServerCertificate=true";
        try {
            connection = DriverManager.getConnection(ConnectionUrl);
            baglantiDurumu = "SQL Bağlantısı başarılı";

        } catch (SQLException e) {
            baglantiDurumu = "Başarısız bağlantı";
            System.out.println("Bağlantı başarısız.");
            e.printStackTrace();
        }

        return connection;
    }

    public static String getBaglantiDurumu() {
        return baglantiDurumu;
    }


}
