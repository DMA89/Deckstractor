package main.java.se.dma.deckstractor.ui;

import main.java.se.dma.deckstractor.Main;
import main.java.se.dma.deckstractor.services.CardService;
import main.java.se.dma.deckstractor.services.CardServiceImpl;
import main.java.se.dma.deckstractor.utils.Handler;

import javax.swing.*;
import java.awt.*;

public class Frame {
    public static final JEditorPane editorPane = new JEditorPane();
    public static final JLabel cardsFound = new JLabel("Cards found: 0");

    public Frame() {
    }

    //Update GUI after every new card found
    public static void UpdateWindow() {
        editorPane.setText(" ");
        for (int m = 0; m < 30; m++) {
            if (Main.cardCount[m] > 0) {
                editorPane.setText(editorPane.getText() + Main.cardCount[m] + "x: " + Main.cardService.getCard(Main.cardNumb[m]).getName() + "\n ");
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
            if (Main.cardCount[m] == -1) {
                Main.cardCount[m] = Main.cardCount[m + 1];
                Main.cardNumb[m] = Main.cardNumb[m + 1];
            }
        }

    }

    public void init() {
        CardService service = new CardServiceImpl();
        for (int i = 0; i < 535; i++) {
            System.out.println(service.getCard(i));
        }
        for (int x = 0; x < 30; x++) {
            Main.cardNumb[x] = -1;
            Main.cardCount[x] = 0;
        }

        JFrame frame = getjFrame();

        Handler handler = new Handler();

        Main.timer = new Timer(Main.interval, handler);
        Main.timerMore = new Timer(Main.interval, handler);

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

        JMenuItem Druid = new JMenuItem("..as Druid");
        extract.add(Druid);
        Druid.addActionListener(handler);
        JMenuItem Hunter = new JMenuItem("..as Hunter");
        extract.add(Hunter);
        Hunter.addActionListener(handler);
        JMenuItem Mage = new JMenuItem("..as Mage");
        extract.add(Mage);
        Mage.addActionListener(handler);
        JMenuItem Paladin = new JMenuItem("..as Paladin");
        extract.add(Paladin);
        Paladin.addActionListener(handler);
        JMenuItem Priest = new JMenuItem("..as Priest");
        extract.add(Priest);
        Priest.addActionListener(handler);
        JMenuItem Rogue = new JMenuItem("..as Rogue");
        extract.add(Rogue);
        Rogue.addActionListener(handler);
        JMenuItem Shaman = new JMenuItem("..as Shaman");
        extract.add(Shaman);
        Shaman.addActionListener(handler);
        JMenuItem Warlock = new JMenuItem("..as Warlock");
        extract.add(Warlock);
        Warlock.addActionListener(handler);
        JMenuItem Warrior = new JMenuItem("..as Warrior");
        extract.add(Warrior);
        Warrior.addActionListener(handler);
        extract.addSeparator();
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
