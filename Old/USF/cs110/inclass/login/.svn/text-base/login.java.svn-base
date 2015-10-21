package login;
import java.util.Scanner;
import java.util.Random;

public class login {

	private String firstname;
	private String lastname;
	Random rand = new Random();
	Scanner input = new Scanner(System.in);

	public void printlogin() {
	
		System.out.println("Enter First Name:");
		firstname = input.toString();
		System.out.println("Enter Last Name:");
		lastname = input.toString();
		
		if (lastname.length() < 5){
			System.out.println(firstname.charAt(0) + lastname.toString()  +
					(10 + rand.nextInt(41)));
		} else {
			System.out.println(firstname.charAt(0) + lastname.substring(0, 4) +
					(10 + rand.nextInt(41)));
		}

	}
	
}