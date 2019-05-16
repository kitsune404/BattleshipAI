package application;

import java.util.Random;

public class AI extends Player{
	
	public static final int MAXCOMPLEX = 2;
	
	/** Int Value Of AI Complexity 0 = Low, 1 = Med, 2 = High */
	private int complexity;
	
	/** RNG That We Will Use */
	private Random r;
	
	/**
	 * Constructor
	 * @param AIComplexity 0 = Simple, 1 = Medium, 2 = High
	 */
	public AI(int AIComplexity) {
		complexity = AIComplexity;
		r = new Random();
		myBoard = new char [10][10];
		opponentBoard = new char [10][10];
		myHitShips = new boolean[5];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++){
				myBoard [i][j] = '~';
				opponentBoard [i][j] = '~';
			}
		}
		placeShips();
	}
	
	/**
	 * Places AI Ships
	 */
	public void placeShips() {
		int shipsPlaced = 0;
		while(shipsPlaced < 5) {
			if(r.nextBoolean()) {//If Horizontal
				int x = r.nextInt(10 - Player.SHIPSIZES[shipsPlaced]);
				int y = r.nextInt(10);
				if(placeShip(shipsPlaced, 'H', x, y)) {
					shipsPlaced++;
				}
			}else{//If Vertical
				int x = r.nextInt(10);
				int y = r.nextInt(10 - Player.SHIPSIZES[shipsPlaced]);
				if(placeShip(shipsPlaced, 'V', x, y)) {
					shipsPlaced++;
				}
			}
		}
		
	}
	
	/**
	 * Determines Complexity Of Decision-Making And Returns Decision
	 * @return
	 */
	public int[] determineMove() {
		switch(complexity) {
			case 2:
				return complexity2Move();
			case 1:
				return complexity1Move();
			default:
				return complexity0Move();
		}
	}
	
	/**
	 * Simplest AI Complexity
	 * Guesses Sequentially
	 * @return Coordinate Decision
	 */
	private int[] complexity0Move() {
		for(int i = 0; i < opponentBoard.length; i++) {
			for(int j = 0; j < opponentBoard.length; j++) {
				if(opponentBoard[i][j] == '~') {
					int[] coord = {i,j};
					return coord;
				}
			}
		}
		
		int[] coord = {-1,-1};
		return coord;
	}
	
	/**
	 * Medium Level Complexity
	 * Guesses Randomly, Then Guesses Around Hit
	 * @return Coordinate Decision
	 */
	private int[] complexity1Move() {
		int[][] values = initValues();
		for(int i = 0; i < opponentBoard.length; i++) {
			for(int j = 0; j < opponentBoard.length; j++) {
				if(opponentBoard[i][j] == 'x') {
					values[i][j+1]++; //Top
					values[i+1][j]++; //Left
					values[i+2][j+1]++;//Bottom
					values[i+1][j+2]++;//Right
				}
			}
		}
		return findMaxValue(values);
	}
	
	/**
	 * Higher Level AI
	 * Guesses Randomly, Then Around Hit
	 * With Priority On Lines
	 * @return Coordinate Decision
	 */
	private int[] complexity2Move() {
		int[][] values = initValues();
		for(int i = 0; i < opponentBoard.length; i++) {
			for(int j = 0; j < opponentBoard.length; j++) {
				if(opponentBoard[i][j] == 'x') {
					//Going Right
					int k = 1;
					while(j + k < 10 && opponentBoard[i][j + k] == 'x') {
						k++;
					}
					values[i+1][j+k+1]++;
					//Going Left
					k = 1;
					while(j - k > -1 && opponentBoard[i][j - k] == 'x') {
						k++;
					}
					values[i+1][j-k+1]++;
					//Going Up
					k = 1;
					while(i - k > -1 && opponentBoard[i - k][j] == 'x') {
						k++;
					}
					values[i-k+1][j+1]++;
					//Going Down
					k = 1;
					while(i + k < 10 && opponentBoard[i + k][j] == 'x') {
						k++;
					}
					values[i+k+1][j+1]++;
				}
			}
		}
		return findMaxValue(values);
	}
	
	/**
	 * Initialize Value Double Array
	 * @return int[][] values
	 */
	private int[][] initValues() {
		int[][] values = new int[myBoard.length + 2][myBoard.length + 2];
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values.length; j++) {
				values[i][j] = 0;
			}
		}
		return values;
	}
	
	/**
	 * Finds The Maximum Value Of A Double Array
	 * @param int[][] values
	 * @return int[] coordinate
	 */
	private int[] findMaxValue(int[][] values) {
		int highValue = 0;
		int[] coord = {-1, -1};
		for(int i = 1; i < values.length - 1; i++) {
			for(int j = 1; j < values.length - 1; j++) {
				if(highValue < values[i][j] && opponentBoard[i-1][j-1] == '~') {
					highValue = values[i][j];
					coord[0] = i - 1;
					coord[1] = j - 1;
				}
			}
		}
		while(highValue == 0) {// If There Is No Optimum Place, Pick A Random Place
			coord[0] = r.nextInt(opponentBoard.length);
			coord[1] = r.nextInt(opponentBoard.length);
			if(opponentBoard[coord[0]][coord[1]] == '~') {
				highValue = 1;
			}
		}
		return coord;
	}
}
