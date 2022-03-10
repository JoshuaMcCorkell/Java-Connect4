package connect4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.ImageIcon;

public class Connect4GUI implements ActionListener{
    static final int SPACE_SIZE = 50;
    static final String RED_DISK = "connect4/resources/Red.png";
    static final String YELLOW_DISK = "connect4/resources/Yellow.png";
    static final String BLANK_DISK = "connect4/resources/Blank.png";

    private int count = 0;
    private Connect4 game;

    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JLabel[][] board = null;

    public Connect4GUI(Connect4 displayGame) {
        game = displayGame;
        init();
    }

    private void init() {
        frame = new JFrame();
        frame.getContentPane().removeAll();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Java Connect 4");
        board = new JLabel[Connect4.COLUMNS][Connect4.ROWS];
        for (int i = 0; i < Connect4.COLUMNS; i++) {
            for (int j = 0; j < Connect4.ROWS; j++) {
                board[i][j] = new JLabel();
                board[i][j].setBounds(i * SPACE_SIZE, (Connect4.ROWS - 1) * SPACE_SIZE - j * SPACE_SIZE, SPACE_SIZE, SPACE_SIZE);
                frame.getContentPane().add(board[i][j]);
            }
        }
        updateBoard();
        frame.setVisible(true);
        frame.setSize(800, SPACE_SIZE * 9);
    }
    
    public void updateBoard() {
        for (int i = 0; i < Connect4.COLUMNS; i++) {
            for (int j = 0; j < Connect4.ROWS; j++) {
                switch (game.current.get(i, j)) {
                    case 0: 
                        board[i][j].setIcon(new ImageIcon(BLANK_DISK));
                        break;
                    case 1:
                        board[i][j].setIcon(new ImageIcon(RED_DISK));
                        break;
                    case 2:
                        board[i][j].setIcon(new ImageIcon(YELLOW_DISK));
                        break;
                    default:
                        board[i][j].setIcon(new ImageIcon(YELLOW_DISK));
                        break;
                }
                board[i][j].setBounds(i * SPACE_SIZE, (Connect4.ROWS - 1) * SPACE_SIZE - j * SPACE_SIZE, SPACE_SIZE, SPACE_SIZE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Number of clicks: " + count);
    }
}