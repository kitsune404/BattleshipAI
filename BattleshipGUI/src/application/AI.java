package application;

import java.util.Random;

public class AI extends Player{
	
	/** Maximum Available Complexity */
	public static final int MAXCOMPLEX = 4;
	
	/** Int Value Of AI Complexity 0 = Basic, 1 = Low, 2 = Medium, 3 = High */
	private int complexity;
	
	/** Keeps Track Of Which Of The Opponent's Ships Are Sunk */
	private boolean[] opponentSunkShips;
	
	/** Keeps Track If AI KNOWS Where A Sunk Ship Was */
	private boolean[] resolvedShip;
	
	/** Coordinate Of Where Opponent Ship Was Sunk */
	private int[][] shipSunkCoords;
	
	/** Number Of Hits The AI Has Made */
	private int hitsMade;
	
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
		hitsMade = 0;
		opponentSunkShips = new boolean[5];
		shipSunkCoords = new int[5][];
		resolvedShip = new boolean[5];
		for(int i = 0; i < opponentSunkShips.length; i++) {
			opponentSunkShips[i] = false;
			resolvedShip[i] = false;
			shipSunkCoords[i] = new int[2];
			shipSunkCoords[i][0] = -1;
			shipSunkCoords[i][1] = -1;
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
		hitsMade++;
		switch(complexity) {
			case 4:
				return complexity4Move();
			case 3:
				return complexity2Move();
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
		int[] coords = findMaxValue(values);
		if(coords[0] == -1 || coords[1] == -1) {
			return getRandomSpace();
		}
		return coords;
	}
	
	/**
	 * Higher Level AI
	 * Guesses Randomly, Then Around Hit
	 * With Priority On Lines
	 * @return Coordinate Decision
	 */
	private int[] complexity2Move() {
		int[][] values = initValues();
		calculateLinearValues(values);
		int[] coords = findMaxValue(values);
		if(coords[0] == -1 || coords[1] == -1) {
			return getRandomSpace();
		}
		return coords;
	}
	
	/**
	 * Keeps Track Of Sunk Ships
	 * And Will Search For Places Where
	 * The Remaining Ships Can Be Placed
	 * As A Last Resort
	 * (Probably Best During Late-Game)
	 * @return Attack Coordinates
	 */
	private int[] complexity4Move() {
		int[][] values = initValues();
		calculateLinearValues(values);
		int[] coords = findMaxValue(values);
		
		if(coords[0] == -1 || coords[1] == -1) {
			if(hitsMade < 20) {
				return getRandomSpace();
			}
			for(int i = 0; i < opponentBoard.length; i++) {
				for(int j = 0; j < opponentBoard.length; j++) {
					if(opponentBoard[i][j] == '~') {
						for(int s = 0; s < SHIPSIZES.length; s++) {
							if(!opponentSunkShips[s]) {
								if(i + SHIPSIZES[s] < 11) {
									boolean isPlacable = true;
									for(int k = 1; k < SHIPSIZES[s]; k++) {
										if(opponentBoard[i + k][j] != '~') {
											isPlacable = false;
											break;
										}
									}
									if(isPlacable) {
										for(int k = 0; k < SHIPSIZES[s]; k++) {
											values[i + k + 1][j + 1]++;
										}
										values[i + (SHIPSIZES[s] / 2) + 1][j + 1]++;
									}
								}
								if(j + SHIPSIZES[s] < 11) {
									boolean isPlacable = true;
									for(int k = 1; k < SHIPSIZES[s]; k++) {
										if(opponentBoard[i][j + k] != '~') {
											isPlacable = false;
											break;
										}
									}
									if(isPlacable) {
										for(int k = 0; k < SHIPSIZES[s]; k++) {
											values[i + 1][j + (SHIPSIZES[s] / 2) + 1]++;
										}
										values[i + 1][j + (SHIPSIZES[s] / 2) + 1]++;
									}
								}
							}
						}
					}
				}
			}
			return findMaxValue(values);
		}
		return coords;
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
	 * Returns A Random Available Space
	 * @return
	 */
	private int[] getRandomSpace() {
		int highValue = 0;
		int[] coord = {-1,-1};
		while(highValue == 0) {// If There Is No Optimum Place, Pick A Random Place
			coord[0] = r.nextInt(opponentBoard.length);
			coord[1] = r.nextInt(opponentBoard.length);
			if(opponentBoard[coord[0]][coord[1]] == '~') {
				highValue = 1;
			}
		}
		return coord;
	}
	
	/**
	 * Finds The Most Valuable Space Of The opponentBoard
	 * @param int[][] values
	 * @return Max Value int[] Coordinate Or {-1,-1} If No Max Value
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
		return coord;
	}
	
	/**
	 * Adds Value To Spaces Based On Contiguous Lines Of Hits
	 * @param values
	 */
	private void calculateLinearValues(int[][] values) {
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
	}
	
	/**
	 * Bookkeeping Where Sunk Opponent Ships Are
	 * @param shipSunk
	 */
	public void sunkOpponentShip(int shipSunk, int posX, int posY) {
		if(complexity > 2) {
			opponentSunkShips[shipSunk] = true;
			shipSunkCoords[shipSunk][0] = posX;
			shipSunkCoords[shipSunk][1] = posY;
			resolveShip(shipSunk, posX, posY);
			if(resolvedShip[shipSunk]) {
				for(int i = 0; i < opponentSunkShips.length; i++) {
					if(opponentSunkShips[i] && !resolvedShip[i]) {
						resolveShip(i, shipSunkCoords[i][0],shipSunkCoords[i][1]);
					}
				}
			}
		}
	}
	
	/**
	 * Checks To See If An Opponent's Sunk Ship's Placement Is Known
	 * @param shipSunk
	 * @param posX
	 * @param posY
	 */
	private void resolveShip(int shipSunk, int posX, int posY) {
		int horCount = 0;
		int vertCount = 0;
		for(int i = 1; i < SHIPSIZES[shipSunk]; i++) {
			if(posX - i > -1 && opponentBoard[posX - i][posY] == 'x') {
				horCount++;
			}else {
				break;
			}
		}
		
		for(int i = 1; i < SHIPSIZES[shipSunk]; i++) {
			if(posX + i < 10 && opponentBoard[posX + i][posY] == 'x') {
				horCount++;
			}else {
				break;
			}
		}
		
		for(int i = 1; i < SHIPSIZES[shipSunk]; i++) {
			if(posY - i > -1 && opponentBoard[posX][posY - i] == 'x') {
				vertCount++;
			}else {
				break;
			}
		}
		
		for(int i = 1; i < SHIPSIZES[shipSunk]; i++) {
			if(posY + i < 10 && opponentBoard[posX][posY + i] == 'x') {
				vertCount++;
			}else {
				break;
			}
		}
		
		opponentBoard[posX][posY] = 's';
		
		if(vertCount < SHIPSIZES[shipSunk]) {
			if(horCount == SHIPSIZES[shipSunk] || posX == 9 || opponentBoard[posX + 1][posY] != 'x') {
				
				for(int i = 1; i < SHIPSIZES[shipSunk]; i++) {
					if(posX - i > -1 && opponentBoard[posX - i][posY] == 'x') {
						opponentBoard[posX - i][posY] = 's';
					}else {
						break;
					}
				}
				resolvedShip[shipSunk] = true;
			}
			if(horCount == SHIPSIZES[shipSunk] || posX == 0 || opponentBoard[posX - 1][posY] != 'x'){
				
				for(int i = 1; i < SHIPSIZES[shipSunk]; i++) {
					if(posX + i < 10 && opponentBoard[posX + i][posY] == 'x') {
						opponentBoard[posX + i][posY] = 's';
					}else {
						break;
					}
				}
				resolvedShip[shipSunk] = true;
			}
		}else if(horCount < SHIPSIZES[shipSunk]){
			if(vertCount == SHIPSIZES[shipSunk] || posY == 9 || opponentBoard[posX][posY + 1] != 'x') {
				
				for(int i = 1; i < SHIPSIZES[shipSunk]; i++) {
					if(posY - i > -1 && opponentBoard[posX][posY - i] == 'x') {
						opponentBoard[posX][posY - i] = 's';
					}else {
						break;
					}
				}
				resolvedShip[shipSunk] = true;
			}else if(vertCount == SHIPSIZES[shipSunk] || posY == 0 || opponentBoard[posX][posY - 1] != 'x'){
				
				for(int i = 1; i < SHIPSIZES[shipSunk]; i++) {
					if(posY + i < 10 && opponentBoard[posX][posY + i] == 'x') {
						opponentBoard[posX][posY + i] = 's';
					}else {
						break;
					}
				}
				resolvedShip[shipSunk] = true;
			}
		}
	}
}
