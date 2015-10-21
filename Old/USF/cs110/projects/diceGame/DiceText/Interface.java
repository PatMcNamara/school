package diceGame.DiceText;
import java.util.Scanner;

public class Interface {
	
	Scanner entry = new Scanner(System.in);
	
	public void printTurn(int player) {
		System.out.println("Player " + player + "'s turn");
	}
	
	public boolean askRoll(int currentScore) {
		System.out.println("You have " + currentScore +
				" points for this round, would you like to roll again?");
		String selection = entry.next().toLowerCase();
		if (selection.charAt(0) == 'y') {return true;}
		else {return false;}
	}
	
}
