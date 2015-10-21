package diceGame.diceGUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Interface extends JPanel implements ActionListener {
	
	//decalres dice variables and whos turn it is
	private int D1;
	private int D2;
	private int player = 1;
	
	//creates a score keeper and pair of dice
	private ScoreKeeper score = new ScoreKeeper();
	private PairOfDice dice = new PairOfDice();
	
	//declares and initializes dice face image icons 
	private ImageIcon blank = new ImageIcon("Blank.gif");
	private ImageIcon one = new ImageIcon("One.gif");
	private ImageIcon two = new ImageIcon("Two.gif");
	private ImageIcon three = new ImageIcon("Three.gif");
	private ImageIcon four = new ImageIcon("Four.gif");
	private ImageIcon five = new ImageIcon("Five.gif");
	private ImageIcon six = new ImageIcon("Six.gif");
	
	//creates 2 new buttons
	private JButton rollAgain = new JButton("Roll Again");
	private JButton passDice = new JButton("Pass the Dice");
	
	//creates labes for player and current scores
	private JLabel p1Score;
	private JLabel p2Score;
	private JLabel currentScore;
	
	//creates label for players turn
	private JLabel playerLabel;
	
	//creates labels to hold each die value
	private JLabel Die1;
	private JLabel Die2;
	
	//creates pannels for current player, buttons and scores
	private JPanel playerPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel scores = new JPanel();
	
	//constructs interface
	public Interface() {
		
		//sets look and feal of window to default of the OS it is running on
        try {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        //sets the background color and layout of panel
		setBackground (Color.GREEN);
		setLayout (new BorderLayout());
		
		//SETS UP BUTTON PANEL
		//adds and action listener to each button (both use same listener),
		//then adds buttons to buttonPanel, sets buttonPanel color to same 
		//color as the main panel background and enables the buttons
		rollAgain.addActionListener(this);
		passDice.addActionListener(this);
		buttonPanel.add(rollAgain);
		buttonPanel.add(passDice);
		rollAgain.setEnabled(false);
		passDice.setEnabled(false);
		buttonPanel.setBackground(Color.GREEN);
		
		//SETS UP SCORE PANEL
		//initializes score panels to labels with 0 values
		p1Score = new JLabel ("Player 1 score: 0");
		p2Score = new JLabel ("Player 2 score: 0");
		currentScore = new JLabel ("Points this turn: 0");
		
		//puts scores labels into scores panel and sets background of scores
		scores.add(p1Score);
		scores.add(p2Score);
		scores.add(currentScore);
		scores.setBackground(Color.green);
		
		//SETS UP PLAYER LABEL AND PANNEL
		playerLabel = new JLabel("Player 1's Turn", SwingConstants.CENTER);
		playerPanel.add(playerLabel);
		
		
		//SETS UP DICE PANELS
		//initializes Die panels to blank die images
		Die1 = new JLabel(blank);
		Die2 = new JLabel(blank);
		
		//adds panels created above to interface panel that is later added
		//to the frame
		add(scores, BorderLayout.SOUTH);
		add(Die1, BorderLayout.EAST);
		add(Die2, BorderLayout.WEST);
		add(buttonPanel, BorderLayout.CENTER);
		add(playerLabel, BorderLayout.NORTH);
		
	}
	
	//first called in main to let computer take first turn, after that it
	//is called after players turn ends
	//not in computerPlayer class because of number of objects referanced and
	//interface changes
	public void computerTurn() {
		
		//disables buttons while computer takes their turn
		rollAgain.setEnabled(false);
		passDice.setEnabled(false);
		//checks to see if it should roll again
		while (computerPlayer.rollAgain(score.getCurrentScore())) {
			//rolls the dice, the roll method will return true if a one
			//one was rolled
			if (roll()) {break;}
		}
		
		//this is only true if current score is greater then 20, in any other
		//case a 1 was rolled and scores have already been set in roll method
		if (!computerPlayer.rollAgain(score.getCurrentScore())) {
			
			//adds the current score to players score, updates display,
			//tells user the dice where passed and then changes to other
			//players turn and updates display acordingly
			score.addToPlayer(false, player);
			updateScore();
			JOptionPane.showMessageDialog(null, "Computer passes the dice.");
			switchPlayer(++player);
			
		}
		
		//enables buttons prepairing for players turn
		rollAgain.setEnabled(true);
		passDice.setEnabled(true);
		
		//rolles dice for player and if it has a 1 calls computer turn again
		if (roll()) {computerTurn();}
		//doesn't call anything, it waits for user imput
	}
	
	//returns true if player rolled at least one 1
	public boolean roll() {
		
		//rolls and updates dice, then adds the dice to the current score
		dice.roll();
		updateDice();
		score.addToCurrent(dice.SumOfDice());
		//checks if the player rolled two ones
		if (dice.SumOfDice() == 2) {
			
			//zeros and updates player score, displays a snake eyes message
			//then changes player
			score.addToPlayer(true, player);
			updateScore();
			JOptionPane.showMessageDialog(null, "Player " + player +
					" rolled snake eyes and lost all their points.");
			if (player == 1) {player++;}
			else {player--;}
			switchPlayer(player);
			return true;
			
		} else if (dice.getD1() == 1 || dice.getD2() == 1) {
			
			//runs if only one one is rolled.  Zeros and updates current score
			//displays dialog informing user, switches and updates player
			score.addToCurrent(0);
			updateScore();
			JOptionPane.showMessageDialog(null, "Player " + player +
				" rolled a one and lost all their points for this turn.");
			if (player == 1) {player++;}
			else {player--;}
			switchPlayer(player);
			return true;
			
		} else {
			//checks to see if the player has won
			if (score.winner(player)) {
				//adds current to player, shows someone won, then exits
				score.addToPlayer(false, player);
				showWinner();
				System.exit(0);
			}
			//updates score and returns
			updateScore();
			return false;
		}
	}
	
	//updates dice display to show current die faces
	private void updateDice() {
		
		//gets value of dice
		D1 = dice.getD1();
		D2 = dice.getD2();
		
		//updates icon of dice for each die
		if (D1 == 1) {Die1.setIcon(one);}
		else if (D1 == 2) {Die1.setIcon(two);}
		else if (D1 == 3) {Die1.setIcon(three);}
		else if (D1 == 4) {Die1.setIcon(four);}
		else if (D1 == 5) {Die1.setIcon(five);}
		else if (D1 == 6) {Die1.setIcon(six);}
		
		if (D2 == 1) {Die2.setIcon(one);}
		else if (D2 == 2) {Die2.setIcon(two);}
		else if (D2 == 3) {Die2.setIcon(three);}
		else if (D2 == 4) {Die2.setIcon(four);}
		else if (D2 == 5) {Die2.setIcon(five);}
		else if (D2 == 6) {Die2.setIcon(six);}
 	}
	
	//updates points labels
	private void updateScore() {
		currentScore.setText("Points this turn: " + score.getCurrentScore());
		p1Score.setText("Player 1 Score: " + score.getPlayer1());
		p2Score.setText("Player 2 Score: " + score.getPlayer2());
	}
	
	//updates player label, run at the end of a turn
	public void switchPlayer(int player) {
		playerLabel.setText("Player " + player +"'s Turn");
	}
	
	//Displayes who won in a dialog box
	public void showWinner() {
		if (score.getPlayer1() < score.getPlayer2()) {
			JOptionPane.showMessageDialog(null, "PLAYER WINS");
		} else {
			JOptionPane.showMessageDialog(null, "COMPUTER WINS");
		}
	}
	
	//listens for button clicks
	public void actionPerformed (ActionEvent event) {
		if (event.getSource() == rollAgain) {
			 if (roll()) {computerTurn();} //rolls again and if a 1 is rolled
			                               //calls computer player
		} else {
			//adds current score to player total, switches player, updates
			//display, calls computer turn
			score.addToPlayer(false, player);
			updateScore();
			switchPlayer(--player);
			computerTurn();
		}
	}
}