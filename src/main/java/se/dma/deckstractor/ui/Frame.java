package main.java.se.dma.deckstractor.ui;

import main.java.se.dma.deckstractor.Main;
import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.services.CardService;
import main.java.se.dma.deckstractor.services.CardServiceImpl;
import main.java.se.dma.deckstractor.services.ClassService;
import main.java.se.dma.deckstractor.services.ClassServiceImpl;
import main.java.se.dma.deckstractor.utils.OSValidator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Just testing a pull request
public class Frame {
    //Timer Interval
    final static int interval = 1;

    //Create Button, Pane and timer
    public static JEditorPane editorPane = new JEditorPane();
    public static JLabel cardsFound = new JLabel("Cards found: 0");
    public static Timer timer;
    public static Timer timerMore;

    //Current card compare int
    public static int i = 0;
    //Search parameters
    public static HearthstoneClass chosenClass = null;
    //Current deck.
    public static int[] cardNumb = new int[30];
    public static int[] cardCount = new int[30];
    public static int currentSlot = 0;
    public static int totCards = 0;
    //Img recognition parameters
    public double test = 0;
    public double percentDiffAllowed = 9;
    public double extraDiffTwenty = 1;
    public static ClassService classService = new ClassServiceImpl();
    public static CardService cardService = new CardServiceImpl();

    public Frame() {
    }

    //Take printscreens for normal search
    public static void GetScreen() {

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

    //Export to hearthPwn
    public static void HearthPwn() {
        String URL = "http://www.hearthpwn.com/deckbuilder/";
        Card card;


        URL = URL + chosenClass.getName().toLowerCase() + "#";

        for (int m = 0; m < 30; m++) {
            if (cardNumb[m] > -1) {
                card = cardService.getCard(cardNumb[m]);
                URL = URL + card.getHearthPwnId() + ":" + cardCount[m] + ";";
            }
        }
        openWebpage(URL);
    }

    //Create card by card .txt
    public static String CreateTxt() {
        String str = "";
        for (int m = 0; m < 30; m++) {
            if (cardNumb[m] > -1) {
                if (cardCount[m] == 2) {
                    str = str + Main.PROPERTIES.getProperty("card.name." + cardNumb[m]) + "\r\n" + Main.PROPERTIES.getProperty("card.name." + cardNumb[m]) + "\r\n";
                } else if (cardCount[m] == 1) {
                    str = str + Main.PROPERTIES.getProperty("card.name." + cardNumb[m]) + "\r\n";
                } else {
                }
            }
        }
        return str;
    }

    //Create normal .txt
    public static String CreateTxtNum() {
        String str = "";
        for (int m = 0; m < 30; m++) {
            if (cardNumb[m] > -1) {
                if (cardCount[m] == 2) {
                    str = str + "2x: " + Main.PROPERTIES.getProperty("card.name." + cardNumb[m]) + "\r\n";
                } else if (cardCount[m] == 1) {
                    str = str + "1x: " + Main.PROPERTIES.getProperty("card.name." + cardNumb[m]) + "\r\n";
                } else {
                }
            }
        }
        return str;
    }

    //Create XML file
    public static String CreateXML(String name) {
        String str = "";
        str = str + "<Deck>" + "\r\n";
        str = str + " <Cards>" + "\r\n";
        for (int m = 0; m < 30; m++) {
            if (cardNumb[m] > -1) {
                str = str + "  <Card>" + "\r\n";
                str = str + "   <Id>" + Main.PROPERTIES.getProperty("blizzard.id." + cardNumb[m]) + "</Id>" + "\r\n";
                str = str + "   <Count>" + cardCount[m] + "</Count>" + "\r\n";
                str = str + "  </Card>" + "\r\n";
            }
        }
        str = str + " </Cards>" + "\r\n";
        str = str + " <Class>" + chosenClass.getName() + "</Class>" + "\r\n";
        str = str + " <Name>" + name + "</Name>" + "\r\n";
        str = str + " <Note />" + "\r\n";
        str = str + " <Tags />" + "\r\n";
        str = str + " <Url />" + "\r\n";
        str = str + "</Deck>" + "\r\n";

        return str;
    }

    //Write .txt/XML files to disk
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

    //Update GUI after every new card found
    public static void UpdateWindow() {
        Card card;
        editorPane.setText(" ");
        for (int m = 0; m < 30; m++) {
            if (cardCount[m] > 0) {
                editorPane.setText(editorPane.getText() + cardCount[m] + "x: " + cardService.getCard(cardNumb[m]).getName() + "\n ");
            }
        }
    }

    //Remove spaces in array keeping track of cards when search is done, also put one gold and one normal cards togeather as one.
    public static void RemoveSpace() {
        for (int m = 0; m < 29; m++) {
            if (cardNumb[m] == cardNumb[m + 1]) {
                cardCount[m] = 2;
                cardCount[m + 1] = 0;
                cardNumb[m + 1] = -1;
            }
        }
        for (int m = 0; m < 29; m++) {
            if (cardCount[m] == -1) {
                cardCount[m] = cardCount[m + 1];
                cardNumb[m] = cardNumb[m + 1];
            }
        }

    }

    //Open URL
    public static void openWebpage(String urlString) {
        Runtime rt = Runtime.getRuntime();
        if(OSValidator.isUnix()) {
            String[] browsers = {"google-chrome-stable","epiphany", "firefox", "mozilla", "konqueror",
                    "netscape","opera","links","lynx"};
            StringBuffer cmd = new StringBuffer();

            for (int i=0; i<browsers.length; i++){
                cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + urlString + "\" ");
            }

            try {
                rt.exec(new String[]{"sh", "-c", cmd.toString()});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(OSValidator.isWindows()) {
            try {
                Desktop.getDesktop().browse(new URL(urlString).toURI());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else if(OSValidator.isMac()) {
            try {
                rt.exec( "open " + urlString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Start new search
    public static void StartSearch() {
        //Button pressed
            editorPane.setText(" ");
        for (int x = 0; x < 30; x++) {
            cardNumb[x] = -1;
            cardCount[x] = 0;
        }
        currentSlot = 0;
        totCards = 0;
        editorPane.setText(" ");
        i = 0;
        GetScreen();
        timer.start();

    }

    //Img compare function
    //Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
    public static double ImgDiffPercent(String IMG1, String IMG2) {
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
    public static double ImgDiffPercentUp(String IMG1, String IMG2) {
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
    public static double ImgDiffPercentDown(String IMG1, String IMG2) {
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

    public void init() {
        CardService service = new CardServiceImpl();
        for (int i = 0; i < 535; i++) {
            System.out.println(service.getCard(i));
        }
        for (int x = 0; x < 30; x++) {
            cardNumb[x] = -1;
            cardCount[x] = 0;
        }

        JFrame frame = getjFrame();

        Handler handler = new Handler();

        timer = new Timer(interval, handler);
        timerMore = new Timer(interval, handler);

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

    //Match images
    public void ImgMatch(int x) {
        //editorPane.setText(editorPane.getText() + x + "\n");
        boolean found = false;
        Card card;

        //Class search
        for (int y = chosenClass.getSearchStart(); y < (chosenClass.getSearchEnd() + 1); y++) {
            card = cardService.getCard(y);
            //Search for matching double cards
            // Create a compare object specifying the 2 images for comparison.
            Path path = Paths.get("DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
            if (Files.exists(path)) {
                test = ImgDiffPercent("TempCards/" + x + ".jpeg", "DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if ((test < percentDiffAllowed) || ((x == 20) && (test < (percentDiffAllowed + extraDiffTwenty)))) {
                    found = true;
                    cardNumb[currentSlot] = y;
                    cardCount[currentSlot] = 2;
                    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                    if (currentSlot > 20) {
                        currentSlot--;
                    } else {
                        currentSlot++;
                    }

                    totCards = totCards + 2;
                    //Match found!

                }
            }
            //Search for matching single cards
            path = Paths.get("SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
            if (Files.exists(path)) {

                test = ImgDiffPercent("TempCards/" + x + ".jpeg", "SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if ((test < percentDiffAllowed) || ((x == 20) && (test < (percentDiffAllowed + extraDiffTwenty)))) {
                    found = true;
                    cardNumb[currentSlot] = y;
                    cardCount[currentSlot]++;
                    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                    if (currentSlot > 20) {
                        currentSlot--;
                    } else {
                        currentSlot++;
                    }
                    totCards++;

                }
            }
            if (found) {
                break;
            }
        }


        //Neutral search
        for (int y = 306; y < 535; y++) {
            card = cardService.getCard(y);
            //Search for matching double cards
            // Create a compare object specifying the 2 images for comparison.
            Path path = Paths.get("DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
            if (Files.exists(path)) {
                test = ImgDiffPercent("TempCards/" + x + ".jpeg", "DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if ((test < percentDiffAllowed) || ((x == 20) && (test < (percentDiffAllowed + extraDiffTwenty)))) {
                    found = true;
                    cardNumb[currentSlot] = y;
                    cardCount[currentSlot] = 2;
                    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                    if (currentSlot > 20) {
                        currentSlot--;
                    } else {
                        currentSlot++;
                    }
                    totCards = totCards + 2;
                    //Match found!


                }
            }
            //Search for matching single cards
            path = Paths.get("SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
            if (Files.exists(path)) {

                test = ImgDiffPercent("TempCards/" + x + ".jpeg", "SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if ((test < percentDiffAllowed) || ((x == 20) && (test < (percentDiffAllowed + extraDiffTwenty)))) {
                    found = true;
                    cardNumb[currentSlot] = y;
                    cardCount[currentSlot]++;
                    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                    if (currentSlot > 20) {
                        currentSlot--;
                    } else {
                        currentSlot++;
                    }
                    totCards++;

                }
            }
            if (found) {
                break;
            }
        }


        // This only runs is Normal test fails###
        if (!found) {
            for (int y = 306; y < 535; y++) {
                card = cardService.getCard(y);

                //One pixel up, double.
                Path path = Paths.get("DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if (Files.exists(path)) {


                    test = ImgDiffPercentUp("TempCards/" + x + ".jpeg", "DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                    if ((test < percentDiffAllowed) || ((x == 20) && (test < (percentDiffAllowed + extraDiffTwenty)))) {
                        found = true;
                        cardNumb[currentSlot] = y;
                        cardCount[currentSlot] = 2;
                        //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                        if (currentSlot > 20) {
                            currentSlot--;
                        } else {
                            currentSlot++;
                        }
                        totCards = totCards + 2;
                        //Match found!

                    }
                }
                //One pixel up
                //Search for matching single cards
                path = Paths.get("SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if (Files.exists(path)) {

                    test = ImgDiffPercentUp("TempCards/" + x + ".jpeg", "SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                    if ((test < percentDiffAllowed) || ((x == 20) && (test < (percentDiffAllowed + extraDiffTwenty)))) {
                        found = true;
                        cardNumb[currentSlot] = y;
                        cardCount[currentSlot]++;
                        //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                        if (currentSlot > 20) {
                            currentSlot--;
                        } else {
                            currentSlot++;
                        }
                        totCards++;

                    }
                }
                //One pixel down, double
                path = Paths.get("DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if (Files.exists(path)) {


                    test = ImgDiffPercentDown("TempCards/" + x + ".jpeg", "DoubleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                    if ((test < percentDiffAllowed) || ((x == 20) && (test < (percentDiffAllowed + extraDiffTwenty)))) {
                        found = true;
                        cardNumb[currentSlot] = y;
                        cardCount[currentSlot] = 2;
                        //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                        if (currentSlot > 20) {
                            currentSlot--;
                        } else {
                            currentSlot++;
                        }
                        totCards = totCards + 2;
                        //Match found!
                    }
                }
                //One pixel down
                //Search for matching single cards
                path = Paths.get("SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                if (Files.exists(path)) {

                    test = ImgDiffPercentDown("TempCards/" + x + ".jpeg", "SingleImgTemplate/" + card.getBlizzardId() + ".jpeg");
                    if ((test < percentDiffAllowed) || ((x == 20) && (test < (percentDiffAllowed + extraDiffTwenty)))) {
                        found = true;
                        cardNumb[currentSlot] = y;
                        cardCount[currentSlot]++;
                        //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
                        if (currentSlot > 20) {
                            currentSlot--;
                        } else {
                            currentSlot++;
                        }
                        totCards++;

                    }
                }
                if (found == true) {
                    break;
                }
            } //End of extra test loop
        } //End of second if-statement


    } //End of ImgMatch

    public class Handler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == timer) {
                //Timer pulse
                if (i == 0) {
                    ImgMatch(i);
                    i++;
                    cardsFound.setText("Cards found: " + totCards);
                } else if (i == 20) {
                    ImgMatch(i);
                    timer.stop();
                    i = 0;
                    cardsFound.setText("Cards found: " + totCards);
                    if (totCards < 30) {
                        String str = "<html><font color=\"red\"> Only " + totCards + " where found, please scroll <br> ALL the way down in your decklist <br> and then press \"Second Extraction\".</font>";
                        cardsFound.setText(str);
                    }

                } else {
                    ImgMatch(i);
                    i++;
                    if (totCards > 29) {
                        timer.stop();
                        i = 0;
                        cardsFound.setText("Cards found: " + totCards);
                    }
                    cardsFound.setText("Cards found: " + totCards);
                }
                UpdateWindow();


            } else if (event.getSource() == timerMore) {

                //Timer pulse
                if (i == 0) {
                    ImgMatch(i);
                    timerMore.stop();
                    i = 0;
                } else {
                    ImgMatch(i);
                    if (totCards > 29) {
                        timerMore.stop();
                        i = 0;
                    }
                    i--;
                }
                cardsFound.setText("Cards found: " + totCards);
                UpdateWindow();
            } else {
                String str;
                Robot robot;
                BufferedImage im;
                File output;
                JTextArea textarea;
                switch (event.getActionCommand()) {
                    case "..as Warrior":
                        chosenClass = classService.getHearthstoneClassByName("Warrior");
                        StartSearch();
                        break;
                    case "..as Warlock":
                        chosenClass = classService.getHearthstoneClassByName("Warlock");
                        StartSearch();
                        break;
                    case "..as Shaman":
                        chosenClass = classService.getHearthstoneClassByName("Shaman");
                        StartSearch();
                        break;
                    case "..as Rogue":
                        chosenClass = classService.getHearthstoneClassByName("Rogue");
                        StartSearch();
                        break;
                    case "..as Priest":
                        chosenClass = classService.getHearthstoneClassByName("Priest");
                        StartSearch();
                        break;
                    case "..as Paladin":
                        chosenClass = classService.getHearthstoneClassByName("Paladin");
                        StartSearch();
                        break;
                    case "..as Mage":
                        chosenClass = classService.getHearthstoneClassByName("Mage");
                        StartSearch();
                        break;
                    case "..as Hunter":
                        chosenClass = classService.getHearthstoneClassByName("Hunter");
                        StartSearch();
                        break;
                    case "..as Druid":
                        chosenClass = classService.getHearthstoneClassByName("Druid");
                        StartSearch();
                        break;
                    case "Export as Text File":
                        RemoveSpace();
                        str = CreateTxtNum();
                        CreateFile(str, ".txt");
                        break;
                    case "Export as Text File (Card by Card)":
                        RemoveSpace();
                        str = CreateTxt();
                        CreateFile(str, ".txt");
                        break;
                    case "Export as XML":
                        RemoveSpace();
                        String xmlClass = JOptionPane.showInputDialog("Choose a name for your deck.");
                        str = CreateXML(xmlClass);
                        CreateFile(str, ".xml");
                        break;
                    case "Export to HearthPwn":
                        RemoveSpace();
                        HearthPwn();
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
                        GetScreenExtra();
                        currentSlot = 29;
                        i = 8;
                        timerMore.start();
                        break;
                } //End of Export as text file.
            }
        }
    }
}
