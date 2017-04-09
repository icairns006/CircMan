package GameEngine;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
public class ProgramManager {
	public int HEIGHT;
	public int WIDTH;
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
		while(!keysActive.contains(KeyEvent.VK_A)){
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
	frame.add(game);
	frame.setVisible(true);
	return game;
}

private void startHomeScreen(){
	HomeScreen myHomeScreen = new HomeScreen(WIDTH,HEIGHT);
	frame.add(myHomeScreen);
	//frame.setVisible(true);
	Font font = new Font("Verdana", Font.BOLD, 40);

    
    
    frame.add(myHomeScreen);
    frame.setVisible(true);
	
}
}

