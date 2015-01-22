package main.java.se.dma.deckstractor.utils;

import main.java.se.dma.deckstractor.Main;
import main.java.se.dma.deckstractor.ui.Frame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by palle on 22/01/15.
 */
public class Handler implements ActionListener {

    private final Comparer comparer = new Comparer();
    private int i = 0;

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == Main.timer) {
            //Timer pulse
            if (i == 0) {
                comparer.ImgMatch(i);
                i++;
                Frame.cardsFound.setText("Cards found: " + Main.totCards);
            } else if (i == 20) {
                comparer.ImgMatch(i);
                Main.timer.stop();
                i = 0;
                Frame.cardsFound.setText("Cards found: " + Main.totCards);
                if (Main.totCards < 30) {
                    String str = "<html><font color=\"red\"> Only " + Main.totCards + " where found, please scroll <br> ALL the way down in your decklist <br> and then press \"Second Extraction\".</font>";
                    Frame.cardsFound.setText(str);
                }

            } else {
                comparer.ImgMatch(i);
                i++;
                if (Main.totCards > 29) {
                    Main.timer.stop();
                    i = 0;
                    Frame.cardsFound.setText("Cards found: " + Main.totCards);
                }
                Frame.cardsFound.setText("Cards found: " + Main.totCards);
            }
            Frame.UpdateWindow();


        } else if (event.getSource() == Main.timerMore) {

            //Timer pulse
            if (i == 0) {
                comparer.ImgMatch(i);
                Main.timerMore.stop();
                i = 0;
            } else {
                comparer.ImgMatch(i);
                if (Main.totCards > 29) {
                    Main.timerMore.stop();
                    i = 0;
                }
                i--;
            }
            Frame.cardsFound.setText("Cards found: " + Main.totCards);
            Frame.UpdateWindow();
        } else {
            String str;
            Robot robot;
            BufferedImage im;
            File output;
            JTextArea textarea;
            switch (event.getActionCommand()) {
                case "..as Warrior":
                    Main.chosenClass = Main.classService.getHearthstoneClassByName("Warrior");
                    comparer.StartSearch(Frame.editorPane);
                    break;
                case "..as Warlock":
                    Main.chosenClass = Main.classService.getHearthstoneClassByName("Warlock");
                    comparer.StartSearch(Frame.editorPane);
                    break;
                case "..as Shaman":
                    Main.chosenClass = Main.classService.getHearthstoneClassByName("Shaman");
                    comparer.StartSearch(Frame.editorPane);
                    break;
                case "..as Rogue":
                    Main.chosenClass = Main.classService.getHearthstoneClassByName("Rogue");
                    comparer.StartSearch(Frame.editorPane);
                    break;
                case "..as Priest":
                    Main.chosenClass = Main.classService.getHearthstoneClassByName("Priest");
                    comparer.StartSearch(Frame.editorPane);
                    break;
                case "..as Paladin":
                    Main.chosenClass = Main.classService.getHearthstoneClassByName("Paladin");
                    comparer.StartSearch(Frame.editorPane);
                    break;
                case "..as Mage":
                    Main.chosenClass = Main.classService.getHearthstoneClassByName("Mage");
                    comparer.StartSearch(Frame.editorPane);
                    break;
                case "..as Hunter":
                    Main.chosenClass = Main.classService.getHearthstoneClassByName("Hunter");
                    comparer.StartSearch(Frame.editorPane);
                    break;
                case "..as Druid":
                    Main.chosenClass = Main.classService.getHearthstoneClassByName("Druid");
                    comparer.StartSearch(Frame.editorPane);
                    break;
                case "Export as Text File":
                    Frame.RemoveSpace();
                    str = Exporter.CreateTxtNum();
                    Exporter.CreateFile(str, ".txt");
                    break;
                case "Export as Text File (Card by Card)":
                    Frame.RemoveSpace();
                    str = Exporter.CreateTxt();
                    Exporter.CreateFile(str, ".txt");
                    break;
                case "Export as XML":
                    Frame.RemoveSpace();
                    String xmlClass = JOptionPane.showInputDialog("Choose a name for your deck.");
                    str = Exporter.CreateXML(xmlClass);
                    Exporter.CreateFile(str, ".xml");
                    break;
                case "Export to HearthPwn":
                    Frame.RemoveSpace();
                    Exporter.HearthPwn();
                    break;
                case "Create Single Card Template":
                    robot = null;
                    try {
                        robot = new Robot();
                    } catch (AWTException m) {
                        m.printStackTrace();
                    }
                    im = robot.createScreenCapture(new Rectangle(1510, 121, 50, 25));
                    output = new File("SingleImgTemplate.jpeg");
                    try {
                        ImageIO.write(im, "jpeg", output);
                    } catch (IOException p) {
                        p.printStackTrace();
                    }
                    break;
                case "Create Double Card Template":
                    robot = null;
                    try {
                        robot = new Robot();
                    } catch (AWTException m) {
                        m.printStackTrace();
                    }
                    im = robot.createScreenCapture(new Rectangle(1510, 121, 50, 25));
                    output = new File("DoubleImgTemplate.jpeg");
                    try {
                        ImageIO.write(im, "jpeg", output);
                    } catch (IOException p) {
                        p.printStackTrace();
                    }
                    break;
                case "Creating Search Template":
                    textarea = new JTextArea(Main.PROPERTIES.getProperty("notification.instructions"));
                    textarea.setEditable(true);
                    JOptionPane.showMessageDialog(null, textarea, "Creating Search Template", JOptionPane.PLAIN_MESSAGE);
                    break;
                case "Missing Cards":
                    textarea = new JTextArea(Main.PROPERTIES.getProperty("notification.missing.cards"));
                    textarea.setEditable(true);
                    JOptionPane.showMessageDialog(null, textarea, "Creating Search Template", JOptionPane.PLAIN_MESSAGE);
                    break;
                case "Instructions":
                    JOptionPane.showMessageDialog(null, " * Deckstractor only works with 1920x1080 resolution. The \"Fullscreen\" option must be checked. \n   The progam takes printscreens of the deck and without fullscreen and 1920x1080 the cordinates \n   won't align properly." +
                                    "\n \n* If your deck has a scrollbar make sure it's at the very top when you extract your deck. \n   After every card shown has been extracted, scroll ALL the way down and press \n   \"Secondary extraction\"."
                    );
                    break;
                case "Second Extraction (If decklist has scroll)":
                    Comparer.GetScreenExtra();
                    Main.currentSlot = 29;
                    i = 8;
                    Main.timerMore.start();
                    break;
            } //End of Export as text file.
        }
    }
}