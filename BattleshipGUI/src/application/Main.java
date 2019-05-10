package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * The main class of the GUI
 * @author meganlahm
 * @author Dylan Prince
 */
public class Main extends Application implements EventHandler<ActionEvent> {
	
	
	/** The top grid pane of the GUI */
	private GridPane topGP;

	/** The bottom grid pane of the GUI */
	private GridPane bottomGP;
	
	private GridPane messageGP;
	
	private GridPane shipPlaceGP;
	
	private Boolean [] activeShip;
	
	private Boolean orientation;
	
	/** Array of the opponent guess board */
	private Button [][] opponentBoardArr;
	
	/** Array of player's board */
	private Button [][] myBoardArr;
	
	/** Button to reset the game */
	private Button resetB;
	
	private Button orientationB;
	
	private Button carrierB; //five
	
	private Button battleshipB; //four
	
	private Button cruiserB; //three
	
	private Button subB; //three
	
	private Button destroyerB; //two
	
	/** Game object */
	private Game letsPlay;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			letsPlay = new Game(true);
			
			activeShip = new Boolean [5];
			orientation = false;
			
			GridPane root = new GridPane();
			root.setStyle("-fx-background-color: #557ea0;");
			
			Scene scene = new Scene(root, 760, 740);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			opponentBoardArr = new Button[10][10];
			myBoardArr = new Button[10][10];
			resetB = new Button();
			resetB.setText("New Game");
			resetB.setOnAction(this);
			GridPane title1GP = new GridPane();
			title1GP.add(new Label("Opponent's Board:"), 0, 0);
			GridPane title2GP = new GridPane();
			title2GP.add(new Label("Your Board:"), 0, 0);
			
			
			messageGP = new GridPane();
			
			root.add(title1GP, 0, 0);
			root.add(topPane(), 0, 1);
			root.add(messageGP, 1, 1);
			root.add(title2GP, 0, 2);
			root.add(bottomPane(), 0, 3);
			root.add(shipsPane(), 1, 3);
			root.add(resetB, 1,4);
			
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
				opponentBoardArr[i][j] = new Button();
				opponentBoardArr[i][j].setOnAction(this);
				opponentBoardArr[i][j].setShape(new Circle(15));
				opponentBoardArr[i][j].setStyle("-fx-background-color: #ced2db");
				
				topGP.add(opponentBoardArr[i][j], j+1, i+1);
			}
		}
		
		return topGP;
	}
	
	
	public GridPane shipsPane() {
		shipPlaceGP = new GridPane();
		orientationB = new Button("Horizontal (click to switch orientation)");
		orientationB.setOnAction(this);
		carrierB = new Button("Place Carrier (5 spaces)");
		carrierB.setOnAction(this);
		battleshipB = new Button("Place Battleship (4 spaces)");
		battleshipB.setOnAction(this);
		cruiserB = new Button("Place Cruiser (3 spaces)");
		cruiserB.setOnAction(this);
		subB = new Button("Place Submarine (3 spaces)");
		subB.setOnAction(this);
		destroyerB = new Button("Place Destroyer (2 spaces)");
		destroyerB.setOnAction(this);
		
		shipPlaceGP.add(new Label("Ships to place:"), 0, 1);
		shipPlaceGP.add(carrierB, 1, 1);
		shipPlaceGP.add(battleshipB, 1, 2);
		shipPlaceGP.add(cruiserB, 1, 3);
		shipPlaceGP.add(subB, 1, 4);
		shipPlaceGP.add(destroyerB, 1, 5);
		shipPlaceGP.add(orientationB, 1, 6);
		
		return shipPlaceGP;
	}
	
	
	/**
	 * Creates the bottom pane of the GUI
	 * @return
	 */
	public GridPane bottomPane() {
		bottomGP = new GridPane();
		bottomGP.setScaleShape(false);
		
		for(int i = 0; i < 10; i++) {
			bottomGP.add(new Label("  "+(char)(i+65)+ " "), i+1,0);
			bottomGP.add(new Label(" "+(i+1)+ " "), 0,i+1);
			for(int j = 0; j < 10; j++) {
				myBoardArr[i][j] = new Button();
				myBoardArr[i][j].setShape(new Circle(15));
				myBoardArr[i][j].setStyle("-fx-background-color: #e0d8c0");
				myBoardArr[i][j].setOnAction(this);

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
					myBoardArr[i][j].setStyle("-fx-background-color: #e0d8c0");
					opponentBoardArr[i][j].setStyle("-fx-background-color: #ced2db");
				}
			}
		}
		else if (event.getSource()==orientationB) {
			if(orientationB.getStyle()=="Horizontal (click to switch orientation)") {
				orientation = false;
				orientationB.setStyle("Vertical (click to switch orientation)");
			}
			else  {
				orientation = true;
				orientationB.setStyle("Horizontal (click to switch orientation)");
			}	
		}
		else if(event.getSource()==carrierB) {
			for(int i = 0; i < 5; i ++) {
				if(i!=0)
					activeShip[i] = false;
				else
					activeShip[i] = true;
			}
		}
		else if(event.getSource()==battleshipB) {
			for(int i = 0; i < 5; i ++) {
				if(i!=1)
					activeShip[i] = false;
				else
					activeShip[i] = true;
			}
		}
		else if(event.getSource()==cruiserB) {
			for(int i = 0; i < 5; i ++) {
				if(i!=2)
					activeShip[i] = false;
				else
					activeShip[i] = true;
			}
		}
		else if(event.getSource()==subB) {
			for(int i = 0; i < 5; i ++) {
				if(i!=3)
					activeShip[i] = false;
				else
					activeShip[i] = true;
			}
		}
		else if(event.getSource()==destroyerB) {
			for(int i = 0; i < 5; i ++) {
				if(i!=4)
					activeShip[i] = false;
				else
					activeShip[i] = true;
			}
		}
		else {
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					if(event.getSource()==myBoardArr[i][j]) {
						//TODO: If my board is clicked
						myBoardArr[i][j].setStyle("-fx-background-color: #496352");
					}
					if(event.getSource()==opponentBoardArr[i][j]) {
						//TODO: If opponent board is clicked
						opponentBoardArr[i][j].setStyle("-fx-background-color: #f44262");
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
