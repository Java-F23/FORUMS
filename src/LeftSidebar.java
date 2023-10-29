import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class LeftSidebar {

    private DataStorageManager dataStorageManager;
    private HomeContent homeContent;
    private JComboBox<String> userComboBox;

    public LeftSidebar(DataStorageManager dataStorageManager, HomeContent homeContent) {
        this.dataStorageManager = dataStorageManager;
        this.homeContent = homeContent;
        this.userComboBox = new JComboBox<>();
    }

    public void updateUserList() {
        userComboBox.removeAllItems();
        for (User user : dataStorageManager.getUsers()) {
            userComboBox.addItem(user.getUsername());
        }
    }

    public JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(300, sidebar.getPreferredSize().height));
        sidebar.setBackground(Color.LIGHT_GRAY);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Title
        JLabel title = new JLabel("Filter & Sort");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // User Filter Panel
        JPanel userFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userFilterPanel.setBackground(Color.LIGHT_GRAY);
        JLabel userFilterLabel = new JLabel("Filter by User:");

        // Populate the ComboBox with user names
        updateUserList();

        // Add action listener to filter posts when a user is selected
        userComboBox.addActionListener(e -> {
            String selectedUser = (String) userComboBox.getSelectedItem();
            ArrayList<Post> filteredPosts = dataStorageManager.filterPostsByAuthor(selectedUser);
            // Here, you can update the main content panel with the filteredPosts
            homeContent.updateMiddleGrid(filteredPosts);
        });

        userFilterPanel.add(userFilterLabel);
        userFilterPanel.add(userComboBox);

        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(userFilterPanel);
        return  sidebar;
/*
        // Sort Panel
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sortPanel.setBackground(Color.LIGHT_GRAY);
        JLabel sortLabel = new JLabel("Sort by:");
        JRadioButton sortByDate = new JRadioButton("Date");
        JRadioButton sortByPopularity = new JRadioButton("Popularity");
        ButtonGroup sortGroup = new ButtonGroup();
        sortGroup.add(sortByDate);
        sortGroup.add(sortByPopularity);
        sortPanel.add(sortLabel);
        sortPanel.add(sortByDate);
        sortPanel.add(sortByPopularity);

        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(userFilterPanel);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(sortPanel);

        return sidebar;
  */
    }

}
