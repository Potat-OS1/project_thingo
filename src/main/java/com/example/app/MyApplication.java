package com.example.app;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.*;
//import org.slf4j.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;
import java.util.*;

public class MyApplication extends Application {
    //private final static Logger logger = LoggerFactory.getLogger(MyApplication.class);

    public Button confirmButton, cancelButton, pushButton, freezeButton, safeButton;
    HBox box = new HBox(5);
    int turnCount;

    int focus;
    @Override
    public void start(Stage primaryStage){
        Circle[] unitMove = new Circle[]{
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
        };
        Circle[] unit = new Circle[]{
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
        };
        Circle[] threat = new Circle[]{
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
        };
        Pane unitPane = new Pane();
        Pane pane = new Pane();
        Pane scenePane = new Pane();
        ImageView bgPane = new ImageView();
        scenePane.getChildren().addAll(createBackGround(bgPane), unitDraw(unit, unitMove, unitPane, threat));
        pane.getChildren().addAll(scenePane, buttonBox(), champSelect(scenePane));

        buttonBoxLogic();
        gameLogic(unitMove, unit, unitPane, threat, bgPane);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
    public Node champSelect(Pane scenePane){
        Pane select = new Pane();
        select.setMinWidth(scenePane.getBoundsInParent().getWidth());
        select.setMinHeight(scenePane.getBoundsInParent().getHeight());
        select.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Rectangle championPane = new Rectangle();
        championPane.setLayoutX(scenePane.getBoundsInParent().getWidth() * .2);
        championPane.setLayoutY(15);
        championPane.setHeight(scenePane.getBoundsInParent().getHeight() * .85);
        championPane.setWidth(scenePane.getBoundsInParent().getWidth() * .585);
        championPane.setFill(new Color(1.0, 1.0, 1.0 ,1.0));

        Circle[] championCircle = new Circle[]{
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
        };
        int champPos = 60;
        for (Circle circle : championCircle){
            select.getChildren().add(circle);
            circle.setFill(new Color(1.0, 1.0, 1.0, 1.0));
            circle.setLayoutX(60);
            circle.setLayoutY(champPos);
            circle.setRadius(45.0);
            champPos = champPos + 100;
        }
        ScrollPane championContainer = new ScrollPane();
        VBox champVBOX = new VBox();
        champVBOX.setLayoutX(scenePane.getBoundsInParent().getWidth() * .2);
        champVBOX.setLayoutY(15);
        champVBOX.setMinSize((scenePane.getBoundsInParent().getWidth() * .5), scenePane.getBoundsInParent().getHeight() * .85);
        championContainer.setLayoutY(15);
        championContainer.setLayoutX(scenePane.getBoundsInParent().getWidth() * .2);
        championContainer.setMinWidth((scenePane.getBoundsInParent().getWidth() * .585));
        championContainer.setMaxSize((scenePane.getBoundsInParent().getWidth() * 2), scenePane.getBoundsInParent().getHeight() * .85);

        champ(champVBOX);

        Button removeThis = new Button();
        removeThis.setOnAction((ActionEvent) ->{
            select.setVisible(false);
            removeThis.setVisible(false);
        });
        championContainer.setContent(champVBOX);
        select.getChildren().addAll(championPane, championContainer, removeThis);
        return select;
    }
    public void champ(VBox champVBOX){
        int champAmt = 20;
        Double hbamt = Math.ceil(champAmt / 4);
        int hboxAmt = hbamt.intValue();

        HBox[] champions = new HBox[hboxAmt];
        Rectangle[] champIcon = new Rectangle[champAmt];
        for(int a = 0; a < hboxAmt; a++){
            champions[a] = new HBox();
            champions[a].setPadding(new Insets(5.0, 5.0, 5.0,5.0));
            champions[a].setSpacing(10.0);
            champVBOX.getChildren().add(champions[a]);
        }
        for (int i = 0; i < champAmt; i++){
            champIcon[i] = new Rectangle();
            champIcon[i].setHeight(105.25);
            champIcon[i].setWidth(105.25);
            champIcon[i].setFill(new Color(1.0,0.0, 0.0, 1.0));
            Double assignedRow = Math.ceil(i / 4);
            champIcon[i].setId("hi " + (i +1));
            champIcon[i].setOnMousePressed(event ->{
                final Node source = (Node) event.getSource();
                String id = source.getId();
                System.out.println(id);
            });
            champions[assignedRow.intValue()].getChildren().add(champIcon[i]);
        }
        champPortrait(champIcon);
    }
    public void champPortrait(Rectangle[] champIcon){
        List<String> results = new ArrayList<String>();
        Path path = Paths.get(URI.create("jrt:/app/champions"));
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)){
            for (Path file: stream) {
                System.out.println(file.getFileName());
                results.add(String.valueOf(file.getFileName()));
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }



//        File[] count = folder.listFiles();
//        for (int i = 0; i < count.length; i++){
//            results.add(count[i].getName());
//        }
        Collections.sort(results);
        for (int a = 0; a < results.size(); a++){
            InputStream portrait = MyApplication.class.getResourceAsStream("/champions/" + results.get(a));
            champIcon[a].setFill(new ImagePattern(new Image(portrait)));
        }
    }
    public Node buttonBox(){
        confirmButton = new Button("Confirm");
        cancelButton = new Button("Cancel");
        pushButton = new Button("Push Lane");
        freezeButton = new Button("Freeze Lane");
        safeButton = new Button("Play Safe");

        box.setPadding(new Insets(3, 3, 3, 3));
        box.getChildren().addAll(confirmButton, cancelButton, pushButton, freezeButton, safeButton);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, null, null)));
        box.setVisible(false);
        return box;
    }

    private void buttonBoxLogic(){
        if (focus == 1){
            confirmButton.setOnAction((ActionEvent) ->{
            });
            cancelButton.setOnAction((ActionEvent) -> box.setVisible(false));
            pushButton.setOnAction((ActionEvent) ->{
            });
            freezeButton.setOnAction((ActionEvent) ->{
            });
            safeButton.setOnAction((ActionEvent) ->{

            });
        }

    }

    public Node unitDraw(Circle[] unit, Circle[] unitMove, Pane unitPane, Circle[] threat){
        List<String> champList = Arrays.asList("Totally not Darius", "Totally not Ornn", "Totally not Ashe", "Totally not Talon", "Totally not Soraka");
        List<Integer> selectedChamp = Arrays.asList(0, 1, 2, 4, 3);
        List<Double> champThreat = Arrays.asList(20.0, 20.0, 40.0, 25.0, 35.0);
        List<Double> champMove = Arrays.asList(50.0, 40.0, 35.0, 50.0, 35.0);
        List<Double> xCoord = Arrays.asList(200.0, 300.0, 400.0, 450.0, 490.0);
        List<Double> yCoord = Arrays.asList(200.0, 300.0, 400.0, 450.0, 470.0);
        for (int i = 0; i < unit.length; i++){
            unit[i].setId(champList.get(selectedChamp.get(i)));
            unit[i].setCenterX(xCoord.get(i));
            unit[i].setCenterY(yCoord.get(i));
            unit[i].setFill(new Color(0,0,1.0, 1.0));
            unit[i].setRadius(8.0);

            unitMove[i].setId(champList.get(selectedChamp.get(i)) + "'s Movement Range");
            unitMove[i].setRadius(champMove.get(i));
            unitMove[i].setCenterX(xCoord.get(i));
            unitMove[i].setCenterY(yCoord.get(i));
            unitMove[i].setStroke(new Color(1.0, 0,1.0, 1));
            unitMove[i].setStrokeWidth(3.0);
            unitMove[i].setFill(Color.TRANSPARENT);

            threat[i].setRadius(champThreat.get(i));
            threat[i].setCenterX(xCoord.get(i));
            threat[i].setCenterY(yCoord.get(i));
            threat[i].setFill(new Color(1.0, 0 ,0, .25));
            unitPane.getChildren().addAll(threat[i], unitMove[i], unit[i]);
        }
            for (Circle circle : unitMove) {
                circle.setVisible(false);
            }
            for (Circle circle : threat) {
                circle.setVisible(false);
            }

        return unitPane;
    }

    public void gameLogic(Circle[] unitMove, Circle[] unit, Pane unitPane, Circle[] threat, ImageView bgPane){
        buttonLogic(unitMove, unit, threat);
        EndTurn(unit, unitMove, unitPane, bgPane);
    }
    public Node detectionButton(Circle[] unitMove, Circle[] unit){
        Button detection = new Button("detect");
        detection.setOnAction(event -> {
            for (int a = 0; a < unit.length; a++){
                Shape u1Intersect = Shape.intersect(unitMove[0], unit[a]);
                if (u1Intersect.getBoundsInLocal().getWidth() != -1 && unit[a] != unit[0]){
                    System.out.println("Hi " + unit[a].getId() + " in " + unit[0].getId() + "'s range");
                }
            }
        });
        return detection;
    }

    public void buttonLogic(Circle[] unitMove, Circle[] unit, Circle[] threat){
        for (int d = 0; d < unit.length; d++){
            unit[d].setOnMousePressed(event ->{
                Node source = (Node) event.getSource();
                String id = source.getId();
                for (int e = 0; e < unit.length; e++){
                    if (Objects.equals(unit[e].getId(), id)){
                        unitMove[e].setVisible(true);
                        threat[e].setVisible(true);
                    }
                    else{
                        unitMove[e].setVisible(false);
                        threat[e].setVisible(false);
                    }
                }
            });
            unitMove[d].setOnMousePressed(event ->{
                Node source = (Node) event.getSource();
                String id = source.getId();
                for (int f = 0; f < unitMove.length; f++){
                    if (Objects.equals(unitMove[f].getId(), id)){
                        unit[f].setCenterX(event.getX());
                        unit[f].setCenterY(event.getY());
                        threat[f].setCenterX(unit[f].getCenterX());
                        threat[f].setCenterY(unit[f].getCenterY());
                    }
                }
            });
        }
    }

    private void EndTurn(Circle[] unitMove, Circle[] unit, Pane unitPane, ImageView bgPane){
        Button endTurn = new Button("End Turn");
        Label tCount = new Label("Turn: " + turnCount);
        endTurn.setOnAction(ActionEvent ->{
            turnCount++;
            for (int i = 0; i < unit.length; i++){
                unit[i].setCenterX(unitMove[i].getCenterX());
                unit[i].setCenterY(unitMove[i].getCenterY());
            }
            tCount.setText("Turn: " + turnCount);
        });
        VBox endBtnBox = new VBox();
        endBtnBox.getChildren().addAll(endTurn, tCount, detectionButton(unit, unitMove));
        unitPane.getChildren().addAll(endBtnBox);
        endBtnBox.setMinWidth(65.0);
        tCount.setAlignment(Pos.CENTER);
        tCount.setMaxWidth(Double.MAX_VALUE);
        tCount.setTextFill(Color.WHITE);
        endBtnBox.setLayoutX(bgPane.getBoundsInParent().getWidth() - 65.0);
    }

    private Node createBackGround(ImageView bgPane){
        InputStream BG = MyApplication.class.getResourceAsStream("/map.png");
        assert BG != null;
        bgPane.setImage(new Image(BG));
        return bgPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}