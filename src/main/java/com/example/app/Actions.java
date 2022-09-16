package com.example.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.List;

import static com.example.app.InGameScreen.pingPane;
import static com.example.app.MyApplication.screeny;
import static com.example.app.MyApplication.screenx;
import static com.example.app.Units.unitMove;
import static com.example.app.Units.trueThreat;
import static com.example.app.Units.threat;
import static com.example.app.TestEnemySpawn.enemy;

public class Actions{
    public void setActions(Circle move, Pane actionPane){
        VBox actionPaneVBox = new VBox();
        actionPaneVBox.setSpacing(screeny * 0.005);
        actionPaneVBox.setPadding(new Insets(screeny * 0.003, screeny * 0.003, screeny * 0.003, screeny * 0.003));
        actionPaneVBox.setBackground(Background.fill(new Color(0.0, 0.7, 0.7, 1.0)));
        List<String> options = Arrays.asList("Attack", "Abilities", "Active", "Cancel");
        actionPane.getChildren().addAll(actionPaneVBox);
        for (String act : options){
            Pane action = new Pane();
            Rectangle buttonBack = new Rectangle(screenx * 0.025, screeny * 0.025);
            buttonBack.setStrokeWidth(screeny * 0.001);
            buttonBack.setFill(new Color(1.0, 1.0, 1.0, 1.0));
            buttonBack.setStroke(new Color(0.0, 0.0, 0.0, 1.0));
            Label actionLabel = new Label(act);
            action.setBackground(Background.fill(new Color(0.5, 0.5, 0.5,1.0)));
            action.getChildren().addAll(buttonBack, actionLabel);
            actionPaneVBox.getChildren().add(action);
            action.setMinSize(screenx * 0.025, screeny * 0.025);
            action.setMaxSize(screenx * 0.025, screeny * 0.025);
            actionLabel.setFont(new Font("Segoe UI", screenx * 0.005));
            actionLabel.setAlignment(Pos.CENTER);
            actionLabel.setMinSize(screenx * 0.025, screeny * 0.025);
            actionLabel.setMaxSize(screenx * 0.025, screeny * 0.025);
            switch(options.indexOf(act)){
                case 0 ->
                    action.setOnMouseClicked(event ->{
                        buttonAttack(move);
                    });
                case 1 ->
                    action.setOnMouseClicked(event ->{
                        System.out.println("oi");
                    });
                case 2 ->
                    action.setOnMouseClicked(event ->{
                        System.out.println("potato");
                    });
                case 3 ->
                    action.setOnMouseClicked(event ->{
                        if (event.getButton() == MouseButton.PRIMARY){
                            Units unitc = new Units();
                            unitc.buttonCancel(move, actionPane);
                        }
                    });
            }
        }
    }
    public void buttonAttack(Circle move){
        int index = Arrays.asList(unitMove).indexOf(move);
        threat[index].setVisible(true);
        trueThreat[index].setVisible(true);

        for (int a = 0; a < enemy.size(); a++){
            Shape u1Intersect = Shape.intersect(trueThreat[index], enemy.get(a));
            if (u1Intersect.getBoundsInLocal().getWidth() != -1){
                generateAttackOption(enemy.get(a), index);
            }
        }
    }
    public void generateAttackOption(Node enemy, int index){
        Circle location = new Circle();
        location.setRadius(screenx * 0.001);
        location.setFill(new Color(1.0, 0.0, 0.0, 1.0));
        location.setLayoutX(enemy.getBoundsInLocal().getCenterX());
        location.setLayoutY(enemy.getBoundsInLocal().getCenterY());
        Shape u2Intersect = Shape.intersect(threat[index], location);
        if (u2Intersect.getBoundsInLocal().getWidth() != -1){
            pingPane.getChildren().add(location);
        }
        pingPane.setVisible(true);
    }
    public void buttonAbilities(){}
    public void buttonActive(){}
    public void actionCall(MouseEvent mouse, Circle move, Pane actionPane){
        actionPane.setVisible(true);
        setActions(move, actionPane);
        actionPane.setLayoutX(mouse.getX() + (screenx * 0.01));
        actionPane.setLayoutY(mouse.getY() - (screeny * 0.1));
    }
}
