import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.swing.JFrame;

public class RunMe  {
	
	public static void main(String[] args){
		Calendar cal;
		long startTime = 0; //time when game starts
		long currentTime = 0; //time that gets updated, only used by runme to see performance, not used by game logic
		long deltaT = 0; //time passed since checkpoint
		JFrame gameWindow = new JFrame(); //the only jframe
		Grid gameGrid = new Grid(); //the only grid, controls and shows everything
		Grid_Mouse_Listener listener = new Grid_Mouse_Listener(gameGrid,gameGrid.menu); //the only listener, registers mouse clicks and tells location
		gameGrid.addMouseListener(listener); 
		gameGrid.addMouseMotionListener(listener);
		int baseDelay = 150; //150 millisecond delay built into game between cycles, used as a thread sleep  
		boolean youwin = false; //boolean for if you meet the win condition of 1000 butterflies
		int savedLast = 99; //the last tool you used, 99 indicates you dont have one picked yet

		gameWindow.getContentPane().add(gameGrid); //add the grid to the frame
		gameWindow.setBackground(Color.gray); //you shouldnt ever actually see the background
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //putting a comment here
		gameWindow.setSize(1190, 825); //magic numbers xy, need 1344 min to have 20x20 of the 64x64 tiles though
		gameWindow.setVisible(true); 
		int stage = 0;
	
		showIntro(); //say hello for debug purposes
		Random r = new Random(); 
		//gameGrid.addWater(0); 
		if(!gameGrid.muted)
			new AePlayWave("storm-rainthunder5.wav").start();
		while (gameGrid.get_Score()>0 && gameGrid.getNumButterflies() <1000 ){ //gameplay loop. while water meter not full and butterflies goal not met
			
			cal = Calendar.getInstance(); //these three check performance, this function can be removed from final version to reduce lag
	
			currentTime = cal.getTimeInMillis();
			deltaT = currentTime - startTime;
			
			
			//if(gameGrid.cycles % 100 == 0)
			//	System.out.println("Cycles: " + gameGrid.cycles + " Ideal Time (s): " + ((gameGrid.cycles * baseDelay)/1000) + " Actual time (s) : " + deltaT/1000);
			
			try {
				//System.out.println("Delay is " + (200-gameGrid.foreground.size()) ); //there are a max of 124 objects making minimum delay 76 ms
    			Thread.sleep(baseDelay); //controls game speed, 150 is the current value set at the top and it seems about right
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
			
			if(gameGrid.checkStarting()){ //only one time
				
				cal = Calendar.getInstance();
				startTime = cal.getTimeInMillis(); //save time when game starts
			}
			//on all of these testing lines the way we're spawning stuff via random is not final, just for now
			if(!gameGrid.checkStarting()){
				gameGrid.advanceTime(); //increment cycles
			//calls grow on a random tile- grow function deals with invalid tiles itself
			if(gameGrid.getCycles() % gameGrid.getGrassGrowRate() == 0)	
				gameGrid.grow_tile(r.nextInt(14), r.nextInt(10)); //14 and 10 are the x and y size of the game grid
		
			//if(r.nextInt(invasiveRate) == 0) //spawns invasives randomly when tiles are grown
			
			//right now with the +2 on the next line it only spawns invasives, without it it would spawn natives and invasives.
			//invasives are set to spawn at 1/5 the rate that tiles grow
			
			if(gameGrid.getCycles() % gameGrid.invasiveRate == 0) //using modulus to call this every n cycles
				gameGrid.addPlantNoSound(r.nextInt(2)+2,r.nextInt(14),r.nextInt(10)); //spawn a random invasive on a random spot
			//if(r.nextInt(gameGrid.waterSpawnRate) == 0)//calls addwater on one of the four spawn positions
			
			if(gameGrid.getCycles()>50){//wait 50 cycles before starting the storm
			if(gameGrid.getCycles() % gameGrid.getWaterSpawnRate()-stage == 0){
				gameGrid.draw_text_grid(); //if the debug boolean in grid is coded to true it will give a console output of the foreground, background and boolean grids
				if(gameGrid.getCycles() <200){ //for first 200 cycles only send water down right path
					gameGrid.addWater(0);
				}
				else if(gameGrid.getCycles() < 400){ //for next 200 cycles only send water down right and bottom paths
					stage =1; //increase water spawn rate
					gameGrid.addWater(r.nextInt(2));
				}
				else if(gameGrid.getCycles() < 800){ //for next 400 cycles send water down every path but the top
					stage = 2; //increase water spawn rate
					gameGrid.addWater(r.nextInt(3));
				}
				else { //for any cycle after 800 send water down every path
					stage = 3; //increase water spawn rate
					gameGrid.addWater(r.nextInt(4));
				}
			}
			}
			
		//	if(r.nextInt(gameGrid.waterMoveRate) == 0){   //this moves all of the waters and checks if they need to be removed
			if(gameGrid.getCycles() % gameGrid.getWaterMoveRate()== 0){
				gameGrid.resetDrainStates(); //make all the drains happy
				gameGrid.moveAllWaters(); //move all the waters- also does other stuff like remove them and make drains sad
				
			}
			
			//to remove water if plant located near water
			if(gameGrid.getCycles() % gameGrid.getWaterMoveRate() == 0){
				gameGrid.removeWater(); //remove water function damages water most of the time, removes if their hp is 0. name isnt great.
			}
			
			//to add butterflies to dynamic count on menu
			//currently hardcoded animal spawn rate, can change later
			if(gameGrid.getCycles() % 20 == 0){
				gameGrid.addButterfly(); //every 20 cycles you get awarded butterflies based on the number of natives
			}
			
			
			int[] info = listener.talkToGrid(gameGrid);
			if(info[2] != 9 ){ //if operation isnt 9, where 9 indicates that no tool has been changed
				gameGrid.setTool(info[2]);
					if(info[1] != 99 && info[2] != 99 && info[3] == 1){ //checks if you clicked on the menu and that your state allows changing 
						//System.out.println("Tool set to " + info[2]);
						savedLast = info[2];
						switch(info[2]){
							case(0):
								gameGrid.addPlant(info[2],info[0],info[1]); //plants fern on square clicked
							//	System.out.println("Planting type " + info[2] + " at " + info[0] + " " + info[1]);
							break;
							case(1):
								gameGrid.addPlant(info[2],info[0],info[1]); //plants weed on square clicked
						//		System.out.println("Planting type " + info[2] + " at " + info[0] + " " + info[1]);
							break;
							case(2):
								gameGrid.addPlant(info[2],info[0],info[1]); //plants red plant on square
							//	System.out.println("Planting type " + info[2] + " at " + info[0] + " " + info[1]);	
								break;
							case(3):
								gameGrid.addPlant(info[2],info[0],info[1]); //plants yellow plant on square
							//	System.out.println("Planting type " + info[2] + " at " + info[0] + " " + info[1]);
							break;
							case(4):
								gameGrid.mow_tile(info[0],info[1]); //mows square
							//	System.out.println("Mowing tile " + info[0] + " " + info[1]);
						    break;
						}
						listener.confirmDone(); //tells listener to change the int that says it can do the above check
					}
				}
			}
			
			gameGrid.updateGrid();  //redraw
			gameGrid.checkIfPlantingWorked(savedLast); //make sure your planting registered before using the cooldown
			gameGrid.setPrev();
		}
		
		if(gameGrid.getNumButterflies() >=1000){ //if you meet the win condition then set the win boolean to true
			youwin=true;
			System.out.println("YOU WIN!"); 
		}
		System.out.println("Time lost unintentionally: " + (deltaT-(gameGrid.cycles * baseDelay))/1000); //this is time lost due to lag, actual change between start and end compared to number of cycles times the base delay
		
		showOutro(); //say goodbye
		while(!gameGrid.learningMore){
			gameGrid.goToEndScreen(); //draw final splash screen
			gameGrid.updateGrid();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(gameGrid.learningMore){
		gameGrid.updateGrid();
		gameGrid.removeMouseListener(listener);
		gameGrid.removeMouseMotionListener(listener);
		}
		gameGrid.updateGrid();
	}
	
	//for debugging purposes
	private static void showIntro(){
		System.out.println("Welcome");
	}
	public static void showOutro(){
		System.out.println("Game over");
	}
	
}
