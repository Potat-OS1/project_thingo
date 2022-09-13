package com.example.app;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static com.example.app.InGameScreen.mousePane;
import static com.example.app.MyApplication.screenx;
import static com.example.app.MyApplication.screeny;

public class TestEnemySpawn{
    static Circle[] enemy = new Circle[100];
    static int enemyNum = 0;
    static List<Integer> enemyHp = new ArrayList<>();
    public Node spawnEnemy(){
        Button spawn = new Button("Spawn Enemy");
        spawn.setOnAction(event -> spawnEnemyAnimation());
        return spawn;
    }
    public void spawnEnemyAnimation(){
        mousePane.setVisible(true);
        mousePane.setMinSize(screenx, screeny);
        enemyHp.add(100);
        enemy[enemyNum] = new Circle();
        enemy[enemyNum].setId("Enemy "  + enemyNum);
        enemy[enemyNum].setRadius(screenx * 0.008);
        enemy[enemyNum].setFill(new Color(1.0, 1.0, 1.0, 1.0));
        enemy[enemyNum].setStrokeWidth(screenx * 0.002);
        enemy[enemyNum].setStroke(new Color(0.2, 0.2, 0.2, 1.0));
        mousePane.setOnMouseMoved(event ->{
            Node source = (Node) event.getSource();
            if (!Objects.equals(source.getId(), String.valueOf(enemyNum))){
                enemy[enemyNum].setCenterX(event.getX());
                enemy[enemyNum].setCenterY(event.getY());
            }
        });
        mousePane.setOnMouseClicked(event -> {
            mousePane.setVisible(false);
            enemyNum++;
        });
        enemy[enemyNum].setOnMouseClicked(event ->{
            InformationPane send = new InformationPane();
            Node source = (Node) event.getSource();
            String index = source.getId();
            index = index.replace("Enemy ", "");
            send.returnInformation(source, String.valueOf(enemyHp.get(Integer.parseInt(index))));
        });
        InGameScreen.minionPane.getChildren().add(enemy[enemyNum]);
    }
}
