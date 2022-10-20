package com.example.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import static com.example.app.InGameScreen.section;
import static com.example.app.MyApplication.screenx;
import static com.example.app.MyApplication.screeny;
import static com.example.app.unitInfo.UnitSpecifics.hpLists;
import static com.example.app.unitInfo.Units.champList;

public class InformationPane{
    public Node LeftInfoPane(){
        Pane leftInfoPane = new Pane();
        leftInfoPane.setBackground(Background.fill(new Color(0.5 ,0.5, 0.5, 1.0)));
        leftInfoPane.setMinSize(screenx * .2, screeny);
        leftInfoPane.getChildren().add(section);
        Rectangle oi = new Rectangle();
        section.getChildren().add(oi);
        return leftInfoPane;
    }
    public Node RightInfoPane(){
        double spacing = screenx * 0.0033;
        Pane rightInfoPane = new Pane();
        rightInfoPane.setMinSize(screenx * .2, screeny);
        rightInfoPane.setLayoutX(screenx * .8);
        rightInfoPane.setBackground(Background.fill(Color.INDIANRED));

        VBox rip_Vbox = new VBox();
        rip_Vbox.setPadding(new Insets(spacing, spacing, spacing, spacing));
        rip_Vbox.setSpacing(spacing);
        for (int i = 0; i < 5; i++){
            rip_Vbox.getChildren().add(infoCard(i, spacing));
        }
        rightInfoPane.getChildren().add(rip_Vbox);
        return rightInfoPane;
    }
    public Node infoCard(int i, double spacing){
        Pane card = new Pane();
        card.setId(champList.get(i));
        card.setBackground(Background.fill(new Color(0.9, 0.4, 0.5, 1.0)));
        card.setMinSize(screenx * .193, screeny * .1930555);

        VBox champ_Vbox = new VBox();
        champ_Vbox.setPadding(new Insets(spacing, spacing, spacing, spacing));

        Label champ_label = new Label();
        String name = card.getId();
        name = name.replace("unit " ,"");
        for (int b = 0; b < 6; b++){
            name = name.replace(String.valueOf(b), "");
        }
        champ_label.setText(name);
        champ_label.setFont(new Font("Arial", screeny * .02));
        champ_label.setAlignment(Pos.CENTER_LEFT);

        Pane champ_HpPane = new Pane();
        Rectangle champ_HpPane_HpContainer = new Rectangle(screenx * 0.19, screeny * .03);
        Rectangle champ_HpPane_HpBar = new Rectangle(screenx * 0.19, screeny * .03);
        champ_HpPane_HpContainer.setFill(Color.DARKGRAY);
        Color hpbar1 = new Color(1.0, 0.0, 0.35, 1.0);
        Color hpbar2 = new Color(1.0, 0.0, 0.15, 1.0);
        Stop[] stops1 = new Stop[] {new Stop(0, hpbar1), new Stop(1, hpbar2)};
        LinearGradient hpbarFill = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops1);

        champ_HpPane_HpBar.setFill(hpbarFill);
        champ_HpPane.getChildren().addAll(champ_HpPane_HpContainer, champ_HpPane_HpBar);
        Label champ_HpPane_Label = new Label();
        champ_HpPane_Label.setText(hpLists.get(i).get(0) + " / " + hpLists.get(i).get(1));
        champ_HpPane_Label.setFont(new Font("Arial", screeny * .02));

        Pane apsPane = new Pane();
        Rectangle aps_Pane_Bar = new Rectangle(screenx * .19, screeny * .015);
        Stop[] stops2 = new Stop[] {new Stop(0, new Color(0.1, 1.0, 1.0, 1.0)), new Stop(1, new Color(0.0, 1.0, 0.0, 1.0))};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops2);
        aps_Pane_Bar.setFill(lg1);
        apsPane.getChildren().add(aps_Pane_Bar);

        HBox split = new HBox();
        split.setPadding(new Insets(spacing, 0, 0, 0));
        split.setSpacing(spacing * 8);
        split.setMinWidth(screenx * .19);

        StackPane split_Pane1 = new StackPane();
        split_Pane1.setBackground(Background.fill(Color.WHITE));
        split_Pane1.setPadding(new Insets(spacing/1.5, spacing/2.55, spacing/2.45, spacing/2.5));
        VBox split_Pane1_Vbox = new VBox();
        split_Pane1_Vbox.setSpacing(screenx * 0.002);
        HBox split_Pane1_Vbox_Hbox1 = new HBox();
        split_Pane1_Vbox_Hbox1.setSpacing(screenx * 0.002);
        HBox split_Pane1_Vbox_Hbox2 = new HBox();
        split_Pane1_Vbox_Hbox2.setSpacing(screenx * 0.002);
        split_Pane1_Vbox.getChildren().addAll(split_Pane1_Vbox_Hbox1, split_Pane1_Vbox_Hbox2);
        split_Pane1.getChildren().add(split_Pane1_Vbox);
        for (int h = 0; h < 3; h++){
            Rectangle itemRow1 = new Rectangle(screenx * 0.02, screenx * 0.02);
            Rectangle itemRow2 = new Rectangle(screenx * 0.02, screenx * 0.02);
            split_Pane1_Vbox_Hbox1.getChildren().add(itemRow1);
            split_Pane1_Vbox_Hbox2.getChildren().add(itemRow2);
        }

        StackPane split_Pane2 = new StackPane();
        split_Pane2.setBackground(Background.fill(Color.TAN));
        split_Pane2.setPadding(new Insets(spacing/1.5, spacing/2.55, spacing/2.45, spacing/2.5));
        split.getChildren().addAll(split_Pane1, split_Pane2);
        VBox split_Pane2_Vbox = new VBox();
        split_Pane2_Vbox.setSpacing(screenx * 0.002);
        HBox split_Pane2_Vbox_Hbox1 = new HBox();
        split_Pane2_Vbox_Hbox1.setSpacing(screenx * 0.002);
        HBox split_Pane2_Vbox_Hbox2 = new HBox();
        split_Pane2_Vbox_Hbox2.setSpacing(screenx * 0.002);


        split_Pane2_Vbox.getChildren().addAll(split_Pane2_Vbox_Hbox1, split_Pane2_Vbox_Hbox2);
        split_Pane2.getChildren().add(split_Pane2_Vbox);
        for (int h = 0; h < 3; h++){
            Rectangle itemRow1 = new Rectangle(screenx * 0.02, screenx * 0.02);
            Rectangle itemRow2 = new Rectangle(screenx * 0.02, screenx * 0.02);
            itemRow1.setFill(Color.INDIANRED);
            itemRow2.setFill(Color.INDIANRED);
            split_Pane2_Vbox_Hbox1.getChildren().add(itemRow1);
            split_Pane2_Vbox_Hbox2.getChildren().add(itemRow2);
        }

        champ_Vbox.getChildren().addAll(champ_label, champ_HpPane, champ_HpPane_Label, apsPane, split);
        card.getChildren().add(champ_Vbox);
        return card;
    }
    public void returnInformation(Node input, int hp, int currentHp){
        VBox info = new VBox();
        Pane hpBox = new Pane();
        Pane namePane = new Pane();

        info.setPadding(new Insets(screeny * 0.01, screeny * 0.01, screeny * 0.01, screeny * 0.01));
        info.setSpacing(screeny * 0.01);
        info.setBackground(Background.fill(new Color(1.0, 1.0, 1.0, 1.0)));
        info.setMinWidth(screenx * 0.2);

        Rectangle hpBar = new Rectangle();
        hpBar.setFill(new Color(1.0, 0.0, 0.35, 1.0));
        hpBar.setWidth(screenx * 0.19);

        if (currentHp < hp){
            double ratio = (double) currentHp / (double) hp;
            System.out.println(currentHp + "   " + hp + "    " + ratio);
            hpBar.setWidth((screenx * 0.19) * ratio);
            System.out.println(hpBar.getWidth());
        }

        hpBar.setHeight(screeny * 0.05);

        hpBox.setBackground(Background.fill(new Color(0.1, 0.1, 0.1, 1.0)));
        hpBox.setMaxSize(screenx * 0.189, screeny * 0.05);

        Label hpAmt = new Label(String.valueOf(currentHp));
        hpAmt.setTextFill(new Color(0.0, 0.0, 0.0, 1.0));
        hpAmt.setMinSize(screenx * 0.19, screeny * 0.05);
        hpAmt.setLayoutX(screeny * 0.01);
        hpAmt.setFont(new Font("Segoe UI", screenx * 0.01));

        String name = input.getId();
        name = name.replace(" ID no: ", "");
        while (Character.isDigit(name.charAt(name.length() - 1))){
            name = name.substring(0, name.length() - 1);
        }

        Label nameDisplay = new Label(name);
        nameDisplay.setFont(new Font("Times New Roman", screeny * 0.025));
        nameDisplay.setLayoutX(screeny * 0.01);
        nameDisplay.setTextFill(new Color(0.0,0.0,0.0, 1.0));

        namePane.setMinSize(screenx * 0.19, screeny * 0.03);
        namePane.setMaxSize(screenx * 0.19, screeny * 0.03);
        namePane.setBackground(Background.fill(new Color(0.2, 0.2, 0.2, 1.0)));
        namePane.getChildren().add(nameDisplay);

        hpBox.getChildren().addAll(hpBar, hpAmt);
        info.getChildren().addAll(namePane, hpBox);
        try{
            section.getChildren().set(0, info);
            section.getChildren().get(0).setVisible(true);
        }
        catch (Exception exc){
            section.getChildren().add(info);
            section.getChildren().get(0).setVisible(true);
        }
        if (currentHp <= 0){
            section.getChildren().get(0).setVisible(false);
            input.setVisible(false);
        }
    }
}
