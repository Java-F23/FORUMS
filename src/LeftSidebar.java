import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LeftSidebar {
    private JComboBox<String> userComboBox;
    private JTextField searchTextField;
    private JButton searchButton;
    private MainController mainController;
    private JTextField searchField; // Add a JTextField for search


    public LeftSidebar(MainController controller) {
        this.mainController = controller;
        this.userComboBox = new JComboBox<>();
        this.searchTextField = new JTextField();
        // Create and add a JButton for search
        searchButton = new JButton("Search");

        updateUserList(controller.getUsers());
    }

    public void updateUserList(ArrayList<User> users) {
        ActionListener[] listeners = userComboBox.getActionListeners();
        for (ActionListener listener : listeners) {
            userComboBox.removeActionListener(listener); // Remove listeners before update
        }

        userComboBox.removeAllItems();
        userComboBox.addItem("All Users"); // Add option to reset filter
        for (User user : users) {
            userComboBox.addItem(user.getUsername());
        }
        for (ActionListener listener : listeners) {
            userComboBox.addActionListener(listener); // Re-add listeners after update
        }
    }

    public JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(300, sidebar.getPreferredSize().height));
        sidebar.setBackground(Color.WHITE);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Filter & Sort");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        title.setBackground(Color.WHITE);

        JPanel userFilterPanel = new JPanel();
        userFilterPanel.setLayout(new BoxLayout(userFilterPanel, BoxLayout.Y_AXIS));
        userFilterPanel.setBackground(Color.WHITE);
        userFilterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userFilterLabel = new JLabel("Filter by User:");
        userFilterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userFilterPanel.add(userFilterLabel);

        userComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        userComboBox.setPreferredSize(new Dimension(200, 30));
        userComboBox.setMaximumSize(new Dimension(200, 30));
        userFilterPanel.add(userComboBox);

        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(userFilterPanel);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel searchLabel = new JLabel("Search by Keyword:");
        searchLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.add(searchLabel);

        // Create and add a JTextField for search
        searchField = new JTextField();
        searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchField.setPreferredSize(new Dimension(200, 30)); // Set the preferred size
        searchField.setMaximumSize(new Dimension(200, 30)); // Set the maximum size
        searchPanel.add(searchField);

        searchButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.add(searchButton);

        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(searchPanel);


        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new BoxLayout(sortPanel, BoxLayout.Y_AXIS));
        sortPanel.setBackground(Color.WHITE);
        sortPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setBackground(Color.WHITE);
        sortLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sortPanel.add(sortLabel);

        JRadioButton sortByDate = new JRadioButton("Date");
        sortByDate.setBackground(Color.WHITE);
        sortByDate.setAlignmentX(Component.LEFT_ALIGNMENT);
        sortPanel.add(sortByDate);

        JRadioButton sortByPopularity = new JRadioButton("Popularity");
        sortByPopularity.setBackground(Color.WHITE);
        sortByPopularity.setAlignmentX(Component.LEFT_ALIGNMENT);
        sortPanel.add(sortByPopularity);

        ButtonGroup sortGroup = new ButtonGroup();
        sortGroup.add(sortByDate);
        sortGroup.add(sortByPopularity);

        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(sortPanel);
        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        // Listeners
        userComboBox.addActionListener(e -> mainController.filterPostsByAuthorAndUpdateView((String) userComboBox.getSelectedItem()));
        searchButton.addActionListener(e -> handleSearch());
        sortByDate.addActionListener(e -> mainController.sortPostsByDateAndUpdateView());
        sortByPopularity.addActionListener(e -> mainController.sortPostsByPopularityAndUpdateView());

        return sidebar;
    }

    private void handleSearch() {
        String keyword = searchField.getText(); // Change this line
        mainController.searchPostsAndUpdateView(keyword);
    }
}
