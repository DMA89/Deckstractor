package main.java.se.dma.deckstractor;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.services.implementations.CardServiceImpl;
import main.java.se.dma.deckstractor.services.implementations.ClassServiceImpl;
import main.java.se.dma.deckstractor.services.interfaces.CardService;
import main.java.se.dma.deckstractor.services.interfaces.ClassService;
import main.java.se.dma.deckstractor.ui.Frame;
import main.java.se.dma.deckstractor.utils.OSValidator;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    public static final int[] cardNumb = new int[30];
    public static final int[] cardCount = new int[30];
    public static final double percentDiffAllowed = 8;
    public static final double percentDiffAllowedDuringExtraSearch = 12;
    public static final double extraDiffTwenty = 1;
    public static Properties PROPERTIES;
    public static Timer timer;
    public static Timer timerMore;
    public static HearthstoneClass chosenClass = null;
    public static int currentSlot = 0;
    public static int totCards = 0;
    public static ClassService classService = new ClassServiceImpl();
    public static CardService cardService = new CardServiceImpl();
    public static int numberOfTimesTimerHasRun = 0;
    public static int threadsStarted = 0;
    public static int threadsRunning = 0;

    public static XStream xstream;

    public static void main(String[] args) {
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(Main.class.getResourceAsStream("/main/resources/data.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        xstream = new XStream(new Dom4JDriver());
        xstream.alias("card", Card.class);
        xstream.alias("hearthstoneclass", HearthstoneClass.class);

        classService.initializeClassDatabase();
        cardService.initializeCardDatabase();

        //initializeDatabase();
        try {
            if (OSValidator.isWindows()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else if (OSValidator.isUnix()) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } else if (OSValidator.isMac()) {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Deckstractor");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception e) {
            System.out.println("Error loading \"look and feel\"");
        }
        Frame frame = new Frame();
        frame.init();
    }

    private static void initializeDatabase() {

        try {
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
            classes.add("Neutral");
            for (int i = 0; i < classes.size(); i++) {
                HearthstoneClass heartstoneClass = new HearthstoneClass();
                heartstoneClass.setId(i);
                heartstoneClass.setName(classes.get(i));
                heartstoneClass.setSearchStart(Integer.valueOf(PROPERTIES.getProperty(classes.get(i).toLowerCase() + ".start")));
                heartstoneClass.setSearchEnd(Integer.valueOf(PROPERTIES.getProperty(classes.get(i).toLowerCase() + ".end")));
                classService.saveHearthstoneClass(heartstoneClass);

                String xml = xstream.toXML(heartstoneClass);
                PrintWriter writer = new PrintWriter("/home/palle/development/gitrepos/Deckstractor/src/main/resources/xstream/hearthstoneclasses/" + String.valueOf(heartstoneClass.getId()) + ".xml", "UTF-8");
                writer.println(xml);
                writer.close();
            }
            for (int i = 0; i < Integer.valueOf(PROPERTIES.getProperty("total.amount.of.cards")); i++) {
                Card card = new Card();
                card.setId(i);
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
                    card.setHearthstoneClass(classService.getHearthstoneClassByName("neutral"));
                }
                cardService.saveCard(card);
                String xml = xstream.toXML(card);
                PrintWriter writer = new PrintWriter("/home/palle/development/gitrepos/Deckstractor/src/main/resources/xstream/cards/" + String.valueOf(card.getId()) + ".xml", "UTF-8");
                writer.println(xml);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}