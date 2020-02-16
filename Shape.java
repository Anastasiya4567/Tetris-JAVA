package tet;

import java.util.Random;

/**
 * public class that creates the table of coordinates and makes operations on the details
 * @author Anastasiya
 *
 */
public class Shape 
{
	public Tetromino detailShape;
	private int coords[][];
	private int [][][] coordsTable;
	
	/**
	 * public constructor of class Shape
	 */
	public Shape()
	{
		initShape();
	}
	
	/**
	 * initialization of coordinations of possible configurations 
	 */
	private void initShape()
	{
		coords = new int[4][2];
		coordsTable = new int [][][] 
		{
            { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
            { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
            { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
            { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
            { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
            { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
            { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
            { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
		};
		
		setShape (Tetromino.NOSHAPE);
	}
	
	/**
	 * sets configuration of the detail on coordinate table 
	 * @param shape configuration of Tetromino
	 */
	public void setShape (Tetromino shape)
	{
		for (int i=0; i<4; i++)
		{
			for (int j=0; j<2; j++)
			{
				coords[i][j]=coordsTable[shape.ordinal()][i][j];
			}
		}
		detailShape = shape;
	}
	
	/**
	 * sets the value of coords[index][0]
	 * @param index new x coordinate 
	 * @param x value 
	 */
	public void setX (int index, int x) { coords[index][0] = x; }
	
	/**
	 * sets the value of coords[index][1]
	 * @param index new x coordinate 
	 * @param y value
	 */
	public void setY (int index, int y) { coords[index][1] = y; }
	
	/**
	 * returns the value of coords[index][0]
	 * @param index new x coordinate
	 * @return returns the value of coords[index][0]
	 */
	public int x (int index) { return coords[index][0]; }
	
	/**
	 * returns the value of coords[index][1]
	 * @param index new x coordinate
	 * @return the value of coords[index][1]
	 */
	public int y (int index) { return coords[index][1]; }
	
	/**
	 * gets detail shape
	 * @return Tetromino configuration 
	 */
	public Tetromino getShape() { return detailShape; }
	
	/**
	 * choice random shape of detail among possible shapes and setting it
	 */
	public void setRandomShape()
	{
		var rand = new Random();
		int x = Math.abs(rand.nextInt())%7 + 1;
		
		Tetromino[] values = Tetromino.values();
		setShape (values[x]);
	}
	
	/**
	 * sets minimum X coordinate of new falling detail 
	 * @return minimum X coordinate 
	 */
	public int minX()
	{
		int m = coords[0][0];
		
		for (int i=0; i<4; i++)
		{
			m = Math.min (m, coords[i][0]);
		}
		
		return m;
	}
	
	/**
	 * sets minimum Y coordinate of new falling detail 
	 * @return minimum Y coordinate 
	 */
	public int minY()
	{
		int m = coords[0][1];
		
		for (int i=0; i<4; i++)
		{
			m = Math.min(m, coords[i][1]);
		}
		
		return m;
	}
	
	/**
	 * left rotation of the detail
	 * if the detail has square configuration, return; otherwise change of coordinates of it
	 * @return square or other detail after left rotation
	 */
	public Shape rotateLeft() 
	{
		if (detailShape == Tetromino.SQUARE)
		{
			return this;
		}
		
		var result = new Shape();
		result.detailShape = detailShape;
		
		for (int i=0; i<4; i++)
		{
			result.setX(i, y(i));
			result.setY(i, -x(i));
		}
		
		return result;
	}
	
	/**
	 * right rotation of the detail
	 * if the detail has square configuration, return; otherwise change of coordinates of it
	 * @return square or other detail after right rotation
	 */
	public Shape rotateRight()
	{
		if (detailShape == Tetromino.SQUARE)
		{
			return this;
		}
		
		var result = new Shape();
		result.detailShape = detailShape;
		
		for (int i=0; i<4; i++)
		{
			result.setX(i, -y(i));
			result.setY(i, x(i));
		}
		
		return result;
	}
}