import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class startScreen extends JPanel implements ActionListener ,MouseListener {
	//If you put images inside the src folder it won't find them, put them in the img/Images folder -- go look at Grass_Tile for examples of working paths
	public BufferedImage Title_Screen_background;
	public JButton startButton;
	public JButton nextButton;
	public BufferedImage Description_Screen;
	public BufferedImage curImage;
	public boolean next;
	public int height = 600;
	public int width = 800;
	public Grid playerGrid;
	public startScreen(){
		try {
    		Title_Screen_background = ImageIO.read(new File("img/guyonright.png"));
    		//Description_Screen = ImageIO.read(new File("src/DescriptionScreen.png"));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		
		startButton = new JButton("Start");
		startButton.setVerticalAlignment(AbstractButton.TOP);
		startButton.addActionListener(this);
		nextButton = new JButton("Next");
		nextButton.setVerticalAlignment(AbstractButton.TOP);
		nextButton.addActionListener(this);
		add(startButton);
		setImage(Title_Screen_background);
		Grid playerGrid = new Grid();
		playerGrid.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(curImage,  0, 0, this);
		
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
		Object src = e.getSource();
		if(src == startButton){
			removeAll();
			add(nextButton);
			revalidate();
			setImage(Title_Screen_background);
		} else if(src == nextButton){
			JFrame frame = new JFrame();
			playerGrid = new Grid();
			//JLabel click_me = new JLabel("Click hereee!");
			//click_me.addMouseListener(this);
			playerGrid.addMouseListener(this);
			frame.add(playerGrid);
		//	frame.add(click_me);
			frame.setSize(1200, 800);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setVisible(true);
		    
		}
		
	}	
	
	private void setImage(BufferedImage image){
		curImage = image;
	}

	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.add(new startScreen());
		frame.setSize(800, 600);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("( " +e.getX() + " "+ e.getY()+ " )");
		System.out.println("( " +e.getXOnScreen() + " "+ e.getYOnScreen()+ " )");
		 //since getX() and getXOnScreen() return different values, we must
		//be consistent when checking on what is clicked, and to use one
		//position getter and stick to it ---ajw
		
		//------------------------//
		//formula for getting grid location!
		/* background [i][j] where
		
		  i =  (e.getYOnScreen() - (e.getYOnScreen % 64) ) /64
		  j = (e.getXOnScreen() - (e.getXOnScreen % 64) ) /64
		  steps --> mod by 64 to get remainder, then subtract that from total
		  		-->take intermediate left-bound, divide by 64 to get position
					(since each tile is 64 x 64)
		
		*/
		
		int i = (e.getYOnScreen() - (e.getYOnScreen() % 64) -50 ) /64 ;
		int j = (e.getXOnScreen() - (e.getXOnScreen() % 64) ) /64;
		System.out.println("Grid position clicked is " + i+  ", " +j);
		
		//- e.getSource pulls back the entire GRID , not a specific 
		//tile in the grid. cant just pull .getSource to change a tile --ajw
		/*Grid_Type change = (Grid_Type)e.getSource();
		change = new Forest_Tile(); */
	}

	//stubs for other mouse events.
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
