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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.*;
import org.slf4j.*;

import java.io.*;
import java.net.URL;
import java.util.*;


public class MyApplication extends Application {
    private final static Logger logger = LoggerFactory.getLogger(MyApplication.class);

    public Button confirmButton, cancelButton, pushButton, freezeButton, safeButton;
    HBox box = new HBox(5);
    int turnCount;
    String focusedChampion = null;
    int focus;
    // 1280/720  1920/1080
    double screenx = 1920;
    double screeny = 1080;
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

        Scene scene = new Scene(pane, screenx, screeny);
        System.out.println(scene.getWidth() + " " + scene.getHeight());
        scenePane.setMinSize(scene.getWidth(), scene.getHeight());
        scenePane.getChildren().addAll(createBackGround(bgPane), unitDraw(unit, unitMove, unitPane, threat));
        pane.getChildren().addAll(scenePane, buttonBox(), champSelect());
        pane.setMinSize(screenx, screeny);

        buttonBoxLogic();
        gameLogic(unitMove, unit, unitPane, threat, bgPane);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
    public Node champSelect(){
        Pane select = new Pane();
        select.setMinWidth(screenx);
        select.setMinHeight(screeny);
        select.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        Circle[] championCircle = new Circle[]{
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
                new Circle(),
        };
        VBox circleHBox = new VBox();

        Rectangle slot = new Rectangle();
        slot.setHeight(screeny * .188);
        slot.setWidth(screenx * .2);
        slot.setLayoutX(screenx * .04);
        slot.setLayoutY(screeny * .02);
        slot.setFill(new Color(0.0,0.0,1.0, 0.5));

        select.getChildren().addAll(slot, circleHBox);
        circleHBox.setLayoutX(screenx * .05);
        circleHBox.setLayoutY(screeny * .025);
        circleHBox.setSpacing(10);
        int increment = 0;
        for (Circle circle : championCircle){
            circleHBox.getChildren().add(circle);
            circle.setFill(new Color(1.0, 1.0, 1.0, 1.0));
            increment++;
            circle.setRadius(screenx * .05);
            circle.setId(String.valueOf(increment));
            circle.setOnMousePressed(event ->{
                final Node source = (Node) event.getSource();
                String id = source.getId();
                System.out.println(id);
            });
        }


        ScrollPane championContainer = new ScrollPane();
        championContainer.setStyle("-fx-background: #ffffff;");
        VBox champVBOX = new VBox();
        champVBOX.setMinSize((screenx * .592), screeny * .85);
        champVBOX.setLayoutX((screenx - champVBOX.getWidth()) / 2);
        champVBOX.setLayoutY(screeny * .02);
        championContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        championContainer.setLayoutY(screeny * .02);
        championContainer.setLayoutX(screenx * .2);
        //championContainer.setMinWidth((screenx * .585));
        championContainer.setMinSize((screenx * .6), screeny * .85);
        championContainer.setMaxHeight(screeny * .85);



        Button removeThis = new Button();
        removeThis.setOnAction((ActionEvent) ->{
            select.setVisible(false);
            removeThis.setVisible(false);

        });
        championContainer.setContent(champVBOX);


        Polygon lockin = new Polygon();
        lockin.getPoints().addAll(new Double[]{
                0.0, 0.0,
                20.0, -10.0,
                120.0, -10.0,
                140.0, 0.0,
                140.0, 40.0,
                120.0, 50.0,
                20.0, 50.0,
                0.0, 40.0,
        });
        lockin.setLayoutX((screenx - lockin.getLayoutBounds().getWidth()) / 2);
        lockin.setLayoutY(screeny * .85);
        lockin.setScaleX(screenx * .0015);
        lockin.setScaleY(screenx * .0015);
        lockin.setFill(new Color(0.0, 1.0, 1.0, 1.0));

        select.getChildren().addAll(championContainer, removeThis, lockin);
        champ(champVBOX, championContainer);

        return select;
    }

    public void champ(VBox champVBOX, ScrollPane championContainer){
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

            champIcon[i].setHeight(((screenx * .6) - 56) /4);
            champIcon[i].setWidth(((screenx * .6) - 56) /4);
            champIcon[i].setFill(new Color(1.0,0.0, 0.0, 1.0));
            Double assignedRow = Math.ceil(i / 4);
            champIcon[i].setOnMousePressed(event ->{
                final Node source = (Node) event.getSource();
                String id = source.getId();
                System.out.println(id);
                focusedChampion = id;
            });
            champions[assignedRow.intValue()].getChildren().add(champIcon[i]);
        }
        for(HBox hbox : champions){
            hbox.setMinWidth(screenx * .5);
            hbox.setMaxWidth(screenx * .5);
        }
        champPortrait(champIcon);
    }
    public void champPortrait(Rectangle[] champIcon) {
        List<Image> results = new ArrayList<>();
        List<String> resultNames = new ArrayList<String>();
        String imageBase = "/champions/";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/champions/champions_list.txt")));
            br.lines().forEach(imageName -> {
                URL imageURL = getClass().getResource(imageBase + imageName);
                Image image = new Image(imageURL.toExternalForm());
                results.add(image);
                resultNames.add(imageName);
            });
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        for (int a = 0; a < results.size(); a++){
            champIcon[a].setFill(new ImagePattern(results.get(a)));
            String id = resultNames.get(a).replace(".png", "");
            champIcon[a].setId(id);
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