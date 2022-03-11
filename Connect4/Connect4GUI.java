package connect4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Connect4GUI extends MouseAdapter{
    //Constants
    static final int SPACE_SIZE = 50;
    static final String[] DISK_IMAGES = {"connect4/resources/Blank.png", "connect4/resources/Red.png", "connect4/resources/Yellow.png"};
    static final String[] PLAYER = {"Error", "Red", "Yellow"};

    //The 'resident' game
    private Connect4 game;

    //Components
    private JFrame frame;
    private JLabel[][] board = null;
    private JLabel winDisplay;
    private JLabel turnDisplay;
    private JMenuBar menuBar;

    /**
     * Creates a new Connect4GUI with a new Connect4 game.
     */
    public Connect4GUI() {
        game = new Connect4();
        init();
    }

    /**
     * Creates a new Connect4GUI with the Connect4 game given.
     */
    public Connect4GUI(Connect4 displayGame) {
        game = displayGame;
        init();
    }

    /**
     * Initializes the GUI.
     */
    private void init() {
        //Initialize the frame
        frame = new JFrame();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Java Connect 4");

        //Initialize the Board as an array of ImageIcons in JLabels
        board = new JLabel[Connect4.COLUMNS][Connect4.ROWS];
        for (int i = 0; i < Connect4.COLUMNS; i++) {
            for (int j = 0; j < Connect4.ROWS; j++) {
                board[i][j] = new JLabel();
                board[i][j].setBounds(i * SPACE_SIZE, (Connect4.ROWS - 1) * SPACE_SIZE - j * SPACE_SIZE, SPACE_SIZE, SPACE_SIZE);
                frame.getContentPane().add(board[i][j]);
            }
        }

        //Initialize the fields
        turnDisplay = new JLabel();
        turnDisplay.setBounds(20, Connect4.ROWS * SPACE_SIZE + 20, 200, 30);
        frame.getContentPane().add(turnDisplay);
        winDisplay = new JLabel();
        winDisplay.setBounds(20, Connect4.ROWS * SPACE_SIZE + 15, 200, 50);
        frame.getContentPane().add(winDisplay);

        initMenu(); //Initialize Menu
    }

    public void initMenu() {
        //Initialize the menu
        menuBar = new JMenuBar();

        JMenu options = new JMenu("Options");
        //Exit Button
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ExitApp(frame));
        options.add(exitItem);
        //New Game Button
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(new NewGameButton(frame));
        options.add(newGameItem);

        menuBar.add(options);

        JMenu help = new JMenu("Help");
        //How to Play Popup
        JMenuItem helpItem = new JMenuItem("How to Play");
        helpItem.addActionListener(new HelpMenu(frame));
        helpItem.setSize(300, 200);
        help.add(helpItem);

        menuBar.add(help);

        frame.setJMenuBar(menuBar);
    }

    public void start() {
        //Update the board to the current position, update the Fields, add the MouseListener and show the frame.
        updateGUI();
        frame.setVisible(true);
        frame.setSize(SPACE_SIZE * (Connect4.COLUMNS + 5), SPACE_SIZE * (Connect4.ROWS + 4));
        frame.addMouseListener(this);
    }

    public void newGame() {
        game = new Connect4();
        updateGUI();
    }

    public void updateGUI() {
        updateBoard();
        updateFields();
    }
    
    /**
     * Updates the Board to reflect the current game position.
     */
    public void updateBoard() {
        for (int i = 0; i < Connect4.COLUMNS; i++) {
            for (int j = 0; j < Connect4.ROWS; j++) {
                board[i][j].setIcon(new ImageIcon(DISK_IMAGES[game.current.get(i, j)]));
                board[i][j].setBounds(i * SPACE_SIZE, (Connect4.ROWS - 1) * SPACE_SIZE - j * SPACE_SIZE, SPACE_SIZE, SPACE_SIZE);
            }
        }
    }
    
    /**
     * Updates the turn and winner fields. 
     */
    public void updateFields() {
        turnDisplay.setText("<html><h2>" + PLAYER[game.turn] + "'s Turn");
        winDisplay.setText("");
        if (game.winner != 0) {
            winDisplay.setText("<html><h1>" + PLAYER[game.winner] + " Wins!");
            turnDisplay.setText("");
        }
    }
    
    //A mouse press event. Works out which column was clicked, and plays there, using the safePlay method (which ignores errors.).
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (game.winner == 0) {
            int clickColumn = mouseEvent.getX() / SPACE_SIZE;
            game.safePlay(clickColumn);
            updateBoard();
            updateFields();
        }
    }

    /**
     * Context Menu for quitting the app
     */
    private class ExitApp implements ActionListener {
        private JFrame frame;

        ExitApp(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int playAgain = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "", JOptionPane.YES_NO_OPTION);

            if (playAgain == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    /**
     * Context Menu for bringing up the help menu
     */
    private class HelpMenu implements ActionListener {
        private JFrame frame;

        HelpMenu(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = 
                """
                Take turns dropping discs into the columns of the board.
                Objective is get of your colored discs in a row (up/down, left/right or diagonally).
                First player to get 4 in a row wins.
                """;

            JOptionPane.showMessageDialog(frame, msg, "How to play", JOptionPane.OK_OPTION, new ImageIcon(DISK_IMAGES[1]));
        }
    }

    /**
     * Context Menu for creating a new game.
     */
    private class NewGameButton implements ActionListener {
        private JFrame frame;

        NewGameButton(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int confirmation = 
            JOptionPane.showConfirmDialog(
                frame, 
                "Are you sure? The contents of the current game will be discarded!", 
                "Confirm", 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmation == 0) {
                newGame();
            }
        }
    }
}