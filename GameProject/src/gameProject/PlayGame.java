package gameProject;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;



public class PlayGame extends JPanel implements KeyListener, ActionListener {
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int player = 310;
	private int ballX = -1;
	private int ballY = -2;
	private int ballXpos = 120;
	private int ballYpos = 350;
	

	private GenerateMap map;
	
	public PlayGame() {
		map = new GenerateMap(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics a) {
		 //background
		a.setColor(Color.black);
		a.fillRect(1, 1, 690, 590);
		
		//drawing map
		map.draw((Graphics2D)a);
		//borders
		a.setColor(Color.orange);
		a.fillRect(0, 0, 3, 590);
		a.fillRect(0, 0,690, 3);
		a.fillRect(680, 0, 3, 590);
		
		//scores
		a.setColor(Color.white);
		a.setFont(new Font("serfi", Font.BOLD, 25));
		a.drawString(""+score, 590, 30);
		
		//the padle
		a.setColor(Color.green);
		a.fillRect(player, 550, 100, 8);
		//the ball
		a.setColor(Color.yellow);
		a.fillOval(ballXpos, ballYpos, 20, 20); 
		
		if(totalBricks <= 0) {
			play = false;
			ballX = 0;
			ballY = 0;
			a.setColor(Color.red);
			a.setFont(new Font("serfi", Font.BOLD, 30));
			a.drawString("YOU WON", 260, 300 );
			
			a.setFont(new Font("serfi", Font.BOLD, 20));
			a.drawString("Press ENTER to Restart", 230, 350 );
		}
		
		if(ballYpos >570) {
			play = false;
			ballX = 0;
			ballY = 0;
			a.setColor(Color.red);
			a.setFont(new Font("serfi", Font.BOLD, 30));
			a.drawString("GAME OVER, Scores: ", 190, 300 );
			
			a.setFont(new Font("serfi", Font.BOLD, 20));
			a.drawString("Press ENTER to Restart", 230, 350 );
			
		}
		
		a.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballXpos, ballYpos, 20, 20).intersects(new Rectangle(player, 550,100,8))) {
				ballY = -ballY;
			}
			
			A: for(int i = 0; i<map.map.length; i++) {
				for(int j=0; j<map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						int brickX = j* map.brickWidth +80;
						int brickY = i* map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballXpos, ballYpos, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							if(ballXpos + 19 <= brickRect.x || ballXpos +1 >= brickRect.x + brickRect.width) {
								ballX = -ballX;
							} else {
								ballY = -ballY;
							}
							
							break A;
							
						}
					}
				}
			}
			
			ballXpos += ballX;
			ballYpos += ballY;
			if(ballXpos < 0) {
				ballX = -ballX;
			} 
			if(ballYpos < 0) {
				ballY = -ballY;
			}
			if(ballXpos > 665) {
				ballX = -ballX;
			}
		}
		repaint();
		
	}
	
    @Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(player >= 600) {
				player = 600;
			} else {
				moveRight();
			}
		}
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
        	if(player < 10) {
				player = 10;
			} else {
				moveLeft();
			}
		}
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        	if(!play) {
        		play = true;
        		ballXpos = 120;
        		ballYpos = 350;
        		ballX = -1;
        		ballY = -2;
        		player = 310;
        		totalBricks = 21;
        		map = new GenerateMap(3,7);
        		
        		repaint();
        	}
        }
		
	}
    
	public void moveRight() {
		play = true;
		player+=20;
	}

	public void moveLeft() {
		play = true;
		player-=20;
	}

	@Override
	public void keyTyped(KeyEvent e) { }
	@Override
	public void keyReleased(KeyEvent e) { }

}
