package com.example.app;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.slf4j.*;

public class MyApplication extends Application {
    private final static Logger logger = LoggerFactory.getLogger(MyApplication.class);
    // 1280/720  1920/1080
    double screenx = 1920;
    double screeny = 1080;
    @Override
    public void start(Stage primaryStage){
        Pane pane = new Pane();
        Scene scene = new Scene(pane, screenx, screeny);

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