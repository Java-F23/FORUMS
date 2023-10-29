import javax.swing.*;
import java.awt.*;

public class RightSidebar {

    public static JPanel createSidebar(String title) {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(300, sidebar.getPreferredSize().height));
        sidebar.setBackground(Color.LIGHT_GRAY);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Placeholder for items in the sidebar
        for (int i = 0; i < 20; i++) {
            sidebar.add(new JLabel(title + " Item " + (i + 1)));
        }

        return sidebar;
    }

}
