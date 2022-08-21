package com.example.app;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class InformationPane extends InGameScreen{
    public Node leftInfoPane(){
        MyApplication grabber = new MyApplication();
        double screenx = grabber.screenx;
        double screeny = grabber.screeny;
        infoPane.setBackground(Background.fill(new Color(1.0 ,1.0, 1.0, 1.0)));
        infoPane.setMinSize((screenx * .2), (screeny));
        infoPane.getChildren().add(section);
        return infoPane;
    }
    public Node returnInformation(Shape input, ArrayList<Double> hpInformation){
        MyApplication grabber = new MyApplication();
        double screenx = grabber.screenx;
        double screeny = grabber.screeny;

        Pane info = new Pane();
        info.setPadding(new Insets(1.0, 1.0, 1.0, 1.0));
        VBox infoVbox = new VBox();
        Rectangle hpBar = new Rectangle();
        hpBar.setFill(new Color(1.0, 0.0, 0.35, 1.0));
        hpBar.setWidth(screenx * 0.18);
        hpBar.setHeight(screeny * 0.05);
        hpBar.setLayoutX(screenx * 0.01);

        String target = input.getId();
        target = target.replace("Nexus ", "");
        target = target.replace("Turret ", "");
        target = target.replace("Inhibitor ", "");
        target = target.replace("Tier 1 ", "");
        target = target.replace("Tier 2 ", "");
        target = target.replace("ID no: ", "");

        Label hpAmt = new Label(String.valueOf(hpInformation.get(Integer.parseInt(target))));
        hpAmt.setTextFill(new Color(0.0, 0.0, 0.0, 1.0));
        hpAmt.setMinSize(screenx * 0.18, screeny * 0.05);
        hpAmt.setLayoutX(screenx * 0.01);

        infoVbox.getChildren().addAll(hpBar, hpAmt);
        info.getChildren().addAll(infoVbox);
        System.out.println(infoPane.getChildren());
        return info;
    }
}
