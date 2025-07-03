import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Signup extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public Signup() {
        setTitle("GamePack - Sign Up");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Create a New Account"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");
        registerButton.addActionListener(this);
        backButton.addActionListener(this);
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, gbc);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both fields.");
            return;
        }

        if (e.getSource() == registerButton) {
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement check = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
                check.setString(1, username);
                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Username already exists.");
                } else {
                    PreparedStatement insert = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
                    insert.setString(1, username);
                    insert.setString(2, password);
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Signup successful!");
                    dispose();
                    new Login();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error!\n" + ex.getMessage());
            }
        } else if (e.getSource() == backButton) {
            dispose();
            new Login();
        }
    }
}
