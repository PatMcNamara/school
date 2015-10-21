package diceGame.diceGUI;

public class ScoreKeeper {
	
	//initialization and assignment of scores
	int player1 = 0;
	int player2 = 0;
	int currentScore = 0;
	
	//Adds a value to the current score, if the value is 0, it resets the score
	public void addToCurrent(int newAmount) {
		
		if (newAmount == 0) {currentScore = 0;}
		else {currentScore += newAmount;}
		
	}
	
	//Adds current value to player score, zero is used to erase player score
	public void addToPlayer (boolean zero, int player) {
		
		if (player == 1) {
			
			if (zero) {player1 = 0;}
			else {player1 += currentScore;}
			
		} else {
			
			if (zero) {player2 = 0;}
			else {player2 += currentScore;}
			
		}
		currentScore = 0;
	}
	
	//takes in who has the current score points returns if someone has over
	//100 points
	public boolean winner(int player) {
		if (player == 1) {return player1 + currentScore >= 100;}
		else if (player == 2) {return player2 + currentScore >= 100;}
		else {return false;}
	}
		
	public int getPlayer1() {
		return player1;
	}
	public int getPlayer2() {
		return player2;
	}
	public int getCurrentScore() {
		return currentScore;
	}
	
}
