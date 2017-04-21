import org.junit.*;
import java.io.*;
import static org.junit.Assert.*;

import java.util.*;
  
public class JHangmanTest {

	private static final String EOL = System.getProperty("line.separator");
	private ByteArrayOutputStream bytes;
	private PrintStream console;
	private JHangman jHangman;

	@Before
	public void setUp() {
		bytes = new ByteArrayOutputStream();
		console = System.out;
		System.setOut(new PrintStream(bytes));
		jHangman = new JHangman();
	}

	@After
	public void tearDown() {
		System.setOut(console);
	}

	@Test(expected=Exception.class)
	public void testErrorIfNoWordSupplied() throws IllegalArgumentException {
		jHangman.init();
	}
	
	@Test(expected=Exception.class)
	public void testErrorIfTooManyArgumentsSupplied() throws IllegalArgumentException {
		jHangman.init("one", "two", "three");
	}
	
	@Test(expected=Exception.class)
	public void testErrorIfSinlgeEmptyArgumentSupplied() throws IllegalArgumentException {
		jHangman.init("");
	}
	
	@Test
	public void testSuppliedWordIsObfuscatedCorrectly()  {
		jHangman.init("testing");
		assertEquals("???????", jHangman.getWorkingWord());
		
		JHangman anotherHangman = new JHangman();
		jHangman.init("anothertest");
		assertEquals("???????????", anotherHangman.getWorkingWord());
	}
	
	@Test
	public void testWePopulateWordWithCorrectlyGuessedLetters() {
		jHangman.init("testing");
		jHangman.tryLetter('t');
		assertEquals("t??t???", jHangman.getWorkingWord());
		jHangman.tryLetter('g');
		assertEquals("t??t??g", jHangman.getWorkingWord());
	}
	
	@Test
	public void testIncorrectGuessIncrementsTryCount() {
		jHangman.init("testing");
		jHangman.tryLetter('x');
		assertEquals("1 try", jHangman.getStatus());
		jHangman.tryLetter('x');
		assertEquals("2 tries", jHangman.getStatus());
	}
	
	@Test
	public void testGameOverIfMaxTriesReached() {
		jHangman.init("testing");
		jHangman.tryLetter('x');
		jHangman.tryLetter('y');
		jHangman.tryLetter('z');
		jHangman.tryLetter('p');
		assertEquals("game over", jHangman.getStatus());
	}
	
	@Test
	public void testGameWonIfwordGuessed() {
		jHangman.init("testing");
		jHangman.tryLetter('t');
		jHangman.tryLetter('e');
		jHangman.tryLetter('s');
		jHangman.tryLetter('i');
		jHangman.tryLetter('n');
		jHangman.tryLetter('g');
		assertEquals("you win", jHangman.getStatus());
	}
	
	@Test
	public void testInvalidExecuteFromMain() {
		JHangman.main();
		assertEquals("You need to supply a single argument — the word to play the game with"  + EOL, bytes.toString());
	}
	
	@Test
	public void testExecuteFromMain() {
		InputStream stdin = System.in;

		String s = "y" + EOL + "z" + EOL + "x" + EOL;
		System.setIn(new ByteArrayInputStream(s.getBytes()));

		JHangman.main("testing");
		System.setIn(stdin);
		
		assertEquals("???????"  + EOL + "letter y" + EOL + "1 try" + EOL + 
			"???????" + EOL + "letter z" + EOL + "2 tries" + EOL + "???????" + EOL + "letter x" + EOL + 
				"game over" + EOL + "???????" + EOL + "The word was testing!" + EOL, bytes.toString());
	}
	
	
	
}


