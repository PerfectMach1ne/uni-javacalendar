package minicalendar;

import javax.swing.JFrame;

public class MiniCalendarPopup {
    private JFrame popupFrame = new JFrame();

    MiniCalendarPopup() {
        Main.alignWindowInCenter(popupFrame);
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.setTitle("Mini calendar");
        popupFrame.setResizable(true);
        popupFrame.add(new MiniCalendarBox());
        popupFrame.pack();

        popupFrame.setVisible(true);
    }
}
