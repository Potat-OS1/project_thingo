package com.example.app;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class InformationPane{
    public Node leftInfoPane(Pane infoPane, VBox section){
        MyApplication grabber = new MyApplication();
        double screenx = grabber.screenx;
        double screeny = grabber.screeny;
        infoPane.setBackground(Background.fill(new Color(0.5 ,0.5, 0.5, 1.0)));
        infoPane.setMinSize((screenx * .2), (screeny));
        infoPane.getChildren().add(section);
        Rectangle oi = new Rectangle();
        section.getChildren().add(oi);
        return infoPane;
    }
    public void returnInformation(Node input, String hp, VBox section){
        MyApplication grabber = new MyApplication();
        double screenx = grabber.screenx;
        double screeny = grabber.screeny;

        VBox info = new VBox();
        Pane hpBox = new Pane();
        Pane namePane = new Pane();

        info.setPadding(new Insets(screeny * 0.01, screeny * 0.01, screeny * 0.01, screeny * 0.01));
        info.setSpacing(screeny * 0.01);
        info.setBackground(Background.fill(new Color(1.0, 1.0, 1.0, 1.0)));
        info.setMinWidth(screenx * 0.2);

        Rectangle hpBar = new Rectangle();
        hpBar.setFill(new Color(1.0, 0.0, 0.35, 1.0));
        hpBar.setWidth(screenx * 0.19);
        hpBar.setHeight(screeny * 0.05);

        hpBox.setBackground(Background.fill(new Color(0.1, 0.1, 0.1, 1.0)));
        hpBox.setMaxSize(screenx * 0.189, screeny * 0.05);

        Label hpAmt = new Label(hp);
        hpAmt.setTextFill(new Color(0.0, 0.0, 0.0, 1.0));
        hpAmt.setMinSize(screenx * 0.19, screeny * 0.05);
        hpAmt.setLayoutX(screeny * 0.01);
        hpAmt.setFont(new Font("Segoe UI", screenx * 0.01));

        String name = input.getId();
        name = name.replace(" ID no: ", "");
        while (Character.isDigit(name.charAt(name.length() - 1))){
            name = name.substring(0, name.length() - 1);
            System.out.println("aaaa");
        }

        Label nameDisplay = new Label(name);
        nameDisplay.setFont(new Font("Times New Roman", screeny * 0.025));
        nameDisplay.setLayoutX(screeny * 0.01);
        nameDisplay.setTextFill(new Color(0.0,0.0,0.0, 1.0));

        namePane.setMinSize(screenx * 0.19, screeny * 0.03);
        namePane.setMaxSize(screenx * 0.19, screeny * 0.03);
        namePane.setBackground(Background.fill(new Color(0.2, 0.2, 0.2, 1.0)));
        namePane.getChildren().add(nameDisplay);

        hpBox.getChildren().addAll(hpBar, hpAmt);
        info.getChildren().addAll(namePane, hpBox);
        try{
            section.getChildren().set(0, info);
        }
        catch (Exception exc){
            section.getChildren().add(info);
        }
    }
}
