package GameEngine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomeScreen extends JPanel implements MouseListener{
	Image img;
	private int height, width;
	private BasicGameEngine myEngine;
	public HomeScreen(int width, int height , BasicGameEngine myEngine) {
		this.myEngine = myEngine;
		this.height= height;
		this.width=width;
	    ImageIcon ii = new ImageIcon(getClass().getResource("/Background/hills.jpg"));
        img = ii.getImage();
        addMouseListener(this);
        
	}
	
	
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img,
				0, 0,
				width, height,
				this);
		
		Font font = new Font("Garamond", 10, 60);
		g.setFont(font);
		g.drawString("Press Play to Begin", (width/2)-250, (height/2)-100);
		//g.drawRect((width/2)-50, (height/2)+25, 120, 50);
		g.setColor(Color.green);
		g.fillRect((width/2)-50, (height/2), 120, 50);
		g.setColor(Color.black);
		
		g.drawRect((width/2)-50, (height/2), 120, 50);
		font = new Font("Garamond", 10, 20);
		g.setFont(font);
		g.drawString("PLAY", (width/2)-15, (height/2)+30);
		
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("hello");
		if(e.getX()>(width/2)-50 && e.getX()<(width/2)-50+120){
			if(e.getY()>(height/2)&&e.getY()<(height/2)+50){
				//System.out.println("hello");
				try {
					this.myEngine.startGame();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
