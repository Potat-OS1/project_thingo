package com.update.app.champ.information;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stats {
    //storage of commonly accessed stats of the champions, like their stats, inventories, and status effects.
    public static List<List<String>> champion1 = Arrays.asList();
    public static List<List<String>> champion2 = Arrays.asList();
    public static List<List<String>> champion3 = Arrays.asList();
    public static List<List<String>> champion4 = Arrays.asList();
    public static List<List<String>> champion5 = Arrays.asList();
    public static List<List<List<String>>> champions = new ArrayList<>();

    public static List<String> champion1status = Arrays.asList();
    public static List<String> champion2status = Arrays.asList();
    public static List<String> champion3status = Arrays.asList();
    public static List<String> champion4status = Arrays.asList();
    public static List<String> champion5status = Arrays.asList();
    public static List<List<String>> championsStatus = new ArrayList<>();

    public static List<List<Color>> championscolor = new ArrayList<>();

    public static List<Rectangle> championsHpBars = new ArrayList<>();
    public static List<String> championsCurrentHp = new ArrayList<>();
    public static List<String> championsCurrentAs = new ArrayList<>();
    public static List<Rectangle> championsActionBars = new ArrayList<>();

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
    }

    private void storeStatusLists(){
        championsStatus.add(champion1status);
        championsStatus.add(champion2status);
        championsStatus.add(champion3status);
        championsStatus.add(champion4status);
        championsStatus.add(champion5status);
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
