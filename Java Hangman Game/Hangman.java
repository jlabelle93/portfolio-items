// Jacob Labelle 300306856
import java.util.Scanner;
import java.util.Arrays;

public class Hangman {
	private String magicWord;
	private int score;
	private int guesses;
	private GameDictionary gameDict = new GameDictionary();
	private HighScoreList hsList  = new HighScoreList();
	private Scanner input = new Scanner(System.in);

	public Hangman() {
		this.magicWord = "";
		this.score = 0;
		this.guesses = 7;
		playGame();
	}
	// Playing the game
	public void playGame() {	
		if(gameDict.wordPopulation()) {
			while(guesses != 0) {
				magicWord = gameDict.wordSelection();
				String[] hiddenWord = new String[magicWord.length()];
				char[] guessedLetters = new char[26];
				char[] incorrectLetters = new char[7];
				for(int index = 0; index < hiddenWord.length; index++) {
					hiddenWord[index] = "_";
				}
				boolean wordGuessed = false;
				while(!wordGuessed && guesses != 0) {
					printCurrentWord(hiddenWord);
					printWrongGuesses(incorrectLetters);
					char guess = takeTurn(guessedLetters);
					int index = (int)guess - 'A';
					guessedLetters[index] = guess;
					boolean validGuess = isHiddenWordElement(guess, magicWord);
					if(!validGuess) {
						incorrectLetters[guesses-1] = guess;
						Arrays.sort(incorrectLetters);
						guesses--;
						System.out.println("Sorry there were no " + guess + "'s.");
					}
					else {
						score += 10 * updateHiddenWord(guess, hiddenWord, magicWord);

					}
					System.out.println();
					wordGuessed = isHiddenWordComplete(hiddenWord);
					if(wordGuessed) {
						score += 100 + (guesses*30);
						System.out.println("You guessed the word. It was " + magicWord + "!");
						System.out.println();
						guesses = 7;
						continue;
					}
				}
			}
			gameEnd();
		}
		else {
			System.out.println("You are not able to play this game without words.");
		}
	}
	// Check the guesses made to determine valid guess
	public boolean isValidUserGuess(char[] guessedLetters, char guess) {
		for(int index = 0; index < guessedLetters.length; index++) {
			if(guess == guessedLetters[index])
				return false;
		}
		return true;
	}
	// Updating the displayed hidden word
	public int updateHiddenWord(char guess, String[] hiddenWord, String magicWord) {
		int scored = 0;
		for(int index = 0; index < magicWord.length(); index++) {
			if(guess == magicWord.charAt(index)) {
				hiddenWord[index] = Character.toString(guess);
				scored++;
			}
		}
		return scored;
	}
	// Crawling the word to see if guess is contained within the word
	public boolean isHiddenWordElement(char guess, String magicWord) {
		for(int index = 0; index < magicWord.length(); index++) {
			if(guess == magicWord.charAt(index))
				return true;
		}
		return false;
	}
	// Check game status
	public boolean isHiddenWordComplete(String[] hiddenWord) {
		for(int index = 0; index < hiddenWord.length; index++) {
			if(hiddenWord[index].equals("_"))
				return false;
		}
		return true;
	}
	// Printing the word as it currently stands, based on user guesses
	public void printCurrentWord(String[] hiddenWord) {
		System.out.print("Hidden Word: ");
		for(int index = 0; index < hiddenWord.length; index++) {
			if(index < hiddenWord.length) {
				System.out.print(hiddenWord[index] + " ");
			}
			else {
				System.out.print(hiddenWord[index]);
			}
		}
		System.out.println();
	}
	// Perform the turn
	public char takeTurn(char[] guessedLetters) {
		System.out.println("Guesses Left: " + guesses);
		System.out.println("Score: " + score);
		System.out.print("Enter your next guess: ");

		String nextGuess = input.next();
		nextGuess = nextGuess.toUpperCase();
		char guess = nextGuess.charAt(0);
		while (!Character.isLetter(guess) || !isValidUserGuess(guessedLetters, guess)) {
			input.nextLine();
			System.out.print("Invalid guess. Please enter a new guess: ");
			nextGuess = input.next();
			nextGuess = nextGuess.toUpperCase();
			guess = nextGuess.charAt(0);
		}
		return guess;
	}
	// End Game
	public void gameEnd() {
		System.out.println("The word was " + magicWord + ".");
		System.out.println("Game over.");
		System.out.println("Your Score: " + score + "\n");
		if(hsList.isHighScore(score)) {
			System.out.print("Enter your name for your high score: ");
			String name = input.next();
			HighScoreUser hsu = new HighScoreUser(name, score);
			hsList.addToList(hsu);
		}
		hsList.printHighScores();
		hsList.saveHighScores();
		input.close();
	}
	// Printing the incorrect guesses
	public void printWrongGuesses(char[] incorrectLetters) {
		System.out.print("Incorrect Guesses: ");
		for(int index = 0; index < incorrectLetters.length; index++) {
			if(incorrectLetters[index] != 0) {
				if(index == incorrectLetters.length-1) {
					System.out.print(incorrectLetters[index]);
				}
				else {
					System.out.print(incorrectLetters[index] + ", ");
				}
			}
		}
		System.out.println();
	}
}




