import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Grid_Mouse_Listener implements MouseListener,MouseMotionListener {

	int x = 0;
	int y = 0;
	int operation = 9;
	int readyToOutput = 0;
	Grid gameGrid;
	Menu menu;
	boolean mowerClicked = false;
	
	
	public Grid_Mouse_Listener(Grid g, Menu m){
		gameGrid = g;
		menu = m;
	}
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		gameGrid.splash();
		int actualX = e.getXOnScreen();
		int actualY = e.getYOnScreen();
		System.out.println("Actual click at " + actualX + " " + actualY);
		int gridX = actualX -18;  //
		int gridY = actualY -171;

		//these offsets need to be played with theyre not exactly right now, clicking top left quadrants of tiles works best.
		int j = (gridY-(gridY % 64))/64;
		int i = (gridX-(gridX % 64))/64;

		if(actualY >= 45 && actualY <=170 ){ //the click is within the area that the menu take up
			new AePlayWave("menuSelect.wav").start();
			if(actualX >50 && actualX <=185){
				//System.out.println("Menu button 1 hit");
				if(menu.getRemCooldownFor(0)==0){
					gameGrid.setAble(true);
					gameGrid.lastToolUsed = 0;
					operation = 0;
					gameGrid.setMowerClicked(false);
				}
				else{
					System.out.println("I'm on CD");
				}
			}
				
			else if(actualX >185 && actualX <=315){
				//System.out.println("Menu button 2 hit");
				if(menu.getRemCooldownFor(1)==0){
					gameGrid.setAble(true);
					gameGrid.lastToolUsed = 1;
					operation = 1;
					gameGrid.setMowerClicked(false);
					}
				else{
					System.out.println("I'm on CD");
				}
			}

			else if(actualX >315 && actualX <=445){
				//System.out.println("Menu button 3 hit");
				if(menu.getRemCooldownFor(2)==0){
					gameGrid.setAble(true);
					gameGrid.lastToolUsed=2;
					operation = 2;
					gameGrid.setMowerClicked(false);
				}
				else{
					System.out.println("I'm on CD");
				}
			}

			else if(actualX >445 && actualX <=565){
				//System.out.println("Menu button 4 hit");
				if(menu.getRemCooldownFor(3)==0){
					gameGrid.setAble(true);
					gameGrid.lastToolUsed=3;
					operation = 3;
					gameGrid.setMowerClicked(false);
				}
				else{
					System.out.println("I'm on CD");
				}
			}
			else if(actualX >565 && actualX <=690){
				//System.out.println("Menu button 5 hit");
				if(menu.getRemCooldownFor(4)==0) {
					gameGrid.setAble(true);
					gameGrid.lastToolUsed = 4;
					operation = 4;
					gameGrid.setMowerClicked(true);
				}
				else{
					System.out.println("I'm on CD");
				}
			}
		//	System.out.println("DEBUG:: gamegrid able: " + gameGrid.ableToPlant + " operation " + operation);
		}
		else if(actualX >= 18 && actualX <= 910 && actualY >= 170 && actualY <= 809){
			//clicked somewhere on grid 
			readyToOutput = 1;
			x = i;
			y = j;
	//		System.out.println("DEBUG:: gamegrid able: " + gameGrid.ableToPlant + " operation " + operation);
		}
		else{ //clicked somwhere else, neither on menu or grid
			//x = 99;
			//y = 99; //when called its checked for these vals meaning bad click
			//operation = 9; //9 is no operation
			new AePlayWave("selectionBeep.wav").start();
		}
		
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
	
	public int[] talkToGrid(Grid g){
		int[] info = new int[4];
		info[0] = x;
		info[1] = y;
		info[2] = operation;
		info[3] = readyToOutput;
		
		return info;
	}
	public void confirmDone(){
	//	System.out.println("Output bit reset");
		readyToOutput = 0;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int actualX = e.getXOnScreen();
		int actualY = e.getYOnScreen();
		gameGrid.setMouseLoc(actualX, actualY);
		//System.out.println("Actual click at " + actualX + " " + actualY);
		if(actualY > 170 && actualX > 17){// its low enough to be over the grid
			int gridX = actualX -17;  //
			int gridY = actualY -170; 
			
			//these offsets need to be played with theyre not exactly right now, clicking top left quadrants of tiles works best.
			int j = (gridY-(gridY % 64))/64;
			int i = (gridX-(gridX % 64))/64;
			// System.out.println("("+i+","+j+")"); debug to get grid location
			if(i < 14 && j < 10 && j >=0 && i >= 0){
			
			if (!gameGrid.foreground.isEmpty() && gameGrid.getThingAt(i, j) != null){
				System.out.println(gameGrid.getThingAt(i, j).getName());
				if((gameGrid.getThingAt(i, j).getName()).equals("Sasafras"))
					gameGrid.setHover(true,0);
				else if((gameGrid.getThingAt(i, j).getName()).equals("Common Milkweed"))
					gameGrid.setHover(true,1);
				else if((gameGrid.getThingAt(i, j).getName()).equals("Japanese Maple"))
					gameGrid.setHover(true,2);
				else if((gameGrid.getThingAt(i, j).getName()).equals("Butterfly Bush"))
					gameGrid.setHover(true,3);
				else if((gameGrid.getThingAt(i, j).getName()).equals("Water"))
					gameGrid.setHover(true,4);
			
			}
			
			else if(gameGrid.getBackground(i,j) != null){
				if(gameGrid.getBackground(i,j).getName().equals("Meadow")) 
					gameGrid.setHover(true,5);
				else if(gameGrid.getBackground(i,j).getName().equals("Forest"))
					gameGrid.setHover(true,6);
				else
					gameGrid.setHover(false,99);
				
				}
			else if((i == 0 && j == 3 ) || (i == 9 && j == 0) || (i == 13 && j == 3) || (i == 12 && j == 9)) //its a drain
				gameGrid.setHover(true,7);
				
			
			else
				gameGrid.setHover(false,99);
			}
		}
	else if(actualY < 170 && actualX > 45){
		gameGrid.setMowerClicked(false);

		if(actualX >45 && actualX <=185){
			//System.out.println("Menu button 1 hit");
			gameGrid.setHover(true,8);
			gameGrid.setMowerClicked(false);
		}
			
		else if(actualX >185 && actualX <=315){
			//System.out.println("Menu button 2 hit");
			gameGrid.setHover(true,9);
			gameGrid.setMowerClicked(false);
		}

		else if(actualX >315 && actualX <=445){
			//System.out.println("Menu button 3 hit");
			gameGrid.setHover(true,10);
			gameGrid.setMowerClicked(false);
		}

		else if(actualX >445 && actualX <=565){
			//System.out.println("Menu button 4 hit");
			gameGrid.setHover(true,11);
			gameGrid.setMowerClicked(false);
		}
		else if(actualX >565 && actualX <=690){
			//System.out.println("Menu button 5 hit");
			gameGrid.setHover(true,12);
			gameGrid.setMowerClicked(false);
		}
		
	}
	else
		gameGrid.setHover(false,99);
		gameGrid.setMowerClicked(false);

		}
}