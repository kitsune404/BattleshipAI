
public class Game {

	public Player playerOne;

	public Player playerTwo;

	public String winner;

	public Game (boolean isAI) {
		playerOne = new Player();
		//TODO: Setup board for player 1
		if(!isAI)
		{
			playerTwo = new Player();
			//TODO: Setup board for each player 2
		}
		else {
			//TODO: Setup AI board
		}
		while(!isWin(playerOne, playerTwo))
		{
			//TODO: determine gameplay, most likely enter coordinates
		}
	}

	/**
	 * Checks for win
	 * @param one 
	 * @param two
	 * @return if a player has sunk all opponent's ships
	 */
	public boolean isWin(Player one, Player two) {
		int oneCount = 0;
		int twoCount = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(one.getOpponentBoard()[i][j] == "x")
					oneCount++;
				if(two.getOpponentBoard()[i][j] == "x")
					twoCount++;
			}
		}
		
		if(oneCount == 15) { //TODO: change number to actual sum of all hits
			winner = "Player One";
			return true;
		}
		if(twoCount == 15) { //TODO: change number to actual sum of all hits
			winner = "Player Two";
			return true;
		}
		return false;
	}

}
