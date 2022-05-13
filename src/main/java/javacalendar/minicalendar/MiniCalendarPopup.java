package javacalendar.minicalendar;

import javax.swing.JFrame;

import javacalendar.Main;

public class MiniCalendarPopup {
    private JFrame popupFrame = new JFrame();

    public MiniCalendarPopup() {
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.setTitle("Mini calendar");
        popupFrame.setResizable(true);
        popupFrame.add(new MiniCalendarBox());
        popupFrame.pack();

        popupFrame.setVisible(true);
        popupFrame.setLocationRelativeTo(null);
    }
}
