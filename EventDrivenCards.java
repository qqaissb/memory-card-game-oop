package eventDrivenParadigm;

import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class EventDrivenCards {

	//Global variables
	//Main game window
	private static JFrame gameFrame;
	
    //An array to store card letters
	private static final ArrayList<String> cardFaceArray = new ArrayList<>();
	
    //An array to store card buttons
	private static final ArrayList<JButton> cardButtonsArray = new ArrayList<>();
	
    //A boolean array for cards that are flipped
	private static boolean[] flippedCards;
	
    //A boolean array for cards that are matched
	private static boolean[] matchedCards;
		
	//to make sure the click already label is active or not
	private static boolean tempLabelActive = false;

	//an attribute for the first card
	private static int firstCard = -1;
    
	//an attribute for the first card
	private static int secondCard = -1;
	
	//an attribute for the cards found
	private static int pairsFound = 0;
	
	//an attribute for if its unomode or not
	private static boolean unoMode = false;
	
	//an attribute for the first card clicked to make sure that the cards get hidden after its clicked
	private static boolean firstCardClicked = false;

	//an attribute for the card index of the listeners
	private static int chosenCardIndex = -1;

	//The main method where it has the frame and main menu
	public static void main(String[] args) {
	
		gameFrame = new JFrame("Card Matching Game"); //Creating the frame with frame title
		gameFrame.setSize(700, 700); //Size of the frame is 700 pixels by 700 pixels
		gameFrame.setLocationRelativeTo(null); //Frame doesn't start at a certain location, in the center
		gameFrame.setResizable(false); //Frame cant be resizeable
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //When pressed X, the game closes
		showMainMenu(); //The main menu method where the game starts
		gameFrame.setVisible(true); //The frame becomes visible
		
	}

	//The most important method, where the whole game starts, the main menu
	private static void showMainMenu() {
		//The frame gets refreshed and all content in it is deleted
		gameFrame.getContentPane().removeAll();
		
		//the game gets reset, where every variable is reset to its default value 
		resetGame(0);
		//A panel where it has the start up menu
		JPanel mainPanel = new JPanel();
		//Box layout to make it stack
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//A title label where it says "memory game"
		JLabel title = new JLabel("Card Matching Game");
		//Font of the title is Arial, it is bold and its size is 30
		title.setFont(new Font("Arial", Font.BOLD, 30));
		//The title is in the center of the frame
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		//Two buttons in the menu, one for normal mode and the other for uno mode
		JButton normalModeButton = createMenuButton("Normal Mode");
		JButton unoModeButton = createMenuButton("Uno Mode");
		//An action listener for the normal mode button, where unoMode is false, so a normal game starts
		normalModeButton.addActionListener(e -> {
			unoMode = false;
			showDifficultyScreen(); //This method clears the main menu and shows the difficulty options
		});
		//An action listener for the Uno mode button, where unoMode is true, so an Uno game starts
		unoModeButton.addActionListener(e -> {
			unoMode = true;
			showDifficultyScreen(); //This method clears the main menu and shows the difficulty options
		});
		//Here, some space is added to make sure everything isnt compressed with each other
		mainPanel.add(Box.createVerticalStrut(50));
		//Adding the title label to panel
		mainPanel.add(title);
		//Adding more space between title and buttons
		mainPanel.add(Box.createVerticalStrut(50));
		//Adding the game mode buttons with little space between them
		mainPanel.add(normalModeButton);
		mainPanel.add(Box.createVerticalStrut(20));
		mainPanel.add(unoModeButton);
		mainPanel.add(Box.createVerticalStrut(50));
		
		//a temporary label that appears when near main buttons
		JLabel tempLabel = new JLabel("Click! What are you waiting for?");
		tempLabel.setFont(new Font("Arial", Font.ITALIC, 18));
		//The title is in the center of the frame
		tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		//a mouse motion listen that detects if the mouse if getting close to the buttons to joke with the user and ask them to click
		mainPanel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX(); // get x coordinate
				int y = e.getY(); // get y coordinate
				boolean isMouseInside = x >= 220 && x <= 470 && y >= 115 && y <= 295; 
				 if (isMouseInside && tempLabelActive == false) {//if the mouse was near the buttons add the label
			            mainPanel.add(tempLabel,7); //put it under the cards
			            mainPanel.revalidate();//recalculate layout
			            mainPanel.repaint();//update the frame
			            tempLabelActive = true;
				 }else if (isMouseInside == false && tempLabelActive == true){ //else if it was not, remove the label
					 mainPanel.remove(tempLabel);
				     mainPanel.revalidate();//recalculate layout
				     mainPanel.repaint();//update the frame
					 tempLabelActive = false;
				 }
			}
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		//Adding the panel to the frame
		gameFrame.setContentPane(mainPanel);
		//Recalculates layout
		gameFrame.revalidate();
		//Updates the frame
		gameFrame.repaint();
		//Lets frame listen to keyboard inputs
		gameFrame.requestFocusInWindow();
	}
	
	//A difficulty screen method, where a difficulty panel is seen when choosing a game mode
	private static void showDifficultyScreen() {
		//idk
		gameFrame.getContentPane().removeAll();
		//Also a box layout to be stacked
		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
		//adding a title to the difficulty screen
		JLabel title = new JLabel("Select Difficulty");
		title.setFont(new Font("Arial", Font.BOLD, 26));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		//2 buttons, easy difficulty, hard difficulty
		JButton easyBtn = createMenuButton("Easy (8 cards)");
		JButton hardBtn = createMenuButton("Hard (16 cards)");
		//a third secret button
		JButton secretBtn = createMenuButton("Secret");
		secretBtn.setVisible(false);
		/*action listeners, where the easy button starts the game with the startGame() method
		*8 cards, and hard starts the game with 16 cards with the startGame() method*/
		easyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame(8);
			}
		});	
		hardBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame(16);
			}
		});
		secretBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame(32);
			}
		});

		//adding the title and buttons with spaces in between, in the difficulty panel
		difficultyPanel.add(Box.createVerticalStrut(50));
		difficultyPanel.add(title);
		difficultyPanel.add(Box.createVerticalStrut(30));
		difficultyPanel.add(easyBtn);
		difficultyPanel.add(Box.createVerticalStrut(15));
		difficultyPanel.add(hardBtn);
		difficultyPanel.add(Box.createVerticalStrut(15));
		difficultyPanel.add(secretBtn);
		difficultyPanel.add(Box.createVerticalStrut(30));
		
		//a mouse motion listener that activates the hidden secret button
		difficultyPanel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				boolean secretArea = (x>0 && x<50 && y > 600 && y < 700);
				if (secretArea == true) {
					secretBtn.setVisible(true);
					difficultyPanel.revalidate();
					difficultyPanel.repaint();
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {				
			}
		});
		
		//setting the panel to be the difficulty panel
		gameFrame.setContentPane(difficultyPanel);
		//Recalculates layout
		gameFrame.revalidate();
		//Updates the frame
		gameFrame.repaint();
		//Lets frame listen to keyboard inputs
		gameFrame.requestFocusInWindow();
	}
	//a method that starts the game and calls the build deck method
	private static void startGame(int numberOfCards) {
		//everything in frame gets removed to refresh
		gameFrame.getContentPane().removeAll();
		//title of the game is determined by the mode chosen
		if (unoMode) {
		    gameFrame.setTitle("Uno Memory Game");
		} else {
		    gameFrame.setTitle("Normal Memory Game");
		}
		//deck is built upon the card number
		buildDeck(numberOfCards);
		//number of rows determined by rows divided by 4, to make it simple
		int rows = numberOfCards / 4;
		//Deck panel with a grid layout, with 4 columns and number of rows determined by difficulty
		JPanel deck = new JPanel(new GridLayout(rows, 4, 10, 10));
		//adds 20 pixel space around, top bottom, left, right
		deck.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		//removes all card buttons from the card buttons array
		cardButtonsArray.clear();

		//adds the deck into the panel
		gameFrame.setContentPane(deck);
		//create card buttons depending on the number of cards
		for (int i = 0; i < numberOfCards; i++) {
			JButton cardButton = new JButton();
			cardButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));//card border
			cardButton.setFont(new Font("Arial", Font.BOLD, 24));
			int index = i;
			//an action listener to click the button of the index wanted
			cardButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cardClick(index);
				}
			});
			//mouse listener that changes the card index depending on where the mouse hovers over the card button
			cardButton.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					if (chosenCardIndex == index) {
						chosenCardIndex = -1;
					}
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					chosenCardIndex = index;
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});

			cardButtonsArray.add(cardButton);
			deck.add(cardButton);
		}
		
		//a key listener that makes that clicks the card when the enter key is pressed
		gameFrame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && chosenCardIndex >= 0) {
					cardClick(chosenCardIndex);
				}	
			}
		});
		gameFrame.setFocusable(true); //Allows the frame to get focused
		//Lets frame listen to keyboard inputs
		gameFrame.requestFocusInWindow();

		flipAllCards(true); //show all cards at start
		gameFrame.revalidate();//recalculate the frame
		gameFrame.repaint();//update the frame
	}
	//a method to decide what happens when a card button is clicked
	private static void cardClick(int index) {
		//this is just to make sure no errors happen while doing the card click
		if (index >= cardButtonsArray.size()) {
			return; 
		}
		//if the game starts and no card has been clicked yet
		if (firstCardClicked == false) {
			//set it so that the card has been clicked
			firstCardClicked = true;
			//flip all the cards back when the first card has been clicked
			for (int i = 0; i < flippedCards.length; i++) {
				flipAllCards(false);
			}
		}
		//the cards selected dont match, flip them back
		if (firstCard != -1 && secondCard != -1) {
			if (matchedCards[firstCard]==false) {
				flippedCards[firstCard] = false;
				changeCardButton(firstCard);
			}
			if (matchedCards[secondCard]==false) {
				flippedCards[secondCard] = false;
				changeCardButton(secondCard);
			}
			//reset it to make the cards not selected
			firstCard = -1;
			secondCard = -1;
		}
		//this doesnt allow the user to flip flipped or matched cards
		if (flippedCards[index] || matchedCards[index]) {
			return;
		}
		//if this method hasnt been returned yet, the card will be flipped
		flippedCards[index] = true;
		//and the card's button will be changed to its
		changeCardButton(index);
		//if the two cards are matched,
		if (firstCard == -1) { //if the first card hasnt been chosen
			firstCard = index;//make it its own index
		} else {
			secondCard = index; //if not, then it must be the second card
			if (isMatch(cardFaceArray.get(firstCard), cardFaceArray.get(secondCard))) { //if they match
				matchedCards[firstCard] = true;//set them to match
				matchedCards[secondCard] = true;
				cardButtonsArray.get(firstCard).setEnabled(false);//disable the buttons
				cardButtonsArray.get(secondCard).setEnabled(false);
				pairsFound++;//increase the pairs found
				firstCard = -1;//reset 
				secondCard = -1;

				if (pairsFound == cardFaceArray.size() / 2) {//if all pairs are found, finish the game
					JOptionPane.showMessageDialog(gameFrame, "You won!");
					showMainMenu();
				}
			} else {
                // Final 2 or 4 checks for Uno mode
				if (unoMode) {
				    // Collects all cards that are not matched yet
				    ArrayList<Integer> unmatchedCards = new ArrayList<>();
				    for (int i = 0; i < matchedCards.length; i++) {
				        if (!matchedCards[i]) {
				            unmatchedCards.add(i);
				        }
				    }

				    // Only check if there are 4 or fewer cards left
				    if (unmatchedCards.size() <= 4) {
				        boolean canMatch = false;

				        // Check every possible pair to see if a match is still possible
				        for (int i = 0; i < unmatchedCards.size(); i++) {
				            for (int j = i + 1; j < unmatchedCards.size(); j++) {
				            	//two for loops so that it checks the two cards at the end
				                int index1 = unmatchedCards.get(i);
				                int index2 = unmatchedCards.get(j);
				                String card1 = cardFaceArray.get(index1);
				                String card2 = cardFaceArray.get(index2);

				                if (isMatch(card1, card2)) {
				                    canMatch = true;
				                    break; // exit inner loop to check the other two cards
				                }
				            }
				            if (canMatch) break; // exit outer loop
				        }

				        // If no match is possible, end the game
				        if (!canMatch) {
				            JOptionPane.showMessageDialog(gameFrame,"The remaining cards cannot be matched...\nGoing back to menu");
				            showMainMenu();
				            return;
				        }
				    }
				}
			}
		}
		//Lets frame listen to keyboard inputs
		gameFrame.requestFocusInWindow();
	}
	//a method to check if the cards match or not
	private static boolean isMatch(String firstChoice, String secondChoice) {
		if (!unoMode) {
			//if its normal mode, it only returns if the first card matches the second card
			return firstChoice.equals(secondChoice);
		}else {
			//else if its uno mode, it would split the letter and the color, and check if color or letter match
			String[] firstChoice1 = firstChoice.split("-");
			String[] secondChoice1 = secondChoice.split("-");
			return firstChoice1[0].equals(secondChoice1[0]) || firstChoice1[1].equals(secondChoice1[1]);
		}
		
	}

	private static void changeCardButton(int index) {
		//a button for every letter and 
		JButton btn = cardButtonsArray.get(index);
		String letter = cardFaceArray.get(index);
		//if the card is flipped or matched, itd still have its face shown
		if (flippedCards[index] || matchedCards[index]) {
			if (unoMode) {
				String[] lettersAndColors = letter.split("-");//separate colors and letters
				btn.setText(lettersAndColors[0]);//set the card face's letter to its letter
				btn.setBackground(getColor(lettersAndColors[1]));//set the card face's color to its color
				btn.setForeground(Color.BLACK);
			} else {//else its normal mode
				btn.setText(letter);
				btn.setBackground(Color.WHITE);//card background is white
				btn.setForeground(Color.BLACK);//text color is black
			}
		} else {//if the card isnt flipped, make it gray and remove the text
			btn.setText("");
			btn.setBackground(new Color(80, 80, 80));
		}
	}
	//a method to get the color of the uno card face background
	private static Color getColor(String color) {
		//a switch case to return the color based on the color string in the build deck method
		switch (color) {
			case "red": return Color.RED;
			case "blue": return Color.CYAN;
			case "green": return Color.GREEN;
			case "yellow": return Color.YELLOW;
			case "beige": return new Color(255,228,181);
			case "purple": return new Color(128, 0, 128);
			case "brown": return new Color(139, 69, 19);
			case "pink": return new Color(255,105,180);
			default: return Color.GRAY;
		}
	}
	//a method that flips cards based on parameter
	private static void flipAllCards(boolean flipped) {
		for (int i = 0; i < cardButtonsArray.size(); i++) {
			flippedCards[i] = flipped;
			//changes the card's face, if its flipped, itd be its letter/ letter and color, else its backside
			changeCardButton(i); 
		}
	}

	private static void buildDeck(int totalCards) {
		//a new round starts which resets variables 
		resetGame(totalCards);
		//an array for all letters and colors (colors only for uno mode)
		String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P"};
		String[] colors = {"red", "beige", "blue", "green", "yellow", "purple", "brown", "pink"};
		//an array list to store all possible letter and color combo
		ArrayList<String> allCombos = new ArrayList<>();
		if (unoMode) {
			//two for loops to make sure theres a color for every letter and vice versa
			for (String letter : letters) {//colors are added to the array list only if its uno mode
				for (String color : colors) {
					allCombos.add(letter + "-" + color);
				}
			}
		} else {//one for loop to add for normal mode
			for (String letter : letters) {
				allCombos.add(letter);
			}
		}
		//shuffle all possible combos using the fisher yates algorithm
		shuffle(allCombos);
		//another array list to select a number of cards from the shuffled allCombos array list
		ArrayList<String> selectedCombos = new ArrayList<>();
		//while loop iterates for the number of pairs
		while (selectedCombos.size() < totalCards / 2 ) {
			String combo = allCombos.remove(0);
		    selectedCombos.add(combo);//add it
		}
		//removes all the cards from the card faces array to add the new Uno ones
		cardFaceArray.clear();
		//for each uno card in the selected combo, add a pair to the card face array
		for (String unoCard : selectedCombos) {
			cardFaceArray.add(unoCard);
			cardFaceArray.add(unoCard);
		}
		//shuffles the cards using 
		shuffle(cardFaceArray);
	}
	//A shuffle method which is based on the fisher-yates algorithm
	private static void shuffle(ArrayList<String> cards) {
		for (int i = cards.size() - 1; i > 0; i--) {
			int randomNum = (int) (Math.random() * (i + 1)); // a random integer is picked from 0 to i
			String temp = cards.get(i); //a temporary string has the value of the first card
			cards.set(i, cards.get(randomNum)); //the card at index i gets replaced with the card at the random index
			cards.set(randomNum, temp); //the card at the random index gets switched with the card previously at index i
		}
	}
	//a method for making menu buttons, like the choose game mode and the difficulty buttons
	private static JButton createMenuButton(String label) {
		JButton button = new JButton(label);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setFont(new Font("Arial", Font.ITALIC, 20));
		return button;
	}
	//a method to reset the game whenever the game is won or left or started
	private static void resetGame(int totalCards) {
		//all variables get set back to default
		cardFaceArray.clear();
		cardButtonsArray.clear();
		flippedCards = new boolean[totalCards];
		matchedCards = new boolean[totalCards];
		firstCard = -1;
		secondCard = -1;
		pairsFound = 0;
		firstCardClicked = false;
	}
}
