import java.util.Scanner;
public class asdm {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.println("First number:");
		float n1 = scan.nextFloat();
		System.out.println("Second number:");
		float n2 = scan.nextFloat();

		System.out.println("Sum = " + (n1 + n2));
		System.out.println("Differance = " + (n1-n2));
		System.out.println("Product = " + (n1 * n2));
		System.out.println("Quotient = " + (n1 / n2));

	}

}
