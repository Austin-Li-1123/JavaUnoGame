package src;
import java.util.*; //shuffle
import java.util.Scanner;

/**
 *  The game state of the uno game. Keeps track of info such as drawing stack, discard stack, players, direction, etc.
 */
public class UnoGame {
	
	/**
	 * The card that the next played card is expected to match color/number/symbol
	 */
	private Card currentStateCard;
	/**
	 * The card that the next played card is expected to match color/number/symbol
	 */
	private int currentColor;
	/**
	 * The card that the next played card is expected to match color/number/symbol
	 */
	private int currentType;
	/**
	 * The card that the next played card is expected to match color/number/symbol
	 */
	private int currentNumber;
	/**
	 * All the players in the game
	 */
	public ArrayList<Player> gamePlayers = new ArrayList<Player>();;
	/**
	 * The player who is playing their turn currently
	 */
	private Player currentPlayer;
	/**
	 * The player who is playing their turn currently
	 */
	private int currentPlayerIndex = 0;
	/**
	 * The number of players in this game
	 */
	private int playerCount;
	/**
	 * The direction which the players take turns. [0, 1] => [normal, reversed]
	 */
	public boolean gameDirection;
	/**
	 * The cards available for drawing. Initialized in constructor
	 */
	public ArrayList<Card> drawPile;
	/**
	 * The cards already played. Empty at start
	 */
	public ArrayList<Card> discardPile;
	/**
	 * Number of cards that are stacked by the "draw two" card and "wild draw four" cards
	 */
	public int numCardsWildDraw = 0;
	/**
	 * if the game is over
	 */
	public boolean gameOver = false;
	/**
	 * Macros which represent the code for the type of cards
	 */
	public static final int TYPE_CODE_NUMBER = 0;
	public static final int TYPE_CODE_SKIP = 1;
	public static final int TYPE_CODE_REVERSE = 2;
	public static final int TYPE_CODE_PLUS2 = 3;
	public static final int TYPE_CODE_WILD = 4;
	public static final int TYPE_CODE_PLUS4 = 5;
	/**
	 * The number of cards each player gets when the game starts
	 */
	public static final int NUM_CARDS_PER_PLAYER = 7;
	
	
	/**
	 * Construct a Player. 
	 * @param Expect 7 cards in the array
	 */
	public UnoGame(int numPlayers, boolean isReversedDirection) {
		playerCount = numPlayers;
		gameDirection = !isReversedDirection;
		// Initializing the draw pile with the 108 cards above
		drawPile = initializeCards();
		discardPile = new ArrayList<Card>();
		// randomize the pile
		shuffleCards(drawPile);
		// draw one card as the initial card and remove the first card
		Card firstCard = drawTopCard(drawPile);
		discardPile.add(firstCard);
		// initialize the initial state
		upadteCurrentState(firstCard);
		
		// Assigning cards to each player and initialize players - 7 each.  Assuming 108 cards are enough for number of players
		initializePlayersWithSevenCards(drawPile, numPlayers, NUM_CARDS_PER_PLAYER);
		
		currentPlayer = gamePlayers.get(0);
	}
	
	/**
	 * Update the current game state according to the input card
	 * 
	 */
	public void upadteCurrentState(Card inputCard) {
		currentColor = inputCard.getColor();
		currentType = inputCard.getType();
		currentNumber = inputCard.getNumber();
		currentStateCard = inputCard;
	}
	
	/**
	 * Initialize the 108 cards according to UNO's game rule
	 * 
	 */
	public static ArrayList<Card> initializeCards() {
		ArrayList<Card> initialCards = new ArrayList<Card>();
		// append colors [red, yellow, green, blue, (black)] => [0,1,2,3,(4)]
		for (int colorCode = 0; colorCode < 4; colorCode++) {
			// number cards: 1 * (0), 2 * (1-9)
			for (int i = 0; i < 10; i++) {
				//only one 0-card
				if (i == 0) {
					initialCards.add(new Card(colorCode, TYPE_CODE_NUMBER, i));   //Card(int color, int type, int number)
				} else {
					// append 2 cards of this non-0 number
					initialCards.add(new Card(colorCode, TYPE_CODE_NUMBER, i));
					initialCards.add(new Card(colorCode, TYPE_CODE_NUMBER, i));
				}
			}
			
			//  add special symbol cards
			for (int i = TYPE_CODE_SKIP; i <= TYPE_CODE_PLUS2; i++) {
				// 2 * special symbol cards
				initialCards.add(new Card(colorCode, i, 0)); // third field does not matter here
				initialCards.add(new Card(colorCode, i, 0)); // third field does not matter here
			}
		}
		
		// add wild cards
		for (int i = TYPE_CODE_WILD; i <= TYPE_CODE_PLUS4; i++) {
			// 4 * special symbol cards
			for (int j = 0; j < 4; j++) {
				initialCards.add(new Card(4, i, 0)); // third field does not matter here
			}
			
		}
		
		return initialCards;
	}
	
	/**
	 * randomize a pile of cards
	 * 
	 */
	public static void shuffleCards(ArrayList<Card> inputStack) {
		Collections.shuffle(inputStack); 
	}
	
	/**
	 *  Draw the top card in this deck and remove it
	 * @return the top card in this deck, null if deck is empty
	 * 
	 */
	public static Card drawTopCard(ArrayList<Card> inputStack) {
		if (inputStack.size() == 0) {
			return null;
		} else {
			// return the first element and remove it
			Card drawedCard = inputStack.get(0);
			inputStack.remove(0);
			
			return drawedCard;
		}
	}
	
	/**
	 * Assigning cards to each player and initialize players - 7 each.  
	 * Assuming 108 cards are enough for number of players
	 * 
	 */
	public void initializePlayersWithSevenCards(ArrayList<Card> inputStack, int numPlayers, int NUM_CARDS_PER_PLAYER) {
		for (int i = 0; i < numPlayers; i++) {
			// draw 7 cards
			ArrayList<Card> playerInitialCards = new ArrayList<Card>();
			for (int j = 0; j < NUM_CARDS_PER_PLAYER; j++) {
				playerInitialCards.add(drawTopCard(inputStack));
			}
			// initialize player
			Player newPlayer = new Player(playerInitialCards);
			// add player to this game
			gamePlayers.add(newPlayer);
		}
	}
	/**
	 * returns the number of player in this game
	 * 
	 */
	public int getPlayerCount() {
		return gamePlayers.size();
	}
	/**
	 * returns a certain player
	 * 
	 */
	public Player getPlayerK(int indexK) {
		return gamePlayers.get(indexK);
	}
	/**
	 * returns the number of cards in the drawing pile
	 * 
	 */
	public int getDrawingPileSize() {
		return drawPile.size();
	}
	/**
	 * returns a copy to the current state card
	 * 
	 */
	public Card getCurrentStateCard() {
		return currentStateCard.copy();
	}

	
	/**
	 * Draw a card from the drawing pile. 
	 * Updating the draw pile when the cards are drawn, and reusing discard pile when no card is left in the draw pile 
	 * 
	 */
	public Card drawFromPile() {
		//check if empty
		if (getDrawingPileSize() == 0) {
			// reuse the discard pile
			reuseDiscardPile();
		}
		Card drawnCard = drawTopCard(drawPile); // remove top card is done here
		//give this card to the current player
		currentPlayer.cardToHand(drawnCard);
		
		return drawnCard;
	}
	
	
	/**
	 * Take all cards in the discard pile, shuffle, insert to drawing pile, empty discard pile
	 * 
	 */
	public void reuseDiscardPile() {
		// shuffle the discard pile
		ArrayList<Card> cards = discardPile;
		shuffleCards(cards);
		//insert into drawing pile
		for (int i = 0; i < cards.size(); i++) {
			drawPile.add(cards.get(i));
		}
		//empty discard pile
		for (int i = 0; i < cards.size(); i++) {
			drawPile.remove(i);
		}
	}
	
	/**
	 * Let the player play a card, check if that card is valid, card to discard pile, update current state
	 * 
	 */
	public void playerPlaysCard() {
		//Let the player play a card if it is valid
		Card playedCard = null;
		while (!validPlayedCard(getCurrentState(), playedCard)) {
			// show the player current game state
			System.out.println("The last card played is '"+ currentStateCard.cardDescription() + "'");
			currentPlayer.printHand();
			// ask if the player wishes to draw a card
			System.out.println("Do you wish to draw a card? (Y/N)");
			Scanner myObj = new Scanner(System.in);
			String inputIndex = myObj.nextLine();
			if (inputIndex.equals("Y")) {
				playerWantsToDrawCard();
				return;
			} 
			playedCard = currentPlayer.playACard();
		}
		// upon valid card, remove card from player's hand
		currentPlayer.removeCardFromHand();
		checkGameOver();
		
		// check for cards with special abilities
		checkForCpecialCards(playedCard);
		
		// card to discard pile
		discardPile.add(playedCard);
		
		// update current state
		upadteCurrentState(playedCard);
		
		
	}
	
	/**
	 * If the card is a reverse, skip, +2, wild, +4, call the methods that perform this card's ability
	 * [number, skip, reverse, plus2, wild, plus4] => [0,1,2,3,4,5]
	 * 
	 */
	public void checkForCpecialCards(Card inputCard) {	
		if (inputCard.getType() == TYPE_CODE_SKIP) {
			skipCardPlayed();
		} else if( inputCard.getType() == TYPE_CODE_REVERSE) {
			reverseCardPlayed();
		} else if( inputCard.getType() == TYPE_CODE_PLUS2) {
			plusTwoCardPlayed();
		} else if( inputCard.getType() == TYPE_CODE_WILD) {
			wildCardPlayed(inputCard);
		} else if( inputCard.getType() == TYPE_CODE_PLUS4) {
			plusFourCardPlayed(inputCard);
		}
	}
	
	/**
	 * If a player draws a card from the draw stack 
	 * (eg. when the player choose to play no card but there is no penalty), they should play it if it is valid. Otherwise, they should skip this turn 
	 * 
	 */
	public void playerWantsToDrawCard() {
		Card drawnCard = drawFromPile();
		// if card is valid -> have to play
		System.out.println("  The card you draw is: '" + drawnCard.cardDescription() + "'.");
		if (validPlayedCard(getCurrentState(), drawnCard)) {
			System.out.println("  This card is valid. The system has played it for you.");
			// play the card: update discard pile, current state, player hand
			discardPile.add(drawnCard);
			// check for cards with special abilities
			checkForCpecialCards(drawnCard);
			upadteCurrentState(drawnCard);
			// remove last card
			currentPlayer.removeCardFromHandIndex(currentPlayer.getNumCards() - 1);
			
		} else {
			// else -> skip
			System.out.println("  This card is invalid. The game will move on to the next player");
		}
		
	}
	/**
	 * Checks if the selected card is valid for the current state
	 * There are only two invalid cases: 
	 * 		1. Different color, not the same type
	 * 		2. Different color, same type, number card, not the same number
	 * 
	 * @return true for valid play
	 */
	public static boolean validPlayedCard(int[] currentState, Card selectedCard) {
		//if null -> return false . This happens at the beginning of the game
		if (selectedCard == null) {
			return false;
		}
		// if input card is wild or wild+4, it is always valid
		if (selectedCard.getType() == TYPE_CODE_WILD || selectedCard.getType() == TYPE_CODE_PLUS4) {
			return true;
		}
		// check if color are different
		System.out.println("  You chose '" + selectedCard.cardDescription() + "'.");
		if (selectedCard.getColor() != currentState[0]) {
			if (selectedCard.getType() != currentState[1]) {
				// 1. Different color, not the same type
				System.out.println("  This card is invalid. Please play another card.");
				return false;
						
			} else {
				// 2. Different color, same type, -> number card, not the same number
				if (currentState[1] == TYPE_CODE_NUMBER && selectedCard.getNumber() != currentState[2]) {
					System.out.println("  This number card is invalid. Please play another card.");
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * @return an int[] containing the current game state
	 * 
	 */
	public int[] getCurrentState() {
		return new int[] {currentColor, currentType, currentNumber};
	}
	
	/**
	 * move on to the next player, depends on the game direction
	 * 
	 */
	public void toNextPlayer() {
		if (gameDirection == true) {
			currentPlayerIndex = (currentPlayerIndex + 1) % playerCount;
		} else {
			currentPlayerIndex = (currentPlayerIndex - 1) % playerCount;
		}
		currentPlayer = getPlayerK(currentPlayerIndex);
	}
	
	/**
	 * called when a reverse card is being played
	 * effect: 
	 * 		1. change the game direction
	 * 
	 */
	public void reverseCardPlayed() {
		gameDirection = !gameDirection;
	}
	
	/**
	 * called when a skip card is being played
	 * effect: 
	 * 		1. skip the next player
	 * 
	 */
	public void skipCardPlayed() {
		toNextPlayer();
	}
	
	/**
	 * called when a +2 card is being played
	 * effect: 
	 * 		1. add two cards to the next player's hand
	 * 		2. skip the next player as well
	 * 
	 */
	public void plusTwoCardPlayed() {
		toNextPlayer();
		for (int i = 0; i < 2; i++) {
			drawFromPile();
		}
	}
	
	/**
	 * called when a wild card is being played
	 * effect: 
	 * 		1. the player can choose a color 
	 * 		2. update the current state to match that chosen color
	 * 
	 */
	public void wildCardPlayed(Card currCard) {
		//the player can choose a color 
		System.out.println("  You have played a wild card! Choose a color: (0/1/2/3)");
		System.out.println("  0: red; 1: yellow; 2: green; 3:blue");
		Scanner myObj = new Scanner(System.in);
		String inputIndex = myObj.nextLine();
		int colorIndex = Integer.parseInt(inputIndex); 
		// update the current state to match that chosen color
		currCard.cardColor = colorIndex;
	}
	
	/**
	 * called when a +4 card is being played
	 * effect: 
	 * 		1. add 4 cards to the next player's hand
	 * 		2. skip the next player as well
	 * 		3. update the current state to match that chosen color
	 * 
	 */
	public void plusFourCardPlayed(Card currCard) {
		toNextPlayer();
		for (int i = 0; i < 4; i++) {
			drawFromPile();
		}
		wildCardPlayed(currCard);
	}
	
	/**
	 * called every time a player deals a card. Checks if that player has empty hand 
	 * 
	 */
	public void checkGameOver() {
		if (currentPlayer.getNumCards() == 0) {
			// game over
			gameOver = true;
		}
	}
	


}
