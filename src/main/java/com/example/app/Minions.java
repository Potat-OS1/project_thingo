package com.example.app;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import static com.example.app.MyApplication.screenx;
import static com.example.app.MyApplication.screeny;


public class Minions {
    InformationPane sendInfo = new InformationPane();
    ArrayList<Circle> casterMinion = new ArrayList<>();
    ArrayList<Circle> meleeMinion = new ArrayList<>();
    static ArrayList<Pane> minionGroup = new ArrayList<>();
    ArrayList<String> minionList = new ArrayList<>();
    ArrayList<Integer> minionHp = new ArrayList<>();
    ArrayList<Integer> currentMinionHp = new ArrayList<>();
    int index = 0;
    int groupIndex = 0;
    int spawnPosition = 0;
    int state = 1;
    public void createMinions(Pane minionPane){
        for (int b = 0; b < 3; b++) {
            minionGroup.add(new Pane());
            for (int i = 0; i < 3; i++) {
                casterMinion.add(new Circle(screeny * 0.006));
                casterMinion.get(index).setId("Caster Minion ID no:" + (index));
                casterMinion.get(index).setFill(new Color(0.0, 1.0, 1.0, 1.0));
                minionHp.add(90);
                currentMinionHp.add(90);
                minionList.add(casterMinion.get(index).getId());
                casterMinion.get(index).setOnMousePressed(event -> {
                    final Node source = (Node) event.getSource();
                    minionInfo(source);
                });

                meleeMinion.add(new Circle(screeny * 0.008));
                meleeMinion.get(index).setId("Melee Minion ID no: " + (index));
                meleeMinion.get(index).setFill(new Color(0.0, 0.7, 0.7, 1.0));
                minionHp.add(100);
                currentMinionHp.add(100);
                minionList.add(meleeMinion.get(index).getId());
                meleeMinion.get(index).setOnMousePressed(event -> {
                    final Node source = (Node) event.getSource();
                    minionInfo(source);
                });

                minionGroup.get(groupIndex).getChildren().addAll(casterMinion.get(index), meleeMinion.get(index));
                minionGroup.get(groupIndex).setBackground(Background.fill(new Color(0.5 ,0.5, 0.5, 1.0)));
                index++;
            }
            minionGroup.get(groupIndex).setId(String.valueOf(groupIndex));
            minionSpawn(minionGroup.get(groupIndex));
            minionPane.getChildren().add(minionGroup.get(groupIndex));
            groupIndex++;
        }
    }
    public void createRanks(ArrayList<Circle> minionSorted, int meleeCount){
        for (int i = 0; i < meleeCount; i++){
            minionSorted.get(i).setLayoutY(screeny * 0.009);
            minionSorted.get(i).setLayoutX(((screenx * 0.01) * (i + .45)));
        }
        for (int b = meleeCount; b < minionSorted.size(); b++){
            minionSorted.get(b).setLayoutY((screeny * 0.009) * (screeny * 0.003));
            minionSorted.get(b).setLayoutX((screenx * 0.01) * ((b - meleeCount) + .45));
        }
    }
    public void createFile(ArrayList<Circle> minionSorted, int meleeCount){
        for (int i = 0; i < meleeCount; i++){
            minionSorted.get(i).setLayoutY((screeny * 0.02) * i);
            minionSorted.get(i).setLayoutX(screeny * 0.008);
        }
        for (int b = meleeCount; b < minionSorted.size(); b++){
            minionSorted.get(b).setLayoutY((screeny * 0.015) * (b - meleeCount) + ((screeny * 0.02) * meleeCount));
            minionSorted.get(b).setLayoutX(screeny * 0.008);
        }
    }
    public void minionCount(Pane minionGroup){
        ObservableList<Node> group = minionGroup.getChildren();
        ArrayList<Circle> minionSorted = new ArrayList<>();
        int meleeCount = 0;
        for (int i = 0; i < group.size(); i++){
            if (minionGroup.getChildren().get(i).getId().contains("Melee")){
                minionSorted.add((Circle) minionGroup.getChildren().get(i));
                meleeCount++;
            }
        }
        for (int b = 0; b < group.size(); b++){
            if (minionGroup.getChildren().get(b).getId().contains("Caster")){
                minionSorted.add((Circle) minionGroup.getChildren().get(b));
            }
        }
        switch(state){
            case 0 ->createFile(minionSorted, meleeCount);
            case 1 ->createRanks(minionSorted, meleeCount);
        }
    }
    public void minionSpawn(Pane minionGroup){
        switch (spawnPosition) {
            case 0 -> {
                minionGroup.setLayoutX((screenx * .24));
                minionGroup.setLayoutY((screeny * .8));
                spawnPosition++;
                minionCount(minionGroup);
                minionGroup.setRotate(0);
            }
            case 1 -> {
                minionGroup.setLayoutX((screenx * .258));
                minionGroup.setLayoutY((screeny * .805));
                spawnPosition++;
                minionCount(minionGroup);
                minionGroup.setRotate(45);
            }
            case 2 -> {
                minionGroup.setLayoutX((screenx * .26));
                minionGroup.setLayoutY((screeny * .84));
                spawnPosition = 0;
                minionCount(minionGroup);
                minionGroup.setRotate(90);
            }
        }
    }
    public void minionInfo(Node minion){
        int hp = (minionHp.get(minionList.indexOf(minion.getId())));
        int currentHp = (currentMinionHp.get(minionList.indexOf(minion.getId())));
        sendInfo.returnInformation(minion, hp, currentHp);
    }
}