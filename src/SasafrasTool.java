import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class SasafrasTool extends Planting_Tool{
	
	public SasafrasTool(){
		super();
		try {
			super.Icon = ImageIO.read(new File("img/Images/sasafras128x128.png"));
		} catch (IOException e) {
			System.out.println("Failed to load sasafras plant image");
			e.printStackTrace();
		}
	}
	public int getCooldown(){
		return 30;
	}
}
