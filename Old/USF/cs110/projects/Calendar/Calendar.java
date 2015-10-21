
public class Calendar {

	private static Appointment[] calendar = new Appointment[100];
	private static int appointmentNum = 0;
	
	public Calendar() {
		calendar = new Appointment[100];
	}
	
	//time needs to be in seconds
	public static void addAppointment(String description, int date, int month,
			int year, int time) {
		
		Appointment temp = new Appointment(description, date, month, year,
				time);
		if (appointmentNum == 0) {calendar[0] = temp;}
		for (int i = 0; appointmentNum < i; i++){
			Appointment comp = calendar[i];
			if (comp.getYear() <= year && comp.getMonth() <= month &&
				comp.getDate() <= date && comp.getTime() <= time){
				 for (int x = appointmentNum; x >= i; x--){
					 calendar[x] = calendar[x++];
					 
				 }
				 calendar[i] = temp;
				 appointmentNum++;
				 return;
			}
		}
		calendar[appointmentNum++] = temp;
	}
	
	public void deleteAppointment(int index) {
		for(int i = index; i < appointmentNum; i++) {
			calendar[i] = calendar[i+1];
		}
		appointmentNum--;
	}
	
	//will be outdated once gui is in place
	public static void printCal() {
		for (int i = 0; i < appointmentNum; i++) {
			System.out.println(calendar[i].toString());
		}
	}
	
	public static void main(String[] args) {
		addAppointment("hello", 17, 3, 1999, 19800);
		addAppointment("hi", 17, 3, 2002, 19800);
		printCal();
	}
}

