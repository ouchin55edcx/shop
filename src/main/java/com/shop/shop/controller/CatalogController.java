package com.shop.shop.controller;

import com.shop.shop.dao.ProductDAO;
import com.shop.shop.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class CatalogController {
    @FXML
    private TableView<Product> productTable;

    @FXML
    public void initialize() {
        // Fetch data from DAO
        ProductDAO productDAO = new ProductDAO();
        ObservableList<Product> products = FXCollections.observableArrayList(
                productDAO.getAllProducts()
        );

        // Bind data to TableView
        productTable.setItems(products);
    }
}