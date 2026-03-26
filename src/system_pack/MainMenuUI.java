package system_pack;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

public class MainMenuUI {

    private JFrame frmPosAndInventory;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainMenuUI window = new MainMenuUI();
                    window.frmPosAndInventory.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainMenuUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmPosAndInventory = new JFrame();
        frmPosAndInventory.setTitle("POScarthos");
        frmPosAndInventory.setBounds(100, 100, 653, 425);
        frmPosAndInventory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmPosAndInventory.getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(255, 11, 372, 363);
        frmPosAndInventory.getContentPane().add(scrollPane);

        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        scrollPane.setViewportView(panel);
        panel.setLayout(null);

        JLabel mainMenuLabel = new JLabel("Main Menu");
        mainMenuLabel.setFont(new Font("Montserrat ExtraBold", Font.BOLD, 20));
        mainMenuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainMenuLabel.setBounds(105, 32, 161, 39);
        panel.add(mainMenuLabel);

        // POS Button Action
        JButton posButton = new JButton("POS");
        posButton.setFont(new Font("Montserrat", Font.PLAIN, 18));
        posButton.setBounds(105, 94, 161, 39);
        posButton.addActionListener(e -> {
            frmPosAndInventory.dispose(); // Close MainMenuUI
            POS_UI posUI = new POS_UI(); // Open POS_UI
            posUI.setVisible(true);
        });
        panel.add(posButton);

        // Inventory Button Action
        JButton inventoryButton = new JButton("Inventory");
        inventoryButton.setFont(new Font("Montserrat", Font.PLAIN, 18));
        inventoryButton.setBounds(105, 144, 161, 39);
        inventoryButton.addActionListener(e -> {
            frmPosAndInventory.dispose(); // Close MainMenuUI
            InventoryUI inventoryUI = new InventoryUI(); // Open InventoryUI
            inventoryUI.setVisible(true);
        });
        panel.add(inventoryButton);

        // Settings Button Action
        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Montserrat", Font.PLAIN, 18));
        settingsButton.setBounds(105, 194, 161, 39);
        settingsButton.addActionListener(e -> {
            frmPosAndInventory.dispose(); // Close MainMenuUI
            SettingsUI settingsUI = new SettingsUI(); // Open SettingsUI
            settingsUI.setVisible(true);
        });
        panel.add(settingsButton);

        // Logout Button Action
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Montserrat", Font.PLAIN, 18));
        logoutButton.setBounds(105, 244, 161, 39);
        logoutButton.addActionListener(e -> {
            frmPosAndInventory.dispose(); // Close MainMenuUI
            LoginUI loginUI = new LoginUI(); // Open LoginUI
            loginUI.setVisible(true);
        });
        panel.add(logoutButton);

        JLabel creditsLabel = new JLabel("©Bert Lagare. All rights reserved 2024");
        creditsLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
        creditsLabel.setBounds(10, 336, 199, 14);
        panel.add(creditsLabel);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 11, 236, 363);
        frmPosAndInventory.getContentPane().add(scrollPane_1);

        JPanel logoPanel = new JPanel();
        scrollPane_1.setViewportView(logoPanel);
        logoPanel.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel("Logo here");
        Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
        lblNewLabel_2.setIcon(new ImageIcon(img));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(10, 38, 224, 200);
        logoPanel.add(lblNewLabel_2);

        JLabel companyNameLabel = new JLabel("Java Project");
        companyNameLabel.setFont(new Font("Montserrat", Font.BOLD, 15));
        companyNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        companyNameLabel.setBounds(21, 280, 183, 34);
        logoPanel.add(companyNameLabel);
    }

    /**
     * Makes the frame visible.
     */
    public void setVisible(boolean visible) {
        frmPosAndInventory.setVisible(visible);
    }
}
