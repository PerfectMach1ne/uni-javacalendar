package javacalendar;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class Main {
    public static JFrame mainFrame;

    private static void createMainFrame() {
        mainFrame = new JFrame("JavaCalendar");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false);
        mainFrame.setSize(1400,850);

        addMainComponents();

        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);  /* Placing this after making the frame visible prevents it from getting
                                                 * sent to the bottom right corner for some reason */
    }

    private static void addMainComponents() {
        WeeksPanel weeksPanel = new WeeksPanel();
        LeftBarPanel leftBarPanel = new LeftBarPanel();
        MenuBar menuBar = new MenuBar();

        mainFrame.add(weeksPanel, BorderLayout.CENTER);
        mainFrame.add(leftBarPanel, BorderLayout.WEST);
        mainFrame.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Main::createMainFrame);
    }
}