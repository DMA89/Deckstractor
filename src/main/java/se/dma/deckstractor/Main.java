package main.java.se.dma.deckstractor;

import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.services.CardService;
import main.java.se.dma.deckstractor.services.CardServiceImpl;
import main.java.se.dma.deckstractor.services.ClassService;
import main.java.se.dma.deckstractor.services.ClassServiceImpl;
import main.java.se.dma.deckstractor.ui.Frame;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    public static Properties PROPERTIES;

    public static void main(String[] args) {
        initializeDatabase();
        Frame frame = new Frame();
        frame.init();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.out.println("Unable to load Windows look and feel");
        }
    }

    public static void initializeDatabase() {
        CardService cardService = new CardServiceImpl();
        ClassService classService = new ClassServiceImpl();
        try {
            PROPERTIES = new Properties();
            PROPERTIES.load(Main.class.getResourceAsStream("/main/resources/data.properties"));
            ArrayList<String> classes = new ArrayList<>();
            classes.add("Warrior");
            classes.add("Warlock");
            classes.add("Shaman");
            classes.add("Rogue");
            classes.add("Priest");
            classes.add("Paladin");
            classes.add("Mage");
            classes.add("Hunter");
            classes.add("Druid");
            classes.add("Classless");
            for (int i = 0; i < classes.size(); i++) {
                HearthstoneClass heartstoneClass = new HearthstoneClass();
                heartstoneClass.setId(i);
                heartstoneClass.setName(classes.get(i));
                heartstoneClass.setSearchStart(Integer.valueOf(PROPERTIES.getProperty(classes.get(i).toLowerCase() + ".start")));
                heartstoneClass.setSearchEnd(Integer.valueOf(PROPERTIES.getProperty(classes.get(i).toLowerCase() + ".end")));
                classService.saveHearthstoneClass(heartstoneClass);
            }
            for (int i = 0; i < Integer.valueOf(PROPERTIES.getProperty("total.amount.of.cards")); i++) {
                Card card = new Card();
                card.setName(PROPERTIES.getProperty("card.name." + i));
                card.setBlizzardId(PROPERTIES.getProperty("blizzard.id." + i));
                card.setHearthPwnId(PROPERTIES.getProperty("hearthpwn.id." + i));
                if (i >= 0 && i < 34) {
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("warrior"));
                } else if (i > 33 && i < 68) {
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("warlock"));
                } else if (i > 67 && i < 102) {
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("shaman"));
                } else if (i > 102 && i < 136) {
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("rogue"));
                } else if (i > 135 && i < 170) {
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("priest"));
                } else if (i > 169 && i < 204) {
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("paladin"));
                } else if (i > 203 && i < 238) {
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("mage"));
                } else if (i > 237 && i < 272) {
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("hunter"));
                } else if (i > 271 && i < 306) {
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("druid"));
                } else {
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("classless"));
                }
                cardService.saveCard(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}