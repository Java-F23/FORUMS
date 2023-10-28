import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

 class Header extends JPanel {
     private static final Color PRIMARY_COLOR = new Color(60, 130, 190); // Calm Blue
     private static final Color SECONDARY_COLOR = new Color(240, 240, 245); // Soft Light Gray

     public Header() {
         JPanel header = new JPanel(new BorderLayout());
         header.setPreferredSize(new Dimension(1600, 45));
         header.setBackground(new Color(60, 130, 190));

         // Create right panel for the logo image aligned to the left of the panel
         ImageIcon logo = new ImageIcon("logo.png");
         Image image = logo.getImage();
         int newWidth = image.getWidth(null) / 15;
         int newHeight = image.getHeight(null) / 15;
         Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
         JLabel logoLabel = new JLabel(new ImageIcon(resizedImage));

         logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
         header.add(logoLabel, BorderLayout.WEST);

         // Create left panel for the logout button aligned to the right of the panel
         JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
         JButton logoutButton = createStyledButton("Logout");
         logoutPanel.add(logoutButton);
         logoutPanel.setBackground(new Color(60, 130, 190));
         header.add(logoutPanel, BorderLayout.EAST);

         // Create middle panel for the 3 buttons aligned to the center of the panel and spread equally with spacing
         JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 200, 6));
         navigationPanel.setBackground(new Color(60, 130, 190));
         JButton homeButton = createStyledButton("Home");
         JButton profileButton = createStyledButton("Profile");
         JButton settingsButton = createStyledButton("Settings");

         navigationPanel.add(homeButton);
         navigationPanel.add(profileButton);
         navigationPanel.add(settingsButton);
         header.add(navigationPanel, BorderLayout.CENTER);
    }

     private JButton createStyledButton(String text) {
         JButton button = new JButton(text);
         button.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseEntered(MouseEvent e) {
                 button.setBackground(button.getBackground().brighter());
             }

             @Override
             public void mouseExited(MouseEvent e) {
                 button.setBackground(new Color(60, 130, 190));
             }
         });

         if (text.equals("Logout")){
             button.setFocusPainted(false);
             button.setBackground(new Color(180, 80, 60));
             button.setFont(new Font("Arial", Font.BOLD, 18));
             return button;
         }
         button.setFont(new Font("Calibri", Font.PLAIN, 20));
         button.setBorderPainted(false);
         button.setFocusPainted(false);
         button.setBackground(new Color(60, 130, 190));
         button.setForeground(Color.WHITE); // Setting text color to white for contrast against PRIMARY_COLOR

         return button;
     }
}
