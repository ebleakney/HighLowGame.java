import java.util.Random;
import java.util.Scanner;
import java.lang.Math;

/** HighLowGame.java
 * ===========================================================
 * @Author: Ethan Bleakney
 * Section: T1
 * Project: PEX0
 * Purpose: Learn Java
 * @Documentation: I used a variety of internet resources which I don't remember, but
 * they were all to learn java syntax which I kinda know now.
 * =========================================================== */

//Initialize class
public class HighLowGame {
    //Initialize global variables
    private static int MAX_NUMBER = 10;
    private static int MAX_NUMBER_OF_GAMES = 100;
    static Scanner scannerName = new Scanner(System.in);
    //Seed the program for randomization
    static Random rand = new Random(System.currentTimeMillis());
    static int guessesPerGame[] = new int[MAX_NUMBER_OF_GAMES];

    /**
     * @brief main method for pex0
     * @param args (unused)
     */
    // Initialize main method
    public static void main(String[] args) {
        int totalGuesses = 0;
        int totalGames = 0;
        // Welcome player to the game
        System.out.println("Welcome to the high low game!\n");
        // Get the name of the player
        String playerName = getPlayerName(scannerName);

        // Prompt user to play the high low game
        // Record number of guesses per game in static array
        // Record total games and total guesses
        do {
            int numGuesses = playHighLow(playerName, scannerName);
            guessesPerGame[totalGames] = numGuesses;
            totalGames++;
            totalGuesses += numGuesses;
            // Allow user to keep playing until negative input is received.
        } while (askToPlayAgain(scannerName));

        // End the game with messages and statistics for the player
        concludeGame(playerName, totalGuesses, totalGames);
    }

    /**
     * @brief gets the name of the player, if empty creates a name for them
     * @param scannerName global scanner
     * @pre N/A
     * @post N/A
     * @return name of the player as a string
     */
    private static java.lang.String getPlayerName(java.util.Scanner scannerName) {
        // prompt for name and scan next line for input
        System.out.println("Enter Player Name: ");
        String playerName = scannerName.nextLine();
        // If player name is empty assign a name.
        if (playerName.isEmpty()) {
            playerName = "Stupid";
            System.out.println("Failed to Enter a Name, you are now: " + playerName);
        }
        return playerName;
    }

    /**
     * @brief asks user if they would like to play again
     * @param scannerName global scanner
     * @pre getPlayerName() is called
     * @post game restarts or concludeGame() is called
     * @return boolean value, 1 = continue
     */
    private static boolean askToPlayAgain(java.util.Scanner scannerName) {
        // Prompt to play again, scan for input
        System.out.println("Would you like to play again? Enter yes or no: ");
        String playAgain = scannerName.next();
        
        while (!playAgain.equalsIgnoreCase("yes") &&
                !playAgain.equalsIgnoreCase("y") &&
                !playAgain.equalsIgnoreCase("no") &&
                !playAgain.equalsIgnoreCase("n")) {
            System.out.println("Invalid input, enter yes or no: ");
            playAgain = scannerName.nextLine();
        }
        if (playAgain.equalsIgnoreCase("yes") || playAgain.equalsIgnoreCase("y")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @brief Runs the actual high low game. Takes and reports guesses, with attitude.
     * @param playerName the name of the player
     * @param scannerName global scanner
     * @return the number of guesses
     */
    private static int	playHighLow(String playerName, java.util.Scanner scannerName) {
        //generate a secret number between 0 and MAX_NUMBER (inclusive)
        int secretNumber = rand.nextInt(MAX_NUMBER) + 1;
        int count = 0;
        int guess = 0;

        System.out.println("Okay, " + playerName + ", lets see how good you are.");
        System.out.println("The secret number is between 0 and " + MAX_NUMBER + " inclusive.");
        System.out.println("Enter your first guess: ");
        count++;
        do {
            if (scannerName.hasNextInt()) {
                    guess = scannerName.nextInt();
                } else {
                    System.out.println("Just enter an integer smart ass.");
                    scannerName.nextLine();
            }
            if ((guess > secretNumber) && (guess <= MAX_NUMBER)) {
                System.out.println("You fool! Your guess was too high.");
                System.out.print("Guess again: ");
                scannerName.nextLine();
                count++;
            } else if ((guess < secretNumber) && (guess >= 0)) {
                System.out.println("You fool! Your guess was too low.");
                System.out.print("Guess again: ");
                scannerName.nextLine();
                count++;
            } else if (guess > MAX_NUMBER) {
                System.out.printf("The number has to be less than %d you dummy.\n", MAX_NUMBER);
                scannerName.nextLine();
            } else if (guess < 0) {
                System.out.println("Only POSITIVE numbers.");
                scannerName.nextLine();
            }
        } while (guess != secretNumber);
        System.out.println("Correct! The secret number was " + guess + ".");
        System.out.print("It took you " + count + " guesses to find the secret number.\n");
        return count;
    }

    /**
     * @brief ends the game with attitude and statistics for the session
     * @param playerName the players name
     * @param totalGuesses total number of guesses for all games played
     * @param totalGames total number of games played
     */
    private static void	concludeGame(String playerName, int totalGuesses, int totalGames) {
        double optimalGuesser = Math.ceil((Math.log(MAX_NUMBER) / Math.log(2)));
        float avgGuesses = (float)totalGuesses / (float)totalGames;

        int twoAboveOptimalPlus = 0;
        int optimalOr1Above = 0;
        int optimalMinus2 = 0;
        int optimalMinus1or2 = 0;

        System.out.println("Thank you for playing " + playerName + "! Here are your results:");
        System.out.printf("Average number of guesses taken across all games: %.1f\n", avgGuesses);
        System.out.printf("Number of guesses needed for an optimal play of a game: %d\n", (int)optimalGuesser);

        for (int i = 0; i < totalGames; i++) {
            if (guessesPerGame[i] >= ((int)optimalGuesser + 2)) {
                twoAboveOptimalPlus++;
            } else if (guessesPerGame[i] >= (int)optimalGuesser && guessesPerGame[i] <= ((int)optimalGuesser + 1)) {
                optimalOr1Above++;
            } else if (guessesPerGame[i] == ((int)optimalGuesser - 2)) {
                optimalMinus2++;
            } else if (guessesPerGame[i] < (int)optimalGuesser && guessesPerGame[i] >= ((int)optimalGuesser - 2)) {
                optimalMinus1or2++;
            }
        }

        System.out.println("Below are the NUMBERS OF GAMES in your session in which you guessed: ");
        System.out.printf("Less than the optimal number of guesses needed minus two (2): %d\n", optimalMinus2);
        System.out.printf("Less than the optimal number of guesses by one or two guesses: %d\n", optimalMinus1or2);
        System.out.printf("At the optimal number of guesses or one above: %d\n", optimalOr1Above);
        System.out.printf("Two or more above the optimal number of guesses: %d\n", twoAboveOptimalPlus);
    }
}