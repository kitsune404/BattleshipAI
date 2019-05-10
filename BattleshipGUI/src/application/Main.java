package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

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
	

	/** GridPane For The Menu Buttons */
	private GridPane mPane;
	
	/** Displays If Current Direction Is Horizontal */
	private Label hLabel;

	
	/** Array of the opponent guess board */
	private Button [][] opponentBoardArr;
	
	/** Array of player's board */
	private Button [][] myBoardArr;
	
	/** Button to reset the game */
	private Button resetB;
	
	/** Buttons Corresponding To The Ships */
	private Button[] shipB;
	
	/** Button Corresponding To Current Ship Direction */
	private Button dirB;
	
	/** Boolean If Ship Placement Is Horizontal*/
	private boolean isHorizontal;
	
	/** Boolean Stating If Ship Placement Is Currently Active */
	private boolean isPlacing;
	
	/** Content Of Feedback Pane */
	private String feedback;
	
	/** Displays Feedback From Game */
	private Text feedPane;
	
	/** Current Selected Ship */
	private int currentShip;
	
	/** Game object */
	private Game letsPlay;
	
	private final int SCENEWIDTH = 850;
	
	private final int SCENEHEIGHT = 800;
	
	@Override
	public void start(Stage primaryStage) {
		try {

			startGame();
			letsPlay = new Game(true);
			
			GridPane root = new GridPane();
			root.setStyle("-fx-background-color: #557ea0;");
			
			Scene scene = new Scene(root, SCENEWIDTH, SCENEHEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			opponentBoardArr = new Button[10][10];
			myBoardArr = new Button[10][10];
			GridPane title1GP = new GridPane();
			title1GP.add(new Label("Opponent's Board:"), 0, 0);
			GridPane title2GP = new GridPane();
			title2GP.add(new Label("Your Board:"), 0, 0);
			
			
			root.add(title1GP, 0, 0);
			root.add(topPane(), 0, 1);
			root.add(title2GP, 0, 2);
			root.add(bottomPane(), 0, 3);
			root.add(new Label("Game Feedback"), 1, 0);
			root.add(textPane(), 1, 1);
			root.add(new Label("Menu"), 1, 2);
			root.add(menuPane(), 1, 3);

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
	private GridPane topPane() {
		topGP = new GridPane();
		topGP.setMinSize((SCENEWIDTH/2), (SCENEHEIGHT/2)-20);
		for(int i = 0; i < 10; i++) {
			topGP.add(new Label("  "+(char)(i+65)+ " "), i+1,0);
			topGP.add(new Label(" "+(i+1)+ " "), 0,i+1);
			for(int j = 0; j < 10; j++) {
				opponentBoardArr[i][j] = new Button();
				opponentBoardArr[i][j].setOnAction(this);
				opponentBoardArr[i][j].setShape(new Circle(10));
				opponentBoardArr[i][j].setStyle("-fx-background-color: #ced2db");
				opponentBoardArr[i][j].setAlignment(Pos.CENTER);
				opponentBoardArr[i][j].setScaleShape(false);
				opponentBoardArr[i][j].setDisable(true);
				opponentBoardArr[i][j].setText(" ");
				topGP.add(opponentBoardArr[i][j], i + 1, j + 1);
			}
		}
		
		return topGP;
	}
	
	
	/**
	 * Creates the bottom pane of the GUI
	 * @return
	 */
	private GridPane bottomPane() {
		bottomGP = new GridPane();
		bottomGP.setScaleShape(false);
		bottomGP.setMinSize((SCENEWIDTH/2), (SCENEHEIGHT/2)-20);
		for(int i = 0; i < 10; i++) {
			bottomGP.add(new Label("  "+(char)(i+65)+ " "), i+1,0);
			bottomGP.add(new Label(" "+(i+1)+ " "), 0,i+1);
			for(int j = 0; j < 10; j++) {
				myBoardArr[i][j] = new Button();
				myBoardArr[i][j].setShape(new Circle(10));
				myBoardArr[i][j].setScaleShape(false);
				myBoardArr[i][j].setStyle("-fx-background-color: #e0d8c0");
				myBoardArr[i][j].setOnAction(this);
				myBoardArr[i][j].setAlignment(Pos.CENTER);
				myBoardArr[i][j].setText(" ");
				bottomGP.add(myBoardArr[i][j], i + 1, j + 1);
				
			}
		}
		
		return bottomGP;
	}

	private void resetText() {
		feedback = "Welcome To Battleship!\n"
				+ "Select Your Ships And Place Them On The Lower Board In A Horizontal Or Vertical Position\n"
				+ "The Selected Space Will Serve As The Leftmost And Topmost Position, Respectively";
	}
	
	private void setText(String s) {
		feedback += s + "\n";
		feedPane.setText(feedback);
	}
	
	private ScrollPane textPane() {
		feedPane = new Text();
		resetText();
		setText("");
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(feedPane);
		return scroll;
	}
	
	private GridPane menuPane() {		
		mPane = new GridPane();
		
		resetB = new Button();
		resetB.setText("New Game");
		resetB.setOnAction(this);
		mPane.add(resetB, 0, 0);
		dirB = new Button();
		dirB.setText("Change Current Direction:");
		dirB.setOnAction(this);
		mPane.add(dirB, 0, 1);
		hLabel = new Label("Horizontal");
		mPane.add(hLabel, 1, 1);
		shipB = new Button[Player.SHIPNAMES.length];
		for(int i = 0; i < shipB.length; i++) {
			shipB[i] = new Button();
			shipB[i].setText("Place " + Player.SHIPNAMES[i]);
			shipB[i].setOnAction(this);
			mPane.add(shipB[i], 0, (i + 2));
			mPane.add(new Label("Size: " + Player.SHIPSIZES[i]), 1, (i + 2));
		}
		return mPane;
	}
	
	/**
	 * Inverts The isHorizontal Boolean And Adjusts
	 * The Label To Reflect This Change
	 */
	private void changeDirection() {
		
		isHorizontal = !isHorizontal;
		if(isHorizontal) {
			hLabel.setText("Horizontal");
		}else {
			hLabel.setText("Vertical");
		}
	}
	
	
	/**
	 * Handles when buttons are clicked
	 * @param event
	 */
	public void handle(ActionEvent event)  {
		if(event.getSource()==resetB) {
			startGame();
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					myBoardArr[i][j].setStyle("-fx-background-color: #e0d8c0");
					myBoardArr[i][j].setDisable(false);
					opponentBoardArr[i][j].setStyle("-fx-background-color: #ced2db");
					opponentBoardArr[i][j].setDisable(true);
				}
			}
			for(int i = 0; i < shipB.length; i++) {
				shipB[i].setDisable(false);
			}
		}else if(event.getSource()==dirB) {
			
			changeDirection();
		}
		else {
			if(isPlacing) {
				for(int i = 0; i < shipB.length; i++) {
					if(event.getSource()== shipB[i]) {
						currentShip = i;
						return;
					}
				}
				for(int i = 0; i < 10; i++) {
					for(int j = 0; j < 10; j++) {
						if(event.getSource()==myBoardArr[i][j]) {
							if(currentShip > -1) {
								boolean isBoardChanged = false;
								char dirChar = 'V';
								if(isHorizontal) {
									dirChar = 'H';
								}
								if(letsPlay.placeShip(currentShip, dirChar, i, j, 0)) {
									setText(Player.SHIPNAMES[currentShip] + " Placed");
									isBoardChanged = true;
									shipB[currentShip].setDisable(true);
									currentShip = -1;
								}else {
									setText("Invalid Placement For Ship");
								}
								if(isBoardChanged) {
									updateBottomBoard();
									int placedShips = 0;
									for(int k = 0; k < shipB.length; k++) {
										if(shipB[k].isDisable()) {
											placedShips++;
										}
									}
									if(placedShips == 5) {
										switchToAttack();
									}
								}
								
							}
							break;
						}
					}
				}
			}else {
				for(int i = 0; i < 10; i++) {
					for(int j = 0; j < 10; j++) {
						if(event.getSource()==opponentBoardArr[i][j]) {
							String s = letsPlay.tryAttack(i, j, 0);
							setText(s);
							if(!s.equals("Invalid Move, Try Again")) {
								updateTopBoard();
								if(letsPlay.isWin()) {
									setText(letsPlay.winner);
									lockTopButtons();
									return;
								}
								setText(letsPlay.receiveAIAttack());
								updateBottomBoard();
								if(letsPlay.isWin()) {
									setText(letsPlay.winner);
									lockTopButtons();
									return;
								}
							}
							return;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Resets Game
	 */
	private void startGame() {
		letsPlay = new Game(true);
		isPlacing = true;
		isHorizontal = true;
		currentShip = -1;
		resetText();
	}
	
	/**
	 * Updates Button Colors For Bottom Board
	 */
	private void updateBottomBoard() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(letsPlay.players.get(0).myBoard[i][j] != '~') {
					if(letsPlay.players.get(0).myBoard[i][j] == 'x') {
						myBoardArr[i][j].setStyle("-fx-background-color: #a31111");
					}else {
						myBoardArr[i][j].setStyle("-fx-background-color: #76e2cb");
					}
				}
			}
		}
	}
	
	/**
	 * Updates Top Board to Reflect Model
	 */
	private void updateTopBoard() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(letsPlay.players.get(0).opponentBoard[i][j] != '~') {
					if(letsPlay.players.get(0).opponentBoard[i][j] == 'x') {
						opponentBoardArr[i][j].setStyle("-fx-background-color: #a31111");
					}else {
						opponentBoardArr[i][j].setStyle("-fx-background-color: #ffffff");
					}
				}
			}
		}
	}
	
	
	/**
	 * Locks The Attack Buttons
	 */
	private void lockTopButtons() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				opponentBoardArr[i][j].setDisable(true);
			}
		}
	}
	
	
	/** Makes Necessary Changes When Transitioning From
	 * Setup To Attack */
	private void switchToAttack() {
		isPlacing = false;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				myBoardArr[i][j].setDisable(true);
				opponentBoardArr[i][j].setDisable(false);
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

