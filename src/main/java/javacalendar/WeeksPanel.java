package javacalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javacalendar.util.StringConstants;

public class WeeksPanel extends JPanel {
    private final JPanel weekdayContainer;
    private final JPanel weekdayLabels;
    private final JPanel hourLabels;
    private final JLabel[] weekdayLabelArray = new JLabel[8];
    private final JLabel[] hourLabelArray = new JLabel[24];
    public static final JPanel[] weekdayPanelArray = new JPanel[7];

    public WeeksPanel() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.gray);

        // Create and add panel for days of the week
        weekdayLabels = new JPanel() {
            {
                setLayout(new FlowLayout());
                setBackground(Color.decode("#bbbaf0"));
                setPreferredSize(new Dimension(1190, 50));
            }
        };
        this.add(weekdayLabels, BorderLayout.NORTH);

        // Label the days of the week
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                weekdayLabelArray[i] = new JLabel("") { // to jest obrzydliwe ale umożliwia poprawne ułożenie JLabeli
                    {
                        setFont(new Font("Arial", Font.BOLD, 24));
                        setPreferredSize(new Dimension(50, 50));
                    }
                };
                weekdayLabels.add(weekdayLabelArray[i]);
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
            weekdayLabels.add(weekdayLabelArray[i]);
        }

        // Create and add panel for hours of the day
        hourLabels = new JPanel() {
            {
                setLayout(new FlowLayout());
                setBackground(Color.decode("#a9a7ed"));
                setPreferredSize(new Dimension(50, 730));
            }
        };
        this.add(hourLabels, BorderLayout.WEST);

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
            hourLabels.add(hourLabelArray[i]);
        }

        // Create and add the 7-day week display for events
        weekdayContainer = new JPanel() {
            {
                setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                setBackground(Color.decode("#f3f6f4"));
//                setBackground(Color.pink); // for testign xd
            }

        };
        this.add(weekdayContainer, BorderLayout.CENTER);

        initializeWeekdayPanels();
    }

    private void initializeWeekdayPanels() {    // works
        for (int i = 0; i < 7; i++) {
            // "Variable 'i' is accessed from within inner class, needs to be final or effectively final"
            int finalI = i;
            weekdayPanelArray[i] = new JPanel() {
                {
                    setLayout(null);
                    setPreferredSize(new Dimension(160, 730));
                    setBackground( finalI % 2 == 0 ? Color.decode("#f3f6f4") : Color.decode("#e7e7e7") );
                    setOpaque(true);
                }
            };
            weekdayContainer.add(weekdayPanelArray[i]);
        }

    }
}
