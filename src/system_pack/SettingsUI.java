package system_pack;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import controllers.UserController;
import models.User;

import java.awt.Font;

public class SettingsUI {

    private JFrame frmSettings;
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField phoneNoField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SettingsUI window = new SettingsUI();
                window.frmSettings.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public SettingsUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmSettings = new JFrame();
        frmSettings.setTitle("Settings");
        frmSettings.setBounds(100, 100, 614, 424);
        frmSettings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmSettings.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.RAISED));
        panel.setBounds(10, 11, 167, 362);
        frmSettings.getContentPane().add(panel);
        panel.setLayout(null);

        JButton addAccountButton = new JButton("Add Account");
        addAccountButton.setFont(new Font("Montserrat", Font.PLAIN, 15));
        addAccountButton.setBounds(10, 11, 147, 47);
        addAccountButton.addActionListener(e -> addAccount());
        panel.add(addAccountButton);

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setFont(new Font("Montserrat", Font.PLAIN, 15));
        deleteAccountButton.setBounds(10, 68, 147, 47);
        deleteAccountButton.addActionListener(e -> deleteAccount());
        panel.add(deleteAccountButton);

        JButton updateAccountButton = new JButton("Update Account");
        updateAccountButton.setFont(new Font("Montserrat", Font.PLAIN, 15));
        updateAccountButton.setBounds(10, 126, 147, 47);
        updateAccountButton.addActionListener(e -> updateAccount());
        panel.add(updateAccountButton);

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Montserrat", Font.PLAIN, 15));
        mainMenuButton.setBounds(10, 304, 147, 47);
        mainMenuButton.addActionListener(e -> {
            frmSettings.dispose();
            MainMenuUI mainMenu = new MainMenuUI();
            mainMenu.setVisible(true);
        });
        panel.add(mainMenuButton);

        createLabelsAndFields();
    }

    /**
     * Creates the labels and input fields for the user details.
     */
    private void createLabelsAndFields() {
        createLabel("First Name", 40);
        firstNameField = createTextField(40);

        createLabel("Middle Name", 84);
        middleNameField = createTextField(84);

        createLabel("Last Name", 128);
        lastNameField = createTextField(128);

        createLabel("Username", 172);
        usernameField = createTextField(172);

        createLabel("Password", 216);
        passwordField = new JPasswordField();
        passwordField.setBounds(334, 216, 216, 33);
        frmSettings.getContentPane().add(passwordField);

        createLabel("Email", 260);
        emailField = createTextField(260);

        createLabel("Phone No.", 304);
        phoneNoField = createTextField(304);
    }
    

    
    
    
    /*
     * Adds a new user account.
     */
    private void addAccount() {
        try {
            User user = collectUserInput();
            if (UserController.addUser(user)) {
                JOptionPane.showMessageDialog(frmSettings, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frmSettings, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmSettings, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes an existing user account.
     */
    private void deleteAccount() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frmSettings, "Username is required to delete an account.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (UserController.deleteUser(username)) {
        	clearFields();
            JOptionPane.showMessageDialog(frmSettings, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frmSettings, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates an existing user account.
     */
    private void updateAccount() {
        try {
            User user = collectUserInput();
            if (UserController.updateUser(user)) {
                JOptionPane.showMessageDialog(frmSettings, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(frmSettings, "Failed to update user. User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmSettings, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void clearFields() {
    	firstNameField.setText("");
    	middleNameField.setText("");
    	lastNameField.setText("");
    	usernameField.setText("");
    	passwordField.setText("");
    	emailField.setText("");
    	phoneNoField.setText("");
    }

    /**
     * Collects user input from the fields and validates it.
     */
    private User collectUserInput() throws Exception {
        String firstName = firstNameField.getText().trim();
        String middleName = middleNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String email = emailField.getText().trim();
        String phoneNo = phoneNoField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw new Exception("All required fields must be filled.");
        }
        
        if (!email.endsWith("@gmail.com") && !email.endsWith("@yahoo.com")) { 
        	JOptionPane.showMessageDialog(null, "Enter a valid email address ending with @gmail.com or @yahoo.com"); 
        	return null; 
        }                   
        
        if (phoneNo.length() != 11) {
            JOptionPane.showMessageDialog(null, "Enter a valid mobile number. \nMust have 11-digits.");
            return null;
        }

        return new User(firstName, middleName, lastName, username, password, email, phoneNo);
    }

    /**
     * Creates a label.
     */
    private JLabel createLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Montserrat", Font.PLAIN, 15));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBounds(199, y, 125, 33);
        frmSettings.getContentPane().add(label);
        return label;
    }

    /*
     * Creates a text field.
     */
    private JTextField createTextField(int y) {
        JTextField textField = new JTextField();
        textField.setBounds(334, y, 216, 33);
        textField.setColumns(10);
        frmSettings.getContentPane().add(textField);
        return textField;
    }

	public void setVisible(boolean visible) {
		frmSettings.setVisible(visible);
	}

}
