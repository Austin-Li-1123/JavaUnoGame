package src;
import java.util.ArrayList;

/**
 * An AI player which can play cards on its own. The baseline AI simply plays any card that is valid
 */
public class BaselineAIPlayer extends AIPlayer{
	/**
	 * Constructor of a baseline AI. Same as AI player constructor but set isAI to true
	 */
	public BaselineAIPlayer(ArrayList<Card> initialCards) {
		super(initialCards);
	}
	
	/**
	 * Baseline AI: Can automatically play a random(if there is more than one) legal cards for each round and draw cards when no legal cards present.
	 */
	public Card AIPlayCard(Card currentState) {
		// check if a card is playable, play the valid card
		for (int i = 0; i < playerHand.size(); i++) {
			Card currentCard = playerHand.get(i);
			// validate card
			if (Card.validPlayedCard(currentState, currentCard)) {
				// play this card and return
				removeCardFromHandIndex(i);
				return currentCard;
			}
		}
		
		// if no card is valid, return null. The game will deal a card
		return null;
	}
	
}
