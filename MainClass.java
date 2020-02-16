package Pack1;

import java.awt.*;
import javax.swing.*;

public class MainClass extends JFrame 
{
	private JLabel statusbar;
	
	public MainClass()
	{
		initUI();
	}
	
	private void initUI ()
	{
		statusbar = new JLabel (" 0");
		add (statusbar, BorderLayout.NORTH);
		
		var board = new Board(this);

		add (board);
		board.start();
		
		setTitle ("Tetris");
		setSize (360, 600);
		setBackground(Color.WHITE);		
		setDefaultCloseOperation (EXIT_ON_CLOSE);
		setLocationRelativeTo (null);
	}
	
	JLabel getStatusBar()
	{
		return statusbar;
	}
	
	public static void main (String[] args)
	{
		EventQueue.invokeLater(()-> {
			
			var game = new MainClass();		
			game.setVisible(true);
		});
	}
}

/*public class MainClass extends JPanel implements Runnable*
{
	
		/*public static final long serialVersionUID=1234567L;
	
		public static final int WIDTH = 780, HEIGTH = WIDTH/12*9;
		
		private Thread thread;
		private boolean running = false;
		
		public MainClass() {
			new Frame (WIDTH, HEIGTH, "Let's play Tetris!", this);
		}
		
		public synchronized void start() {
			thread = new Thread(this);
			thread.start();
			running = true;
		}
		
		public synchronized void stop() {
			try {
				thread.join();
				running = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			thread = new Thread(this);
			thread.start();
			running = true;
		}
		
		public void run() {
			
		}
	    	 
		 public static void main (String args[]) 
		 {
			 new MainClass();
		  //Frame.setFrame ();
		  //new Menu ();
		  /*SwingUtilities.invokeLater(new Runnable() {
			  @Override
			  public void run() {
				  new WindowColor ();
			  }
		  });
		// right rotation (90 deg)
		private final Point[][][] myPoint= 
		{
			{
				//I
				{ new Point (1,0), 	new Point (1,1), new Point (1,2), new Point (1,3) },
				{ new Point (0,1), 	new Point (1,1), new Point (2,1), new Point (3,1) },
				{ new Point (1,0), 	new Point (1,1), new Point (1,2), new Point (1,3) },
				{ new Point (0,1), 	new Point (1,1), new Point (2,1), new Point (3,1) }
			 },
			
			 { 
				//O
				{ new Point (0,0), 	new Point (1,1), new Point (1,0), new Point (1,1) },
				{ new Point (0,0), 	new Point (1,1), new Point (1,0), new Point (1,1) },
				{ new Point (0,0), 	new Point (1,1), new Point (1,0), new Point (1,1) },
				{ new Point (0,0), 	new Point (1,1), new Point (1,0), new Point (1,1) }
		    },
			 
			{ 
				//T
				{ new Point (0,1), 	new Point (1,1), new Point (1,0), new Point (2,1) },
				{ new Point (1,0), 	new Point (1,1), new Point (2,1), new Point (1,2) },
				{ new Point (0,1), 	new Point (1,1), new Point (1,2), new Point (2,1) },
				{ new Point (1,0), 	new Point (1,1), new Point (0,1), new Point (1,2) }
			},
			
			{ 
				//Z
				{ new Point (0,0), 	new Point (1,0), new Point (1,1), new Point (2,1) },
				{ new Point (2,0), 	new Point (2,1), new Point (1,1), new Point (1,2) },
				{ new Point (0,0), 	new Point (1,0), new Point (1,1), new Point (2,1) },
				{ new Point (2,0), 	new Point (2,1), new Point (1,1), new Point (1,2) }
			},
			 
			{ 
				//L
				{ new Point (0,1), 	new Point (1,1), new Point (2,1), new Point (2,0) },
				{ new Point (1,0), 	new Point (1,1), new Point (1,2), new Point (2,2) },
				{ new Point (0,1), 	new Point (1,1), new Point (2,1), new Point (0,0) },
				{ new Point (1,0), 	new Point (1,1), new Point (1,2), new Point (2,0) }
			}
	};	
		
	private final Color[] myColor = { Color.PINK, Color.YELLOW, Color.ORANGE, Color.GREEN, Color.BLUE, Color.CYAN, Color.LIGHT_GRAY, Color.RED };	
		 
	private Point pt;
	
	private int currentDetail;
	
	private int rotation;
	
	private long score;
	
	private Color[][] background;
	
	private ArrayList<Integer> nextDetail = new ArrayList<Integer> ();
	
	private void init()  //view
	{
		background = new Color[14][21];
		for (int i=0; i<14; i++)
		{
			for (int j=0; j<21; j++)
			{
				if (i==0 || i==13)
					background[i][j]=Color.DARK_GRAY;
				else
					background[i][j]=Color.black;
			}
		}
		newDetail();
	}
		 
	public void newDetail()  //model
	{
		pt = new Point (5,1);
		rotation=0;
		if (nextDetail.isEmpty())
		{
			Collections.addAll(nextDetail, 0,1,2,3);
			Collections.shuffle(nextDetail);   //randomly shuffle the list
		}
	}
	
	private boolean collide (int x, int y, int rotation)  //model
	{
		for (Point p: myPoint[currentDetail][rotation])
		{
			if (background[p.x+x][p.y+y]!=Color.black)
				return true;	
		}
		return false;
	}
	
	private void rotate (int i)  //model
	{
		int newRotation = (rotation+i)%4;
		if (newRotation < 0)
			newRotation = 3;
		if (!collide(pt.x, pt.y, newRotation))
			rotation=newRotation;
		repaint();
	}
	
	public void move (int i)  //model
	{
		if (!collide (pt.x, pt.y, rotation))
			pt.x+=i;
		repaint();
	}
	
	public void drop ()
	{
		if (!collide (pt.x, pt.y, rotation))
			pt.y+=1;
		else
			fixToWall();
		repaint();
	}
	
	public void fixToWall ()  //model
	{
		for (Point p: myPoint[currentDetail][rotation])
		{
			background [pt.x+p.x][pt.y+p.y] = myColor [currentDetail];
		}
		cleanRows();
		newDetail();
	}
	
	public void deleteRow (int row)  //model
	{
		for (int j=row-1; j>0; --j)
		{
			for (int i=1; i<14; ++i)
				background[i][j+1]=background[i][j];
		}
	}
	
	public void cleanRows ()  //view
	{
		boolean gap;
		int numRemoved=0;   // number of removed rows
		for (int j=20; j>0; --j)
		{
			gap=false;
			for (int i=1; i<14; ++i)
			{
				if (background[i][j]==Color.black)
				{
					gap=true;
					break;
				}	
			}
			if (!gap)
			{
				deleteRow (numRemoved);
				++j;
				++numRemoved;
			}
		}
		switch (numRemoved)
		{
			case 1:
				score+=20;
				break;
			case 2:
				score+=35;
				break;
			case 3:
				score+=55;
				break;
			case 4:
				score+=80;
				break;	
		}
	}
	
	private void drawDetail (Graphics g)  //model
	{
		g.setColor(myColor[currentDetail]);
		for (Point p: myPoint[currentDetail][rotation])
		{
			g.fill3DRect((p.x+pt.x)*30, (p.y+pt.y)*30, 26, 26, true);
		}
	}
	
	public void paintComponent (Graphics g)  //view
	{
		g.fill3DRect(0, 0, 30*14, 30*21, true);
		for (int i=0; i<14; ++i)
		{
			for (int j=0; j<21; ++j)
			{
				g.setColor(background[i][j]);
				g.fill3DRect(30*i, 30*j, 29, 29, true);
			}
		}
		g.setColor(Color.WHITE);
		g.drawString ("Score:  "+score, 22*14, 29);
		drawDetail (g);
	}
	
	public static void main (String[] args)
	{
		JFrame frame = new JFrame("Tetris");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension (14*30+10, 30*21+29));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		final MainClass game = new MainClass ();
		game.init();
		game.setFocusable(true);
		frame.add(game);
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped (KeyEvent e)
			{
				
			}
			
		    @Override
			public void keyReleased (KeyEvent e)
			{
				
			}
			
			@Override
			public void keyPressed (KeyEvent e)
			{
				switch (e.getKeyCode())
				{
				case KeyEvent.VK_UP:
					game.rotate(-1);
					break;
				
				case KeyEvent.VK_DOWN:
					game.rotate(1);
					break;	
				
				case KeyEvent.VK_LEFT:
					game.move(-1);
					break;	
					
				case KeyEvent.VK_RIGHT:
					game.move(1);
					break;
					
				case KeyEvent.VK_SPACE:
					game.drop();
					game.score+=1;
					break;	
				}
			}
		});
		
		new Thread()
		{
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					game.drop();
					
				}
			}
			
		}.start();
	}
	
}*/
