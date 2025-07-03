import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class TypingFury extends JFrame implements ActionListener, KeyListener {

    private String[] sentences = {
        "The quick brown fox jumps over the lazy dog",
        "Java programming is fun and powerful",
        "Typing speed test improves your skills",
        "Practice makes perfect in coding",
        "Artificial intelligence is the future"
    };

    private JLabel sentenceLabel;
    private JTextField typingField;
    private JLabel resultLabel;
    private JButton startButton;

    private String currentSentence;
    private long startTime;
    private boolean testRunning = false;

    public TypingFury() {
        setTitle("Typing Fury - Speed Test");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.DARK_GRAY);

        sentenceLabel = new JLabel("Click Start to begin", SwingConstants.CENTER);
        sentenceLabel.setFont(new Font("Serif", Font.BOLD, 20));
        sentenceLabel.setForeground(Color.WHITE);

        typingField = new JTextField();
        typingField.setFont(new Font("Monospaced", Font.PLAIN, 18));
        typingField.setEnabled(false);
        typingField.addKeyListener(this);

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 18));
        resultLabel.setForeground(Color.CYAN);

        startButton = new JButton("Start Test");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.addActionListener(this);

        add(sentenceLabel, BorderLayout.NORTH);
        add(typingField, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
        add(startButton, BorderLayout.EAST);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton && !testRunning) {
            startTest();
        }
    }

    private void startTest() {
        Random rand = new Random();
        currentSentence = sentences[rand.nextInt(sentences.length)];
        sentenceLabel.setText(currentSentence);
        typingField.setText("");
        typingField.setEnabled(true);
        typingField.requestFocus();
        resultLabel.setText("");
        startTime = System.currentTimeMillis();
        testRunning = true;
        startButton.setEnabled(false);
    }

    private void endTest() {
        long elapsedTime = System.currentTimeMillis() - startTime; // in ms
        String typedText = typingField.getText();

        int correctChars = countCorrectChars(currentSentence, typedText);
        double accuracy = (correctChars * 100.0) / currentSentence.length();

        double wordsTyped = typedText.trim().split("\\s+").length;
        double minutes = elapsedTime / 60000.0;
        double wpm = wordsTyped / minutes;

        resultLabel.setText(String.format("Speed: %.2f WPM | Accuracy: %.2f%%", wpm, accuracy));
        typingField.setEnabled(false);
        startButton.setEnabled(true);
        testRunning = false;
    }

    private int countCorrectChars(String original, String typed) {
        int count = 0;
        for (int i = 0; i < Math.min(original.length(), typed.length()); i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (testRunning) {
            if (typingField.getText().length() >= currentSentence.length()) {
                endTest();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ignored) {}

        SwingUtilities.invokeLater(() -> new TypingFury());
    }
}
