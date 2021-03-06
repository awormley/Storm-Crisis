import java.awt.image.BufferedImage;


public abstract class Plant extends Drawable{
	
	private String[] description;
	 int strength;
	private int range;
	private int cost;
	private Grid_Type type;
	private BufferedImage[] idle;
	private BufferedImage[] eat;
	private int instrument; //0,1,2,3 we'll see
	public boolean eating;
	
	public Plant(int x, int y){
		super(x,y);
	}
	
	public void setDesc(String[] desc){
		description = desc;
	}
	public String[] getDesc(){
		return description;
	}
	public int getStrength(){ 
		return strength+size;// invasives do 1-3 damage, natives do 3-5, depending on what type of terrain theyre on
		//an invasive on forest is just as effective as a native on grass
	}
	public int getRange(){
		return range;
	}
	public BufferedImage getIdleImg(){
		return idle[0];  //for now, just display 1st one in array
	}
	public BufferedImage getEatImg(){
		return eat[0];  //for now, just display 1st one in array
	}
	@Override
	public BufferedImage getPicture(){ 
		if (eating){
			return getEatImg();
		}
		else{
			return getIdleImg();
		}
	}
	public int getInstrument(){
		//Not used yet
		return instrument;
	}
	
	public int getSize(){
		return size;
	}
	public void setSize(int s){
		size = s;
	}
	
	
	
	public int absorb(Grid g){
		//this function makes the plant search around for water enemies to damage then signals which one should be taking damage
		int loc[] = getLoc(); //get plants location
		if(checkSquare(-1,0,g)) //check left
			return(g.foreground.indexOf(g.getThingAt(loc[0]-1,loc[1]))); //send the index of the water it sees in the arraylist of foreground to the function that applies damage
		else if(checkSquare(1,0,g)) //check right
			return(g.foreground.indexOf(g.getThingAt(loc[0]+1,loc[1]))); //get thing at returns the object based on an x y coord on grid
		else if(checkSquare(0,-1,g)) //check up
			return(g.foreground.indexOf(g.getThingAt(loc[0],loc[1]-1))); //foreground is all of the drawables, send the index so you dont have concurrency errors
		else if(checkSquare(0,1,g)) //check down
			return(g.foreground.indexOf(g.getThingAt(loc[0],loc[1]+1))); 			
		else
			return 9999;
		

	}
	
	//still working on this
	private boolean checkSquare(int dx, int dy, Grid g){
		//this function will tell if a square is in range and has a water. returns true if valid, false if invalid. 
		
		int[] loc = getLoc(); //plant x and y loc
		int[][] grid = g.getIntGrid();
		boolean occupied[][] = g.getOccupiedSquares();
		int maxX = g.getNumTilesX();
		int maxY = g.getNumTilesY();
		g.populateGrids(); //this updates locations of everything to the boolean and foreground grids, possible performance hit cause at larger scales
		//check all plants that if nearby grid loc is path and it is occupied (then water)
		if((loc[0] + dx) < maxX && (loc[0] + dx )>= 0 && (loc[1] + dy) < maxY && (loc[1] + dy) >=0){ //checks its in bounds
			if(g.foregroundGrid[loc[0]+dx][loc[1]+dy] == 9){ // checks that its water
				//	System.out.println("Trying to kill a water"); 
				//	System.out.println("found water at (" + locX + ", " + locY + ")");
						eating = true;
						return true; 
				}
			
		}
		eating = false;
		return false;
	}
	
	public void setup(int str, int ran, int cos, BufferedImage[] id, BufferedImage[] ea){
		//sets up the values of a plant easier than writing this many times
		//System.out.println("Setting up!");
		strength = str; //the amount of damage it does to a water's size per absorb
		range = ran; //number of squares away from itself it checks for water
		cost = cos; //the delay before you can plant another one
		idle = id; //pictures for when not drinking
		eat = ea; //pictures for when drinking
	}
	
}
