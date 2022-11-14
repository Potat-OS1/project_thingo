package com.update.app.champselect;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.update.app.champselect.ChampSelect.*;

public class ChampSelectData {
    public static List<ImagePattern> champIcons = new ArrayList<>();
    public static List<String> championNames = new ArrayList<>();
    public static String selectedChampion;
    static boolean ability1toggle = true;
    static boolean ability2toggle = true;
    public void champLists(){
        //this makes the list for the Center panes scrollbox, and the list for the champion names to be used later.
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
        //this tells how many rows of champions the center will have.
        int hboxAmt = (int)Math.ceil(championNames.size() / 4.0);
        int c = 0;
        for (int a = 0; a < hboxAmt; a++){
            HBox hbox = new HBox();
            hbox.setSpacing(margin);
            //theres 4 portraits to a row, this makes those portraits
            for (int b = 0; b < 4; b++){
                Rectangle portrait = new Rectangle(rectangleTotal / 4, rectangleTotal / 4);
                try{
                    portrait.setFill(champIcons.get(c));
                    portrait.setId(championNames.get(c));
                    portrait.setOnMouseClicked(event->{
                        ability1toggle = true;
                        ability2toggle = true;
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
        //this advances the selected slot and it sets the circle on the left to whatever was locked in.
        if (selectedChampion != null) {
            for (String listname : championNames) {
                if (listname.contains(selectedChampion)) {
                    ChampSelect.championCircle.get(selectedSlot).setFill(champIcons.get(championNames.indexOf(listname)));
                    selectedSlot++;
                }
            }
        }
    }
    public void champInformation(){
        //clear the children of the right pane so that when you switch between hovered champions, you won't keep their information on it.
        right.getChildren().clear();

        //build the right pane information again.
        Label name = new Label(selectedChampion);
        name.setFont(new Font("Arial", rightPaneWidth / 10));

        //
        StackPane spane = new StackPane(name);
        spane.setBorder(new Border(new BorderStroke(null, null, color2, null, null, null, BorderStrokeStyle.SOLID, null, null, new BorderWidths(rightPaneWidth / 75), null)));

        //column 1 of the stats, aka the stat names.
        VBox column1 = new VBox();
        column1.setAlignment(Pos.CENTER);
        column1.setBorder(new Border(new BorderStroke(null, color2, null, null, null, BorderStrokeStyle.DOTTED, null, BorderStrokeStyle.NONE, null, new BorderWidths(rightPaneWidth / 75), null)));
        column1.setMinWidth(rightPaneWidth / 2.37);

        //column 2 of the stats, the actual values of them.
        VBox column2 = new VBox();
        column2.setAlignment(Pos.CENTER);
        column2.setMinWidth(rightPaneWidth / 3);

        //container for columns
        HBox hbox = new HBox(column1, column2);

        //container for everything so far.
        VBox vbox = new VBox(spane, hbox);
        vbox.setPadding(new Insets(0, rightPaneWidth / 12, rightPaneWidth / 12, rightPaneWidth / 12));
        vbox.setMaxWidth(rightPaneWidth);
        right.getChildren().add(vbox);

        //now to actually populate the columns
        List<String> stats = Arrays.asList( "Move Speed", "Range", "Health", "Attack Damage", "Ability Power", "Armor", "Magic Resistance", "Attack Speed");

        //this list contains the stats of the champion thats selected
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
        //champion abilitiy descriptions get added with this.
        vbox.getChildren().add(championAbilityDescription());
    }
    public Node championAbilityDescription(){
        //container for everything
        VBox pane = new VBox();
        pane.setBorder(new Border(new BorderStroke(color2, null, null, null, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, null, new BorderWidths(rightPaneWidth / 75), null)));
        //creating the buttons to call the descriptions of the abilities, and spacing them correctly.
        Rectangle ability1 = new Rectangle(rightPaneWidth / 2.5, rightPaneWidth / 8);
        Rectangle ability2 = new Rectangle(rightPaneWidth / 2.5, rightPaneWidth / 8);
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        HBox container = new HBox(ability1, region, ability2);
        container.setMaxWidth(rightPaneWidth - (rightPaneWidth / 6));

        //creating the text for the abilities when you press the button.
        TextFlow ability = new TextFlow();
        ability.setVisible(false);

        //toggles for visibility of Ability 1 and Ability 2 descriptions
        ability1.setOnMouseClicked(event-> {
            if (ability1toggle) {
                ability.setVisible(true);
                String string = descriptionGetter("basicDescription");
                abilityTextFormatter(string, ability);
                ability1toggle = false;
                ability2toggle = true;
            } else {
                ability2toggle = true;
                ability1toggle = true;
                ability.setVisible(false);
            }
        });

        ability2.setOnMouseClicked(event-> {
            if (ability2toggle) {
                ability.setVisible(true);
                String string = descriptionGetter("ultiDescription");
                abilityTextFormatter(string, ability);
                ability2toggle = false;
                ability1toggle = true;
            } else {
                ability2toggle = true;
                ability1toggle = true;
                ability.setVisible(false);
            }
        });
        pane.getChildren().addAll(container, ability);
        return pane;
    }
    public void abilityTextFormatter(String abilityDesc, TextFlow ability){
        //create an array with regex based on a string. every space the array will treat as the delimiter.
        String[] stringArray = abilityDesc.split("\\s+");
        //clear ability so we can reuse
        ability.getChildren().clear();
        boolean nameColor = false;
        for(String word: stringArray){

            //see if it needs to be colored a different color
            if(word.contains("COLOR")){
                word = word.replace("COLOR", "");
                nameColor = true;
            }
            Text text = new Text(word + " ");

            //sets word colors.
            switch(word){
                case("Passive:")->{
                    text.setFill(Color.ROYALBLUE);
                    nameColor = false;
                    text.setFont(new Font("Arial", rightPaneWidth /22));
                }
                case("Active:")->{
                    text.setFill(Color.CRIMSON);
                    nameColor = false;
                    text.setFont(new Font("Arial", rightPaneWidth /22));
                }
                case("BREAKLINE")->ability.getChildren().add(new Text(System.lineSeparator()));
                //outside of passive, active and breakline, theres two default colors. if nameColor is true, make it the color for titles.
                default->{
                    if (nameColor){
                        text.setFill(Color.DARKGOLDENROD);
                        text.setFont(new Font("Arial", rightPaneWidth /18));
                    }
                    else{
                        text.setFill(Color.GRAY);
                        text.setFont(new Font("Arial", rightPaneWidth /22));
                    }
                }
            }

            if (!word.contains("BREAKLINE")){
                ability.getChildren().add(text);
            }
        }
    }
    public String descriptionGetter(String ability){
        //this grabs the descriptions based on each characters .java file. i could probably have done this in a notepad instead, but wheres the flair in that.
        String description = "";
        try{
            Class<?> c = Class.forName(("com.update.app.champ.champions." + selectedChampion));
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
        List<String> nameField = new ArrayList<>();
        nameField.add("NAME");
        nameField.add(name);
        champion.add(nameField);
        //ugly but it works, it grabs the champions data and puts it into a list. i wanted to use a switch statement.
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
}
