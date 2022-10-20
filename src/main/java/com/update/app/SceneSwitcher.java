package com.update.app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SceneSwitcher {
    private static final IntegerProperty sceneNum = new SimpleIntegerProperty();
    public final double getScene(){return sceneNum.get();}
    public static void changeScene(Integer value) {sceneNum.set(value);}
    public IntegerProperty sceneProperty(){return sceneNum;}
}
