package com.example.app;

import com.example.app.shop.GenerateShop;
import com.example.app.unitInfo.Units;
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
    static VBox section = new VBox();
    static Pane mousePane = new Pane();
    static Pane minionPane = new Pane();
    static Pane unitPane = new Pane();
    public static Pane shopPane = new Pane();
    public static Pane pingPane = new Pane();
    public Node GameScreen(double screenx, double screeny, Circle[] championCircle, List<Image> results, List<String> resultNames){
        Pane gameScreen = new Pane();

        Pane actionPane = new Pane();
        actionPane.setMinSize(screenx, screeny);
        actionPane.setVisible(false);

        InformationPane infoGrabber = new InformationPane();
        Structures structureGrabber = new Structures();
        GenerateShop shop = new GenerateShop();
        shop.shopCreate();

        Units assign = new Units();
        Minions minionGrabber = new Minions();
        interactibles.getChildren().addAll(
                infoGrabber.leftInfoPane(infoPane),
                unitPane);
        unitPane.getChildren().add(minionPane);
        structureGrabber.createStructures(screenx, screeny, unitPane);
        assign.createUnits(actionPane);
        assign.BaseStatAssign(unitPane, screeny, screenx, championCircle, results, resultNames);
        minionGrabber.createMinions(minionPane);

        gameScreen.getChildren().addAll(createBackGround(screenx, screeny),
                assign.endTurn(screenx),
                interactibles,
                actionPane,
                shopPane,
                mousePane,
                pingPane);
        mousePane.setVisible(false);
        shopPane.setVisible(false);
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
