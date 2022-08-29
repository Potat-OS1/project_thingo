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
import javafx.scene.shape.Shape;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Units {
    int turnCount;
    int state = 0;
    int teamSize = 5;
    Circle[] unit = new Circle[teamSize];
    Circle[] unitMove = new Circle[teamSize];
    Circle[] threat = new Circle[teamSize];
    public void createUnits(){
        for (int i = 0; i < teamSize; i++){
            unit[i] = new Circle();
            unitMove[i] = new Circle();
            threat[i] = new Circle();
        }
        buttonLogic();
    }
    public void buttonLogic(){
        for (int d = 0; d < unit.length; d++){
            unit[d].setOnMousePressed(event ->{
                Node source = (Node) event.getSource();
                String id = source.getId();
                if (state == 0){
                    for (int e = 0; e < unit.length; e++){
                        if (Objects.equals(unit[e].getId(), id)){
                            unitMove[e].setVisible(true);
                            threat[e].setVisible(true);
                            state = 1;
                            System.out.println("on");
                        }
                        else{
                            unitMove[e].setVisible(false);
                            threat[e].setVisible(false);
                        }
                    }
                }
                if (state == 2){
                    for (int e = 0; e < unit.length; e++){
                        unitMove[e].setVisible(false);
                        threat[e].setVisible(false);
                        state = 0;
                    }
                }
                if (state == 1){
                    state = 2;
                    System.out.println(state);

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
    public Node endTurn(double screenx){
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
    public void BaseStatAssign(Pane unitPane, Double screeny, Double screenx, Circle[] champCircle, List<Image> results, List<String> resultNames){
        List<String> champList = Arrays.asList((champCircle[0].getId() + " unit 1"), (champCircle[1].getId() + " unit 2"), (champCircle[2].getId() + " unit 3"), (champCircle[3].getId() + " unit 4"), (champCircle[4].getId() + " unit 5"));
        List<Integer> selectedChamp = Arrays.asList(0, 1, 2, 3, 4);
        List<Double> champThreat = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
        List<Double> champMove = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
        List<Integer> champMoney = Arrays.asList(0,0,0,0,0);
        for (int a = 0; a < champCircle.length; a++) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/champions/" + champCircle[a].getId() + ".txt")));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("range")) {
                        champThreat.set(a, (screeny * Double.parseDouble(line.replace("range ", ""))));
                    }
                    if (line.contains("move")){
                        champMove.set(a, (screeny * Double.parseDouble(line.replace("move ", ""))));
                    }
                }
            } catch (Exception exc) {
                System.out.println("fuck you");
            }
        }
        List<Double> xCoord = Arrays.asList(screenx * .215, screenx * .226, screenx * .236, screenx * .245, screenx * .25);
        List<Double> yCoord = Arrays.asList(screeny * .9, screeny * .903, screeny * .91, screeny * .922, screeny * .94);
        for (int i = 0; i < unit.length; i++){
            unit[i].setId(champList.get(selectedChamp.get(i)));
            unit[i].setCenterX(xCoord.get(i));
            unit[i].setCenterY(yCoord.get(i));
            unit[i].setFill(new ImagePattern(results.get(resultNames.indexOf(champCircle[i].getId() + ".png"))));
            unit[i].setRadius(screeny * .01);
            unitMove[i].setId(champList.get(selectedChamp.get(i)) + "'s Movement Range");
            unitMove[i].setRadius(champMove.get(i));
            unitMove[i].setCenterX(xCoord.get(i));
            unitMove[i].setCenterY(yCoord.get(i));
            unitMove[i].setStroke(new Color(.7, 0,.7, 1));
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
    }
}
