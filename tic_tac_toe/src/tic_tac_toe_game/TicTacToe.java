package tic_tac_toe_game;

import java.util.Scanner;

/**
 * The TicTacToe class mimics taking turns during a Tic Tac Toe game by
 * creating a 2D array and printing it to standard output.
 */
public class TicTacToe {
    // For the game board
    private int gameWinner;
    private int row, col;
    private final int size;             // Input from user for array size
    private final int[][] boardArray;   // Game board

    // The game board status
    private int currentState;           // the current state of the game
    private int currentPlayer;          // the current player
    private int currentRow, currentCol; // currently selected row and column

    // Constants to represent the players and locations
    private final int EMPTY = 0;
    private final int PLAYER_X = 2;
    private final int PLAYER_O = 3;

    // Constants to represent the various states of the game
    private final int PLAYING = 0;
    private final int TIE = 1;
    private final int X_WIN = 2;
    private final int O_WIN = 3;

    /**
     * Constructor
     * @param number The size of the array (for rows and columns)
     */
    public TicTacToe(int number) {
        size = number;
        boardArray = new int[number][number];

        // Start the game
        initGame();
        play();
    }

    /**
     * The initGame method initializes the board game with empty cells,
     * sets the status of the game as "playing" and the first player as "X."
     */
    public void initGame(){
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                // Initialize all the cells to empty
                boardArray[row][col] = EMPTY;
            }
        }
        currentState = PLAYING;
        currentPlayer = PLAYER_X;  // X plays first
        printBoard();
    }

    /**
     * The printBoard method displays the 2D array to standard output.
     */
   public void printBoard() {
       String vertLine = " | ";
       String horLines = "------";

       System.out.println("\n\t\t  Tic-Tac-Toe\n\n");

       // Display the columns header
       for(int i = 0; i < size; i++) {
           System.out.printf("%5s.", i);
       }
       System.out.println();
       System.out.println("\t\t");

       for(int row = 0; row < boardArray.length; row++){

           // Display the row header
           System.out.print(row + ".\t");

           for(int col = 0; col <boardArray[row].length; col++){
               printCell(boardArray[row][col]);        // Display each cell
               if (col != size - 1)
                   System.out.printf("%s", vertLine);  // Display vertical line
           }
           System.out.println();

           // Display horizontal lines
           if (row != size -1)
               System.out.println("\t" + horLines.repeat(size));
       }
   }

    /**
     * The printCell method adds the current player's marker to the game board.
     * @param marker The selected cell (row, column) number.
     */
    public void printCell(int marker) {
        String empty = "   ";
        String x = " X ";
        String o = " O ";

        switch (marker) {
            case EMPTY:
                System.out.printf("%2s", empty);
                break;
            case PLAYER_X:
                System.out.printf("%2s", x);
                break;
            case PLAYER_O:
                System.out.printf("%2s", o);
                break;
        }
    }

    /**
     * The play method mimics a player taking a turn.
     */
    public void play(){
        // Create Scanner
        Scanner keyboard = new Scanner(System.in);

        do {
            // Current player selects a valid cell for the marker
            playerMove(currentPlayer, keyboard);

            // Update the status of the game
            currentState = updateGameStatus(currentPlayer);
            if (currentState == 2) {
                printBoard();
                System.out.println("\n'X' is the winner!");
                currentState = X_WIN;
            } else if (currentState == 3) {
                printBoard();
                System.out.println("\n'O' is the winner!");
                currentState = O_WIN;
            } else if(currentState == 1) {
                printBoard();
                System.out.println("\nIt's a tie!");
                currentState = TIE;
            } else {
                currentState = PLAYING;
                printBoard();
            }

            // Switch player
            if(currentPlayer == PLAYER_X)
                currentPlayer = PLAYER_O;
            else
                currentPlayer= PLAYER_X;
        } while (currentState == PLAYING);
    }

    /**
     * The playerMove method determines if the cell selection for the
     * player's marker is valid and empty.
     * @param playerTurn The current player
     * @param keyboard Scanner For user input
     */
    public void playerMove(int playerTurn, Scanner keyboard) {
        boolean validInput = false;  // for input validation

        do {
            if(playerTurn == PLAYER_X)
                System.out.print("\nPlayer 'X' select your cell by " +
                        "entering the row\nnumber followed by a space " +
                        "followed by the column\nnumber (ex: 0 2) here: ");
            else
                System.out.print("\nPlayer 'O' select your cell by " +
                        "entering the row\nnumber followed by a space " +
                        "followed by the column\nnumber (ex: 0 2) here: ");
            row = keyboard.nextInt();
            col = keyboard.nextInt();

            // Determine if cell is empty
            if (row >= 0 && row < size && col >= 0 && col < size &&
                    boardArray[row][col] == EMPTY) {
                currentRow = row;
                currentCol = col;

                // Designate cell to current player
                boardArray[currentRow][currentCol] = playerTurn;
                validInput = true;
            } else {
                System.out.println("The cell " + row + col + " isn't valid " +
                        "or has been used.");
            }
        } while (!validInput); // Repeat until cell selection is valid
    }

    /**
     * The updateGameStatus method returns the updated game status
     * @param playerTurn The current player
     * @return int To update the game's status
     */
    public int updateGameStatus(int playerTurn) {

        if(checkForWinner(playerTurn)) {
            if (playerTurn == PLAYER_X)
                return X_WIN;
            else
                return O_WIN;
        } else if(isTIE())
            return TIE;
        else
            return PLAYING;  // else still playing */
    }

    /**
     * The checkForWinner method determines if there is a winner.
     * @param playerTurn The current player
     * @return boolean true if there is a winner, false if there isn't
     */
    public boolean checkForWinner(int playerTurn) {
        int diagonal1Count = 0, diagonal2Count = 0, colCount = 0;

        for(int row = 0; row < size; row++) {

            // Check left-to-right diagonal
            if (boardArray[row][row] == playerTurn) {
                diagonal1Count++;
                if (diagonal1Count == size)
                    return true;

            } else if (boardArray[row][size - row - 1] == playerTurn) {

                //Check right-to-left diagonal
                diagonal2Count++;
                if (diagonal2Count == size)
                    return true;
            }
        }

        for (int i = 0; i < size; i++) {
            int rowSum = 0, colSum = 0;
            for (int j = 0; j < size; j++) {

                // Check the rows
                if (boardArray[i][j] == playerTurn) {
                    rowSum = rowSum + boardArray[i][j];
                }

                //Check the columns
                if (boardArray[j][i] == playerTurn) {
                    colSum = colSum + boardArray[j][i];
                }

                if (playerTurn == PLAYER_X) {
                    if (rowSum == (size * 2) || colSum == (size * 2))
                        return true;
                }
                if (rowSum == (size * 3) || colSum == (size * 3))
                    return true;
            }
        }
        return false;
    }

    /**
     * The isTIE method determines if there is a tie, no winner.
     * @return boolean true if there is a tie, false if there are empty cells
     */
    public boolean isTIE() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (boardArray[row][col] == EMPTY)
                    return false;   // Empty cell = no tie
            }
        }
        return true;
    }
}