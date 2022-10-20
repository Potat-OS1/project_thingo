package com.update.app.champ;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.update.app.champ.ChampSelect.*;

public class ChampSelectData {
    public static List<List<List<String>>> champions = new ArrayList<>();
    public static List<ImagePattern> champIcons = new ArrayList<>();
    public static List<String> championNames = new ArrayList<>();
    public static String selectedChampion;
    static boolean ability1toggle = true;
    static boolean ability2toggle = true;
    public void champLists(){
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/champions/champions_list.txt"))));
        String line;
        while(true){
            try {
                if (((line = br.readLine()) != null)){
                    if (line.contains("break")){
                        break;
                    }
                    String championName = line.replace(".png", "");
                    URL url = getClass().getResource("/champions/" + line);
                    assert url != null;
                    ImagePattern img = new ImagePattern(new Image(url.toExternalForm()));
                    if(!championNames.contains(championName)){
                        championNames.add(championName);
                        champIcons.add(img);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
    public Node championIcons(Double width){
        double margin = (width * .1)/5;
        double rectangleTotal = width * .9;
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(margin, margin, margin, margin));
        vbox.setSpacing(margin);
        int hboxAmt = (int)Math.ceil(championNames.size() / 4.0);
        int c = 0;
        for (int a = 0; a < hboxAmt; a++){
            HBox hbox = new HBox();
            hbox.setSpacing(margin);
            for (int b = 0; b < 4; b++){
                Rectangle portrait = new Rectangle(rectangleTotal / 4, rectangleTotal / 4);
                try{
                    portrait.setFill(champIcons.get(c));
                    portrait.setId(championNames.get(c));
                    portrait.setOnMouseClicked(event->{
                        Node source = (Node) event.getSource();
                        selectedChampion = source.getId();
                        //this code is copy pasted because im lazy to properly debug, the change would probably be about the same amount of lines anyways so nbd?
                        for (String listname : championNames){
                            if (listname.contains(selectedChampion)){
                                ChampSelect.championCircle.get(selectedSlot).setFill(champIcons.get(championNames.indexOf(listname)));
                                championCircle.get(selectedSlot).setId(selectedChampion);
                            }
                        }
                        champInformation();
                    });
                    hbox.getChildren().add(portrait);
                }
                catch(Exception e){
                    break;
                }
                c++;
            }
            vbox.getChildren().add(hbox);
        }
        return vbox;
    }
    public void setIcon(){
        for (String listname : championNames){
            if (listname.contains(selectedChampion)){
                ChampSelect.championCircle.get(selectedSlot).setFill(champIcons.get(championNames.indexOf(listname)));
                selectedSlot++;
            }
        }
    }
    public void champInformation(){
        right.getChildren().clear();
        Label name = new Label(selectedChampion);
        name.setFont(new Font("Arial", rightPaneWidth / 10));
        StackPane spane = new StackPane(name);
        spane.setBorder(new Border(new BorderStroke(null, null, color2, null, null, null, BorderStrokeStyle.SOLID, null, null, new BorderWidths(rightPaneWidth / 75), null)));
        VBox column1 = new VBox();
        column1.setAlignment(Pos.CENTER);
        column1.setBorder(new Border(new BorderStroke(null, color2, null, null, null, BorderStrokeStyle.DOTTED, null, BorderStrokeStyle.NONE, null, new BorderWidths(rightPaneWidth / 75), null)));
        VBox column2 = new VBox();
        column2.setAlignment(Pos.CENTER);
        column2.setMinWidth(rightPaneWidth / 3);
        HBox hbox = new HBox(column1, column2);
        VBox vbox = new VBox(spane, hbox);
        vbox.setPadding(new Insets(0, rightPaneWidth / 12, rightPaneWidth / 12, rightPaneWidth / 12));
        vbox.setMaxWidth(rightPaneWidth);
        right.getChildren().add(vbox);
        List<String> stats = Arrays.asList( "Move Speed", "Range", "Health", "Attack Damage", "Ability Power", "Armor", "Magic Resistance", "Attack Speed");
        List<List<String>> temp = championInformation(selectedChampion);
        int i = 0;
        for(String stat : stats){
            Label statLabel = new Label(stat);
            statLabel.setFont(new Font("Arial", rightPaneWidth / 22));
            column1.getChildren().add(statLabel);

            Label statAmount = new Label(temp.get(i).get(1));
            i++;
            statAmount.setFont(new Font("Arial", rightPaneWidth /22));
            column2.getChildren().add(statAmount);
        }
        //champion abilities
        vbox.getChildren().add(championAbilityDescription());
    }
    public Node championAbilityDescription(){
        VBox pane = new VBox();
        pane.setBorder(new Border(new BorderStroke(color2, null, null, null, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, null, new BorderWidths(rightPaneWidth / 75), null)));

        Rectangle ability1 = new Rectangle(rightPaneWidth / 2.5, rightPaneWidth / 8);
        Rectangle ability2 = new Rectangle(rightPaneWidth / 2.5, rightPaneWidth / 8);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox container = new HBox(ability1, region, ability2);
        container.setMaxWidth(rightPaneWidth - (rightPaneWidth / 6));

        Text ability = new Text();
        ability.setVisible(false);

        ability1.setOnMouseClicked(event-> {
            if (ability1toggle) {
                ability.setVisible(true);
//                String string = descriptionGetter("basicDescription");
//                if (string.contains("Passive: ")){
//                    string = string.replace("Passive: ", "");
//                }
                ability.setText(descriptionGetter("basicDescription"));
                ability1toggle = false;
            } else {
                ability1toggle = true;
                ability.setVisible(false);
            }
        });

        ability2.setOnMouseClicked(event-> {
            if (ability2toggle) {
                ability.setVisible(true);
                ability.setText(descriptionGetter("ultiDescription"));
                ability2toggle = false;
            } else {
                ability2toggle = true;
                ability.setVisible(false);
            }
        });

        ability.setWrappingWidth(rightPaneWidth - (rightPaneWidth / 6));
        ability.setFont(new Font("Arial", rightPaneWidth /22));
        pane.getChildren().addAll(container, ability);
        return pane;
    }
    public String descriptionGetter(String ability){
        String description = "";
        try{
            Class<?> c = Class.forName(("com.update.app.champ." + selectedChampion));
            Object obj = c.getDeclaredConstructor().newInstance();

            Method method = obj.getClass().getMethod(ability);
            Object m = method.invoke(obj);
            description = (String) m;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }
    public List<List<String>> championInformation(String name){
        @SuppressWarnings({}) //as far as i know how i coded there should be zero reason *why* this should be null and i didnt wanna look at a soft warning.
        BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/champions/" + name + ".txt"))));
        String line;
        List<List<String>> champion = new ArrayList<>();
        //ugly but it works
        while(true){
            try {
                if (((line = br.readLine()) != null)){
                    if (line.contains("break")){
                        break;
                    }
                    List<String> stat = new ArrayList<>();
                    if(line.contains("MOVE")){
                        stat.add("MOVE");
                        stat.add(line.replace("MOVE ", ""));
                    }
                    if(line.contains("RANGE")){
                        stat.add("RANGE");
                        stat.add(line.replace("RANGE ", ""));
                    }
                    if(line.contains("HP")){
                        stat.add("HP");
                        stat.add(line.replace("HP ", ""));
                    }
                    if(line.contains("AD")){
                        stat.add("AD");
                        stat.add(line.replace("AD ", ""));
                    }
                    if(line.contains("AP")){
                        stat.add("AP");
                        stat.add(line.replace("AP ", ""));
                    }
                    if(line.contains("ARMOR")){
                        stat.add("ARMOR");
                        stat.add(line.replace("ARMOR ", ""));
                    }
                    if(line.contains("MR")){
                        stat.add("MR");
                        stat.add(line.replace("MR ", ""));
                    }
                    if(line.contains("AS")){
                        stat.add("AS");
                        stat.add(line.replace("AS ", ""));
                    }
                    champion.add(stat);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        return champion;
    }
    public void storeList(List<List<String>> list){
        champions.add(list);
    }
}
