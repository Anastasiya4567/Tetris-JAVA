package Pack1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel
{
	private final int BOARD_WIDTH = 12;
	private final int BOARD_HEIGHT = 20;
	private final int PERIOD_INTERVAL = 300;
	
	private Timer timer;
	private boolean isFalled = false;
	private boolean isPaused = false;
	private int numRemovedLines = 0;
	private int curX = 0;
	private int curY = 0;
	private JLabel statusbar;
	private Shape curDetail;
	private Tetromino[] board;
	
	public Board(MainClass parent)
	{
		initBoard (parent);
	}
	
	private void initBoard (MainClass parent)
	{
		setFocusable (true);
		statusbar = parent.getStatusBar();
		addKeyListener (new TAdapter());
	}
	
	private int squareWidth()
	{
		return (int) getSize().getWidth() / BOARD_WIDTH; 
	}
	
	private int squareHeight()
	{
		return (int) getSize().getHeight() / BOARD_HEIGHT; 
	}
	
	private Tetromino shapeAt (int x, int y)
	{
		return board[(y*BOARD_WIDTH)+x];
	}
	
	void start ()
	{
		curDetail = new Shape();
		board = new Tetromino [BOARD_WIDTH*BOARD_HEIGHT];
		
		clearBoard();
		newDetail();
		
		timer = new Timer (PERIOD_INTERVAL, new GameCycle());
		timer.restart();
	}
	
	private void pause()
	{
		isPaused = !isPaused;
		
		if (isPaused)
		{
			statusbar.setText ("Paused");
		}
		else
		{
			statusbar.setText(String.valueOf(numRemovedLines));
		}
		
		repaint();
	}
	
	@Override
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		doDrawing(g);
	}
	
	private void doDrawing (Graphics g)
	{
		var size = getSize();
		int boardTop = (int) size.getHeight()- BOARD_HEIGHT * squareHeight();
		
		for (int i =0; i<BOARD_HEIGHT; i++)
		{
			for (int j=0; j<BOARD_WIDTH; j++)
			{
				Tetromino shape = shapeAt (j, BOARD_HEIGHT-i-1);
				if (shape != Tetromino.NOSHAPE)
				{
					drawSquare (g, j*squareWidth(),boardTop+i*squareHeight(), shape);
				}
			}
		}
		
		if (curDetail.getShape() != Tetromino.NOSHAPE)
		{
			for (int i=0; i<4; i++)
			{
				int x = curX+curDetail.x(i);
				int y = curY-curDetail.y(i);
				
				drawSquare (g, x*squareWidth(), boardTop + (BOARD_HEIGHT-y-1) *squareHeight(), curDetail.getShape());
			}
		}
	}
	
	private void dropDown ()
	{
		int newY = curY;
		
		while (newY>0)
		{
			if (!tryMove(curDetail, curX, newY - 1))
			{
				break;
			}
			newY--;
		}
		detailDropped();
	}
	
	private void oneLineDown ()
	{
		if (!tryMove(curDetail, curX, curY - 1))
		{
			detailDropped();
		}
	}	
	
	private void clearBoard()
	{
		for (int i=0; i<BOARD_HEIGHT*BOARD_WIDTH; i++)
		{
			board[i]=Tetromino.NOSHAPE;
		}		
	}
	
	private void detailDropped()
	{
		for (int i=0; i<4; i++)
		{
			int x=curX + curDetail.x(i);
			int y=curY - curDetail.y(i);
			board[(y*BOARD_WIDTH)+x] = curDetail.getShape();
		}
		
		removeFullLines();
		
		if (!isFalled)
		{
			newDetail();
		}
	}
	
	private void newDetail()
	{
		curDetail.setRandomShape();
		curX = BOARD_WIDTH/2 + 1;
		curY = BOARD_HEIGHT - 1 +curDetail.minY();
		
		if (!tryMove(curDetail, curX, curY))
		{
			curDetail.setShape(Tetromino.NOSHAPE);
			timer.stop();
			
			var message = String.format("Game Over! Your score: %d", numRemovedLines);
			statusbar.setText(message);
		}
	}
	
	private boolean tryMove (Shape newDetail, int newX, int newY)
	{
		for (int i=0; i<4; i++)
		{
			int x = newX + newDetail.x(i);
			int y = newY - newDetail.y(i);
			
			if (x<0 || x>=BOARD_WIDTH || y<0 || y>=BOARD_HEIGHT)
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
		
		repaint();
		
		return true;
	}
	
	private void removeFullLines()
	{
		int numFullLines = 0;
		
		for (int i = BOARD_HEIGHT-1; i>=0; i--)
		{
			boolean lineIsFull = true;
			
			for (int j=0; j<BOARD_WIDTH; j++)
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
				
				for (int k=i; k<BOARD_HEIGHT-1; k++)
				{
					for (int j=0; j<BOARD_WIDTH; j++)
					{
						board[(k*BOARD_WIDTH) + j] = shapeAt (j, k+1);
					}
				}
			}
		}		
		
		if (numFullLines>0)
		{
			numRemovedLines += numFullLines;
			
			statusbar.setText(String.valueOf(numRemovedLines));
			isFalled = true;
			curDetail.setShape(Tetromino.NOSHAPE);
		}
	}
	
	private void drawSquare (Graphics g, int x, int y, Tetromino shape)
	{
		Color colors[] = { new Color (102, 178, 255), new Color (255, 178, 102), new Color (255, 255, 102), new Color (102, 255, 102), 
						new Color (255, 153, 153), new Color (204, 153, 255), new Color (102, 255, 255), new Color (255, 102, 255) }; 
		
		var color = colors[shape.ordinal()];
		
		g.setColor(color);
		g.fill3DRect(x+1, y+1, squareWidth() -2, squareHeight() - 2, true);
		
		g.setColor (color.brighter());
		g.drawLine (x, y+squareHeight()-1, x, y);
		g.drawLine (x, y, x+squareWidth()-1, y);
		
		g.setColor(color.darker());
		g.drawLine (x+1, y+squareHeight()-1, x+squareWidth()-1, y+squareHeight()-1);
		g.drawLine (x+squareWidth()-1, y+squareHeight()-1, x+squareWidth()-1, y+1);
	}
	
	private class GameCycle implements ActionListener 
	{
		@Override 
		public void actionPerformed (ActionEvent e)
		{
			doGameCycle();
		}
	}
	
	private void doGameCycle ()
	{
		update();
		repaint();
	}
	
	private void update ()
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
	
	class TAdapter extends KeyAdapter 
	{
		@Override 
		public void keyPressed (KeyEvent e)
		{
			if (curDetail.getShape() == Tetromino.NOSHAPE)
			{
				return;
			}
			
			int keycode = e.getKeyCode();
			
			switch (keycode)
			{
				case KeyEvent.VK_P:
					pause();
					break;
				case KeyEvent.VK_LEFT:
					tryMove(curDetail, curX - 1, curY);
					break;
                case KeyEvent.VK_RIGHT: 
                	tryMove(curDetail, curX + 1, curY);
					break;
                case KeyEvent.VK_DOWN: 
                	tryMove(curDetail.rotateRight(), curX, curY);
					break;
                case KeyEvent.VK_UP: 
                	tryMove(curDetail.rotateLeft(), curX, curY);
					break;
                case KeyEvent.VK_SPACE: 
                	dropDown();
					break;
                case KeyEvent.VK_D: 
               		oneLineDown();
					break;
			}
		}
	}
}
