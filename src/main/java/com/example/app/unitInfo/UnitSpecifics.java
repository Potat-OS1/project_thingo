package com.example.app.unitInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitSpecifics {
    //champion inventories
    static List<String> champ1 = new ArrayList<>(6);
    static List<String> champ2 = new ArrayList<>(6);
    static List<String> champ3 = new ArrayList<>(6);
    static List<String> champ4 = new ArrayList<>(6);
    static List<String> champ5 = new ArrayList<>(6);
    static List<List<String>> champInventories = Arrays.asList(champ1, champ2, champ3, champ4, champ5);
    //champion gold amounts
    static List<Integer> champMoney = Arrays.asList(0,0,0,0,0);
    //champion status effects
    static List<String> champ1Status = new ArrayList<>();
    static List<String> champ2Status = new ArrayList<>();
    static List<String> champ3Status = new ArrayList<>();
    static List<String> champ4Status = new ArrayList<>();
    static List<String> champ5Status = new ArrayList<>();
    ////champion stats -------------------------------------------

    //hp / current hp
    static List<Integer> champ1Hp = new ArrayList<>();
    static List<Integer> champ2Hp = new ArrayList<>();
    static List<Integer> champ3Hp = new ArrayList<>();
    static List<Integer> champ4Hp = new ArrayList<>();
    static List<Integer> champ5Hp = new ArrayList<>();
    public static List<List<Integer>> hpLists = Arrays.asList(champ1Hp, champ2Hp, champ3Hp, champ4Hp, champ5Hp);
    //ad / bonus ad
    static List<Integer> champ1Ad = new ArrayList<>();
    static List<Integer> champ2Ad = new ArrayList<>();
    static List<Integer> champ3Ad = new ArrayList<>();
    static List<Integer> champ4Ad = new ArrayList<>();
    static List<Integer> champ5Ad = new ArrayList<>();
    public static List<List<Integer>> adLists = Arrays.asList(champ1Ad, champ2Ad, champ3Ad, champ4Ad, champ5Ad);
    //bonus ap

    //as / bonus as

    //range / bonus range

    //def / bonus def

    //mr / bonus mr

    //collated lists for each champion
}
