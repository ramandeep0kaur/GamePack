import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;



public class PuzzlePop extends JFrame implements ActionListener {
    private JButton[] tiles = new JButton[9];
    private int blankIndex;
    private int moves = 0;
    private JLabel movesLabel, timerLabel;
    private Timer gameTimer;
    private int secondsPassed = 0;

    public PuzzlePop() {
        setTitle("Puzzle Pop - Advanced");
        setSize(450, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        movesLabel = new JLabel("Moves: 0", SwingConstants.CENTER);
        timerLabel = new JLabel("Time: 0s", SwingConstants.CENTER);
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame());

        topPanel.add(movesLabel);
        topPanel.add(timerLabel);
        topPanel.add(restartButton);

        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Integer[] nums = generateSolvablePuzzle();
        for (int i = 0; i < 9; i++) {
            tiles[i] = new JButton();
            tiles[i].setFont(new Font("Arial", Font.BOLD, 30));
            tiles[i].setBackground(Color.WHITE);
            tiles[i].setFocusable(false);
            tiles[i].addActionListener(this);
            if (nums[i] == 0) {
                tiles[i].setText("");
                tiles[i].setBackground(Color.LIGHT_GRAY);
                blankIndex = i;
            } else {
                tiles[i].setText(String.valueOf(nums[i]));
            }
            gridPanel.add(tiles[i]);
        }

        add(topPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);

        startTimer();
        setVisible(true);
    }

    private void startTimer() {
        gameTimer = new Timer(1000, e -> {
            secondsPassed++;
            timerLabel.setText("Time: " + secondsPassed + "s");
        });
        gameTimer.start();
    }

    private void restartGame() {
        gameTimer.stop();
        secondsPassed = 0;
        timerLabel.setText("Time: 0s");
        moves = 0;
        movesLabel.setText("Moves: 0");

        Integer[] nums = generateSolvablePuzzle();
        for (int i = 0; i < 9; i++) {
            if (nums[i] == 0) {
                tiles[i].setText("");
                tiles[i].setBackground(Color.LIGHT_GRAY);
                blankIndex = i;
            } else {
                tiles[i].setText(String.valueOf(nums[i]));
                tiles[i].setBackground(Color.WHITE);
            }
        }
        gameTimer.start();
    }

    private Integer[] generateSolvablePuzzle() {
        List<Integer> list;
        do {
            list = new ArrayList<>();
            for (int i = 0; i < 9; i++) list.add(i);
            Collections.shuffle(list);
        } while (!isSolvable(list));
        return list.toArray(new Integer[0]);
    }

    private boolean isSolvable(List<Integer> list) {
        int inv = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (list.get(i) != 0 && list.get(j) != 0 && list.get(i) > list.get(j)) inv++;
            }
        }
        return inv % 2 == 0;
    }

    private boolean isAdjacent(int i1, int i2) {
        int r1 = i1 / 3, c1 = i1 % 3;
        int r2 = i2 / 3, c2 = i2 % 3;
        return (Math.abs(r1 - r2) + Math.abs(c1 - c2)) == 1;
    }

    private boolean isSolved() {
        for (int i = 0; i < 8; i++) {
            if (!tiles[i].getText().equals(String.valueOf(i + 1))) return false;
        }
        return tiles[8].getText().equals("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        int index = Arrays.asList(tiles).indexOf(clicked);

        if (isAdjacent(index, blankIndex)) {
            tiles[blankIndex].setText(clicked.getText());
            tiles[blankIndex].setBackground(Color.WHITE);
            clicked.setText("");
            clicked.setBackground(Color.LIGHT_GRAY);
            blankIndex = index;
            moves++;
            movesLabel.setText("Moves: " + moves);

            if (isSolved()) {
                gameTimer.stop();
                JOptionPane.showMessageDialog(this,
                        "🎉 Well done! Solved in " + moves + " moves and " + secondsPassed + " seconds.");
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PuzzlePop::new);
    }
}
