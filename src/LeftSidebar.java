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
        sidebar.setBackground(Color.WHITE);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Title
        JLabel title = new JLabel("Filter & Sort");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.LEFT_ALIGNMENT); // Change to LEFT_ALIGNMENT
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        title.setBackground(Color.WHITE);

        // User Filter Panel
        JPanel userFilterPanel = new JPanel();
        userFilterPanel.setLayout(new BoxLayout(userFilterPanel, BoxLayout.Y_AXIS)); // Change to BoxLayout

        userFilterPanel.setBackground(Color.WHITE);
        userFilterPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Add this line

        JLabel userFilterLabel = new JLabel("Filter by User:");
        userFilterLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Add this line
        userFilterPanel.add(userFilterLabel);

        // Populate the ComboBox with usernames
        updateUserList();
        userComboBox.setAlignmentX(Component.LEFT_ALIGNMENT); // Add this line
        userComboBox.setPreferredSize(new Dimension(200, 30));  // for example: width of 200 and height of 30
        userComboBox.setMaximumSize(new Dimension(200, 30));
        userFilterPanel.add(userComboBox);

        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(userFilterPanel);

        // Sort Panel
        JPanel sortPanel = new JPanel();
        
        sortPanel.setLayout(new BoxLayout(sortPanel, BoxLayout.Y_AXIS)); // Change to BoxLayout
        sortPanel.setBackground(Color.WHITE);
        sortPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Add this line

        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setBackground(Color.WHITE);
        sortLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Add this line
        sortPanel.add(sortLabel);

        JRadioButton sortByDate = new JRadioButton("Date");
        sortByDate.setBackground(Color.WHITE);

        sortByDate.setAlignmentX(Component.LEFT_ALIGNMENT); // Add this line
        sortPanel.add(sortByDate);

        JRadioButton sortByPopularity = new JRadioButton("Popularity");
        sortByPopularity.setBackground(Color.WHITE);

        sortByPopularity.setAlignmentX(Component.LEFT_ALIGNMENT); // Add this line
        sortPanel.add(sortByPopularity);

        ButtonGroup sortGroup = new ButtonGroup();
        sortGroup.add(sortByDate);
        sortGroup.add(sortByPopularity);

        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(sortPanel);

        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // Add action listener to filter posts when a user is selected
        userComboBox.addActionListener(e -> {
            String selectedUser = (String) userComboBox.getSelectedItem();
            ArrayList<Post> filteredPosts = dataStorageManager.filterPostsByAuthor(selectedUser);
            homeContent.updateMiddleGrid(filteredPosts);
        });

        // Add action listeners for sorting
        sortByDate.addActionListener(e -> {
            if (sortByDate.isSelected()) {
                ArrayList<Post> sortedPosts = dataStorageManager.sortPostsByDate();
                homeContent.updateMiddleGrid(sortedPosts);
            }
        });

        sortByPopularity.addActionListener(e -> {
            if (sortByPopularity.isSelected()) {
                ArrayList<Post> sortedPosts = dataStorageManager.sortPostsByPopularity();
                homeContent.updateMiddleGrid(sortedPosts);
            }
        });

        return sidebar;
    }

}

