/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmanwork;

/**
 *
 * @author mgillman
 */

    
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author maxeg
 */
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class HangmanWork {

    private WordList wl;              // List of words to grab a random word to guess from
    private char[] lettersFound;      // Array of characters that indicates correct letter guesses and their position in the word
    private char[] lettersMissed;     // Array of characters that indicates incorrect letter guesses
    private String currentWord;       // String to hold the current word we are trying to guess
    private int numFound;             // Number of current correct letter guesses
    private int numMissed;            // Number of current incorrect letter guesses (6 and you lose!)
    private boolean gameWon;          // Flag to indicate the game has been won or lost

    // Constructor
    public HangmanWork() throws FileNotFoundException {

        // Create an instance of our word list
        wl = new WordList();

        // Give some memory to our character arrays
        lettersFound = new char[25]; // Largest word in our wordlist file is 22 characters... so max of 25 characters is fine
        lettersMissed = new char[6]; //  Max of 6 wrong guesses (head, torso, right arm, left arm, right leg, left leg) 

        //get word, store to current word. iterate through lettersFound list. If no letter match, add " ". If match, add letter at i.       
    }

    // Initializes all of our attributes back to their initial state
    private void setupNewGame() {

        // Get a new word
        this.currentWord = this.wl.getRandomWord(); //SET THIS BACK ONCE FIXED
        this.currentWord = this.currentWord.toUpperCase();

        // Reset the control variables
        numMissed = 0;
        numFound = 0;
        gameWon = false;

        // Reset the letters found character array back to spaces
        // Loop through our characters arrays
        for (int i = 0; i < lettersFound.length; ++i) {
            this.lettersFound[i] = ' ';
        }

        // Reset the misses character array back to spaces
        for (int i = 0; i < lettersMissed.length; ++i) {
            this.lettersMissed[i] = ' ';
        }

    }

    // This method runs continuously until the user says they are done
    public void runGame() {

        // Create a variable that will hold the answer of "keep playing?"
        // This will also be our loop variable to keep making new games
        int keepPlaying = 0;
        Scanner scnr = new Scanner(System.in);

        // Create a variable to hold a winning or losing message
        String wonLostMessage = "";

// Keep running new games until the user says no more (via Messagebox)
        while (keepPlaying == 0) {
            this.setupNewGame();// Set up a new game
            System.out.print("\033[H\033[2J");
            while (gameWon == false) {
                  

                this.makeTurn(); //loop this

                this.printGameStatus();
                this.drawGallows();
                if (numMissed == 6) {
                    System.out.print("You lose! The word was:  " + currentWord);
                    break;
                }

            }
            // Run the game until it is over**************THIS IS THE MEAT OF THE GAME FUNCTION HERE, DEFINE OTHER FUNCTIONS FIRST
            // The game is over when someone makes 6 incorrect guesses 
            // OR
            // The person guesses all the letters (the game is won)
            // Print the game status
            // Make a turn

            // If we are finished the game, print the final status screen
            // Assign our message to show the user in our JOptionPane based
            // on if they won or lost
            // Tell the player they won or lost and ask if they want to play again using a JOptionPane
            // The user's response will be assigned to the keepPLaying variable
            System.out.println();
            System.out.print("Keep playing? Y = 0, N = 1 : "); // FIXME - MAKE THIS DIALOGUE BOX
            keepPlaying = scnr.nextInt();
        }

    }

    // This method will take the user input and figure out if it was in the word or not
    // It will also determine if the user has won or not
    public void makeTurn() {

        // Create a scanner object for us to get input from the console
        Scanner scnr = new Scanner(System.in);

        // We'll need some variables to hold some of our input stuff and to convert it
        String input;              // A string to hold the input from the scanner
        char guess;                // A character to convert the string to
        boolean found = false;     // A boolean variable to be a flag on whether or not a word was found
        int totalSpacesFilled = 0; // a value that determines how many spots in the WordList word have been guessed correctly.

        //print out initial blank word to give the player a clue
        for (int i = 0; i < currentWord.length(); ++i) {
            System.out.print("_ ");
        }
        System.out.println(); //go to next line
        // Ask the user for a guess
        System.out.println("Guess a letter: ");

        // Scan in the guess
        input = scnr.next();

        // Convert the input to all Upper Case
        input = input.toUpperCase();

        // Convert the string to a character
        // First make sure the string is not empty
        if (!(input.equals(""))) {
            // If it isn't, assign the first character as our guess
            guess = input.charAt(0);
        } // If it is empty, return (exit) from the function
        else {
            System.out.println();//go to next line
            System.out.println("WARNING: Did not receive input");// FIXME - MAKE THIS DIALOGUE BOX
            System.out.println();//go to next line
            return;
        }

        // Make sure we haven't guessed this before
        // Check in our correct guesses array
        for (int i = 0; i < this.lettersFound.length; ++i) {

            // If it's already been guessed let the user know and return (exit) the function
            if (guess == lettersFound[i]) {
                System.out.println();//go to next line
                System.out.println(guess + " has already been guessed!");// FIXME - MAKE THIS DIALOGUE BOX
                System.out.println();//go to next line
                return;// Exit the method with a return
            }
        }

        // Check in our wrong guesses array
        for (int i = 0; i < this.lettersMissed.length; ++i) {

            // If it's already been guessed let the user know and return (exit) the function
            if (guess == this.lettersMissed[i]) {
                System.out.println();
                System.out.println(guess + " has already been guessed!");// FIXME - MAKE THIS DIALOGUE BOX
                System.out.println();
                return;
            }
        }

        //get word, store to current word. iterate through lettersFound list. If no letter match, add " ". If match, add letter at i. 
        // Guess is new
        // See if the guess is in our word
        // Loop through our list to find where in our word they are and then 
        // replace the character with the guess
        // Letter found found
        for (int i = 0; i < this.currentWord.length(); ++i) {
            if (guess == this.currentWord.charAt(i)) {
                this.lettersFound[i] = guess; // Update our lettersFound array
                this.numFound += 1; // Increase our found count
                System.out.print(guess + " is in the word!"); // Indicate we found the letter this time through
                found = true;
                break;
            }
        }
        // find the total spaces filled in the root word. If this number is equal to length of word, player wins the game.
        for (int i = 0; i < currentWord.length(); ++i) {

            for (int j = 0; j < lettersFound.length; ++j) {

                if (currentWord.charAt(i) == lettersFound[j]) {
                    totalSpacesFilled += 1;

                }

            }

        }

        if (totalSpacesFilled == this.currentWord.length()) {  // Check to see if the user won
            this.gameWon = true; // If so, update our gameWon attribute
            System.out.println();
            System.out.print("YOU WIN!!!!");
            System.out.println("The word is: " + currentWord);
        }

        // If we didn't find the letter in this pass add it to our lettersMissed array
        if (found == false) {// Didn't find the letter 
            this.lettersMissed[numMissed] = guess; // Add it to our missed list array
            numMissed += 1; // Update our missed count

        }

    }

    //********need to make lettersFound list not contain spaces, just all letters found******** BECAUSE PRINTING IS DONE SEPERATELY NOT THROUGH THAT LIST
    private void printGameStatus() {
        boolean foundMatch;
        // Print Title

        System.out.println("****************************");
        System.out.println("\tHANGMAN\t");
        System.out.println("****************************");

        // Print current game board (Hangman)
        // Print the correct guesses
        System.out.println("\tCorrect Guesses:\t");

        for (int i = 0; i < currentWord.length(); ++i) {
            foundMatch = false;
            for (int j = 0; j < lettersFound.length; ++j) {
                if (currentWord.charAt(i) == lettersFound[j]) {
                    System.out.print(currentWord.charAt(i) + " ");
                    foundMatch = true;
                }
            }
            if (foundMatch == false) {// Print the underline (shows spaces of characters to be guessed
                System.out.print("_ ");
            }
        }

        System.out.println();
        System.out.println();
        System.out.println("\tIncorrect Guesses: " + numMissed + "/6");
        // Print the wrong guesses

        for (int i = 0; i < lettersMissed.length; ++i) {
            System.out.print(lettersMissed[i] + " ");
        }
        for (int i = 0; i < 6 - (lettersMissed.length); ++i) {
            System.out.print("_ ");
        }

        // Print the underline (6 total (maximum number of misses
        System.out.println();
    }

    // This draws the Hangman
    // Use the numMissed variable to change what is shown
    private void drawGallows() {

        if (numMissed == 6) {
            System.out.println(" _________     ");// Top bar
            System.out.println("|         |    ");// Rope
            System.out.println("|         0    ");// Head and arms
            System.out.println("|        /|\\  ");// Torso
            System.out.println("|        / \\  ");// Legs
            System.out.println("|              ");
            System.out.println("|______________");
        }

        if (numMissed == 5) {
            System.out.println(" _________     ");
            System.out.println("|         |    ");
            System.out.println("|         0    ");
            System.out.println("|        /|\\  ");
            System.out.println("|        /     ");
            System.out.println("|              ");
            System.out.println("|______________");
        }

        if (numMissed == 4) {
            System.out.println(" _________     ");
            System.out.println("|         |    ");
            System.out.println("|         0    ");
            System.out.println("|        /|\\  ");
            System.out.println("|              ");
            System.out.println("|              ");
            System.out.println("|______________");
        }

        if (numMissed == 3) {
            System.out.println(" _________     ");
            System.out.println("|         |    ");
            System.out.println("|         0    ");
            System.out.println("|        / \\  ");
            System.out.println("|              ");
            System.out.println("|              ");
            System.out.println("|______________");
        }

        if (numMissed == 2) {
            System.out.println(" _________     ");
            System.out.println("|         |    ");
            System.out.println("|         0    ");
            System.out.println("|        /     ");
            System.out.println("|              ");
            System.out.println("|              ");
            System.out.println("|______________");
        }

        if (numMissed == 1) {
            System.out.println(" _________     ");
            System.out.println("|         |    ");
            System.out.println("|         0    ");
            System.out.println("|              ");
            System.out.println("|              ");
            System.out.println("|              ");
            System.out.println("|______________");
        }

        if (numMissed == 0) {
            System.out.println(" _________     ");
            System.out.println("|         |    ");
            System.out.println("|              ");
            System.out.println("|              ");
            System.out.println("|              ");
            System.out.println("|              ");
            System.out.println("|______________");
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        HangmanWork hm = new HangmanWork();

        hm.runGame();

// TODO code application logic here
    }

}


