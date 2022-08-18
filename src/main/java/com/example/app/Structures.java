package com.example.app;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Structures {
    public Node createStructures(double screenx, double screeny){
        Pane structurePane = new Pane();
        structurePane.getChildren().addAll(createTurrets(screenx, screeny), createInhibitors(screenx, screeny), createNexus(screenx, screeny));
        return structurePane;
    }
    public Node createTurrets(double screenx, double screeny){
        int turretAmt = 22;
        Rectangle[] turret = new Rectangle[turretAmt];
        Pane turretPane = new Pane();
        for (int b = 0; b < turretAmt; b++) {
            turret[b] = new Rectangle((screenx * 0.01), (screeny * 0.025));
            turret[b].setFill(new Color(1.0, 1.0, 1.0, 1.0));
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/structure_coords.txt")));
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
        turretPane.getChildren().addAll(turret);
        return turretPane;
    }
    public Node createInhibitors(double screenx, double screeny){
        int inhibAmt = 6;
        Circle[] inhibitor = new Circle[inhibAmt];
        Pane inhibitorPane = new Pane();
        for (int b = 0; b < inhibAmt; b++) {
            inhibitor[b] = new Circle((screenx * 0.01));
            inhibitor[b].setFill(new Color(1.0, 0.0, 0.5, 1.0));
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/structure_coords.txt")));
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
        inhibitorPane.getChildren().addAll(inhibitor);
        return inhibitorPane;
    }
    public Node createNexus(double screenx, double screeny){
        int nexusAmt = 2;
        Circle[] nexus = new Circle[nexusAmt];
        Pane nexusPane = new Pane();
        for (int b = 0; b < nexusAmt; b++) {
            nexus[b] = new Circle((screenx * 0.01));
            nexus[b].setFill(new Color(0.35, 0.1, 0.5, 1.0));
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/structure_coords.txt")));
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
        nexusPane.getChildren().addAll(nexus);
        return nexusPane;
    }
}
