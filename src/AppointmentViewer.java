import javax.swing.*;

/**
 * Created by Mike on 2017-02-21.
 */
public class AppointmentViewer {
    public static void main(String[] args) {
        JFrame frame = new AppointmentFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Appointments");
        frame.setVisible(true);
    }
}
