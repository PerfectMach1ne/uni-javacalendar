//
// The worst class in the entire app.
// Very likely overdue for a complete rewrite, because this is some stinky shit.
//
package javacalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javacalendar.util.StringConstants;

public class WeeksPanel extends JPanel implements ComponentListener {
    private final int PARENT_PANEL_WIDTH = 1122;
    private final int PARENT_PANEL_HEIGHT = 730;
    private final int WEEKDAY_PANEL_WIDTH = 160;
    private final int WEEKDAY_PANEL_HEIGHT = 730;

    private JPanel weekdayContainer;
    private JPanel weekdayLabelPanel;
    private JPanel hourLabelPanel;
    private JLabel[] weekdayLabelArray = new JLabel[8];
    private JLabel[] hourLabelArray = new JLabel[24];
    public static final JPanel[] weekdayPanelArray = new JPanel[7];

    public WeeksPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.gray);

        // Create and add panel for days of the week
        createWeekdayLabelPanel();
        this.add(weekdayLabelPanel, BorderLayout.NORTH);

        // Label the days of the week
        /* This is disgusting but it allows the JLabels to at least imitate correct position.
           I have to fix this in the future, because this feels more criminal than using fflush(stdin)
           in my 120 minute C programming class test, in January 2021.
        */
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                weekdayLabelArray[i] = new JLabel("") {
                    {
                        setFont(new Font("Arial", Font.BOLD, 24));
                        setPreferredSize(new Dimension(50, 50));
                    }
                };
                weekdayLabelPanel.add(weekdayLabelArray[i]);
                continue;
            }
            weekdayLabelArray[i] = new JLabel(StringConstants.weekdays[i - 1]) {
                {
                    setFont(new Font("Arial", Font.BOLD, 24));
                    setPreferredSize(new Dimension(154, 50));

                    setHorizontalTextPosition(JLabel.RIGHT);
                    setVerticalTextPosition(JLabel.BOTTOM);
                }
            };
            weekdayLabelPanel.add(weekdayLabelArray[i]);
        }

        // Create and add panel for hours of the day
        hourLabelPanel = new JPanel() {
            {
                setLayout(new FlowLayout());
                setBackground(Color.decode("#a9a7ed"));
                setPreferredSize(new Dimension(50, 730));
            }
        };
        this.add(hourLabelPanel, BorderLayout.WEST);

        // Label the hours of the day
        for (int i = 0; i < 24; i++) {
            hourLabelArray[i] = new JLabel((i < 10) ?
                    ("0" + Integer.valueOf(i).toString() + ":00") :
                    Integer.valueOf(i).toString() + ":00") {
                {
                    setFont(new Font("Consolas", Font.BOLD, 12));
                    setPreferredSize(new Dimension(50, 25));
                    setHorizontalTextPosition(JLabel.LEFT);
                    setVerticalTextPosition(JLabel.BOTTOM);
                }
            };
            hourLabelPanel.add(hourLabelArray[i]);
        }

        // Create and add the 7-day week display for events
        createWeekdayContainer();
        this.add(weekdayContainer, BorderLayout.CENTER);

        weekdayContainer.addComponentListener(this);
    }

    private void createWeekdayContainer() {
        weekdayContainer = new JPanel();
        weekdayContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        weekdayContainer.setBackground(Color.decode("#f3f6f4"));

        createWeekdayPanels();
    }

    private void createWeekdayPanels(){
        for (int i = 0; i < 7; i++) {
            weekdayPanelArray[i] = new JPanel();
            weekdayPanelArray[i].setLayout(null);
            weekdayPanelArray[i].setPreferredSize(new Dimension(WEEKDAY_PANEL_WIDTH, WEEKDAY_PANEL_HEIGHT));
            // Makes the panel colors alternate between lighter and darker gray
            weekdayPanelArray[i].setBackground( i % 2 == 0 ? Color.decode("#f3f6f4") : Color.decode("#e7e7e7") );
            weekdayPanelArray[i].setOpaque(true);
            weekdayContainer.add(weekdayPanelArray[i]);
        }
    }

    private void createWeekdayLabelPanel() {
        weekdayLabelPanel = new JPanel();
        weekdayLabelPanel.setLayout(new FlowLayout());
        weekdayLabelPanel.setBackground(Color.decode("#bbbaf0"));
        weekdayLabelPanel.setPreferredSize(new Dimension(1190, 50));
    }

    @Override
    public void componentResized(ComponentEvent e) {
        // Debugging info about weekdayContainer dimensions, to delete later (maybe)
        System.out.println(weekdayContainer.getWidth() + " " + weekdayContainer.getHeight());
        double updatedPanelWidth = weekdayContainer.getWidth() * WEEKDAY_PANEL_WIDTH / PARENT_PANEL_WIDTH;
        double updatedPanelHeight = weekdayContainer.getHeight() * WEEKDAY_PANEL_HEIGHT / PARENT_PANEL_HEIGHT;
        System.out.println(updatedPanelWidth + " " + updatedPanelHeight);
        for (int i = 0; i < 7; i++) {
            weekdayPanelArray[i].setPreferredSize(new Dimension(
                    (int)Math.floor(updatedPanelWidth),
                    (int)Math.floor(updatedPanelHeight))
            );
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
