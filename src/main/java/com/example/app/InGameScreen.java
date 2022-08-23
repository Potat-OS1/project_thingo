package com.example.app;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.List;

public class InGameScreen {

    Pane interactibles = new Pane();
    Pane infoPane = new Pane();
    VBox section = new VBox();
    public Node GameScreen(double screenx, double screeny, Circle[] championCircle, List<Image> results, List<String> resultNames){
        Pane gameScreen = new Pane();
        Pane unitPane = new Pane();

        InformationPane infoGrabber = new InformationPane();
        Structures structureGrabber = new Structures();
        Units assign = new Units();


        interactibles.getChildren().addAll(
                infoGrabber.leftInfoPane(infoPane, section),
                unitPane);

        structureGrabber.createStructures(screenx, screeny, unitPane, infoPane, section);
        assign.createUnits();
        assign.BaseStatAssign(unitPane, screeny, screenx, championCircle, results, resultNames);

        gameScreen.getChildren().addAll(createBackGround(screenx, screeny),
                assign.endTurn(screenx),
                interactibles);

        gameScreen.setMinSize(screenx, screeny);
        return gameScreen;
    }

    private Node createBackGround(double screenx, double screeny){
        InputStream BG = MyApplication.class.getResourceAsStream("/map.png");
        assert BG != null;
        Rectangle background = new Rectangle();
        background.setFill(new ImagePattern(new Image(BG)));
        background.setWidth(screenx);
        background.setHeight(screeny);
        return background;
    }
}
