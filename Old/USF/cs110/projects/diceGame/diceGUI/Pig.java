package diceGame.diceGUI;
/*
 * Dice Game Project (now GUItastic!)
 * Patrick McNamara
 */

import javax.swing.JFrame;


public class Pig {
	
	public static void main(String[] args) {
		
		//Creates a new interface object with basic interface setup
		Interface UI = new Interface();
		
		//Creates a new frame
		JFrame frame = new JFrame("Pig");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//puts interface into frame
		frame.getContentPane().add(UI);
		frame.pack();
		frame.setVisible(true);
		
		//Done building window
		//Begins running main program
		UI.computerTurn();
		
	}
}