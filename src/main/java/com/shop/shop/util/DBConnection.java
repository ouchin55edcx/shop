package com.shop.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/masterShop?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "masterShopUser";
    private static final String PASSWORD = "MasterShop##22";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established successfully.");
            return connection;
        } catch (SQLException e) {
            System.err.println("Connection failed. Details:");
            System.err.println("URL: " + URL);
            System.err.println("Username: " + USER);
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }
}