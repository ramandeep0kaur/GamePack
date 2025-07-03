import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class MathBlaster extends JFrame implements ActionListener {
    private JLabel questionLabel, scoreLabel, timerLabel, levelLabel;
    private JTextField answerField;
    private JButton submitButton;
    private int correctAnswer;
    private int score = 0, timeLeft = 60, correctCount = 0, level = 1;
    private Timer timer;
    private Random rand = new Random();

    public MathBlaster() {
        setTitle("Math Blaster - Level Mode");
        setSize(400, 280);
        setLayout(new GridLayout(6, 1));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        levelLabel = new JLabel("Level: 1", SwingConstants.CENTER);
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        timerLabel = new JLabel("Time Left: 60s", SwingConstants.CENTER);
        questionLabel = new JLabel("", SwingConstants.CENTER);

        answerField = new JTextField();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        add(levelLabel);
        add(scoreLabel);
        add(timerLabel);
        add(questionLabel);
        add(answerField);
        add(submitButton);

        generateQuestion();
        startTimer();

        setVisible(true);
    }

    private void generateQuestion() {
        int a = 1, b = 1, op = 0;
        switch (level) {
            case 1 -> {
                a = rand.nextInt(20) + 1;
                b = rand.nextInt(20) + 1;
                op = rand.nextInt(2); // + or -
            }
            case 2 -> {
                a = rand.nextInt(50) + 1;
                b = rand.nextInt(50) + 1;
                op = rand.nextInt(3); // +, -, *
            }
            case 3 -> {
                a = rand.nextInt(100) + 1;
                b = rand.nextInt(1, 10);
                a = a - (a % b); // make divisible
                op = rand.nextInt(4); // +, -, *, /
            }
        }

        switch (op) {
            case 0 -> {
                questionLabel.setText(a + " + " + b);
                correctAnswer = a + b;
            }
            case 1 -> {
                questionLabel.setText(a + " - " + b);
                correctAnswer = a - b;
            }
            case 2 -> {
                questionLabel.setText(a + " * " + b);
                correctAnswer = a * b;
            }
            case 3 -> {
                questionLabel.setText(a + " / " + b);
                correctAnswer = a / b;
            }
        }
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft + "s");
            if (timeLeft == 0) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Time's up! Your final score: " + score);
                dispose();
            }
        });
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int userAnswer = Integer.parseInt(answerField.getText().trim());
            if (userAnswer == correctAnswer) {
                score += 10;
                correctCount++;
                if (correctCount % 10 == 0 && level < 3) {
                    level++;
                    JOptionPane.showMessageDialog(this, "Level Up! Now Level " + level);
                    levelLabel.setText("Level: " + level);
                }
                scoreLabel.setText("Score: " + score);
            }
            answerField.setText("");
            generateQuestion();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MathBlaster::new);
    }
}
