import java.io.*;

public class JHangman {

	private static String word;
	private static String workingWord;
	private static String status;
	private static int numberOfTries;
	private static int MAX_NUMBER_OF_TRIES = 3;
	private static String errorMessage;

	JHangman() {
		numberOfTries = 0;
		status = "";
	}

	private static void setWord(String w) {
		word = w;
	}

	private static boolean validateSetupArgs(String[] args) {
		if (args.length == 0) {
			errorMessage = "You need to supply a single argument — the word to play the game with";
			return false;
		}

		else if (args[0] == "") {
			errorMessage = "You need to supply a word of at least one letter";
			return false;
		}

		else if (args.length > 1) {
			errorMessage = "You only need to suply one word, with no spaces";
			return false;
		}
		return true;
	}

	public static void init(String... args) {
		if(validateSetupArgs(args)) {
			setWord(args[0]);
			obfuscateWord();
		}
		else {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	private static void obfuscateWord() {
		String j = "";
		for (int i = 0; i < word.length(); i++) {
			j += "?";
		}
		workingWord = j;
	}

	public static String getWorkingWord() {
		return workingWord;
	}

	private static String replaceCharAt(String s, int pos, char c) {
		StringBuffer buf = new StringBuffer( s );
		buf.setCharAt( pos, c );
		return buf.toString( );
	}

	public static void tryLetter(char letter) {
		Boolean tries = true;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == letter) {
				tries = false;
				workingWord = replaceCharAt(workingWord, i, letter);
			}
		}
		if (tries) {
			numberOfTries++;
		}

		if (workingWord.indexOf('?') == -1) {
			numberOfTries = -1;
		}

		updateStatus();
	}

	private static void updateStatus() {
		String message = " try";

		if (numberOfTries == -1) {
			status = "you win";
		}
		else if (numberOfTries == 1) {
			status = Integer.toString(numberOfTries) + message;
		}
		else if (numberOfTries >= MAX_NUMBER_OF_TRIES) {
			status = "game over";
		}
		else {
			message = " tries";
			status = Integer.toString(numberOfTries) + message;
		}
	}

	public static String getStatus() {
		return status;
	}

	public static void main(String... args) {

		if(!validateSetupArgs(args)) {
			System.out.println(errorMessage);
			return;
		}

		final JHangman myInstance = new JHangman();
		myInstance.init(args);

		System.out.println(myInstance.getWorkingWord());

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		char letter;

		while(myInstance.getStatus() != "you win" & myInstance.getStatus() != "game over") {
			try {
				userInput = br.readLine();
				try {
					letter = userInput.charAt(0);
					System.out.println("letter " + letter);
				}
				catch (StringIndexOutOfBoundsException oobe) {
					System.out.println("Oops, enter a character, please!");
					continue;
				}
				myInstance.tryLetter(letter);
				System.out.println(myInstance.getStatus());
				System.out.println(myInstance.getWorkingWord());
			}
			catch (IOException ioe) {
				System.out.println("IO error trying to read your name!");
				System.exit(1);
			}
			catch (NullPointerException np) {
				System.out.println("aaaaaaaathghhg!");
				System.exit(1);
			}
		}
		System.out.println("The word was " + word + "!");
	}
}
