package javacalendar.event;

import javax.swing.JLabel;
import java.awt.Color;

import java.util.HashMap;

import javacalendar.WeeksPanel;
import javacalendar.util.Colors;

public final class CalendarEventHandler extends WeeksPanel {
    public static HashMap<String, JLabel> eventStorage = new HashMap<>();
    public static HashMap<String, Integer> eventDays = new HashMap<>();
    public static HashMap<String, String> eventNames = new HashMap<>();
    public static HashMap<String, String> eventDescriptions = new HashMap<>();
    public static HashMap<String, Colors> eventColors = new HashMap<>();
    public static HashMap<String, String> eventStartHours = new HashMap<>();
    public static HashMap<String, String> eventEndHours = new HashMap<>();

    private static final int EVENT_POSITION_OFFSET = 14;
    private static final int HOUR_HEIGHT_UNIT = 29;

    public static String getEventKey(int day, String eventLabel, int eventStartHour, int eventEndHour) {
        String fixedString = eventLabel;
        if (fixedString.length() < 3) {
            while (fixedString.length() < 3) {
                fixedString = fixedString.concat("_");    // Jeżeli wydarzenie ma mniej niż 3 wymagane litery w tytule,
            }                                             // wtedy w miejsce "pustki" wstawiane są znaki '_';
        } else {
            fixedString = eventLabel.substring(0, 3);
        }
        return Integer.valueOf(day).toString() + fixedString + Integer.valueOf(eventStartHour).toString()
                + "-" + Integer.valueOf(eventEndHour).toString();
        // 0Tes196:252
        // Meanings of individual characters:
        // 1 - day panel that the panel was assigned to
        // 2,3,4 - first 3 event characters
        // 2-4 chars - eventStartHour in raw value
        // single char inbetween - the '-' separator character
        // 2-4 chars - eventEndHour in raw value
    }

    public static void addCalendarEvent(int day, String eventLabel, String eventDescription, Color eventColor, Color textColor, String eventStartHour, String eventEndHour) {
        eventStorage.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)), new JLabel() {
            {
                setText("<html><body>" + "<div style=\"font-size:16;\">" + eventLabel +
                        "</div><div style=\"font-family:consolas; font-size:14;\">" + eventStartHour + " - " + eventEndHour +
                        "</div><div style=\"font-family:arial; font-size:13;\">" + textBreaker(eventDescription) + "</div></body></html>");
                setVerticalAlignment(JLabel.TOP);
                setForeground(textColor);
                setBackground(eventColor);
                setBounds(0, processHoursIntoEventStartValue(eventStartHour), 170, processHoursIntoEventEndValue(eventEndHour) - processHoursIntoEventStartValue(eventStartHour));
                setOpaque(true);
            }
        });
        System.out.println( "Added event with key " + getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)) );

        weekdayPanelArray[day].add( eventStorage.get( getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)) ) );
        eventDays.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                day);
        eventNames.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                eventLabel);
        eventDescriptions.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                eventDescription );
        String RGBToHex = String.format("#%02x%02x%02x", eventColor.getRed(), eventColor.getGreen(), eventColor.getBlue());
        eventColors.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                Colors.getColorFromHex(RGBToHex));
        eventStartHours.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                eventStartHour);
        eventEndHours.put(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)),
                eventEndHour);
        WeeksPanel.weekdayPanelArray[day].revalidate();
        WeeksPanel.weekdayPanelArray[day].repaint();
//        weekdayPanelArray[day].add(new JLabel() {
//            {
//                setText("<html>" + eventLabel + "<br/>" + eventStartHour + ":" + eventEndHour);
//                setBackground(eventColor);
//                setBounds(0, eventStartHour, 170, eventEndHour - eventStartHour);
//                setOpaque(true);
//            }
//        });
    }

    public static void removeCalendarEvent(int day, String eventLabel, String eventStartHour, String eventEndHour) {
        WeeksPanel.weekdayPanelArray[day].remove( eventStorage.get(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour))) );
        eventStorage.remove(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)));
        eventDays.remove(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)));
        eventNames.remove(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)) );
        eventDescriptions.remove(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)));
        eventColors.remove(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)));
        eventStartHours.remove(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)));
        eventEndHours.remove(getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)));
        WeeksPanel.weekdayPanelArray[day].revalidate();
        WeeksPanel.weekdayPanelArray[day].repaint();
        System.out.println( "Deleted event with key " +
                getEventKey(day, eventLabel, processHoursIntoEventStartValue(eventStartHour), processHoursIntoEventEndValue(eventEndHour)) );
    }

    public static void removeCalendarEventByHashKey(String key) {
        int day = Integer.parseInt(String.valueOf(key.charAt(0)));
        if ( CalendarEventHandler.eventStorage.containsKey(key) ) {
            WeeksPanel.weekdayPanelArray[day].remove(eventStorage.get(key));
            eventStorage.remove(key);
            eventDays.remove(key);
            eventNames.remove(key);
            eventDescriptions.remove(key);
            eventColors.remove(key);
            eventStartHours.remove(key);
            eventEndHours.remove(key);
            WeeksPanel.weekdayPanelArray[day].revalidate();
            WeeksPanel.weekdayPanelArray[day].repaint();
            System.out.println( "Deleted event with key " + key);
        }
    }

    // Transformuje godziny podane w formacie hh:mm jako String na pozycję początkową komponentu
    public static int processHoursIntoEventStartValue(String stringHours) {
        // odległość od napisu "00:00" od krańca poziomego JPanelu wynosi 14px
        // 29px to średnia długość JLabeli w panelu godzin
        return getEVENT_POSITION_OFFSET() + (int)Math.round( getHOUR_HEIGHT_UNIT() * ( Double.parseDouble(stringHours.substring(0,2))
                + Double.parseDouble(stringHours.substring(3)) / 60 ) ); // HOURS + MINUTES / 60
    }

    // Transformuje godziny podane w formacie hh:mm jako String na parametr potrzebny do wyliczenia szerokości komponentu
    public static int processHoursIntoEventEndValue(String stringHours) {
        // +1 poprawia ułożenie JLabel-i gdy metoda podana jest jako argument eventStart
        // Bez tego komponent wydarzenia kończącego się o godz. "23:00" sięga co najwyżej wysokości JLabela "22:00"
        return getEVENT_POSITION_OFFSET() + (int)Math.round( getHOUR_HEIGHT_UNIT() * ( 1 + Double.parseDouble(stringHours.substring(0,2))
                + Double.parseDouble(stringHours.substring(3)) / 60 ) ); // 1 + HOURS + MINUTES / 60
    }

    // Ta metoda naprawia "młotkiem" problem zbyt długich słow nie mieszczacych sie w eventDescription
    public static String textBreaker(String text) {
        // Długość panelu = średnio 23 cyfry
        // Ponieważ znaki posiadają różną długość, tekst jest "ucinany" po 21 znakach zamiast 23.
        if (text.length() <= 21) {
            return text;
        }
        return text.substring(0,21) + "<br>" + textBreaker( text.substring(21) );
    }

    public static int getEVENT_POSITION_OFFSET() {
        return EVENT_POSITION_OFFSET;
    }

    public static int getHOUR_HEIGHT_UNIT() {
        return HOUR_HEIGHT_UNIT;
    }
}
