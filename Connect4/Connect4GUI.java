package connect4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Connect4GUI extends MouseAdapter{
    //Constants
    static final int SPACE_SIZE = 50;
    static final ImageIcon[] DISK_ICONS = {
        new ImageIcon("connect4/resources/Blank.png"), 
        new ImageIcon("connect4/resources/Red.png"), 
        new ImageIcon("connect4/resources/Yellow.png")
    };
    static final String[] PLAYER = {"Error", "Red", "Yellow"};
    static final String[] GAME_MODE_STRINGS = {"Player v Player", "Easy Mode", "Hard Mode"};

    //Other Variables
    public Connect4 game;
    
    public enum GameMode {
        PLAYER_V_PLAYER,
        PLAYER_V_RANDOM,
        PLAYER_V_COMPUTER
    }
    
    public GameMode currentMode;
    private boolean playerTurn;
    public int playerColour;

    //Components
    private JFrame frame;
    private JLabel[][] board = null;
    private JLabel winDisplay;
    private JLabel turnDisplay;
    private JLabel modeDisplay;
    private JMenuBar menuBar;
    private JLabel playerColourDisplay;

    /**
     * Creates a new Connect4GUI with a new Connect4 game.
     */
    public Connect4GUI() {
        game = new Connect4();
        init();
    }

    /**
     * Creates a new Connect4GUI with the Connect4 game given.
     * @param displayGame  Open the GUI with this game loaded
     * @param playerColourIndex  The colour that the player is playing in the position.
     */
    public Connect4GUI(Connect4 displayGame, int playerColourIndex) {
        game = displayGame;
        playerColour = playerColourIndex;
        if (playerColour == game.turn) {
            playerTurn = true;
        } else {
            playerTurn = false;
        }
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
        frame.setTitle("Java Connect " + Connect4.TOWIN);

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
        modeDisplay = new JLabel();
        modeDisplay.setBounds(Connect4.COLUMNS * SPACE_SIZE + 20, 0, 200, 100);
        frame.getContentPane().add(modeDisplay);
        playerColourDisplay = new JLabel();
        playerColourDisplay.setBounds(Connect4.COLUMNS * SPACE_SIZE + 20, 75, 200, 100);
        frame.getContentPane().add(playerColourDisplay);

        initMenu(); //Initialize Menu
    }

    private void initMenu() {
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
        currentMode = GameMode.PLAYER_V_PLAYER;
        playerTurn = true;
        updateGUI();
        frame.setVisible(true);
        frame.setSize(SPACE_SIZE * (Connect4.COLUMNS + 5), SPACE_SIZE * (Connect4.ROWS + 4));
        frame.addMouseListener(this);
    }

    public void newGame() {
        game = new Connect4();
        playerTurn = true;
        playerColour = 1;
        updateGUI();
    }

    public void newGame(boolean playerStarts) {
        game = new Connect4();
        playerTurn = playerStarts;
        playerColour = playerStarts? 1 : 2;
        updateGUI();
        if (!playerStarts) {
            playAuto();
            updateGUI();
        }
    }

    public void updateGUI() {
        updateBoard();
        updateFields();
        if (game.winner != 0) {
            String[] options = {"OK", "New Game", "Exit"};

            int input = JOptionPane.showOptionDialog(
                frame, 
                Connect4.TOWIN + " in a row! " + PLAYER[game.winner] + " has won!", 
                "Game Over", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE,
                DISK_ICONS[game.winner], 
                options,
                options[1]
            );

            if (input == 1) {
                GameMode newMode = askGameMode();
                if (newMode != null) {
                    currentMode = newMode;
                    newGame();
                }
            } else if (input == 2) {
                System.exit(0);
            }
        }
    }
    
    /**
     * Updates the Board to reflect the current game position.
     */
    public void updateBoard() {
        for (int i = 0; i < Connect4.COLUMNS; i++) {
            for (int j = 0; j < Connect4.ROWS; j++) {
                board[i][j].setIcon(DISK_ICONS[game.current.get(i, j)]);
                board[i][j].setBounds(i * SPACE_SIZE, (Connect4.ROWS - 1) * SPACE_SIZE - j * SPACE_SIZE, SPACE_SIZE, SPACE_SIZE);
            }
        }
    }
    
    /**
     * Updates the turn and winner fields. 
     */
    public void updateFields() {
        //Set the mode display
        String modeDisplayText;
        switch (currentMode) {
            case PLAYER_V_PLAYER: modeDisplayText = GAME_MODE_STRINGS[0]; break;
            case PLAYER_V_RANDOM: modeDisplayText = GAME_MODE_STRINGS[1]; break;
            case PLAYER_V_COMPUTER: modeDisplayText = GAME_MODE_STRINGS[2]; break;
            default: modeDisplayText = "Error in Displaying Mode";
        }
        modeDisplay.setText("<html><h1>Current Mode: <br>" + modeDisplayText);

        //Set the turn/winner display
        if (game.winner == 0) {
            turnDisplay.setText("<html><h2>" + PLAYER[game.turn] + "'s Turn");
            winDisplay.setText("");
        } else {
            winDisplay.setText("<html><h1>" + PLAYER[game.winner] + " Wins!");
            turnDisplay.setText("");
        }

        if (currentMode != GameMode.PLAYER_V_PLAYER) {
            playerColourDisplay.setText("You are playing: " + PLAYER[playerColour]);
        } else {
            playerColourDisplay.setText("");
        }
        
    }
    
    //A mouse press event. Works out which column was clicked, and plays there, using the safePlay method (which ignores errors.).
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        int clickColumn = mouseEvent.getX() / SPACE_SIZE;
        
        if (game.winner == 0) {
            if (currentMode == GameMode.PLAYER_V_PLAYER) {
                // If in player v player mode, always play when the user clicks.
                game.safePlay(clickColumn);
            } else if ((currentMode == GameMode.PLAYER_V_COMPUTER || currentMode == GameMode.PLAYER_V_RANDOM) && playerTurn) {
                // If in player v computer or random, play only if it is the users turn. 
                boolean played = game.safePlay(clickColumn);
                updateGUI();
                if (played) {
                    playerTurn = false;
                    if (game.winner == 0) {
                        playAuto();
                    }
                }
            }
            updateGUI();
        }
    }

    /**
     * Plays the automatic move, which will either be random or computed, based on the currentMode.
     */
    public void playAuto() {
        try {
            if (currentMode == GameMode.PLAYER_V_RANDOM) {
                game.playRandom();
            } else {
                game.playComputer();
            }
        } catch (IllegalMoveException e) {
            System.out.println("The Computer just played an Illegal move!");
            e.printStackTrace();
        }
        playerTurn = true;
    }

    public GameMode askGameMode() {
        String input = (String) JOptionPane.showInputDialog(
            frame, 
            "What game mode do you want to play?", 
            "Game Mode Selection", 
            JOptionPane.QUESTION_MESSAGE, 
            DISK_ICONS[2], GAME_MODE_STRINGS, 
            GAME_MODE_STRINGS[0]
        );
        if (input == null) {
            return null;
        }
        if (input.equals(GAME_MODE_STRINGS[0])) {
            return GameMode.PLAYER_V_PLAYER;
        }
        if (input.equals(GAME_MODE_STRINGS[1])) {
            return GameMode.PLAYER_V_RANDOM;
        }
        if (input.equals(GAME_MODE_STRINGS[2])) {
            return GameMode.PLAYER_V_COMPUTER;
        }
        //If none of the above are selected, just default to player v player.
        return GameMode.PLAYER_V_PLAYER;
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

            JOptionPane.showMessageDialog(frame, msg, "How to play", JOptionPane.OK_OPTION, DISK_ICONS[1]);
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
            int confirmation;
            if (game.winner == 0) {
                confirmation = 
                JOptionPane.showConfirmDialog(
                    frame, 
                    "Are you sure? The contents of the current game will be discarded!", 
                    "Confirm", 
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
                confirmation = JOptionPane.OK_OPTION;
            }
            if (confirmation == JOptionPane.OK_OPTION) {
                GameMode newMode = askGameMode();
                if (newMode != null) {
                    currentMode = newMode;
                    newGame();
                }
            }
        }
    }
}