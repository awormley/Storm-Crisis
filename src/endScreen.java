import java.awt.Graphics;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

 
public class endScreen {
	
	public  BufferedImage DelDot_Engineer;
	public  BufferedImage medal;
	public BufferedImage final_background;
	public Label Score_display;
	int score;
	boolean win;
	
	public endScreen(int ascore, boolean awin){
		score = ascore;
		win = awin;
	}
	
	public void paintComponent(Graphics g){
		
		try {
			DelDot_Engineer = ImageIO.read(new File("engineer.png"));
			
			if(score>1000)
				medal = ImageIO.read(new File("gold.png"));
			else if(score<500)
				medal = ImageIO.read(new File("bronze.png"));
			else
				medal = ImageIO.read(new File("silver.png"));
			
			final_background = ImageIO.read(new File("final_background.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}  

		
		
		System.out.println("Game over");
	}
}
