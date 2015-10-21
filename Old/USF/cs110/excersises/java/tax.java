import java.util.Scanner;
public class tax {
    public static void main(String[] args) {
	//A program to calculate tax and total cost for an item.
	//constant rate of taxation
	final double TAX_RATE = .0825;

	Scanner s = new Scanner(System.in);
	System.out.println("Enter item cost: ");
	
	//ask user for the cost of the item
	double cost = s.nextDouble();

	//calculate the tax
	double tax = cost*TAX_RATE;

	//calculate total cost
	double total = cost+tax;

	System.out.println("Cost: " + cost);
	System.out.println("Tax : " + tax);
	System.out.println("Total: " + total);
    }
}
