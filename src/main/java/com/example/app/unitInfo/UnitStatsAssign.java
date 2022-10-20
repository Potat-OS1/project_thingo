package com.example.app.unitInfo;
import java.util.List;

public class UnitStatsAssign {
    public void stat(String line, int a, List<List<Integer>> statList){
        String temp = line.replace("HP ", "");
        temp = temp.replace("AD ", "");
        temp = temp.replace("AS ", "");
        temp = temp.replace("DEF ", "");
        temp = temp.replace("MR ", "");
        int max = Integer.parseInt(temp);
        int current = max;
        statList.get(a).add(max);
        statList.get(a).add(current);
    }
}
