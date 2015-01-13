import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ButterflyBush extends Invasive{
	BufferedImage[] id = new BufferedImage[5];
	BufferedImage[] ea = new BufferedImage[5];
	public ButterflyBush(int x, int y){
		super(x,y);
		
		for(int i = 0; i < 5; i++){
			try {
				id[i] = ImageIO.read(new File("img/Images/ButterflyBush.png"));
				//System.out.println("Got plant d");
			} catch (IOException e) {
				System.out.println("Couldnt find plant D");
				e.printStackTrace();
			}
			try {
				ea[i] = ImageIO.read(new File("img/Images/ButterflyBushDrink.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		setName("Butterfly Bush");
		setup(1,2,0,id,ea);
		
	}
	
}
