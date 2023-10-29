import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class RightSidebar {

    private DataStorageManager dataStorageManager;

    public RightSidebar(DataStorageManager dataStorageManager) {
        this.dataStorageManager = dataStorageManager;
    }

    public JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(300, sidebar.getPreferredSize().height));
        sidebar.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Statistics");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        // Create the JTable for statistics
        String[] columnNames = {"Statistic", "Value"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable statsTable = new JTable(model);
        statsTable.setEnabled(false); // Make the table not editable
        statsTable.setShowGrid(false); // Hide grid lines
        statsTable.setTableHeader(null); // Hide table header
        statsTable.setRowHeight(25);
        statsTable.setFillsViewportHeight(true);

        Map<String, Object> statistics = dataStorageManager.generateStatistics();
        for (String key : statistics.keySet()) {
            model.addRow(new Object[]{key, statistics.get(key)});
        }

        JScrollPane tableScrollPane = new JScrollPane(statsTable);
        tableScrollPane.setPreferredSize(new Dimension(250, statsTable.getRowHeight() * model.getRowCount()));
        tableScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);


        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(tableScrollPane);

        return sidebar;
    }
}
