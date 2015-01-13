import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
//CISC275 FALL2012 GROUP 5-- STORM CRISIS 

public class Grid extends JPanel implements ActionListener, MouseListener {
	
	//Grid is the main file for our game, it controls the locations and actions of nearly everything. It does all of the drawing.
	
	//RunMe is the one you'll want to run to play the game, it sets things up and controls timing based on values saved right below here in grid.
	
	//Other important classes are the four plants, the water, and the classes they extend, drawable and plant.
	
	public int numButterflies;//number of butterflies on the grid.
	public int topMargin = 135; //128 is minimum here because of menu size
	public int leftMargin = 8; //empty space between grid & screen
	public int menutopMargin = 0;
	public int menuleftMargin = 50;
	public int numTilesX = 14; //number of columns
	public int numTilesY = 10; //number of rows
	public Grid_Type background[][] = new Grid_Type[numTilesX][numTilesY]; //for the time being hard coded to 14 by 10
	public boolean occupiedSquares[][] = new boolean[numTilesX][numTilesY];
	public ArrayList<Drawable> foreground = new ArrayList<Drawable>(25); //contains all plants and waters
	ArrayList<Drawable> killedWaters = new ArrayList<Drawable>(20);
	private int[] runoff_Tolerance; //0 north, 1 east, 2 south, 3 west
	private int score = 100; //number of waters that can reach drains before you lose- changing from 100 will break water meter animation
	int rainIndex = 0;
	public growThread grower; 
	int toolUsed = 9999; //used by the overlay on tools
	boolean starting = true; //true when splash screen is shown, false during play or ending
	boolean explaining = false;
	boolean stillExplaining = false;
	boolean stillStillExplaining = false;
	boolean clearingSplash = false;
	boolean ending = false; //true when final screen is shown, false during play or intro
	boolean learningMore = false;
	int invasiveRate = 20; //25 means does this every 25 cycles. 
	int waterSpawnRate = 6; //see above comment, same deal
	int waterMoveRate = 10; //and again
	int grassSpawnRate = 8;
	int waterBaseSize = 15;
	int maxWaterSize = 60; //maximum spawn health for water enemies
	int waterGrowRate = 50; //waters spawn with 1 more hp every (this many) cycles
	boolean ableToPlant = false;
	boolean drainStates[] = {false, false, false, false};
	int attemptedx = 9999;//used by functions that confirm planting worked
	int attemptedy = 9999;//without this planting wont work if you click at the wrong time, such as when waters are moving
	int lastToolUsed = 0;
	boolean hovering = false; //if hovering over something that should have a tooltip drawn
	boolean mowerClicked = false; 
	int mouseX =0; //current mouse position, according to the last time you checked
	int mouseY =0;
	int prevTool = 999; //previous tool used, used by function to clear screen of lawnmower pictures out of bounds when you switch from lawnmower tool
	
	//private instantiations of all the images used in the game
	private BufferedImage waterMeterHeader; 
	private BufferedImage waterMeterEmpty;
	private BufferedImage waterMeterFull;
	private BufferedImage waterMeterWave;
	private BufferedImage noLayover;
	private BufferedImage Layover;
	private BufferedImage scoreShower;
	private BufferedImage womanOnRight;
	private BufferedImage house;
	private BufferedImage startScreen;
	private BufferedImage descriptionScreen;
	private BufferedImage endScreenFail;
	private BufferedImage endScreenBronze;
	private BufferedImage endScreenSilver;
	private BufferedImage endScreenGold;
	private BufferedImage happyDrain;
	private BufferedImage happyDrainEast;
	private BufferedImage happyDrainWest;
	private BufferedImage happyDrainNorth;
	private BufferedImage happyDrainSouth;
	private BufferedImage sadDrain;
	private BufferedImage sadDrainEast;
	private BufferedImage sadDrainWest;
	private BufferedImage sadDrainNorth;
	private BufferedImage sadDrainSouth;
	private BufferedImage ButterflyBushDescription;
	private BufferedImage CommonMilkweedDescription;
	private BufferedImage JapaneseMapleDescription;
	private BufferedImage SasafrasDescription;
	private BufferedImage LawnmowerDescription;
	private BufferedImage cooldownOverlayFull;
	private BufferedImage cooldownOverlayHalf;
	private BufferedImage cooldownOverlayQuarter;
	private BufferedImage cooldownOverlay3Quarters;
	private BufferedImage butterflyDisplayBackground;
	private BufferedImage smallButterfly;
	private BufferedImage toolsLabel;
	private BufferedImage defaultdesc;
	private BufferedImage introdesc;
	private BufferedImage tooltipPrototype;
	private BufferedImage rains[] = new BufferedImage[20];
	private BufferedImage waterMeterImages[] = new BufferedImage[8];
	private BufferedImage tooltips[] = new BufferedImage[9];
	private BufferedImage firstDescriptionScreen;
	private BufferedImage secondDescriptionScreen;
	private BufferedImage thirdDescriptionScreen;
	private BufferedImage mouseMower;
	private BufferedImage mouseMowerImages[] = new BufferedImage[4];
	private BufferedImage bronzeImage;
	private BufferedImage silverImage;
	private BufferedImage goldImage;
	private BufferedImage failImage;
	private BufferedImage learnMoreScreen;
	boolean muted = false;
	boolean mouseOverMenu = false;
	int waterMeterIndex = 0;
	boolean debugMode = false;
	int hoverIndex = 99;
	int cycles = 0;

	//These are columns for the default grid
	//could make different ones for different difficulties, not hard to do.
	public int[][] intGrid ={{0,0,0,5,0,0,0,0,0,0}, //remember these are columns despite this format in the code
							 {0,0,0,6,3,3,3,3,9,0},
							 {0,0,0,0,0,0,0,0,5,0},
							 {0,0,0,0,7,3,3,3,8,0},
							 {0,0,0,0,5,0,0,0,0,0},
							 {0,0,0,4,5,4,4,0,0,0},
							 {0,7,3,3,4,4,4,0,0,0},
							 {0,5,0,4,4,4,3,3,9,0},
							 {0,5,0,4,4,5,0,0,5,0},
							 {3,8,0,0,0,5,0,0,5,0},
							 {0,0,0,0,0,6,9,0,5,0},
							 {0,0,0,0,0,0,5,0,5,0},
							 {0,0,0,7,3,3,8,0,6,3},
							 {0,0,0,5,0,0,0,0,0,0}};
	public int[][] foregroundGrid = new int[14][10];
	public Menu menu = new Menu(); //menu class populates a set of tools
	
	public void change_Score(int c){ //used to decrement score when waters reach drains
		score += c;
	}	
	public void checkIfPlantingWorked(int op){ //used by addplant to see if should reset cooldown
		if(attemptedx!=9999 && attemptedy != 9999){
			
		switch(op){
		case(0):
		if(occupiedSquares[attemptedx][attemptedy] == true && getThingAt(attemptedx,attemptedy).getName().equals("Sasafras")){
			setAble(false); //if the square you tried to plant on has something on it and that thing is the type of thing you wanted to plant
			menu.setUsed(0); //then it worked and you shouldnt be able to plant another one until you select the tool again, also sets the tool
			resetCheck(); //as used for cooldown purposes
			}
			break;
		case(1):
			if(occupiedSquares[attemptedx][attemptedy] == true && getThingAt(attemptedx,attemptedy).getName().equals("Common Milkweed")){
				setAble(false); //flags that you've used the tool and shouldn't be able to plant
				menu.setUsed(1); //resets the cooldown time to the maximum time for that tool
				resetCheck(); //sets the attemptedx and y to 9999 to avoid checking this again until you pick a new tool
				}
			break;	
		case(2):
			if(occupiedSquares[attemptedx][attemptedy] == true && getThingAt(attemptedx,attemptedy).getName().equals("Japanese Maple")){
				setAble(false);
				menu.setUsed(2);
				speedUpSpread(); //planting invasives increases the natural spawn rate of invasives
				resetCheck();
				}
			break;	
		case(3):
			if(occupiedSquares[attemptedx][attemptedy] == true && getThingAt(attemptedx,attemptedy).getName().equals("Butterfly Bush")){
				setAble(false);
				menu.setUsed(3);
				speedUpSpread();
				resetCheck();
				}
			break;		
		default:
			break;
		}
		}
	
	}
	public void resetCheck(){  //used by function directly above to say that the planting worked and shouldnt keep checking
		toolUsed = 999;
		attemptedx = 9999;
		attemptedy= 9999;
	}
	
	//used to set boolean if mower tool is clicked
	public void setMowerClicked(boolean t){
		mowerClicked = t;
	}
	
	public void setHover(boolean b, int which){ //used to draw tooltips for menu tools, which is which tool number 0-4
		hovering = b;
		hoverIndex = which;
	}
	
	
	//public get methods
	public int getNumButterflies(){
		return numButterflies;
	}
	
	public int getWaterSpawnRate(){
		return waterSpawnRate;
	}
	public int getWaterMoveRate(){
		return waterMoveRate;
	}
	public int getGrassGrowRate(){
		return grassSpawnRate;
	}
	public void updateWaterSpawnRate(){
		waterSpawnRate--;
	}
	public int getCycles(){
		return cycles;
	}
	public void setAble(boolean b){
		ableToPlant=b;
	}
	public boolean checkStarting(){
		// determines if start screens are being shown, prevents game from running without you seeing it
		return starting || explaining || stillExplaining || stillStillExplaining;
	}
	public boolean checkEnding(){
		// determines if end screen is being shown
		return ending;
	}
	
	public int getNumTilesX(){
		return numTilesX;
	}
	
	public int getNumTilesY(){
		return numTilesY;
	}
	
	public int[][] getIntGrid(){
		//the int grid is the int representation of the background tiles.
		return intGrid;
	}
	
	public boolean[][] getOccupiedSquares(){
		//the occupied squares boolean grid is a representation of which squares have a foreground object on them
		return occupiedSquares;
	}
	
	public boolean checkOccupied(int x, int y){
		//returns if something is at that xy location in the foreground
		return occupiedSquares[x][y];
	}
	
	public class growThread extends Thread {
		//determines which grid_Tile grows, and when.
	
		public void run (){
		Random r = new Random();
			int x =  r.nextInt(numTilesY); //get a random x tile
			int y = r.nextInt(numTilesX); //get a random y tile
			//make a method for getting a new location? seems that this block
			//will be used frequently.
			System.out.println("<"+ x + ", "+ y+ ">");
		}
	}
	
	public int get_Score(){ //used to show water meter correctly
		return score;
	}
	
	public void paint(Graphics g){
		if(starting){ //does this only at start of game
			
			g.drawImage(startScreen,0,0,this);
			makeItRain(g);}
		
		if(explaining){ //also a one time thing
		g.drawImage(firstDescriptionScreen,0,0,this); //newer one by patrick
		//	g.drawImage(descriptionScreen, 0, 0, this);  older one by andrew
		makeItRain(g);
		}
		if(stillExplaining)
			g.drawImage(secondDescriptionScreen,0,0,this); //the crappy temp one you see now, just replace the file with same name
			
		if(stillStillExplaining)
			g.drawImage(thirdDescriptionScreen,0,0,this);
		
		if(clearingSplash){ //hides splash screens
			clearingSplash = false;
			g.clearRect(0, 0, 1195, 830);
		}
		
		if(ending && !learningMore) //does this only at the end of game
			draw_end_screen(g);
		
		if(learningMore)
			draw_learn_screen(g);
		
		if(!explaining && !stillExplaining && !stillStillExplaining && !starting && !ending){
		//drawing grid background
			if(prevTool == 4 && toolUsed != 4)
				g.clearRect(0, 0, 1195, 830); //clears everything if you were using a lawnmower but now aren't.
			draw_Background(g); //all of the grasses and paths
			g.drawImage(house, 330, 330, this); //house at middle
			if(getCycles() >=50) //for first 50 cycles waters arent spawning and the storm hasnt started
				makeItRain(g); //animated rain
			//drawing grid foreground
			draw_Foreground(g); //all plants and waters
			animate_Gutters(g); //four gutters at ends of paths
		//drawing menu
			draw_selection_Layover(g); //yellow border over tools
			draw_Menu(g); //transparent tools and description boxes
			drawButterflyDisplay(g); //the score shower
			drawCooldowns(g); //overlays on tools
			if(hovering) //if youre hovering show the tooltip for the thing youre hovering over
				showTooltip(g);
			else if(!mouseOverMenu)  //otherwise if your mouse isnt over the menu then you should draw the tooltip saying how to play
				g.drawImage(tooltips[8], 916, 115, this);
		//drawing water meter
			draw_Water_Level(g); //shows the water meter
			if(toolUsed == 4)
				showLawnMowerMouse(g);
			
		}
	}
	public void setPrev(){
		prevTool = toolUsed;
	}
	public void draw_end_screen(Graphics g){
		if(get_Score() >90){ //you got to 1k butterflies with meter less than 10% full
			new AePlayWave("fanfare_x.wav").start();
			g.drawImage(endScreenGold,0,0,this);}
		else if(get_Score()>50) //you got to 1k butterflies with meter less than 50% full
			g.drawImage(endScreenSilver,0,0,this);
		else if(get_Score()>0) //you got to 1k butterflies with meter more than 50% full
			g.drawImage(endScreenBronze,0,0,this);
		else {//the meter filled
			new AePlayWave("storm-rainthunder5.wav").start();
			g.drawImage(endScreenFail,0,0,this);}
	}
	public void draw_learn_screen(Graphics g){
		g.drawImage(learnMoreScreen, 0, 0, this);
	}
	
	public void showTooltip(Graphics g){
		
		if(hoverIndex !=99){
			if(hoverIndex < 8)
				g.drawImage(tooltips[hoverIndex],916,115,this);
			else
				drawToolTooltips(hoverIndex-8,g);
		}
		
	}
	
	//used to set the x and y location of the mouse from Grid_Mouse_Listener
	public void setMouseLoc(int x, int y){
		mouseX = x;
		mouseY = y;
	}
	
	public void showLawnMowerMouse(Graphics g){
		//later change so loops through image array for animation effect
			
		/*for(int i=0; i < mouseMowerImages.length; i++){
				g.drawImage(mouseMowerImages[i], mouseX-50, mouseY-100,this);
			}*/
		
			g.drawImage(mouseMower, mouseX-50, mouseY-100,this);
	}
	
	public void draw_text_grid(){
		//this is purely for debugging purposes, draws 3 arrays to console to help find if something is working wrong logically.
		populateGrids();
		if(debugMode){ //wont spam you if you dont have the boolean at the top of the file set to true
			System.out.println(" ");
			for(int j = 0; j < numTilesY; j++){
				for(int i = 0; i < numTilesX; i++){
					int tempVal = occupiedSquares[i][j]? 1 : 0; //convert boolean to int
					System.out.print(" " + tempVal); //occupied booleans, 1 occupied 0 not occupied
				}
				System.out.print("      ");
				for(int i = 0; i < numTilesX; i++){
					System.out.print(" " + intGrid[i][j]); //background tiles, 0 grass 1 meadow 2 forest 3 path 4 roof 5 path 6 path
				}
				System.out.print("      ");
				for(int i = 0; i < numTilesX; i++){
					System.out.print(" " + foregroundGrid[i][j]); //types of foreground objects, 0 nothing, 3 invasive, 2 native, 9 water
				}
				System.out.println();
			}
		}
	}
	
	public void populateGrids(){
		//refreshes all of the logic grids with current locations of objects so other things can accurately 'see'.
		resetGrids(); //blanks out foregroundgrid and occupiedsquares to all zeroes
		int location[] = {0,0}; //initialize location
		for(Drawable d:foreground){  //iterates over all drawable objects (plants, waters)
			location  = d.getLoc(); //gets location from that object
			foregroundGrid[location[0]][location[1]]= d.getType(); //and populates the foreground and occupied squares grids for use elsewhere
			occupiedSquares[location[0]][location[1]] = true;
		}
	}
	
	public void resetGrids(){
		//this function blanks out the foregroundgrid and occupiedsquares arrays
		for(int i = 0; i < numTilesX; i++){
			for(int j = 0; j<numTilesY;j++){
				foregroundGrid[i][j] = 0;
				occupiedSquares[i][j] = false;
			}
		}
	}
	public void speedUpSpread(){
		//every time you plant an invasive plant you increase the rate at which they automatically spawn
		if(invasiveRate>2){ //prevents spawn rate from dropping below 2 cycles, even at this rate the screen will fill with them
			invasiveRate--;
		}
	}
	
	public void draw_Background(Graphics g){
		//this draws all background tiles- grass, meadow, forest, path, roof.
		
		for(int i = 0; i < numTilesX; i++){ //iterates over all tiles and grabs the right object's picture from background array
			for(int j = 0; j < numTilesY; j++){
				g.drawImage(background[i][j].getTile(), i * 64 + leftMargin, j * 64 + topMargin,  this); 
			}
		}
	}
	
	//draw cooldown timer overlays onto menu bar
	//changes picture of circle timer based on how much time is left in cooldown
	public void drawCooldowns(Graphics g){
		for(int i = 0; i < menu.getNumTools();i++){
			
			int remCD = menu.getRemCooldownFor(i); //remaining time in CD
			int totCD = menu.getBaseCooldownFor(i); //total cooldown time
			
			if(totCD >0){ //only show if have a cooldown (mower doesnt have a CD)
				if( remCD <= totCD && remCD >= (totCD - (totCD/4))){
					//full timer-100%
					g.drawImage(cooldownOverlayFull, i*128+menuleftMargin, menutopMargin,this);
				}
				else if( remCD < (totCD - (totCD/4)) && remCD >= (totCD - (totCD/2))){
					//three quarters full timer-75%
					g.drawImage(cooldownOverlay3Quarters, i*128+menuleftMargin, menutopMargin,this);
				}
				else if( remCD < (totCD - (totCD/2)) && remCD >= (totCD/4)){
					//half full timer-50%
					g.drawImage(cooldownOverlayHalf, i*128+menuleftMargin, menutopMargin,this);
				}
				else if( remCD < (totCD/4) && remCD > 0){
					//quarter full timer-25%
					g.drawImage(cooldownOverlayQuarter, i*128+menuleftMargin, menutopMargin,this);
				}
			}
		}	
	}
	public void drawToolTooltips(int toolNumber, Graphics g){
		switch(toolNumber){
			//this shows the description box
		case(0): //916 is just a magic number for x offset
			g.drawImage(SasafrasDescription,916,menutopMargin,this);
			break;
		case(1):
			g.drawImage(CommonMilkweedDescription,916,menutopMargin,this);
			break;
		case(2):
			g.drawImage(JapaneseMapleDescription,916,menutopMargin,this);
			break;
		case(3):
			g.drawImage(ButterflyBushDescription,916,menutopMargin,this);
			break;
		case(4):
			g.drawImage(LawnmowerDescription,916,menutopMargin,this);
			break;
			}
		}
	public void draw_Menu(Graphics g){
		//this draws the tools at the top of the screen, the score showing butterfly and the description at the top right
		g.drawImage(toolsLabel,8,menutopMargin,this);
		
		for(int i = 0; i < menu.getNumTools(); i++){ //iterates over menu and draws all five in right spot
			g.drawImage(menu.getToolBox()[i].getIcon(), i * 128 + menuleftMargin, menutopMargin, this); 
		}
		
		switch(toolUsed){
			//this shows the description box
		case(0): //916 is just a magic number for x offset
			if(ableToPlant)
			g.drawImage(SasafrasDescription,916,menutopMargin,this);
			else
			g.drawImage(defaultdesc,916,menutopMargin,this);
			break;
		case(1):
			if(ableToPlant)
			g.drawImage(CommonMilkweedDescription,916,menutopMargin,this);
			else
			g.drawImage(defaultdesc,916,menutopMargin,this);
			break;
		case(2):
			if(ableToPlant)
			g.drawImage(JapaneseMapleDescription,916,menutopMargin,this);
			else
			g.drawImage(defaultdesc,916,menutopMargin,this);
			break;
		case(3):
			if(ableToPlant)
			g.drawImage(ButterflyBushDescription,916,menutopMargin,this);
			else
			g.drawImage(defaultdesc,916,menutopMargin,this);
			break;
		case(4):
			g.drawImage(LawnmowerDescription,916,menutopMargin,this);
			break;
		case (9999):
			g.drawImage(introdesc,916,menutopMargin,this);
			break;
		case (999):
			g.drawImage(defaultdesc,916,menutopMargin,this);
			break;
		default: //originally we were using this picture of a deldot worker i drew but commented her out for being awful.
			g.drawImage(womanOnRight, 916, menutopMargin, this);	
			break;
		}	
		//ajani-
		//if you want to add hover-over tooltips just add draw statements right here that switch off of a boolean saying that youre currently hovering
		//and want to see a description then write right over the same location that the above switch writes to with
		//g.drawImage(imageName,916,menutopMargin,this);
		//just make them same dimensions and offsets as above ones.
		//declare the bufferedimage variables at the top of the file and load them in the big try catch at the bottom of the file
	}

	public void makeItRain(Graphics g){
		//this shows the rain overlay and cycles through 20 different pictures for it to animate
		g.drawImage(rains[rainIndex],0,0,this);
		if(rainIndex < 17)
			rainIndex+=2; //using +2 to speed up animation and make it look more like rain 
		else
			rainIndex = 0;
	}
	
	public void draw_Foreground(Graphics g){
		//this draws all foreground objects, theyre all drawable class, this is all plants and waters.
		growAllPlants();
		int thisloc[] = new int[2]; //foreground objects know their own location and its pulled into here to be used
		int offsetLoc[] = new int[2]; //offset lets us shift it to the center of the square, foreground tiles are 52x52 while background are 64x64
		for(Drawable d: foreground){ //iterates over the arraylist that contains them all
			thisloc = d.getLoc();
			offsetLoc[0] = (thisloc[0]*64); //64 is the sidelength of a background tile
			offsetLoc[1] = (thisloc[1]*64);
			offsetLoc[0] += leftMargin+6; //because foreground images are 52x52, 6 is the number to center them in a square
			offsetLoc[1] += topMargin+6;
			//System.out.println("Drawing to foreground at  " + (thisloc[1]*64));
			//g.drawImage(temp,offsetLoc[0],offsetLoc[1],this);
			g.drawImage(d.getPicture(),offsetLoc[0],offsetLoc[1],this); 
		}
	}
	
	public void draw_selection_Layover(Graphics g){
		//This function is used to highlight a tool that is selected on the menu
		
		for(int i = 0; i < 5; i++){ //uses tool used to show the yellow border behind active tool and gray behind all others
			if(toolUsed == i && ableToPlant)	
				g.drawImage(Layover, 50+(128*i), menutopMargin, this);  //this one is the yellow border
			else				
				g.drawImage(noLayover, 50+(128*i), menutopMargin, this); //this is plain blank white
		}
	}
	
	public void removeFromForeground(int x, int y){
		//this function will do the actual removing of a foreground object, it's done this way to prevent concurrency exceptions
		
		int thisloc[] = new int[2]; //as with above function, drawables know own loc so this pulls it in
		Drawable killThis = null; //if it doesnt find the one it wants it wont try to remove any
		for(Drawable a: foreground){ 
			thisloc = a.getLoc(); //pull location in
			if(thisloc[0] == x && thisloc[1] == y) //if it's the one you want to kill as per given parameters
				killThis = a; //then mark it as the one you want to remove but wait til after iterating over all of them to do so
		}
		if(killThis != null){
			occupiedSquares[x][y] = false; //on occupied squares array it marks that nothing is there
			foreground.remove(killThis); //removes the object from foreground arraylist here
		} 
	}
	
	
	public Drawable getThingAt(int x, int y){
		//this takes an xy location and returns the object that has that location, stored in the foreground arraylist
		
		int thisloc[] = new int[2]; //as with above function, drawables know own loc so this pulls it in
		for(Drawable a: foreground){ 
			thisloc = a.getLoc(); //pull location in
			if(thisloc[0] == x && thisloc[1] == y) //if it's the one you want to kill as per given parameters
				return a; //then mark it as the one you want to remove but wait til after iterating over all of them to do so
		}
		return null;
	}
	
	public Grid_Type getBackground(int x, int y){
		if (x>=0&& x <=9 &&  y>= 0 && y<=13)
			return  background[x][y];	
		else
			return null;
	}
	
	public void drawButterflyDisplay(Graphics g){ //this function shows the WILDLIFE SUPPORTED box
		g.drawImage(butterflyDisplayBackground,706,menutopMargin,this); //draw the wildlife supported background
		for(int i = 0; i<getNumButterflies();i+=100){ //for every 100 butterflies you should display one more
			System.out.println(getNumButterflies());
			if(i<500)
				g.drawImage(smallButterfly,710+((i/100)*35),menutopMargin+32,this); //first row of 5
			else
				g.drawImage(smallButterfly,710+(((i-500)/100)*35),menutopMargin+64,this); //second row of 5
		}
		
	}
	
	public void animate_Gutters(Graphics g){
		//shows one of two gutter images for each drain depending on if theyre being flooded by water or not
		//changes so there are four sets of two images, gutter direction changes based on location on grid path
		int drainLocs[][] = {{0,9,13,12},{3,0,3,9}}; //xy locations of the four drains
		
		//might want to change so not so much redundant code here
		for(int i = 0; i < 4; i++){ 
		//	System.out.println("Iteration " + i);
			if(drainLocs[0][i] == 0 && drainLocs[1][i] == 3){
				//use west
				//System.out.println("Drawing west");
				if(drainStates[i] == true)
					g.drawImage(sadDrainWest,drainLocs[0][i]* 64 + leftMargin,drainLocs[1][i]*64 + topMargin,this); //a lot of magic numbers here to get things in the right spot
				else
					g.drawImage(happyDrainWest, drainLocs[0][i] *64 + leftMargin, drainLocs[1][i]*64 + topMargin, this);
			}
			else if(drainLocs[0][i] == 9 && drainLocs[1][i] == 0){
				//use north
			//	System.out.println("Drawing north");
				if(drainStates[i] == true)
					g.drawImage(sadDrainNorth,drainLocs[0][i]* 64 + leftMargin,drainLocs[1][i]*64 + topMargin,this);
				else
					g.drawImage(happyDrainNorth, drainLocs[0][i] *64 + leftMargin, drainLocs[1][i]*64 + topMargin, this);
			}
			else if(drainLocs[0][i] == 13 && drainLocs[1][i] == 3){
				//use east
			//	System.out.println("Should be drawing east");
				if(drainStates[i] == true)
					g.drawImage(sadDrainEast,drainLocs[0][i]* 64 + 5*leftMargin,drainLocs[1][i]*64 + topMargin,this);
				else
					g.drawImage(happyDrainEast, drainLocs[0][i] *64 + 5*leftMargin, drainLocs[1][i]*64 + topMargin, this);
			
			}
			else if(drainLocs[0][i] == 12 && drainLocs[1][i] == 9){
				//use south
			//	System.out.println("Drawing south");
				if(drainStates[i] == true)
					g.drawImage(sadDrainSouth,drainLocs[0][i]* 64 + leftMargin-1,drainLocs[1][i]*64 + topMargin+30+2,this);
				else
					g.drawImage(happyDrainSouth, drainLocs[0][i] *64 + leftMargin-1, drainLocs[1][i]*64 + topMargin+30+2, this);
			
			}
		}
	}
	
	public void setDrainSad(int x, int y){
		//this function takes the location of a water and if its a gutter's location that gutter is set to sad
		//sad drains are ones that are being flooded with water
		if(x == 0 && y == 3 ) //left
			drainStates[0] = true;
				
		else if (x == 9 && y == 0) //top
			drainStates[1] = true;
				
		else if (x == 13 && y == 3) //right
			drainStates[2] = true;
		
		else if (x == 12 && y == 9) //bottom
			drainStates[3] = true;
	}
	
	public void resetDrainStates(){
		//this sets all of the gutters to happy, the default state
		for(int i=0; i < 4; i++){
			drainStates[i] = false;
		}
	}
	
	public void draw_Water_Level(Graphics g){
		//drawing water meter on right
		
		//The meter is based on 33 14 pixel high rows below a 50 pixel header, adding to 512 pixels tall, all are 256 pixels wide.
		
		int  numEmpty = (int) score/3; //see above comment that explains these numbers
		int  numFull = 33- numEmpty; 
		
		
		g.drawImage(waterMeterHeader, 916, menutopMargin+260, this); //draw header
		showRatingIndicator(g);
		
		for(int i = 0; i < numFull+1; i++){ //draw blue ones below empties, each below previous
				
				if(i == 0){
				g.drawImage(waterMeterImages[waterMeterIndex], 916, menutopMargin+300+((numEmpty-1) *14) , this);
				if(waterMeterIndex<7)
					waterMeterIndex++;
				else
					waterMeterIndex = 0;
				}
				else
				g.drawImage(waterMeterFull, 916, menutopMargin+300+16+((numEmpty-1) *14) +(14*i), this);
				
		}
		
		//this adds the wave picture at top of water in meter
		
		
	}
	public void showRatingIndicator(Graphics g){
		if(get_Score() > 90){
			g.drawImage(goldImage, 916, menutopMargin+290,this);
		}
		else if(get_Score() > 50){
			g.drawImage(silverImage, 916, menutopMargin+290,this);
		}
		else if(get_Score() > 10){
			g.drawImage(bronzeImage, 916, menutopMargin+290,this);
		}
		else{
			g.drawImage(failImage, 916, menutopMargin+290,this);
		}
	}
	public void setMouseOverMenu(boolean b){
		mouseOverMenu = b;
	}

	//adds butterfly count for sasafras and common milkweed plants
	public void addButterfly(){
		for(Drawable a: foreground){
			if(a.getName().equals("Sasafras") || a.getName().equals("Common Milkweed"))
				numButterflies ++;
		}
	}
	
	public void growAllPlants(){
		//makes plants sizes match up to background tiles
		//all plants sizes grow using this but right now invasives dont gain an effective strength bonus from it
		//that is to say the strength of a native starts at 3 and can go up to 5 if it's on forest (4 meadow)
		//but the strength of an invasive is always 1.
		populateGrids(); //refreshes locations of everything in the int and boolean grids
		for(int i = 0; i < numTilesX;i++){ 
			for(int j = 0; j<numTilesY;j++){ //iterate over background
				if(foregroundGrid[i][j] == 2 || foregroundGrid[i][j] == 3){ //if this square is a native or invasive plant
					
					switch(intGrid[i][j]){ //switch over background's int grid
					
					case(0): //if grass
						getThingAt(i,j).setSize(0); //set size bonus to 0
						break;
					case(1): //if meadow
						getThingAt(i,j).setSize(1); //set size bonus to 1
						break;
					case(2): //if forest
						getThingAt(i,j).setSize(2); //set size bonus to 2
						break;
					default:
						break;
					
					}	
				}
			}
		}
		
	}
	public void addPlantNoSound(int type, int x, int y){ 
		//this function adds a plant of the given type to the foreground arraylist, and gives it an x y location
		//types 0-7 accepted where 0,1 are native and 2,3 are invasive. any other vals will just say you cant do that in console.
		populateGrids();
		if(intGrid[x][y] <3 && x < numTilesX && y < numTilesY && occupiedSquares[x][y]!=true && ableToPlant){ //makes sure that the square is in bounds, it's not a path or roof, and that it's not occupied
			switch (type){
			case 0:
				foreground.add(new Sasafras(x,y)); //all of these just add a new object of a specific subclass at the location
				break;
			case 1:
				foreground.add(new CommonMilkweed(x,y));
				break;
			case 2:
				foreground.add(new JapaneseMaple(x,y));
				break;
			case 3:
				foreground.add(new ButterflyBush(x,y));
				break;
			default:
				System.out.println("Only types 0 thru 7 accepted");
				break;
			}
			occupiedSquares[x][y] = true; //marks this square as occupied
			if(toolUsed!=999){
			attemptedx = x; //the attempted click locations are used by a function elsewhere that checks if the planting was successful
			attemptedy = y; //if you click at the wrong time, while things are being drawn, very small window, it wont register so this prevents false cooldown triggers
		}	
			}
		else{
		//	System.out.println("Not a plantable square, is occupied or out of bounds");
		}
			
	}
	public void addPlant(int type, int x, int y){ 
		//this function adds a plant of the given type to the foreground arraylist, and gives it an x y location
		//types 0-7 accepted where 0,1 are native and 2,3 are invasive. any other vals will just say you cant do that in console.
		populateGrids();
		if(intGrid[x][y] <3 && x < numTilesX && y < numTilesY && occupiedSquares[x][y]!=true && ableToPlant){ //makes sure that the square is in bounds, it's not a path or roof, and that it's not occupied
			if(!muted)
			//new AePlayWave("CHIMES.WAV").start();
			new AePlayWave("PlantingSoundMaybe.wav").start();
			switch (type){
			case 0:
				foreground.add(new Sasafras(x,y)); //all of these just add a new object of a specific subclass at the location
				break;
			case 1:
				foreground.add(new CommonMilkweed(x,y));
				break;
			case 2:
				foreground.add(new JapaneseMaple(x,y));
				break;
			case 3:
				foreground.add(new ButterflyBush(x,y));
				break;
			default:
				System.out.println("Only types 0 thru 7 accepted");
				break;
			}
			occupiedSquares[x][y] = true; //marks this square as occupied
			if(toolUsed!=999){
			attemptedx = x; //the attempted click locations are used by a function elsewhere that checks if the planting was successful
			attemptedy = y; //if you click at the wrong time, while things are being drawn, very small window, it wont register so this prevents false cooldown triggers
		}	
			}
		else{
		//	System.out.println("Not a plantable square, is occupied or out of bounds");
		}
			
	}
	
	
	public void addWater(int pathNum){ 
		//this function spawns a water on the roof at one of four squares at start of paths.
		int gradualSize = waterBaseSize + cycles/waterGrowRate; //every 20 cycles water gets linearly stronger
		if (gradualSize > maxWaterSize)
			gradualSize = maxWaterSize; //max size
		switch(pathNum){
		case(0): 
			if(occupiedSquares[9][5] == false) //see if theres one right ahead of it
				foreground.add(new Water(0,8,5,gradualSize)); //east path
		break;
		case(1):
			if(occupiedSquares[7][7] == false) //south path
				foreground.add(new Water(1,7,6,gradualSize)); //spawns a water direction 1, at x = 7, y= 6 with size 20
		break;
		case(2):
			if(occupiedSquares[4][4] == false) //west path
				foreground.add(new Water(2,5,4,gradualSize));
		break;
		case(3):
			if(occupiedSquares[6][2] == false) //north path
				foreground.add(new Water(3,6,3,gradualSize));
		break;
		default:
			break;
		
		}
	}
	
	
	public void decrement_Gutter(int gutter){ 
		//not used probably, score is being used as runoff tolerance and theres a method at the top that does this for that.
		runoff_Tolerance[gutter]--;
	}

	public void setTool(int t){ 
		//used to say which tool needs overlay
		//also sets the description on top right
		toolUsed = t;
	}

	public void updateGrid(){ 
		//just makes sure that the 'this' is right when we want to repaint 
		this.repaint();
	}
	
	public void setOccupiedSquares(boolean tf, int x, int y){
		//this sets a square to either true or false, it's rechecked by another method but this was the older way we did it
		//by itself it doesn't work all the time because things move, need both.
		
		//System.out.println("setOcc given " + tf + " " + x + " " + y + " and position is currently " + occupiedSquares[x][y]);
		occupiedSquares[x][y] = tf;
	//	System.out.println("It is now " + occupiedSquares[x][y]);
	}

	public void advanceTime(){ //this is called every cycle in runme and just increments cycles and decrements cooldowns to represent the passing of time in the code
		cycles ++;
		menu.decrementAllCooldowns();
	}
	
	public void grow_tile(int x, int y){ 
		//this takes an x y location and tries to make the grass grow longer, if its grass or meadow it will go up 1, (to meadow/forest) otherwise it cant grow.
		Random r = new Random();
		switch(intGrid[x][y]){
		case 0 : //0 being grass, change it to meadow (1)
			background[x][y] = new Meadow_Tile(); 
			intGrid[x][y] = 1;
			break;
		case 1: //1 is meadow, grow it to forest(2)
			background[x][y] = new Forest_Tile();
			intGrid[x][y] = 2;
			break;	
		case 2: //forest is cool if whole map is forest it doesn't care and will let it be
			break;
		default: //if it lands on roof or path it will try a new random spot
			//System.out.println("This doesn't grow");
			grow_tile(r.nextInt(numTilesX),r.nextInt(numTilesY));
			break;
		}
	}
	
	public void mow_tile(int x,int y){
		//used by lawnmower to revert a tile to plain grass and kill anything from the foreground there
		
		if(x>= 0 && x<numTilesX && y>= 0 && y<numTilesY){
			if(intGrid[x][y] <3){ //if it's grass meadow or forest not path or roof,
				background[x][y] = new Grass_Tile(); //add a grass there
				intGrid[x][y] = 0; //mark that location on intgrid as being grass
				removeFromForeground(x,y); //remove any objects that are already there
				clearingSplash = true;
				new AePlayWave("mower.wav").start();
			}
			else{
				//System.out.println("Can't mow here");
			}
		}
	}
	
	
	public void removeWater(){
		//this is a separate function from removefromforeground used for waters
		//it goes through all plants and makes them call their absorb function to search around themselves for valid waters
		//if it finds waters it will damage them based on the plant's strength
		boolean playSound = false;
		int savedStrengths[] = new int[100]; //saves the strengths of plants in the order they do damage
		for(int i = 0; i < 100; i++) //initializes and resets that array to 0s, can accomodate up to 100 damages at a time which is overkill
			savedStrengths[i] = 0;
		int numHurt = 0; //number of attacks to distribute
		int thisIndex;
		for(Drawable d: foreground){ //for all of the drawable objects
			thisIndex = d.absorb(this);
				if(thisIndex!=9999 && thisIndex != 0) {
					playSound = true;
					savedStrengths[numHurt]=d.getStrength(); //get the strength of this plant and save it in the array
				//	System.out.println("Added" + d.getStrength() + " to index " + numHurt + " from plant " + d.getName());
					numHurt++; //add 1 to number of attacks
					//System.out.println(foreground.get(thisIndex).getName());
					killedWaters.add(foreground.get(thisIndex));}	//instead of removing it here flag it to remove it later
		}
		numHurt = 0;
		if(killedWaters.size() > 0){ //doing it this way fixes the concurrent modification error
			for(Drawable d: killedWaters){ //for all flagged waters
				d.takeDamage(savedStrengths[numHurt]);  //make the plant take the appropriate amount of damage, from the corresponding plant's str
			//	System.out.println("Did " + savedStrengths[numHurt] + " damage");
				numHurt++; //move on to next index so next plnt takes correct dmg
				if(d.getSize() <= 0) //if its hp is 0 or less then remove it
					foreground.remove(d);	 //its safe to do this here because were not iterating over foreground, were iterating over killedwaters
						
			}
		}
		killedWaters.clear(); //clear out indexes of removed ones
		if(playSound) //if anything is sucking water play the sound once, instead of having many instances of it at the same time
			new AePlayWave("suckingSound.wav").start();
	}
		
		
	public void moveAllWaters(){
		//this makes all waters move intelligently and at once
		
		populateGrids(); //refreshes locations of everything in logic grids
		ArrayList<Drawable> killedWaters = new ArrayList<Drawable>(20); //creates an arraylist to put ones that reach the end of paths
		int thisIndex;
		for(Drawable d: foreground){ //for all of the drawable objects
			thisIndex = d.move(this); //try to make it move- only waters can actually move but all have the method, others do nothing- also note that it returns an int
			if(thisIndex!=9999)//it will return 9999 from all plants and living waters
				killedWaters.add(foreground.get(thisIndex));	//instead of removing it here flag it to remove it later
		}
		if(killedWaters.size() > 0){ //doing it this way fixes the concurrent modification error
		//	System.out.println("There are some waters to be removed");
			for(Drawable d: killedWaters){ //for all flagged waters
		//		System.out.println("That water should be removed");
				foreground.remove(d);	 
			}
		}
		killedWaters.clear(); //clear out indexes of removed ones
	}
	
	public Grid(){
		//constructor
			grower = new growThread();
			
			try{
				introdesc = ImageIO.read(new File("img/Images/introDesc.png"));
				defaultdesc = ImageIO.read(new File("img/Images/noToolDesc.png"));
				waterMeterHeader = ImageIO.read(new File("img/Images/water_meter_header.png")); //we load all of these pictures only one time in the game's runtime
				waterMeterEmpty = ImageIO.read(new File("img/Images/water_meter_empty.png"));
				waterMeterFull = ImageIO.read(new File("img/Images/water_meter_full.png"));
				//waterMeterWave = ImageIO.read(new File("img/Images/water_meter_wave.png"));
				noLayover = ImageIO.read(new File("img/Images/noLayover.png"));
				Layover = ImageIO.read(new File("img/Images/selectLayover.png"));
				scoreShower = ImageIO.read(new File("img/Images/Placeholders/scoreShower.png"));				
				house = ImageIO.read(new File("img/Images/house-topdown.png"));
				
				//womanOnRight = ImageIO.read(new File("img/Images/guyonright.png"));
				//womanOnRight = ImageIO.read(new File("img/Images/guyonrightB.png"));
				startScreen = ImageIO.read(new File("img/Images/StartScreen.png"));
				endScreenFail = ImageIO.read(new File("img/Images/finalScreenFail.png"));
				endScreenBronze = ImageIO.read(new File("img/Images/finalScreenBronze.png"));
				endScreenSilver = ImageIO.read(new File("img/Images/finalScreenSilver.png"));
				endScreenGold = ImageIO.read(new File("img/Images/finalScreenGold.png"));
				happyDrain = ImageIO.read(new File("img/Images/happyDrain.png"));
				sadDrain = ImageIO.read(new File("img/Images/sadDrain.png"));
				happyDrainEast = ImageIO.read(new File("img/Images/happyDrain-east.png"));
				happyDrainWest = ImageIO.read(new File("img/Images/happyDrain-west.png"));
				happyDrainNorth = ImageIO.read(new File("img/Images/happyDrain-north.png"));
				happyDrainSouth = ImageIO.read(new File("img/Images/happyDrain-south.png"));
				sadDrainEast = ImageIO.read(new File("img/Images/sadDrain-east.png"));
				sadDrainWest = ImageIO.read(new File("img/Images/sadDrain-west.png"));
				sadDrainNorth = ImageIO.read(new File("img/Images/sadDrain-north.png"));
				sadDrainSouth = ImageIO.read(new File("img/Images/sadDrain-south.png"));
				butterflyDisplayBackground = ImageIO.read(new File("img/Images/butterflyBackground.png"));
				smallButterfly = ImageIO.read(new File("img/Images/smallButterfly.png"));
				LawnmowerDescription = ImageIO.read(new File("img/Images/lawnmowerDesc.png"));
				JapaneseMapleDescription= ImageIO.read(new File("img/Images/japanesemapleDesc.png"));
				SasafrasDescription= ImageIO.read(new File("img/Images/sasafrasDesc.png"));
				ButterflyBushDescription= ImageIO.read(new File("img/Images/butterflybushDesc.png"));
				CommonMilkweedDescription= ImageIO.read(new File("img/Images/milkweedDesc.png"));
				cooldownOverlayFull = ImageIO.read(new File("img/Images/cooldown-full.png"));
				cooldownOverlayHalf = ImageIO.read(new File("img/Images/cooldown-half.png"));
				cooldownOverlayQuarter = ImageIO.read(new File("img/Images/cooldown-one-quarter.png"));
				cooldownOverlay3Quarters = ImageIO.read(new File("img/Images/cooldown-three-quarters.png"));
				toolsLabel = ImageIO.read(new File("img/Images/toolsLabel.png"));
				tooltipPrototype = ImageIO.read(new File("img/Images/tooltip.png"));
				thirdDescriptionScreen = ImageIO.read(new File("img/Images/DescriptionScreenThree.png"));
				secondDescriptionScreen = ImageIO.read(new File("img/Images/SecondTutorialScreen2.png")); //changed "Invasive" to where the plants are native to. something he said to do 
				firstDescriptionScreen = ImageIO.read(new File("img/Images/updatedFirstDescScreen.png"));
				goldImage = ImageIO.read(new File("img/Images/goldRating.png"));
				silverImage = ImageIO.read(new File("img/Images/silverRating.png"));
				bronzeImage = ImageIO.read(new File("img/Images/bronzeRating.png"));
				failImage = ImageIO.read(new File("img/Images/failRating.png"));
				mouseMower = ImageIO.read(new File("img/Images/PlaceHolders/mouseMower.png"));
				learnMoreScreen = ImageIO.read(new File("img/Images/learnMore2.png"));
				
						
				for(int z = 0; z < 7;z++){
					String filename = "img/Images/water_meter_wave"+(z+1)+".png";
					waterMeterImages[z] = ImageIO.read(new File(filename));
				}
				
				for (int y = 0; y < 9; y++){
					String filename = "img/Images/tooltip" + y + ".png";
					tooltips[y] = ImageIO.read(new File(filename));
				}
				for(int x=0; x < mouseMowerImages.length; x++){
					String filename = "img/Images/Placeholders/mouse_mower" + (x+1) + ".png";;
					mouseMowerImages[x] = ImageIO.read(new File(filename));;
				}
				
				//set pictures for rain animation
				for(int x=0; x < rains.length; x++){
					String filename;
					if((x+1)<10)
						 filename = "img/Images/Rain/Rain000" + (x+1) + ".png";
					else
						 filename = "img/Images/Rain/Rain00" + (x+1) + ".png";
					rains[x] = ImageIO.read(new File(filename));
				}
				
			} 
			
			catch (IOException e) {
				e.printStackTrace();
			}
			
			//setting up grid from default hard coded way at the top, note that those are columns
			for(int i = 0; i<numTilesX; i++){
				for(int j= 0; j<numTilesY; j++){
					//System.out.println("Made it to " + i + " " + j + "  " + intGrid[i][j]);
				
					switch(intGrid[i][j]){
						case 0 : //self explanitory what these do but note the case numbers, they match up to code in intgrid
							background[i][j] = new Grass_Tile();
							break;
						case 1:
							background[i][j] = new Meadow_Tile();
							break;	
						case 2:
							background[i][j] = new Forest_Tile();
							break;
						case 3:
							background[i][j] = new Queue_Of_Care(0); //vertical tile
							break;
						case 4:
							background[i][j] = new Roof_Tile();
							break;
						case 5:
							background[i][j] = new Queue_Of_Care(1); //horizontal tile
							break;
						case 6:
							background[i][j] = new Queue_Of_Care(2); //NE corner tile 
							break;
						case 7:
							background[i][j] = new Queue_Of_Care(3); //NW corner tile
							break;
						case 8:
							background[i][j] = new Queue_Of_Care(4); //SE corner tile 
							break;
						case 9:
							background[i][j] = new Queue_Of_Care(5); //SW corner tile 
							break;
						default:
							background[i][j] = new Grass_Tile();
							break;
					}

				}
			}
			resetGrids();
 	
	}
	
	//ends start screen
	public void splash(){ 
		//splash screen, this is different from water's method called splash.
		if(starting){
			starting = false; //used to be extra explicit with when things are being shown
			explaining = true;
		}
		else if(explaining){ //first desc screen
			explaining = false;
			stillExplaining = true;
		}
		else if(stillExplaining){ //second
			stillExplaining = false;
			stillStillExplaining = true;
		}
		else if(stillStillExplaining){ //third
			stillStillExplaining = false;
			clearingSplash = true;
		}
		if(ending){
			learningMore = true;
			
		}
	}
	
	public void stopExplaining(){
		explaining = true; 
	}
	public void stopExplainingAgain(){
		stillExplaining = true;
	}
	//goes to end game screen
	public void goToEndScreen(){
		ending = true;
	}


	//MOUSE LISTENER METHODS
	//Didn't use. Have separate mouse listener
	//if we had tool tips for when you mouse over things this is where that would be done i think
	
	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	
	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		
	}

}
