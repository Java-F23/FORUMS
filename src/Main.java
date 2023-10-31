import javax.swing.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create and display the MainPlatformFrame
        SwingUtilities.invokeLater(() -> {
            new MainController(); // This should be responsible for setting up the UI
        });
    }
}
