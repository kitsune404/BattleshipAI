package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Main extends Application {
	
	
	/** The top grid pane of the GUI */
	private GridPane topGP;

	/** The bottom grid pane of the GUI */
	private GridPane bottomGP;
	
	/** Array of the opponent guess board */
	private Circle [][] opponentBoardArr;
	
	/** Array of player's board */
	private Circle [][] myBoardArr;
	
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
	
	public GridPane topPane() {
		topGP = new GridPane();
		
		for(int i = 0; i < 10; i++) {
			topGP.add(new Label(" "+(char)(i+65)+ " "), i+1,0);
			for(int j = 0; j < 10; j++) {
				topGP.add(new Label(" "+(j+1)+ " "), 0,j+1);
				opponentBoardArr[i][j] = new Circle(16);
				opponentBoardArr[i][j].setFill(Color.BURLYWOOD);
				//TODO: make event handler for buttons
				topGP.add(opponentBoardArr[i][j], j+1, i+1);
			}
		}
		
		return topGP;
	}
	
	
	public GridPane bottomPane() {
		bottomGP = new GridPane();
		
		for(int i = 0; i < 10; i++) {
			bottomGP.add(new Label(" "+(char)(i+65)+ " "), i+1,0);
			for(int j = 0; j < 10; j++) {
				bottomGP.add(new Label(" "+(j+1)+ " "), 0,j+1);
				myBoardArr[i][j] = new Circle(16);
				myBoardArr[i][j].setFill(Color.DARKGREY);
				//TODO: make event handler for buttons
				bottomGP.add(myBoardArr[i][j], j+1, i+1);
			}
		}
		
		return bottomGP;
	}
	
	public void handle(ActionEvent event)  {
		//TODO: handle stuff
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
