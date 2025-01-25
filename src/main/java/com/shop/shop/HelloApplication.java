package com.shop.shop;

import com.shop.shop.controller.CatalogController;
import com.shop.shop.controller.ProductDetailController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        initRootLayout();
        showCatalog();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("/com/shop/shop/root-layout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCatalog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/shop/shop/catalog-view.fxml"));
            Pane catalogView = loader.load();

            CatalogController controller = loader.getController();
            controller.setMainApplication(this);

            rootLayout.setCenter(catalogView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProductDetail(int productId) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/shop/shop/product-detail-view.fxml"));
            Pane detailView = loader.load();

            ProductDetailController controller = loader.getController();
            controller.setMainApplication(this);
            controller.initializeProduct(productId);

            rootLayout.setCenter(detailView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}