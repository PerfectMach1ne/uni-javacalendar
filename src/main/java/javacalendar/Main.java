import javax.swing.JFrame;
import java.awt.Toolkit;

public class Main {
    public static JFrame mainFrame;

    public static void main(String[] args) {
        mainFrame = new MainFrame();
    }

    public static void alignWindowInCenter(JFrame frame) {
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        int x = (screenWidth - frame.getWidth()) / 2;
        int y = (screenHeight - frame.getHeight()) / 2;

        frame.setLocation(x, y);
    }
}