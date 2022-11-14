package com.update.app.ingame;

import com.example.app.MyApplication;
import com.update.app.LaunchApp;
import com.update.app.champ.information.Stats;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.util.List;

import static com.update.app.LaunchApp.scene3;
import static com.update.app.champ.information.Stats.*;

public class ConstructGame {
    double x, y;
    public static VBox infoCardContainer = new VBox();
//    Color c2 = new Color(0.1, 0.2, 0.2, 1.0);
//    Color c3 = new Color(0.1, 0.3, 0.3, 1.0);
    Color c3 = new Color(0.075, 0.075, 0.075, 1);
    Color textC = new Color(0.1, 0.1, 0.1, 1.0);
    Color bg = new Color(0.15, 0.15, 0.15, 1.0);

    public static Pane interactionPane = new Pane();

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

        interactionPane.setVisible(false);
        pane.getChildren().addAll(map, hbox, interactionPane);
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
        double cardSizeY = useabley / 5;

        //setting the size of the right pane
        StackPane sp2 = new StackPane();
        //create cards for each champion in the game.
        VBox vbox = new VBox();
        int index = 0;
        for(List<List<String>> data : Stats.champions){
            vbox.getChildren().add(createChampionCard(data, useablex, cardSizeY, index));
            index++;
        }
        sp2.getChildren().add(vbox);

        return sp2;
    }

    private Node createChampionCard(List<List<String>> data, double x, double y, int index){
        //this creates the champion card.
        StackPane infoCard = new StackPane();
        infoCard.setPrefSize(x, y);

        //makes a border of one of the colors in the champions class file, then uses the other color for the fill.
        infoCard.setBorder(new Border(new BorderStroke(championscolor.get(index).get(1), BorderStrokeStyle.SOLID, null, new BorderWidths(y / 22))));
        infoCard.setBackground(new Background(new BackgroundFill(championscolor.get(index).get(0), null, null)));

        //container to hold all the elements
        VBox vbox = new VBox();

        //hbox for the stats on the bottom of the card
        HBox hbox = new HBox(stats(data, y));

        //vbox for the hp and action bars.
        VBox barsVbox = new VBox(hpBar(x, y, index), attacksBar(x, y, index));
        barsVbox.setSpacing(y/44);
        barsVbox.setPadding(new Insets(y/44, y/44, y/44, y/44));
        barsVbox.setBackground(new Background(new BackgroundFill((Color.gray(0.15)), null, null)));

        //put everything together.
        vbox.getChildren().addAll(title(index, y), barsVbox, hbox);
        vbox.setPadding(new Insets(0, y/44, 0, y/44));

        infoCard.getChildren().add(vbox);

        return infoCard;
    }

    private Node title(int index, double y){
        //the name on the top of the card is generated with this.
        String string = Stats.champions.get(index).get(0).get(1);
        Text name = new Text(string);
        name.setFont(Font.font("Arial", FontWeight.BOLD, y/11));
        name.setFill(textC);

        //centering regions
        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);
        Region r2 = new Region();
        HBox.setHgrow(r2, Priority.ALWAYS);
        r1.setMaxWidth(y * 0.05);

        return new HBox(r1, name, r2);
    }
    private Node hpBar(double x, double y, int index){
        //hp bar is made with this
        Rectangle backdrop = new Rectangle(x* .775, y / 20);
        Rectangle hp = new Rectangle(x * .775, y / 20);

        //add hp bar to Stats class so it can be more easily referenced later.
        championsHpBars.add(hp);

        //colors to blend into the HP bar
        Color hpbar1 = new Color(1.0, 0.0, 0.35, 1.0);
        Color hpbar2 = new Color(1.0, 0.0, 0.15, 1.0);
        Stop[] stops1 = new Stop[] {new Stop(0, hpbar1), new Stop(1, hpbar2)};
        LinearGradient hpbarFill = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops1);
        hp.setFill(hpbarFill);

        //text to show the current HP amount and max HP
        Text label = new Text(Stats.champions.get(index).get(3).get(1) + " / " + championsCurrentHp.get(index));
        label.setFont(new Font("Arial", y / 20));
        label.setStroke(Color.WHITE);

        //container to hold everything
        Pane hpPane = new Pane(backdrop, hp);
        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);

        return new HBox(hpPane, r1, label);
    }

    private Node attacksBar(double x, double y, int index){
        //attack bar is made with this.
        Rectangle backdrop = new Rectangle(x* .775, y / 20);
        Rectangle attacks = new Rectangle(x * .775, y / 20);

        //adds action bar to stats so it can be referenced more easily later.
        championsActionBars.add(attacks);

        //blends colors for the action bar fill
        Color hpbar1 = new Color(0.0, 1.0, 0.35, 1.0);
        Color hpbar2 = new Color(0.1, 0.95, 0.95, 1.0);
        Stop[] stops1 = new Stop[] {new Stop(0, hpbar1), new Stop(1, hpbar2)};
        LinearGradient hpbarFill = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops1);
        attacks.setFill(hpbarFill);

        //text to show the current attacks available and max attacks available.
        Text label = new Text(championsCurrentAs.get(index) + " AS");
        label.setFont(new Font("Arial", y / 20));
        label.setStroke(Color.WHITE);

        //container to hold everything
        Pane attacksPane = new Pane(backdrop, attacks);
        Region r1 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);

        return new HBox(attacksPane, r1, label);
    }

    private Node stats(List<List<String>> data, double y){
        //this makes the stats on the bottom of the card.
        HBox hbox = new HBox();
        hbox.setSpacing(y/22);
        VBox vb1 = new VBox();
        VBox vb2 = new VBox();

        //this fetches the data and puts the names into one column and the actual values into the other.
        for(List<String> field : data){
            if(!field.get(0).equals("NAME")){
                Text statName = new Text(field.get(0));
                Text statAmount = new Text(field.get(1));
                vb1.getChildren().add(statName);
                vb2.getChildren().add(statAmount);
            }
        }

        hbox.getChildren().addAll(vb1, vb2);

        return hbox;
    }
}
