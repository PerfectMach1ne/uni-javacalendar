import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Toolkit;

public class Main {
    public static JFrame mainFrame;

    public final static String[] weekdays = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    public final static String[] comboBoxColorNames = {"Pink", "Red", "Orange", "Yellow", "Light green", "Green", "Light blue", "Blue",
            "Dark blue", "Lavender", "Purple", "Magenta", "White", "Light gray", "Gray", "Black"};

    public static void main(String[] args) {
        mainFrame = new MainFrame();
    }

    // Wycentruj okienko na ekranie
    public static void alignWindowInCenter(JFrame frame) {
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        int x = (screenWidth - frame.getWidth()) / 2;
        int y = (screenHeight - frame.getHeight()) / 2;

        frame.setLocation(x, y);
    }
}