import java.util.Scanner;
public class time2 {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.println("Seconds:");
		int sec = scan.nextInt();
		int min = (sec - (sec % 60)) / 60;
		sec %= 60;
		int hours = (min - (min % 60)) /60;
		min %= 60;

		System.out.println(hours + " hours " + min + " minutes " + sec + " seconds");
	}
}
