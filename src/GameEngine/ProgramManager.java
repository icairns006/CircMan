package GameEngine;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
public class ProgramManager {
	/*
	public enum level{ STAR,NONE};
	private static level curLev = level.NONE;
	
	public void setCurLev(level curLev) {
		this.curLev = curLev;
	}

	private int HEIGHT;
	private int WIDTH;
	private JFrame frame;
	static Set<Integer> keysActive = new HashSet<Integer>();
public ProgramManager(){
	frame = new JFrame("Minion Smash");
	WIDTH=frame.getToolkit().getScreenSize().width;
	HEIGHT=frame.getToolkit().getScreenSize().height;
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(WIDTH, HEIGHT);
	addKeyList();
	frame.setVisible(true);
}
	
	
public static void main(String[] args) throws InterruptedException {
		ProgramManager myProgram=new ProgramManager();
		
		myProgram.startHomeScreen();
		while(curLev == level.NONE){
			Thread.sleep(100);
		}
		
		Game game = myProgram.startGame();
		Thread.sleep(500);
		while (true) {
			game.move(keysActive);
			game.repaint();
			Thread.sleep(4);
		}
	}

void addKeyList(){
	frame.addKeyListener(new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(keysActive.contains(e.getKeyCode())){
				keysActive.remove(e.getKeyCode());
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			keysActive.add(e.getKeyCode());
		}
	});
	frame.setFocusable(true);
}
private Game startGame(){
	Game game = new Game(WIDTH,HEIGHT);
	frame.setLayout(new BorderLayout());
	frame.add(game);
	frame.setVisible(true);
	return game;
}

private void startHomeScreen(){
	HomeScreen myHomeScreen = new HomeScreen(HEIGHT, WIDTH);
	frame.setContentPane(myHomeScreen);
	//panel1.add(test);
	JButton b3 = new JButton("Star Land",
            new ImageIcon
            ("Resources/Background/starland.jpeg"));
     b3.setVerticalTextPosition(SwingConstants.TOP);
     b3.setHorizontalTextPosition(SwingConstants.CENTER);
     b3.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			curLev = level.STAR;
		}
    	 
     }); 
	
     frame.setLayout( new GridBagLayout() );
     frame.add(b3, new GridBagConstraints());
	//frame.add(b3);
	
    frame.setVisible(true);
	}
	*/
}



