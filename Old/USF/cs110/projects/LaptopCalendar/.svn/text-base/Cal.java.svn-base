import java.util.Calendar;
//keeps track of the appointments
public class Cal {

	//creates an array and an array counter for appointment objects
	private Appointment[] calendar = new Appointment[100];
	private int appointmentNum = 0;
	
	//only 100 appointments allowed, could easly be increased
	public Cal() {
		calendar = new Appointment[100];
	}

	//adds an appointment and keeps them sorted
	public void addAppointment(String description, Calendar cal, int time) {
		
		Appointment temp = new Appointment(description, cal, time);
		Calendar date = temp.getCalendar();
		if (appointmentNum == 0) {calendar[0] = temp;}
		for (int i = 0; appointmentNum < i; i++){
			Appointment comparison = calendar[i];
			Calendar comp = comparison.getCalendar();
			if (comp.get(1) <= date.get(1) && comp.get(2) <= date.get(2) &&
				comp.get(5) <= date.get(5) && comparison.getIntTime() <= temp.getIntTime()){
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
	
	//deletes an appointment
	public void deleteAppointment(int index) {
		for(int i = index; i < appointmentNum; i++) {
			calendar[i] = calendar[i+1];
		}
		appointmentNum--;
	}
	public int getAppointmentNum() {
		return appointmentNum;
	}
	public Appointment getDate(int index) {
		return calendar[index];
	}
}