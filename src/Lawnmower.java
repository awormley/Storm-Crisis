import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Lawnmower extends Tool {
	public Lawnmower(){
		try {
			super.Icon = ImageIO.read(new File("img/Images/Placeholders/Lawnmower_tool.png"));
		} catch (IOException e) {
			System.out.println("Failed to load lawnmower image");
			e.printStackTrace();
		}
	}
	
	public void mowSquare(){}
	
	
}
