import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainPlatformFrame extends JFrame {
    private static final Dimension POST_DIMENSION = new Dimension(900, 150);
    private static final Dimension POST_INPUT_DIMENSION = new Dimension(900, 200);
    private static final Color PRIMARY_COLOR = new Color(60, 130, 190); // Calm Blue
    private static final Color ACTION_COLOR = new Color(220, 80, 60); // Vibrant Red for actions

    private static final Color SECONDARY_COLOR = new Color(240, 240, 245); // Soft Light Gray
    private static final Color ACCENT_COLOR = new Color(45, 100, 160); // Darker Blue
    private static final Color TEXT_PRIMARY = new Color(20, 20, 20); // Almost Black
    private static final Color TEXT_SECONDARY = new Color(120, 120, 120); // Soft Gray
    private static final User testUser = new User("test_user", "test_password", UserRole.NORMAL_USER);


    private DataStorageManager dataStorageManager;
    private Navbar navbar;
    private HomeContent homeContent;

    public MainPlatformFrame() {
        dataStorageManager = ProjectPopulator.populate();
        navbar = new Navbar();
        homeContent = new HomeContent(dataStorageManager);
        initializeFrame();
    }

    private void initializeFrame() {
        setTitle("Halaqa Main Platform");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(navbar.createHeader(), BorderLayout.NORTH);
        JScrollPane mainContent = homeContent.createMainContent();
        add(mainContent, BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
        setVisible(true);
    }


    private JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(SECONDARY_COLOR);
        JLabel footerLabel = new JLabel("© 2023 Halaqa Community. All rights reserved.");
        footer.add(footerLabel);
        return footer;
    }

    static class ScrollablePanel extends JPanel implements Scrollable {
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return super.getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }

}
