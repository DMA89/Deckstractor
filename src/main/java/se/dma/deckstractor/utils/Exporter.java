package main.java.se.dma.deckstractor.utils;

import main.java.se.dma.deckstractor.Main;
import main.java.se.dma.deckstractor.domain.Card;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by palle on 22/01/15.
 */
class Exporter {
    public static void HearthPwn() {
        String URL = "http://www.hearthpwn.com/deckbuilder/";
        Card card;
        URL = URL + Main.chosenClass.getName().toLowerCase() + "#";

        for (int m = 0; m < 30; m++) {
            if (Main.cardNumb[m] > -1) {
                card = Main.cardService.getCard(Main.cardNumb[m]);
                URL = URL + card.getHearthPwnId() + ":" + Main.cardCount[m] + ";";
            }
        }
        openWebpage(URL);
    }

    public static String CreateTxt() {
        Card card;
        String str = "";
        for (int m = 0; m < 30; m++) {
            if (Main.cardNumb[m] > -1) {
                card = Main.cardService.getCard(Main.cardNumb[m]);
                if (Main.cardCount[m] == 2) {
                    str = str + card.getName() + "\r\n" + card.getName() + "\r\n";
                } else if (Main.cardCount[m] == 1) {
                    str = str + card.getName() + "\r\n";
                } else {
                }
            }
        }
        return str;
    }

    public static String CreateTxtNum() {
        Card card;
        String str = "";
        for (int m = 0; m < 30; m++) {
            if (Main.cardNumb[m] > -1) {
                card = Main.cardService.getCard(Main.cardNumb[m]);
                if (Main.cardCount[m] == 2) {
                    str = str + "2x: " + card.getName() + "\r\n";
                } else if (Main.cardCount[m] == 1) {
                    str = str + "1x: " + card.getName() + "\r\n";
                } else {
                }
            }
        }
        return str;
    }

    public static String CreateXML(String name) {
        Card card;
        String str = "";
        str = str + "<Deck>" + "\r\n";
        str = str + " <Cards>" + "\r\n";
        for (int m = 0; m < 30; m++) {
            if (Main.cardNumb[m] > -1) {
                card = Main.cardService.getCard(Main.cardNumb[m]);
                str = str + "  <Card>" + "\r\n";
                str = str + "   <Id>" + card.getBlizzardId() + "</Id>" + "\r\n";
                str = str + "   <Count>" + Main.cardCount[m] + "</Count>" + "\r\n";
                str = str + "  </Card>" + "\r\n";
            }
        }
        str = str + " </Cards>" + "\r\n";
        str = str + " <Class>" + Main.chosenClass.getName() + "</Class>" + "\r\n";
        str = str + " <Name>" + name + "</Name>" + "\r\n";
        str = str + " <Note />" + "\r\n";
        str = str + " <Tags />" + "\r\n";
        str = str + " <Url />" + "\r\n";
        str = str + "</Deck>" + "\r\n";

        return str;
    }

    public static void CreateFile(String str, String end) {
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(parentFrame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToChoose = fileChooser.getSelectedFile();
            String filePath = fileToChoose.getPath();
            if (!filePath.toLowerCase().endsWith(end)) {
                fileToChoose = new File(filePath + end);
            }
            try (PrintStream out = new PrintStream(new FileOutputStream(fileToChoose))) {
                out.print(str);
            } catch (FileNotFoundException ee) {
                ee.printStackTrace();
            }
        }
    }

    private static void openWebpage(String urlString) {
        Runtime rt = Runtime.getRuntime();
        if (OSValidator.isUnix()) {
            String[] browsers = {"google-chrome-stable", "epiphany", "firefox", "mozilla", "konqueror",
                    "netscape", "opera", "links", "lynx"};
            StringBuilder cmd = new StringBuilder();

            for (int i = 0; i < browsers.length; i++) {
                cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + urlString + "\" ");
            }

            try {
                rt.exec(new String[]{"sh", "-c", cmd.toString()});
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (OSValidator.isWindows()) {
            try {
                Desktop.getDesktop().browse(new URL(urlString).toURI());
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (OSValidator.isMac()) {
            try {
                rt.exec("open " + urlString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
