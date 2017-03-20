//Name: Michael Teitelbaum
//Student ID: 500747561

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class AppointmentFrame extends JFrame {
    //Instance Variables
    private static int FRAME_WIDTH = 400;
    private static int FRAME_HEIGHT = 1000;
    private Calendar currentDate;
    private JLabel currentDateLabel, year, month, day, hour, minute, descrip;
    private SimpleDateFormat sdf;
    private ArrayList<Appointment> appointments;
    private JTextArea appointmentsTextArea;
    private JScrollPane scrollPane;
    private JPanel ctrlPanel, datePanel, appointmentPanel, but, da, time, but2, desc;
    private JButton next, previous, show, create, cancel;
    private JTextField d, m, y, h, min, description;

    //Constructor method calling all other methods to create the frame
    public AppointmentFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLayout(new BorderLayout());
        appointments = new ArrayList<>();
        WindowListener wl = new WL();
        addWindowListener(wl);
        readFile();
        createDate();
        createTextArea();
        createControlPanel();

    }

    //Method to get the current date label at the top of the panel
    private void createDate() {
        currentDate = new GregorianCalendar();
        sdf = new SimpleDateFormat("EEEE MMMM dd, yyyy");
        currentDateLabel = new JLabel(sdf.format(currentDate.getTime()));
        add(currentDateLabel, BorderLayout.NORTH);

    }

    //Method to create the text area to show all of the daily appointments
    private void createTextArea() {
        appointmentsTextArea = new JTextArea();
        appointmentsTextArea.setSize(400, 500);
        appointmentsTextArea.setEditable(false);
        putAppt();
        scrollPane = new JScrollPane(appointmentsTextArea);
        scrollPane.createVerticalScrollBar();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    //Method to create a panel to hold the interactive part of the GUI
    private void createControlPanel() {
        ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new GridLayout(2, 1));
        createDatePanel();
        ctrlPanel.add(datePanel);
        createAddApptPanel();
        ctrlPanel.add(appointmentPanel);
        add(ctrlPanel, BorderLayout.SOUTH);


    }

    //Method to create the panel that holds the options for setting the current date
    private void createDatePanel() {
        year = new JLabel("Year:");
        month = new JLabel("Month:");
        day = new JLabel("Day:");
        show = new JButton("Show");
        datePanel = new JPanel();
        datePanel.setLayout(new GridLayout(3, 1));
        datePanel.setBorder(new TitledBorder(new EtchedBorder(), "Date"));
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
        datePanel.add(but, 0);
        datePanel.add(da, 1);
        datePanel.add(show, 2);

    }

    //Method that creates the panel that holds the options to add appointments to the current date
    private void createAddApptPanel() {
        appointmentPanel = new JPanel();
        appointmentPanel.setLayout(new GridLayout(3, 1));
        appointmentPanel.setBorder(new TitledBorder(new EtchedBorder(), "Create"));
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
        appointmentPanel.add(time);
        appointmentPanel.add(desc);
        appointmentPanel.add(but2);

    }

    //Method to read from a data file with the appointments and add them all to the ArrayList
    public void readFile() {
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
            in.close();
            bin.close();
        } catch (Exception IOEx) {
            JOptionPane.showMessageDialog(new JFrame(), "Previous Data is Unavailable");
        }
    }

    //Mehtod to get the new appointment based on the values entered and adds it to the ArrayList
    private void apptDay() {
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int month = currentDate.get(Calendar.MONTH);
        int year = currentDate.get(Calendar.YEAR);
        int hour = Integer.parseInt(h.getText());
        int minute = 0;
        boolean doesntExists = true;
        String descrip = description.getText();
        if (!(min.getText().equals(""))) {
            minute = Integer.parseInt(min.getText());
        }
        Appointment appt = new Appointment(year, month, day, hour, minute, descrip);
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).compareTo(appt) == 0)
                doesntExists = false;
        }
        if (doesntExists) {

            appointments.add(appt);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Error:\nAppoointment at that time already exists.");
        }

        Collections.sort(appointments);
    }

    //Method to display the appointments in the TextArea
    private void putAppt() {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getDate().get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH) &&
                    appointments.get(i).getDate().get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                    appointments.get(i).getDate().get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)) {
                appointmentsTextArea.append(appointments.get(i).print());
                appointmentsTextArea.append("\n");
            }
        }
    }

    //ActionListener class for the previous date button
    class PreviousListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            currentDate.add(Calendar.DAY_OF_MONTH, -1);
            currentDateLabel.setText(sdf.format(currentDate.getTime()));
            appointmentsTextArea.setText("");
            putAppt();
        }
    }

    //ActionListener class for the next date button
    class NextListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            currentDate.add(Calendar.DAY_OF_MONTH, 1);
            currentDateLabel.setText(sdf.format(currentDate.getTime()));
            appointmentsTextArea.setText("");
            putAppt();
        }
    }

    //ActionListener for the show button which sets the current date to the value entered
    class ShowListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            int day = Integer.parseInt(d.getText());
            int month = Integer.parseInt(m.getText()) - 1;
            int year = Integer.parseInt(y.getText());
            d.setText("");
            m.setText("");
            y.setText("");
            currentDate.set(year, month, day);
            currentDateLabel.setText(sdf.format(currentDate.getTime()));
            appointmentsTextArea.setText("");
            putAppt();
        }
    }

    //ActionListener for the Create button which creates the appointment based on the values entered and the current date
    class CreateListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            apptDay();
            appointmentsTextArea.setText("");
            putAppt();
            h.setText("");
            min.setText("");
            description.setText("");
        }
    }

    //ActionListener for the Cancel button which cancels the appointment based ont he current date and the values entered
    class CancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int day = currentDate.get(Calendar.DAY_OF_MONTH);
            int month = currentDate.get(Calendar.MONTH);
            int year = currentDate.get(Calendar.YEAR);
            int hour = Integer.parseInt(h.getText());
            int minute = 0;
            boolean notRemoved = true;
            if (!(min.getText().equals(""))) {
                minute = Integer.parseInt(min.getText());
            }
            for (int i = 0; i < appointments.size(); i++) {
                if (appointments.get(i).occursOn(year, month, day, hour, minute)) {
                    appointments.remove(i);
                    notRemoved = false;
                }
            }
            if (notRemoved) {
                JOptionPane.showMessageDialog(new JFrame(), "Error:\nNo Appointment at that time.");
            }
            appointmentsTextArea.setText("");
            putAppt();
            h.setText("");
            min.setText("");
            description.setText("");
        }
    }


    //Class which handles window events
    class WL implements WindowListener {
        //Method triggered when the window is closing, takes all of the appointments in the ArrayList and stores it in a data file
        public void windowClosing(WindowEvent e) {
            System.out.println("CLOSED");
            FileWriter out;
            BufferedWriter bout;
            try {
                out = new FileWriter("Appointments.dat");
                bout = new BufferedWriter(out);
                bout.write("");
                for (int i = 0; i < appointments.size(); i++) {
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
            } catch (IOException IOEx) {
                JOptionPane.showMessageDialog(new JFrame(), "Error: writing to file");
            }
        }
        @Override
        public void windowActivated(WindowEvent e) {}
        public void windowClosed(WindowEvent e) {}
        public void windowDeactivated(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowOpened(WindowEvent e) {}
    }
}

