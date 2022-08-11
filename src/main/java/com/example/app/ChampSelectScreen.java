package com.example.app;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChampSelectScreen {
    String focusedChampion = null;
    public Node champSelect(double screenx, double screeny){
        Pane select = new Pane();
        select.setMinWidth(screenx);
        select.setMinHeight(screeny);
        select.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Button removeThis = new Button();
        removeThis.setOnAction((ActionEvent) ->{
            select.setVisible(false);
            removeThis.setVisible(false);

        });

        select.getChildren().addAll(selectedSlot(screenx, screeny), lockedIn(screenx, screeny), container(screenx, screeny), removeThis, lockin(screenx, screeny));

        //toggle for when im working on the game and not champ select.
        select.setVisible(false);

        return select;
    }
    public Node lockedIn(double screenx, double screeny){
        Circle[] championCircle = new Circle[]{
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
        };
        VBox circleHBox = new VBox();
        circleHBox.setLayoutX(screenx * .05);
        circleHBox.setLayoutY(screeny * .025);
        circleHBox.setSpacing(10);

        int increment = 0;
        for (Circle circle : championCircle){
            circleHBox.getChildren().add(circle);
            circle.setFill(new Color(1.0, 1.0, 1.0, 1.0));
            increment++;
            circle.setRadius(screenx * .05);
            circle.setId(String.valueOf(increment));
            circle.setOnMousePressed(event ->{
                final Node source = (Node) event.getSource();
                String id = source.getId();
                System.out.println(id);
            });
        }
        return circleHBox;
    }
    public Node container(double screenx, double screeny){
        ScrollPane championContainer = new ScrollPane();
        championContainer.setStyle("-fx-background: #ffffff;");
        VBox champVBOX = new VBox();
        champVBOX.setMinSize((screenx * .592), screeny * .85);
        champVBOX.setLayoutX((screenx - champVBOX.getWidth()) / 2);
        champVBOX.setLayoutY(screeny * .02);
        championContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        championContainer.setLayoutY(screeny * .02);
        championContainer.setLayoutX(screenx * .2);
        championContainer.setMinSize((screenx * .6), screeny * .85);
        championContainer.setMaxHeight(screeny * .85);
        championContainer.setContent(champVBOX);
        //content of container
        champ(champVBOX, screenx);

        return championContainer;
    }

    public void champ(VBox champVBOX, double screenx){
        int champAmt = 20;
        //Double hbamt = Math.ceil(champAmt / 4);
        int hboxAmt = (int) Math.ceil(champAmt / 4);
        HBox[] champions = new HBox[hboxAmt];
        Rectangle[] champIcon = new Rectangle[champAmt];

        for(int a = 0; a < hboxAmt; a++){
            champions[a] = new HBox();
            champions[a].setPadding(new Insets(5.0, 5.0, 5.0,5.0));
            champions[a].setSpacing(10.0);
            champVBOX.getChildren().add(champions[a]);
        }

        for (int i = 0; i < champAmt; i++){
            champIcon[i] = new Rectangle();
            champIcon[i].setHeight(((screenx * .6) - 56) /4);
            champIcon[i].setWidth(((screenx * .6) - 56) /4);
            champIcon[i].setFill(new Color(1.0,0.0, 0.0, 1.0));
            Double assignedRow = Math.ceil(i / 4);
            champIcon[i].setOnMousePressed(event ->{
                final Node source = (Node) event.getSource();
                String id = source.getId();
                System.out.println(id);
                focusedChampion = id;
            });
            champions[assignedRow.intValue()].getChildren().add(champIcon[i]);
        }
        for(HBox hbox : champions){
            hbox.setMinWidth(screenx * .5);
            hbox.setMaxWidth(screenx * .5);
        }
        champPortrait(champIcon);
    }

    public Node selectedSlot(double screenx, double screeny){
        Rectangle slot = new Rectangle();
        slot.setHeight(screeny * .188);
        slot.setWidth(screenx * .2);
        slot.setLayoutX(screenx * .04);
        slot.setLayoutY(screeny * .02);
        slot.setFill(new Color(0.0,0.0,1.0, 0.5));

        return slot;
    }

    public Node lockin(double screenx, double screeny){
        Polygon lockin = new Polygon();
        lockin.getPoints().addAll(new Double[]{
                0.0, 0.0,
                20.0, -10.0,
                120.0, -10.0,
                140.0, 0.0,
                140.0, 40.0,
                120.0, 50.0,
                20.0, 50.0,
                0.0, 40.0,
        });
        lockin.setLayoutX((screenx - lockin.getLayoutBounds().getWidth()) / 2);
        lockin.setLayoutY(screeny * .85);
        lockin.setScaleX(screenx * .0015);
        lockin.setScaleY(screenx * .0015);
        lockin.setFill(new Color(0.0, 1.0, 1.0, 1.0));

        return lockin;
    }

    public void champPortrait(Rectangle[] champIcon) {
        //this code reads the champions_list.txt and applies the names to each of the icons ID.
        List<Image> results = new ArrayList<>();
        List<String> resultNames = new ArrayList<String>();
        String imageBase = "/champions/";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/champions/champions_list.txt")));
            br.lines().forEach(imageName -> {
                URL imageURL = getClass().getResource(imageBase + imageName);
                Image image = new Image(imageURL.toExternalForm());
                results.add(image);
                resultNames.add(imageName);
            });
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        for (int a = 0; a < results.size(); a++){
            champIcon[a].setFill(new ImagePattern(results.get(a)));
            String id = resultNames.get(a).replace(".png", "");
            champIcon[a].setId(id);
        }
    }
}
