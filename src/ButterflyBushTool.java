import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ButterflyBushTool extends Planting_Tool{
	
	public ButterflyBushTool(){
		super();
		try {
			super.Icon = ImageIO.read(new File("img/Images/butterflybush128x128.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getCooldown(){
		return 20;
	}
}
