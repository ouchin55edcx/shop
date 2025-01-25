package com.shop.shop.dao;

import com.shop.shop.model.Product;
import com.shop.shop.util.DBConnection;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {
    private static final Logger logger = Logger.getLogger(ProductDAO.class.getName());

    public List<Product> getProductsByPage(int page, int itemsPerPage, String searchQuery, String category) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE (name LIKE ? OR description LIKE ?) " +
                "AND category LIKE ? LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + searchQuery + "%");
            stmt.setString(2, "%" + searchQuery + "%");
            stmt.setString(3, "%" + category + "%");
            stmt.setInt(4, itemsPerPage);
            stmt.setInt(5, (page - 1) * itemsPerPage);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("category"),
                        rs.getString("image_url")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching products", e);
        }
        return products;
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT category FROM products")) {

            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching categories", e);
        }
        return categories;
    }

    public int getTotalProductsCount(String searchQuery, String category) {
        String query = "SELECT COUNT(*) FROM products WHERE (name LIKE ? OR description LIKE ?) AND category LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + searchQuery + "%");
            stmt.setString(2, "%" + searchQuery + "%");
            stmt.setString(3, "%" + category + "%");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error counting products", e);
        }
        return 0;
    }
}