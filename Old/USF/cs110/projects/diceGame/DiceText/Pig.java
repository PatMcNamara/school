package diceGame.DiceText;

public class Pig {
	
	public static void main(String[] args) {
		
		int player = 1;
		
		Interface UI = new Interface();
		computerPlayer AI = new computerPlayer();
		ScoreKeeper score = new ScoreKeeper();
		PairOfDice dice = new PairOfDice();
		UI.printTurn(player);
		
		while (!score.winner(player)) {
			
			dice.roll();
			System.out.println("Rolled a " + dice.getD1() + " and a " +
					dice.getD2());
				
			if (dice.SumOfDice() == 2) {
				
				score.addToPlayer(true, player);
				System.out.println("Player " + player + 
						" rolled snake eyes and looses all their points");
				if (player == 1) {player++;}
				else {player--;}
				score.printScores();
				UI.printTurn(player);
				
			} else if (dice.getD1() == 1 || dice.getD2() == 1) {
				
				score.addToCurrent(0);
				System.out.println("Player " + player +
						" rolled a one and looses their points for this turn");
				if (player == 1) {player++;}
				else {player--;}
				score.printScores();
				UI.printTurn(player);
			
			} else {
				
				if (player == 1) {
					
					score.addToCurrent(dice.SumOfDice());
					boolean Selection = AI.rollAgain(score.getCurrentScore());
					if (!Selection) {
						score.addToPlayer(false, player);
						player++;
						score.printScores();
						UI.printTurn(player);
					}
					
				} else {
				
					score.addToCurrent(dice.SumOfDice());
					boolean Selection = UI.askRoll(score.getCurrentScore());
					if (!Selection) {
						score.addToPlayer(false, player);
						player--;
						score.printScores();
						UI.printTurn(player);
					}
				}	
			}
			
		}
		score.addToPlayer(false, player);
		if (score.getPlayer1() >= 100) {System.out.println("Computer  wins");}
		else {System.out.println("Player wins");}	
	}
}
