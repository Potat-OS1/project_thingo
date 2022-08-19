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
import java.util.List;
import java.util.Objects;

public class InGameScreen {
    int turnCount;
    int teamSize = 5;
    public Circle[] unit = new Circle[teamSize];
    Circle[] unitMove = new Circle[teamSize];
    Circle[] threat = new Circle[teamSize];
    public Node GameScreen(double screenx, double screeny, Circle[] championCircle, List<Image> results, List<String> resultNames){
        Pane gameScreen = new Pane();
        Structures structures = new Structures();
        for (int i = 0; i < teamSize; i++){
            unit[i] = new Circle();
            unitMove[i] = new Circle();
            threat[i] = new Circle();
        }
        Pane interactibles = new Pane();
        interactibles.getChildren().addAll(
                structures.createStructures(screenx, screeny),
                unitDraw(screeny, screenx, championCircle, results, resultNames));

        gameScreen.getChildren().addAll(createBackGround(screenx, screeny),
                EndTurn(screenx),
                interactibles);
        gameScreen.setMinSize(screenx, screeny);
        return gameScreen;
    }

    public Node unitDraw(double screeny, double screenx, Circle[] championCircle, List<Image> results, List<String> resultNames){
        Pane unitPane = new Pane();
        ChampionAssign assign = new ChampionAssign();
        assign.BaseStatAssign(unit, unitMove, threat, unitPane, screeny, screenx, championCircle, results, resultNames);
        buttonLogic();

        return unitPane;
    }

    public void buttonLogic(){
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

    private Node EndTurn(double screenx){

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

        endBtnBox.getChildren().addAll(endTurn, tCount, detectionButton());
        endBtnBox.setMinWidth(65.0);
        tCount.setAlignment(Pos.CENTER);
        tCount.setMaxWidth(Double.MAX_VALUE);
        tCount.setTextFill(Color.WHITE);
        endBtnBox.setLayoutX(screenx * .95);
        return endBtnBox;
    }

    public Node detectionButton(){
        Button detection = new Button("detect");
        detection.setOnAction(event -> {
            for (int a = 0; a < unit.length; a++){
                Shape u1Intersect = Shape.intersect(threat[0], unit[a]);
                if (u1Intersect.getBoundsInLocal().getWidth() != -1 && unit[a] != unit[0]){
                    System.out.println("Hi " + unit[a].getId() + " in " + unit[0].getId() +  "'s range");
                }
                Shape u2Intersect = Shape.intersect(threat[1], unit[a]);
                if (u2Intersect.getBoundsInLocal().getWidth() != -1 && unit[a] != unit[1]){
                    System.out.println("Hi " + unit[a].getId() + " in " + unit[1].getId() +  "'s range");
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
