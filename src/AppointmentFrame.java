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
    private static int FRAME_WIDTH = 400;
    private static int FRAME_HEIGHT = 1000;
    private Calendar currentDate;
    private JLabel jl;
    private SimpleDateFormat sdf;
    private ArrayList<Appointment> appointments;
    private JTextArea ta;
    private JScrollBar sb;
    private JPanel cp, dp, ap, desp, but, da;
    private JButton next, previous, show, create, cancel;
    private JTextField d, m, y, h, min;

    public AppointmentFrame(){
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLayout(new BorderLayout());
        appointments = new ArrayList<Appointment>();
        createDate();
        createTextArea();
        createControlPanel();
        add(cp);

    }
    private void createDate(){
        currentDate = new GregorianCalendar();
        sdf = new SimpleDateFormat("EEEE MMMM dd, yyyy");
        jl = new JLabel(sdf.format(currentDate.getTime()));
        add(jl, BorderLayout.NORTH);

    }
    private void createTextArea(){
        ta = new JTextArea();
       // ta.setSize(700,600);
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
        cp.setLayout(new GridLayout(2,1));
        createDatePanel();
        cp.add(dp);


    }
    private void createDatePanel(){
        dp = new JPanel();
        dp.setLayout(new GridLayout(2,1));
        dp.setBorder(new TitledBorder(new EtchedBorder(), "Date"));
        but = new JPanel();
        da = new JPanel();
        next = new JButton(">");
        next.setSize(500,300);
        previous = new JButton("<");
        previous.setSize(500,300);
        ActionListener pl = new PreviousListener();
        previous.addActionListener(pl);
        ActionListener nl = new NextListener();
        next.addActionListener(nl);
        d = new JTextField();
        m = new JTextField();
        y = new JTextField();
        but.add(previous);
        but.add(next);
        da.add(d);
        da.add(m);
        da.add(y);
        dp.add(but, 0);
        dp.add(da, 1);

    }
    class PreviousListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            currentDate.add(Calendar.DAY_OF_MONTH, -1);
            jl.setText(sdf.format(currentDate.getTime()));
        }
    }
    class NextListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            currentDate.add(Calendar.DAY_OF_MONTH, 1);
            jl.setText(sdf.format(currentDate.getTime()));
        }
    }
}
