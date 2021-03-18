package src;
import java.util.ArrayList;
import java.util.Scanner;  // Import the Scanner class

/**
 * A Player has a hand of cards and methods of drawing, playing the cards.
 */
public class Player {
	/**
	 * True if the player is an AI player
	 */
	protected boolean isAI = false;
	/**
	 * The cards that the player holds, 7 cards at start.
	 */
	public ArrayList<Card> playerHand;
	/**
	 * If is not initialized, player cannot draw or play. True after constructor is called
	 */
	public boolean is_initialized = false;
	/**
	 * keeps track of the last card attempted to play
	 */
	private int lasyCardIndex = 0;
	
	/**
	 * Construct a Player. 
	 * @param Expect 7 cards in the array
	 */
	public Player(ArrayList<Card>initialCards) {
		playerHand = new ArrayList<Card>(initialCards);
		is_initialized = true;
	}
	
	/**
	 * Add a given card to the plater's hand
	 */
	public void cardToHand(Card drawedCard) {
		if (is_initialized) {
			playerHand.add(drawedCard);
		} else {
			// print error message
			System.out.println("Player is not initialized");
		}
	}
	/**
	 * Play a card from the hand, ask for which card to play
	 * @return the card that is played. Will be checked in the game class
	 */
	public Card playACard() {
		if (is_initialized) {
			// display current hand and ask for which card to use
			printHand();
			System.out.println("Please input the index of the card to play: ");
			Scanner myObj = new Scanner(System.in);
			// get the index of the desired card
			String inputIndex = myObj.nextLine();
			int indexCard = Integer.parseInt(inputIndex); 
			// return that card
			lasyCardIndex = indexCard;
			Card cardCopy = playerHand.get(indexCard);
			
			return cardCopy;
		}
		// print error message
		System.out.println("Player is not initialized");
		return new Card(0,0,0);
	}
	
	/**
	 *  helper function which removes the car that was attampted to be played
	 */
	public void removeCardFromHand() {
		playerHand.remove(lasyCardIndex);
	}
	/**
	 *  helper function which removes the card with the provided index
	 */
	public void removeCardFromHandIndex(int index) {
		playerHand.remove(index);
	}
	
	/**
	 *  helper function which prints the cards in playerHand
	 */
	public void printHand() {
		System.out.println("Here is your current hand:");
		for (int i = 0; i < playerHand.size(); i++) {
			Card currentCard = playerHand.get(i);
			//System.out.println("" + currentCard.cardColor+ currentCard.cardType+ currentCard.cardNumber);
			System.out.println(i + ": " + currentCard.cardDescription());
		}
		
	}

	
	/**
	 * @return the number of card in hand
	 */
	public int getNumCards() {
		return playerHand.size();
	}
	
	/**
	 * @return true if the player is an AI player
	 */
	public boolean getIsAI() {
		return isAI;
	}
}
