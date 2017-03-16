import sun.awt.WindowClosingListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
        WindowListener wl = new WL();
        addWindowListener(wl);
        readFile();
        createDate();
        createTextArea();
        createControlPanel();

    }

    //Done Don't Touch
    private void createDate() {
        currentDate = new GregorianCalendar();
        sdf = new SimpleDateFormat("EEEE MMMM dd, yyyy");
        jl = new JLabel(sdf.format(currentDate.getTime()));
        add(jl, BorderLayout.NORTH);

    }

    private void createTextArea() {
        ta = new JTextArea();
        ta.setSize(400, 500);
        //ta.setEditable(false);
        putAppt();
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
        da = new JPanel(new GridLayout(1, 3));
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

    private void createAddApptPanel() {
        ap = new JPanel();
        ap.setLayout(new GridLayout(3, 1));
        time = new JPanel();
        time.setLayout(new GridLayout(1, 2));
        but2 = new JPanel();
        but2.setLayout(new GridLayout(1, 2));
        desc = new JPanel();
        hour = new JLabel("Hour (0-23) :");
        minute = new JLabel("Minute:");
        h = new JTextField();
        min = new JTextField();
        create = new JButton("Create");
        cancel = new JButton("Cancel");
        ActionListener ccl = new CancelListener();
        cancel.addActionListener(ccl);
        descrip = new JLabel("Description");
        description = new JTextField();
        description.setPreferredSize(new Dimension(200, 50));
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
            ta.setText("");
            putAppt();
        }
    }

    class NextListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            currentDate.add(Calendar.DAY_OF_MONTH, 1);
            jl.setText(sdf.format(currentDate.getTime()));
            ta.setText("");
            putAppt();
        }
    }

    class ShowListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            int day = Integer.parseInt(d.getText());
            int month = Integer.parseInt(m.getText()) - 1;
            int year = Integer.parseInt(y.getText());
            currentDate.set(year, month, day);
            jl.setText(sdf.format(currentDate.getTime()));
        }
    }

    class CreateListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            apptDay();
            ta.setText("");
            putAppt();
            h.setText("");
            min.setText("");
            description.setText("");
        }
    }
    class CancelListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            int month = currentDate.get(Calendar.MONTH);
            int year = currentDate.get(Calendar.YEAR);
            int hour = Integer.parseInt(h.getText());
            int minute = 0;
            if (!(min.getText().equals(""))) {
                minute = Integer.parseInt(min.getText());
            }
            for(int i = 0; i < appointments.size(); i++){
                if(appointments.get(i).occursOn(year, month, day, hour, minute)){
                    appointments.remove(i);
                }
            }
            ta.setText("");
            putAppt();
            h.setText("");
            min.setText("");
            description.setText("");
        }
    }

    private void apptDay() {
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int month = currentDate.get(Calendar.MONTH);
        int year = currentDate.get(Calendar.YEAR);
        int hour = Integer.parseInt(h.getText());
        int minute = 0;
        if (!(min.getText().equals(""))) {
            minute = Integer.parseInt(min.getText());
        }
        String descrip = description.getText();
        Appointment appt = new Appointment(year, month, day, hour, minute, descrip);
        appointments.add(appt);
        Collections.sort(appointments);
    }

    private void putAppt() {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getDate().get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH) &&
                    appointments.get(i).getDate().get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                    appointments.get(i).getDate().get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)) {
                ta.append(appointments.get(i).print());
                ta.append("\n");
            }
        }}

        public void readFile(){
            try {
                FileReader in = new FileReader("Appointments.dat");
                BufferedReader bin = new BufferedReader(in);
                String line;
                while ((line = bin.readLine()) != null) {
                    System.out.println(line);
                    String[] l = line.split("\\|");
                    System.out.println(l[0] + l[1] + l[2] + l[3] + l[4] + l[5]);
                    Appointment appt = new Appointment(Integer.parseInt(l[0]), Integer.parseInt(l[1]), Integer.parseInt(l[2]), Integer.parseInt(l[3]), Integer.parseInt(l[4]), l[5]);
                    System.out.println(appt.print());
                    appointments.add(appt);
                }

            } catch(Exception IOEx) {
                System.out.println(IOEx.getMessage());
            }
    }
class WL implements WindowListener {
    public void windowClosing(WindowEvent e)
    {
        System.out.println("CLOSED");
        FileWriter out;
        BufferedWriter bout;
        try {
            out = new FileWriter("Appointments.dat");
            bout = new BufferedWriter(out);
            bout.write("");
            for(int i = 0; i < appointments.size(); i++) {
                bout.append(appointments.get(i).getDate().get(Calendar.YEAR) + "|"
                        + appointments.get(i).getDate().get(Calendar.MONTH) + "|"
                        + appointments.get(i).getDate().get(Calendar.DAY_OF_MONTH) + "|"
                        + appointments.get(i).getDate().get(Calendar.HOUR_OF_DAY) + "|"
                        + appointments.get(i).getDate().get(Calendar.MINUTE) + "|"
                        + appointments.get(i).getDescription());
                bout.newLine();
            }
            bout.close();
            out.close();
        } catch(IOException IOEx) {
            JOptionPane.showMessageDialog(new JFrame(), IOEx.getMessage());
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }
}
    }

