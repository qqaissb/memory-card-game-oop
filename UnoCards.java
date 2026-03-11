package objectOrientedParadigm;

public class UnoCards extends Cards {
	
	//color attribute for uno card
	private String color;

	//a constructor for UnoCards with letter and color
	UnoCards(String letter, String color) {
		super(letter);
		this.color = color;
	}

	//override to return letter and color
	public String getCardFace() {
		return (super.getCardFace() + "~" + this.color);
	}

	//overloaded method to return either face or color based on parameter
	public String getCardFace(int face) {
		if (face == 1) {
			return super.getCardFace(); // Returns just the letter
		} else {
			return color; // Just the color
		}
	}
}
