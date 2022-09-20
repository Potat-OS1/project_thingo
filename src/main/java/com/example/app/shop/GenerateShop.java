package com.example.app.shop;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.example.app.InGameScreen.shopPane;
import static com.example.app.MyApplication.screenx;
import static com.example.app.MyApplication.screeny;

public class GenerateShop {
    public void shopCreate(){
        Pane shop = new Pane();
        shop.setPrefSize(screenx * 0.6, screeny * 0.8);
        shop.setLayoutX(screenx * 0.2);
        shop.setLayoutY(screeny * 0.1);
        shop.setBackground(Background.fill(new Color(1.0, 1.0, 1.0, 1.0)));

        double spacing = screenx * 0.01;
        double iconSize = screenx * 0.085;

        //hey dumbass do the widgets here the next time you code. it goes on top of itemCategory
        //

        HBox itemCategory = new HBox();
        itemCategory.setSpacing(screenx * 0.01);
        itemCategory.setMinWidth(screenx * 0.1);
        itemCategory.setMinHeight(screenx * 0.1);
        itemCategory.setFillHeight(true);
        itemCategory.setPadding(new Insets(spacing, spacing, spacing, spacing));

        itemCategory.setBackground(Background.fill(new Color(0.7, 0.7, 0.7, 1.0)));
        VBox shopContainer = new VBox();
        shopContainer.setPadding(new Insets(spacing, spacing, spacing, spacing));
        String imageBase = "/shop/categories/";
        String imageEnd = ".png";
        List<String> imageName = Arrays.asList("Power", "Magic", "Speed", "Defence", "Resistances", "All");

        for (int i = 0; i < 6; i++){
            try{
                URL imageURL = getClass().getResource(imageBase + imageName.get(i) + imageEnd);
                Image image = new Image(imageURL.toExternalForm());
                ImageView view = new ImageView(image);
                view.setFitHeight(iconSize);
                view.setFitWidth(iconSize);
                itemCategory.getChildren().add(view);
            }
            catch(Exception exc){
                exc.getCause();
                Rectangle tile = new Rectangle(iconSize, iconSize);
                tile.setFill(new Color(1.0, 0.1 * i, 0.0, 1.0));
                itemCategory.getChildren().add(tile);
            }
        }
        shopContainer.getChildren().add(itemCategory);
        shop.getChildren().addAll(shopContainer);
        try{
            shopPane.getChildren().set(0, shop);
        }
        catch(Exception aaaa){
            shopPane.getChildren().add(shop);
        }
    }
}
