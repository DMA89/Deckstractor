package main.java.se.dma.deckstractor;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static Properties PROPERTIES;
    private static String path;
    public static void main(String[] args) {
        path = "src/main/resources/data.properties";
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        Frame frame = new Frame();
        frame.init();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            System.out.println("Unable to load Windows look and feel");
        }
    }
}