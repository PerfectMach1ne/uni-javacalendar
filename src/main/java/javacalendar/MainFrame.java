import javax.swing.JFrame;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {
    public MainFrame() {
        this.setSize(1400,850);
        Main.alignWindowInCenter(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Java Calendar");
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        WeeksPanel weeksPanel = new WeeksPanel();
        LeftBarPanel leftBarPanel = new LeftBarPanel();
        MenuBar menuBar = new MenuBar();

        this.add(weeksPanel, BorderLayout.CENTER);
        this.add(leftBarPanel, BorderLayout.WEST);
        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }
}
