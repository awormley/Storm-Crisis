import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Meadow_Tile extends Grid_Type{
	public Meadow_Tile(){
		name = "Meadow";
		try {
			tile = ImageIO.read(new File("img/Images/grass.png"));
			
		} catch (IOException e) {
			System.out.println("Failed to load meadow tile image");
			e.printStackTrace();
		}
	}
	@Override
	public Image getTile() {
		return tile;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
