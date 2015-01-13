import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class CommonMilkweed extends Native{
	BufferedImage[] id = new BufferedImage[5];
	BufferedImage[] ea = new BufferedImage[5];
	public CommonMilkweed(int x, int y){
		super(x,y);
		
		for(int i = 0; i < 5; i++){
			try {
				id[i] = ImageIO.read(new File("img/Images/commonmilkweed52x52.png"));
			} catch (IOException e) {
				System.out.println("Couldnt find Milkweed plant");
				e.printStackTrace();
			}
			try {
				ea[i] = ImageIO.read(new File("img/Images/CommonMilkWeedDrink.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		setName("Common Milkweed");
		setup(4,3,1,id,ea);
		setSpawns(false, true);
	}
	
}
