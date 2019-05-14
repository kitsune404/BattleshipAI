package application;

import java.util.Random;

public class AI extends Player{
	
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
			case 1:
				return complexity1Move();
			default:
				return complexity0Move();
		}
	}
	
	/**
	 * Simplest AI Complexity
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
	 * @return Coordinate Decision
	 */
	private int[] complexity1Move() {
		int[][] values = new int[myBoard.length + 2][myBoard.length + 2];
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values.length; j++) {
				values[i][j] = 0;
			}
		}
		for(int i = 0; i < opponentBoard.length; i++) {
			for(int j = 0; j < opponentBoard.length; j++) {
				if(opponentBoard[i][j] == 'x') {
					values[i][j+1]++;
					values[i+1][j]++;
					values[i+2][j+1]++;
					values[i+1][j+2]++;
				}
			}
		}
		int highValue = -1;
		int highI = -1;
		int highJ = -1;
		for(int i = 1; i < values.length - 1; i++) {
			for(int j = 1; j < values.length - 1; j++) {
				if(highValue < values[i][j] && opponentBoard[i-1][j-1] == '~') {
					highValue = values[i][j];
					highI = i - 1;
					highJ = j - 1;
				}
			}
		}
		while(highValue == 0) {// If There Is No Optimal Place, Pick A Random Place
			highI = r.nextInt(opponentBoard.length);
			highJ = r.nextInt(opponentBoard.length);
			if(opponentBoard[highI][highJ] == '~') {
				highValue = 1;
			}
		}
		int[] coords = {highI, highJ};
		return coords;
	}
}
