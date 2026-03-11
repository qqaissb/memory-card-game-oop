package objectOrientedParadigm;

public class Cards {
	//letter attribute
	private String letter;
	//a boolean that is if the card is flipped or not
	private boolean isFlipped = false;

	//a parameterized constructor
	Cards(String letter) {
		this.letter = letter;
	}

	//this returns the value of the card's face 
	public String getCardFace() {
		return letter.toString();
	}

	//a getter to check if the card is flipped or not
	public boolean getFlip() {
		return isFlipped;
	}

	//a setter to flip or unflip the card
	public void setFlip(boolean bool) {
		this.isFlipped = bool;
	}

}
