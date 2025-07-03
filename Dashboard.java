import java.awt.*;
import javax.swing.*;

public class Dashboard extends JFrame {
    private String username;

    public Dashboard(String username) {
        this.username = username;

        setTitle("GamePack - Dashboard");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.NORTH);

        // Game buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        addGameButton(buttonPanel, "⭕ Tic Tac Toe", () -> new TicTacToe());
        addGameButton(buttonPanel, "🧠 Brain Buzz", () -> new BrainBuzz());
        addGameButton(buttonPanel, "🎯 Math Blaster", () -> new MathBlaster());
        addGameButton(buttonPanel, "⌨️ Typing Fury", () -> new TypingFury());
        addGameButton(buttonPanel, "🧩 Puzzle Pop", () -> new PuzzlePop());
        addGameButton(buttonPanel, "🔤 Word Twist", () -> new WordTwist());
        addGameButton(buttonPanel, "❓ Quiz Game", () -> new QuizGame());

        add(buttonPanel, BorderLayout.CENTER);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new Login();
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addGameButton(JPanel panel, String name, Runnable action) {
        JButton button = new JButton(name);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.addActionListener(e -> {
            SwingUtilities.invokeLater(action::run);
        });
        panel.add(button);
    }
}
