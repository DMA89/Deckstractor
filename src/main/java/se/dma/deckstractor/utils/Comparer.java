package main.java.se.dma.deckstractor.utils;

import main.java.se.dma.deckstractor.Main;
import main.java.se.dma.deckstractor.domain.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by palle on 22/01/15.
 */
class Comparer {

    //Start new search
    public static void StartSearch(JEditorPane editorPane) {
        //Button pressed
        editorPane.setText(" ");
        for (int x = 0; x < 30; x++) {
            Main.cardNumb[x] = -1;
            Main.cardCount[x] = 0;
        }
        Main.currentSlot = 0;
        Main.totCards = 0;
        editorPane.setText(" ");
        Main.i = 0;
        Comparer.GetScreen();
        Main.timer.start();
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

        for (int x = 0; x < 21; x++) {
            BufferedImage i;
            i = robot.createScreenCapture(new Rectangle(pLeft, (top[x] + 1), pWidth, pHeight));
            File output = new File("TempCards/" + x + ".jpeg");
            try {
                ImageIO.write(i, "jpeg", output);
            } catch (IOException p) {
                p.printStackTrace();
            }
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

        for (int x = 0; x < 9; x++) {
            BufferedImage i = null;
            i = robot.createScreenCapture(new Rectangle(pLeft, (top[x] - 5), pWidth, pHeight));
            File output = new File("TempCards/" + x + ".jpeg");
            try {
                ImageIO.write(i, "jpeg", output);
            } catch (IOException p) {
                p.printStackTrace();
            }
        }

    } //End of GetScreenExtra

    //Img compare function
    //Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
    private static double ImgDiffPercent(String IMG1, String IMG2) {
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        try {
            //URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
            //URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
            img1 = ImageIO.read(new File(IMG1));
            img2 = ImageIO.read(new File(IMG2));
        } catch (IOException e) {
            e.printStackTrace();
        }


        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);
        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);
        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            System.exit(1);
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = (rgb1) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = (rgb2) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;
        return (double) (p * 100.0);

    }

    //Img compare function one pixel up
    //Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
    private static double ImgDiffPercentUp(String IMG1, String IMG2) {
        BufferedImage cimg1 = null;
        BufferedImage cimg2 = null;
        try {
            //URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
            //URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
            cimg1 = ImageIO.read(new File(IMG1));
            cimg2 = ImageIO.read(new File(IMG2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage img1 = cimg1.getSubimage(0, 0, 50, 24);
        BufferedImage img2 = cimg2.getSubimage(0, 1, 50, 24);

        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);
        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);
        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            System.exit(1);
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = (rgb1) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = (rgb2) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;
        return (double) (p * 100.0);

    }

    //Img compare function one pixel down
    //Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
    private static double ImgDiffPercentDown(String IMG1, String IMG2) {
        BufferedImage cimg1 = null;
        BufferedImage cimg2 = null;
        try {
            //URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
            //URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
            cimg1 = ImageIO.read(new File(IMG1));
            cimg2 = ImageIO.read(new File(IMG2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage img1 = cimg1.getSubimage(0, 1, 50, 24);
        BufferedImage img2 = cimg2.getSubimage(0, 0, 50, 24);

        int width1 = img1.getWidth(null);
        int width2 = img2.getWidth(null);
        int height1 = img1.getHeight(null);
        int height2 = img2.getHeight(null);
        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            System.exit(1);
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = (rgb1) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = (rgb2) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;
        return (p * 100.0);

    }

    //Match images
    public void ImgMatch(int x) {
        //editorPane.setText(editorPane.getText() + x + "\n");
        boolean found = false;
        Card card;

        //Class search
        for (int y = Main.chosenClass.getSearchStart(); y < (Main.chosenClass.getSearchEnd() + 1); y++) {
            card = Main.cardService.getCard(y);
            //Search for matching double cards
            // Create a compare object specifying the 2 images for comparison.
            Path path = Paths.get("DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
            if (Files.exists(path)) {
                Main.test = ImgDiffPercent("TempCards/" + x + ".jpeg", "DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if ((Main.test < Main.percentDiffAllowed) || ((x == 20) && (Main.test < (Main.percentDiffAllowed + Main.extraDiffTwenty)))) {
                    found = true;
                    Main.cardNumb[Main.currentSlot] = y;
                    Main.cardCount[Main.currentSlot] = 2;
                    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                    if (Main.currentSlot > 20) {
                        Main.currentSlot--;
                    } else {
                        Main.currentSlot++;
                    }

                    Main.totCards = Main.totCards + 2;
                    //Match found!

                }
            }
            //Search for matching single cards
            path = Paths.get("SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
            if (Files.exists(path)) {

                Main.test = ImgDiffPercent("TempCards/" + x + ".jpeg", "SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if ((Main.test < Main.percentDiffAllowed) || ((x == 20) && (Main.test < (Main.percentDiffAllowed + Main.extraDiffTwenty)))) {
                    found = true;
                    Main.cardNumb[Main.currentSlot] = y;
                    Main.cardCount[Main.currentSlot]++;
                    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                    if (Main.currentSlot > 20) {
                        Main.currentSlot--;
                    } else {
                        Main.currentSlot++;
                    }
                    Main.totCards++;

                }
            }
            if (found) {
                break;
            }
        }


        //Neutral search
        for (int y = 306; y < 535; y++) {
            card = Main.cardService.getCard(y);
            //Search for matching double cards
            // Create a compare object specifying the 2 images for comparison.
            Path path = Paths.get("DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
            if (Files.exists(path)) {
                Main.test = ImgDiffPercent("TempCards/" + x + ".jpeg", "DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if ((Main.test < Main.percentDiffAllowed) || ((x == 20) && (Main.test < (Main.percentDiffAllowed + Main.extraDiffTwenty)))) {
                    found = true;
                    Main.cardNumb[Main.currentSlot] = y;
                    Main.cardCount[Main.currentSlot] = 2;
                    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                    if (Main.currentSlot > 20) {
                        Main.currentSlot--;
                    } else {
                        Main.currentSlot++;
                    }
                    Main.totCards = Main.totCards + 2;
                    //Match found!


                }
            }
            //Search for matching single cards
            path = Paths.get("SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
            if (Files.exists(path)) {

                Main.test = ImgDiffPercent("TempCards/" + x + ".jpeg", "SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if ((Main.test < Main.percentDiffAllowed) || ((x == 20) && (Main.test < (Main.percentDiffAllowed + Main.extraDiffTwenty)))) {
                    found = true;
                    Main.cardNumb[Main.currentSlot] = y;
                    Main.cardCount[Main.currentSlot]++;
                    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                    if (Main.currentSlot > 20) {
                        Main.currentSlot--;
                    } else {
                        Main.currentSlot++;
                    }
                    Main.totCards++;

                }
            }
            if (found) {
                break;
            }
        }


        // This only runs is Normal test fails###
        if (!found) {
            for (int y = 306; y < 535; y++) {
                card = Main.cardService.getCard(y);

                //One pixel up, double.
                Path path = Paths.get("DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if (Files.exists(path)) {


                    Main.test = ImgDiffPercentUp("TempCards/" + x + ".jpeg", "DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                    if ((Main.test < Main.percentDiffAllowed) || ((x == 20) && (Main.test < (Main.percentDiffAllowed + Main.extraDiffTwenty)))) {
                        found = true;
                        Main.cardNumb[Main.currentSlot] = y;
                        Main.cardCount[Main.currentSlot] = 2;
                        //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                        if (Main.currentSlot > 20) {
                            Main.currentSlot--;
                        } else {
                            Main.currentSlot++;
                        }
                        Main.totCards = Main.totCards + 2;
                        //Match found!

                    }
                }
                //One pixel up
                //Search for matching single cards
                path = Paths.get("SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if (Files.exists(path)) {

                    Main.test = ImgDiffPercentUp("TempCards/" + x + ".jpeg", "SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                    if ((Main.test < Main.percentDiffAllowed) || ((x == 20) && (Main.test < (Main.percentDiffAllowed + Main.extraDiffTwenty)))) {
                        found = true;
                        Main.cardNumb[Main.currentSlot] = y;
                        Main.cardCount[Main.currentSlot]++;
                        //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                        if (Main.currentSlot > 20) {
                            Main.currentSlot--;
                        } else {
                            Main.currentSlot++;
                        }
                        Main.totCards++;

                    }
                }
                //One pixel down, double
                path = Paths.get("DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if (Files.exists(path)) {


                    Main.test = ImgDiffPercentDown("TempCards/" + x + ".jpeg", "DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                    if ((Main.test < Main.percentDiffAllowed) || ((x == 20) && (Main.test < (Main.percentDiffAllowed + Main.extraDiffTwenty)))) {
                        found = true;
                        Main.cardNumb[Main.currentSlot] = y;
                        Main.cardCount[Main.currentSlot] = 2;
                        //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                        if (Main.currentSlot > 20) {
                            Main.currentSlot--;
                        } else {
                            Main.currentSlot++;
                        }
                        Main.totCards = Main.totCards + 2;
                        //Match found!
                    }
                }
                //One pixel down
                //Search for matching single cards
                path = Paths.get("SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if (Files.exists(path)) {

                    Main.test = ImgDiffPercentDown("TempCards/" + x + ".jpeg", "SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                    if ((Main.test < Main.percentDiffAllowed) || ((x == 20) && (Main.test < (Main.percentDiffAllowed + Main.extraDiffTwenty)))) {
                        found = true;
                        Main.cardNumb[Main.currentSlot] = y;
                        Main.cardCount[Main.currentSlot]++;
                        //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                        if (Main.currentSlot > 20) {
                            Main.currentSlot--;
                        } else {
                            Main.currentSlot++;
                        }
                        Main.totCards++;

                    }
                }
                if (found) {
                    break;
                }
            }
        }
    }
}
