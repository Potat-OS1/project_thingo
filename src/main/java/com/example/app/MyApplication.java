package com.example.app;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import org.slf4j.*;

public class MyApplication extends Application {
    private final static Logger logger = LoggerFactory.getLogger(MyApplication.class);

    public Button confirmButton, cancelButton, pushButton, freezeButton, safeButton;
    HBox box = new HBox(5);
    int focus;
    // 1280/720  1920/1080
    double screenx = 1280;
    double screeny = 720;
    @Override
    public void start(Stage primaryStage){
        Pane pane = new Pane();
        Scene scene = new Scene(pane, screenx, screeny);

        ChampSelectScreen run = new ChampSelectScreen();
        InGameScreen run2 = new InGameScreen();
        pane.getChildren().addAll(run2.GameScreen(screenx, screeny), buttonBox(), run.champSelect(screenx, screeny));
        pane.setMinSize(screenx, screeny);

        buttonBoxLogic();

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public Node buttonBox(){
        confirmButton = new Button("Confirm");
        cancelButton = new Button("Cancel");
        pushButton = new Button("Push Lane");
        freezeButton = new Button("Freeze Lane");
        safeButton = new Button("Play Safe");

        box.setPadding(new Insets(3, 3, 3, 3));
        box.getChildren().addAll(confirmButton, cancelButton, pushButton, freezeButton, safeButton);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, null, null)));
        box.setVisible(false);
        return box;
    }

    private void buttonBoxLogic(){
        if (focus == 1){
            confirmButton.setOnAction((ActionEvent) ->{
            });
            cancelButton.setOnAction((ActionEvent) -> box.setVisible(false));
            pushButton.setOnAction((ActionEvent) ->{
            });
            freezeButton.setOnAction((ActionEvent) ->{
            });
            safeButton.setOnAction((ActionEvent) ->{

            });
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}