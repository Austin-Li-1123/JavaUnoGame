package src;
import java.util.ArrayList;

/**
 * An AI player which can play cards on its own. The strategic AI maximize its own advantage
 */
public class StrategicAIPlayer extends AIPlayer{
	/**
	 * Constructor of a baseline AI. Same as AI player constructor but set isAI to true
	 */
	public StrategicAIPlayer(ArrayList<Card> initialCards) {
		super(initialCards);
	}
	
	/**
	 * Strategic AI:
	 * 		1. Play lower valued cards first:
	 * 			value: wild > functional cards > number cards -> scores: [5, 4, 2]
	 * 		2. Prefer to play a playable card with a popular color(not black) in our cards -> scores: hand card count - color count
	 */
	public Card AIPlayCard(Card currentState) {
		// get a list of valid cards
		ArrayList<Card> validCards = new ArrayList<Card>();
		ArrayList<Integer> validCardsIndex = new ArrayList<Integer>();
		
		int[] colorCounts = new int[] {0,0,0,0};    // [red, yellow, green, blue, black] => [0,1,2,3,4]
		ArrayList<Integer> cardRanking = new ArrayList<Integer>();
		
		// check if a card is playable, play the valid card
		for (int i = 0; i < playerHand.size(); i++) {
			Card currentCard = playerHand.get(i);
			colorCounts[currentCard.getColor()] += 1;
			
			// validate card
			if (Card.validPlayedCard(currentState, currentCard)) {
				// add to the array lists and rank it
				validCards.add(currentCard);
				validCardsIndex.add(i);
				// rank
				if (currentCard.getColor() == 4) {
					cardRanking.add(5);
				} else if (currentCard.getType() != 0) {
					cardRanking.add(4);
				} else {
					cardRanking.add(2);
				}
				
			}
		}
		
		// if no valid card, return null and the game is deal a card
		if (validCards.size() == 0) {
			return null;
		}
		
		// If only one card is valid, return that card
		if (validCards.size() == 1) {
			removeCardFromHandIndex(validCardsIndex.get(0));
			return validCards.get(0);
		}
		
		// add ranking score according to color counts
		int handCount = playerHand.size();
		
		for (int i = 0; i < validCards.size(); i++) {
			Card currentCard = validCards.get(i);
			int updatedScore = cardRanking.get(i) + (handCount - colorCounts[currentCard.getColor()]);
			cardRanking.set(i, updatedScore);
		}
		
		// pick the card with the top score
		int topScoreIndex = 0;
		int topScore = 0;
		
		for (int i = 0; i < cardRanking.size(); i++) {
			if (cardRanking.get(i) > topScore) {
				topScore = cardRanking.get(i);
				topScoreIndex = i;
			}
		}
		removeCardFromHandIndex(validCardsIndex.get(topScoreIndex));
		return validCards.get(topScoreIndex);
		
	}
}
