package main.java.se.dma.deckstractor.ui;

import main.java.se.dma.deckstractor.Main;
import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.utils.Handler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frame {
    public static final JEditorPane editorPane = new JEditorPane();
    public static final JLabel cardsFound = new JLabel("Cards found: 0");
    private final int interval = 1;

    public Frame() {
    }

    //Update GUI after every new card found
    public static void UpdateWindow() {
        editorPane.setText(" ");
        for (int m = 0; m < 30; m++) {
            if (Main.cardCount[m] > 0) {
                //System.out.println("M: " + m);
                //System.out.println("Main.cardCount[m]: " + Main.cardCount[m]);
                //System.out.println(Main.cardService.getCard(Main.cardNumb[m]).getName());
                        editorPane.setText(editorPane.getText() + Main.cardCount[m] + "x: " + Main.cardService.getCard(Main.cardNumb[m]).getName() + "\n ");
            }else{
                editorPane.setText(editorPane.getText() + " " + "\n ");
            }

        }
    }


    public static void RemoveOverThirtyFirst(){
        for (int i = 29; i > 13; i--) {
            if (Main.totCards>30) {
                Main.totCards = Main.totCards - Main.cardCount[i];
                Main.cardCount[i] = 0;
                Main.cardNumb[i] = -1;
            }
        }
    }


    public static void RemoveOverThirtySecond(){
        for (int i = 21; i < 30; i++) {
            if (Main.totCards>30) {
                Main.totCards = Main.totCards - Main.cardCount[i];
                Main.cardCount[i] = 0;
                Main.cardNumb[i] = -1;
            }
        }
    }


    //Remove spaces in array keeping track of cards when search is done, also put one gold and one normal cards togeather as one.
    public static void RemoveSpace() {


        for (int m = 0; m < 29; m++) {
            if (Main.cardNumb[m] == Main.cardNumb[m + 1]) {
                Main.cardCount[m] = 2;
                Main.cardCount[m + 1] = 0;
                Main.cardNumb[m + 1] = -1;
            }
        }

        for (int m = 0; m < 29; m++) {
            if (Main.cardNumb[m] == -1 || Main.cardNumb[m] == -2) {
                System.out.println("move it" + m);
                int Counter = 0;
                while((Main.cardNumb[m] == -1 || Main.cardNumb[m] == -2)&&(Counter < 29)){
                    for (int n = m; n < 29; n++){
                        Main.cardCount[n] = Main.cardCount[n + 1];
                        Main.cardNumb[n] = Main.cardNumb[n + 1];
                    }
                    Main.cardCount[29] = 0;
                    Main.cardNumb[29] = -1;
                    Counter++;
                }

                Main.cardNumb[29] = -1;
                Main.cardCount[29] = 0;


                /*
                System.out.println("TEST NUMBER: " + m);
                for (int k = 0; k < 30; k++) {
                    System.out.println(k);
                    System.out.println("CardNumb:" + Main.cardNumb[k]);
                    System.out.println("cardCount:" + Main.cardCount[k]);
                }
                */

            }
        }
    }

    public void init() {
        for (int x = 0; x < 30; x++) {
            Main.cardNumb[x] = -1;
            Main.cardCount[x] = 0;
        }

        JFrame frame = getjFrame();

        Handler handler = new Handler();

        Main.timer = new Timer(interval, handler);
        Main.timerMore = new Timer(interval + 20, handler);

        editorPane.setEditable(true);
        editorPane.setPreferredSize(new Dimension(190, 500));

        initializeMenuBar(frame, handler);

        frame.add(cardsFound);
        frame.add(editorPane);
        frame.revalidate();
    }

    private void initializeMenuBar(JFrame frame, Handler handler) {
        JMenuBar bar = new JMenuBar();
        frame.setJMenuBar(bar);

            JMenu extract = new JMenu("Extract");
            bar.add(extract);

                JMenuItem extractItem = new JMenuItem("Extract");
                extract.add(extractItem);
                extractItem.addActionListener(handler);
                JMenuItem ExtractMore = new JMenuItem("Second Extraction (If decklist has scroll)");
                extract.add(ExtractMore);
                ExtractMore.addActionListener(handler);

            JMenu Export = new JMenu("Export");
            bar.add(Export);

                JMenuItem HearthPwn = new JMenuItem("Export to HearthPwn");
                Export.add(HearthPwn);
                HearthPwn.addActionListener(handler);

                JMenuItem Textfile = new JMenuItem("Export as Text File");
                Export.add(Textfile);
                Textfile.addActionListener(handler);

                JMenuItem Textfile2 = new JMenuItem("Export as Text File (Card by Card)");
                Export.add(Textfile2);
                Textfile2.addActionListener(handler);

                JMenuItem xml = new JMenuItem("Export as XML");
                Export.add(xml);
                xml.addActionListener(handler);

            JMenu Help = new JMenu("Help");
            bar.add(Help);

                JMenuItem Instructions = new JMenuItem("Instructions");
                Help.add(Instructions);
                Instructions.addActionListener(handler);

                JMenuItem MissingCards = new JMenuItem("Missing Cards");
                Help.add(MissingCards);
                MissingCards.addActionListener(handler);

                JMenuItem SearchTemplate = new JMenuItem("Creating Search Template");
                Help.add(SearchTemplate);
                SearchTemplate.addActionListener(handler);

            JMenu Template = new JMenu("Template");
            bar.add(Template);

                JMenuItem SingleTemplate = new JMenuItem("Create Single Card Template");
                Template.add(SingleTemplate);
                SingleTemplate.addActionListener(handler);

                JMenuItem DoubleTemplate = new JMenuItem("Create Double Card Template");
                Template.add(DoubleTemplate);
                DoubleTemplate.addActionListener(handler);
    }

    private JFrame getjFrame() {
        JFrame frame = new JFrame("Deckstractor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(220, 590);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.setAlwaysOnTop(true);
        frame.setFocusable(false);
        frame.setFocusableWindowState(false);
        frame.setLocation(1695, 200);
        return frame;
    }
}
