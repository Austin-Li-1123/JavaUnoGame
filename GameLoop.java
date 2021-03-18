package src;

public class GameLoop {

	public static void main(String[] args) {
		// initialize a game
		int numPlayers = 3;
		UnoGame game = new UnoGame(numPlayers, false);
				
		//game loop
		while (!game.gameOver) {
			// ask current player to play a card
			System.out.println("---New turn");
			game.playerPlaysCard();
		
			game.toNextPlayer();
		}

	}

}
