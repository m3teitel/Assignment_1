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
    private JLabel jl, year, month, day, hour, minute, descrip;
    private SimpleDateFormat sdf;
    private ArrayList<Appointment> appointments;
    private JTextArea ta;
    private JScrollBar sb;
    private JPanel cp, dp, ap, but, da, time, but2, desc;
    private JButton next, previous, show, create, cancel;
    private JTextField d, m, y, h, min, description;

    public AppointmentFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLayout(new BorderLayout());
        appointments = new ArrayList<Appointment>();
        createDate();
        createTextArea();
        createControlPanel();

    }

    private void createDate() {
        currentDate = new GregorianCalendar();
        sdf = new SimpleDateFormat("EEEE MMMM dd, yyyy");
        jl = new JLabel(sdf.format(currentDate.getTime()));
        add(jl, BorderLayout.NORTH);

    }

    private void createTextArea() {
        ta = new JTextArea();
        ta.setSize(400,500);
        ta.setEditable(false); for (int i = 0; i > appointments.size(); i++) {
            ta.append(appointments.toString());
            ta.append("\n");
        }
        JScrollPane scrollPane = new JScrollPane(ta);
        scrollPane.createVerticalScrollBar();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createControlPanel() {
        cp = new JPanel();
        cp.setLayout(new GridLayout(2, 1));
        createDatePanel();
        cp.add(dp);
        createAddApptPanel();
        cp.add(ap);
        add(cp, BorderLayout.SOUTH);


    }

    private void createDatePanel() {
        year = new JLabel("Year:");
        month = new JLabel("Month:");
        day = new JLabel("Day:");
        show = new JButton("Show");
        dp = new JPanel();
        dp.setLayout(new GridLayout(3, 1));
        dp.setBorder(new TitledBorder(new EtchedBorder(), "Date"));
        but = new JPanel();
        da = new JPanel(new GridLayout(1,3));
        next = new JButton(">");
        next.setSize(500, 300);
        previous = new JButton("<");
        previous.setSize(500, 300);
        ActionListener pl = new PreviousListener();
        previous.addActionListener(pl);
        ActionListener nl = new NextListener();
        next.addActionListener(nl);
        ActionListener sl = new ShowListener();
        show.addActionListener(sl);
        d = new JTextField("");
        m = new JTextField("");
        y = new JTextField("");
        d.setSize(10, 1);
        m.setSize(10, 1);
        y.setSize(10, 1);
        but.add(previous);
        but.add(next);
        da.add(year);
        da.add(y);
        da.add(month);
        da.add(m);
        da.add(day);
        da.add(d);
        dp.add(but, 0);
        dp.add(da, 1);
        dp.add(show, 2);

    }
    private void createAddApptPanel(){
        ap = new JPanel();
        ap.setLayout(new GridLayout(3, 1));
        time = new JPanel();
        time.setLayout(new GridLayout(1,2));
        but2 = new JPanel();
        but2.setLayout(new GridLayout(1,2));
        desc = new JPanel();
        hour = new JLabel("Hour (0-23) :");
        minute = new JLabel("Minute:");
        h = new JTextField();
        min = new JTextField();
        create = new JButton("Create");
        cancel = new JButton("Cancel");
        descrip = new JLabel("Description");
        description = new JTextField();
        description.setPreferredSize(new Dimension(200,50));
        ActionListener cl = new CreateListener();
        create.addActionListener(cl);
        time.add(hour);
        time.add(h);
        time.add(minute);
        time.add(min);
        but2.add(create);
        but2.add(cancel);
        desc.add(descrip);
        desc.add(description);
        ap.add(time);
        ap.add(desc);
        ap.add(but2);

    }
    class PreviousListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            currentDate.add(Calendar.DAY_OF_MONTH, -1);
            jl.setText(sdf.format(currentDate.getTime()));
        }
    }

    class NextListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            currentDate.add(Calendar.DAY_OF_MONTH, 1);
            jl.setText(sdf.format(currentDate.getTime()));
        }
    }
    class ShowListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            int day = Integer.parseInt(d.getText());
            int month = Integer.parseInt(m.getText()) - 1;
            int year = Integer.parseInt(y.getText());
            currentDate.set(year, month, day);
            jl.setText(sdf.format(currentDate.getTime()));
        }
    }
    class CreateListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            int month = currentDate.get(Calendar.MONTH) - 1;
            int year = currentDate.get(Calendar.YEAR);
            int hour = Integer.parseInt(h.getText());
            int minute = 0;
            if(!(min.getText().equals(""))){
                minute = Integer.parseInt(min.getText());
            }
            String descrip = description.getText();
            Appointment appt = new Appointment(year, month, day, hour, minute, descrip);
            appointments.add(appt);
            ta.append(appt.print());
        }
    }
}
