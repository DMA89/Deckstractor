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
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by palle on 22/01/15.
 */
public class Handler implements ActionListener {

    public static BufferedImage tempImg[] = new BufferedImage[21];


    public void actionPerformed(ActionEvent event) {
            String str;
            Robot robot;
            BufferedImage im;
            File output;
            JTextArea textarea;

            if(event.getSource()==Main.timer){
                Main.numberOfTimesTimerHasRun++;
                System.out.println("NUMBER OF TIMES: " + Main.numberOfTimesTimerHasRun);
                System.out.println(Main.threadsStarted);
                if((Main.threadsRunning < Runtime.getRuntime().availableProcessors())) {
                    System.out.println("Main.numberOfTimesTimerHasRun: " + Main.numberOfTimesTimerHasRun);
                    System.out.println("threadsStarted: " + Main.threadsStarted);
                    Comparer comparer = new Comparer(false);
                    comparer.start();
                    Frame.cardsFound.setText("Cards found: " + Main.totCards);
                    Frame.UpdateWindow();

                }

                //STOP TIMER?
                if(Main.threadsStarted > 20 || Main.numberOfTimesTimerHasRun > 200) {
                    Main.timer.stop();
                    Frame.RemoveOverThirtyFirst();
                    System.out.println("timer stop");
                }

                Frame.cardsFound.setText("Cards found: " + Main.totCards);
                Frame.UpdateWindow();
            }else if(event.getSource()==Main.timerMore){
                Main.numberOfTimesTimerHasRun++;
                System.out.println(Main.threadsStarted);
                if((Main.threadsRunning < Runtime.getRuntime().availableProcessors())) {
                    Comparer comparer = new Comparer(true);
                    comparer.start();
                    Frame.cardsFound.setText("Cards found: " + Main.totCards);
                    Frame.UpdateWindow();
                }

                //STOP TIMER?
                if((Main.threadsStarted > 8 || Main.numberOfTimesTimerHasRun > 100)) {
                    Main.timerMore.stop();
                    Frame.RemoveOverThirtySecond();
                    Frame.RemoveSpace();
                }

                System.out.println("VI KOM HIT!!!!");
                Frame.cardsFound.setText("Cards found: " + Main.totCards);
                Frame.UpdateWindow();
            }else
            {
                switch (event.getActionCommand()) {
                    case "Extract":
                        StartSearch();
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
                        //Old capture for class recognition
                        //im = robot.createScreenCapture(new Rectangle(1420, 30, 25, 25));
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
                        JOptionPane.showMessageDialog(null, Main.PROPERTIES.getProperty("notification.instructions"));
                        break;
                    case "Missing Cards":
                        int counter = 0;
                        StringBuilder sb = new StringBuilder();
                        sb.append(Main.PROPERTIES.getProperty("notification.missing.cards.start"));
                        String prefix = "";
                        for(int i = 0; i < Integer.valueOf(Main.PROPERTIES.getProperty("total.amount.of.cards")); i++) {
                            String path = "SingleImgTemplate/" + Main.cardService.getCard(i).getBlizzardId() + ".jpeg";
                            if (Files.exists(Paths.get(path))) {
                            }else {
                                if((counter % 8) == 0){
                                    sb.append("\n");
                                }
                                sb.append(prefix);
                                prefix = ", ";
                                sb.append(Main.cardService.getCard(i).getName());
                                counter++;
                            }
                        }
                        sb.append(Main.PROPERTIES.getProperty("notification.missing.cards.end"));
                        String message = sb.toString();
                        JOptionPane.showMessageDialog(null, message);
                        break;
                    case "Instructions":
                        String message1 = " * Deckstractor only works with 1920x1080 resolution. The \"Fullscreen\" option must be checked. \n   The progam takes printscreens of the deck and without fullscreen and 1920x1080 the cordinates \n   won't align properly." +
                                "\n \n* If your deck has a scrollbar make sure it's at the very top when you extract your deck. \n   After every card shown has been extracted, scroll ALL the way down and press \n   \"Secondary extraction\".";

                        JOptionPane.showMessageDialog(null, message1);
                        break;
                    case "Second Extraction (If decklist has scroll)":
                        GetScreenExtra();
                        ComplementSearch();
                        break;
                }
            } //End of Export as text file.
    }

    //Take printscreens for normal search
    private static void GetScreen() {

        int pLeft = 1510;
        int pHeight = 25;
        int pWidth = 50;

        Robot robot = null;

        try {
            robot = new Robot();
        } catch (AWTException m) {
            m.printStackTrace();
        }

        //Capture screens distance to top:
        int[] top = new int[21];
        top[0] = 120;
        top[1] = 161;
        top[2] = 201;
        top[3] = 242;
        top[4] = 282;
        top[5] = 322;
        top[6] = 363;
        top[7] = 403;
        top[8] = 444;
        top[9] = 484;
        top[10] = 525;
        top[11] = 565;
        top[12] = 606;
        top[13] = 646;
        top[14] = 687;
        top[15] = 727;
        top[16] = 767;
        top[17] = 808;
        top[18] = 848;
        top[19] = 889;
        top[20] = 929;

        for (int i = 0; i < 21; i++) {
            tempImg[i] = robot.createScreenCapture(new Rectangle(pLeft, (top[i] + 1), pWidth, pHeight));
        }

    } //End of GetScreen
    //Take printscreens for search after scroll
    public static void GetScreenExtra() {

        int pLeft = 1510;
        int pHeight = 25;
        int pWidth = 50;

        Robot robot = null;

        try {
            robot = new Robot();
        } catch (AWTException m) {
            m.printStackTrace();
        }

        //Capture screens distance to top:
        int[] top = new int[21];
        top[0] = 606;
        top[1] = 646;
        top[2] = 687;
        top[3] = 727;
        top[4] = 767;
        top[5] = 808;
        top[6] = 849;
        top[7] = 889;
        top[8] = 930;

        for (int i = 0; i < 9; i++) {
            tempImg[i] = robot.createScreenCapture(new Rectangle(pLeft, (top[i] - 5), pWidth, pHeight));

        }

    } //End of GetScreenExtra

    public static void StartSearch() {
        //Button pressed
        Main.threadsRunning = 0;
        Main.threadsStarted = 0;
        Main.numberOfTimesTimerHasRun = 0;
        Frame.editorPane.setText(" ");
        for (int x = 0; x < 30; x++) {
            Main.cardNumb[x] = -1;
            Main.cardCount[x] = 0;
        }
        Main.currentSlot = 0;
        Main.totCards = 0;
        Frame.editorPane.setText(" ");
        GetScreen();
        Comparer.CheckClass();
        Main.timer.start();
    }

    public static void ComplementSearch() {
        Main.threadsStarted = 0;
        Main.threadsRunning = 0;
        Main.numberOfTimesTimerHasRun = 0;
        Main.timerMore.start();
    }
}