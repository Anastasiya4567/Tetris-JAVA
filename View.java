package tet;

import java.awt.*;
import javax.swing.*;

/**
 * public class that is important for the foreground (visible) side of game
 * @author Anastasiya
 */
public class View extends JPanel
{
	private static final long serialVersionUID = -3330268422947754514L;
	private final int BOARD_WIDTH = 12;
	private final int BOARD_HEIGHT = 20;
	private JLabel statusbar;
	private JFrame mainFrame;
	private Controller controller;
	
	/**
	 * gets main frame of game
	 * @return main frame of game to the class Controller
	 */
	JFrame getMainFrame()
	{
		return mainFrame;
	}
	
	/**
	 * constructor of View class that sets statusbar, parameters and options of main frame
	 */
	public View ()
	{
		mainFrame = new JFrame();

		statusbar = new JLabel ("Score: " + 0);
		statusbar.setFont(new Font("Serif", Font.BOLD, 17));
		mainFrame.add (statusbar, BorderLayout.NORTH);
		mainFrame.setFocusable (true);	
		mainFrame.setTitle ("Tetris");
		mainFrame.setSize (410, 720);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo (null);
		mainFrame.getContentPane().setBackground(new Color(176, 196, 222));
		setOpaque(true);		
		mainFrame.setVisible(true);		
		mainFrame.add(this);
	}
	
	/**
	 * formats text with game-over message and call procedure that displays this one
	 * @param numRemovedLines score
	 */
	public void printGameOverMessage(int numRemovedLines)
	{
		var message = String.format("Game Over! Your score: %d. Press any key to restart", numRemovedLines);
		printText(message);
	}
	
	/**
	 * adds class Controller object to the View class and call the procedure on it
	 * @param controller object
	 */
	public void setController(Controller controller)
	{
		this.controller = controller;
		controller.callStart();		
	}
	
	/**
	 * draws squares (parts of the detail) and set color on them
	 * @param g
	 * @param x
	 * @param y
	 * @param shape
	 */
	private void drawSquare (Graphics g, int x, int y, Tetromino shape)
	{
		Color colors[] = {new Color (255, 178, 102), new Color (255, 218, 185),  new Color (255, 255, 102), new Color (102, 255, 102), 
						new Color (255, 153, 153), new Color (204, 153, 255), new Color (102, 255, 255)/*, new Color (255, 102, 255)*/}; 
		var color = colors[shape.ordinal()-1];
		
		g.setColor(color);
		g.fill3DRect(x+1, y+1, squareWidth() -2, squareHeight() - 2, true);
		
		g.setColor (color.brighter());
		g.drawLine (x, y+squareHeight()-1, x, y);
		g.drawLine (x, y, x+squareWidth()-1, y);
		
		g.setColor(color.darker());
		g.drawLine (x+1, y+squareHeight()-1, x+squareWidth()-1, y+squareHeight()-1);
		g.drawLine (x+squareWidth()-1, y+squareHeight()-1, x+squareWidth()-1, y+1);
	}
	
	/**
	 * sets background color and draws it and details after rotation
	 * @param g
	 */
	private void doDrawing (Graphics g) 
	{
		var size = getSize();
		int boardTop = (int) size.getHeight()- BOARD_HEIGHT * squareHeight();
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint (RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		Color color1 = new Color(176, 196, 222);
		Color color2 = Color.DARK_GRAY;
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
		g2D.setPaint (gp);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i<BOARD_HEIGHT; i++)
		{
			for (int j=0; j<BOARD_WIDTH; j++)
			{
				Tetromino shape = controller.callShapeAt(j, BOARD_HEIGHT-i-1);
				if (shape != Tetromino.NOSHAPE)
				{
					drawSquare (g, j*squareWidth(),boardTop+i*squareHeight(), shape);
				}
			}
		}
		
		if (controller.callCurDetail().getShape() != Tetromino.NOSHAPE)
		{
			for (int i=0; i<4; i++)
			{
				int x = controller.callGetCurX()+controller.callCurDetail().x(i);
				int y = controller.callGetCurY()-controller.callCurDetail().y(i);
				
				drawSquare (g, x*squareWidth(), boardTop + (BOARD_HEIGHT-y-1) *squareHeight(), controller.callCurDetail().getShape());
			}
		}
	}
	
	/**
	 * calls built-in function of graphic library and calls drawing procedure
	 */
	@Override
	public void paintComponent (Graphics g) 
	{
		super.paintComponent(g);
		doDrawing(g);
	}
	
	/**
	 * calls built-in function of graphic library
	 */
	public void makeRepaint()
	{
		repaint();
	}
	
	/**
	 * displays text message
	 * @param text some message
	 */
	public void printText (String text)
	{
		statusbar.setText(text);
	}
	
	/**
	 * gets width of square (the part of detail)
	 * @return width of square (the part of detail) to the class Model
	 */
	public int squareWidth()
	{
		return (int) getSize().getWidth() / BOARD_WIDTH; 
	}
	
	/**
	 * gets height of square (the part of detail)
	 * @return height of square (the part of detail) to the class Model
	 */
	public int squareHeight()
	{
		return (int) getSize().getHeight() / BOARD_HEIGHT; 
	}
	
	/**
	 * gets board width
	 * @return board width to the class Model
	 */
	public int getBoardWidth ()
	{
		return BOARD_WIDTH;
	}
	
	/**
	 * gets board height 
	 * @return board height to the class Model
	 */
	public int getBoardHeight ()
	{
		return BOARD_HEIGHT;
	}
	
}
