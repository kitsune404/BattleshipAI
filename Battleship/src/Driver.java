
import java.util.Scanner;
/**
 * Driver class
 * @author meganlahm
 * Handles gameplay via the Game object
 */
public class Driver {

	/** The game object */
	private static Game letsPlay;
	
	private static Scanner input;
	
	public static void main(String [] args) {
		letsPlay = new Game(false);
		input = new Scanner(System.in);
		//TODO: have both players place ships
		System.out.println("Player 1, place your ships.");
		chooseShips(0);
		
		System.out.println("Player 2, place your ships.");
		chooseShips(1);
		
		boolean turn = false;
		
		while(!letsPlay.isWin()) {
			int player = turn ? 1 : 0;
			System.out.println("Attack Board");
			System.out.println(letsPlay.players.get(player).printOpponentBoard());
			System.out.println("Defend Board");
			System.out.println(letsPlay.players.get(player).printMyBoard());
			System.out.println("Player " + (player + 1) + ", Make An Attack In The Format \"A 10\"");
			String[] command = input.nextLine().split(" ");
			char posX = command[0].charAt(0);
			int posY = Integer.parseInt(command[1]);
			String response = letsPlay.tryAttack(posX, posY, player);
			if(response.equals("Invalid Move, Try Again")){
				turn = !turn;
			}
			System.out.println(response);
			System.out.println("Hit Enter To Continue");
			input.nextLine();
			turn = !turn;
			clearScreen();
		}
	}
	
	/** Clears the screen so next player can move */
	public static void clearScreen() { 
		for(int i = 0; i < 10; i++){
			System.out.println("");
		}
	}
	
	/**
	 * Used to place ships per player
	 * @param player
	 */
	public static void chooseShips(int player){
		
		boolean isTest = true;
		if(isTest){
			for(int i = 0; i < 5; i++){
				if(!letsPlay.placeShip(i, 'V', (char)(65 + i), 1, player)){
					System.out.println("You Messed Up, Fam");
				}
			}
		}else{
			for(int i = 0; i < 5; i++){
				System.out.println(letsPlay.players.get(player).printMyBoard());
				System.out.println("Input Position In The Form Of \"A 10 V\" or \"D 5 H\"");
				System.out.println("H = Horizontal and V = Vertical With \"A 10\" Being The Top Or Left Of The Ship");
				System.out.println("Where To Place:" + Player.SHIPNAMES[i] + " Of Size: " + Player.SHIPSIZES[i]);
				String[] command = input.nextLine().split(" ");
				char posX = command[0].charAt(0);
				int posY = Integer.parseInt(command[1]);
				char dir = command[2].charAt(0);
				if(letsPlay.placeShip(i, dir, posX, posY, player)){
					System.out.println(Player.SHIPNAMES[i] + " Placed At: " + posX + "," + posY);
					letsPlay.players.get(player).printMyBoard();
				}else{
					System.out.println("Invalid Placement Of Ship");
					i--;
				}
				clearScreen();
			}
		}
		
	}
	
}
