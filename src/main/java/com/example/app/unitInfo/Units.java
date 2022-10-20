package com.example.app.unitInfo;

import com.example.app.Actions;
import com.example.app.TestEnemySpawn;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.app.InGameScreen.pingPane;
import static com.example.app.InGameScreen.shopPane;
import static com.example.app.unitInfo.UnitSpecifics.*;

public class Units{
    boolean shopToggle = false;
    int turnCount;
    static int state = 0;
    static int teamSize = 5;
    public static Circle[] unit = new Circle[teamSize];
    public static Circle[] unitMove = new Circle[teamSize];
    public static Circle[] threat = new Circle[teamSize];
    public static Circle[] trueThreat = new Circle[teamSize];

    public static List<String> champList = new ArrayList<>();

    public static double coordx = 0.0;
    public static double coordy = 0.0;
    TestEnemySpawn tes = new TestEnemySpawn();

    public void createUnits(Pane actionPane){
        for (int i = 0; i < teamSize; i++){
            unit[i] = new Circle();
            unitMove[i] = new Circle();
            threat[i] = new Circle();
            trueThreat[i] = new Circle();
        }
        buttonLogic(actionPane);
    }
    public void buttonCancel(Circle move, Pane actionPane){
            actionPane.setVisible(false);
            state = 3;
            move.setCursor(Cursor.DEFAULT);
            String id = move.getId();
            int idIndex = move.getId().length();
            if (move.getId().contains("'")){
                idIndex = id.indexOf("'");
            }
            else{
                System.out.println(" ");
            }
            StringBuilder searchId = new StringBuilder();
            for (int b = 0; b < idIndex; b++){
                searchId.append(id.charAt(b));
            }
            for (Circle Unitc : unit) {
                if (Unitc.getId().contains(searchId.toString())){
                    if (Unitc.getCenterX() != coordx){
                        Unitc.setCenterX(coordx);
                        Unitc.setCenterY(coordy);
                    }
                }
            }
            for (Circle range : unitMove) {
                if (range.getId().contains(searchId.toString())){
                    if (range.getCenterX() != coordx){
                        range.setCenterX(coordx);
                        range.setCenterY(coordy);
                    }
                }
                if (range.isVisible()) {
                    range.setVisible(false);
                }
            }
            for (Circle threatRange : threat) {
                if (threatRange.getId().contains(searchId.toString())){
                    if (threatRange.getCenterX() != coordx){
                        threatRange.setCenterX(coordx);
                        threatRange.setCenterY(coordy);
                    }
                }
                if (threatRange.isVisible()) {
                    threatRange.setVisible(false);
                }
            }
            for (Circle truethreat : trueThreat){
                truethreat.setVisible(false);
            }
            pingPane.getChildren().clear();
            pingPane.setVisible(false);
            coordx = 0;
            coordy = 0;
    }
    public void buttonLogic(Pane actionPane){
        for (Circle Unit: unit){
            Unit.setOnMouseEntered(event ->{
                Node source = (Node) event.getSource();
                if (state == 0 || state == 3){
                    for (Circle range: unitMove){
                        if (range.getId().contains(source.getId())){
                            range.setVisible(true);
                            range.setOpacity(0.5);
                        }
                    }
                    for (Circle threatRange: threat){
                        if (threatRange.getId().contains(source.getId())){
                            threatRange.setVisible(true);
                            threatRange.setOpacity(0.4);
                        }
                    }
                }
            });
            Unit.setOnMouseExited(event ->{
                if (state == 0 || state == 3){
                    Node source = (Node) event.getSource();
                    for (Circle range: unitMove){
                        if (range.getId().contains(source.getId())){
                            range.setVisible(false);
                        }
                    }
                    for (Circle threatRange: threat){
                        if (threatRange.getId().contains(source.getId())){
                            threatRange.setVisible(false);
                        }
                    }
                }
            });
            Unit.setOnMousePressed(event -> {
                Node source = (Node) event.getSource();
                if (event.getButton() == MouseButton.PRIMARY){
                    for (Circle Unitc: unit){
                        if (Unitc.getId().contains(source.getId()) && ((state == 0) || (state == 3))){
                            coordx = Unitc.getCenterX();
                            coordy = Unitc.getCenterY();
                        }
                    }
                    for (Circle range: unitMove){
                        if (range.getId().contains(source.getId())){
                            range.setOpacity(0.8);
                        }
                    }
                    for (Circle threatRange: threat){
                        if (threatRange.getId().contains(source.getId())){
                            threatRange.setOpacity(0.8);
                        }
                    }
                }
                state = 1;
                if (event.getButton() == MouseButton.SECONDARY){
                    buttonCancel(Unit, actionPane);
                    state = 3;
                }
            });
        }
        for (Circle move: unitMove){
            move.setOnMousePressed(event ->{
                if (event.getButton() == MouseButton.SECONDARY){
                    buttonCancel(move, actionPane);
                    state = 3;
                }
                Node source = (Node) event.getSource();
                String id = source.getId();
                if (event.getButton() == MouseButton.PRIMARY){
                    for (int f = 0; f < unitMove.length; f++){
                        if (Objects.equals(unitMove[f].getId(), id)){
                            state = 2;
                            unit[f].setCenterX(event.getX());
                            unit[f].setCenterY(event.getY());
                            threat[f].setCenterX(unit[f].getCenterX());
                            threat[f].setCenterY(unit[f].getCenterY());
                            trueThreat[f].setCenterX(unit[f].getCenterX());
                            trueThreat[f].setCenterY(unit[f].getCenterY());
                            Actions action = new Actions();
                            unitMove[f].setVisible(false);
                            threat[f].setVisible(false);
                            action.actionCall(event, move, actionPane);
                        }
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

        endBtnBox.getChildren().addAll(endTurn, tCount, tes.spawnEnemy());
        endBtnBox.setMinWidth(screenx * .05);
        endBtnBox.setMaxWidth(screenx * .05);
        tCount.setAlignment(Pos.CENTER);
        tCount.setMaxWidth(Double.MAX_VALUE);
        tCount.setTextFill(Color.WHITE);
        endBtnBox.setLayoutX(screenx * .95);

        Button toggleShop = new Button("Toggle Shop");
        toggleShop.setOnAction(actionEvent -> {
            shopToggle = !shopToggle;
            shopPane.setVisible(shopToggle);
        });
        toggleShop.setMinWidth(screenx * .05);
        toggleShop.setMaxWidth(screenx * .05);
        toggleShop.setAlignment(Pos.CENTER);
        toggleShop.setTextFill(Color.BLACK);
        endBtnBox.getChildren().add(toggleShop);

        return endBtnBox;
    }

    public void BaseStatAssign(Pane unitPane, Double screeny, Double screenx, Circle[] champCircle, List<Image> results, List<String> resultNames){
        int y = 1;
        for (Circle champC : champCircle){
            champList.add((champC.getId() + " unit " + y));
            champMoney.set(y - 1, 500);
            y++;
        }
        List<Double> champThreat = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
        List<Double> champMove = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
        for (int a = 0; a < champCircle.length; a++) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/champions/" + champCircle[a].getId() + ".txt"))));
                String line;
                UnitStatsAssign usa = new UnitStatsAssign();
                while ((line = br.readLine()) != null) {
                    if (line.contains("range")) {
                        champThreat.set(a, (screeny * Double.parseDouble(line.replace("range ", ""))));
                    }
                    if (line.contains("move")){
                        champMove.set(a, (screeny * Double.parseDouble(line.replace("move ", ""))));
                    }
                    if (line.contains("HP")){
                        usa.stat(line, a, hpLists);
                    }
                    if (line.contains("AD")){
                        usa.stat(line, a, adLists);
                    }
                }
            } catch (Exception exc) {
                System.out.println("fuck you");
            }
        }
        List<Double> xCoord = Arrays.asList(screenx * .215, screenx * .226, screenx * .236, screenx * .245, screenx * .25);
        List<Double> yCoord = Arrays.asList(screeny * .9, screeny * .903, screeny * .91, screeny * .922, screeny * .94);
        for (int i = 0; i < unit.length; i++){
            unit[i].setId(champList.get(i));
            unit[i].setCenterX(xCoord.get(i));
            unit[i].setCenterY(yCoord.get(i));
            unit[i].setFill(new ImagePattern(results.get(resultNames.indexOf(champCircle[i].getId() + ".png"))));
            unit[i].setRadius(screeny * .01);
            unitMove[i].setId(champList.get(i) + "'s Movement Range");
            unitMove[i].setRadius(champMove.get(i));
            unitMove[i].setCenterX(xCoord.get(i));
            unitMove[i].setCenterY(yCoord.get(i));
            unitMove[i].setStroke(new Color(.7, 0,.7, 1));
            unitMove[i].setStrokeWidth(3.0);
            unitMove[i].setFill(Color.TRANSPARENT);
            unitMove[i].setVisible(false);
            threat[i].setId(champList.get(i) + "'s Threat Range");
            threat[i].setRadius(champThreat.get(i));
            threat[i].setCenterX(xCoord.get(i));
            threat[i].setCenterY(yCoord.get(i));
            threat[i].setFill(new Color(1.0, 0.0 ,0.5, .25));
            threat[i].setVisible(false);
            trueThreat[i].setId(i + "'s True Threat Range");
            trueThreat[i].setRadius(champThreat.get(i) + (screenx * 0.005));
            trueThreat[i].setFill(new Color(0.0, 0.0, 1.0, 0.25));
            trueThreat[i].setCenterX(xCoord.get(i));
            trueThreat[i].setCenterY(yCoord.get(i));
            trueThreat[i].setVisible(false);
            unitPane.getChildren().addAll(threat[i], unitMove[i], unit[i], trueThreat[i]);
        }
    }
}
