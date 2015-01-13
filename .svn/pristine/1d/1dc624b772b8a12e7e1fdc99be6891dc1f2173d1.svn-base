import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Sasafras extends Native{
	BufferedImage[] id = new BufferedImage[5];
	BufferedImage[] ea = new BufferedImage[5];
	public Sasafras(int x, int y){
		super(x,y);
		
		for(int i = 0; i < 5; i++){
			try {
				id[i] = ImageIO.read(new File("img/Images/sasafras52x52.png"));
			} catch (IOException e) {
				System.out.println("Couldnt find plant sasafras plant");
				e.printStackTrace();
			}
			try {
				ea[i] = ImageIO.read(new File("img/Images/SasafrasDrink.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		setName("Sasafras");
		setup(4,3,3,id,ea);
		
	}
	
}
