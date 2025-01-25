package com.shop.shop.controller;

import com.shop.shop.HelloApplication;
import com.shop.shop.dao.ProductDAO;
import com.shop.shop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import java.util.List;

public class ProductDetailController {
    @FXML private ImageView mainImage;
    @FXML private Label productName;
    @FXML private Label productPrice;
    @FXML private Label productStock;
    @FXML private TextArea productDescription;
    @FXML private FlowPane relatedProducts;

    private Product currentProduct;
    private final ProductDAO productDAO = new ProductDAO();
    private HelloApplication mainApplication;

    public void initializeProduct(int productId) {
        currentProduct = productDAO.getProductById(productId);
        if(currentProduct != null) {
            loadProductDetails();
            loadRelatedProducts();
        }
    }

    private void loadProductDetails() {
        mainImage.setImage(new Image(currentProduct.getImageUrl()));
        productName.setText(currentProduct.getName());
        productPrice.setText(String.format("$%.2f", currentProduct.getPrice()));
        productStock.setText("In Stock: " + currentProduct.getStock());
        productDescription.setText(currentProduct.getDescription());
    }

    private void loadRelatedProducts() {
        List<Product> related = productDAO.getRelatedProducts(
                currentProduct.getCategory(), currentProduct.getId()
        );

        relatedProducts.getChildren().clear();
        for(Product product : related) {
            relatedProducts.getChildren().add(createRelatedProductCard(product));
        }
    }

    public void setMainApplication(HelloApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    private Pane createRelatedProductCard(Product product) {
        VBox card = new VBox(10);
        card.getStyleClass().add("product-card");
        card.setOnMouseClicked(e -> mainApplication.showProductDetail(product.getId()));
        //card.setOnMouseClicked(e -> navigateToProduct(product.getId()));

        ImageView image = new ImageView(new Image(product.getImageUrl()));
        image.setFitWidth(150);
        image.setFitHeight(150);

        Label name = new Label(product.getName());
        name.getStyleClass().add("product-name");

        card.getChildren().addAll(image, name);
        return card;
    }

    @FXML
    private void handleAddToCart() {
        // Implement cart logic
        System.out.println("Added to cart: " + currentProduct.getName());
    }

    private void navigateToProduct(int productId) {
        // Implement navigation logic
    }
}