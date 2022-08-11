package com.example.app;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InGameScreen {
    int turnCount;
    public Node GameScreen(double screenx, double screeny){
        Pane gameScreen = new Pane();

        int teamSize = 5;
        Circle[] unit = new Circle[teamSize];
        Circle[] unitMove = new Circle[teamSize];
        Circle[] threat = new Circle[teamSize];
        for (int i = 0; i < teamSize; i++){
            unit[i] = new Circle();
        }
        for (int i = 0; i < teamSize; i++){
            unitMove[i] = new Circle();
        }
        for (int i = 0; i < teamSize; i++){
            threat[i] = new Circle();
        }
        gameScreen.getChildren().addAll(createBackGround(screenx, screeny), unitDraw(unitMove, unit, threat), EndTurn(unitMove, unit, screenx));
        gameScreen.setMinSize(screenx, screeny);
        return gameScreen;
    }

    public Node unitDraw(Circle[] unitMove, Circle[] unit, Circle[] threat){
        Pane unitPane = new Pane();

        List<String> champList = Arrays.asList("Totally not Darius", "Totally not Ornn", "Totally not Ashe", "Totally not Talon", "Totally not Soraka");
        List<Integer> selectedChamp = Arrays.asList(0, 1, 2, 4, 3);
        List<Double> champThreat = Arrays.asList(20.0, 20.0, 40.0, 25.0, 35.0);
        List<Double> champMove = Arrays.asList(50.0, 40.0, 35.0, 50.0, 35.0);
        List<Double> xCoord = Arrays.asList(200.0, 300.0, 400.0, 450.0, 490.0);
        List<Double> yCoord = Arrays.asList(200.0, 300.0, 400.0, 450.0, 470.0);
        for (int i = 0; i < unit.length; i++){
            unit[i].setId(champList.get(selectedChamp.get(i)));
            unit[i].setCenterX(xCoord.get(i));
            unit[i].setCenterY(yCoord.get(i));
            unit[i].setFill(new Color(0,0,1.0, 1.0));
            unit[i].setRadius(8.0);

            unitMove[i].setId(champList.get(selectedChamp.get(i)) + "'s Movement Range");
            unitMove[i].setRadius(champMove.get(i));
            unitMove[i].setCenterX(xCoord.get(i));
            unitMove[i].setCenterY(yCoord.get(i));
            unitMove[i].setStroke(new Color(1.0, 0,1.0, 1));
            unitMove[i].setStrokeWidth(3.0);
            unitMove[i].setFill(Color.TRANSPARENT);
            unitMove[i].setVisible(false);

            threat[i].setRadius(champThreat.get(i));
            threat[i].setCenterX(xCoord.get(i));
            threat[i].setCenterY(yCoord.get(i));
            threat[i].setFill(new Color(1.0, 0.0 ,0.5, .25));
            threat[i].setVisible(false);
            unitPane.getChildren().addAll(threat[i], unitMove[i], unit[i]);
        }
        buttonLogic(unitMove, unit, threat);

        return unitPane;
    }

    public void buttonLogic(Circle[] unitMove, Circle[] unit, Circle[] threat){
        for (int d = 0; d < unit.length; d++){
            unit[d].setOnMousePressed(event ->{
                Node source = (Node) event.getSource();
                String id = source.getId();
                for (int e = 0; e < unit.length; e++){
                    if (Objects.equals(unit[e].getId(), id)){
                        unitMove[e].setVisible(true);
                        threat[e].setVisible(true);
                    }
                    else{
                        unitMove[e].setVisible(false);
                        threat[e].setVisible(false);
                    }
                }
            });
            unitMove[d].setOnMousePressed(event ->{
                Node source = (Node) event.getSource();
                String id = source.getId();
                for (int f = 0; f < unitMove.length; f++){
                    if (Objects.equals(unitMove[f].getId(), id)){
                        unit[f].setCenterX(event.getX());
                        unit[f].setCenterY(event.getY());
                        threat[f].setCenterX(unit[f].getCenterX());
                        threat[f].setCenterY(unit[f].getCenterY());
                    }
                }
            });
        }
    }

    private Node EndTurn(Circle[] unitMove, Circle[] unit, double screenx){

        Button endTurn = new Button("End Turn");
        Label tCount = new Label("Turn: " + turnCount);

        endTurn.setOnAction(ActionEvent ->{
            turnCount++;
            for (int i = 0; i < unit.length; i++){
                unitMove[i].setCenterX(unit[i].getCenterX());
                unitMove[i].setCenterY(unit[i].getCenterY());
            }
            tCount.setText("Turn: " + turnCount);
        });

        VBox endBtnBox = new VBox();

        endBtnBox.getChildren().addAll(endTurn, tCount, detectionButton(unitMove, unit));
        endBtnBox.setMinWidth(65.0);
        tCount.setAlignment(Pos.CENTER);
        tCount.setMaxWidth(Double.MAX_VALUE);
        tCount.setTextFill(Color.WHITE);
        endBtnBox.setLayoutX(screenx * .95);
        return endBtnBox;
    }

    public Node detectionButton(Circle[] unitMove, Circle[] unit){
        Button detection = new Button("detect");
        detection.setOnAction(event -> {
            for (int a = 0; a < unit.length; a++){
                Shape u1Intersect = Shape.intersect(unitMove[0], unit[a]);
                if (u1Intersect.getBoundsInLocal().getWidth() != -1 && unit[a] != unit[0]){
                    System.out.println("Hi " + unit[a].getId() + " in " + unit[0].getId() +  "'s range");
                }
            }
        });
        return detection;
    }

    private Node createBackGround(double screenx, double screeny){
        InputStream BG = MyApplication.class.getResourceAsStream("/map.png");
        assert BG != null;
        Rectangle background = new Rectangle();
        background.setFill(new ImagePattern(new Image(BG)));
        background.setWidth(screenx);
        background.setHeight(screeny);
        return background;
    }
}
