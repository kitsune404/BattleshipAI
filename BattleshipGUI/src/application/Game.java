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
			ai = new AI(1);
			players.add(ai);
		}
	}
	
	/** Constructor */
	public Game (int AIType) {
		players = new ArrayList<Player>();
		players.add(new Player());
		ai = new AI(AIType);
		players.add(ai);
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
	
	
	/**
	 * Determines the text of the move to be sent to the controller
	 * @return
	 */
	public String receiveAIAttack() {
		int[] coords = ai.determineMove();
		int attackType = tryAttack(coords[0],coords[1],1);
		switch(attackType) {
			case -1: System.out.println(coords[0] + "," + coords[1]); return "!?";
			case 0: return "AI Strikes " + convertCoord(coords[0],coords[1]) + " And Misses";
			case 1: return "AI Strikes " + convertCoord(coords[0],coords[1]) + " And Hits";
			default: ai.sunkOpponentShip(attackType - 2,coords[0],coords[1]);
				return "AI Strikes " + convertCoord(coords[0],coords[1]) + " And " + Player.SHIPNAMES[attackType - 2] + " Sunk";
		}
	}
	
	/**
	 * Determines The Text Of The Move To Be Sent to The Controller
	 * @param x
	 * @param y
	 * @return
	 */
	public String receivePlayerAttack(int x, int y) {
		int attackType = tryAttack(x,y,0);
		switch(attackType) {
			case -1: return "!?";
			case 0: return "Miss";
			case 1: return "Hit";
			default: return "Opponent " + Player.SHIPNAMES[attackType - 2] + " Sunk";
		}
	}
	
	
	/**
	 * Check if attack is a valid move
	 * @param posX
	 * @param posY
	 * @param player
	 * @return
	 */
	public int tryAttack(char posX, int posY, int player){
		int[] coords = convertCoord(posX,posY);
		int attackType = -1;
		if(players.get(player).isValidAttack(coords[0], coords[1])){
			attackType = players.get((player + 1) % 2).receiveMove(coords[0], coords[1]);
			if(attackType == 0){
				players.get(player).setOpponentBoardTile(coords[0], coords[1], 'o');
			}else{
				players.get(player).setOpponentBoardTile(coords[0], coords[1], 'x');
			}
		}
		return attackType;
	}
	
	
	
	/**
	 * Try Attack With Array Indexes
	 * @param posX
	 * @param posY
	 * @param player
	 * @return
	 */
	public int tryAttack(int posX, int posY, int player){
		int attackType = -1;
		if(players.get(player).isValidAttack(posX, posY)){
			attackType = players.get((player + 1) % 2).receiveMove(posX, posY);
			if(attackType == 0){
				players.get(player).setOpponentBoardTile(posX, posY, 'o');
			}else{
				players.get(player).setOpponentBoardTile(posX, posY, 'x');
			}
		}
		return attackType;
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