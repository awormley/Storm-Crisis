import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Forest_Tile extends Grid_Type{
	public Forest_Tile(){
		name = "Forest";
		try {
			tile = ImageIO.read(new File("img/Images/darkgrass.png"));
			
		} catch (IOException e) {
			System.out.println("Failed to load forest tile image");
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
