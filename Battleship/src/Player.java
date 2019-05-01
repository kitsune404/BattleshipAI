
public class Player {

	/** The board of the player*/
	private String [][] myBoard;
	
	/** The board of the opponent */
	private String [][] opponentBoard;
	
	public Player () {
		//Set up boards
		myBoard = new String [10][10];
		opponentBoard = new String [10][10];
		for(int i = 0; i < 10; i++)
		{
			myBoard [i][i] = "~";
			opponentBoard [i][i] = "~";
		}
	}
	
}
