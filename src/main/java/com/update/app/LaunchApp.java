package com.update.app;

import com.update.app.champ.ChampSelect;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LaunchApp extends Application {
    //grabs screen size and makes a launcher thats proportional to it. we don't know how big to make it otherwise since a lot of people have different sized monitors.
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    double x = screenBounds.getWidth() / 4.0;
    double y = screenBounds.getHeight() / 3.0;
    public static Scene scene1, scene2, scene3;
    public Stage primaryStage = new Stage();

    public void start(Stage stage){
        scene1 = new Scene((Parent) LauncherCreate());
        primaryStage.setScene(scene1);
        primaryStage.sizeToScene();
        primaryStage.setTitle("Game");
        primaryStage.show();
        SceneSwitcher scenegrab = new SceneSwitcher();
        //this swaps the scene whenever changeScene(int) is used. nifty.
        scenegrab.sceneProperty().addListener((o, oldVal, newVal) ->{
                System.out.println("Scene swapped to  " + scenegrab.getScene());
                switch((int) scenegrab.getScene()){
                    case(0) -> primaryStage.setScene(scene1);
                    case(1) -> primaryStage.setScene(scene2);
                    case(2) -> primaryStage.setScene(scene3);
                }
                primaryStage.centerOnScreen();
            });
        SceneSwitcher.changeScene(0);
    }
    public Node LauncherCreate(){
        VBox vbox = new VBox();
        StackPane pane = new StackPane(vbox);
        pane.setMinSize(x, y);
        double margin = x / 15;
        //splash
        Rectangle splash = new Rectangle(x, y/1.5);
        //container for the resolutions+label
        VBox container1 = new VBox();
        Label label = new Label("Resolution");
        label.setFont(new Font("Arial", y / 30));
        container1.getChildren().add(label);
        container1.setAlignment(Pos.CENTER);
        //resolution combobox
        final ComboBox<String> resolutions = new ComboBox<>();
        resolutions.getItems().addAll("720x480", "1280x720", "1920x1080");
        resolutions.setMinHeight(y / 10);
        resolutions.setValue("Set Value");
        container1.getChildren().add(resolutions);
        //start button and adding everything together.
        Rectangle start = new Rectangle(x / 5, y /5);
        start.setFill(Color.AQUA);
        //
        Label startLabel = new Label("Start");
        startLabel.setFont(new Font("Arial", y / 15));
        //
        StackPane startContainer = new StackPane();
        startContainer.setMaxSize(x / 2.5, y / 2.5);
        startContainer.getChildren().addAll(start, startLabel);
        startContainer.setOnMouseClicked(event -> StartGame(resolutions.getValue()));
        //
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox bottomRow = new HBox(container1, region, startContainer);
        bottomRow.setPadding(new Insets(margin, margin, margin, margin));


        vbox.getChildren().addAll(splash, bottomRow);
        return pane;
    }
    public void StartGame(String resolution){
        if(!resolution.equals("Set Value")){
            int x = 0;
            int y = 0;
            switch (resolution) {
                case ("720x480") -> {
                    x = 720;
                    y = 480;
                }
                case ("1280x720") -> {
                    x = 1280;
                    y = 720;
                }
                case ("1920x1080") -> {
                    x = 1920;
                    y = 1090;
                }
            }
            ChampSelect next = new ChampSelect();
            scene2 = new Scene((Parent) next.MainScreen(x, y));
            SceneSwitcher.changeScene(1);
        }
    }
    public static void main(String[] args){
        launch();
    }
}
