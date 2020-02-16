package Pack1;

import java.util.Random;

public class Shape 
{
	protected enum Tetromino { NOSHAPE, ZSHAPE, SSHAPE, TSHAPE,
		LSHAPE, SQUARE, LINE, JSHAPE }
	private Tetromino detailShape;
	private int coords[][];
	private int [][][] coordsTable;
	
	public Shape()
	{
		initShape();
	}
	
	private void initShape()
	{
		coords = new int[4][2];
		coordsTable = new int [][][] {
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
	
	protected void setShape (Tetromino shape)
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
	
	private void setX (int index, int x) { coords[index][0] = x; }
	private void setY (int index, int y) { coords[index][1] = y; }
	public int x (int index) { return coords[index][0]; }
	public int y (int index) { return coords[index][1]; }
	public Tetromino getShape() { return detailShape; }
	
	public void setRandomShape()
	{
		var rand = new Random();
		int x = Math.abs(rand.nextInt())%7 + 1;
		
		Tetromino[] values = Tetromino.values();
		setShape (values[x]);
	}
	
	public int minX()
	{
		int m = coords[0][0];
		
		for (int i=0; i<4; i++)
		{
			m = Math.min (m, coords[i][0]);
		}
		
		return m;
	}
	
	public int minY()
	{
		int m = coords[0][1];
		
		for (int i=0; i<4; i++)
		{
			m = Math.min(m, coords[i][1]);
		}
		
		return m;
	}
	
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
