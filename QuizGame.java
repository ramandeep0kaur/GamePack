import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;



public class QuizGame extends JFrame implements ActionListener {
    private JLabel questionLabel;
    private JButton[] options = new JButton[4];
    private JLabel scoreLabel, feedbackLabel;
    private int current = 0, score = 0;
    private List<Question> questions;
    private String correctAnswer;

    public QuizGame() {
        setTitle("Quiz Game - Real Style");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 250, 255));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 22));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        optionsPanel.setBackground(new Color(245, 250, 255));

        for (int i = 0; i < 4; i++) {
            options[i] = new JButton();
            options[i].setFocusPainted(false);
            options[i].setFont(new Font("Arial", Font.PLAIN, 16));
            options[i].setBackground(Color.WHITE);
            options[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            options[i].addActionListener(this);
            options[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            optionsPanel.add(options[i]);
        }

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(new Color(0, 102, 204));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        feedbackLabel.setForeground(Color.DARK_GRAY);
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        add(questionLabel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(scoreLabel, BorderLayout.SOUTH);
        add(feedbackLabel, BorderLayout.PAGE_END);

        generateQuestions();
        loadQuestion();

        setVisible(true);
    }

    private void generateQuestions() {
        questions = new ArrayList<>();
        // Easy Level
        questions.add(new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 1));
        questions.add(new Question("Capital of India?", new String[]{"Delhi", "Mumbai", "Kolkata", "Chennai"}, 0));
        questions.add(new Question("Which color is the sky?", new String[]{"Red", "Green", "Blue", "Black"}, 2));
        questions.add(new Question("Which day comes after Monday?", new String[]{"Sunday", "Tuesday", "Friday", "Wednesday"}, 1));
        questions.add(new Question("What shape has 3 sides?", new String[]{"Square", "Triangle", "Circle", "Hexagon"}, 1));

        // Easy-Medium
        questions.add(new Question("Which planet is closest to the Sun?", new String[]{"Earth", "Venus", "Mercury", "Mars"}, 2));
        questions.add(new Question("How many continents are there?", new String[]{"5", "6", "7", "8"}, 2));
        questions.add(new Question("Who is the father of computers?", new String[]{"Bill Gates", "Alan Turing", "Charles Babbage", "Steve Jobs"}, 2));
        questions.add(new Question("Fastest land animal?", new String[]{"Lion", "Horse", "Cheetah", "Tiger"}, 2));
        questions.add(new Question("What is H2O?", new String[]{"Hydrogen", "Water", "Oxygen", "Acid"}, 1));

        // Medium Level
        questions.add(new Question("Which gas do plants absorb?", new String[]{"Oxygen", "Carbon Dioxide", "Hydrogen", "Nitrogen"}, 1));
        questions.add(new Question("Which is the longest river?", new String[]{"Nile", "Amazon", "Ganga", "Mississippi"}, 0));
        questions.add(new Question("Square root of 144?", new String[]{"11", "12", "13", "14"}, 1));
        questions.add(new Question("Currency of Japan?", new String[]{"Yen", "Won", "Dollar", "Rupee"}, 0));
        questions.add(new Question("Who painted Mona Lisa?", new String[]{"Picasso", "Van Gogh", "Da Vinci", "Michelangelo"}, 2));

        // More Medium
        questions.add(new Question("Which country is called the Land of Rising Sun?", new String[]{"China", "Australia", "Japan", "Korea"}, 2));
        questions.add(new Question("Which language is used to create Android Apps?", new String[]{"Python", "Java", "Swift", "C++"}, 1));
        questions.add(new Question("Which metal is liquid at room temperature?", new String[]{"Iron", "Copper", "Mercury", "Silver"}, 2));
        questions.add(new Question("Who discovered gravity?", new String[]{"Einstein", "Newton", "Galileo", "Edison"}, 1));
        questions.add(new Question("Where is Eiffel Tower?", new String[]{"London", "Berlin", "Paris", "Rome"}, 2));

        // Final 10
        questions.add(new Question("Which festival is known as the festival of lights?", new String[]{"Holi", "Eid", "Diwali", "Christmas"}, 2));
        questions.add(new Question("Which organ purifies blood?", new String[]{"Heart", "Liver", "Kidney", "Lungs"}, 2));
        questions.add(new Question("Largest desert in the world?", new String[]{"Sahara", "Thar", "Gobi", "Kalahari"}, 0));
        questions.add(new Question("What does CPU stand for?", new String[]{"Central Performance Unit", "Control Panel Unit", "Central Processing Unit", "Compute Power Unit"}, 2));
        questions.add(new Question("Which planet has rings?", new String[]{"Earth", "Venus", "Saturn", "Mars"}, 2));
        questions.add(new Question("National animal of India?", new String[]{"Lion", "Tiger", "Elephant", "Peacock"}, 1));
        questions.add(new Question("How many legs does a spider have?", new String[]{"6", "8", "10", "12"}, 1));
        questions.add(new Question("Taj Mahal is located in?", new String[]{"Delhi", "Agra", "Jaipur", "Mumbai"}, 1));
        questions.add(new Question("Which blood cells help fight infection?", new String[]{"WBC", "RBC", "Platelets", "Plasma"}, 0));
        questions.add(new Question("Largest mammal?", new String[]{"Elephant", "Blue Whale", "Hippo", "Shark"}, 1));

        Collections.shuffle(questions);
    }

    private void loadQuestion() {
        if (current >= questions.size()) {
            JOptionPane.showMessageDialog(this, "Quiz Over! Your Score: " + score);
            dispose();
            return;
        }

        Question q = questions.get(current);
        questionLabel.setText((current + 1) + ". " + q.getQuestion());

        List<String> shuffledOptions = new ArrayList<>(Arrays.asList(q.getOptions()));
        Collections.shuffle(shuffledOptions);
        correctAnswer = q.getCorrectAnswer();

        for (int i = 0; i < 4; i++) {
            options[i].setText(shuffledOptions.get(i));
            options[i].setEnabled(true);
            options[i].setBackground(Color.WHITE);
        }
        feedbackLabel.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        String selected = clicked.getText();

        for (JButton btn : options) {
            btn.setEnabled(false);
        }

        if (selected.equals(correctAnswer)) {
            clicked.setBackground(Color.GREEN);
            feedbackLabel.setText("Correct!");
            score += 10;
        } else {
            clicked.setBackground(Color.RED);
            feedbackLabel.setText("Incorrect! Correct: " + correctAnswer);
            for (JButton btn : options) {
                if (btn.getText().equals(correctAnswer)) {
                    btn.setBackground(Color.GREEN);
                    break;
                }
            }
        }

        scoreLabel.setText("Score: " + score);

        Timer delay = new Timer(1500, evt -> {
            current++;
            loadQuestion();
        });
        delay.setRepeats(false);
        delay.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizGame());
    }
}

class Question {
    private String question;
    private String[] options;
    private int correctIndex;

    public Question(String question, String[] options, int correctIndex) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return options[correctIndex];
    }
}
