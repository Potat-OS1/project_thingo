package com.update.app.champ.information;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stats {
    //storage of commonly accessed stats of the champions, like their stats, inventories, and status effects. just so i don't have to track them down between all the classes.
    public static List<List<String>> champion1 = Arrays.asList();
    public static List<List<String>> champion2 = Arrays.asList();
    public static List<List<String>> champion3 = Arrays.asList();
    public static List<List<String>> champion4 = Arrays.asList();
    public static List<List<String>> champion5 = Arrays.asList();
    public static List<List<List<String>>> champions = new ArrayList<>();

    //the champions current stats. at the start of the game they are just a copy of their initial stats. they will be updated when items are bought.
    public static List<List<String>> champion1Current = Arrays.asList();
    public static List<List<String>> champion2Current = Arrays.asList();
    public static List<List<String>> champion3Current = Arrays.asList();
    public static List<List<String>> champion4Current = Arrays.asList();
    public static List<List<String>> champion5Current = Arrays.asList();
    public static List<List<List<String>>> championsCurrent = new ArrayList<>();

    //status effects on the champion. these lists start empty.
    public static List<String> champion1status = Arrays.asList();
    public static List<String> champion2status = Arrays.asList();
    public static List<String> champion3status = Arrays.asList();
    public static List<String> champion4status = Arrays.asList();
    public static List<String> champion5status = Arrays.asList();
    public static List<List<String>> championsStatus = new ArrayList<>();

    //the list of the colors their champion card is.
    public static List<List<Color>> championscolor = new ArrayList<>();

    //list of the rectangles/their current hp in the champion card so they can be updated based on their current HP when they take damage or heal.
    public static List<Rectangle> championsHpBars = new ArrayList<>();
    public static List<String> championsCurrentHp = new ArrayList<>();

    //list of the rectangles/current as of the champion card so they can be updated based on their current AS.
    public static List<String> championsCurrentAs = new ArrayList<>();
    public static List<Rectangle> championsActionBars = new ArrayList<>();

    //list of all the items in their inventory. rectangles so we can just apply a new image and onMouseHovered text effect.
    public static List<Rectangle> champion1Inventory = new ArrayList<>();
    public static List<Rectangle> champion2Inventory = new ArrayList<>();
    public static List<Rectangle> champion3Inventory = new ArrayList<>();
    public static List<Rectangle> champion4Inventory = new ArrayList<>();
    public static List<Rectangle> champion5Inventory = new ArrayList<>();
    public static List<List<Rectangle>> championsInventories = new ArrayList<>();

    public static int a = 0;
    public void storeStats(List<List<String>> lists){
        System.out.println(lists);
        switch (a) {
            case (0) -> champion1 = lists;
            case (1) -> champion2 = lists;
            case (2) -> champion3 = lists;
            case (3) -> champion4 = lists;
            case (4) -> {
                champion5 = lists;
                if (champions.isEmpty()) {
                    storeListsInList();
                    storeStatusLists();
                    colors();
                    initialValues();
                }
            }
        }
        a++;
    }

    private void storeListsInList(){
        champions.add(champion1);
        champions.add(champion2);
        champions.add(champion3);
        champions.add(champion4);
        champions.add(champion5);

        champion1Current = champion1;
        champion2Current = champion2;
        champion3Current = champion3;
        champion4Current = champion4;
        champion5Current = champion5;

        championsCurrent.add(champion1Current);
        championsCurrent.add(champion2Current);
        championsCurrent.add(champion3Current);
        championsCurrent.add(champion4Current);
        championsCurrent.add(champion5Current);
    }

    private void storeStatusLists(){
        championsStatus.add(champion1status);
        championsStatus.add(champion2status);
        championsStatus.add(champion3status);
        championsStatus.add(champion4status);
        championsStatus.add(champion5status);
    }

    //this one works in reverse because it was easier and less fiddly.
    public void storeInventoriesList(){
        champion1Inventory = championsInventories.get(0);
        champion2Inventory = championsInventories.get(1);
        champion3Inventory = championsInventories.get(2);
        champion4Inventory = championsInventories.get(3);
        champion5Inventory = championsInventories.get(4);

    }

    private void initialValues(){
        for(List<List<String>> champ : champions) {
            championsCurrentHp.add(champ.get(3).get(1));
            championsCurrentAs.add(champ.get(8).get(1));
        }
    }

    private void colors(){
        for(List<List<String>> champList : champions){
            String championName = champList.get(0).get(1);
            try{
                //create a class object basedon the location string
                Class<?> c = Class.forName(("com.update.app.champ.champions." + championName));
                Object obj = c.getDeclaredConstructor().newInstance();
                //create a method of the class created above
                Method method = c.getDeclaredMethod("colorOfCard");
                method.setAccessible(true);
                //invoke the method.
                championscolor.add((List<Color>) method.invoke(obj));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
