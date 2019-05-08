package application;

import java.util.Random;

public class AI extends Player{
	
	public AI() {
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
	
	public void placeShips() {
		Random r = new Random();
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
	
	public int[] determineMove() {
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
	
	
}
