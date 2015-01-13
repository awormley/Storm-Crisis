import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Grass_Tile extends Grid_Type{
	public Grass_Tile(){
		name  = "Grass";
		try {
			tile = ImageIO.read(new File("img/Images/lightgrass.png"));
			
		} catch (IOException e) {
			System.out.println("Failed to load grass tile image");
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
