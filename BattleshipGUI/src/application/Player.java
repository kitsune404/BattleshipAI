package application;

/**
 * Player class
 * @author meganlahm
 * Handles the player's boards and moves
 */
public class Player {

	/** The board of the player*/
	protected char [][] myBoard;
	
	/** The board of the opponent */
	protected char [][] opponentBoard;
	
	/** Keeps track of sunken ships */
	protected boolean [] myHitShips; //Carrier 'C' (5), Battleship 'B' (4), Cruiser 'U' (3), Sub 'S' (3), Destroyer 'D' (2)
	
	/** The chars representing each ship */
	public static final char[] SHIPICONS = {'C','B','U','S','D'};
	
	/** The size of each ship */
	public static final int[] SHIPSIZES = {5,4,3,3,2};
	
	/** The name of each ship */
	public static final String[] SHIPNAMES = 
			{"Carrier","Battleship","Cruiser","Submarine","Destroyer"};
	
	
	/**
	 * Constructor
	 */
	public Player () {
		myBoard = new char [10][10];
		opponentBoard = new char [10][10];
		myHitShips = new boolean[5];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++){
				myBoard [i][j] = '~';
				opponentBoard [i][j] = '~';
			}
		}
	}
	
	/** Checks if coordinates are in bounds */
	public boolean isValidAttack(int posX, int posY){		
		return (posX > -1 && posX < 10 &&
				posY > -1 && posY < 10 &&
				opponentBoard[posX][posY] == '~');
	}
	
	/**
	 * check your board based on opponents move, return string based on hit or miss or sink
	 * @param x
	 * @param y
	 * @return
	 */
	public int receiveMove(int x, int y) {
		if(isHit(x, y)) {
			char shipHit = myBoard[x][y];
			setBoardTile(x,y,'x');
			for(int i = 0; i < myBoard.length; i++){
				for(int j = 0; j < myBoard.length; j++){
					if(myBoard[i][j] == shipHit){
						return 1;
					}
				}
			}
			
			for(int i = 0; i < SHIPICONS.length; i++){
				if(shipHit == SHIPICONS[i]){
					myHitShips[i] = true;
					return i + 2;
				}
			}
			return -1;
		}
		setBoardTile(x,y,'o');
		return 0;
	}
	
	/**
	 * returns if the move is a hit or not
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isHit(int x, int y) {
		//determine if move is hit or not
		if(myBoard[x][y]== '~' || myBoard[x][y]== 'x') {
			return false;
		}
		return true;
	}
	
	/**
	 * returns a string of the board passed in
	 * @param board
	 * @return
	 */
	private String printBoard(char[][] board) {
		String toPrint = "\t";
		for(int i = 0; i < board.length; i++){
			toPrint += ((char)(65 + i) + " ");
		}
		toPrint += "\n";
		for(int i = 0; i < board.length; i++) {
			toPrint += (i + 1) + "\t";
			for (int j = 0; j <board.length; j++) {
				toPrint += board[j][i] + " ";
			}
			toPrint += "\n";
		}
		return toPrint;
	}
	
	
	/**
	 * Places specific ship in the direction from the coordinate specified
	 * @param ship
	 * @param dir
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean placeShip(int ship, char dir, int x, int y){
		Character.toUpperCase(dir);
		if(dir == 'V') { //vertical
			if(x < 0 || x > 9 || y < 0 || y > 10 - SHIPSIZES[ship]){
				return false;
			}else{
				for(int i = 0; i < SHIPSIZES[ship]; i++){
					if(myBoard[x][y + i] != '~'){
						return false;
					}
				}
				for(int i = 0; i < SHIPSIZES[ship]; i++){
					myBoard[x][y + i] = SHIPICONS[ship];
				}
			}
		} else if (dir == 'H') { // horizontal
			if(y < 0 || y > 9 || x < 0 || x > 10 - SHIPSIZES[ship]){
				return false;
			}else{
				for(int i = 0; i < SHIPSIZES[ship]; i++){
					if(myBoard[x + i][y] != '~'){
						return false;
					}
				}
				for(int i = 0; i < SHIPSIZES[ship]; i++){
					myBoard[x+i][y] = SHIPICONS[ship];
				}
			}
		}
		//return true if ship is possible and placed
		return true;
	}
	
	/** Prints the player's board */
	public String printMyBoard(){
		return printBoard(myBoard);
	}
	
	/** Prints the opponent's board */
	public String printOpponentBoard(){
		return printBoard(opponentBoard);
	}
	
	/** places the specified tile on player's board */
	public void setBoardTile(int x, int y, char type)
	{
		myBoard[x][y] = type;
	}
	
	/** places the specified tile on opponent's board */
	public void setOpponentBoardTile(int x, int y, char type)
	{
		opponentBoard[x][y] = type;
	}
	
	/** returns the array of sunken ships */
	public boolean[] getMyHitShips () {
		return myHitShips;
	}
	
	/** returns the player's board */
	public char [][] getMyBoard() {
		return myBoard;
	}
	
	/** returns the opponent's board */
	public char [][] getOpponentBoard() {
		return opponentBoard;
	}
}
