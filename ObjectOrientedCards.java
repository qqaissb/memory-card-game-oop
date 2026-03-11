package objectOrientedParadigm;

import java.util.Scanner;

public class ObjectOrientedCards {

	public static void main(String[] args) {
		// Welcome message and scanner input for game mode selection
		System.out.print("Welcome to Memory Matching!\n1) ~Normal~\t2) ~Uno~\nChoose Game Mode: ");

		//making a scanner
		Scanner input = new Scanner(System.in);
		//a mode variable
		int mode = -1; 

		// Ensure user selects a valid mode (1 or 2)
		while (true) {
			try {
				mode = input.nextInt(); // User selects mode
				if (mode == 1 || mode == 2) {
					break; //breaks the loop if valid input
				} else {
					System.out.print("Enter 1 or 2: ");
				}
			} catch (Exception e) {
				System.out.print("Invalid input. Please enter 1 or 2: ");
				input.nextLine(); // clears the invalid input
			}
		}

		// If user selects mode 1 (normal mode)
		if (mode == 1) {
			Deck deck = new Deck(); // Create normal deck
			deck.buildDeck(input); // Build deck based on difficulty
			deck.shuffle(); // Shuffle the deck
			deck.revealCards(); // Reveal the card faces for one second
			deck.checkCards(input);  // Start game loop

		// If user selects mode 2 (Uno-mode)
		} else if (mode == 2) {
			UnoDeck unoDeck = new UnoDeck(); // Create Uno-style deck
			unoDeck.buildDeck(input); // Build Uno deck
			unoDeck.shuffle(); // Shuffles the deck
			unoDeck.revealCards(); // Reveal the card faces for one second
			unoDeck.checkCards(input); // Begin UNO game loop
		}
		input.close(); //closing the scanner
	}
}
