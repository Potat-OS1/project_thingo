package com.example.app.shop;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.app.InGameScreen.shopPane;
import static com.example.app.MyApplication.screenx;
import static com.example.app.MyApplication.screeny;
import static com.example.app.shop.GenerateItemField.ItemField;

public class GenerateShop {
    String selectedTab = "";
    ScrollPane itemField = new ScrollPane();
    VBox shopContainer = new VBox();
    List<List<String>> healthItems = new ArrayList<>();
    List<List<String>> powerItems = new ArrayList<>();
    List<List<String>> magicItems = new ArrayList<>();
    List<List<String>> speedItems = new ArrayList<>();
    List<List<String>> defenceItems = new ArrayList<>();
    List<List<String>> resistanceItems = new ArrayList<>();
    List<List<String>> allItems = new ArrayList<>();
    double spacing = screenx * 0.01;
    double iconSize = screenx * 0.0715;
    public void shopCreate(){
        Pane shop = new Pane();
        shop.setPrefSize(screenx * 0.6, screeny * 0.8);
        shop.setLayoutX(screenx * 0.2);
        shop.setLayoutY(screeny * 0.1);
        shop.setBackground(Background.fill(new Color(1.0, 1.0, 1.0, 1.0)));


        shopContainer.getChildren().addAll(widgets(), ItemCategory(), itemField);
        shopContainer.setPadding(new Insets(spacing, spacing, spacing, spacing));

        itemField.setVisible(false);

        //hey dumbass do the widgets here the next time you code. it goes on top of itemCategory
        //widgets include targetted Units shops

        populateItemFields();

        shop.getChildren().addAll(shopContainer);
        try{
            shopPane.getChildren().set(0, shop);
        }
        catch(Exception aaaa){
            shopPane.getChildren().add(shop);
        }
    }
    public Node widgets(){
        Pane widget = new Pane();
        return widget;
    }
    public void populateItemFields(){
        List<String> itemList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/shop/items/items_list.txt"))));
            br.lines().forEach(itemList::add);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        for (String listedItem : itemList){
            try {
                List<String> item = new ArrayList<>();
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/shop/items/" + listedItem + ".txt"))));
                br.lines().forEach(item::add);
                for (String get : item){
                    switch(get){
                        case "Health" -> healthItems.add(item);
                        case "Power" -> powerItems.add(item);
                        case "Magic" -> magicItems.add(item);
                        case "Speed" -> speedItems.add(item);
                        case "Defence" -> defenceItems.add(item);
                        case "Resistances" -> resistanceItems.add(item);
                    }
                }
                allItems.add(item);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        for (List<String> entry : powerItems){
            System.out.println(entry);
        }
    }
    public Node ItemCategory(){
        HBox itemCategory = new HBox();
        itemCategory.setSpacing(screenx * 0.01);
        itemCategory.setFillHeight(true);
        itemCategory.setPadding(new Insets(spacing, spacing, spacing, spacing));
        itemCategory.setBackground(Background.fill(new Color(0.7, 0.7, 0.7, 1.0)));
        String imageBase = "/shop/categories/";
        String imageEnd = ".png";
        List<String> imageName = Arrays.asList("Health", "Power", "Magic", "Speed", "Defence", "Resistances", "All");

        for (int i = 0; i < 7; i++){
            try{
                URL imageURL = getClass().getResource(imageBase + imageName.get(i) + imageEnd);
                assert imageURL != null;
                Image image = new Image(imageURL.toExternalForm());
                ImageView view = new ImageView(image);
                view.setFitHeight(iconSize);
                view.setFitWidth(iconSize);
                view.setId(imageName.get(i));
                view.setOnMouseClicked(event ->{
                    itemField.setVisible(true);
                    Node source = (Node) event.getSource();
                    selectedTab = source.getId();
                    itemField.setContent(null);
                    switch(source.getId()){
                        case "Power" -> itemField.setContent((ItemField(powerItems, selectedTab, spacing, iconSize)));
                        case "Magic" -> itemField.setContent((ItemField(magicItems, selectedTab, spacing, iconSize)));
                        case "Health" -> itemField.setContent((ItemField(healthItems, selectedTab, spacing, iconSize)));
                        case "Speed" -> itemField.setContent((ItemField(speedItems, selectedTab, spacing, iconSize)));
                        case "Defence" -> itemField.setContent((ItemField(defenceItems, selectedTab, spacing, iconSize)));
                        case "Resistances" -> itemField.setContent((ItemField(resistanceItems, selectedTab, spacing, iconSize)));
                        case "All" -> itemField.setContent((ItemField(allItems, selectedTab, spacing, iconSize)));
                    }
                    System.out.println(shopContainer.getChildren());
                });
                itemCategory.getChildren().add(view);
            }
            catch(Exception exc){
                exc.getCause();
                Rectangle tile = new Rectangle(iconSize, iconSize);
                tile.setFill(new Color(1.0, 0.1 * i, 0.0, 1.0));
                itemCategory.getChildren().add(tile);
                //placeholder code
                tile.setId(imageName.get(i));
                tile.setOnMouseClicked(event ->{
                    Node source = (Node) event.getSource();
                    selectedTab = source.getId();
                    System.out.println(selectedTab);
                    switch(source.getId()){
                        case "Power" -> System.out.println("There is " + powerItems.size() + " items in the Power category");
                        case "Magic" -> System.out.println("There is " + magicItems.size() + " items in the Magic category");
                        case "Health" -> System.out.println("There is " + healthItems.size() + " items in the Health category");
                        case "Speed" -> System.out.println("There is " + speedItems.size() + " items in the Speed category");
                        case "Defence" -> System.out.println("There is " + defenceItems.size() + " items in the Defence category");
                        case "Resistances" -> System.out.println("There is " + resistanceItems.size() + " items in the Resistances category");
                        case "All" -> System.out.println("There is " + allItems.size() + " items in the All category");
                    }
                });
                //
            }
        }
        return itemCategory;
    }
}
