package com.update.app.champ.ingame;

import com.example.app.MyApplication;
import com.update.app.LaunchApp;
import com.update.app.champ.information.Stats;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.InputStream;
import java.util.List;

import static com.update.app.LaunchApp.scene3;

public class GameStart {
    double x, y;
    public static VBox infoCardContainer = new VBox();
    VBox championCard = new VBox();
    Color hpbar2 = new Color(0.6, 0.0, 0.25, 1.0);
    Color hpbar1 = new Color(0.5, 0.0, 0.05, 1.0);

//    Color c2 = new Color(0.1, 0.2, 0.2, 1.0);
//    Color c3 = new Color(0.1, 0.3, 0.3, 1.0);
    Color c3 = new Color(0.075, 0.075, 0.075, 1);
    Color c2 = new Color(0.15, 0.15, 0.155, 1);


    Color textC = new Color(0.1, 0.1, 0.1, 1.0);
    Color bg = new Color(0.15, 0.15, 0.15, 1.0);

    int champNum = 1;
    public void Construct(){
        x = LaunchApp.scene2.getWidth();
        y = LaunchApp.scene2.getHeight();
        scene3 = new Scene((Parent) Map(x, y));
    }
    public Node Map(double x, double y){
        Pane pane = new Pane();
        pane.setMinSize(x, y);
        pane.setBackground(new Background(new BackgroundFill((bg), null, null)));
        HBox hbox = new HBox();
        hbox.getChildren().addAll(leftPane(), centerPane(), rightPane());
        Rectangle map = new Rectangle(x, y);
        InputStream BG = MyApplication.class.getResourceAsStream("/map3.png");
        assert BG != null;
        map.setFill(new ImagePattern(new Image(BG)));
        pane.getChildren().addAll(map, hbox);
        return pane;
    }
    public Node centerPane(){
        StackPane sp3 = new StackPane();
        sp3.setMinSize(x * .57, y);
        return sp3;
    }
    public Node leftPane(){
        StackPane sp1 = new StackPane();
        sp1.setMinSize(x * .215, y);
        sp1.setBackground(new Background(new BackgroundFill((c3), null, null)));
        sp1.getChildren().add(infoCardContainer);
        return sp1;
    }
    public Node rightPane(){
        //useable sizes
        double useablex = x * .215;
        double useabley = y;
        double cardSizeY = useabley / 5.25;
        double m = cardSizeY * .04175;
        double cardSizeX = useablex - (m*2);

        //setting the size of the right pane
        StackPane sp2 = new StackPane();
        sp2.setMaxSize(useablex, useabley);
        sp2.setMinSize(useablex, useabley);
        Color c = new Color(0.1, 0.1, 0.1, 1.0);
        sp2.setBackground(new Background(new BackgroundFill((c), null, null)));
        sp2.getChildren().add(championCard);
        sp2.setPadding(new Insets(m,m,m,m));
        //create cards for each champion in the game.
        VBox vbox = new VBox();
        for(List<List<String>> data : Stats.champions){
            vbox.getChildren().add(createChampionCard(data, m, cardSizeX, cardSizeY));
        }
        sp2.getChildren().add(vbox);
        vbox.setSpacing(m);

        return sp2;
    }
    public Node championHpBar(double x, double m, String hp){
        Pane pane = new Pane();
        double size = x - (m*4);
        pane.setMinSize(size * .8, size / 18);

        Rectangle background = new Rectangle(size * .8, size / 18);
        Rectangle hpbar = new Rectangle(size * .8, size / 18);

        Stop[] stops1 = new Stop[] {new Stop(0, hpbar1), new Stop(1, hpbar2)};
        LinearGradient hpbarFill = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops1);

        hpbar.setFill(hpbarFill);
        pane.getChildren().addAll(background, hpbar);

        Label hpText = new Label(hp + "/" + hp );
        hpText.setMinSize(size * .2, size /18);
        hpText.setAlignment(Pos.CENTER);
        hpText.setFont(new Font("Sans-serif Bold", m * 2));
        hpText.setTextFill(textC);

        HBox hbox = new HBox(pane, hpText);
        hbox.setMaxSize(size, size/12);

        return hbox;
    }
    public Node nameTag(double x, double m, String name){
        double useablex = x + (m*2);
        Label title = new Label("Champion " + champNum + ": " + name);
        champNum++;
        title.setFont(Font.font("Sans-serif Bold", FontWeight.BOLD, m * 2));
        title.setTextFill(textC);

        StackPane r1 = new StackPane();
        r1.setMinSize(useablex * .075, useablex /24);
        return new HBox(r1, title);
    }
    public Node createChampionCard(List<List<String>> championInfo, double m, double x, double y){
        //create the card
        StackPane champ = new StackPane();
        champ.setMinSize(x, y);
        BorderStrokeStyle bss = BorderStrokeStyle.SOLID;
        champ.setBorder(new Border(new BorderStroke(c3, c3, c3, c3, bss, bss, bss, bss, null, new BorderWidths(m), null)));
        champ.setBackground(new Background(new BackgroundFill((c2), null, null)));
        champ.setPadding(new Insets(m/2, m/2, m/2, m/2));

        VBox vbox = new VBox(nameTag(x, m , championInfo.get(0).get(1)), championHpBar(x, m, championInfo.get(3).get(1)));
        champ.getChildren().add(vbox);

        //stat containers
        VBox column1 = new VBox();
        column1.setSpacing(m/4);
        VBox column2 = new VBox();
        column2.setSpacing(m/4);
        HBox statContainer = new HBox(column1, column2);
        statContainer.setPadding(new Insets(m/2, m/2, m/2, m/2));
        vbox.getChildren().add(statContainer);

        //populate the columns.
        for(List<String> data : championInfo){
            if (!data.get(0).contains("HP") && !data.get(0).contains("NAME")){
                Label text1 = new Label(data.get(0));
                column1.getChildren().add(text1);
                text1.setFont(new Font("Sans-serif Bold", m * 1.5));
                text1.setTextFill(textC);
                Label text2 = new Label(data.get(1));
                column2.getChildren().add(text2);
                text2.setFont(new Font("Sans-serif Bold", m * 1.5));
                text2.setTextFill(textC);
            }
        }
        return champ;
    }
}
