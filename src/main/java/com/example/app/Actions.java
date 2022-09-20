package com.example.app;

import com.example.app.unitInfo.Units;
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
import static com.example.app.InGameScreen.section;
import static com.example.app.MyApplication.*;
import static com.example.app.TestEnemySpawn.*;
import static com.example.app.unitInfo.Units.unitMove;
import static com.example.app.unitInfo.Units.trueThreat;
import static com.example.app.unitInfo.Units.threat;

public class Actions{
    static double xOffset, yOffset;
    static boolean enableMove = false;
    public void setActions(Circle move, Pane actionPane, MouseEvent mouse){
        if (actionPane.getChildren().size() == 0){
            Rectangle moveActionPane = new Rectangle(screenx * 0.025, screeny * 0.025);
            moveActionPane.setFill(new Color(0.0, 0.5, 0.5, 1.0));
            moveActionPane.setOnMouseClicked(event ->{
                if (enableMove){
                    enableMove = false;
                }
                else{
                    enableMove = true;
                }
                xOffset = event.getX();
                System.out.println(xOffset);
                yOffset = event.getY();
            });

            VBox actionPaneVBox = new VBox();
            actionPaneVBox.setLayoutX(mouse.getSceneX() + (screenx * 0.025));
            actionPaneVBox.setLayoutY(mouse.getSceneY() - (screeny * 0.05));
            actionPaneVBox.setSpacing(screeny * 0.005);
            actionPaneVBox.setPadding(new Insets(screeny * 0.003, screeny * 0.003, screeny * 0.003, screeny * 0.003));
            actionPaneVBox.setBackground(Background.fill(new Color(0.0, 0.7, 0.7, 1.0)));
            actionPaneVBox.getChildren().add(moveActionPane);

            actionPane.setVisible(true);
            actionPane.getChildren().addAll(actionPaneVBox);

            List<String> options = Arrays.asList("Attack", "Abilities", "Active", "Cancel");

            scene.setOnMouseMoved(event->{
                if (enableMove) {
                    actionPaneVBox.setLayoutX(event.getSceneX() - xOffset);
                    actionPaneVBox.setLayoutY(event.getSceneY() - yOffset);
                }
            });
            System.out.println("added to actionPane");
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
                            action.setOnMouseClicked(event ->buttonAttack(move));
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
        if (actionPane.getChildren().size() == 1){
            actionPane.setVisible(true);
            actionPane.getChildren().get(0).setLayoutX(mouse.getSceneX() + (screenx * 0.025));
            actionPane.getChildren().get(0).setLayoutY(mouse.getSceneY() - (screeny * 0.05));
        }
    }
    public void buttonAttack(Circle move){
        int index = Arrays.asList(unitMove).indexOf(move);
        threat[index].setVisible(true);
        trueThreat[index].setVisible(true);

        for (int a = 0; a < enemy.size(); a++){
            Shape u1Intersect = Shape.intersect(trueThreat[index], enemy.get(a));
            if (u1Intersect.getBoundsInLocal().getWidth() != -1){
                generateAttackOption(enemy.get(a), index, move);
            }
        }
    }
    public void generateAttackOption(Node target, int index, Circle unit){
        Circle location = new Circle();
        location.setRadius(unit.getRadius());
        location.setFill(new Color(1.0, 0.0, 0.0, 1.0));
        location.setLayoutX(target.getBoundsInLocal().getCenterX());
        location.setLayoutY(target.getBoundsInLocal().getCenterY());
        Shape u2Intersect = Shape.intersect(threat[index], location);
        if (u2Intersect.getBoundsInLocal().getWidth() != -1){
            pingPane.getChildren().add(location);
        }
        pingPane.setVisible(true);
        location.setOnMouseClicked(event ->{
            if (target.getId().contains("Enemy")){
                int i = enemy.indexOf(target);
                currentEnemyHp.set(i, enemyHp.get(i) - 100);
                if (section.isVisible()){
                    InformationPane aaaa = new InformationPane();
                    aaaa.returnInformation(target, enemyHp.get(i), currentEnemyHp.get(i));
                }
                if (currentEnemyHp.get(i) <= 0){
                    location.setVisible(false);
                }
            }
        });
    }
    public void buttonAbilities(){}
    public void buttonActive(){}
    public void actionCall(MouseEvent mouse, Circle move, Pane actionPane){
        setActions(move, actionPane, mouse);
    }
}
