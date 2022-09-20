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
    static List<Circle> enemy = new ArrayList<>();
    static int enemyNum = 0;
    static List<Integer> enemyHp = new ArrayList<>();
    static List<Integer> currentEnemyHp = new ArrayList<>();
    public Node spawnEnemy(){
        Button spawn = new Button("Spawn Enemy");
        spawn.setOnAction(event -> spawnEnemyAnimation());
        return spawn;
    }
    public void spawnEnemyAnimation(){
        mousePane.setVisible(true);
        mousePane.setMinSize(screenx, screeny);
        enemyHp.add(100);
        currentEnemyHp.add(100);
        Circle enemyC = new Circle();
        enemy.add(enemyC);
        enemyC.setId("Enemy "  + enemyNum);
        enemyC.setRadius(screenx * 0.008);
        enemyC.setFill(new Color(1.0, 1.0, 1.0, 1.0));
        enemyC.setStrokeWidth(screenx * 0.002);
        enemyC.setStroke(new Color(0.2, 0.2, 0.2, 1.0));
        mousePane.setOnMouseMoved(event ->{
            Node source = (Node) event.getSource();
            if (!Objects.equals(source.getId(), ("Enemy " + enemyNum))){
                enemyC.setCenterX(event.getX());
                enemyC.setCenterY(event.getY());
            }
        });
        mousePane.setOnMouseClicked(event -> {
            mousePane.setVisible(false);
            enemyNum++;
        });
        enemyC.setOnMouseClicked(event ->{
            InformationPane send = new InformationPane();
            Node source = (Node) event.getSource();
            String index = source.getId();
            index = index.replace("Enemy ", "");
            send.returnInformation(source, (enemyHp.get(Integer.parseInt(index))), (currentEnemyHp.get(Integer.parseInt(index))));
        });
        InGameScreen.minionPane.getChildren().add(enemyC);
    }
}
