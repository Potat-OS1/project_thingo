package com.update.app.champ.champions;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Feri {
    public String basicDescription(){
        return "COLORBluster Round BREAKLINE Active: Feri shoots out in a line, dealing damage to all targets it hits.";
    }
    public String ultiDescription(){
        return "COLORStatic Invert BREAKLINE Passive: Feri's basic ability scales with Attack Speed " +
                "BREAKLINE COLOROverloaded BREAKLINE Active: Feri's basic ability width and length is increased for a duration.";
    }
    public List<Color> colorOfCard(){
        List<Color> list = new ArrayList<>();
        Color one = new Color(0.0, 0.9, 0.9, 1.0);
        Color two = new Color(0.0, 0.7, 0.7, 1.0);
        list.add(one);
        list.add(two);
        return list;
    }
}
