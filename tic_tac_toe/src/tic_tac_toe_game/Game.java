package tic_tac_toe_game;

/**
 * Karen Axon
 * This is free and unencumbered software released into the public domain.
 */

import java.util.Scanner;

/**
 * This program mimics a Tic Tac Toe two player game. The user selects the
 * size of the game board and the players take turns placing a marker (X and
 * O) in empty cells of the game board. The first player to fill a row, column
 * or diagonal with his/her markers wins. The user can play as many times as
 * wished.
 *
 * @author karen axon
 * @version 2.0
 */

/**
 * The Game class creates a new Tic Tac Toe game instance.
 */
public class Game {

    /**
     * The createGame method creates a new TicTacToe game instance.
     * @param size The size of the array rows and columns
     */
    public static void createGame(int size) {
        TicTacToe game = new TicTacToe(size);
    }

    /**
     * The validateInput method verifies that the user input for the game
     * board size is valid.
     * @param number The integer user input
     * @param array  The valid sizes for the array rows and columns
     * @return An integer
     */
    public static int validateInput(int number, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == number) {
                return i;
            }
        }
        return -1;
    }

    /**
     * The getSize method takes in user input
     * @param keyboard Scanner for user input
     * @return An integer
     */
    public static int getSize(Scanner keyboard) {
        int size;

        System.out.print("\nSelect the size of the board game\nEnter 3 " +
                "for a  9 cell board game\nEnter 5 for a 25 cell " +
                "board game\nEnter 7 for a 49 cell board game\nEnter your " +
                "selection here: ");
        size = keyboard.nextInt();
        // Consume next line
        keyboard.nextLine();

        return size;
    }

    /**
     * The goodbye method displays a message to standard output.
     */
    public static void goodbye () {
        System.out.println("\nThank you for playing Tic-Tac-Toe. Goodbye!");
    }

    /**
     * The welcome method displays a message to standard output.
     */
    public static void welcome () {
        System.out.println("\n*** Welcome to Silly Little Games Tic-Tac_Toe" +
                " ***\nThis is a two player game. You take turns placing\nan " +
                "X or an O in an empty space. The first player to\nfill a " +
                "row, column or diagonal wins.");
    }

    public static void main (String[]args){
        char repeat = 'y';
        int input;                  // User input for the 2D array size
        int[] sizesArray = {3, 5, 7};   // The valid sizes of the board game
        int verifiedInput;              // To validate user input

        // Create Scanner
        Scanner keyboard = new Scanner(System.in);

        // Display welcome
        welcome();

        do{
            // Get input from the user for the size of the game board
            input = getSize(keyboard);

            // Validate the input
            verifiedInput = validateInput(input, sizesArray);

            // If invalid number, get a new input
            while (verifiedInput == -1) {
                System.out.println(input + " isn't a valid number.");
                input = getSize(keyboard);
                verifiedInput = validateInput(input, sizesArray);
            }

            // If the input is valid, create the game
            if(verifiedInput != -1)
                createGame(input);

            // Determine if user wants to play again
            System.out.print("\n" +
                    "-----------------------------------------------\n" +
                    "\nWould you like to play again? " +
                    "Enter 'y' or 'n': ");
            repeat = keyboard.nextLine().charAt(0);
        } while (repeat == 'y' || repeat == 'Y');

        goodbye();

        // Close Scanner
        keyboard.close();
    }
}
