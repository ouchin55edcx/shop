package com.shop.shop.dao;

import com.shop.shop.model.Product;
import com.shop.shop.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {
    private static final Logger logger = Logger.getLogger(ProductDAO.class.getName());

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT id, name, description, price, stock FROM products";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock")
                ));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch products", e);
        }
        return products;
    }
}