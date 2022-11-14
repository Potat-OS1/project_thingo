package com.update.app.champ.champions;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Gallahan {
    public String basicDescription(){
        return "COLORReap and Sow BREAKLINE " +
                "Active: Gallahan swings their weapon in a cone, If a champion is hit this way and they are close enough, they are Feared.";
    }
    public String ultiDescription(){
        return "COLORGrowing Fear BREAKLINE Passive: Gallahan heals at the end of each turn based on the amount of unit's near him that are Feared. " +
                "BREAKLINE BREAKLINE COLORTerrifying Presence BREAKLINE Active: Enemies within a radius of Gallahan are Feared.";
    }
    public List<Color> colorOfCard(){
        List<Color> list = new ArrayList<>();
        Color one = new Color(0.6, 0.3, 0.3, 1.0);
        Color two = new Color(0.5, 0.3, 0.3, 1.0);
        list.add(one);
        list.add(two);
        return list;
    }
}
