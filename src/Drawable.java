import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public abstract class Drawable {
	private int xloc;
	private int yloc;
	int size;
	public BufferedImage picture;
	private String name;
	int inttype = 0;

	public Drawable(int x,int y){
		xloc = x;
		yloc = y;

	}
	public void setLoc(int x, int y, Grid g){
		if(xloc != x || yloc != y){	//if new location is different from current
			g.setOccupiedSquares(true, x, y); //and current location as occupied
			g.setOccupiedSquares(false, xloc, yloc); //mark its previous location as unoccupied
		//	System.out.println("Moved from " + xloc + "," + yloc + " to " + x + "," + y);
			xloc = x;
			yloc = y;
			
		}
		//otherwise you dont have to change anything
	}
	
	//public get and set methods
	public int[] getLoc(){ //returns an array where index of 0 is x pos and index of 1 is y pos
		int[] location = {xloc,yloc};
		return location;
	}
	public String getName(){
		return name;
	}
	public int getType(){
		return inttype;
	}
	public void setName(String n){
		name = n;
	}
	public int getStrength(){
		return 0;
	}
	
	public void splash(){
		//not sure if this is actually used?
	}
	public BufferedImage getPicture(){
		return picture;
	}
	public int move(Grid g){
		return 9999;
	}
	public int absorb(Grid g){
		return 9999;
	}
	public void takeDamage(int amt){
		
	}
	public int getSize(){
		return 1;
	}
	public void setSize(int i) {
		size = i;
	}
}
