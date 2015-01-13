import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class JapaneseMaple extends Invasive{
	BufferedImage[] id = new BufferedImage[5];
	BufferedImage[] ea = new BufferedImage[5];
	public JapaneseMaple(int x, int y){
		super(x,y);
		
		for(int i = 0; i < 5; i++){
			try {
				id[i] = ImageIO.read(new File("img/Images/japanesemaple52x52.png"));
				//System.out.println("Got plant d");
			} catch (IOException e) {
				System.out.println("Couldnt find plant D");
				e.printStackTrace();
			}
			try {
				ea[i] = ImageIO.read(new File("img/Images/JapaneseMapleDrink.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		setName("Japanese Maple");
		setup(1,1,0,id,ea);
		
	}
	
}
