import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class representing the Tic Tac Toe game GUI
public class TicTacToeGUI extends JFrame implements ActionListener {
    private JButton[] buttons; // Array to hold the buttons representing the game grid
    private Map map; // Instance of the game logic

    // Constructor to set up the GUI
    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(3, 3));

        buttons = new JButton[9]; // Create buttons array for the 3x3 grid
        map = new Map(); // Initialize the game logic

        // Create buttons for each cell of the grid
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 60));
            buttons[i].addActionListener(this); // Add action listener to handle button clicks
            add(buttons[i]); // Add button to the JFrame
        }

        setVisible(true); // Make the JFrame visible
    }

    // Action listener method to handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource(); // Get the button that was clicked
        int position = -1;
        // Determine the position of the clicked button
        for (int i = 0; i < 9; i++) {
            if (buttons[i] == clickedButton) {
                position = i;
                break;
            }
        }
        // If the clicked position is valid and empty, place the mark and update the game state
        if (position != -1 && map.isEmpty(position)) {
            char currentPlayer = map.getCurrentPlayer(); // Get the current player
            clickedButton.setText(String.valueOf(currentPlayer)); // Set the button text to X or O
            map.placeMark(position); // Update the game logic with the player's move
            // Check for win or draw
            if (map.checkForWin()) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                resetBoard(); // Reset the board for a new game
            } else if (map.checkForDraw()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetBoard(); // Reset the board for a new game
            } else {
                map.switchPlayer(); // Switch to the next player
            }
        }
    }

    // Method to reset the game board
    private void resetBoard() {
        for (JButton button : buttons) {
            button.setText(""); // Clear the text of all buttons
        }
        map.reset(); // Reset the game logic
    }

    // Main method to create an instance of the GUI
    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}

// Class representing the game logic
class Map {
    private char[][] board; // 2D array to represent the game board
    private char currentPlayer; // Current player (X or O)

    // Constructor to initialize the game board and current player
    public Map() {
        board = new char[3][3]; // Create a 3x3 game board
        currentPlayer = 'X'; // Start with player X
        // Initialize the game board with empty cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Method to check if a cell at a given position is empty
    public boolean isEmpty(int position) {
        int row = position / 3;
        int col = position % 3;
        return board[row][col] == ' ';
    }

    // Method to get the current player
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    // Method to place a mark (X or O) at a given position
    public void placeMark(int position) {
        int row = position / 3;
        int col = position % 3;
        board[row][col] = currentPlayer;
    }

    // Method to check if the current player has won the game
    public boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true; // Horizontal win
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true; // Vertical win
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true; // Diagonal win (top-left to bottom-right)
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true; // Diagonal win (top-right to bottom-left)
        }
        return false;
    }

    // Method to check if the game is a draw
    public boolean checkForDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false; // Not a draw, empty cell found
                }
            }
        }
        return true; // Draw, no empty cells left
    }

    // Method to switch to the next player
    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    // Method to reset the game board and current player
    public void reset() {
        currentPlayer = 'X'; // Reset to player X
        // Clear the game board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }
}
