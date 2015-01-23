package main.java.se.dma.deckstractor.utils;

import main.java.se.dma.deckstractor.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by palle on 22/01/15.
 */


class Comparer extends Thread {

    private double test = 0;
    private Thread t;
    boolean complementSearch;
    public Comparer(boolean complimentSearch) {
        this.complementSearch = complimentSearch;
    }

    //Start new search
    public void run() {
        boolean found = imgFind(complementSearch);
        if(found) {
            Handler.threadsRunning--;
            try {
                System.out.println("Thread killed");
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Handler.threadsRunning--;
            try {
                System.out.println("Thread killed");
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start ()
    {
        System.out.println("Threads running: " + Handler.threadsRunning);
        if (t == null)
        {
            t = new Thread (this);
            t.start ();
        }
    }

    public static void CheckClass(){
        Robot robot;
        robot = null;
        Double test = 50.0;
        Double percentDifference = 10.0;
        try {
            robot = new Robot();
        } catch (AWTException m) {
            m.printStackTrace();
        }
        BufferedImage classScreenShot = robot.createScreenCapture(new Rectangle(1420, 30, 25, 25));
        String path;

        path = "class-images/" + "constructed-warrior" + ".jpeg";
        if (Files.exists(Paths.get(path))){
            test = ImgDiffPercent(classScreenShot, path, 0);
        }
        System.out.println(test);
        if (test<percentDifference){
            Main.chosenClass = Main.classService.getHearthstoneClassByName("Warrior");
        }

        path = "class-images/" + "constructed-warlock" + ".jpeg";
        if (Files.exists(Paths.get(path))){
            test = ImgDiffPercent(classScreenShot, path, 0);
        }
        System.out.println(test);
        if (test<percentDifference){
            Main.chosenClass = Main.classService.getHearthstoneClassByName("Warlock");
        }

        path = "class-images/" + "constructed-shaman" + ".jpeg";
        if (Files.exists(Paths.get(path))) {
            test = ImgDiffPercent(classScreenShot, path, 0);
        }
        System.out.println(test);
        if (test<percentDifference){
            Main.chosenClass = Main.classService.getHearthstoneClassByName("Shaman");
        }

        path = "class-images/" + "constructed-rogue" + ".jpeg";
        if (Files.exists(Paths.get(path))) {
            test = ImgDiffPercent(classScreenShot, path, 0);
        }
        System.out.println(test);
        if (test<percentDifference){
            Main.chosenClass = Main.classService.getHearthstoneClassByName("Rogue");
        }

        path = "class-images/" + "constructed-priest" + ".jpeg";
        if (Files.exists(Paths.get(path))) {
            test = ImgDiffPercent(classScreenShot, path, 0);
        }
        System.out.println(test);
        if (test<percentDifference){
            Main.chosenClass = Main.classService.getHearthstoneClassByName("Priest");
        }

        path = "class-images/" + "constructed-paladin" + ".jpeg";
        if (Files.exists(Paths.get(path))) {
            test = ImgDiffPercent(classScreenShot, path, 0);
        }
        System.out.println(test);
        if (test<percentDifference){
            Main.chosenClass = Main.classService.getHearthstoneClassByName("Paladin");
        }

        path = "class-images/" + "constructed-mage" + ".jpeg";
        if (Files.exists(Paths.get(path))) {
            test = ImgDiffPercent(classScreenShot, path, 0);
        }
        System.out.println(test);
        if (test<percentDifference){
            Main.chosenClass = Main.classService.getHearthstoneClassByName("Mage");
        }

        path = "class-images/" + "constructed-hunter" + ".jpeg";
        if (Files.exists(Paths.get(path))) {
            test = ImgDiffPercent(classScreenShot, path, 0);
        }
        System.out.println(test);
        if (test<percentDifference){
            Main.chosenClass = Main.classService.getHearthstoneClassByName("Hunter");
        }

        path = "class-images/" + "constructed-druid" + ".jpeg";
        if (Files.exists(Paths.get(path))) {
            test = ImgDiffPercent(classScreenShot, path, 0);
        }
        System.out.println(test);
        if (test<percentDifference){
            Main.chosenClass = Main.classService.getHearthstoneClassByName("Druid");
        }
    }

    //Img compare function
    // (First buffered image, String location for second image, search direction: -1 for up, 1 for down or 0 for stay the same)
    //Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
    private static double ImgDiffPercent(BufferedImage img1, String IMG2, int searchDirection) {
        BufferedImage img2 = null;
        try {
            //URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
            //URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
            img2 = ImageIO.read(new File(IMG2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (searchDirection==-1){
            img1 = img1.getSubimage(0, 0, 50, 24);
            img2 = img2.getSubimage(0, 1, 50, 24);
        }else if (searchDirection==1){
            img1 = img1.getSubimage(0, 1, 50, 24);
            img2 = img2.getSubimage(0, 0, 50, 24);
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



    //Match images
    public boolean ImageMatchCheck(int i, int j, boolean single, int searchDirection, int currentSlot){
        //Card card;
        //card = Main.cardService.getCard(j);
        // Create a compare object specifying the 2 images for comparison.
        String path;
        if (single){
            path = "SingleImgTemplate/" + Main.cardService.getCard(j).getBlizzardId() + ".jpeg";
        }else{
            path = "DoubleImgTemplate/" + Main.cardService.getCard(j).getBlizzardId() + ".jpeg";
        }
        if (Files.exists(Paths.get(path))) {
            test = ImgDiffPercent(Handler.tempImg[i],path,searchDirection);
            if ((test < Main.percentDiffAllowed) || ((i == 20) && (test < (Main.percentDiffAllowed + Main.extraDiffTwenty)))) {
                Main.cardNumb[currentSlot] = j;
                if (single){
                    Main.cardCount[currentSlot]++;
                    Main.totCards++;
                }else{
                    Main.cardCount[currentSlot] = 2;
                    Main.totCards = Main.totCards + 2;
                }
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }



    public boolean imgFind(boolean complimentSearch) {
        boolean found;
        int currentSlot = -1;
        int i = -1;
        if (complimentSearch){
            i=8;
            for (int u = 29; u > 20; u--) {
                if (Main.cardNumb[u] == -1) {
                    currentSlot = u;
                    Main.cardNumb[u] = -2;
                    break;
                }else{
                    i--;
                }

            }
        }else {
            for (int u = 0; u < 21; u++) {
                if (Main.cardNumb[u] == -1) {
                    currentSlot = u;
                    Main.cardNumb[u] = -2;
                    i = u;
                    break;
                }
            }
        }
        if (currentSlot==-1){
            return false;
        }
        System.out.println("i:" + i);
        System.out.println("currentSlot:" + currentSlot);
        //Class search
        for (int j = Main.chosenClass.getSearchStart(); j < (Main.chosenClass.getSearchEnd() + 1); j++) {

            found = ImageMatchCheck(i, j, false, 0, currentSlot);
            if (!found) {
                found = ImageMatchCheck(i, j, true, 0, currentSlot);
            }
            if (found) {
                return true;

            }
        }

        //Neutral search
        for (int j = 306; j < 535; j++) {
            found = ImageMatchCheck(i, j, false, 0, currentSlot);
            if (!found) {
                found = ImageMatchCheck(i, j, true, 0, currentSlot);
            }
            if (found) {
                return true;
            }
        }
        //Extra test with one pixel up and down
        // This only runs is Normal test fails###

        System.out.println("Extra search engaged");
        for (int j = Main.chosenClass.getSearchStart(); j < (Main.chosenClass.getSearchEnd() + 1); j++) {
                //One pixel up, double.
                found = ImageMatchCheck(i, j, false, -1, currentSlot);
                if (!found){
                    //One pixel up, single cards
                    found = ImageMatchCheck(i, j, true, -1, currentSlot);
                }
                if (!found){
                    //One pixel down, double cards
                    found = ImageMatchCheck(i, j, false, 1, currentSlot);
                }
                if (!found){
                    //One pixel down, single cards
                    found = ImageMatchCheck(i, j, true, 1, currentSlot);
                }
                if (found){
                    return true;
                }
        }
        for (int j = 306; j < 535; j++) {
            //One pixel up, double.
            found = ImageMatchCheck(i, j, false, -1, currentSlot);
            if (!found) {
                //One pixel up, single cards
                found = ImageMatchCheck(i, j, true, -1, currentSlot);
            }
            if (!found) {
                //One pixel up, single cards
                found = ImageMatchCheck(i, j, false, 1, currentSlot);
            }
            if (!found) {
                //One pixel up, single cards
                found = ImageMatchCheck(i, j, true, 1, currentSlot);
            }
            if (found) {
                return true;
            }
        }
        return false;
    }

}
