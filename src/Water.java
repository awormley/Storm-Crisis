import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
public class Water extends Drawable{
	
	//object class for every water enemy, has functions to move itself along board and change things on grid to tell it has moved
	
	private int size;
	private int direction; //0 east 1 south 2 west 3 north- matches up to one of four images loaded 
	private boolean takingDamage = false;
	BufferedImage directionImages[] = new BufferedImage[4];
	BufferedImage smallDirectionImages[] = new BufferedImage[4];
	BufferedImage largeDirectionImages[] = new BufferedImage[4];
	BufferedImage damagedirectionImages[] = new BufferedImage[4];
	BufferedImage splashImage; 
	boolean dead = false;
	boolean moved = false;
	
	public Water(int dir, int x, int y, int s){
		super(x,y);
		setName("Water");
		inttype = 9;
		
		size = s; //default size subject to change
		direction = dir; //0 E, 1 S, 2 W, 3 N
		try {
			splashImage = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemySplash.png"));
			directionImages[0] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyRight2.png"));
			directionImages[1] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyDown2.png"));
			directionImages[2] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyLeft2.png"));
			directionImages[3] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyUp2.png"));
			smallDirectionImages[0] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyRight1.png"));
			smallDirectionImages[1] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyDown1.png"));
			smallDirectionImages[2] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyLeft1.png"));
			smallDirectionImages[3] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyUp1.png"));
			largeDirectionImages[0] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyRight3.png"));
			largeDirectionImages[1] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyDown3.png"));
			largeDirectionImages[2] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyLeft3.png"));
			largeDirectionImages[3] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyUp3.png"));
			
			
			
			
			damagedirectionImages[0] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyRight2.png"));
			damagedirectionImages[1] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyDown2.png"));
			damagedirectionImages[2] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyLeft2.png"));
			damagedirectionImages[3] = ImageIO.read(new File("img/Images/Placeholders/WaterEnemy/WaterEnemyUp2.png"));
		} 
		catch (IOException e) {
			System.out.println("Failed to load water images");
			e.printStackTrace();
		}

	}
	public void takeDamage(int amount){
		takingDamage = true;
		size -= amount;
	}
	public int getSize(){
		return size;
	}
	
	public int move(Grid g){
		takingDamage = false;
		moved = false;
		if(!dead){ //don't move if dead
		int prevLoc[] = getLoc(); //save location before moving and have another for after the move
		int loc[] = getLoc();
		if(direction == 0){ //if going right
//			System.out.println("Currently going right");
			if(checkSquare(1,0,g)){ //check square to right
//				System.out.println("Can go straight!");
				setLoc(loc[0]+1,loc[1],g);
			}
			else if(checkSquare(0,1,g)){ //check square below
//				System.out.println("Can go down!");
				setLoc(loc[0],loc[1]+1,g);
				direction = 1;}
			else if(checkSquare(0,-1,g)) {//check square above
//				System.out.println("Can go up!");
				setLoc(loc[0],loc[1]-1,g);
				direction = 3;}
		}	
		else if(direction == 1){ //if going south
//			System.out.println("Currently going down");
			if(checkSquare(0,1,g)){ //check square below
//				System.out.println("Can go straight!");
				setLoc(loc[0],loc[1]+1,g);
			}
			else if(checkSquare(-1,0,g)){ //check square to stage left
	//			System.out.println("Can go left!");
				setLoc(loc[0]-1,loc[1],g);
				direction = 2;}
			else if(checkSquare(1,0,g)) {//check square to stage right
	//			System.out.println("Can go right!");
				setLoc(loc[0]+1,loc[1],g);
				direction = 0;}
		}	
		else if(direction == 2){ //if going west
	//		System.out.println("Currently going left");
			if(checkSquare(-1,0,g)){ //check square to left
//				System.out.println("Can go straight!");
				setLoc(loc[0]-1,loc[1],g);
			}
			else if(checkSquare(0,1,g)){ //check square below
//				System.out.println("Can go down!");
				setLoc(loc[0],loc[1]+1,g);
				direction = 1;}
			else if(checkSquare(0,-1,g)) {//check square above
	//			System.out.println("Can go up!");
				setLoc(loc[0],loc[1]-1,g);
				direction = 3;}
		}	
		else if(direction == 3){ //if going north
//			System.out.println("Currently going up");
			if(checkSquare(0,-1,g)){ //check square above
	//			System.out.println("Can go straight!");
				setLoc(loc[0],loc[1]-1,g);
			}
			else if(checkSquare(-1,0,g)){ //check square to stage left
	//			System.out.println("Can go left!");
				setLoc(loc[0]-1,loc[1],g);
				direction = 2;}
			else if(checkSquare(1,0,g)) {//check square to stage right
//				System.out.println("Can go right!");
				setLoc(loc[0]+1,loc[1],g);
				direction = 0;}
		}	
		
		loc = getLoc(); //refresh location
//		System.out.println("Loc is" + loc[0] + " " + loc[1] + " prevloc is " + prevLoc[0] + " " + prevLoc[1]);
	
		if((loc[0] == 0 && loc[1] == 3 ) || (loc[0] == 9 && loc[1] == 0) || (loc[0] == 13 && loc[1] == 3) || (loc[0] == 12 && loc[1] == 9)){
			splash(g); //if it's on the end of a path then change animation to a puddle and mark it for death
		}
		
		return 9999; //this indicates it's not to be removed
		}
		
		else{ //if marked as dead,
			return(g.foreground.indexOf(this)); //returns the index if it should die
		}
		
		
	}
	private boolean checkSquare(int dx, int dy, Grid g){ //this is used by the move function to let it see if a square is a candidate to be moved to
		//dx and dy are change in x and change in y- usually -1, 0 or 1. Potentially we could have fast waters that skip 2 or more squares ahead.
		//Some could even jump over bad squares in that direction, just giving it a 5 would do that, to make it move fast but only with a clear path youd need to check all between with this
		int loc[] = getLoc(); //grab its location

		if((loc[0] + dx) < g.getNumTilesX() && (loc[0] + dx )>-1 && (loc[1] + dy) < g.getNumTilesY() && (loc[1] + dy) >-1){ //check that the square it wants to move to is in bounds before passing that to other checks
			
			if(g.intGrid[loc[0]+dx][loc[1]+dy] == 3 || g.intGrid[loc[0]+dx][loc[1]+dy] >=5){ //if its path
			//	System.out.println("Square is a path");
				if(g.checkOccupied(loc[0]+dx,loc[1]+dy) == false){ //and nothing is there
			//		System.out.println("Square is empty");
					
					return true; //and allow it to move
				}
			}
		}
		
		return false; //if fails any of the above then it can't go there
	}
	
	public void splash(Grid g){ //this function lets it change its picture to a puddle
		if(!g.muted)
			new AePlayWave("splash-01.wav").start();
		int loc[] = getLoc(); 
		g.setDrainSad(loc[0],loc[1]);
		g.setOccupiedSquares(false, loc[0], loc[1]); //mark its square as available to be moved to
		dead = true; //mark it as dead so the next time its moved it will be taken off board
		g.change_Score(-1); //decrement the score on grid
	}
	public BufferedImage getPicture(){
		if (!dead) //this is how we show the different images for the water enemies automatically
			if(getSize()<5)
				return smallDirectionImages[direction];
			else if(getSize()<15)
				return directionImages[direction];
			else
				return largeDirectionImages[direction];
		
		//else if(!dead)
			//return damagedirectionImages[direction];
		else
			return splashImage;
	}
	
}