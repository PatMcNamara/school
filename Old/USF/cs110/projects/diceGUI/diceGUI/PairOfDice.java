package diceGame.diceGUI;

public class PairOfDice {
	
	//creates 2 new die objects
	Die D1 = new Die();
	Die D2 = new Die();
	
	//constructor
	public PairOfDice() {
		this.roll(); //rolls each of the dice
	}
	
	//rolls the dice
	public void roll() {
		D1.roll();
		D2.roll();
	}

	//returns the sum of the dice
	public int SumOfDice() {
		return (D1.getFaceValue() + D2.getFaceValue());	
	}

	public int getD1() {
		return D1.getFaceValue();
	}

	public int getD2() {
		return D2.getFaceValue();
	}
	
}
