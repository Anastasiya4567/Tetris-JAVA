package Pack1;

import java.awt.Color;
import javax.swing.*;

public class Menu {
	  JMenu menu, submenu;
	  JMenuItem i1,i2,i3;
	  Menu ()
	  {
		  JFrame f = new JFrame ("Menuu");
		  JMenuBar mb = new JMenuBar ();
		  menu = new JMenu ("Menu");
		  submenu = new JMenu ("Sub Menu");
		  i1=new JMenuItem ("Item 1");
		  i2=new JMenuItem ("Item 2");
		  i3=new JMenuItem ("Item 3");
		  menu.add (i1);
		  menu.add (i2);
		  submenu.add (i3);
		  menu.add (submenu);
		  mb.add (menu);
		  f.setJMenuBar (mb);
		  f.setSize (400,400);
		  f.setLayout (null);
		  f.setVisible (true);
	  	 }
}