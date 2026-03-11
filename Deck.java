package objectOrientedParadigm;

import java.util.ArrayList;
import java.util.Scanner;

public class Deck {

	protected ArrayList<Cards> cards = new ArrayList<Cards>(); // stores card objects 
	protected int numberOfCards; // number of cards used in game (8 or 16 based on difficulty)

	//shuffle method to randomize the order of cards using the fisher-yates algorithm
	public void shuffle() {
		for (int i = numberOfCards - 1; i > 0; i--) {
			int randomNum = (int) (Math.random() * (i + 1)); // generate a random index from 0 to i
			Cards temp = cards.get(i); // store the card at index i temporarily
			cards.set(i, cards.get(randomNum)); // replace card at i with card at the random number
			cards.set(randomNum, temp); // set card at the random number to the card at index i
		}
	}

	// A method that checks if the user input is valid and checks if the selected cards match
	public void checkCards(Scanner input) {
		int cardsFound = 0; // number of matched cards
		int first = -1; // first card index
		int second = -1; // second card index

		System.out.print("Choose a card: ");

		while (cardsFound != numberOfCards) {

			// Get first card input
			while (true) {
				try {
					first = input.nextInt(); // user input
					//if the first card was out of bounds of the array or is already flipped
					if (first < 1 || first > cards.size() || cards.get(first - 1).getFlip()) {
						System.out.print("Choose a valid card: ");
						continue; // prompt again if invalid
					}
					break;
				} catch (Exception e) {
					System.out.print("Invalid input. Please enter a number: ");
					input.nextLine(); // clear invalid input
				}
			}

			// flip and show first card
			cards.get(first - 1).setFlip(true);
			printCards();
			System.out.println();
			System.out.print("You chose ");
			printCards(first - 1);
			System.out.println();

			//get the second card input
			System.out.print("Choose another card: ");
			while (true) {
				try {
					second = input.nextInt(); // user input
					if (second < 1 || second > cards.size() || second == first || cards.get(second - 1).getFlip()) {
						System.out.print("Choose a valid card: ");
						continue; // prompt again if invalid
					}
					break;
				} catch (Exception e) {
					System.out.print("Invalid input. Please enter a number: ");
					input.nextLine(); // clears the invalid input
				}
			}

			// flip and show second card
			cards.get(second - 1).setFlip(true);
			printCards();
			System.out.println();
			System.out.print("You chose ");
			printCards(first - 1);
			System.out.print(" and ");
			printCards(second - 1);
			System.out.println();

			// check if both cards match
			if (cards.get(second - 1).getCardFace().equals(cards.get(first - 1).getCardFace())) {
				System.out.print("Well done, match!! ");
				cardsFound += 2; // update matched count
				if (cardsFound != numberOfCards) {
					System.out.print("Choose a card: ");
				}
			} else {
				System.out.print("Not a match.. Try again! Choose a card: ");
				// unflip both cards
				cards.get(second - 1).setFlip(false);
				cards.get(first - 1).setFlip(false);
			}
		}

		// game ends when all matches are found
		System.out.println("You found all the cards!");
	}

	// Method that shows all cards for a short time before hiding them again
	public void revealCards() {
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setFlip(true); // flip all cards
		}
		printCards(); // print all cards revealed

		try {
			Thread.sleep(1000); // pause for 1 second
		} catch (InterruptedException e) {
			System.out.println("Thread was interrupted during reveal.");
		}

		for (int i = 0; i < 30; i++) {
			System.out.println(); // clear screen
		}

		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setFlip(false); // unflip all cards again
		}
		printCards(); // print board again with hidden cards
	}

	// Method that prints all cards, showing face if flipped or number if hidden
	public void printCards() {
		for (int i = 0; i < numberOfCards; i++) {
			if (cards.get(i).getFlip()) {
				System.out.print("|" + cards.get(i).getCardFace() + "| ");
			} else {
				System.out.print("|" + (i + 1) + "| ");
			}
		}
		System.out.println();
	}

	// Overloaded method that prints a specific card
	public void printCards(int cardNumber) {
		System.out.print("|" + cards.get(cardNumber).getCardFace() + "| ");
	}

	// Builds the deck based on user-selected difficulty
	public void buildDeck(Scanner input) {
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H" }; // card letters to choose from

		System.out.print("Choose difficulty\n(1 for easy, 2 for hard): ");
		int difficulty = -1;

		// make sure input is valid
		while (true) {
			try {
				difficulty = input.nextInt(); // input taken
				if (difficulty == 1 || difficulty == 2) { //if input is valid
					break; //while loop breaks
				} else { // if input is an integer and not 1 or 2, it will print the below
					System.out.print("Enter 1 or 2: ");
				}
			} catch (Exception e) { //if a non integer is inputted, the buffer clears and the sentence below is printed
				System.out.print("Invalid input. Enter a number (1 or 2): ");
				input.nextLine(); // clears the invalid input
			}
		}

		if (difficulty == 1) {
			numberOfCards = 8; // easy = 4 pairs
		} else if (difficulty == 2) {
			numberOfCards = 16; // hard = 8 pairs
		}

		// add matching pairs to the deck
		for (int i = 0; i < (numberOfCards / 2); i++) {
			cards.add(new Cards(letters[i]));
			cards.add(new Cards(letters[i]));
		}
	}
}
