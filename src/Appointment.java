import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Mike on 21-Feb-17.
 */
//Appointment Class taking the Year, Month, Day, Hour, Minute, and Description of an appointment
public class Appointment implements Comparable<Appointment> {
    //Instance Variables of the Date and Description of the appointment
    private Calendar date;
    private String description;
    //Constructor method that takes in ints for the year, month, day, hour and minute to set the date and takes in a String to set the description to
    public Appointment(int year, int month, int day, int hour, int minute, String description) {
        date = new GregorianCalendar(year, month, day, hour, minute);
        this.description = description;
    }
    //Getter method for the date
    public Calendar getDate() {
        return date;
    }
    //Getter method for the description
    public String getDescription() {
        return description;
    }
    //Setter method for the date
    public void setDate(int year, int month, int day, int hour, int minute) {
        date.set(year, month, day, hour, minute);
    }
    //Setter method for the description
    public void setDescription(String des) {
        description = des;
    }
    //Print method returning a String representation of the appointment in the form "HH:MM" + description
    public String print() {
        return String.format("%02d", date.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", date.get(Calendar.MINUTE)) + " " + description;
    }
    //Occurs on method which checks if two appointments are at the same time
    public boolean occursOn(int year, int month, int day, int hour, int minute) {
        Calendar comp = new GregorianCalendar(year, month, day, hour, minute);
        return date.equals(comp);
    }
    //Compare to method taking in another appointment and comparing the two
    public int compareTo(Appointment other) {
        return date.compareTo(other.getDate());
    }

}
