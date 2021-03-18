package src;

import java.util.ArrayList;

/**
 * An AI player which can play cards on its own. Specific types of AI players extends this abstract class.
 */
public abstract class AIPlayer extends Player{
	/**
	 * Constructor of a baseline AI. Same as player constructor but set isAI to true
	 */
	public AIPlayer(ArrayList<Card> initialCards) {
		super(initialCards);
		isAI = true;
	}
	
	/**
	 * the AI player players card based on the type of AI they are
	 */
	abstract Card AIPlayCard(Card CurrentState);
	
	
}
