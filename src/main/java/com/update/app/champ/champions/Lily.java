package com.update.app.champ.champions;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Lily {
    public String basicDescription(){
        return "COLORMourning BREAKLINE Active: BREAKLINE Decreases the Ability Power and Attack Damage of all units in Lily's Range.";
    }
    public String ultiDescription(){
        return "COLORPut to Rest BREAKLINE Active: BREAKLINE  " +
                "Put a target to Sleep for 1 turn. Sleeping targets take increased damage, and are Awoken when struck";
    }
    public List<Color> colorOfCard(){
        List<Color> list = new ArrayList<>();
//        Color one = new Color(0.6, 0.4, 0.6, 1.0);
//        Color two = new Color(0.6, 0.3, 0.5, 1.0);
        Color one = new Color(0.5, 0.5, 0.85, 1.0);
        Color two = new Color(0.4, 0.4, 0.65, 1.0);
        list.add(one);
        list.add(two);
        return list;
    }
}
