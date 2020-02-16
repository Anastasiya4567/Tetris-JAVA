package Pack1;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings ("serial")
public class WindowColor extends JFrame {
	JPanel panel; 
	Color backgrColor = Color.WHITE;
	public WindowColor() {
		panel = new JPanel (new BorderLayout());
		
		JButton bttnColor = new JButton ("Change color");
		panel.add(bttnColor, BorderLayout.SOUTH);
		bttnColor.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent evt) {
				Color color = JColorChooser.showDialog(WindowColor.this, "Choose a color", backgrColor);
				if (color!= null) {
					backgrColor = color;
				}
				panel.setBackground(backgrColor);
			}
		});
		
		setContentPane (panel);
		
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setTitle ("WindowColor Demo");
		setSize (300, 200);
		setLocationRelativeTo (null);
		setVisible (true);		
	}
	
}
