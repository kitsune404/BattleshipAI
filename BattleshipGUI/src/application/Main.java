package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
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
			//root.setBackground();
			
			Scene scene = new Scene(root, 760, 740);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			opponentBoardArr = new Circle[10][10];
			myBoardArr = new Circle[10][10];
			GridPane title1GP = new GridPane();
			GridPane title2GP = new GridPane();
			
			//root.add(title1GP, 0, 1); //Do something for title
			root.add(topPane(), 0, 0);
			//root.add(title2GP, 0, 1); //Do something for title
			root.add(bottomPane(), 0, 2);
			
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
			for(int j = 0; j < 10; j++) {
				opponentBoardArr[i][j] = new Circle(18);
				opponentBoardArr[i][j].setFill(Color.BURLYWOOD);
				//TODO: make event handler for buttons
				topGP.add(opponentBoardArr[i][j], j, i);
			}
		}
		
		return topGP;
	}
	
	
	public GridPane bottomPane() {
		bottomGP = new GridPane();
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				myBoardArr[i][j] = new Circle(18);
				myBoardArr[i][j].setFill(Color.DARKGREY);
				//TODO: make event handler for buttons
				bottomGP.add(myBoardArr[i][j], j, i);
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
