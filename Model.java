package tet;

import javax.swing.*;
import java.awt.event.*;

/**
 * public class that is important for background side of game
 * @author Anastasiya
 */
public class Model
{
	private View view;	
	
	private Timer timer;
	private boolean isFalled = false;
	private int numRemovedLines = 0;
	private Shape curDetail;
	private Tetromino[] board;
	private int curX = 0;
	private int curY = 0;
	private boolean isPaused = false;
	private final int PERIOD_INTERVAL = 300;
	
	/**
	 * public constructor that creates the board of details
	 * @param view View class object
	 */
	public Model (View view)
	{
		this.view = view;
		board = new Tetromino [view.getBoardWidth()*view.getBoardHeight()];
	}	
	
	/**
	 * changes Pause/not Pause game state for the opposite, repaints the board
	 * @return  current Pause/not Pause state
	 */
	public boolean pause()
	{
		isPaused = !isPaused;
		
		if (isPaused)
		{
			view.printText ("Paused");
		}
		else
		{
			view.printText (String.valueOf("Score: " + numRemovedLines));
		}
		
		view.makeRepaint();
		return isPaused;
	}
	
	/**
	 * creates coordinate table, clears Tetromino board and set timer
	 */
	public void start () 
	{
		curDetail = new Shape();
		
		clearBoard();
		newDetail();
		
		timer = new Timer (PERIOD_INTERVAL, new GameCycle());
		timer.restart();
	}
	
	/**
	 * repaints current board and sets start parameters, resets timer
	 */
	void restartGame ()
	{
		view.printText("Score: " + 0);
		view.makeRepaint();
		
		clearBoard();
		
		isFalled = true;
		timer.restart();
	}
	
	/**
	 * shifts down the detail as it's possible and than calls the procedure sets X and Y coordinates of dropped detail
	 */
	public void dropDown () 
	{
		int newY = curY;
		
		while (newY>0)
		{
			if (!tryMove(curDetail, curX, curY - 1))
			{
				break;
			}
			newY--;
		}
		detailDropped();
	}
	
	/**
	 * checks if the detail vertical movement is impossible
	 */
	public void oneLineDown () 
	{
		if (!tryMove(curDetail, curX, curY - 1))
		{
			detailDropped();
		}
	}	
	
	/**
	 * sets clear Tetromino board (without any configuration)
	 */
	private void clearBoard() 
	{
		for (int i=0; i<view.getBoardWidth()*view.getBoardHeight(); i++)
		{
			board[i]=Tetromino.NOSHAPE;
		}		
	}
	
	/**
	 * checks what and how many lines are full
	 * clears full line and shifts one line down squares above this
	 * if 1 line is full, adds 5 points to the score
	 * if 2 lines are full, adds 15 points to the score
	 * if 3 lines are full, adds 25 points to the score
	 * if 4 lines are full, adds 40 points to the score
	 * refreshes the score and sets current detail on NOSHAPE configuration
	 */
	private void removeFullLines() 
	{
		int numFullLines = 0;
		
		for (int i = view.getBoardHeight()-1; i>=0; i--)
		{
			boolean lineIsFull = true;
			
			for (int j=0; j<view.getBoardWidth(); j++)
			{
				if (shapeAt(j, i) == Tetromino.NOSHAPE)
				{
					lineIsFull = false;
					break;
				}
			}
		
			if (lineIsFull)
			{
				++numFullLines;
				
				for (int k=i; k<view.getBoardHeight()-1; k++)
				{
					for (int j=0; j<view.getBoardWidth(); j++)
					{
						board[(k*view.getBoardWidth()) + j] = shapeAt (j, k+1);
					}
				}
			}
		}		
		
		if (numFullLines>0)
		{
			switch (numFullLines)
			{
			case 1:
				numRemovedLines +=5;
				break;
			case 2:
				numRemovedLines +=15;
				break;
			case 3:
				numRemovedLines +=25;
				break;
			case 4:
				numRemovedLines +=40;
				break;	
			}
			
			view.printText("Score: "+ String.valueOf(numRemovedLines));
			isFalled = true;
			curDetail.setShape(Tetromino.NOSHAPE);
		}
	}
	
	/**
	 * returns detail configuration according to its coordinates
	 * @param x horizontal coordinate 
	 * @param y vertical coordinate
	 * @return Tetromino configuration
	 */
	public Tetromino shapeAt (int x, int y)
	{
		return board[(y*view.getBoardWidth())+x];
	}
	
	/**
	 * sets X and Y coordinates of dropped detail and calls procedure that removes full lines
	 */
	private void detailDropped() 
	{
		for (int i=0; i<4; i++)
		{
			int x=curX + curDetail.x(i);
			int y=curY - curDetail.y(i);
			board[(y*view.getBoardWidth())+x] = curDetail.getShape();
		}
		
		removeFullLines();
		
		if (!isFalled)
		{
			newDetail();
		}
	}
	
	/**
	 * creates new detail with randomly chosen configuration, sets it's coordinates
	 * if its vertical movement is impossible, ends game with game-over message
	 */
	private void newDetail() 
	{
		curDetail.setRandomShape();
		curX = view.getBoardWidth()/2;
		curY = view.getBoardHeight()-1 + curDetail.minY();
		
		if (!tryMove(curDetail, curX, curY))
		{
			curDetail.setShape(Tetromino.NOSHAPE);
			timer.stop();
		
			view.printGameOverMessage(numRemovedLines);
		}
	}
	
	/**
	 * tries to move detail on the table and, if it's possible, changes its coordinates and makes repaint 
	 * @param newDetail new detail 
	 * @param newX new X coordinate
	 * @param newY new Y coordinate
	 * @return if movement is possible, true; otherwise false
	 */
	public boolean tryMove (Shape newDetail, int newX, int newY) 
	{
		for (int i=0; i<4; i++)
		{
			int x = newX + newDetail.x(i);
			int y = newY - newDetail.y(i);
			
			if (x<0 || x>=view.getBoardWidth() || y<0 )
			{
				return false;
			}
			
			if (shapeAt (x,y) != Tetromino.NOSHAPE)
			{
				return false;
			}
		}
		
		curDetail = newDetail;
		curX = newX;
		curY = newY;
		
		view.makeRepaint();
		
		return true;
	}
	
	/**
	 * class with the only method that starts new cycle of game
	 * @author Anastasiya
	 */
	private class GameCycle implements ActionListener 
	{
		/**
		 * is called after the user has performed an action
		 *  calls procedure that starts new cycle of game
		 */
		@Override 
		public void actionPerformed (ActionEvent e)
		{
			doGameCycle();
		}
	}
	
	/**
	 * calls procedure that updates current state of game if it isn't paused and repaint the table
	 */
	private void doGameCycle () 
	{
		update();
		view.makeRepaint();
	}
	
	/**
	 * updates current state of game if it isn't paused
	 */
	public void update () 
	{
		if (isPaused)
		{
			return;
		}
		
		if (isFalled)
		{
			isFalled = false;
			newDetail();
		}
		else
		{
			oneLineDown();
		}
	}
	
	/**
	 * gets current falling element
	 * @return the shape of currently falling detail
	 */
	public Shape getCurDetail () { return curDetail; }
	
	/**
	 * gets X coordinate of currently falling detail
	 * @return X coordinate of currently falling detail to the controller
	 */
	public int getCurX () { return curX; }
	
	/**
	 * gets Y coordinate of currently falling detail
	 * @return Y coordinate of currently falling detail to the class Controller
	 */
	public int getCurY () { return curY; }
}
