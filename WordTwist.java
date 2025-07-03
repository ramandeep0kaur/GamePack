import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;


public class WordTwist extends JFrame implements ActionListener {
    private JLabel scrambledLabel, resultLabel, scoreLabel, timerLabel, levelLabel;
    private JTextField guessField;
    private JButton checkButton, nextButton;
    private List<String> words;
    private String currentWord, scrambledWord;
    private int score = 0, level = 1, wordIndex = 0;
    private Timer timer;
    private int timeLeft = 60;

    public WordTwist() {
        setTitle("Word Twist - Real Style");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.setBackground(new Color(220, 240, 255));
        levelLabel = new JLabel("Level: 1", JLabel.LEFT);
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        timerLabel = new JLabel("Time: 60s", JLabel.RIGHT);

        Font f = new Font("Arial", Font.BOLD, 16);
        levelLabel.setFont(f);
        scoreLabel.setFont(f);
        timerLabel.setFont(f);

        topPanel.add(levelLabel);
        topPanel.add(scoreLabel);
        topPanel.add(timerLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1));
        centerPanel.setBackground(new Color(245, 250, 255));

        scrambledLabel = new JLabel("Scrambled: ", JLabel.CENTER);
        scrambledLabel.setFont(new Font("Arial", Font.BOLD, 28));
        centerPanel.add(scrambledLabel);

        guessField = new JTextField();
        guessField.setFont(new Font("Arial", Font.PLAIN, 20));
        centerPanel.add(guessField);

        checkButton = new JButton("Check");
        checkButton.setFont(new Font("Arial", Font.BOLD, 18));
        checkButton.addActionListener(this);
        centerPanel.add(checkButton);

        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        centerPanel.add(resultLabel);

        add(centerPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next Word");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> nextWord());
        add(nextButton, BorderLayout.SOUTH);

        initializeWords();
        startGame();
        setVisible(true);
    }

    private void initializeWords() {
        words = Arrays.asList(
                "banana", "orange", "laptop", "school", "flower", "window", "monkey", "pencil",
                "elephant", "giraffe", "keyboard", "monitor", "holiday", "science", "teacher", "station",
                "umbrella", "calendar", "backpack", "library"
        );
        Collections.shuffle(words);
    }

    private void startGame() {
        wordIndex = 0;
        score = 0;
        level = 1;
        loadWord();
        startTimer();
    }

    private void loadWord() {
        if (wordIndex >= words.size()) {
            JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + score);
            dispose();
            return;
        }

        currentWord = words.get(wordIndex);
        scrambledWord = shuffle(currentWord);
        scrambledLabel.setText("Unscramble: " + scrambledWord);
        guessField.setText("");
        resultLabel.setText("");
        nextButton.setEnabled(false);
        checkButton.setEnabled(true);
        timeLeft = 60;
        updateLabels();
    }

    private void updateLabels() {
        scoreLabel.setText("Score: " + score);
        timerLabel.setText("Time: " + timeLeft + "s");
        levelLabel.setText("Level: " + level);
    }

    private String shuffle(String word) {
        List<Character> chars = new ArrayList<>();
        for (char c : word.toCharArray()) chars.add(c);
        Collections.shuffle(chars);
        StringBuilder sb = new StringBuilder();
        for (char c : chars) sb.append(c);
        return sb.toString();
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft + "s");
            if (timeLeft <= 0) {
                timer.stop();
                resultLabel.setText("Time's up! The word was: " + currentWord);
                checkButton.setEnabled(false);
                nextButton.setEnabled(true);
            }
        });
        timer.start();
    }

    private void nextWord() {
        wordIndex++;
        if (wordIndex % 5 == 0) level++;
        loadWord();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String guess = guessField.getText().trim().toLowerCase();
        if (guess.equals(currentWord)) {
            resultLabel.setText("Correct! ✔");
            resultLabel.setForeground(Color.GREEN);
            score += 10;
            checkButton.setEnabled(false);
            nextButton.setEnabled(true);
            timer.stop();
        } else {
            resultLabel.setText("Wrong! Try again.");
            resultLabel.setForeground(Color.RED);
        }
        scoreLabel.setText("Score: " + score);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordTwist());
    }
}
