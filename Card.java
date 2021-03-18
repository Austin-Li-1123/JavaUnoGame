package src;
import java.util.*; 


/**
 * A Card has a color, a number/symbol. 
 */
public class Card {

	/**
	 * The color of a card. [red, yellow, green, blue, black] => [0,1,2,3,4]
	 */
	public int cardColor;
	/**
	 * If a card is a number card. [number, skip, reverse, plus2, wild, plus4] => [0,1,2,3,4,5]
	 */
	public int cardType;
	/**
	 * The number on a card: [0-9]
	 */
	public int cardNumber;
	
	/**
	 * Maps integer color codes to string of color names
	 */
	public Map<Integer, String> colorMap = new HashMap<Integer, String>() {{
		put(0, "red");
		put(1, "yellow");
		put(2, "green");
		put(3, "blue");
		put(4, "black");
	}};
	/**
	 * Maps integer type codes to string of type names
	 */
	private Map<Integer, String> typeMap = new HashMap<Integer, String>() {{
		put(0, "number");
		put(1, "skip");
		put(2, "reverse");
		put(3, "plus2");
		put(4, "wild");
		put(5, "plus4");
	}};
	
	
	/**
	 * Construct a Card. Expected caller to give all fields
	 */
	public Card(int color, int type, int number) {
		cardColor = color;
		cardType = type;
		cardNumber = number;
//		System.out.println("" + cardColor+ cardType+ cardNumber);
	}
	
	/**
	 * Generate a string description of the card
	 */
	public String cardDescription() {

		String description = "";
		// color
		description += colorMap.get(cardColor);
		// if number
		if (cardType == 0) {
			description += " " + cardNumber;
		} else {
			// print the symbol name
			description += " " + typeMap.get(cardType);
		}
		
		
		return description;
		
	}
	
	/**
	 * creates a copy of the card
	 */
	public Card copy(){
		return new Card(cardColor, cardType, cardNumber);
	}
	/**
	 * Accessing the cardColor field from other classes
	 */
	public int getColor() {
		return cardColor;
	}
	/**
	 * Accessing the cardNumber field from other classes
	 */
	public int getNumber() {
		return cardNumber;
	}
	/**
	 * Accessing the cardType field from other classes
	 */
	public int getType() {
		return cardType;
	}
	/**
	 * Accessing the cardType field from other classes
	 */
	public static boolean equals(Card first, Card second) {
		if (first.cardColor == second.cardColor && first.cardType == second.cardType && first.cardNumber == second.cardNumber) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * generate a string containing a list of cards
	 */
	public static String printCardList(ArrayList<Card> cards) {
		String message = "";
		for (int i = 0; i < cards.size(); i++) {
			Card currentCard = cards.get(i);
			//System.out.println("" + currentCard.cardColor+ currentCard.cardType+ currentCard.cardNumber);
			message += currentCard.cardDescription() + ", ";
		}
		
		return message;
		
	}
	
	/**
	 * Checks if the selected card is valid for the current state
	 * There are only two invalid cases: 
	 * 		1. Different color, not the same type
	 * 		2. Different color, same type, number card, not the same number
	 * 
	 * @return true for valid play
	 */
	public static boolean validPlayedCard(Card currentState, Card selectedCard) {
		//if null -> return false . This happens at the beginning of the game
		if (currentState == null || selectedCard == null) {
			return false;
		}
		// if input card is wild or wild+4, it is always valid
		if (selectedCard.getType() == UnoGame.TYPE_CODE_WILD || selectedCard.getType() == UnoGame.TYPE_CODE_PLUS4) {
			return true;
		}
		// check if color are different
		if (selectedCard.getColor() != currentState.getColor()) {
			if (selectedCard.getType() != currentState.getType()) {
				// 1. Different color, not the same type
				return false;
						
			} else {
				// 2. Different color, same type, -> number card, not the same number
				if (currentState.getType() == UnoGame.TYPE_CODE_NUMBER && selectedCard.getNumber() != currentState.getNumber()) {
					return false;
				}
			}
		}
		
		return true;
	}

}
