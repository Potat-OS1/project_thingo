package com.update.app.champ.information;

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
                }
            }
        }
        System.out.println(champions);
        a++;
    }
    void storeListsInList(){
        champions.add(champion1);
        champions.add(champion2);
        champions.add(champion3);
        champions.add(champion4);
        champions.add(champion5);
    }
}
