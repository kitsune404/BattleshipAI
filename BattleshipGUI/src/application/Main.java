package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The main class of the GUI
 * @author meganlahm
 * @author Dylan Prince
 */
public class Main extends Application {
	
	
	/** The top grid pane of the GUI */
	private GridPane topGP;

	/** The bottom grid pane of the GUI */
	private GridPane bottomGP;
	
	/** Array of the opponent guess board */
	private Circle [][] opponentBoardArr;
	
	/** Array of player's board */
	private Circle [][] myBoardArr;
	
	/** Button to reset the game */
	private Button resetB;
	
	/** Game object */
	private Game letsPlay;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			letsPlay = new Game(true);
			
			GridPane root = new GridPane();
			root.setStyle("-fx-background-color: #557ea0;");
			
			Scene scene = new Scene(root, 760, 740);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			opponentBoardArr = new Circle[10][10];
			myBoardArr = new Circle[10][10];
			GridPane title1GP = new GridPane();
			title1GP.add(new Label("Opponent's Board:"), 0, 0);
			GridPane title2GP = new GridPane();
			title2GP.add(new Label("Your Board:"), 0, 0);
			
			root.add(title1GP, 0, 0);
			root.add(topPane(), 0, 1);
			root.add(title2GP, 0, 2);
			root.add(bottomPane(), 0, 3);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Battleship");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates the top GP of the GUI
	 * @return
	 */
	public GridPane topPane() {
		topGP = new GridPane();
		
		for(int i = 0; i < 10; i++) {
			topGP.add(new Label("  "+(char)(i+65)+ " "), i+1,0);
			topGP.add(new Label(" "+(i+1)+ " "), 0,i+1);
			for(int j = 0; j < 10; j++) {
				opponentBoardArr[i][j] = new Circle(16);
				opponentBoardArr[i][j].setFill(Color.BURLYWOOD);
				//TODO: make event handler for buttons
				topGP.add(opponentBoardArr[i][j], j+1, i+1);
			}
		}
		
		return topGP;
	}
	
	
	/**
	 * Creates the bottom pane of the GUI
	 * @return
	 */
	public GridPane bottomPane() {
		bottomGP = new GridPane();
		
		for(int i = 0; i < 10; i++) {
			bottomGP.add(new Label("  "+(char)(i+65)+ " "), i+1,0);
			bottomGP.add(new Label(" "+(i+1)+ " "), 0,i+1);
			for(int j = 0; j < 10; j++) {
				myBoardArr[i][j] = new Circle(16);
				myBoardArr[i][j].setFill(Color.DARKGREY);
				//TODO: make event handler for buttons
				bottomGP.add(myBoardArr[i][j], j+1, i+1);
			}
		}
		
		return bottomGP;
	}
	
	/**
	 * Handles when buttons are clicked
	 * @param event
	 */
	public void handle(ActionEvent event)  {
		if(event.getSource()==resetB) {
			//TODO: make new game clear moves, reset colors, etc.
			letsPlay = new Game(true);
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					myBoardArr[i][j].setFill(Color.DARKGREY);
					opponentBoardArr[i][j].setFill(Color.BURLYWOOD);
				}
			}
		}
		else {
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					if(event.getSource()==myBoardArr[i][j]) {
						//TODO: try to place a ship
					}
					if(event.getSource()==opponentBoardArr[i][j]) {
						//TODO: try to make a move
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
