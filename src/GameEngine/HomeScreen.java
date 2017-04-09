package GameEngine;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeScreen extends JPanel{
	private int width, height;
	Image img;
	public HomeScreen(int width, int height){
		this.width = width;
		this.height=height;
		ImageIcon ii = new ImageIcon("Resources/Background/hills.jpg");
        img = ii.getImage();
        Font font = new Font("Verdana", Font.BOLD, 40);
		
		JLabel label1 = new JLabel();
		label1.setFont(font);
	    label1.setText("Press A to Start");
	    label1.setBounds(0, 0, 200, 50);
	    
	    this.add(label1);
	}
	@Override
	public void paint(Graphics g) {
		
		g.drawImage(img,
				0, 0,
				width, height,
				null);
		super.paint(g);
		
		
		
		//g.drawString( "Press A to Start" , width/2,height/2 ); 
	}
	

}
