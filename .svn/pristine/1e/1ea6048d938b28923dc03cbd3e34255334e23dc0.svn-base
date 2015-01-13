import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.awt.Component;
import javax.imageio.ImageIO;


public class Queue_Of_Care extends Grid_Type{
	/******Queue/pathing ideas
	 *   my current plan to get away from hard-coded paths and relying upon intGrid
	 *   is to make it so that each Path tile (under the Queue_Of_Care) class does 3 things:
	 *   --will respond t/f if the square is already occupied
	 *   --will tell water where to go next, instead of trying to search for a new path
	 *   --have a different image for each
	 *  
	 *    the idea is to have each path tile "point" to where the water needs to go, as opposed to
	 *    needing to mess w/ int grid
	 */
	boolean occupied = false;
	public Queue_Of_Care(int direction){
		//0 is vertical 1 is horizontal 3 is corner
	
		String file_location =  new String();
		switch(direction) //no need for nested ifs, limited # of possibilities --ajw
		{
			case 0:
				file_location = "img/Images/PathVertical.png";	
				name = "Path_Vert";
				break;
			case 1: 
				file_location = "img/Images/PathHorizontal.png";
				name = "Path_Hort";
				break;
			case 2:
				file_location = "img/Images/PathCornerNE.png";
				name = "Path_Corner";
				break;
			case 3:
				file_location = "img/Images/PathCornerNW.png";
				name = "Path_Corner";
				break;
			case 4:
				file_location = "img/Images/PathCornerSE.png";
				name = "Path_Corner";
				break;
			case 5:
				file_location = "img/Images/PathCornerSW.png";
				name = "Path_Corner";
				break;
				
		}
		
		try{
			tile = ImageIO.read(new File(file_location));
		} catch (IOException e) {
			// grabs whatever file needed for the tile image.
			e.printStackTrace();
		}
		
		
	}//end of constructor
	@Override
	public Image getTile() {
		return tile;
	}

	@Override
	public String getName() {
		return name;
	}
	
	
	
	
}
