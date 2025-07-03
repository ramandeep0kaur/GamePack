import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[] buttons = new JButton[9];
    private boolean isXturn = true;
    private JLabel statusLabel, scoreLabel;
    private int xScore = 0, oScore = 0;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon("assets/background.png");
                g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new GridLayout(3, 3));

        Font font = new Font("Arial", Font.BOLD, 60);
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(font);
            buttons[i].setBackground(Color.WHITE);
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(255, 228, 196)); // Soft orange

        statusLabel = new JLabel("X's Turn");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        scoreLabel = new JLabel("Score - X: 0 | O: 0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(statusLabel);
        topPanel.add(scoreLabel);

        add(topPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (!button.getText().equals("")) return;

        button.setText(isXturn ? "X" : "O");
        button.setForeground(isXturn ? Color.RED : Color.BLUE);

        if (checkWin()) {
            String winner = isXturn ? "X" : "O";
            statusLabel.setText(winner + " Wins!");
            if (isXturn) xScore++; else oScore++;
            updateScore();
            disableAllButtons();
            restartGame();
        } else if (isBoardFull()) {
            statusLabel.setText("It's a Draw!");
            restartGame();
        } else {
            isXturn = !isXturn;
            statusLabel.setText((isXturn ? "X" : "O") + "'s Turn");
        }
    }

    private boolean checkWin() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = buttons[i].getText();
        }

        for (int i = 0; i < 3; i++) {
            if (!board[i][0].equals("") && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) return true;
            if (!board[0][i].equals("") && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) return true;
        }
        if (!board[0][0].equals("") && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) return true;
        if (!board[0][2].equals("") && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (JButton b : buttons) {
            if (b.getText().equals("")) return false;
        }
        return true;
    }

    private void disableAllButtons() {
        for (JButton b : buttons) {
            b.setEnabled(false);
        }
    }

    private void restartGame() {
        Timer delay = new Timer(1500, e -> {
            for (JButton b : buttons) {
                b.setText("");
                b.setEnabled(true);
            }
            isXturn = true;
            statusLabel.setText("X's Turn");
        });
        delay.setRepeats(false);
        delay.start();
    }

    private void updateScore() {
        scoreLabel.setText("Score - X: " + xScore + " | O: " + oScore);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToe());
    }
}
