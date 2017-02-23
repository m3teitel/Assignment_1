import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Mike on 21-Feb-17.
 */
public class Appointment implements Comparable<Appointment> {
    private Calendar date;
    private String description;

    public Appointment(int year, int month, int day, int hour, int minute, String description){
        date = new GregorianCalendar(year, month, day, hour, minute);
        this.description = description;
    }
    public Calendar getDate(){
        return date;
    }
    public String getDescription(){
        return description;
    }
    public void setDate(int year, int month, int day, int hour, int minute){
        date.set(year, month, day, hour, minute);
    }
    public void setDescription(String des){
        description = des;
    }
    public String print(){
        return Integer.toString(date.get(Calendar.HOUR_OF_DAY)) + ":" + Integer.toString(date.get(Calendar.MINUTE)) + " " + description;
    }
    public boolean occurson(int year, int month, int day, int hour, int minute) {
        Calendar comp = new GregorianCalendar(year, month, day, hour, minute);
        return date.equals(comp);
    }

    public int compareTo(Appointment other){
       return date.compareTo(other.getDate());
    }

}
