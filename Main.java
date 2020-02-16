package tet;

import java.awt.EventQueue;

/**
 * public class with the main function that starts a program
 * @author Anastasiya
 *
 */
public class Main 
{
	private Model model;
	private  View view;
	private  Controller controller;
	
	/**
	 * constructor that creates objects of MVC classes
	 */
	Main ()
	{
		view = new View ();		
		model = new Model(view);
		controller = new Controller(model, view);
	}
	/**
	 * call Main class constructor that creates objects of MVC classes
	 * @param args standard 
	 */
	public static void main (String[] args)
	{
		EventQueue.invokeLater(()-> {
			
			new Main();		
		});
	}
}
