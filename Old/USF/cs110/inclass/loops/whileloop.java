package loops;
import java.util.Scanner; 
public class whileloop {
	
	public static void main (String[] args){
	
		Scanner s = new Scanner(System.in);
		System.out.println("Enter a positive number:");
		int num = s.nextInt();
		while (num <= 0) {
		
			System.out.println("Not a positive number");
			System.out.println("Enter a positive number");
			num = s.nextInt();
			
		}
		
	}
}
