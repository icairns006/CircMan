package GameEngine;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

public class BasicGameEngine {
	static JFrame frame;
	private static int WIDTH;
	private static int HEIGHT;
	static BasicGameEngine myEngine;
	static Set<Integer> keysActive = new HashSet<Integer>();
	public static void main(String[] args) throws InterruptedException{
		myEngine = new BasicGameEngine();
		JFrame homeFrame = new JFrame();
		WIDTH=homeFrame.getToolkit().getScreenSize().width;
		HEIGHT=homeFrame.getToolkit().getScreenSize().height;
		homeFrame.setSize(WIDTH, HEIGHT);
		HomeScreen homeScreen = new HomeScreen(WIDTH,HEIGHT,myEngine);
		
		homeFrame.add(homeScreen);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setVisible(true);
		
		
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
	public void startGame() throws InterruptedException{
		Thread thread = new Thread(new Runnable(){
			public void run(){
		frame = new JFrame("Minion Smash");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		myEngine.addKeyList();
		frame.setVisible(true);
		
		Game game = new Game(WIDTH,HEIGHT);
		frame.add(game);
		frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                frame.dispose();
                
            }
        });
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean gaming = true;
		frame.setVisible(true);
		while (gaming) {
			gaming = game.move(keysActive);
			game.repaint();
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		while(keysActive.contains(KeyEvent.VK_M)){
			try {
				Thread.sleep(2);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		frame.dispose();
			}
			
		
		});
		thread.start();
	}
}

