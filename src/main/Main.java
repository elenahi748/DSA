package main;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        // Set up the frame with CardLayout
        this.setTitle("Last Day On Earth");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Custom exit handling
        this.setResizable(false);

        // Create a CardLayout to switch between panels
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Create the menu and game panels
        MenuPanel menuPanel = new MenuPanel(mainPanel, cardLayout, this);
        MapPanel mapPanel = new MapPanel(mainPanel, cardLayout, this);
        Panel gamePanel = new Panel(mainPanel, cardLayout, this);

        // Add panels to the main panel
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamePanel, "Game");
        mainPanel.add(mapPanel, "Map");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int scaledWidth = (int) (screenSize.width * 0.9);
        int scaledHeight = (int) (screenSize.height * 0.9);

        // Add main panel to the frame
        this.add(mainPanel);
        this.pack();
        this.setPreferredSize(new Dimension(scaledWidth, scaledHeight));
        this.setSize(scaledWidth, scaledHeight);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Custom exit confirmation
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    public void showMapPanel(JPanel mainPanel, CardLayout cardLayout) {
        cardLayout.show(mainPanel, "Map");
    }
    public void startGameWithMap(JPanel mainPanel, CardLayout cardLayout, String mapType) {
        // Gọi hàm setMapType trên gamePanel nếu cần
        cardLayout.show(mainPanel, "Game");
        // Cần gọi startGameThread() ở Panel tại đây nếu muốn
    }
    public static void main(String[] args) {
        new Main();
    }
}
