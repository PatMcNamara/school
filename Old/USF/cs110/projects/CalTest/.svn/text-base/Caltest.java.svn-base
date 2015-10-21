import java.util.*;

public class Caltest {

	GregorianCalendar cal;
	
	public Caltest(){
		cal = new GregorianCalendar(Locale.US);
	}
	public Caltest(TimeZone zone){
		cal = new GregorianCalendar(zone, Locale.US);
	}
	public String toString(){
		return cal.getTime().toString();
	}
	public String ID(){
		return cal.getTimeZone().getID();
	}
	public String DisplayName(){
		return cal.getTimeZone().getDisplayName();
	}
}
