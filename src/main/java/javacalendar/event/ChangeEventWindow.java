package javacalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javacalendar.util.Colors;
import javacalendar.util.LengthRestrictedDocument;
import javacalendar.util.StringConstants;

public class ChangeEventWindow implements ActionListener, MouseListener {
    private JFrame changeEventFrame = new JFrame();

    private JComboBox eventComboBox;
    private JTextField eventNameTextField;
    private JTextArea eventDescriptionTextArea;
    private JComboBox weekdayComboBox;
    private JComboBox colorComboBox;
    private JTextField eventStartHours;
    private JTextField eventStartMinutes;
    private JTextField eventEndHours;
    private JTextField eventEndMinutes;
    private JLabel chooseEventLabel = new JLabel("Choose an event: ");
    private JLabel eventWeekdayLabel = new JLabel("Day of the week: ");
    private JLabel eventNameLabel = new JLabel("Event name: ");
    private JLabel eventDescriptionLabel = new JLabel("Event description: ");
    private JLabel eventColorLabel = new JLabel("Event color: ");
    private JLabel eventTimeLabel = new JLabel("Event start and end time: ");
    private JLabel doubleColonLabel1 = new JLabel(":");
    private JLabel doubleColonLabel2 = new JLabel(":");
    private JButton confirmButton;
    private JButton cancelButton;

    private String eventToRemoveKey = new String();

    private String[] eventNames;
    private String[] eventKeys;

    private final int horizontalGap = 5;
    private final int verticalGap = 5;

    ChangeEventWindow() {
        changeEventFrame.setBounds(0,0,100,150);
        changeEventFrame.setSize(new Dimension(325,500));
        Main.alignWindowInCenter(changeEventFrame);
        changeEventFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changeEventFrame.setResizable(false);
        changeEventFrame.setTitle("Change an event");

        changeEventFrame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        changeEventFrame.setVisible(true);

        eventNames = new String[CalendarEventHandler.eventNames.size()];
        eventKeys = new String[CalendarEventHandler.eventNames.size()];
        String[] parsedEventNames = new String[CalendarEventHandler.eventNames.size()];
        int i = 0;
        for (String eventName : CalendarEventHandler.eventNames.values()) {
            eventNames[i] = eventName;
            i++;
        } i = 0;
        for (String key : CalendarEventHandler.eventNames.keySet()) {
            eventKeys[i] = key;
            i++;
        } i = 0;
        // Zamienia dni tygodnia z liczb od 0 do 6 na pełne angielskie nazwy
        for (String eventName : eventNames) {
            String key = eventKeys[i];
            int day = Integer.parseInt(String.valueOf(key.charAt(0)));
            parsedEventNames[i] = eventName + " (" + weekdayToString(day) + ")";
            i++;
        }

        // Choose an event label
        constraint.insets = new Insets(horizontalGap, 0, horizontalGap, 0);
        constraint.gridx = 0;
        constraint.gridy = 0;
        changeEventFrame.add(chooseEventLabel, constraint);

        // Choose an event ComboBox
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        eventComboBox = new JComboBox(parsedEventNames);
        eventComboBox.setEditable(false);
        eventComboBox.addActionListener(this);
        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.gridwidth = 3;

        changeEventFrame.add(eventComboBox, constraint);

        // Event weekday label
        constraint.insets = new Insets(horizontalGap, 0, horizontalGap, 5);
        constraint.gridx = 0;
        constraint.gridy = 2;
        changeEventFrame.add(eventWeekdayLabel, constraint);

        constraint.gridx = 1;
        constraint.gridy = 2;
        changeEventFrame.add(new JLabel(""), constraint); // Ugly but at least fixes a weird bug

        // Choose the day of the week ComboBox
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        weekdayComboBox = new JComboBox(StringConstants.weekdays);
        weekdayComboBox.setEditable(false);
        constraint.gridx = 2;
        constraint.gridy = 2;
        constraint.gridwidth = 3;

        changeEventFrame.add(weekdayComboBox, constraint);

        // Event name label
        constraint.insets = new Insets(horizontalGap, 0, horizontalGap, 0);
        constraint.gridx = 0;
        constraint.gridy = 3;
        changeEventFrame.add(eventNameLabel, constraint);

        // Enter event name TextField
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        eventNameTextField = new JTextField();
        eventNameTextField.setPreferredSize(new Dimension(150,20));
        constraint.gridx = 0;
        constraint.gridy = 4;

        changeEventFrame.add(eventNameTextField, constraint);

        // Event description label
        constraint.insets = new Insets(horizontalGap, 0, horizontalGap, 0);
        constraint.gridx = 0;
        constraint.gridy = 5;
        changeEventFrame.add(eventDescriptionLabel, constraint);

        // Write an event description TextArea
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        eventDescriptionTextArea = new JTextArea();
        eventDescriptionTextArea.setPreferredSize(new Dimension(150,50));
        eventDescriptionTextArea.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        eventDescriptionTextArea.setLineWrap(true);
        constraint.gridx = 0;
        constraint.gridy = 6;

        changeEventFrame.add(eventDescriptionTextArea, constraint);

        // Event choose color label
        constraint.insets = new Insets(horizontalGap, 0, horizontalGap, 0);
        constraint.gridx = 0;
        constraint.gridy = 7;
        changeEventFrame.add(eventColorLabel, constraint);

        // Choose a color ComboBox
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        colorComboBox = new JComboBox(StringConstants.comboBoxColorNames);
        colorComboBox.setEditable(false);
        constraint.gridx = 0;
        constraint.gridy = 8;

        changeEventFrame.add(colorComboBox, constraint);

        // Event time label
        constraint.insets = new Insets(horizontalGap, 0, horizontalGap, 0);
        constraint.gridx = 0;
        constraint.gridy = 9;
        changeEventFrame.add(eventTimeLabel, constraint);

        // Event start time TextFields
        JPanel eventStartTimePanel = new JPanel();
        eventStartTimePanel.setLayout(new FlowLayout());
        constraint.gridx = 0;
        constraint.gridy = 10;
        eventStartHours = new JTextField();
        eventStartHours.setPreferredSize(new Dimension(30, 20));
        eventStartHours.setDocument(new LengthRestrictedDocument(2));
        eventStartMinutes = new JTextField();
        eventStartMinutes.setPreferredSize(new Dimension(30, 20));
        eventStartMinutes.setDocument(new LengthRestrictedDocument(2));

        eventStartTimePanel.add(eventStartHours);
        eventStartTimePanel.add(doubleColonLabel1);
        eventStartTimePanel.add(eventStartMinutes);

        changeEventFrame.add(eventStartTimePanel, constraint);

        // Event end time TextFields
        JPanel eventEndTimePanel = new JPanel();
        eventEndTimePanel.setLayout(new FlowLayout());
        constraint.gridx = 0;
        constraint.gridy = 11;
        changeEventFrame.add(eventEndTimePanel);
        eventEndHours = new JTextField();
        eventEndHours.setPreferredSize(new Dimension(30, 20));
        eventEndHours.setDocument(new LengthRestrictedDocument(2));
        eventEndMinutes = new JTextField();
        eventEndMinutes.setPreferredSize(new Dimension(30, 20));
        eventEndMinutes.setDocument(new LengthRestrictedDocument(2));

        eventEndTimePanel.add(eventEndHours);
        eventEndTimePanel.add(doubleColonLabel2);
        eventEndTimePanel.add(eventEndMinutes);

        changeEventFrame.add(eventEndTimePanel, constraint);

        // Change event button
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        confirmButton = new JButton();
        confirmButton.setText("Change event");
        confirmButton.setFocusable(false);
        confirmButton.addMouseListener(this);
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 12;
        constraint.anchor = GridBagConstraints.PAGE_END;

        changeEventFrame.add(confirmButton, constraint);

        // Cancel button
        constraint.insets = new Insets(horizontalGap, verticalGap, horizontalGap, verticalGap);
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addMouseListener(this);
        constraint.gridwidth = 1;
        constraint.gridx = 1;
        constraint.gridy = 12;
        constraint.anchor = GridBagConstraints.PAGE_END;

        changeEventFrame.add(cancelButton, constraint);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == eventComboBox ) {
            String selectedEventKey = eventKeys[eventComboBox.getSelectedIndex()];
            System.out.println(selectedEventKey);
            eventToRemoveKey = selectedEventKey;
            System.out.println(eventToRemoveKey);
            weekdayComboBox.setSelectedIndex(CalendarEventHandler.eventDays.get(selectedEventKey));
            eventNameTextField.setText(CalendarEventHandler.eventNames.get(selectedEventKey));
            eventDescriptionTextArea.setText(CalendarEventHandler.eventDescriptions.get(selectedEventKey));
            String colorString = Colors.getPrettyNameFromColor(CalendarEventHandler.eventColors.get(selectedEventKey));
            colorComboBox.setSelectedItem(colorString);
            eventStartHours.setText(CalendarEventHandler.eventStartHours.get(selectedEventKey).substring(0,2));
            eventStartMinutes.setText(CalendarEventHandler.eventStartHours.get(selectedEventKey).substring(3));
            eventEndHours.setText(CalendarEventHandler.eventEndHours.get(selectedEventKey).substring(0,2));
            eventEndMinutes.setText(CalendarEventHandler.eventEndHours.get(selectedEventKey).substring(3));
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if ( e.getSource() == confirmButton ) {
            String weekdayString = weekdayComboBox.getSelectedItem().toString();
            // Check if day of the week is correct (very, very important)
            if (stringToWeekday(weekdayString) == -1) {
                JOptionPane.showMessageDialog(changeEventFrame, "An error occurred while parsing day of the week.",
                        "Input error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error: String to weekday integer conversion returned -1");
                changeEventFrame.dispose();
            }
            String colorString = colorComboBox.getSelectedItem().toString();
            Color actualColor = Color.decode(Colors.getColorFromName(colorString).getHexColor());
            // Check if proper hours have been entered
            boolean properTimeConditions;
            while(true) {
                try {
                    properTimeConditions = (Integer.parseInt( eventStartHours.getText()) >= 0 && Integer.parseInt(eventStartHours.getText()) < 24)
                            && (Integer.parseInt(eventStartMinutes.getText()) >= 0 && Integer.parseInt(eventStartMinutes.getText()) < 60)
                            && (Integer.parseInt(eventEndHours.getText()) >= 0 && Integer.parseInt(eventEndHours.getText()) < 24)
                            && (Integer.parseInt(eventEndMinutes.getText()) >= 0 && Integer.parseInt(eventEndMinutes.getText()) < 60);
                    if (!properTimeConditions) {
                        JOptionPane.showMessageDialog(changeEventFrame, "Please enter a proper time!",
                                "Input error", JOptionPane.ERROR_MESSAGE);
                        break;
                    } else {
                        boolean isBroken = false;
                        String fixedStartHours = eventStartHours.getText();
                        String fixedStartMinutes = eventStartMinutes.getText();
                        String fixedEndHours = eventEndHours.getText();
                        String fixedEndMinutes = eventEndMinutes.getText();
                        if (Integer.parseInt(eventStartHours.getText()) < 10) {
                            fixedStartHours = "0".concat(Integer.toString(Integer.parseInt(eventStartHours.getText())));
                            isBroken = true;
                        }
                        if (Integer.parseInt(eventStartMinutes.getText()) < 10) {
                            fixedStartMinutes = "0".concat(Integer.toString(Integer.parseInt(eventStartMinutes.getText())));
                            isBroken = true;
                        }
                        if (Integer.parseInt(eventEndHours.getText()) < 10) {
                            fixedEndHours = "0".concat(Integer.toString(Integer.parseInt(eventEndHours.getText())));
                            isBroken = true;
                        }
                        if (Integer.parseInt(eventEndMinutes.getText()) < 10) {
                            fixedEndMinutes = "0".concat(Integer.toString(Integer.parseInt(eventEndMinutes.getText())));

                            isBroken = true;
                        }
                        if (isBroken) {
                            // Dispose of the old event in style
                            if ( CalendarEventHandler.eventStorage.containsKey(eventToRemoveKey) ) {
                                CalendarEventHandler.removeCalendarEventByHashKey(eventToRemoveKey);
                            }
                            // Actually add the event
                            String eventStartTime = fixedStartHours + ":" + fixedStartMinutes;
                            String eventEndTime = fixedEndHours + ":" + fixedEndMinutes;
                            String editedEventKey = CalendarEventHandler.getEventKey(stringToWeekday(weekdayString), eventNameTextField.getText(),
                                    CalendarEventHandler.processHoursIntoEventStartValue(eventStartTime),
                                    CalendarEventHandler.processHoursIntoEventEndValue(eventEndTime));
                            // Naprawia bug gdzie zdarzenia wstawiają się podwójnie
                            if ( !CalendarEventHandler.eventStorage.containsKey(editedEventKey) ) {
                                CalendarEventHandler.addCalendarEvent(stringToWeekday(weekdayString), eventNameTextField.getText(),
                                        eventDescriptionTextArea.getText(), actualColor,
                                        Colors.getColorFromName(colorString).getProperTextColor(), eventStartTime, eventEndTime);
                            }
                            System.out.println("Added the event from isBroken try block");
                            changeEventFrame.dispose();
                        }
                        // Dispose of the old event in style
                        if ( CalendarEventHandler.eventStorage.containsKey(eventToRemoveKey) ) {
                            CalendarEventHandler.removeCalendarEventByHashKey(eventToRemoveKey);
                        }
                        // Actually add the event
                        String eventStartTime = fixedStartHours + ":" + fixedStartMinutes;
                        String eventEndTime = fixedEndHours + ":" + fixedEndMinutes;
                        String editedEventKey = CalendarEventHandler.getEventKey(stringToWeekday(weekdayString), eventNameTextField.getText(),
                                CalendarEventHandler.processHoursIntoEventStartValue(eventStartTime),
                                CalendarEventHandler.processHoursIntoEventEndValue(eventEndTime));
                        // Naprawia bug gdzie zdarzenia wstawiają się podwójnie
                        if ( !CalendarEventHandler.eventStorage.containsKey(editedEventKey) ) {
                            CalendarEventHandler.addCalendarEvent(stringToWeekday(weekdayString), eventNameTextField.getText(),
                                    eventDescriptionTextArea.getText(), actualColor,
                                    Colors.getColorFromName(colorString).getProperTextColor(), eventStartTime, eventEndTime);
                        }
                        System.out.println("Added the event from main try block");
                        changeEventFrame.dispose();
                        break;
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(changeEventFrame, "Please enter a proper time!",
                            "Input error", JOptionPane.ERROR_MESSAGE);
                    nfe.addSuppressed(nfe); // Suppress the exception to prevent it from an endless while(true) loop

                    boolean isBroken = false;
                    String fixedStartHours = eventStartHours.getText();
                    String fixedStartMinutes = eventStartMinutes.getText();
                    String fixedEndHours = eventEndHours.getText();
                    String fixedEndMinutes = eventEndMinutes.getText();
                    if (Integer.parseInt(eventStartHours.getText()) < 10) {
                        fixedStartHours = "0".concat(Integer.toString(Integer.parseInt(eventStartHours.getText())));
                        isBroken = true;
                    }
                    if (Integer.parseInt(eventStartMinutes.getText()) < 10) {
                        fixedStartMinutes = "0".concat(Integer.toString(Integer.parseInt(eventStartMinutes.getText())));
                        isBroken = true;
                    }
                    if (Integer.parseInt(eventEndHours.getText()) < 10) {
                        fixedEndHours = "0".concat(Integer.toString(Integer.parseInt(eventEndHours.getText())));
                        System.out.println(fixedEndHours);
                        isBroken = true;
                    }
                    if (Integer.parseInt(eventEndMinutes.getText()) < 10) {
                        fixedEndMinutes = "0".concat(Integer.toString(Integer.parseInt(eventEndMinutes.getText())));
                        System.out.println(fixedEndMinutes);
                        isBroken = true;
                    }
                    if (isBroken) {
                        // Dispose of the old event in style
                        if ( CalendarEventHandler.eventStorage.containsKey(eventToRemoveKey) ) {
                            CalendarEventHandler.removeCalendarEventByHashKey(eventToRemoveKey);
                        }
                        // Actually add the event
                        String eventStartTime = fixedStartHours + ":" + fixedStartMinutes;
                        String eventEndTime = fixedEndHours + ":" + fixedEndMinutes;
                        String editedEventKey = CalendarEventHandler.getEventKey(stringToWeekday(weekdayString), eventNameTextField.getText(),
                                CalendarEventHandler.processHoursIntoEventStartValue(eventStartTime),
                                CalendarEventHandler.processHoursIntoEventEndValue(eventEndTime));
                        // Naprawia bug gdzie zdarzenia wstawiają się podwójnie
                        if ( !CalendarEventHandler.eventStorage.containsKey(editedEventKey) ) {
                            CalendarEventHandler.addCalendarEvent(stringToWeekday(weekdayString), eventNameTextField.getText(),
                                    eventDescriptionTextArea.getText(), actualColor,
                                    Colors.getColorFromName(colorString).getProperTextColor(), eventStartTime, eventEndTime);
                        }
                        System.out.println("Added the event from catch block");
                        changeEventFrame.dispose();
                    }
                }
            }
        } else if ( e.getSource() == cancelButton ) {
            changeEventFrame.dispose();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private String weekdayToString(int weekday) {
        switch (weekday) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
        }
        return "undefined";
    }

    private int stringToWeekday(String weekday) {
        switch (weekday) {
            case "Mon":
                return 0;
            case "Tue":
                return 1;
            case "Wed":
                return 2;
            case "Thu":
                return 3;
            case "Fri":
                return 4;
            case "Sat":
                return 5;
            case "Sun":
                return 6;
        }
        return -1;
    }

}
