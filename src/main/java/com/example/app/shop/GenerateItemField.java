package com.example.app.shop;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GenerateItemField {
    public static Node ItemField(List<List<String>> itemListings, String selectedTab, double spacing, double iconSize){
        VBox itemFieldVBox = new VBox();
        List<HBox> hboxes = new ArrayList<>();
        if (!selectedTab.equals("")){
            int i = 0;
            int b = 0;
            int perRow = 8;
            int rowCount = (int) Math.ceil(itemListings.size() / (double) perRow);
            for (int a = 0; rowCount > a; a++){
                HBox row = new HBox();
                row.setSpacing(spacing);
                hboxes.add(row);
            }
            for (List<String> item : itemListings){
                if (b <= hboxes.size()){
                    if (i < perRow){
                        try{
                            Rectangle itemBounds = new Rectangle(iconSize * 0.8, iconSize * 0.8);
                            URL imageURL = GenerateItemField.class.getResource("/shop/items/" + item.get(0) + ".png");
                            Image image = new Image(imageURL.toExternalForm());
                            itemBounds.setFill(new ImagePattern(image));
                            hboxes.get(b).getChildren().add(itemBounds);
                        }
                        catch(Exception exc){
                            Rectangle itemBounds = new Rectangle(iconSize * 0.8, iconSize * 0.8);
                            itemBounds.setFill(new Color(0.0, 0.0, 0.0, 1.0));
                            hboxes.get(b).getChildren().add(itemBounds);
                        }
                        i++;
                    }
                    if (i == perRow){
                        i = 0;
                        b++;
                    }
                }
            }
            itemFieldVBox.setPadding(new Insets(spacing, spacing, spacing, spacing));
            for (HBox rows : hboxes){
                itemFieldVBox.getChildren().add(rows);
            }
        }
        return itemFieldVBox;
    }
}
