
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
	
	
	public String recieveMove(int x, int y) {
		//TODO: check your board based on opponents move, return string based on hit or miss or sink
		if(isHit(x, y)) {
			//check if sink is sunk?
		}
			
		return "miss";
	}
	
	public boolean isHit(int x, int y) {
		//TODO: determine if move is hit or not
		if(myBoard[x][y]== "~" || myBoard[x][y]== "x") {
			//Do we want to change the persons own board to show where the hit was? then we can have the other player change their opponent board?
			return false;
		}
		return true;
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
