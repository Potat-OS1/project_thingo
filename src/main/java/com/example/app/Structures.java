package com.example.app;

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class Structures{
    ArrayList<String> structures = new ArrayList<>();
    ArrayList<Integer> hpInformation = new ArrayList<>();
    public void createStructures(double screenx, double screeny, Pane unitPane){
        createTurrets(screenx, screeny, unitPane);
        createInhibitors(screenx, screeny, unitPane);
        createNexus(screenx, screeny, unitPane);
        hpSetter();
    }
    public void createTurrets(double screenx, double screeny, Pane unitPane){
        Rectangle[] turret = new Rectangle[22];
        for (int b = 0; b < turret.length; b++) {
            turret[b] = new Rectangle((screenx * 0.01), (screeny * 0.025));
            turret[b].setFill(new Color(1.0, 1.0, 1.0, 1.0));
            if (b < 4){
                turret[b].setId("Nexus Turret ID no: " + b);
                turret[b].setFill(new Color(0.0, 0.8, 0.8, 1.0));
            }
            if (3 < b && b < 10){
                turret[b].setId("Inhibitor Turret ID no: " + b);
                turret[b].setFill(new Color(0.0, 0.8, 0.6, 1.0));
            }
            if (9 < b && b < 16){
                turret[b].setId("Tier 2 Turret ID no: " + b);
                turret[b].setFill(new Color(0.0, 0.8, 0.4, 1.0));
            }
            if (15 < b){
                turret[b].setId("Tier 1 Turret ID no: " + b);
                turret[b].setFill(new Color(0.0, 0.8, 0.2, 1.0));
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/structure_coords.txt"))));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(b + "x: ")) {
                        turret[b].setX(screenx * (Double.parseDouble(line.replace((b + "x: "), ""))));
                    }
                    if (line.startsWith(b + "y: ")) {
                        turret[b].setY(screeny * (Double.parseDouble(line.replace((b + "y: "), ""))));
                    }
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        hpBar(turret, screenx, screeny, unitPane);
        unitPane.getChildren().addAll(turret);
    }
    public void createInhibitors(double screenx, double screeny, Pane unitPane){
        int inhibAmt = 6;
        Circle[] inhibitor = new Circle[inhibAmt];
        for (int b = 0; b < inhibAmt; b++) {
            inhibitor[b] = new Circle((screenx * 0.01));
            inhibitor[b].setFill(new Color(1.0, 0.0, 0.5, 1.0));
            inhibitor[b].setId("Inhibitor ID no: " + (b + 22));
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/structure_coords.txt"))));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(b + "r: ")) {
                        inhibitor[b].setRadius(screenx * (Double.parseDouble(line.replace((b + "r: "), ""))));
                    }
                    if (line.startsWith(b + "rx: ")) {
                        inhibitor[b].setCenterX(screenx * (Double.parseDouble(line.replace((b + "rx: "), ""))));
                    }
                    if (line.startsWith(b + "ry: ")) {
                        inhibitor[b].setCenterY(screeny * (Double.parseDouble(line.replace((b + "ry: "), ""))));
                    }
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        hpBar(inhibitor, screenx, screeny, unitPane);
        unitPane.getChildren().addAll(inhibitor);
    }
    public void createNexus(double screenx, double screeny, Pane unitPane){
        int nexusAmt = 2;
        Circle[] nexus = new Circle[nexusAmt];
        for (int b = 0; b < nexusAmt; b++) {
            nexus[b] = new Circle((screenx * 0.01));
            nexus[b].setFill(new Color(0.35, 0.1, 0.5, 1.0));
            nexus[b].setId("Nexus ID no: " + (b + 28));
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/structure_coords.txt"))));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(b + "n: ")) {
                        nexus[b].setRadius(screenx * (Double.parseDouble(line.replace((b + "n: "), ""))));
                    }
                    if (line.startsWith(b + "nx: ")) {
                        nexus[b].setCenterX(screenx * (Double.parseDouble(line.replace((b + "nx: "), ""))));
                    }
                    if (line.startsWith(b + "ny: ")) {
                        nexus[b].setCenterY(screeny * (Double.parseDouble(line.replace((b + "ny: "), ""))));
                    }
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        unitPane.getChildren().addAll(nexus);
        hpBar(nexus, screenx, screeny, unitPane);
    }
    public void hpBar(Shape[] input, double screenx, double screeny, Pane unitPane){
        Pane[] structureHp = new Pane[input.length];
        Rectangle[] health = new Rectangle[input.length];
        Rectangle[] hpBackdrop = new Rectangle[input.length];
        for (int i = 0; i < input.length; i++){
            structureHp[i] = new Pane();

            structureHp[i].setLayoutY(input[i].getBoundsInLocal().getMinY() - (screeny * 0.015));
            structureHp[i].setMinSize((screenx * 0.03), (screeny * 0.01));
            structureHp[i].setBackground(Background.fill(new Color(0.2, 0.2, 0.2, 1.0)));

            health[i] = new Rectangle();
            health[i].setLayoutX(screenx * 0.0025);
            health[i].setLayoutY(screeny * 0.0025);
            health[i].setHeight(screeny * 0.005);
            health[i].setWidth(screenx * 0.025);
            health[i].setFill(new Color(0.8, 0.0, 0.4, 1.0));

            hpBackdrop[i] = new Rectangle();
            hpBackdrop[i].setLayoutX(screenx * 0.0025);
            hpBackdrop[i].setLayoutY(screeny * 0.0025);
            hpBackdrop[i].setHeight(screeny * 0.005);
            hpBackdrop[i].setWidth(screenx * 0.025);
            hpBackdrop[i].setFill(new Color(0.0, 0.0, 0.0, 1.0));

            structureHp[i].getChildren().addAll(hpBackdrop[i], health[i]);
            structureHp[i].setLayoutX(input[i].getBoundsInLocal().getCenterX() - (structureHp[i].getBoundsInLocal().getWidth() / 2.0) - (screenx * 0.0015));
            structures.add(input[i].getId());
            structureOnClick(input);
        }
        unitPane.getChildren().addAll(structureHp);
    }
    public void hpSetter(){
        for (String object : structures){
            if(object.startsWith("Nexus Turret ID")){
                hpInformation.add(100);
            }
            if(object.startsWith("Nexus ID")){
                hpInformation.add(200);
            }
            if(object.startsWith("Inhibitor Turret ID")){
                hpInformation.add(120);
            }
            if(object.startsWith("Inhibitor ID")){
                hpInformation.add(100);
            }
            if(object.startsWith("Tier 2 Turret ID")){
                hpInformation.add(110);
            }
            if(object.startsWith("Tier 1 Turret ID")){
                hpInformation.add(100);
            }
        }
    }
    public void structureOnClick(Shape[] input){
        for (Shape inputMod : input) {
            inputMod.setOnMousePressed(event -> {
                InformationPane aaaa = new InformationPane();
                final Node source = (Node) event.getSource();
                String hp = String.valueOf(hpInformation.get(structures.indexOf(source.getId())));
                aaaa.returnInformation(source, hp);
            });
        }
    }
}
