import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Mike on 2017-02-21.
 */
public class AppointmentFrame extends JFrame {
    private static int FRAME_WIDTH = 800;
    private static int FRAME_HEIGHT = 1000;
    private Calendar currentDate;
    private JLabel jl;
    private SimpleDateFormat sdf;
    private ArrayList<Appointment> appointments;
    private JTextArea ta;
    private JScrollBar sb;
    private JPanel cp, dp, ap, desp;
    private JButton next, previous, show, create, cancel;
    private JTextField d, m, y, h, min;

    public AppointmentFrame(){
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        appointments = new ArrayList<Appointment>();
        createDate();
        createTextArea();
        createControlPanel();

    }
    private void createDate(){
        currentDate = new GregorianCalendar();
        sdf = new SimpleDateFormat("EEEE MMMM dd, yyyy");
        jl = new JLabel(sdf.format(currentDate.getTime()));
        add(jl, BorderLayout.NORTH);
    }
    private void createTextArea(){
        ta = new JTextArea();
        sb = new JScrollBar();
        ta.add(sb);
        for(int i = 0; i > appointments.size(); i++){
            ta.append(appointments.toString());
            ta.append("\n");
        }
        add(ta, BorderLayout.CENTER);
    }
    private void createControlPanel(){
        cp = new JPanel();
        createDatePanel();


    }
    private void createDatePanel(){
        dp = new JPanel();
        dp.setBorder(new TitledBorder(new EtchedBorder(), "Date"));
        next = new JButton(">");
        previous = new JButton("<");
        d = new JTextField("Day");
        m = new JTextField("Month");
        y = new JTextField("Year");
    }
    class PreviousListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            currentDate.add(Calendar.DAY_OF_MONTH, -1);
            jl.setText(sdf.format(currentDate));
        }
    }
}
