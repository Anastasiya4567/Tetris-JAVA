package tet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * public class that is important for communication between View and Model classes and reaction on typing keys 
 * @author Anastasiya
 */
public class Controller implements KeyListener
{
	private Model model;
	private View view;
	
	/**
	 * public constructor with 2 parameters - objects of class Model and class View
	 * @param model Model class object
	 * @param view View class object
	 */
	public Controller (Model model, View view)
	{
		this.model = model;
		this.view = view;
		view.setController(this);
		view.getMainFrame().addKeyListener (this);
	}
	
	/**
	 * calls fuction of Model that gets detail configuration according to its coordinates
	 * @param x horizontal coordinate
	 * @param y vertical coorsinate
	 * @return Tetromino configuration to the class Controller
	 */
	public Tetromino callShapeAt (int x, int y)
	{
		return model.shapeAt(x, y);
	}
	
	/**
	 * calls function of Model that gets current X coordinate
	 * @return this coordinate to the class Controller
	 */
	public int callGetCurX ()
	{
		return model.getCurX();
	}
	
	/**
	 * calls function of Model that gets current Y coordinate
	 * @return this coordinate to the class Controller
	 */
	public int callGetCurY ()
	{
		return model.getCurY();
	}
	
	/**
	 * calls function of Model that gets current falling element
	 *  @return current falling element to the class Controller
	 */
	public Shape callCurDetail()
	{
		return model.getCurDetail();
	}
	
	/**
	 * call procedure start in model
	 */
	public void callStart()
	{
		model.start();
	}

	/**
	 * invoked when a key has been typed
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	/**
	 * invoked when a key has been pressed
	 * if there is no detail falling, return; otherwise make actions according to the pressed key
	 */
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (model.getCurDetail().getShape() == Tetromino.NOSHAPE)
		{
			model.restartGame();
			return;
		}
		
		int keycode = e.getKeyCode();
		
		switch (keycode)
		{
			case KeyEvent.VK_P:
				model.pause();
				model.update();
				break;
			case KeyEvent.VK_LEFT:
				model.tryMove(model.getCurDetail(), model.getCurX()-1, model.getCurY());
				break;
            case KeyEvent.VK_RIGHT: 
            	model.tryMove(model.getCurDetail(), model.getCurX()+1, model.getCurY());
				break;
            case KeyEvent.VK_DOWN: 
            	model.tryMove(model.getCurDetail().rotateRight(), model.getCurX(), model.getCurY());
				break;
            case KeyEvent.VK_UP: 
            	model.tryMove(model.getCurDetail().rotateLeft(), model.getCurX(), model.getCurY());
				break;
            case KeyEvent.VK_SPACE: 
            	model.dropDown();
				break;
            case KeyEvent.VK_D: 
           		model.oneLineDown();
				break;
		}
	}
	
	/**
	 * invoked when a key has been released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub	
	}
}
