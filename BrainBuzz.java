import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class BrainBuzz extends JFrame implements ActionListener {
    private JLabel patternLabel, timerLabel, roundsLabel;
    private JTextField inputField;
    private JButton submitButton;
    private String currentPattern = "";
    private int score = 0, round = 1, timeLeft = 5;
    private Timer countdownTimer;

    public BrainBuzz() {
        setTitle("🧠 Brain Buzz Memory Game");
        setSize(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));
        getContentPane().setBackground(new Color(250, 250, 255));

        timerLabel = new JLabel("Get ready to memorize!", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(timerLabel);

        patternLabel = new JLabel("", SwingConstants.CENTER);
        patternLabel.setFont(new Font("Consolas", Font.BOLD, 28));
        add(patternLabel);

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 18));
        inputField.setEnabled(false);
        add(inputField);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setEnabled(false);
        submitButton.addActionListener(this);
        add(submitButton);

        roundsLabel = new JLabel("Round: 1 of 5", SwingConstants.CENTER);
        roundsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        add(roundsLabel);

        add(new JLabel("Memorize → Hide → Type → Submit", SwingConstants.CENTER));

        startNewRound();
        setVisible(true);
    }

    private void startNewRound() {
        if (round > 5) {
            JOptionPane.showMessageDialog(this, "🎉 Game Over!\nYour Final Score: " + score + "/5");
            dispose();
            return;
        }

        currentPattern = generatePattern(round + 2); // pattern length increases with rounds
        patternLabel.setText(currentPattern);
        inputField.setText("");
        inputField.setEnabled(false);
        submitButton.setEnabled(false);
        timerLabel.setText("Memorize this pattern!");
        roundsLabel.setText("Round: " + round + " of 5");

        timeLeft = 5;
        countdownTimer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft == 0) {
                countdownTimer.stop();
                patternLabel.setText("???");
                inputField.setEnabled(true);
                submitButton.setEnabled(true);
                timerLabel.setText("Now type the pattern!");
                inputField.requestFocus();
            }
        });
        countdownTimer.start();
    }

    private String generatePattern(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String answer = inputField.getText().trim().toUpperCase();
        if (answer.equals(currentPattern)) {
            score++;
            JOptionPane.showMessageDialog(this, "✅ Correct!");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Wrong!\nCorrect: " + currentPattern);
        }
        round++;
        startNewRound();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BrainBuzz());
    }
}
