import java.util.*;
import java.text.*;
//holds individual appointments
public class Appointment {
	
	DateFormat format = DateFormat.getDateTimeInstance();

	private String description;
	//Calendar is use because the Java API is stupid and the date class is only
	//used on absolute times from midnight Jan 1, 1970
	//Calendar object is instatiated to default time zone and location
	private Calendar date;
	private int time;
	
	//creates a new appointment
	public Appointment (String description, Calendar DMY, int time) {
		
		this.description = description;
		date = DMY;
		this.time = time;
		int minute = time % 60;
		int hours = time / 60;
		date.set(11,hours);
		date.set(12,minute);
	}
	//getters and setters
	public int getIntTime(){
		return time;
	}
	public Calendar getCalendar(){
		return date;
	}
	public String getDate() {
		return format.format(date.getTime());
	}
	public String getTime(){
		return "";
	}
	public String getDescription(){
		return description;
	}
}
