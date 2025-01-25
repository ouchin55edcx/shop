package com.shop.shop.controller;

import com.shop.shop.dao.ProductDAO;
import com.shop.shop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import java.util.List;

public class CatalogController {
    private static final int ITEMS_PER_PAGE = 12;

    @FXML private FlowPane productGrid;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private TextField searchField;
    @FXML private Pagination pagination;

    private ProductDAO productDAO = new ProductDAO();

    @FXML
    public void initialize() {
        setupCategoryFilter();
        setupPagination();
        loadPage(1);
    }

    private void setupCategoryFilter() {
        categoryFilter.getItems().add("All Categories");
        categoryFilter.getItems().addAll(productDAO.getAllCategories());
        categoryFilter.getSelectionModel().selectFirst();
        categoryFilter.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> handleSearch()
        );
    }

    private void setupPagination() {
        pagination.currentPageIndexProperty().addListener(
                (obs, oldVal, newVal) -> loadPage(newVal.intValue() + 1)
        );
    }

    private void loadPage(int page) {
        String searchText = searchField.getText().trim();
        String category = categoryFilter.getValue().equals("All Categories") ? "" : categoryFilter.getValue();

        List<Product> products = productDAO.getProductsByPage(
                page, ITEMS_PER_PAGE, searchText, category
        );

        updateProductGrid(products);
        updatePaginationCount(searchText, category);
    }

    private void updateProductGrid(List<Product> products) {
        productGrid.getChildren().clear();

        for (Product product : products) {
            VBox card = createProductCard(product);
            productGrid.getChildren().add(card);
        }
    }

    private VBox createProductCard(Product product) {
        VBox card = new VBox(10);
        card.getStyleClass().add("product-card");

        ImageView imageView = new ImageView(new Image(product.getImageUrl()));
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);

        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("product-name");

        Label priceLabel = new Label(String.format("$%.2f", product.getPrice()));
        priceLabel.getStyleClass().add("product-price");

        card.getChildren().addAll(imageView, nameLabel, priceLabel);
        return card;
    }

    private void updatePaginationCount(String searchText, String category) {
        int totalItems = productDAO.getTotalProductsCount(searchText, category);
        int pageCount = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
        pagination.setPageCount(pageCount);
    }

    @FXML
    private void handleSearch() {
        pagination.setCurrentPageIndex(0);
        loadPage(1);
    }
}