import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Roof_Tile extends Grid_Type{
	public Roof_Tile(){
		try {
			//tile = ImageIO.read(new File("img/Images/Placeholders/roofTile.png"));
			tile = ImageIO.read(new File("img/Images/lightGrass.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Image getTile() {
		return tile;
	}
	
	@Override
	public String getName() {
		return "Roof";
	}
}
