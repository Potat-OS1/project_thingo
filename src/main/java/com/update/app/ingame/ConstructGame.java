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
import java.util.ArrayList;
import java.util.List;

import static com.update.app.LaunchApp.scene3;
import static com.update.app.champ.information.Stats.*;

public class ConstructGame {
    double x, y;
    public static VBox infoCardContainer = new VBox();
    Color c3 = new Color(0.075, 0.075, 0.075, 1);
    Color textC = new Color(0.1, 0.1, 0.1, 1.0);
    Color bg = new Color(0.15, 0.15, 0.15, 1.0);

    public static Pane interactionPane = new Pane();

    public void Construct(){
        x = LaunchApp.scene2.getWidth();
        y = LaunchApp.scene2.getHeight();
        scene3 = new Scene((Parent) Map(x, y));
    }
    private Node Map(double x, double y){
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

    private Node centerPane(){
        StackPane sp3 = new StackPane();
        sp3.setMinSize(x * .57, y);
        return sp3;
    }
    private Node leftPane(){
        StackPane sp1 = new StackPane();
        sp1.setMinSize(x * .215, y);
        sp1.setBackground(new Background(new BackgroundFill((c3), null, null)));
        sp1.getChildren().add(infoCardContainer);
        return sp1;
    }
    private Node rightPane(){
        //useable sizes
        double useablex = x * .215;
        double useabley = y;
        double cardSizeY = useabley / 5;

        //setting the size of the right pane
        StackPane sp2 = new StackPane();
        sp2.setMaxSize(useablex, useabley);

        //create cards for each champion in the game.
        VBox vbox = new VBox();
        int index = 0;
        for(List<List<String>> data : Stats.champions){
            List<List<String>> current = championsCurrent.get(index);
            vbox.getChildren().add(createChampionCard(data, current, useablex, cardSizeY, index));
            index++;
        }
        Stats stat = new Stats();
        stat.storeInventoriesList();
        sp2.getChildren().add(vbox);

        return sp2;
    }

    private Node createChampionCard(List<List<String>> data, List<List<String>> current, double x, double y, int index){
        //this creates the champion card.
        StackPane infoCard = new StackPane();
        infoCard.setPrefSize(x, y);

        //makes a border of one of the colors in the champions class file, then uses the other color for the fill.
        infoCard.setBorder(new Border(new BorderStroke(championscolor.get(index).get(1), BorderStrokeStyle.SOLID, null, new BorderWidths(y / 22))));
        infoCard.setBackground(new Background(new BackgroundFill(championscolor.get(index).get(0), null, null)));

        //container to hold all the elements
        VBox vbox = new VBox();

        //hbox for the stats on the bottom of the card
        Region r1 = new Region();
        Region r2 = new Region();
        HBox.setHgrow(r1, Priority.ALWAYS);
        HBox.setHgrow(r2, Priority.ALWAYS);
        HBox hbox = new HBox(stats(data, y), addedStats(data, current, y), r1, itemsBox(y), r2);
        hbox.setSpacing(y/22);

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
                statName.setFont(Font.font("Arial", FontWeight.BOLD, y/16));
                Text statAmount = new Text(field.get(1));
                statAmount.setFont(Font.font("Arial", FontWeight.BOLD, y/16));
                vb1.getChildren().add(statName);
                vb2.getChildren().add(statAmount);
            }
        }

        hbox.getChildren().addAll(vb1, vb2);

        //formatting
        Region r1 = new Region();
        Region r2 = new Region();
        VBox.setVgrow(r1, Priority.ALWAYS);
        VBox.setVgrow(r2, Priority.ALWAYS);

        return new VBox(r1, hbox, r2);
    }

    private Node addedStats(List<List<String>> data, List<List<String>> current, double y){
        int index = 0;
        VBox vbox = new VBox();

        //arraylists for the base and current totals.
        List<Double> baseTotal = new ArrayList<>();
        List<Double> addedTotal = new ArrayList<>();


        //grabs the base data
        for (List<String> base : data){
            if (base != data.get(0)){
                baseTotal.add(Double.valueOf(base.get(1)));
            }
        }

        //grabs the current stat.
        for(List<String> added : current){
            if (added != current.get(0)){
                addedTotal.add(Double.valueOf(added.get(1)));
            }
        }

        //does the math to see what the difference to see the added.
        for(Double stat : baseTotal){
            int intValue = (int)(addedTotal.get(index) - stat);
            Text statAmount = new Text("+  " + (intValue));
            statAmount.setFill(Color.GOLDENROD);
            statAmount.setFont(Font.font("Arial", FontWeight.BOLD, y/16));
            vbox.getChildren().add(statAmount);
            index++;
        }

        //formatting
        Region r1 = new Region();
        Region r2 = new Region();
        VBox.setVgrow(r1, Priority.ALWAYS);
        VBox.setVgrow(r2, Priority.ALWAYS);

        return new VBox(r1, vbox, r2);
    }

    private Node itemsBox(double y){
        //rows for the items
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        hbox1.setSpacing(y/44);
        hbox2.setSpacing(y/44);

        //formatting and holder for the rows
        Region r1 = new Region();
        Region r2 = new Region();
        VBox.setVgrow(r1, Priority.ALWAYS);
        VBox.setVgrow(r2, Priority.ALWAYS);

        VBox vbox = new VBox(r1, hbox1, hbox2, r2);
        vbox.setSpacing(y/44);

        //how many items per row
        int itemPerRow = 3;
        List<Rectangle> items = new ArrayList<>();

        //populate the rows
        for(int a = 0; a < itemPerRow; a++){
            Rectangle item1 = new Rectangle(y/3.6, y/3.6);
            Rectangle item2 = new Rectangle(y/3.6, y/3.6);
            items.add(item1);
            items.add(item2);
            hbox1.getChildren().add(item1);
            hbox2.getChildren().add(item2);
        }

        //put the items in the inventories
        championsInventories.add(items);

        return vbox;
    }
}
