import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class JapaneseMapleTool extends Planting_Tool{
	
	public JapaneseMapleTool(){
		super();
		try {
			super.Icon = ImageIO.read(new File("img/Images/japanesemaple128x128.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getCooldown(){
		return 20;
	}
}
