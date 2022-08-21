package gameProject;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		JFrame object = new JFrame();
		PlayGame playgame = new PlayGame();
		object.setBounds(10, 10, 700, 600);
		object.setTitle("DX Ball");
		object.setVisible(true);
		object.setResizable(false);
		object.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		object.add(playgame);
	
	}

}