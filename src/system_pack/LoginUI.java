package system_pack;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import controllers.UserController;
import models.User;

public class LoginUI {

	private JFrame frmPosAndInventory;
	private JTextField usernameTextField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
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
	public LoginUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPosAndInventory = new JFrame();
		frmPosAndInventory.setTitle("POScarthos");
		frmPosAndInventory.setBounds(100, 100, 707, 382);
		frmPosAndInventory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPosAndInventory.getContentPane().setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(263, 11, 418, 320);
		frmPosAndInventory.getContentPane().add(scrollPane_1);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		scrollPane_1.setViewportView(loginPanel);
		loginPanel.setLayout(null);
		
		JLabel loginLabel = new JLabel("LOGIN");
		loginLabel.setFont(new Font("Montserrat ExtraBold", Font.BOLD, 25));
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.setBounds(154, 39, 104, 37);
		loginPanel.add(loginLabel);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Montserrat", Font.PLAIN, 15));
		usernameLabel.setBounds(49, 106, 77, 27);
		loginPanel.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Montserrat", Font.PLAIN, 15));
		passwordLabel.setBounds(49, 149, 77, 27);
		loginPanel.add(passwordLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setFont(new Font("SansSerif", Font.PLAIN, 15));
		usernameTextField.setBounds(136, 105, 222, 32);
		loginPanel.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("SansSerif", Font.PLAIN, 15));
		passwordField.setBounds(136, 149, 222, 32);
		loginPanel.add(passwordField);
		
		JLabel creditsLabel = new JLabel("©Bert Lagare. All rights reserved 2024");
		creditsLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
		creditsLabel.setBounds(10, 295, 191, 14);
		loginPanel.add(creditsLabel);
		
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Montserrat", Font.BOLD, 15));
		loginButton.setBounds(154, 209, 104, 37);
		loginPanel.add(loginButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 243, 320);
		frmPosAndInventory.getContentPane().add(scrollPane);
		
		JPanel logoPanel = new JPanel();
		scrollPane.setViewportView(logoPanel);
		logoPanel.setLayout(null);
		
		JLabel logoLabel = new JLabel("Logo here");
		Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
		logoLabel.setIcon(new ImageIcon(img));
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		logoLabel.setBounds(10, 11, 231, 228);
		logoPanel.add(logoLabel);
		
		JLabel companyNameLabel = new JLabel("Java Project");
		companyNameLabel.setFont(new Font("Montserrat ExtraBold", Font.BOLD, 15));
		companyNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		companyNameLabel.setBounds(32, 250, 174, 34);
		logoPanel.add(companyNameLabel);
		
		loginButton.addActionListener(e -> {
		    String username = usernameTextField.getText();
		    String password = new String(passwordField.getPassword());

		    User user = UserController.authenticate(username, password);

		    if (user != null) {
		        // Login successful
		        frmPosAndInventory.dispose(); // Close login window
		        MainMenuUI mainMenu = new MainMenuUI(); // Pass the user info
		        mainMenu.setVisible(true);
		    } else {
		        // Show an error message
		        JOptionPane.showMessageDialog(frmPosAndInventory, "Invalid username or password!", "Login Error", JOptionPane.ERROR_MESSAGE);
		    }
		});

		
	}

	public void setVisible(boolean visible) {
		frmPosAndInventory.setVisible(visible);
	}

}

