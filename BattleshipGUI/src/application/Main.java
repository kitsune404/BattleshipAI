package application;

import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * The main class of the GUI
 * @author meganlahm
 * @author Dylan Prince
 */
public class Main extends Application{
	
	
	/** The top grid pane of the GUI */
	private GridPane topGP;

	/** The bottom grid pane of the GUI */
	private GridPane bottomGP;
	
	/** GridPane For The Menu Buttons */
	private GridPane mPane;
	
	/** Displays Feedback From Game */
	private Text feedPane;
	
	/** Allows The feedPane To Scroll */
	private ScrollPane scrollPane;
	
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
	
	/** Current Selected Ship */
	private int currentShip;
	
	/** Game object */
	private Game letsPlay;
	
	/** Type Of AI Being Implemented */
	private int AIType;
	
	/** The width of the GUI */
	private int sceneWidth = 850;
	
	/** The height of the GUI */
	private int sceneHeight = 800;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			startGame();
			
			primaryStage.heightProperty().addListener((obs, oldVal, newVal) ->
					updateHeight(primaryStage.getHeight()));
			primaryStage.widthProperty().addListener((obs,oldVal,newVal) ->
					updateWidth(primaryStage.getWidth()));
			
			GridPane root = new GridPane();
			root.setStyle("-fx-background-color: #557ea0;");
			
			Scene scene = new Scene(root, sceneWidth, sceneHeight);
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

			primaryStage.setScene(scene);
			primaryStage.setTitle("Battleship");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	///////////////////////////
	// PANE INITIALIZERS
	///////////////////////////
	
	/**
	 * Creates the top GP of the GUI
	 * @return
	 */
	private GridPane topPane() {
		topGP = new GridPane();
		topGP.setPrefSize((sceneWidth/2), (sceneHeight/2)-20);
		topGP.setGridLinesVisible(true);
		
		int circleSize = 10 * sceneHeight / 800;
		Circle circle = new Circle(circleSize);
		for(int i = 0; i < 10; i++) {
			topGP.add(new Label("  "+(char)(i+65)+ " "), i+1,0);
			topGP.add(new Label(" "+(i+1)+ " "), 0,i+1);
			
			for(int j = 0; j < 10; j++) {
				
				opponentBoardArr[i][j] = new Button();
				opponentBoardArr[i][j].setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent thisEvent) {
						if(!isPlacing){
							topButtonEvent(thisEvent);
						}
					}
				});
				opponentBoardArr[i][j].setShape(circle);
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
		bottomGP.setPrefSize((sceneWidth/2), (sceneHeight/2)-20);
		bottomGP.setGridLinesVisible(true);
		
		int circleSize = 10 * sceneHeight / 800;
		Circle circle = new Circle(circleSize);
		for(int i = 0; i < 10; i++) {
			bottomGP.add(new Label("  "+(char)(i+65)+ " "), i+1,0);
			bottomGP.add(new Label(" "+(i+1)+ " "), 0,i+1);
			
			for(int j = 0; j < 10; j++) {
				myBoardArr[i][j] = new Button();
				myBoardArr[i][j].setShape(circle);
				myBoardArr[i][j].setScaleShape(false);
				myBoardArr[i][j].setStyle("-fx-background-color: #e0d8c0");
				myBoardArr[i][j].setOnAction(new EventHandler<ActionEvent>() {
					
					public void handle(ActionEvent thisEvent) {
						if(isPlacing && currentShip != -1){
							bottomButtonEvent(thisEvent);
						}
					}
				});
				myBoardArr[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
					
					public void handle(MouseEvent thisButton) {
						if(isPlacing && currentShip != -1) {
							setOnHover(thisButton);
						}
					}
				});
				myBoardArr[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
					
					public void handle(MouseEvent thisButton) {
						if(isPlacing && currentShip != -1) {
							setOnRemove(thisButton);
						}
					}
				});
				myBoardArr[i][j].setAlignment(Pos.CENTER);
				myBoardArr[i][j].setText(" ");
				bottomGP.add(myBoardArr[i][j], i + 1, j + 1);
				
			}
		}
		return bottomGP;
	}
	
	/**
	 * Create the Scroll pane that shows moves
	 * @return the created pane
	 */
	private ScrollPane textPane() {
		scrollPane = new ScrollPane();
		scrollPane.setPrefSize(sceneWidth/2, (sceneHeight/2)-20);
		feedPane = new Text();
		feedPane.setWrappingWidth(sceneWidth/2);
		resetText();
		setText("");
		scrollPane.setContent(feedPane);
		return scrollPane;
	}
	
	/**
	 * Create the menu pane
	 * @return the created menu pane
	 */
	private GridPane menuPane() {	
		int buttonWidth = sceneWidth / 4;
		mPane = new GridPane();
		mPane.setPrefSize(sceneWidth/2, (sceneHeight/2)-20);
		resetB = new Button();
		resetB.setText("New Game");
		resetB.setPrefWidth(buttonWidth);
		resetB.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent thisEvent) {
				resetButtonEvent();
			}
		});
		mPane.add(resetB, 0, 7);
		dirB = new Button();
		dirB.setText("Change Current Direction:");
		dirB.setPrefWidth(buttonWidth);
		dirB.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent thisEvent) {
				changeDirection();
			}
		});
		mPane.add(dirB, 0, 1);
		hLabel = new Label("Horizontal");
		mPane.add(hLabel, 1, 1);
		shipB = new Button[Player.SHIPNAMES.length];
		
		for(int i = 0; i < shipB.length; i++) {
			shipB[i] = new Button();
			shipB[i].setText("Place " + Player.SHIPNAMES[i]);
			shipB[i].setPrefWidth(buttonWidth);
			shipB[i].setOnAction(new EventHandler<ActionEvent>() {
				
				public void handle(ActionEvent thisEvent) {
					if(isPlacing){
						shipButtonEvent(thisEvent);
					}
				}
			});
			mPane.add(shipB[i], 0, (i + 2));
			mPane.add(new Label("Size: " + Player.SHIPSIZES[i]), 1, (i + 2));
		}
		return mPane;
	}
	
	//////////////////////
	// EVENT HANDLERS
	//////////////////////
	
	/**
	 * Event That Playes When The Reset Button Is Pressed
	 */
	private void resetButtonEvent() {
		startGame();
		setText("");
		resetButtonColors();
		setLockBottomButtons(false);
		setLockTopButtons(true);
		
		for(int i = 0; i < shipB.length; i++) {
			shipB[i].setDisable(false);
		}
	}
	
	/**
	 * Event That Is Played When A Top Board Button Is Pressed
	 * @param event
	 */
	private void topButtonEvent(ActionEvent event) {
		for(int i = 0; i < opponentBoardArr.length; i++) {
			for(int j = 0; j < opponentBoardArr.length; j++) {
				if(event.getSource().equals(opponentBoardArr[i][j])) {
					String s = letsPlay.receivePlayerAttack(i,j);
					setText(s);
					if(!s.equals("!?")) {
						
						updateTopBoard();
						if(letsPlay.isWin()) {
							endGame();
							return;
						}
						setText(letsPlay.receiveAIAttack());
						updateBottomBoard();
						if(letsPlay.isWin()) {
							endGame();
							return;
						}
					}
					return;
				}
			}
		}
	}
	
	/**
	 * Event That Plays When A Bottom Board Button Is Pressed
	 * @param event
	 */
	private void bottomButtonEvent(ActionEvent event) {
		for(int i = 0; i < myBoardArr.length; i++) {
			for(int j = 0; j < myBoardArr.length; j++) {
				
				if(event.getSource().equals(myBoardArr[i][j])) {
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
					return;
				}
			}
		}
	}
	
	/**
	 * Sets The currentShip To Be The Ship Corresponding To The Button Pressed
	 * @param event
	 */
	private void shipButtonEvent(ActionEvent event) {
		for(int i = 0; i < shipB.length; i++) {
			
			if(event.getSource()== shipB[i]) {
				currentShip = i;
				return;
			}
		}
	}
	
	/**
	 * Sets A Ghost Image Of The Ship Over The Bottom Buttons When Hovered Over
	 * @param event
	 */
	private void setOnHover(MouseEvent event) {
		int x = 0;
		int y = 0;
		
		for(int i = 0; i < myBoardArr.length; i++) {
			for(int j = 0; j < myBoardArr.length; j++) {
				if(myBoardArr[i][j].equals(event.getSource())) {
					x = i;
					y = j;
					break;
				}
			}
		}
		
		if(isHorizontal) {
			for(int i = 0; i < Player.SHIPSIZES[currentShip]; i++) {
				if(x + i > myBoardArr.length - 1) {
					break;
				}
				if(letsPlay.players.get(0).myBoard[x + i][y] == '~') {
					myBoardArr[x + i][y].setStyle("-fx-background-color: #aacbff");
				}
			}
		}else{
			for(int i = 0; i < Player.SHIPSIZES[currentShip]; i++) {
				if(y + i > myBoardArr.length - 1) {
					break;
				}
				if(letsPlay.players.get(0).myBoard[x][y + i] == '~') {
					myBoardArr[x][y + i].setStyle("-fx-background-color: #aacbff");
				}
			}
		}
	}
	
	/**
	 * Removes The Ghost Image Placed By setOnHover
	 * @param event
	 */
	private void setOnRemove(MouseEvent event) {
		int x = 0;
		int y = 0;
		
		for(int i = 0; i < myBoardArr.length; i++) {
			for(int j = 0; j  < myBoardArr.length; j++) {
				if(event.getSource().equals(myBoardArr[i][j])) {
					x = i;
					y = j;
					break;
				}
			}
		}
		if(isHorizontal) {
			for(int i = 0; i < Player.SHIPSIZES[currentShip]; i++) {
				if(x + i > myBoardArr.length - 1) {
					break;
				}
				if(letsPlay.players.get(0).myBoard[x + i][y] == '~') {
					myBoardArr[x + i][y].setStyle("-fx-background-color: #e0d8c0");
				}
			}
		}else{
			for(int i = 0; i < Player.SHIPSIZES[currentShip]; i++) {
				if(y + i > myBoardArr.length - 1) {
					break;
				}
				if(letsPlay.players.get(0).myBoard[x][y + i] == '~') {
					myBoardArr[x][y + i].setStyle("-fx-background-color: #e0d8c0");
				}
			}
		}
	}
	
	/////////////////////////////////
	// PANE AND GAME LOGISTICS
	/////////////////////////////////
	
	/**
	 * Updates Element Whose Size Depends On Width
	 * @param width
	 */
	private void updateWidth(double width) {
		sceneWidth = (int)width;
		
		int paneWidth = sceneWidth / 2;
		mPane.setPrefWidth(paneWidth);
		scrollPane.setPrefWidth(paneWidth);
		topGP.setPrefWidth(paneWidth);
		bottomGP.setPrefWidth(paneWidth);
		feedPane.setWrappingWidth(paneWidth);
		
		int mButtonWidth = sceneWidth / 4;
		dirB.setPrefWidth(mButtonWidth);
		resetB.setPrefWidth(mButtonWidth);
		for(int i = 0; i < shipB.length; i++) {
			shipB[i].setPrefWidth(mButtonWidth);
		}
	}
	
	/**
	 * Updates Elements Whose Size Depends On Height
	 * @param height
	 */
	private void updateHeight(double height) {
		sceneHeight = (int)height;
		
		int paneHeight = (sceneHeight / 2) - 20;
		mPane.setPrefHeight(paneHeight);
		scrollPane.setPrefHeight(paneHeight);
		topGP.setPrefHeight(paneHeight);
		bottomGP.setPrefHeight(paneHeight);
//		int circleWidth = 10 * sceneHeight / 800;
//		Circle circle = new Circle(circleWidth);
//		for(int i = 0; i < myBoardArr.length; i++) {
//			for(int j = 0; j < myBoardArr.length; j++) {
//				myBoardArr[i][j].setShape(circle);
//				opponentBoardArr[i][j].setShape(circle);
//			}
//		}
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
	 * Updates Button Colors For Bottom Board
	 */
	private void updateBottomBoard() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				
				if(letsPlay.players.get(0).myBoard[i][j] != '~') {
					if(letsPlay.players.get(0).myBoard[i][j] == 'x') {
						myBoardArr[i][j].setStyle("-fx-background-color: #a31111");
					}else if(letsPlay.players.get(0).myBoard[i][j] == 'o'){
						myBoardArr[i][j].setStyle("-fx-background-color: #ffffff");
					}else {
						myBoardArr[i][j].setStyle("-fx-background-color: #5ee4ff");
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
	 * Locks Or Unlocks Top Pane Buttons
	 * @param isLocked
	 */
	private void setLockTopButtons(boolean isLocked) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				opponentBoardArr[i][j].setDisable(isLocked);
			}
		}
	}
	
	/**
	 * Locks Or Unlocks Bottom Pane Buttons
	 * @param isLocked
	 */
	private void setLockBottomButtons(boolean isLocked) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				
				myBoardArr[i][j].setDisable(isLocked);
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
		setText("Use The Top Board To Attack The Enemy Ships!");
	}
	
	/**
	 * Set the text of the feedback pane
	 * @param s
	 */
	private void setText(String s) {
		
		feedback += s + "\n\n";
		feedPane.setText(feedback);
		scrollPane.setVvalue(1.0);
	}
	
	////////////////////////////////////
	// STARTING AND RESETTING THE GAME
	////////////////////////////////////
	/**
	 * Resets Game
	 */
	private void startGame() {
		popUpAISelection();
		letsPlay = new Game(AIType);
		isPlacing = true;
		isHorizontal = true;
		currentShip = -1;
		resetText();
	}
	
	/**
	 * Takes Necessary Steps To End The Game
	 */
	private void endGame() {
		setText(letsPlay.winner);
		setLockTopButtons(true);
	}
	
	/**
	 * Reset the text in the message pane
	 */
	private void resetText() {
		feedback = "Welcome To Battleship!\n"
				+ "Select Your Ships And Place Them On The Lower Board In A Horizontal Or Vertical Position\n"
				+ "The Selected Space Will Serve As The Leftmost And Topmost Position, Respectively";
	}
	
	/**
	 * Resets The Buttons To Base Colors
	 */
	private void resetButtonColors() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				myBoardArr[i][j].setStyle("-fx-background-color: #e0d8c0");
				opponentBoardArr[i][j].setStyle("-fx-background-color: #ced2db");
			}
		}
	}
	
	/**
	 * Creates A DialogBox That Selects An AI Level
	 */
	private void popUpAISelection() {
		ArrayList<Integer> collect = new ArrayList<Integer>();
		for(int i = 0; i <= AI.MAXCOMPLEX; i++) {
			collect.add(i);
		}
		ChoiceDialog<Integer> dialog = new ChoiceDialog<Integer>(0,collect);
		dialog.setTitle("AI Difficulty Selection");
		dialog.setHeaderText("Select An AI Difficulty");
		dialog.setContentText("AI Level:");
		Optional<Integer> result = dialog.showAndWait();
		result.ifPresent(integer -> {
			AIType = integer;
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}