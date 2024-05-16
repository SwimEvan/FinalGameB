import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.Image;
public class BattleshipGame extends JFrame {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int CELL_SIZE = 50;
    private static final int SHIP_SIZE = 1;

    private JButton[][] buttons;
    private int[][] grid;
    private int shipsRemaining;

    ImageIcon boomy = new ImageIcon("boom.png");

    ImageIcon womp = new ImageIcon("wompwomp.png");

    public BattleshipGame() {
        setTitle("Battleship Game");
        setSize(ROWS * CELL_SIZE, COLS * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        grid = new int[ROWS][COLS];
        buttons = new JButton[ROWS][COLS];
        shipsRemaining = 10; // Change this value to increase the number of ships

        boomy.setImage(boomy.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT));
        womp.setImage(womp.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT));
        initializeGrid();
        setupUI();
        placeShips();
    }

public ImageIcon getBoomy(){
        return boomy;
}
public ImageIcon getWomp(){
        return womp;
}

    private void initializeGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = 0;
            }
        }
    }

    private void setupUI() {
        JPanel panel = new JPanel(new GridLayout(ROWS, COLS));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                button.addActionListener(new ButtonClickListener(i, j));
                panel.add(button);
                buttons[i][j] = button;
            }
        }

        add(panel);
    }

    private void placeShips() {

            Random rand = new Random();

            for (int i = 0; i < shipsRemaining; i++) {
                int row = rand.nextInt(ROWS);
                int col = rand.nextInt(COLS);

                if (isValidShipPlacement(row, col)) {
                    for (int k = 0; k < SHIP_SIZE; k++) {
                        grid[row][col + k] = 1;
                    }
                } else {
                    i--; // Retry placing the ship
                }
            }

    }
    private boolean isValidShipPlacement(int row, int col) {
        if (col + SHIP_SIZE > COLS) {
            return false;
        }

        for (int i = 0; i < SHIP_SIZE; i++) {
            if (grid[row][col + i] != 0) {
                return false;
            }
        }
        return true;
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (grid[row][col] == 1) {
                buttons[row][col].setIcon(getBoomy());
                JOptionPane.showMessageDialog(null, "Hit!");
                shipsRemaining--;
                if (shipsRemaining == 0) {

                    JOptionPane.showMessageDialog(null, "You sunk all the battleships!");
                    System.exit(0);
                }
            } else {
                buttons[row][col].setIcon(getWomp());
                JOptionPane.showMessageDialog(null, "Miss!");
            }
            buttons[row][col].setEnabled(false); // Disable the button after clicking
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BattleshipGame().setVisible(true);
        });
    }
}
