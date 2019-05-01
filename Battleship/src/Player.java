
public class Player {

	/** The board of the player*/
	private String [][] myBoard;
	
	private boolean [] myHitShips;
	
	/** The board of the opponent */
	private String [][] opponentBoard;
	
	public Player () {
		//Set up boards
		myBoard = new String [10][10];
		opponentBoard = new String [10][10];
		myHitShips = new boolean[5];
		for(int i = 0; i < 10; i++) {
			myBoard [i][i] = "~";
			opponentBoard [i][i] = "~";
		}
	}
	
	public boolean[] getMyHitShips () {
		return myHitShips;
	}
	
	public String [][] getMyBoard() {
		return myBoard;
	}
	
	public String [][] getOpponentBoard() {
		return opponentBoard;
	}
}
