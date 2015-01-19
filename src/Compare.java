import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.*;
import javax.swing.Timer;
import java.net.URL;
import java.nio.file.*;


import javax.imageio.ImageIO;


public class Compare
{	
	
	//Timer Interval
	final static int interval = 1;
	
	//ID Strings
	public static String[] ID = new String[535];
	public static String[] Name = new String[535];
	public static String[] HearthPwnID = new String[535];
	
	//Create Button, Pane and timer
    public static JEditorPane editorPane = new JEditorPane();
    public static JLabel totalcards = new JLabel("Cards found: 0");
    public static Timer timer;
    public static Timer timerMore;
    
    //Current card compare int
   	public static int i = 0;
   	
   	//Img recognition parameters
   	public double test = 0;
   	public double percentdiffallowed = 9;
   	public double extradifftwenty = 1;
   	
   	//Search parameters
   	public int StartID = -1;
   	public int EndID = -1;
    public static int ChosenClass = -1;
    public static String ChosenClassStr = "";
   	
   	
   	public static void main(String[] args){
		Compare com = new Compare();
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception e){
			System.out.println("Unable to load Windows look and feel");
		}
		
	}
	
   	//Current deck.
   	public static int[] CardNumb = new int[30];
   	public static int[] CardCount = new int[30];

    public static int CurrentSlot = 0;
    public static int TotCards = 0;

   	
	public Compare(){
		
		for(int x = 0; x < 30; x++){ 
			CardNumb[x] = -1;
			CardCount[x] = 0;
		}
		
		//Populate Strings
		populateStringArray();
		
		//Create frame
		JFrame frame = new JFrame("Deckstractor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(220,590);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		frame.setAlwaysOnTop (true);
		frame.setFocusable(false);
		frame.setFocusableWindowState(false);
		frame.setLocation(1695,200);
		
			
		//Create event handler
		thehandler handler = new thehandler();
		

		//Create timer
		timer = new Timer(interval, handler);
		timerMore = new Timer(interval, handler);
		
		//editorPane options
		editorPane.setEditable(true);
		editorPane.setPreferredSize(new Dimension(190, 500));
						
		JMenuBar bar = new JMenuBar();
		frame.setJMenuBar(bar);
		
			JMenu Extract = new JMenu("Extract");
			bar.add(Extract);
		
				JMenuItem Druid = new JMenuItem("..as Druid");
				Extract.add(Druid);
				Druid.addActionListener(handler);
				JMenuItem Hunter = new JMenuItem("..as Hunter");
				Extract.add(Hunter);
				Hunter.addActionListener(handler);
				JMenuItem Mage = new JMenuItem("..as Mage");
				Extract.add(Mage);
				Mage.addActionListener(handler);
				JMenuItem Paladin = new JMenuItem("..as Paladin");
				Extract.add(Paladin);
				Paladin.addActionListener(handler);
				JMenuItem Priest = new JMenuItem("..as Priest");
				Extract.add(Priest);
				Priest.addActionListener(handler);
				JMenuItem Rogue = new JMenuItem("..as Rogue");
				Extract.add(Rogue);
				Rogue.addActionListener(handler);
				JMenuItem Shaman = new JMenuItem("..as Shaman");
				Extract.add(Shaman);
				Shaman.addActionListener(handler);
				JMenuItem Warlock = new JMenuItem("..as Warlock");
				Extract.add(Warlock);
				Warlock.addActionListener(handler);
				JMenuItem Warrior = new JMenuItem("..as Warrior");
				Extract.add(Warrior);
				Warrior.addActionListener(handler);
				Extract.addSeparator();
				JMenuItem ExtractMore = new JMenuItem("Second Extraction (If decklist has scroll)");
				Extract.add(ExtractMore);
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
				
		//Add stuff to frame
		

		frame.add(totalcards);
		frame.add(editorPane);
		frame.revalidate();
	
	} //Constructor end.
	
	//Action listener
	public class thehandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource()==timer){	

				//Timer pulse
				if(i==0){
					ImgMatch(i);
					i++;
					totalcards.setText("Cards found: " + TotCards);
				}else if(i==20){
					ImgMatch(i);
					timer.stop();
					i=0;
					totalcards.setText("Cards found: " + TotCards);
					if (TotCards<30){
						String str = "<html><font color=\"red\"> Only " + TotCards + " where found, please scroll <br> ALL the way down in your decklist <br> and then press \"Second Extraction\".</font>";
						totalcards.setText(str);
					}

				}else{
					ImgMatch(i);
					i++;
					if (TotCards>29){
						timer.stop();
						i=0;
						totalcards.setText("Cards found: " + TotCards);
					}
					totalcards.setText("Cards found: " + TotCards);
				}
				UpdateWindow();
				

			}else if(event.getSource()==timerMore){
				
				//Timer pulse
				if(i==0){
					ImgMatch(i);
					timerMore.stop();
					i=0;
				}else{
					ImgMatch(i);
					if (TotCards>29){
						timerMore.stop();
						i=0;
					}
					i--;
				}
				totalcards.setText("Cards found: " + TotCards);
				UpdateWindow();
			}else if("..as Warrior".equals(event.getActionCommand())){
				ChosenClass=1;
				ChosenClassStr="warrior";
			   	StartID = 0;
			   	EndID = 33;
			   	StartSearch();
			}else if("..as Warlock".equals(event.getActionCommand())){
				ChosenClass=2;
				ChosenClassStr="warlock";
			   	StartID = 34;
			   	EndID = 67;
			   	StartSearch();
			}else if("..as Shaman".equals(event.getActionCommand())){
				ChosenClass=3;
				ChosenClassStr="shaman";
			   	StartID =68;
			   	EndID =101;
			   	StartSearch();
			}else if("..as Rogue".equals(event.getActionCommand())){
				ChosenClass=4;
				ChosenClassStr="rogue";
			   	StartID =102;
			   	EndID =135;
			   	StartSearch();
			}else if("..as Priest".equals(event.getActionCommand())){
				ChosenClass=5;
				ChosenClassStr="priest";
			   	StartID =136;
			   	EndID =169;
			   	StartSearch();
			}else if("..as Paladin".equals(event.getActionCommand())){
				ChosenClass=6;
				ChosenClassStr="paladin";
			   	StartID =170;
			   	EndID =203;
			   	StartSearch();
			}else if("..as Mage".equals(event.getActionCommand())){
				ChosenClass=7;
				ChosenClassStr="mage";
			   	StartID =204;
			   	EndID =237;
			   	StartSearch();
			}else if("..as Hunter".equals(event.getActionCommand())){
				ChosenClass=8;
				ChosenClassStr="hunter";
			   	StartID =238;
			   	EndID =271;
			   	StartSearch();
			}else if("..as Druid".equals(event.getActionCommand())){
				ChosenClass=9;
				ChosenClassStr="druid";
			   	StartID =272;
			   	EndID =305;
			   	StartSearch();
			}else if("Export as Text File".equals(event.getActionCommand())){
				RemoveSpace();
				String str = CreateTxtNum();
				CreateFile(str, ".txt");
			}else if("Export as Text File (Card by Card)".equals(event.getActionCommand())){
				RemoveSpace();
				String str = CreateTxt();
				CreateFile(str, ".txt");	
			}else if("Export as XML".equals(event.getActionCommand())){
				RemoveSpace();
				String xmlclass = JOptionPane.showInputDialog("Choose a name for your deck.");
				String str = CreateXML(xmlclass);
				CreateFile(str, ".xml");
			}else if("Export to HearthPwn".equals(event.getActionCommand())){
				RemoveSpace();
				HearthPwn();
			}else if("Create Single Card Template".equals(event.getActionCommand())){
				Robot robot = null;
				try {
					robot = new Robot ();
				} catch (AWTException m) {
					m.printStackTrace();
				}
				BufferedImage i = null;
				i = robot.createScreenCapture(new Rectangle(1510,121,50,25));
				File output = new File("SingleImgTemplate.jpeg");
				try {
					ImageIO.write(i, "jpeg", output );
				} catch (IOException p) {
					p.printStackTrace();
				}
			}else if("Create Double Card Template".equals(event.getActionCommand())){
				Robot robot = null;
				try {
					robot = new Robot ();
				} catch (AWTException m) {
					m.printStackTrace();
				}
				BufferedImage i = null;
				i = robot.createScreenCapture(new Rectangle(1510,121,50,25));
				File output = new File("DoubleImgTemplate.jpeg");
				try {
					ImageIO.write(i, "jpeg", output );
				} catch (IOException p) {
					p.printStackTrace();
				}
			}else if("Creating Search Template".equals(event.getActionCommand())){
				JTextArea textarea= new JTextArea("The program needs Search Templates to be able to detect cards, one for single cards and one for doubles. " +
						"\nThe program is missing some of these templates because I personally don't have the cards. See the list" +
						"\nin \"Missing Cards\" under Help." +  
						"\n" + 
						"\nTo create a template follow the stepts below:" +
						"\n" + 
						"\n1. Make a new deck." +
						"\n" +
						"\n2. Put a single card or double card in the deck (depending on if you want to make a single template or a double)" +
						"\n" +
						"\n3. Click \"Template\" then choose single or double." +
						"\n" +
						"\n4. Rename the image to the corresponding ID (found in the missing cards.txt in the deckstactor folder)." +
						"\n" +
						"\n5. Put the renamed template in the single or double template folder." +
						"\n" +
						"\n6. DONE! Your program should now be able to detect the new card." +
						"\n" +					
						"\nIt would be of great help if you could email me these new templates at deckstractor@gmail.com so that " +
						"\nI can make the database complete for future releases." +
						"\nWhen sending me pictures, name the image with the corresponding id AND also if its a single or double " +
						"\npicture: Forexample: CS2_037_Single Please also put the name of the cards in the email subject, thanks!" +
						"\nIf you want to get credited (and you are the first person to send that template) just let me know in the email."
						);
				 textarea.setEditable(true);
				 JOptionPane.showMessageDialog(null, textarea, "Creating Search Template", JOptionPane.PLAIN_MESSAGE);
			}else if("Missing Cards".equals(event.getActionCommand())){
				JTextArea textarea= new JTextArea(
						" The Deckstractor database is not yet complete, therefore it can't detect all cards. " +
						"\n I don't have the following cards and I am therefore unable to make search-templates for them myself:" + 
						"\n" +
						"\n Legendary:" +
						"\n" +
						"\n Iron Juggernaut, Mal'Ganis, Al'Akir the Windlord, Neptulon, Trade Prince Gallywix," + 
						"\n Vol'jin, Bolvar Fordragon, Flame Leviathan, King Krush, Gahz'rilla, Malorne," +
						"\n Alexstrasza, Baron Geddon, Gruul, Hogger, King Mukla, Lorewalker Cho," +
						"\n Millhouse Manastorm, Nat Pagle, Nozdormu, Onyxia, The Black Knight, Tinkmaster Overspark, " +
						"\n Ysera, Blingtron 3000, Foe Reaper 4000, Mekgineer Thermaplugg, Mimiron's Head," +
						"\n Mogor the Ogre, Sneed's Old Shredder, Toshley, Troggzor the Earthinator, Elite Tauren Chieftain" +
						"\n" + 
						"\n Epic: " +
						"\n" + 
						"\n Twisting Nether, Anima Golem, Ancestor's Call, Preparation, Lightbomb, Shadowbomber," +
						"\n Coghammer, Quartermaster, Feign Death, Steamwheedle Sniper, Tree of Life, Clockwork Giant, " +
						"\n Mini-Mage, Piloted Sky Golem" + 
						"\n" +
						"\n" +
						"\n There are also some epics that I only have 1 of. The program can therefor only detect if you have one " +
						"\n of those, I still need the search-template for having two." +
						"\n" +
						"\n Epic(Missing double-search-template):" +
						"\n" + 
						"\n Dark Wispers, Avenging Wrath, Bane of Doom, Big Game Hunter, Bouncing Blade, Cogmaster's Wrench," +
						"\n Crush, Far Sight, Gladiator's Longbow, Hungry Crab, Junkbot, Lay on Hands, Pit Lord, Recombobulator, " +
						"\n Sabotage, Sword of Justice, Wee Spellstopper," +
						"\n" + 
						"\n The program should be able to detect ALL Rares or lower quality, either one or two cards." + 
						"\n" + 
						"\n If you have some of these cards and want to make the program able to detect them, or even better, help me" +
						"\n make the original program able to detect them. Then please check out \"Creating Search Template\" under help."
						);
				 textarea.setEditable(true);
				 JOptionPane.showMessageDialog(null, textarea, "Creating Search Template", JOptionPane.PLAIN_MESSAGE);
			}else if("Instructions".equals(event.getActionCommand())){
				JOptionPane.showMessageDialog(null, " * Deckstractor only works with 1920x1080 resolution. The \"Fullscreen\" option must be checked. \n   The progam takes printscreens of the deck and without fullscreen and 1920x1080 the cordinates \n   won't align properly." +
						"\n \n* If your deck has a scrollbar make sure it's at the very top when you extract your deck. \n   After every card shown has been extracted, scroll ALL the way down and press \n   \"Secondary extraction\"."
						);
	        }else if("Second Extraction (If decklist has scroll)".equals(event.getActionCommand())){
				GetScreenExtra();
				CurrentSlot=29;
				i=8;
				timerMore.start();
				} //End of Export as text file. 
			}
		}//End of the handler.

	
	//Take printscreens for normal search
	public static void GetScreen(){
	
		int pleft = 1510;
		int pheight = 25;
		int pwidth = 50;
    		    
		Robot robot = null;
			
		try {
			robot = new Robot ();
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
		    
		for(int x = 0; x < 21; x++){
			BufferedImage i = null;
			i = robot.createScreenCapture(new Rectangle(pleft,(top[x] + 1),pwidth,pheight) );
			File output = new File("TempCards\\" + x + ".jpeg");
			try {
				ImageIO.write(i, "jpeg", output );
			} catch (IOException p) {
				p.printStackTrace();
			}
		}
			
	} //End of GetScreen
	
	
	//Take printscreens for search after scroll
	public static void GetScreenExtra(){
		
		int pleft = 1510;
		int pheight = 25;
		int pwidth = 50;
    		    
		Robot robot = null;
			
		try {
			robot = new Robot ();
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

		for(int x = 0; x < 9; x++){
			BufferedImage i = null;
			i = robot.createScreenCapture(new Rectangle(pleft,(top[x] - 5),pwidth,pheight) );
			File output = new File("TempCards\\" + x + ".jpeg");
			try {
				ImageIO.write(i, "jpeg", output );
			} catch (IOException p) {
				p.printStackTrace();
			}
		}
					
	} //End of GetScreenExtra
	
	
	//Match images
	public void ImgMatch(int x){
			//editorPane.setText(editorPane.getText() + x + "\n");
		boolean found = false;
		
		//Class search
		for (int y = StartID; y < (EndID+1); y++){
			//Search for matching double cards
			// Create a compare object specifying the 2 images for comparison.
			Path path = Paths.get("DoubleImgTemplate\\" + ID[y] + ".jpeg");
			if (Files.exists(path)) {
				test = ImgDiffPercent("TempCards\\" + x + ".jpeg", "DoubleImgTemplate\\" + ID[y] + ".jpeg");
				if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
					found = true;
					CardNumb[CurrentSlot] = y;
				    CardCount[CurrentSlot] = 2;
				    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
				    if (CurrentSlot>20){
				    	CurrentSlot--;	
				    }else{
				    	CurrentSlot++;
				    }
				    
				    TotCards = TotCards +2;
				    //Match found!
														
				}
			}
			//Search for matching single cards
			path = Paths.get("SingleImgTemplate\\" + ID[y] + ".jpeg");
			if (Files.exists(path)) {
				
				test = ImgDiffPercent("TempCards\\" + x + ".jpeg", "SingleImgTemplate\\" + ID[y] + ".jpeg");
				if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
					found = true;
					CardNumb[CurrentSlot] = y;
				    CardCount[CurrentSlot]++;
				    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
				    if (CurrentSlot>20){
				    	CurrentSlot--;	
				    }else{
				    	CurrentSlot++;
				    }
				    TotCards++;
												
				}						
			}
			if (found==true){
			break;
			}
		}
		
		
		
		//Neutral search
		for (int y = 306; y < 535; y++){
				//Search for matching double cards
				// Create a compare object specifying the 2 images for comparison.
				Path path = Paths.get("DoubleImgTemplate\\" + ID[y] + ".jpeg");
				if (Files.exists(path)) {
					test = ImgDiffPercent("TempCards\\" + x + ".jpeg", "DoubleImgTemplate\\" + ID[y] + ".jpeg");
					if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
						found = true;
						CardNumb[CurrentSlot] = y;
					    CardCount[CurrentSlot] = 2;
					    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
					    if (CurrentSlot>20){
					    	CurrentSlot--;	
					    }else{
					    	CurrentSlot++;
					    }
					    TotCards = TotCards +2;
					    //Match found!
						
																		
					}
				}
				//Search for matching single cards
				path = Paths.get("SingleImgTemplate\\" + ID[y] + ".jpeg");
				if (Files.exists(path)) {
					
					test = ImgDiffPercent("TempCards\\" + x + ".jpeg", "SingleImgTemplate\\" + ID[y] + ".jpeg");
					if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
						found = true;
						CardNumb[CurrentSlot] = y;
					    CardCount[CurrentSlot]++;
					    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
					    if (CurrentSlot>20){
					    	CurrentSlot--;	
					    }else{
					    	CurrentSlot++;
					    }
					    TotCards++;
													
					}						
				}
				if (found==true){
				break;
				}
			}
			
			
			
		
		// This only runs is Normal test fails###
		if(found==false){
			for (int y = 306; y < 535; y++){
				
			//One pixel up, double.
			Path path = Paths.get("DoubleImgTemplate\\" + ID[y] + ".jpeg");
			if (Files.exists(path)) {
				
				
				test = ImgDiffPercentUp("TempCards\\" + x + ".jpeg", "DoubleImgTemplate\\" + ID[y] + ".jpeg");
				if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
					found = true;
					CardNumb[CurrentSlot] = y;
				    CardCount[CurrentSlot] = 2;
				    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
				    if (CurrentSlot>20){
				    	CurrentSlot--;	
				    }else{
				    	CurrentSlot++;
				    }
				    TotCards = TotCards +2;
				    //Match found!
																
				}
			}
			//One pixel up
				//Search for matching single cards
			path = Paths.get("SingleImgTemplate\\" + ID[y] + ".jpeg");
			if (Files.exists(path)) {
				
				test = ImgDiffPercentUp("TempCards\\" + x + ".jpeg", "SingleImgTemplate\\" + ID[y] + ".jpeg");
				if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
					found = true;
					CardNumb[CurrentSlot] = y;
				    CardCount[CurrentSlot]++;
				    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
				    if (CurrentSlot>20){
				    	CurrentSlot--;	
				    }else{
				    	CurrentSlot++;
				    }
				    TotCards++;
											
				}						
			}
			//One pixel down, double
			path = Paths.get("DoubleImgTemplate\\" + ID[y] + ".jpeg");
			if (Files.exists(path)) {
					
					
					test = ImgDiffPercentDown("TempCards\\" + x + ".jpeg", "DoubleImgTemplate\\" + ID[y] + ".jpeg");
					if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
						found = true;
						CardNumb[CurrentSlot] = y;
					    CardCount[CurrentSlot] = 2;
					    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
					    if (CurrentSlot>20){
					    	CurrentSlot--;	
					    }else{
					    	CurrentSlot++;
					    }
					    TotCards = TotCards +2;
					    //Match found!															
					}
				}
			//One pixel down
					//Search for matching single cards
				path = Paths.get("SingleImgTemplate\\" + ID[y] + ".jpeg");
				if (Files.exists(path)) {
					
					test = ImgDiffPercentDown("TempCards\\" + x + ".jpeg", "SingleImgTemplate\\" + ID[y] + ".jpeg");
					if ((test<percentdiffallowed)||((x==20)&&(test<(percentdiffallowed+extradifftwenty)))){
						found = true;
						CardNumb[CurrentSlot] = y;
					    CardCount[CurrentSlot]++;
					    //editorPane.setText(editorPane.getText() + CardCount[CurrentSlot] +"x: " + Name[CardNumb[CurrentSlot]] + "\n ");
					    if (CurrentSlot>20){
					    	CurrentSlot--;	
					    }else{
					    	CurrentSlot++;
					    }
					    TotCards++;
													
					}						
				}
			if (found==true){
				break;
			}
			} //End of extra test loop
		} //End of second if-statement
			
		
		
		} //End of ImgMatch
			
	
	//Export to hearthPwn
	public static void HearthPwn (){
		String URL = "http://www.hearthpwn.com/deckbuilder/";
				
		
		URL = URL + ChosenClassStr + "#";
		
		for (int m = 0; m < 30; m++){
			if (CardNumb[m]>-1){
				URL = URL + HearthPwnID[CardNumb[m]] + ":" + CardCount[m] + ";";
			}
		}
		openWebpage(URL);
	}
	
	//Create card by card .txt
	public static String CreateTxt(){
		String str = "";
		for (int m = 0; m < 30; m++){
			if (CardNumb[m]>-1){
				if (CardCount[m]==2){
					str = str + Name[CardNumb[m]] + "\r\n" + Name[CardNumb[m]] + "\r\n";
				}else if (CardCount[m]==1){
					str = str + Name[CardNumb[m]] + "\r\n";
				}else{
				}
			}
		}
		return str;
	}
	
	//Create normal .txt
	public static String CreateTxtNum(){
		String str = "";
		for (int m = 0; m < 30; m++){
			if (CardNumb[m]>-1){
				if (CardCount[m]==2){
					str = str + "2x: " + Name[CardNumb[m]] + "\r\n";
				}else if (CardCount[m]==1){
					str = str + "1x: " + Name[CardNumb[m]] + "\r\n";
				}else{
				}
			}
		}
		return str;
	}
	
	//Create XML file
	public static String CreateXML(String name){
		String str = "";
		str = str + "<Deck>" + "\r\n";
		str = str + " <Cards>" + "\r\n";
		for (int m = 0; m < 30; m++){
			if (CardNumb[m]>-1){
			str = str + "  <Card>" + "\r\n";
			str = str + "   <Id>" + ID[CardNumb[m]] + "</Id>" + "\r\n";
			str = str + "   <Count>" + CardCount[m] + "</Count>" + "\r\n";
			str = str + "  </Card>" + "\r\n";
			}
		}
		str = str + " </Cards>" + "\r\n";
		str = str + " <Class>" + capitalizeFirstLetter(ChosenClassStr)+ "</Class>" + "\r\n";
		str = str + " <Name>" + name + "</Name>" + "\r\n";
		str = str + " <Note />" + "\r\n";
		str = str + " <Tags />" + "\r\n";
		str = str + " <Url />" + "\r\n";
		str = str + "</Deck>" + "\r\n";
		
		return str;
	}
	
	//Write .txt/XML files to disk
	public static void CreateFile(String str, String end){
		JFrame parentFrame = new JFrame();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		int userSelection = fileChooser.showSaveDialog(parentFrame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
		    File f = fileChooser.getSelectedFile();
		    String filePath = f.getPath();
		    if(!filePath.toLowerCase().endsWith(end))
		    {
		        f = new File(filePath + end);
		    }
		    try (PrintStream out = new PrintStream(new FileOutputStream(f))) {
		        out.print(str);
		    } catch (FileNotFoundException ee) {
				ee.printStackTrace();
			}
		}
	}
	
	//Update GUI after every new card found
	public static void UpdateWindow(){
		editorPane.setText(" ");
		for (int m = 0; m < 30; m++){
			if(CardCount[m]>0){
				editorPane.setText(editorPane.getText() + CardCount[m] +"x: " + Name[CardNumb[m]] + "\n ");
			}				
		}
	}
	
	//Remove spaces in array keeping track of cards when search is done, also put one gold and one normal cards togeather as one.
	public static void RemoveSpace(){
		for (int m = 0; m < 29; m++){
			if(CardNumb[m]==CardNumb[m+1]){
				CardCount[m]=2;
				CardCount[m+1]=0;
				CardNumb[m+1]=-1;
			}				
		}
		for (int m = 0; m < 29; m++){
			if(CardCount[m]==-1){
				CardCount[m]=CardCount[m+1];
				CardNumb[m]=CardNumb[m+1];
			}				
		}

	}
	
	//Open URL
	public static void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//Start new search
	public static void StartSearch(){
		//Button pressed
			editorPane.setText(" ");
			for(int x = 0; x < 30; x++){ 
				CardNumb[x] = -1;
				CardCount[x] = 0;
			}
		   	CurrentSlot=0;
		   	TotCards=0;
			editorPane.setText(" ");
			i=0;
			GetScreen();
			timer.start();
			
	}
	
	//Img compare function
	//Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
	public static double ImgDiffPercent(String IMG1, String IMG2)
	{
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
	        int g1 = (rgb1 >>  8) & 0xff;
	        int b1 = (rgb1      ) & 0xff;
	        int r2 = (rgb2 >> 16) & 0xff;
	        int g2 = (rgb2 >>  8) & 0xff;
	        int b2 = (rgb2      ) & 0xff;
	        diff += Math.abs(r1 - r2);
	        diff += Math.abs(g1 - g2);
	        diff += Math.abs(b1 - b2);
	      }
	    }
	    double n = width1 * height1 * 3;
	    double p = diff / n / 255.0;
	    return (double) (p*100.0);
	    
	}
	
	//Img compare function one pixel up
	//Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
	public static double ImgDiffPercentUp(String IMG1, String IMG2)
	{
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
	        int g1 = (rgb1 >>  8) & 0xff;
	        int b1 = (rgb1      ) & 0xff;
	        int r2 = (rgb2 >> 16) & 0xff;
	        int g2 = (rgb2 >>  8) & 0xff;
	        int b2 = (rgb2      ) & 0xff;
	        diff += Math.abs(r1 - r2);
	        diff += Math.abs(g1 - g2);
	        diff += Math.abs(b1 - b2);
	      }
	    }
	    double n = width1 * height1 * 3;
	    double p = diff / n / 255.0;
	    return (double) (p*100.0);
	    
	}

	//Img compare function one pixel down
	//Following function almost 100% taken from: http://rosettacode.org/wiki/Percentage_difference_between_images
	public static double ImgDiffPercentDown(String IMG1, String IMG2)
	{
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
	        int g1 = (rgb1 >>  8) & 0xff;
	        int b1 = (rgb1      ) & 0xff;
	        int r2 = (rgb2 >> 16) & 0xff;
	        int g2 = (rgb2 >>  8) & 0xff;
	        int b2 = (rgb2      ) & 0xff;
	        diff += Math.abs(r1 - r2);
	        diff += Math.abs(g1 - g2);
	        diff += Math.abs(b1 - b2);
	      }
	    }
	    double n = width1 * height1 * 3;
	    double p = diff / n / 255.0;
	    return (double) (p*100.0);
	    
	}
	
	
	//Capitalize first letter
	public static String capitalizeFirstLetter(String original){
	    if(original.length() == 0)
	        return original;
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
	
	//Populate arrays, nothing of interest below this point, seriously.
	public void populateStringArray()
	{
		HearthPwnID[8] = "1";
	    HearthPwnID[125] = "3";
	    HearthPwnID[232] = "4";
	    HearthPwnID[182] = "7";
	    HearthPwnID[265] = "8";
	    HearthPwnID[84] = "10";
	    HearthPwnID[128] = "14";
	    HearthPwnID[333] = "15";
	    HearthPwnID[390] = "18";
	    HearthPwnID[419] = "19";
	    HearthPwnID[243] = "22";
	    HearthPwnID[196] = "23";
	    HearthPwnID[431] = "24";
	    HearthPwnID[451] = "25";
	    HearthPwnID[214] = "26";
	    HearthPwnID[310] = "27";
	    HearthPwnID[217] = "28";
	    HearthPwnID[174] = "29";
	    HearthPwnID[211] = "30";
	    HearthPwnID[312] = "31";
	    HearthPwnID[442] = "33";
	    HearthPwnID[296] = "34";
	    HearthPwnID[51] = "36";
	    HearthPwnID[145] = "38";
	    HearthPwnID[393] = "39";
	    HearthPwnID[324] = "41";
	    HearthPwnID[436] = "42";
	    HearthPwnID[37] = "43";
	    HearthPwnID[208] = "44";
	    HearthPwnID[336] = "47";
	    HearthPwnID[209] = "49";
	    HearthPwnID[17] = "50";
	    HearthPwnID[96] = "51";
	    HearthPwnID[422] = "54";
	    HearthPwnID[329] = "55";
	    HearthPwnID[204] = "56";
	    HearthPwnID[358] = "57";
	    HearthPwnID[311] = "60";
	    HearthPwnID[153] = "62";
	    HearthPwnID[409] = "67";
	    HearthPwnID[27] = "69";
	    HearthPwnID[364] = "73";
	    HearthPwnID[306] = "74";
	    HearthPwnID[365] = "75";
	    HearthPwnID[342] = "76";
	    HearthPwnID[79] = "77";
	    HearthPwnID[407] = "80";
	    HearthPwnID[6] = "81";
	    HearthPwnID[103] = "82";
	    HearthPwnID[315] = "84";
	    HearthPwnID[57] = "85";
	    HearthPwnID[262] = "86";
	    HearthPwnID[107] = "87";
	    HearthPwnID[371] = "88";
	    HearthPwnID[112] = "90";
	    HearthPwnID[116] = "92";
	    HearthPwnID[415] = "94";
	    HearthPwnID[425] = "95";
	    HearthPwnID[2] = "96";
	    HearthPwnID[359] = "97";
	    HearthPwnID[252] = "99";
	    HearthPwnID[183] = "100";
	    HearthPwnID[261] = "101";
	    HearthPwnID[435] = "103";
	    HearthPwnID[80] = "107";
	    HearthPwnID[179] = "108";
	    HearthPwnID[454] = "112";
	    HearthPwnID[249] = "114";
	    HearthPwnID[161] = "117";
	    HearthPwnID[61] = "119";
	    HearthPwnID[285] = "120";
	    HearthPwnID[36] = "122";
	    HearthPwnID[456] = "123";
	    HearthPwnID[94] = "124";
	    HearthPwnID[229] = "125";
	    HearthPwnID[127] = "127";
	    HearthPwnID[375] = "128";
	    HearthPwnID[93] = "129";
	    HearthPwnID[22] = "130";
	    HearthPwnID[389] = "131";
	    HearthPwnID[119] = "135";
	    HearthPwnID[423] = "138";
	    HearthPwnID[374] = "140";
	    HearthPwnID[181] = "142";
	    HearthPwnID[77] = "146";
	    HearthPwnID[159] = "147";
	    HearthPwnID[287] = "148";
	    HearthPwnID[283] = "149";
	    HearthPwnID[91] = "151";
	    HearthPwnID[69] = "152";
	    HearthPwnID[357] = "153";
	    HearthPwnID[284] = "154";
	    HearthPwnID[424] = "157";
	    HearthPwnID[189] = "158";
	    HearthPwnID[222] = "160";
	    HearthPwnID[10] = "161";
	    HearthPwnID[263] = "162";
	    HearthPwnID[246] = "163";
	    HearthPwnID[110] = "164";
	    HearthPwnID[286] = "165";
	    HearthPwnID[13] = "166";
	    HearthPwnID[242] = "167";
	    HearthPwnID[43] = "170";
	    HearthPwnID[68] = "172";
	    HearthPwnID[313] = "173";
	    HearthPwnID[348] = "174";
	    HearthPwnID[356] = "176";
	    HearthPwnID[210] = "177";
	    HearthPwnID[396] = "178";
	    HearthPwnID[444] = "179";
	    HearthPwnID[0] = "182";
	    HearthPwnID[331] = "184";
	    HearthPwnID[218] = "188";
	    HearthPwnID[180] = "189";
	    HearthPwnID[197] = "191";
	    HearthPwnID[160] = "192";
	    HearthPwnID[23] = "193";
	    HearthPwnID[264] = "194";
	    HearthPwnID[54] = "196";
	    HearthPwnID[410] = "197";
	    HearthPwnID[114] = "198";
	    HearthPwnID[395] = "203";
	    HearthPwnID[111] = "205";
	    HearthPwnID[186] = "206";
	    HearthPwnID[147] = "207";
	    HearthPwnID[52] = "208";
	    HearthPwnID[397] = "209";
	    HearthPwnID[254] = "210";
	    HearthPwnID[378] = "212";
	    HearthPwnID[385] = "213";
	    HearthPwnID[81] = "214";
	    HearthPwnID[18] = "215";
	    HearthPwnID[71] = "216";
	    HearthPwnID[534] = "217";
	    HearthPwnID[228] = "220";
	    HearthPwnID[441] = "221";
	    HearthPwnID[418] = "222";
	    HearthPwnID[260] = "225";
	    HearthPwnID[7] = "227";
	    HearthPwnID[162] = "228";
	    HearthPwnID[163] = "232";
	    HearthPwnID[73] = "233";
	    HearthPwnID[56] = "236";
	    HearthPwnID[282] = "237";
	    HearthPwnID[295] = "238";
	    HearthPwnID[248] = "239";
	    HearthPwnID[408] = "241";
	    HearthPwnID[297] = "242";
	    HearthPwnID[115] = "244";
	    HearthPwnID[447] = "245";
	    HearthPwnID[320] = "246";
	    HearthPwnID[457] = "247";
	    HearthPwnID[148] = "249";
	    HearthPwnID[170] = "250";
	    HearthPwnID[532] = "251";
	    HearthPwnID[34] = "252";
	    HearthPwnID[72] = "256";
	    HearthPwnID[273] = "258";
	    HearthPwnID[176] = "260";
	    HearthPwnID[381] = "261";
	    HearthPwnID[231] = "263";
	    HearthPwnID[416] = "264";
	    HearthPwnID[446] = "265";
	    HearthPwnID[281] = "266";
	    HearthPwnID[370] = "267";
	    HearthPwnID[74] = "270";
	    HearthPwnID[453] = "273";
	    HearthPwnID[227] = "274";
	    HearthPwnID[213] = "276";
	    HearthPwnID[239] = "278";
	    HearthPwnID[266] = "279";
	    HearthPwnID[362] = "280";
	    HearthPwnID[280] = "282";
	    HearthPwnID[195] = "283";
	    HearthPwnID[117] = "284";
	    HearthPwnID[420] = "285";
	    HearthPwnID[129] = "286";
	    HearthPwnID[309] = "289";
	    HearthPwnID[171] = "293";
	    HearthPwnID[12] = "297";
	    HearthPwnID[149] = "301";
	    HearthPwnID[353] = "303";
	    HearthPwnID[247] = "304";
	    HearthPwnID[379] = "305";
	    HearthPwnID[221] = "309";
	    HearthPwnID[344] = "310";
	    HearthPwnID[288] = "311";
	    HearthPwnID[144] = "315";
	    HearthPwnID[256] = "317";
	    HearthPwnID[347] = "323";
	    HearthPwnID[434] = "324";
	    HearthPwnID[343] = "325";
	    HearthPwnID[339] = "326";
	    HearthPwnID[44] = "327";
	    HearthPwnID[26] = "328";
	    HearthPwnID[277] = "329";
	    HearthPwnID[35] = "332";
	    HearthPwnID[250] = "334";
	    HearthPwnID[92] = "335";
	    HearthPwnID[439] = "338";
	    HearthPwnID[412] = "339";
	    HearthPwnID[53] = "340";
	    HearthPwnID[250] = "344";
	    HearthPwnID[15] = "345";
	    HearthPwnID[414] = "346";
	    HearthPwnID[38] = "348";
	    HearthPwnID[177] = "350";
	    HearthPwnID[187] = "355";
	    HearthPwnID[317] = "356";
	    HearthPwnID[330] = "357";
	    HearthPwnID[448] = "360";
	    HearthPwnID[328] = "362";
	    HearthPwnID[238] = "363";
	    HearthPwnID[120] = "364";
	    HearthPwnID[14] = "366";
	    HearthPwnID[76] = "367";
	    HearthPwnID[413] = "368";
	    HearthPwnID[440] = "372";
	    HearthPwnID[400] = "373";
	    HearthPwnID[108] = "378";
	    HearthPwnID[266] = "379";
	    HearthPwnID[118] = "382";
	    HearthPwnID[185] = "383";
	    HearthPwnID[109] = "385";
	    HearthPwnID[392] = "386";
	    HearthPwnID[314] = "388";
	    HearthPwnID[438] = "389";
	    HearthPwnID[90] = "390";
	    HearthPwnID[198] = "391";
	    HearthPwnID[399] = "392";
	    HearthPwnID[175] = "394";
	    HearthPwnID[405] = "395";
	    HearthPwnID[445] = "396";
	    HearthPwnID[47] = "398";
	    HearthPwnID[140] = "401";
	    HearthPwnID[59] = "402";
	    HearthPwnID[245] = "407";
	    HearthPwnID[138] = "409";
	    HearthPwnID[346] = "410";
	    HearthPwnID[230] = "411";
	    HearthPwnID[327] = "414";
	    HearthPwnID[139] = "415";
	    HearthPwnID[124] = "417";
	    HearthPwnID[417] = "420";
	    HearthPwnID[151] = "421";
	    HearthPwnID[401] = "422";
	    HearthPwnID[372] = "424";
	    HearthPwnID[352] = "425";
	    HearthPwnID[351] = "428";
	    HearthPwnID[142] = "431";
	    HearthPwnID[421] = "432";
	    HearthPwnID[102] = "433";
	    HearthPwnID[340] = "434";
	    HearthPwnID[332] = "435";
	    HearthPwnID[404] = "436";
	    HearthPwnID[141] = "438";
	    HearthPwnID[150] = "442";
	    HearthPwnID[253] = "447";
	    HearthPwnID[384] = "450";
	    HearthPwnID[42] = "452";
	    HearthPwnID[367] = "453";
	    HearthPwnID[16] = "454";
	    HearthPwnID[406] = "456";
	    HearthPwnID[146] = "457";
	    HearthPwnID[300] = "459";
	    HearthPwnID[360] = "463";
	    HearthPwnID[289] = "464";
	    HearthPwnID[380] = "467";
	    HearthPwnID[106] = "471";
	    HearthPwnID[316] = "472";
	    HearthPwnID[361] = "473";
	    HearthPwnID[376] = "474";
	    HearthPwnID[428] = "475";
	    HearthPwnID[386] = "476";
	    HearthPwnID[443] = "477";
	    HearthPwnID[326] = "479";
	    HearthPwnID[275] = "480";
	    HearthPwnID[58] = "482";
	    HearthPwnID[430] = "483";
	    HearthPwnID[244] = "488";
	    HearthPwnID[205] = "489";
	    HearthPwnID[75] = "491";
	    HearthPwnID[9] = "493";
	    HearthPwnID[458] = "495";
	    HearthPwnID[220] = "496";
	    HearthPwnID[369] = "498";
	    HearthPwnID[178] = "499";
	    HearthPwnID[398] = "500";
	    HearthPwnID[335] = "502";
	    HearthPwnID[426] = "503";
	    HearthPwnID[24] = "504";
	    HearthPwnID[188] = "506";
	    HearthPwnID[55] = "507";
	    HearthPwnID[449] = "509";
	    HearthPwnID[322] = "510";
	    HearthPwnID[403] = "513";
	    HearthPwnID[427] = "518";
	    HearthPwnID[325] = "519";
	    HearthPwnID[207] = "522";
	    HearthPwnID[450] = "523";
	    HearthPwnID[366] = "525";
	    HearthPwnID[78] = "526";
	    HearthPwnID[40] = "529";
	    HearthPwnID[82] = "530";
	    HearthPwnID[215] = "531";
	    HearthPwnID[272] = "532";
	    HearthPwnID[338] = "535";
	    HearthPwnID[363] = "539";
	    HearthPwnID[377] = "542";
	    HearthPwnID[152] = "544";
	    HearthPwnID[307] = "545";
	    HearthPwnID[143] = "547";
	    HearthPwnID[274] = "548";
	    HearthPwnID[121] = "550";
	    HearthPwnID[255] = "553";
	    HearthPwnID[136] = "554";
	    HearthPwnID[382] = "557";
	    HearthPwnID[533] = "559";
	    HearthPwnID[337] = "560";
	    HearthPwnID[126] = "562";
	    HearthPwnID[321] = "564";
	    HearthPwnID[60] = "566";
	    HearthPwnID[172] = "567";
	    HearthPwnID[105] = "568";
	    HearthPwnID[219] = "569";
	    HearthPwnID[355] = "572";
	    HearthPwnID[46] = "573";
	    HearthPwnID[308] = "576";
	    HearthPwnID[350] = "577";
	    HearthPwnID[241] = "578";
	    HearthPwnID[184] = "581";
	    HearthPwnID[411] = "584";
	    HearthPwnID[299] = "587";
	    HearthPwnID[206] = "589";
	    HearthPwnID[212] = "595";
	    HearthPwnID[349] = "597";
	    HearthPwnID[388] = "598";
	    HearthPwnID[157] = "600";
	    HearthPwnID[391] = "602";
	    HearthPwnID[345] = "603";
	    HearthPwnID[319] = "604";
	    HearthPwnID[298] = "605";
	    HearthPwnID[387] = "610";
	    HearthPwnID[341] = "611";
	    HearthPwnID[373] = "612";
	    HearthPwnID[95] = "613";
	    HearthPwnID[429] = "614";
	    HearthPwnID[276] = "619";
	    HearthPwnID[279] = "620";
	    HearthPwnID[323] = "624";
	    HearthPwnID[383] = "625";
	    HearthPwnID[437] = "627";
	    HearthPwnID[455] = "629";
	    HearthPwnID[251] = "630";
	    HearthPwnID[1] = "632";
	    HearthPwnID[290] = "633";
	    HearthPwnID[433] = "634";
	    HearthPwnID[89] = "636";
	    HearthPwnID[368] = "637";
	    HearthPwnID[19] = "638";
	    HearthPwnID[354] = "641";
	    HearthPwnID[191] = "642";
	    HearthPwnID[28] = "643";
	    HearthPwnID[25] = "644";
	    HearthPwnID[5] = "646";
	    HearthPwnID[39] = "647";
	    HearthPwnID[432] = "648";
	    HearthPwnID[158] = "656";
	    HearthPwnID[190] = "657";
	    HearthPwnID[113] = "658";
	    HearthPwnID[334] = "659";
	    HearthPwnID[394] = "660";
	    HearthPwnID[318] = "663";
	    HearthPwnID[11] = "664";
	    HearthPwnID[278] = "667";
	    HearthPwnID[41] = "670";
	    HearthPwnID[137] = "671";
	    HearthPwnID[216] = "672";
	    HearthPwnID[45] = "673";
	    HearthPwnID[402] = "674";
	    HearthPwnID[452] = "675";
	    HearthPwnID[85] = "676";
	    HearthPwnID[83] = "679";
	    HearthPwnID[531] = "682";
	    HearthPwnID[479] = "683";
	    HearthPwnID[262] = "86";
	    HearthPwnID[473] = "7751";
	    HearthPwnID[464] = "7756";
	    HearthPwnID[462] = "7754";
	    HearthPwnID[467] = "7748";
	    HearthPwnID[471] = "7730";
	    HearthPwnID[470] = "7738";
	    HearthPwnID[473] = "7751";
	    HearthPwnID[461] = "7753";
	    HearthPwnID[468] = "7747";
	    HearthPwnID[472] = "7749";
	    HearthPwnID[465] = "7742";
	    HearthPwnID[474] = "7744";
	    HearthPwnID[463] = "7745";
	    HearthPwnID[478] = "7758";
	    HearthPwnID[469] = "7755";
	    HearthPwnID[291] = "7726";
	    HearthPwnID[477] = "7757";
	    HearthPwnID[475] = "7750";
	    HearthPwnID[476] = "7737";
	    HearthPwnID[460] = "7736";
	    HearthPwnID[466] = "7746";
	    HearthPwnID[459] = "7740";
	    HearthPwnID[267] = "7741";
	    HearthPwnID[3] = "7734";
	    HearthPwnID[291] = "7726";
	    HearthPwnID[223] = "7732";
	    HearthPwnID[192] = "7729";
	    HearthPwnID[164] = "7735";
	    HearthPwnID[130] = "7728";
	    HearthPwnID[86] = "7731";
	    HearthPwnID[62] = "7733";
	    HearthPwnID[301] = "12219";
	    HearthPwnID[303] = "12273";
	    HearthPwnID[302] = "12243";
	    HearthPwnID[292] = "12298";
	    HearthPwnID[293] = "12279";
	    HearthPwnID[305] = "12226";
	    HearthPwnID[304] = "12293";
	    HearthPwnID[294] = "12270";
	    HearthPwnID[527] = "12288";
	    HearthPwnID[487] = "12200";
	    HearthPwnID[488] = "12179";
	    HearthPwnID[480] = "12181";
	    HearthPwnID[491] = "12180";
	    HearthPwnID[497] = "12249";
	    HearthPwnID[511] = "12188";
	    HearthPwnID[513] = "12189";
	    HearthPwnID[520] = "12274";
	    HearthPwnID[521] = "12198";
	    HearthPwnID[523] = "12258";
	    HearthPwnID[526] = "12266";
	    HearthPwnID[493] = "12247";
	    HearthPwnID[498] = "12286";
	    HearthPwnID[499] = "12199";
	    HearthPwnID[500] = "12213";
	    HearthPwnID[502] = "12250";
	    HearthPwnID[503] = "12214";
	    HearthPwnID[507] = "12239";
	    HearthPwnID[517] = "12281";
	    HearthPwnID[525] = "12184";
	    HearthPwnID[528] = "12202";
	    HearthPwnID[482] = "12246";
	    HearthPwnID[485] = "12233";
	    HearthPwnID[490] = "12176";
	    HearthPwnID[504] = "12216";
	    HearthPwnID[506] = "12252";
	    HearthPwnID[508] = "12284";
	    HearthPwnID[510] = "12253";
	    HearthPwnID[515] = "12262";
	    HearthPwnID[518] = "12191";
	    HearthPwnID[481] = "12227";
	    HearthPwnID[483] = "12183";
	    HearthPwnID[484] = "12193";
	    HearthPwnID[492] = "12264";
	    HearthPwnID[501] = "12268";
	    HearthPwnID[505] = "12251";
	    HearthPwnID[509] = "12177";
	    HearthPwnID[514] = "12190";
	    HearthPwnID[522] = "12263";
	    HearthPwnID[496] = "12287";
	    HearthPwnID[516] = "12282";
	    HearthPwnID[519] = "12175";
	    HearthPwnID[529] = "12225";
	    HearthPwnID[489] = "12182";
	    HearthPwnID[530] = "12272";
	    HearthPwnID[494] = "12217";
	    HearthPwnID[495] = "12248";
	    HearthPwnID[524] = "12187";
	    HearthPwnID[512] = "12196";
	    HearthPwnID[486] = "12201";
	    HearthPwnID[527] = "12288";
	    HearthPwnID[240] = "12267";
	    HearthPwnID[257] = "12224";
	    HearthPwnID[259] = "12238";
	    HearthPwnID[271] = "12242";
	    HearthPwnID[270] = "12254";
	    HearthPwnID[258] = "12304";
	    HearthPwnID[269] = "12285";
	    HearthPwnID[268] = "12232";
	    HearthPwnID[225] = "12192";
	    HearthPwnID[226] = "12178";
	    HearthPwnID[235] = "12230";
	    HearthPwnID[236] = "12306";
	    HearthPwnID[224] = "12300";
	    HearthPwnID[234] = "12195";
	    HearthPwnID[237] = "12261";
	    HearthPwnID[233] = "12290";
	    HearthPwnID[194] = "12305";
	    HearthPwnID[203] = "12257";
	    HearthPwnID[173] = "12228";
	    HearthPwnID[193] = "12223";
	    HearthPwnID[202] = "12240";
	    HearthPwnID[199] = "12244";
	    HearthPwnID[200] = "12222";
	    HearthPwnID[201] = "12280";
	    HearthPwnID[154] = "12297";
	    HearthPwnID[165] = "12278";
	    HearthPwnID[166] = "12256";
	    HearthPwnID[167] = "12197";
	    HearthPwnID[156] = "12174";
	    HearthPwnID[168] = "12185";
	    HearthPwnID[169] = "12296";
	    HearthPwnID[155] = "12301";
	    HearthPwnID[131] = "12212";
	    HearthPwnID[134] = "12255";
	    HearthPwnID[104] = "12265";
	    HearthPwnID[132] = "12229";
	    HearthPwnID[122] = "12236";
	    HearthPwnID[123] = "12276";
	    HearthPwnID[133] = "12235";
	    HearthPwnID[135] = "12291";
	    HearthPwnID[88] = "12241";
	    HearthPwnID[100] = "12259";
	    HearthPwnID[101] = "12231";
	    HearthPwnID[70] = "12269";
	    HearthPwnID[71] = "12218";
	    HearthPwnID[97] = "12234";
	    HearthPwnID[99] = "12277";
	    HearthPwnID[98] = "12292";
	    HearthPwnID[48] = "12299";
	    HearthPwnID[67] = "12283";
	    HearthPwnID[50] = "12302";
	    HearthPwnID[64] = "12221";
	    HearthPwnID[49] = "12237";
	    HearthPwnID[65] = "12271";
	    HearthPwnID[63] = "12245";
	    HearthPwnID[66] = "12294";
	    HearthPwnID[33] = "12260";
	    HearthPwnID[4] = "12211";
	    HearthPwnID[20] = "12203";
	    HearthPwnID[30] = "12220";
	    HearthPwnID[32] = "12275";
	    HearthPwnID[29] = "12295";
	    HearthPwnID[31] = "12215";
	    HearthPwnID[21] = "12303";
		
		
		
		ID[0] = "CS2_112";
		ID[1] =	"CS2_106";
		ID[2] =	"EX1_411";
		ID[3] =	"FP1_021";
		ID[4] = "GVG_054";
		ID[5] = "CS2_103";
		ID[6] = "CS2_114";
		ID[7] = "CS2_108";
		ID[8] = "CS2_105";
		ID[9] = "EX1_606";
		ID[10] = "EX1_400";
		ID[11]= "EX1_392";
		ID[12]= "EX1_407";
		ID[13]= "NEW1_036";
		ID[14]="EX1_607";
		ID[15]="EX1_408";
		ID[16]="CS2_104";
		ID[17]="EX1_410";
		ID[18]="EX1_391";
		ID[19]="EX1_409";
		ID[20]="GVG_050";
		ID[21]="GVG_052";
		ID[22]="NEW1_011";
		ID[23]="EX1_084";
		ID[24]="EX1_398";
		ID[25]="EX1_402";
		ID[26]="EX1_603";
		ID[27]="EX1_604";
		ID[28]="EX1_414";
		ID[29]="GVG_056";
		ID[30]="GVG_055";
		ID[31]="GVG_053";
		ID[32]="GVG_086";
		ID[33]="GVG_051";
		ID[34]="CS2_063";
		ID[35]="CS2_061";
		ID[36]="CS2_062";
		ID[37]="EX1_302";
		ID[38]="NEW1_003";
		ID[39]="CS2_057";
		ID[40]="EX1_308";
		ID[41]="EX1_320";
		ID[42]="EX1_596";
		ID[43]="EX1_316";
		ID[44]="EX1_317";
		ID[45]="EX1_303";
		ID[46]="EX1_309";
		ID[47]="EX1_312";
		ID[48]="GVG_015";
		ID[49]="GVG_019";
		ID[50]="GVG_045";
		ID[51]="CS2_064";
		ID[52]="EX1_306";
		ID[53]="CS2_065";
		ID[54]="CS2_059";
		ID[55]="EX1_310";
		ID[56]="EX1_301";
		ID[57]="EX1_319";
		ID[58]="EX1_323";
		ID[59]="EX1_313";
		ID[60]="EX1_315";
		ID[61]="EX1_304";
		ID[62]="FP1_022";
		ID[63]="GVG_077";
		ID[64]="GVG_020";
		ID[65]="GVG_100";
		ID[66]="GVG_021";
		ID[67]="GVG_018";
		ID[68]="EX1_567";
		ID[69]="EX1_247";
		ID[70]="GVG_036";
		ID[71]="CS2_041";
		ID[72]="CS2_046";
		ID[73]="CS2_037";
		ID[74]="EX1_246";
		ID[75]="CS2_045";
		ID[76]="EX1_244";
		ID[77]="CS2_039";
		ID[78]="CS2_038";
		ID[79]="EX1_245";
		ID[80]="CS2_053";
		ID[81]="EX1_248";
		ID[82]="EX1_251";
		ID[83]="EX1_241";
		ID[84]="EX1_238";
		ID[85]="EX1_259";
		ID[86]="FP1_025";
		ID[87]="GVG_029";
		ID[88]="GVG_038";
		ID[89]="CS2_042";
		ID[90]="EX1_565";
		ID[91]="EX1_587";
		ID[92]="NEW1_010";
		ID[93]="EX1_243";
		ID[94]="EX1_250";
		ID[95]="EX1_575";
		ID[96]="EX1_258";
		ID[97]="GVG_066";
		ID[98]="GVG_042";
		ID[99]="GVG_040";
		ID[100]="GVG_039";
		ID[101]="GVG_037";
		ID[102]="CS2_080";
		ID[103]="EX1_133";
		ID[104]="GVG_024";
		ID[105]="CS2_076";
		ID[106]="CS2_072";
		ID[107]="CS2_074";
		ID[108]="EX1_129";
		ID[109]="EX1_581";
		ID[110]="EX1_278";
		ID[111]="CS2_075";
		ID[112]="CS2_077";
		ID[113]="NEW1_004";
		ID[114]="EX1_126";
		ID[115]="CS2_233";
		ID[116]="CS2_073";
		ID[117]="EX1_128";
		ID[118]="EX1_124";
		ID[119]="EX1_137";
		ID[120]="EX1_145";
		ID[121]="EX1_144";
		ID[122]="GVG_047";
		ID[123]="GVG_022";
		ID[124]="EX1_131";
		ID[125]="EX1_613";
		ID[126]="NEW1_005";
		ID[127]="NEW1_014";
		ID[128]="EX1_522";
		ID[129]="EX1_134";
		ID[130]="FP1_026";
		ID[131]="GVG_023";
		ID[132]="GVG_027";
		ID[133]="GVG_088";
		ID[134]="GVG_025";
		ID[135]="GVG_028";
		ID[136]="CS2_236";
		ID[137]="CS1_112";
		ID[138]="CS1_130";
		ID[139]="DS1_233";
		ID[140]="CS1_113";
		ID[141]="CS2_003";
		ID[142]="CS2_004";
		ID[143]="EX1_622";
		ID[144]="CS2_234";
		ID[145]="EX1_621";
		ID[146]="EX1_624";
		ID[147]="CS1_129";
		ID[148]="EX1_626";
		ID[149]="EX1_345";
		ID[150]="EX1_334";
		ID[151]="EX1_625";
		ID[152]="EX1_332";
		ID[153]="EX1_339";
		ID[154]="GVG_012";
		ID[155]="GVG_008";
		ID[156]="GVG_010";
		ID[157]="CS2_235";
		ID[158]="EX1_591";
		ID[159]="EX1_091";
		ID[160]="EX1_335";
		ID[161]="EX1_341";
		ID[162]="EX1_350";
		ID[163]="EX1_623";
		ID[164]="FP1_023";
		ID[165]="GVG_009";
		ID[166]="GVG_072";
		ID[167]="GVG_011";
		ID[168]="GVG_083";
		ID[169]="GVG_014";
		ID[170]="CS2_091";
		ID[171]="CS2_097";
		ID[172]="EX1_366";
		ID[173]="GVG_059";
		ID[174]="CS2_092";
		ID[175]="CS2_087";
		ID[176]="CS2_093";
		ID[177]="CS2_094";
		ID[178]="EX1_371";
		ID[179]="CS2_089";
		ID[180]="EX1_360";
		ID[181]="EX1_384";
		ID[182]="EX1_355";
		ID[183]="EX1_363";
		ID[184]="EX1_349";
		ID[185]="EX1_619";
		ID[186]="EX1_132";
		ID[187]="EX1_365";
		ID[188]="EX1_354";
		ID[189]="EX1_130";
		ID[190]="EX1_136";
		ID[191]="EX1_379";
		ID[192]="FP1_020";
		ID[193]="GVG_061";
		ID[194]="GVG_057";
		ID[195]="CS2_088";
		ID[196]="EX1_382";
		ID[197]="EX1_362";
		ID[198]="EX1_383";
		ID[199]="GVG_063";
		ID[200]="GVG_062";
		ID[201]="GVG_060";
		ID[202]="GVG_101";
		ID[203]="GVG_058";
		ID[204]="CS2_025";
		ID[205]="CS2_023";
		ID[206]="EX1_277";
		ID[207]="CS2_029";
		ID[208]="CS2_032";
		ID[209]="CS2_026";
		ID[210]="CS2_024";
		ID[211]="CS2_027";
		ID[212]="CS2_022";
		ID[213]="CS2_028";
		ID[214]="EX1_275";
		ID[215]="EX1_287";
		ID[216]="EX1_289";
		ID[217]="EX1_295";
		ID[218]="CS2_031";
		ID[219]="EX1_294";
		ID[220]="EX1_279";
		ID[221]="tt_010";
		ID[222]="EX1_594";
		ID[223]="FP1_018";
		ID[224]="GVG_005";
		ID[225]="GVG_001";
		ID[226]="GVG_003";
		ID[227]="CS2_033";
		ID[228]="EX1_559";
		ID[229]="EX1_274";
		ID[230]="EX1_612";
		ID[231]="NEW1_012";
		ID[232]="EX1_608";
		ID[233]="GVG_007";
		ID[234]="GVG_004";
		ID[235]="GVG_002";
		ID[236]="GVG_123";
		ID[237]="GVG_122";
		ID[238]="EX1_536";
		ID[239]="DS1_188";
		ID[240]="GVG_043";
		ID[241]="NEW1_031";
		ID[242]="DS1_185";
		ID[243]="CS2_084";
		ID[244]="EX1_539";
		ID[245]="DS1_183";
		ID[246]="DS1_184";
		ID[247]="EX1_549";
		ID[248]="EX1_617";
		ID[249]="EX1_537";
		ID[250]="EX1_610";
		ID[251]="EX1_544";
		ID[252]="EX1_611";
		ID[253]="EX1_533";
		ID[254]="EX1_554";
		ID[255]="EX1_609";
		ID[256]="EX1_538";
		ID[257]="GVG_017";
		ID[258]="GVG_073";
		ID[259]="GVG_026";
		ID[260]="DS1_070";
		ID[261]="CS2_237";
		ID[262]="DS1_175";
		ID[263]="DS1_178";
		ID[264]="EX1_543";
		ID[265]="EX1_534";
		ID[266]="EX1_531";
		ID[267]="FP1_011";
		ID[268]="GVG_049";
		ID[269]="GVG_046";
		ID[270]="GVG_048";
		ID[271]="GVG_087";
		ID[272]="CS2_005";
		ID[273]="CS2_007";
		ID[274]="EX1_169";
		ID[275]="CS2_009";
		ID[276]="CS2_008";
		ID[277]="CS2_011";
		ID[278]="EX1_173";
		ID[279]="CS2_012";
		ID[280]="CS2_013";
		ID[281]="EX1_570";
		ID[282]="EX1_571";
		ID[283]="EX1_155";
		ID[284]="EX1_161";
		ID[285]="EX1_164";
		ID[286]="EX1_160";
		ID[287]="EX1_578";
		ID[288]="EX1_158";
		ID[289]="NEW1_007";
		ID[290]="EX1_154";
		ID[291]="FP1_019";
		ID[292]="GVG_041";
		ID[293]="GVG_031";
		ID[294]="GVG_033";
		ID[295]="CS2_232";
		ID[296]="NEW1_008";
		ID[297]="EX1_178";
		ID[298]="EX1_573";
		ID[299]="EX1_165";
		ID[300]="EX1_166";
		ID[301]="GVG_030";
		ID[302]="GVG_080";
		ID[303]="GVG_032";
		ID[304]="GVG_035";
		ID[305]="GVG_034";
		ID[306]="EX1_066";
		ID[307]="CS2_155";
		ID[308]="CS2_172";
		ID[309]="CS2_173";
		ID[310]="CS2_187";
		ID[311]="CS2_200";
		ID[312]="CS2_182";
		ID[313]="CS2_201";
		ID[314]="EX1_582";
		ID[315]="DS1_055";
		ID[316]="EX1_025";
		ID[317]="CS2_189";
		ID[318]="CS2_121";
		ID[319]="CS2_226";
		ID[320]="CS2_147";
		ID[321]="CS1_042";
		ID[322]="EX1_508";
		ID[323]="EX1_399";
		ID[324]="CS2_141";
		ID[325]="CS2_125";
		ID[326]="CS2_142";
		ID[327]="CS2_162";
		ID[328]="CS2_118";
		ID[329]="CS2_168";
		ID[330]="EX1_506";
		ID[331]="EX1_593";
		ID[332]="EX1_015";
		ID[333]="CS2_119";
		ID[334]="CS2_197";
		ID[335]="CS2_122";
		ID[336]="CS2_196";
		ID[337]="CS2_213";
		ID[338]="CS2_120";
		ID[339]="CS2_179";
		ID[340]="EX1_019";
		ID[341]="CS2_127";
		ID[342]="CS2_171";
		ID[343]="CS2_150";
		ID[344]="CS2_222";
		ID[345]="CS2_131";
		ID[346]="EX1_011";
		ID[347]="CS2_186";
		ID[348]="CS2_124";
		ID[349]="EX1_097";
		ID[350]="CS2_188";
		ID[351]="EX1_007";
		ID[352]="EX1_006";
		ID[353]="EX1_561";
		ID[354]="EX1_393";
		ID[355]="EX1_057";
		ID[356]="EX1_584";
		ID[357]="EX1_045";
		ID[358]="EX1_009";
		ID[359]="EX1_089";
		ID[360]="EX1_067";
		ID[361]="EX1_008";
		ID[362]="EX1_284";
		ID[363]="EX1_249";
		ID[364]="EX1_005";
		ID[365]="EX1_590";
		ID[366]="EX1_012";
		ID[367]="NEW1_025";
		ID[368]="NEW1_018";
		ID[369]="EX1_110";
		ID[370]="NEW1_024";
		ID[371]="EX1_050";
		ID[372]="EX1_103";
		ID[373]="EX1_059";
		ID[374]="EX1_595";
		ID[375]="EX1_046";
		ID[376]="NEW1_030";
		ID[377]="EX1_093";
		ID[378]="EX1_102";
		ID[379]="EX1_162";
		ID[380]="NEW1_021";
		ID[381]="NEW1_022";
		ID[382]="CS2_117";
		ID[383]="EX1_170";
		ID[384]="EX1_564";
		ID[385]="NEW1_023";
		ID[386]="CS1_069";
		ID[387]="tt_004";
		ID[388]="EX1_283";
		ID[389]="EX1_095";
		ID[390]="NEW1_038";
		ID[391]="EX1_558";
		ID[392]="EX1_556";
		ID[393]="NEW1_040";
		ID[394]="NEW1_017";
		ID[395]="EX1_614";
		ID[396]="EX1_597";
		ID[397]="CS2_181";
		ID[398]="CS2_203";
		ID[399]="EX1_017";
		ID[400]="EX1_014";
		ID[401]="NEW1_019";
		ID[402]="EX1_116";
		ID[403]="EX1_029";
		ID[404]="EX1_001";
		ID[405]="EX1_096";
		ID[406]="EX1_100";
		ID[407]="EX1_082";
		ID[408]="EX1_563";
		ID[409]="EX1_055";
		ID[410]="EX1_616";
		ID[411]="NEW1_037";
		ID[412]="NEW1_029";
		ID[413]="EX1_085";
		ID[414]="EX1_396";
		ID[415]="EX1_620";
		ID[416]="EX1_105";
		ID[417]="EX1_509";
		ID[418]="EX1_507";
		ID[419]="EX1_557";
		ID[420]="EX1_560";
		ID[421]="EX1_562";
		ID[422]="EX1_076";
		ID[423]="EX1_583";
		ID[424]="EX1_044";
		ID[425]="EX1_412";
		ID[426]="EX1_298";
		ID[427]="CS2_161";
		ID[428]="EX1_020";
		ID[429]="EX1_586";
		ID[430]="EX1_080";
		ID[431]="EX1_405";
		ID[432]="CS2_151";
		ID[433]="EX1_023";
		ID[434]="NEW1_027";
		ID[435]="CS2_146";
		ID[436]="EX1_048";
		ID[437]="CS2_221";
		ID[438]="NEW1_041";
		ID[439]="EX1_028";
		ID[440]="EX1_058";
		ID[441]="EX1_032";
		ID[442]="EX1_016";
		ID[443]="EX1_390";
		ID[444]="EX1_577";
		ID[445]="EX1_002";
		ID[446]="EX1_021";
		ID[447]="EX1_083";
		ID[448]="EX1_043";
		ID[449]="CS2_227";
		ID[450]="NEW1_026";
		ID[451]="NEW1_020";
		ID[452]="EX1_033";
		ID[453]="CS2_231";
		ID[454]="EX1_010";
		ID[455]="CS2_169";
		ID[456]="EX1_004";
		ID[457]="EX1_049";
		ID[458]="EX1_572";
		ID[459]="FP1_031";
		ID[460]="FP1_029";
		ID[461]="FP1_009";
		ID[462]="FP1_003";
		ID[463]="FP1_015";
		ID[464]="FP1_002";
		ID[465]="FP1_013";
		ID[466]="FP1_030";
		ID[467]="FP1_004";
		ID[468]="FP1_010";
		ID[469]="FP1_017";
		ID[470]="FP1_007";
		ID[471]="FP1_005";
		ID[472]="FP1_012";
		ID[473]="FP1_008";
		ID[474]="FP1_014";
		ID[475]="FP1_027";
		ID[476]="FP1_028";
		ID[477]="FP1_024";
		ID[478]="FP1_016";
		ID[479]="FP1_001";
		ID[480]="GVG_085";
		ID[481]="GVG_069";
		ID[482]="GVG_091";
		ID[483]="GVG_119";
		ID[484]="GVG_099";
		ID[485]="GVG_068";
		ID[486]="GVG_121";
		ID[487]="GVG_082";
		ID[488]="GVG_013";
		ID[489]="GVG_110";
		ID[490]="GVG_107";
		ID[491]="GVG_076";
		ID[492]="GVG_016";
		ID[493]="GVG_084";
		ID[494]="GVG_113";
		ID[495]="GVG_079";
		ID[496]="GVG_117";
		ID[497]="GVG_081";
		ID[498]="GVG_098";
		ID[499]="GVG_092";
		ID[500]="GVG_095";
		ID[501]="GVG_120";
		ID[502]="GVG_104";
		ID[503]="GVG_089";
		ID[504]="GVG_094";
		ID[505]="GVG_106";
		ID[506]="GVG_074";
		ID[507]="GVG_097";
		ID[508]="GVG_071";
		ID[509]="GVG_090";
		ID[510]="GVG_078";
		ID[511]="GVG_006";
		ID[512]="GVG_116";
		ID[513]="GVG_103";
		ID[514]="GVG_111";
		ID[515]="GVG_109";
		ID[516]="GVG_112";
		ID[517]="GVG_065";
		ID[518]="GVG_096";
		ID[519]="GVG_105";
		ID[520]="GVG_064";
		ID[521]="GVG_108";
		ID[522]="GVG_070";
		ID[523]="GVG_075";
		ID[524]="GVG_114";
		ID[525]="GVG_044";
		ID[526]="GVG_067";
		ID[527]="GVG_093";
		ID[528]="GVG_102";
		ID[529]="GVG_115";
		ID[530]="GVG_118";
		ID[531]="PRO_001";
		ID[532]="EX1_112";
		ID[533]="NEW1_016";
		ID[534]="EX1_062";
		
		Name[0]="Arcanite Reaper";
		Name[1]="Fiery War Axe";
		Name[2]="Gorehowl";
		Name[3]="Death's Bite";
		Name[4]="Ogre Warmaul";
		Name[5]="Charge";
		Name[6]="Cleave";
		Name[7]="Execute";
		Name[8]="Heroic Strike";
		Name[9]="Shield Block";
		Name[10]="Whirlwind";
		Name[11]="Battle Rage";
		Name[12]="Brawl";
		Name[13]="Commanding Shout";
		Name[14]="Inner Rage";
		Name[15]="Mortal Strike";
		Name[16]="Rampage";
		Name[17]="Shield Slam";
		Name[18]="Slam";
		Name[19]="Upgrade!";
		Name[20]="Bouncing Blade";
		Name[21]="Crush";
		Name[22]="Kor'kron Elite";
		Name[23]="Warsong Commander";
		Name[24]="Arathi Weaponsmith";
		Name[25]="Armorsmith";
		Name[26]="Cruel Taskmaster";
		Name[27]="Frothing Berserker";
		Name[28]="Grommash Hellscream";
		Name[29]="Iron Juggernaut";
		Name[30]="Screwjank Clunker";
		Name[31]="Shieldmaiden";
		Name[32]="Siege Engine";
		Name[33]="Warbot";
		Name[34]="Corruption";
		Name[35]="Drain Life";
		Name[36]="Hellfire";
		Name[37]="Mortal Coil";
		Name[38]="Sacrificial Pact";
		Name[39]="Shadow Bolt";
		Name[40]="Soulfire";
		Name[41]="Bane of Doom";
		Name[42]="Demonfire";
		Name[43]="Power Overwhelming";
		Name[44]="Sense Demons";
		Name[45]="Shadowflame";
		Name[46]="Siphon Soul";
		Name[47]="Twisting Nether";
		Name[48]="Darkbomb";
		Name[49]="Demonheart";
		Name[50]="Imp-losion";
		Name[51]="Dread Infernal";
		Name[52]="Succubus";
		Name[53]="Voidwalker";
		Name[54]="Blood Imp";
		Name[55]="Doomguard";
		Name[56]="Felguard";
		Name[57]="Flame Imp";
		Name[58]="Lord Jaraxxus";
		Name[59]="Pit Lord";
		Name[60]="Summoning Portal";
		Name[61]="Void Terror";
		Name[62]="Voidcaller";
		Name[63]="Anima Golem";
		Name[64]="Fel Cannon";
		Name[65]="Floating Watcher";
		Name[66]="Mal'Ganis";
		Name[67]="Mistress of Pain";
		Name[68]="Doomhammer";
		Name[69]="Stormforged Axe";
		Name[70]="Powermace";
		Name[71]="Ancestral Healing";
		Name[72]="Bloodlust";
		Name[73]="Frost Shock";
		Name[74]="Hex";
		Name[75]="Rockbiter Weapon";
		Name[76]="Totemic Might";
		Name[77]="Windfury";
		Name[78]="Ancestral Spirit";
		Name[79]="Earth Shock";
		Name[80]="Far Sight";
		Name[81]="Feral Spirit";
		Name[82]="Forked Lightning";
		Name[83]="Lava Burst";
		Name[84]="Lightning Bolt";
		Name[85]="Lightning Storm";
		Name[86]="Reincarnate";
		Name[87]="Ancestor's Call";
		Name[88]="Crackle";
		Name[89]="Fire Elemental";
		Name[90]="Flametongue Totem";
		Name[91]="Windspeaker";
		Name[92]="Al'Akir the Windlord";
		Name[93]="Dust Devil";
		Name[94]="Earth Elemental";
		Name[95]="Mana Tide Totem";
		Name[96]="Unbound Elemental";
		Name[97]="Dunemaul Shaman";
		Name[98]="Neptulon";
		Name[99]="Siltfin Spiritwalker";
		Name[100]="Vitality Totem";
		Name[101]="Whirling Zap-o-matic";
		Name[102]="Assassin's Blade";
		Name[103]="Perdition's Blade";
		Name[104]="Cogmaster's Wrench";
		Name[105]="Assassinate";
		Name[106]="Backstab";
		Name[107]="Deadly Poison";
		Name[108]="Fan of Knives";
		Name[109]="Sap";
		Name[110]="Shiv";
		Name[111]="Sinister Strike";
		Name[112]="Sprint";
		Name[113]="Vanish";
		Name[114]="Betrayal";
		Name[115]="Blade Flurry";
		Name[116]="Cold Blood";
		Name[117]="Conceal";
		Name[118]="Eviscerate";
		Name[119]="Headcrack";
		Name[120]="Preparation";
		Name[121]="Shadowstep";
		Name[122]="Sabotage";
		Name[123]="Tinker's Sharpsword Oil";
		Name[124]="Defias Ringleader";
		Name[125]="Edwin VanCleef";
		Name[126]="Kidnapper";
		Name[127]="Master of Disguise";
		Name[128]="Patient Assassin";
		Name[129]="SI:7 Agent";
		Name[130]="Anub'ar Ambusher";
		Name[131]="Goblin Auto-Barber";
		Name[132]="Iron Sensei";
		Name[133]="Ogre Ninja";
		Name[134]="One-eyed Cheat";
		Name[135]="Trade Prince Gallywix";
		Name[136]="Divine Spirit";
		Name[137]="Holy Nova";
		Name[138]="Holy Smite";
		Name[139]="Mind Blast";
		Name[140]="Mind Control";
		Name[141]="Mind Vision";
		Name[142]="Power Word: Shield";
		Name[143]="Shadow Word: Death";
		Name[144]="Shadow Word: Pain";
		Name[145]="Circle of Healing";
		Name[146]="Holy Fire";
		Name[147]="Inner Fire";
		Name[148]="Mass Dispel";
		Name[149]="Mindgames";
		Name[150]="Shadow Madness";
		Name[151]="Shadowform";
		Name[152]="Silence";
		Name[153]="Thoughtsteal";
		Name[154]="Light of the Naaru";
		Name[155]="Lightbomb";
		Name[156]="Velen's Chosen";
		Name[157]="Northshire Cleric";
		Name[158]="Auchenai Soulpriest";
		Name[159]="Cabal Shadow Priest";
		Name[160]="Lightspawn";
		Name[161]="Lightwell";
		Name[162]="Prophet Velen";
		Name[163]="Temple Enforcer";
		Name[164]="Dark Cultist";
		Name[165]="Shadowbomber";
		Name[166]="Shadowboxer";
		Name[167]="Shrinkmeister";
		Name[168]="Upgraded Repair Bot";
		Name[169]="Vol'jin";
		Name[170]="Light's Justice";
		Name[171]="Truesilver Champion";
		Name[172]="Sword of Justice";
		Name[173]="Coghammer";
		Name[174]="Blessing of Kings";
		Name[175]="Blessing of Might";
		Name[176]="Consecration";
		Name[177]="Hammer of Wrath";
		Name[178]="Hand of Protection";
		Name[179]="Holy Light";
		Name[180]="Humility";
		Name[181]="Avenging Wrath";
		Name[182]="Blessed Champion";
		Name[183]="Blessing of Wisdom";
		Name[184]="Divine Favor";
		Name[185]="Equality";
		Name[186]="Eye for an Eye";
		Name[187]="Holy Wrath";
		Name[188]="Lay on Hands";
		Name[189]="Noble Sacrifice";
		Name[190]="Redemption";
		Name[191]="Repentance";
		Name[192]="Avenge";
		Name[193]="Muster for Battle";
		Name[194]="Seal of Light";
		Name[195]="Guardian of Kings";
		Name[196]="Aldor Peacekeeper";
		Name[197]="Argent Protector";
		Name[198]="Tirion Fordring";
		Name[199]="Bolvar Fordragon";
		Name[200]="Cobalt Guardian";
		Name[201]="Quartermaster";
		Name[202]="Scarlet Purifier";
		Name[203]="Shielded Minibot";
		Name[204]="Arcane Explosion";
		Name[205]="Arcane Intellect";
		Name[206]="Arcane Missiles";
		Name[207]="Fireball";
		Name[208]="Flamestrike";
		Name[209]="Frost Nova";
		Name[210]="Frostbolt";
		Name[211]="Mirror Image";
		Name[212]="Polymorph";
		Name[213]="Blizzard";
		Name[214]="Cone of Cold";
		Name[215]="Counterspell";
		Name[216]="Ice Barrier";
		Name[217]="Ice Block";
		Name[218]="Ice Lance";
		Name[219]="Mirror Entity";
		Name[220]="Pyroblast";
		Name[221]="Spellbender";
		Name[222]="Vaporize";
		Name[223]="Duplicate";
		Name[224]="Echo of Medivh";
		Name[225]="Flamecannon";
		Name[226]="Unstable Portal";
		Name[227]="Water Elemental";
		Name[228]="Archmage Antonidas";
		Name[229]="Ethereal Arcanist";
		Name[230]="Kirin Tor Mage";
		Name[231]="Mana Wyrm";
		Name[232]="Sorcerer's Apprentice";
		Name[233]="Flame Leviathan";
		Name[234]="Goblin Blastmage";
		Name[235]="Snowchugger";
		Name[236]="Soot Spewer";
		Name[237]="Wee Spellstopper";
		Name[238]="Eaglehorn Bow";
		Name[239]="Gladiator's Longbow";
		Name[240]="Glaivezooka";
		Name[241]="Animal Companion";
		Name[242]="Arcane Shot";
		Name[243]="Hunter's Mark";
		Name[244]="Kill Command";
		Name[245]="Multi-Shot";
		Name[246]="Tracking";
		Name[247]="Bestial Wrath";
		Name[248]="Deadly Shot";
		Name[249]="Explosive Shot";
		Name[250]="Explosive Trap";
		Name[251]="Flare";
		Name[252]="Freezing Trap";
		Name[253]="Misdirection";
		Name[254]="Snake Trap";
		Name[255]="Snipe";
		Name[256]="Unleash the Hounds";
		Name[257]="Call Pet";
		Name[258]="Cobra Shot";
		Name[259]="Feign Death";
		Name[260]="Houndmaster";
		Name[261]="Starving Buzzard";
		Name[262]="Timber Wolf";
		Name[263]="Tundra Rhino";
		Name[264]="King Krush";
		Name[265]="Savannah Highmane";
		Name[266]="Scavenging Hyena";
		Name[267]="Webspinner";
		Name[268]="Gahz'rilla";
		Name[269]="King of Beasts";
		Name[270]="Metaltooth Leaper";
		Name[271]="Steamwheedle Sniper";
		Name[272]="Claw";
		Name[273]="Healing Touch";
		Name[274]="Innervate";
		Name[275]="Mark of the Wild";
		Name[276]="Moonfire";
		Name[277]="Savage Roar";
		Name[278]="Starfire";
		Name[279]="Swipe";
		Name[280]="Wild Growth";
		Name[281]="Bite";
		Name[282]="Force of Nature";
		Name[283]="Mark of Nature";
		Name[284]="Naturalize";
		Name[285]="Nourish";
		Name[286]="Power of the Wild";
		Name[287]="Savagery";
		Name[288]="Soul of the Forest";
		Name[289]="Starfall";
		Name[290]="Wrath";
		Name[291]="Poison Seeds";
		Name[292]="Dark Wispers";
		Name[293]="Recycle";
		Name[294]="Tree of Life";
		Name[295]="Ironbark Protector";
		Name[296]="Ancient of Lore";
		Name[297]="Ancient of War";
		Name[298]="Cenarius";
		Name[299]="Druid of the Claw";
		Name[300]="Keeper of the Grove";
		Name[301]="Anodized Robo Cub";
		Name[302]="Druid of the Fang";
		Name[303]="Grove Tender";
		Name[304]="Malorne";
		Name[305]="Mech-Bear-Cat";
		Name[306]="Acidic Swamp Ooze";
		Name[307]="Archmage";
		Name[308]="Bloodfen Raptor";
		Name[309]="Bluegill Warrior";
		Name[310]="Booty Bay Bodyguard";
		Name[311]="Boulderfist Ogre";
		Name[312]="Chillwind Yeti";
		Name[313]="Core Hound";
		Name[314]="Dalaran Mage";
		Name[315]="Darkscale Healer";
		Name[316]="Dragonling Mechanic";
		Name[317]="Elven Archer";
		Name[318]="Frostwolf Grunt";
		Name[319]="Frostwolf Warlord";
		Name[320]="Gnomish Inventor";
		Name[321]="Goldshire Footman";
		Name[322]="Grimscale Oracle";
		Name[323]="Gurubashi Berserker";
		Name[324]="Ironforge Rifleman";
		Name[325]="Ironfur Grizzly";
		Name[326]="Kobold Geomancer";
		Name[327]="Lord of the Arena";
		Name[328]="Magma Rager";
		Name[329]="Murloc Raider";
		Name[330]="Murloc Tidehunter";
		Name[331]="Nightblade";
		Name[332]="Novice Engineer";
		Name[333]="Oasis Snapjaw";
		Name[334]="Ogre Magi";
		Name[335]="Raid Leader";
		Name[336]="Razorfen Hunter";
		Name[337]="Reckless Rocketeer";
		Name[338]="River Crocolisk";
		Name[339]="Sen'jin Shieldmasta";
		Name[340]="Shattered Sun Cleric";
		Name[341]="Silverback Patriarch";
		Name[342]="Stonetusk Boar";
		Name[343]="Stormpike Commando";
		Name[344]="Stormwind Champion";
		Name[345]="Stormwind Knight";
		Name[346]="Voodoo Doctor";
		Name[347]="War Golem";
		Name[348]="Wolfrider";
		Name[349]="Abomination";
		Name[350]="Abusive Sergeant";
		Name[351]="Acolyte of Pain";
		Name[352]="Alarm-o-Bot";
		Name[353]="Alexstrasza";
		Name[354]="Amani Berserker";
		Name[355]="Ancient Brewmaster";
		Name[356]="Ancient Mage";
		Name[357]="Ancient Watcher";
		Name[358]="Angry Chicken";
		Name[359]="Arcane Golem";
		Name[360]="Argent Commander";
		Name[361]="Argent Squire";
		Name[362]="Azure Drake";
		Name[363]="Baron Geddon";
		Name[364]="Big Game Hunter";
		Name[365]="Blood Knight";
		Name[366]="Bloodmage Thalnos";
		Name[367]="Bloodsail Corsair";
		Name[368]="Bloodsail Raider";
		Name[369]="Cairne Bloodhoof";
		Name[370]="Captain Greenskin";
		Name[371]="Coldlight Oracle";
		Name[372]="Coldlight Seer";
		Name[373]="Crazed Alchemist";
		Name[374]="Cult Master";
		Name[375]="Dark Iron Dwarf";
		Name[376]="Deathwing";
		Name[377]="Defender of Argus";
		Name[378]="Demolisher";
		Name[379]="Dire Wolf Alpha";
		Name[380]="Doomsayer";
		Name[381]="Dread Corsair";
		Name[382]="Earthen Ring Farseer";
		Name[383]="Emperor Cobra";
		Name[384]="Faceless Manipulator";
		Name[385]="Faerie Dragon";
		Name[386]="Fen Creeper";
		Name[387]="Flesheating Ghoul";
		Name[388]="Frost Elemental";
		Name[389]="Gadgetzan Auctioneer";
		Name[390]="Gruul";
		Name[391]="Harrison Jones";
		Name[392]="Harvest Golem";
		Name[393]="Hogger";
		Name[394]="Hungry Crab";
		Name[395]="Illidan Stormrage";
		Name[396]="Imp Master";
		Name[397]="Injured Blademaster";
		Name[398]="Ironbeak Owl";
		Name[399]="Jungle Panther";
		Name[400]="King Mukla";
		Name[401]="Knife Juggler";
		Name[402]="Leeroy Jenkins";
		Name[403]="Leper Gnome";
		Name[404]="Lightwarden";
		Name[405]="Loot Hoarder";
		Name[406]="Lorewalker Cho";
		Name[407]="Mad Bomber";
		Name[408]="Malygos";
		Name[409]="Mana Addict";
		Name[410]="Mana Wraith";
		Name[411]="Master Swordsmith";
		Name[412]="Millhouse Manastorm";
		Name[413]="Mind Control Tech";
		Name[414]="Mogu'shan Warden";
		Name[415]="Molten Giant";
		Name[416]="Mountain Giant";
		Name[417]="Murloc Tidecaller";
		Name[418]="Murloc Warleader";
		Name[419]="Nat Pagle";
		Name[420]="Nozdormu";
		Name[421]="Onyxia";
		Name[422]="Pint-Sized Summoner";
		Name[423]="Priestess of Elune";
		Name[424]="Questing Adventurer";
		Name[425]="Raging Worgen";
		Name[426]="Ragnaros the Firelord";
		Name[427]="Ravenholdt Assassin";
		Name[428]="Scarlet Crusader";
		Name[429]="Sea Giant";
		Name[430]="Secretkeeper";
		Name[431]="Shieldbearer";
		Name[432]="Silver Hand Knight";
		Name[433]="Silvermoon Guardian";
		Name[434]="Southsea Captain";
		Name[435]="Southsea Deckhand";
		Name[436]="Spellbreaker";
		Name[437]="Spiteful Smith";
		Name[438]="Stampeding Kodo";
		Name[439]="Stranglethorn Tiger";
		Name[440]="Sunfury Protector";
		Name[441]="Sunwalker";
		Name[442]="Sylvanas Windrunner";
		Name[443]="Tauren Warrior";
		Name[444]="The Beast";
		Name[445]="The Black Knight";
		Name[446]="Thrallmar Farseer";
		Name[447]="Tinkmaster Overspark";
		Name[448]="Twilight Drake";
		Name[449]="Venture Co. Mercenary";
		Name[450]="Violet Teacher";
		Name[451]="Wild Pyromancer";
		Name[452]="Windfury Harpy";
		Name[453]="Wisp";
		Name[454]="Worgen Infiltrator";
		Name[455]="Young Dragonhawk";
		Name[456]="Young Priestess";
		Name[457]="Youthful Brewmaster";
		Name[458]="Ysera";
		Name[459]="Baron Rivendare";
		Name[460]="Dancing Swords";
		Name[461]="Deathlord";
		Name[462]="Echoing Ooze";
		Name[463]="Feugen";
		Name[464]="Haunted Creeper";
		Name[465]="Kel'Thuzad";
		Name[466]="Loatheb";
		Name[467]="Mad Scientist";
		Name[468]="Maexxna";
		Name[469]="Nerub'ar Weblord";
		Name[470]="Nerubian Egg";
		Name[471]="Shade of Naxxramas";
		Name[472]="Sludge Belcher";
		Name[473]="Spectral Knight";
		Name[474]="Stalagg";
		Name[475]="Stoneskin Gargoyle";
		Name[476]="Undertaker";
		Name[477]="Unstable Ghoul";
		Name[478]="Wailing Soul";
		Name[479]="Zombie Chow";
		Name[480]="Annoy-o-Tron";
		Name[481]="Antique Healbot";
		Name[482]="Arcane Nullifier X-21";
		Name[483]="Blingtron 3000";
		Name[484]="Bomb Lobber";
		Name[485]="Burly Rockjaw Trogg";
		Name[486]="Clockwork Giant";
		Name[487]="Clockwork Gnome";
		Name[488]="Cogmaster";
		Name[489]="Dr. Boom";
		Name[490]="Enhance-o Mechano";
		Name[491]="Explosive Sheep";
		Name[492]="Fel Reaver";
		Name[493]="Flying Machine";
		Name[494]="Foe Reaper 4000";
		Name[495]="Force-Tank MAX";
		Name[496]="Gazlowe";
		Name[497]="Gilblin Stalker";
		Name[498]="Gnomeregan Infantry";
		Name[499]="Gnomish Experimenter";
		Name[500]="Goblin Sapper";
		Name[501]="Hemet Nesingwary";
		Name[502]="Hobgoblin";
		Name[503]="Illuminator";
		Name[504]="Jeeves";
		Name[505]="Junkbot";
		Name[506]="Kezan Mystic";
		Name[507]="Lil' Exorcist";
		Name[508]="Lost Tallstrider";
		Name[509]="Madder Bomber";
		Name[510]="Mechanical Yeti";
		Name[511]="Mechwarper";
		Name[512]="Mekgineer Thermaplugg";
		Name[513]="Micro Machine";
		Name[514]="Mimiron's Head";
		Name[515]="Mini-Mage";
		Name[516]="Mogor the Ogre";
		Name[517]="Ogre Brute";
		Name[518]="Piloted Shredder";
		Name[519]="Piloted Sky Golem";
		Name[520]="Puddlestomper";
		Name[521]="Recombobulator";
		Name[522]="Salty Dog";
		Name[523]="Ship's Cannon";
		Name[524]="Sneed's Old Shredder";
		Name[525]="Spider Tank";
		Name[526]="Stonesplinter Trogg";
		Name[527]="Target Dummy";
		Name[528]="Tinkertown Technician";
		Name[529]="Toshley";
		Name[530]="Troggzor the Earthinator";
		Name[531]="Elite Tauren Chieftain";
		Name[532]="Gelbin Mekkatorque";
		Name[533]="Captain's Parrot";
		Name[534]="Old Murk-Eye";
		}
}