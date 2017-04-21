#Hangman
Hangman is a word game where you must guess letters which make up a word. You have a set number of guesses you are able to make before your man gets hanged. It comes with its own JUnit test suite.

##Usage:
There is a single class called Hangman. Instantiate the class with one argument, the word being used in the game:

	JHangman myGame = new JHangman("testing");
	
If you want to run the game on the command line use a single argument on the command line:

	java -cp src JHangman testing


##Compilation/Tests:
To keep the checkout and build of the project simple, just run the build script to compile the source and run the unit tests.

	./build.sh