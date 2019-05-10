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
	
	/** AI Player */
	private AI ai;
	
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
			ai = new AI();
			players.add(ai);
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
	 * Determines where the ship is placed
	 * @param ship
	 * @param direction
	 * @param x
	 * @param y
	 * @param player
	 * @return
	 */
	public boolean placeShip (int ship, char direction, int x, int y, int player) {
		return players.get(player).placeShip(ship, direction, x, y);
	}
	
	
	
	public String receiveAIAttack() {
		int[] coords = ai.determineMove();
		String s = tryAttack(coords[0],coords[1],1);
		if(s.equals("miss")) {
			return "AI Strikes " + convertCoord(coords[0],coords[1]) + " And Misses";
		}else if(s.equals("hit")){
			return "AI Strikes " + convertCoord(coords[0],coords[1]) + " And Hits";
		}
		return "AI Strikes " + convertCoord(coords[0],coords[1]) + " And " + s;
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
	 * Try Attack With Array Indexes
	 * @param posX
	 * @param posY
	 * @param player
	 * @return
	 */
	public String tryAttack(int posX, int posY, int player){
		String output = "Invalid Move, Try Again";
		if(players.get(player).isValidAttack(posX, posY)){
			output = players.get((player + 1) % 2).receiveMove(posX, posY);
			if(output.equals("miss")){
				players.get(player).setOpponentBoardTile(posX, posY, 'o');
			}else{
				players.get(player).setOpponentBoardTile(posX, posY, 'x');
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
	 * Converts Coordinates From int To String For Printing Purposes
	 * @param x
	 * @param y
	 * @return Coordinates in String Format
	 */
	public String convertCoord(int x, int y) {
		char first = (char)(x + 65);
		return "[" + first + "," + (y + 1) + "]";
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
			winner = "Player Two Wins!";
			return true;
		}
		if(twoCount == 5) {
			winner = "Player One Wins!";
			return true;
		}
		return false;
	}

}
