package com.example.app;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class MyApplication extends Application {
    // 1280/720  1920/1080
    public static double screenx = 1280;
    public static double screeny = 720;
    static Pane pane = new Pane();
    static Scene scene = new Scene(pane, screenx, screeny);
    @Override
    public void start(Stage primaryStage){
        ChampSelectScreen run = new ChampSelectScreen();
        pane.getChildren().add(run.champSelect(screenx, screeny, pane));
        pane.setMinSize(screenx, screeny);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}