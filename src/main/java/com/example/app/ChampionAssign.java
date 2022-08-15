package com.example.app;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class ChampionAssign {
    public void BaseStatAssign(Circle[] unit, Circle[] unitMove, Circle[] threat, Pane unitPane, Double screeny, Double screenx, Circle[] champCircle, List<Image> results, List<String> resultNames){
        List<String> champList = Arrays.asList((champCircle[0].getId() + " 1"), champCircle[1].getId(), champCircle[2].getId(), champCircle[3].getId(), champCircle[4].getId());
        List<Integer> selectedChamp = Arrays.asList(0, 1, 2, 3, 4);
        List<Double> champThreat = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
        List<Double> champMove = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0);
        for (int a = 0; a < champCircle.length; a++) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/champions/" + champCircle[a].getId() + ".txt")));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("range")) {
                        champThreat.set(a, (screeny * Double.parseDouble(line.replace("range ", ""))));
                    }
                    if (line.contains("move")){
                        champMove.set(a, (screeny * Double.parseDouble(line.replace("move ", ""))));
                    }
                }
            } catch (Exception exc) {
                System.out.println("fuck you");
            }
        }


        List<Double> xCoord = Arrays.asList(screenx * .215, screenx * .226, screenx * .236, screenx * .245, screenx * .25);
        List<Double> yCoord = Arrays.asList(screeny * .9, screeny * .903, screeny * .91, screeny * .922, screeny * .94);
        for (int i = 0; i < unit.length; i++){
            unit[i].setId(champList.get(selectedChamp.get(i)));
            unit[i].setCenterX(xCoord.get(i));
            unit[i].setCenterY(yCoord.get(i));
            unit[i].setFill(new ImagePattern(results.get(resultNames.indexOf(champCircle[i].getId() + ".png"))));
            unit[i].setRadius(screeny * .01);

            unitMove[i].setId(champList.get(selectedChamp.get(i)) + "'s Movement Range");
            unitMove[i].setRadius(champMove.get(i));
            unitMove[i].setCenterX(xCoord.get(i));
            unitMove[i].setCenterY(yCoord.get(i));
            unitMove[i].setStroke(new Color(.7, 0,.7, 1));
            unitMove[i].setStrokeWidth(3.0);
            unitMove[i].setFill(Color.TRANSPARENT);
            unitMove[i].setVisible(false);

            threat[i].setRadius(champThreat.get(i));
            threat[i].setCenterX(xCoord.get(i));
            threat[i].setCenterY(yCoord.get(i));
            threat[i].setFill(new Color(1.0, 0.0 ,0.5, .25));
            threat[i].setVisible(false);
            unitPane.getChildren().addAll(threat[i], unitMove[i], unit[i]);
        }
    }
}
