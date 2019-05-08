package application;

import java.util.ArrayList;

/**
 * Game class
 * @author meganlahm
 * handles information about the game
 */
public class Game {
	
	/** Stores the players of the game */
	public ArrayList<Player> players;
	
	/** The winner var */
	public String winner;
	
	/** Constructor */
	public Game (boolean isAI) {
		players = new ArrayList<Player>();
		players.add(new Player());
		if(!isAI)
		{
			players.add(new Player());
		}
		else {
			//TODO: Setup AI player
		}
	}
	
	/**
	 * Determines where the ship is placed
	 * @param ship
	 * @param direction
	 * @param x
	 * @param y
	 * @param player
	 * @return
	 */
	public boolean placeShip (int ship, char direction, char x, int y, int player) {
		int[] coords = convertCoord(x,y);
		return players.get(player).placeShip(ship, direction, coords[0], coords[1]);
	}
	
	/**
	 * Check if attack is a valid move
	 * @param posX
	 * @param posY
	 * @param player
	 * @return
	 */
	public String tryAttack(char posX, int posY, int player){
		int[] coords = convertCoord(posX,posY);
		String output = "Invalid Move, Try Again";
		if(players.get(player).isValidAttack(coords[0], coords[1])){
			output = players.get((player + 1) % 2).receiveMove(coords[0], coords[1]);
			if(output.equals("miss")){
				players.get(player).setOpponentBoardTile(coords[0], coords[1], 'o');
			}else{
				players.get(player).setOpponentBoardTile(coords[0], coords[1], 'x');
			}
		}
		return output;
	}
	
	/**
	 * convert coordinates from char to int for board purposes
	 * @param x
	 * @param y
	 * @return
	 */
	public int[] convertCoord(char x, int y){
		int[] coords = {0,0};
		Character.toUpperCase(x);
		coords[0] = (int)x - 65;
		coords[1] = y - 1;
		return coords;
	}
	
	
	/**
	 * Checks for win
	 * @param one 
	 * @param two
	 * @return if a player has sunk all opponent's ships
	 */
	public boolean isWin() {
		int oneCount = 0;
		int twoCount = 0;
		for(int i = 0; i < 5; i++) {
			if(players.get(0).getMyHitShips()[i] == true)
				oneCount++;
			if(players.get(1).getMyHitShips()[i] == true)
				twoCount++;
		}
		
		if(oneCount == 5) {
			winner = "Player One";
			return true;
		}
		if(twoCount == 5) {
			winner = "Player Two";
			return true;
		}
		return false;
	}

}
