package objectOrientedParadigm;

import java.util.ArrayList;
import java.util.Scanner;

public class UnoDeck extends Deck {

	// Overridden method to build a deck of colored cards (Uno-style)
	public void buildDeck(Scanner input) {
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H" }; // card letters
		String[] colors = { "red", "orange", "blue", "green", "yellow", "purple", "brown", "pink" }; // card colors

		System.out.print("Choose difficulty\n(1 for easy, 2 for hard): ");
		int difficulty = -1;

		// input validation using try catch to make sure only 1 or 2 is selected
		while (true) {
			try {
				difficulty = input.nextInt(); // input
				if (difficulty == 1 || difficulty == 2) {
					break; // valid input
				} else {
					System.out.print("Enter 1 or 2: ");
				}
			} catch (Exception e) {
				System.out.print("Invalid input. Enter a number (1 or 2): ");
				input.nextLine(); // clear invalid input
			}
		}

		if (difficulty == 1) {
			numberOfCards = 8; // 4 pairs
		} else if (difficulty == 2) {
			numberOfCards = 16; // 8 pairs
		}

		// a for loop to create all possible letter/color combinations
		ArrayList<UnoCards> allCombos = new ArrayList<UnoCards>();
		for (int i = 0; i < letters.length; i++) {
			for (int j = 0; j < colors.length; j++) {
				allCombos.add(new UnoCards(letters[i], colors[j]));
			}
		}

		// shuffle the combinations using fisher yates algorithm
		for (int i = allCombos.size() - 1; i > 0; i--) {
			int randomNum = (int) (Math.random() * (i + 1));
			UnoCards temp = allCombos.get(i);
			allCombos.set(i, allCombos.get(randomNum));
			allCombos.set(randomNum, temp);
		}

		// pick the first N and duplicate them to make pairs
		for (int i = 0; i < numberOfCards / 2; i++) {
			cards.add(new UnoCards(allCombos.get(i).getCardFace(1), allCombos.get(i).getCardFace(2)));
			cards.add(new UnoCards(allCombos.get(i).getCardFace(1), allCombos.get(i).getCardFace(2)));
		}
	}

	// Overridden checkCards method to support matching by letter OR color
	public void checkCards(Scanner input) {
		int cardsFound = 0;
		int first = -1;
		int second = -1;

		System.out.print("Choose a card: ");

		while (cardsFound != numberOfCards) {

			while (true) {
				try {
					first = input.nextInt(); // input
					if (first < 1 || first > cards.size() || cards.get(first - 1).getFlip()) {
						System.out.print("Choose a valid card: ");
						continue; // prompt again
					}
					break;
				} catch (Exception e) {
					System.out.print("Invalid input. Please enter a number: ");
					input.nextLine(); // clear
				}
			}

			cards.get(first - 1).setFlip(true);
			printCards();
			System.out.println();
			System.out.print("You chose ");
			printCards(first - 1);
			System.out.println();

			System.out.print("Choose another card: ");
			while (true) {
				try {
					second = input.nextInt(); // input
					if (second < 1 || second > cards.size() || second == first || cards.get(second - 1).getFlip()) {
						System.out.print("Choose a valid card: ");
						continue; // prompt again
					}
					break;
				} catch (Exception e) {
					System.out.print("Invalid input. Please enter a number:");
					input.nextLine(); // clear
				}
			}

			cards.get(second - 1).setFlip(true);
			printCards();
			System.out.println();
			System.out.print("You chose ");
			printCards(first - 1);
			System.out.print(" and ");
			printCards(second - 1);
			System.out.println();

			UnoCards c1 = (UnoCards) cards.get(first - 1); // cast to UnoCards
			UnoCards c2 = (UnoCards) cards.get(second - 1);

			//match uno cards by either letter or color
			if (c1.getCardFace(1).equals(c2.getCardFace(1)) || c1.getCardFace(2).equals(c2.getCardFace(2))) {
				System.out.print("Well done, match!! ");
				cardsFound += 2;
				if (cardsFound != numberOfCards) {
					System.out.print("Choose a card: ");
				}
			} else {
				System.out.print("Not a match.. Try again! Choose a card: ");
				cards.get(second - 1).setFlip(false);
				cards.get(first - 1).setFlip(false);
				if(canLastCardsMatch()==false) {//if the last cards can't be matched, exit the game
					System.out.println("The last cards can't be matched.. exiting");
					return;
				}
			}
		}

		System.out.println("You found all the cards!");
	}
	//a method to ensure that the last cards match 
	private boolean canLastCardsMatch() {
		ArrayList<UnoCards> remainingCards = new ArrayList<>();
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getFlip()==false) {
				remainingCards.add((UnoCards)cards.get(i));
			}
		}
		if (remainingCards.size()!=4) {
			return true;
		}
		for (int i = 0; i < 3; i++) {
			for (int j = i + 1; j < 4; j++) {
				if (remainingCards.get(i).getCardFace(1).equals(remainingCards.get(j).getCardFace(1)) ||
					remainingCards.get(i).getCardFace(2).equals(remainingCards.get(j).getCardFace(2))) {
					return true; //match found
				}
			}
		}
		return false; // no matches
	}
}
