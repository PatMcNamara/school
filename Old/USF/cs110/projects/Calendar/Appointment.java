
public class Appointment {

	private String description;
	private int month;
	private int date;
	private int year;
	private int time;
	
	public Appointment (String description, int month, int date, int year,
			int time) {
		
		this.description = description;
		this.month = month;
		this.date = date;
		this.year = year;
		this.time = time;
		
	}
		
	public int getDate() {
		return date;
	}

	public int getMonth() {
		return month;
	}

	public int getTime() {
		return time;
	}

	public int getYear() {
		return year;
	}

	public String toString() {
		
		if (description.length() <=20){
			return description.toString() + " at " + formatTime() + " on " +
					month + "/" + date + "/" + year;
		}
		return description.substring(0,20) + " at " + formatTime() + " on " +
				month + "/" + date + "/" + year;
		
	}
	
	private String formatTime() {
		
		int seconds = time % 60;
		int minlong = (time - seconds) / 60;
		int minuts = minlong % 60;
		int hours = (minlong - minuts) / 60;
		
		return hours + ":" + minuts + ":" + seconds;
		
	}
}
