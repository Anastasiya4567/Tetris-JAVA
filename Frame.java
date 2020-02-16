package Pack1;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.*;
//import java.awt.geom.Rectangle2D;

public class Frame extends Canvas {
	
	public static final long serialVersionUID=1234567L;
	
	public Frame (int width, int heigth, String title, MainClass game) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, heigth));
		frame.setMaximumSize(new Dimension(2*width, 2*heigth));
		frame.setMinimumSize(new Dimension(width, heigth));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
	/*@Override 
	public void paint (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		GradientPaint blueToBlack = new GradientPaint (0, 0, Color.BLUE,
				1000, 1000, Color.BLACK);
		//g2.setPaint(blueToBlack);
	}*/
	
	/*public static void setFrame () {
		  JFrame frame = new JFrame ("Tetris");
		  frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		   //frame.setBackground(Color.RED);
		  //frame.setSize(1200, 800);
		  frame.setExtendedState (JFrame.MAXIMIZED_BOTH);
		  //frame.setUndecorated (true);
		  frame.getContentPane().setBackground(Color.WHITE);
		  frame.setVisible(true);
	}*/
}
