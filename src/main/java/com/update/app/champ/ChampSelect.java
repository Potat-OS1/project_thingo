package com.update.app.champ;

import com.update.app.SceneSwitcher;
import com.update.app.champ.information.Stats;
import com.update.app.champ.ingame.GameStart;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class ChampSelect {
    double screenx, screeny;
    StackPane selectedChampions = new StackPane();
    ScrollPane champions = new ScrollPane();
    double sendWidth;
    public static List<Circle> championCircle = new ArrayList<>();
    public static int selectedSlot = 0;
    static double rightPaneWidth;
    Rectangle select;
    double selectOffset;
    double selectIncrement;
    Color color = new Color(0.1, 0.1, 0.1, 1.0);
    public static Color color2 = new Color(0.05, 0.05, 0.05, 1.0);
    public static StackPane right = new StackPane();
    public Node MainScreen(int x, int y){
        Pane scene = new Pane();
        screenx = x;
        screeny = y;
        scene.setMinSize(x, y);
        VBox vbox = new VBox(topWidgets(), hbox());
        scene.getChildren().add(vbox);
        //ok we built the main bulk of the screen. lets put shit in it.
        ChampSelectData csd = new ChampSelectData();
        csd.champLists();
        champions.setContent(csd.championIcons(sendWidth));
        return scene;
    }
    public Node topWidgets(){
        //top row of icons in champ select
        HBox widgetsBox = new HBox();
        Rectangle exit = new Rectangle(screenx * 0.05, screenx * 0.05);
        Rectangle whitespace = new Rectangle(screenx * .95, screenx * 0.05);
        whitespace.setFill(new Color(0.075, 0.075, 0.075, 1.0));
        exit.setFill(Color.INDIANRED);
        exit.setOnMouseClicked(event->swap());
        widgetsBox.setAlignment(Pos.CENTER_RIGHT);
        widgetsBox.getChildren().addAll(whitespace, exit);
        return widgetsBox;
    }
    public Node hbox(){
        //builds the scene below the widget bar
        HBox sceneDiv = new HBox();
        sceneDiv.getChildren().addAll(buildLeftPane(), buildCenterPane(), buildRightPane());
        return sceneDiv;
    }
    public Node buildLeftPane(){
        //using these dimensions
        double useableX = screenx * .2;
        double useableY = screeny - (screenx * 0.05);
        //
        selectedChampions.setMaxSize(useableX, useableY);
        selectedChampions.setMinSize(useableX, useableY);
        selectedChampions.setPadding(new Insets((useableY * .025), 0, (useableY * .025), 0));
        selectedChampions.setBackground(new Background(new BackgroundFill(color, null, null)));
        Pane background = new Pane();
        //background.setBackground(new Background(new BackgroundFill(Color.RED, null ,null)));
        VBox championCircles = new VBox();
        Pane highlight = new Pane();

        //circles on the left in champ select screen
        championCircles.setAlignment(Pos.CENTER);
        championCircles.maxWidth(useableX);
        championCircles.setPadding(new Insets((useableY * 0.05) / 6, (useableY * 0.05) / 6, (useableY * 0.05) / 6, (useableY * 0.05) / 6));
        championCircles.setSpacing((useableY * 0.05) / 1.75);
        for(int a = 0; a < 5; a++){
            Circle championC = new Circle();
            championC.setRadius((useableY * .41)/5);
            championC.setFill(new Color(0.2, 0.2, 0.5, 1.0));
            championCircles.getChildren().add(championC);
            championCircle.add(championC);
        }
        background.getChildren().addAll(highlight, championCircles);

        //rectangle behind the cirlce to highlight which circle is highlighted
        select = new Rectangle((useableY * 0.4), (useableY * 0.4)/2.1);
        highlight.getChildren().add(select);
        select.setLayoutX(useableX * 0.45 - (((useableY * 0.4)/2.5) / 2));
        select.setFill(new Color(1.0, 0.0, 0.0, 0.1));
        selectOffset = -(useableY * 0.005);
        select.setLayoutY(selectOffset);
        selectIncrement =  ((useableY * .41)/5) + ((useableY * 0.05) / 1.75) + (selectOffset * 3);

        championCircles.setLayoutX(useableX / 4);
        selectedChampions.getChildren().addAll(background);
        return selectedChampions;
    }
    public Node buildRightPane(){
        //datapane on the right
        double useableX = screenx * .3;
        double useableY = screeny - (screenx * 0.05);
        rightPaneWidth = useableX;
        right.setBackground(new Background(new BackgroundFill(color, null, null)));
        right.setMaxSize(useableX, useableY);
        right.setMinSize(useableX, useableY);
        right.setPadding(new Insets(useableY * 0.01, 0, useableY * 0.01, 0));
        return right;
    }
    public Node buildCenterPane(){
        //center holding all the champions
        double useableX = screenx * .5;
        double useableY = screeny - (screenx * 0.05);
        StackPane center = new StackPane();
        center.setPadding(new Insets(useableY * 0.01, useableY * 0.01, useableY * 0.01, useableY * 0.01));
        center.setBackground(new Background(new BackgroundFill(color2, null, null)));
        center.setMaxSize(useableX, useableY);
        center.setMinSize(useableX, useableY);

        //scrollpane such
        champions.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        champions.setStyle("-fx-background:black;"+"-fx-border-color:black;" + "-fx-focus-color:transparent;");
        champions.setMinSize(useableX - (useableY * 0.02), useableY - (useableY * 0.2));
        champions.setMaxSize(useableX - (useableY * 0.02), useableY - (useableY * 0.2));
        sendWidth = useableX - (useableY * 0.02);
        Pane pane = new Pane();
        VBox vbox = new VBox();
        vbox.getChildren().add(champions);
        center.getChildren().add(pane);

        //lockin button
        Polygon lockin = new Polygon();
        lockin.getPoints().addAll(0.0, 0.0,
                20.0, -10.0,
                120.0, -10.0,
                140.0, 0.0,
                140.0, 40.0,
                120.0, 50.0,
                20.0, 50.0,
                0.0, 40.0);
        lockin.setScaleX(screenx * .0015);
        lockin.setScaleY(screenx * .0015);
        lockin.setFill(color);
        lockin.setOnMouseClicked(event ->{
            ChampSelectData csd = new ChampSelectData();
            if(selectedSlot < 5){
                csd.setIcon();
                if(ChampSelectData.selectedChampion != null && selectedSlot <= 4){
                    select.setLayoutY(championCircle.get(selectedSlot).getLayoutY() - (select.getHeight() / 2));
                }
            }
            if(selectedSlot == 5){
                System.out.println("oi");
                //start the game here dumbass
                for (Node circle : championCircle){
                    Stats stat = new Stats();
                    stat.storeStats(csd.championInformation(circle.getId()));
                }
                GameStart gs = new GameStart();
                gs.Construct();
                SceneSwitcher.changeScene(2);
            }

        });

        //undo button
        Circle undo = new Circle(screenx * 0.025);
        undo.setFill(color);
        vbox.getChildren().add(lockin);
        pane.getChildren().addAll(vbox, undo);
        vbox.setAlignment(Pos.CENTER);
        undo.setLayoutX(useableX * .838);
        undo.setLayoutY(useableY * .9);
        undo.setOnMouseClicked(event->{
            selectedSlot--;
            select.setLayoutY(championCircle.get(selectedSlot).getLayoutY() - (select.getHeight() / 2));
            championCircle.get(selectedSlot).setFill(new Color(0.2, 0.2, 0.5, 1.0));
        });
        return center;
    }
    public void swap(){
        SceneSwitcher.changeScene(0);
    }
}