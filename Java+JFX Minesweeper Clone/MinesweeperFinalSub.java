// Jacob Labelle 300306856 COSC 121 Minesweeper Part 3
// Win condition: all mines flagged via right click or all tiles that aren't mines
// revealed
// Final submission
package application;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MinesweeperFinalSub extends Application {
	// Game Boards and State
	private boolean[][] gameKey;
	private int[][] proximity;
	private GameTile[][] buttonGrid;
	private boolean gameOver, gameWon;
	private boolean firstClick;
	private int possMinesRemaining;
	// Difficulty Values
	private static final int EASY_X = 8, EASY_Y = 8, EASY_MINES = 10;
	private static final int INTERMEDIATE_X = 16, INTERMEDIATE_Y = 16, INTERMEDIATE_MINES = 40;
	private static final int EXPERT_X = 16, EXPERT_Y = 32, EXPERT_MINES = 99;
	// Default Load
	private static int diffX = EASY_X, diffY = EASY_Y, mineDiff = EASY_MINES;
	// Default title
	private static String title = "Minesweeper";
	// Counters
	private DigitDisplay mineOnes, mineTens, mineHundreds;
	private DigitDisplay timeOnes, timeTens, timeHundreds;
	private Timeline timer;
	private boolean timerRunning = false;
	private int currentTime = 0;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage msUI) {
		// Make the Boards
		firstClick = false;
		// Make the Counters
		generateMineCounter();
		generateTimeCounter();
		// Set Counters
		possMinesRemaining = mineDiff;
		timer = new Timeline();
		timer.setCycleCount(Animation.INDEFINITE);
		timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1), tmr -> {
			updateTimeCounter();
		}));
		// Make the UI Elements
		MenuBar menu = difficultyMenu(msUI);
		GridPane gp = new GridPane();
		BorderPane topBP = new BorderPane();
		BorderPane bp = new BorderPane();
		Scene scene = new Scene(bp);
		HBox timePane = new HBox();	
		HBox minePane = new HBox();
		FaceButton playerState = new FaceButton();
		buttonGrid = new GameTile[diffX][diffY];
		updateMineCounter();
		// Set alignments and spacing
		timePane.getChildren().add(timeHundreds);
		timePane.getChildren().add(timeTens);
		timePane.getChildren().add(timeOnes);
		minePane.getChildren().add(mineHundreds);
		minePane.getChildren().add(mineTens);
		minePane.getChildren().add(mineOnes);
		timePane.setAlignment(Pos.CENTER);
		minePane.setAlignment(Pos.CENTER);
		gp.setAlignment(Pos.CENTER);
		// Start a New Game if Smiley Man is clicked
		playerState.setOnMouseClicked(e -> {
			msUI.close();
			MinesweeperFinalSub newGame = new MinesweeperFinalSub();
			newGame.start(msUI);
		});
		// Generate the Game Tiles
		generateGameTiles(gp, buttonGrid, playerState);
		// Bring the pane
		// CSS Provided by Ken
		bp.setStyle(
				"-fx-background-color: #bfbfbf;-fx-border-color: #fafafa #787878 #787878 #fafafa; "
						+ "-fx-border-width: 5; -fx-border-radius: 0.001;");
		topBP.setStyle(
				"-fx-background-color: #bfbfbf; -fx-border-color: #787878 #fafafa #fafafa #787878; "
						+ "-fx-border-width: 5; -fx-border-radius: 0.001;");
		gp.setStyle(
				"-fx-border-color:  #787878 #fafafa #fafafa #787878; -fx-border-width: 5; "
						+ "-fx-border-radius: 0.001; -fx-faint-focus-color: transparent;");
		//
		topBP.setCenter(playerState);
		topBP.setLeft(minePane);
		topBP.setRight(timePane);
		bp.setPadding(new Insets(5, 5, 5, 5));
		bp.setCenter(topBP);
		bp.setBottom(gp);
		bp.setTop(menu);

		scene.setFill(Color.BLACK);

		// Set the stage
		msUI.setTitle(title);
		msUI.setScene(scene);
		msUI.show();
	}

	public void revealAdjacent(int x, int y) {
		if(proximity[x][y] > 0 && buttonGrid[x][y].state == 0) {
			buttonGrid[x][y].state = 1;
		}
		else if(proximity[x][y] == 0 && buttonGrid[x][y].state == 0) {
			buttonGrid[x][y].state = 1;
			if(x > 0) {
				revealAdjacent(x-1, y);
			}
			if(x < proximity.length - 1) {
				revealAdjacent(x+1, y);
			}
			if(y > 0) {
				revealAdjacent(x, y-1);
			}
			if(y < proximity[x].length - 1) {
				revealAdjacent(x, y+1);
			}
			if(x > 0 && y > 0) {
				revealAdjacent(x-1, y-1);
			}
			if(x < proximity.length - 1 && y < proximity[x].length - 1) {
				revealAdjacent(x+1, y+1);
			}
			if(x < proximity.length - 1 && y > 0) {
				revealAdjacent(x+1, y-1);
			}
			if(x > 0 && y < proximity[x].length - 1) {
				revealAdjacent(x-1, y+1);
			}
		}
	}

	public void setDifficulty(int x, int y, int mines) {
		diffX = x;
		diffY = y;
		mineDiff = mines;
	}

	public void generateTimeCounter() {
		mineOnes = new DigitDisplay();
		mineTens = new DigitDisplay();
		mineHundreds = new DigitDisplay();
	}

	public void generateMineCounter() {
		timeOnes = new DigitDisplay();
		timeTens = new DigitDisplay();
		timeHundreds = new DigitDisplay();
	}

	public void updateMineCounter() {
		mineHundreds.counter = possMinesRemaining / 100 % 10;
		mineHundreds.setGraphic(mineHundreds.digits.get(mineHundreds.counter));
		mineTens.counter = possMinesRemaining / 10 % 10;
		mineTens.setGraphic(mineTens.digits.get(mineTens.counter));
		mineOnes.counter = possMinesRemaining % 10;
		mineOnes.setGraphic(mineOnes.digits.get(mineOnes.counter));
	}

	public void updateTimeCounter() {
		currentTime++;
		timeHundreds.counter = currentTime / 100 % 10;
		timeHundreds.setGraphic(timeHundreds.digits.get(timeHundreds.counter));
		timeTens.counter = currentTime / 10 % 10;
		timeTens.setGraphic(timeTens.digits.get(timeTens.counter));
		timeOnes.counter = currentTime % 10;
		timeOnes.setGraphic(timeOnes.digits.get(timeOnes.counter));
	}

	public MenuBar difficultyMenu(Stage msUI) {
		MenuBar menu = new MenuBar();
		Menu difficulty = new Menu("Difficulty");

		MenuItem beginner = new MenuItem("Beginner (8 x 8, 10 mines)");
		beginner.setOnAction(beg -> {
			msUI.close();
			MinesweeperFinalSub newEasyGame = new MinesweeperFinalSub();
			title = "Minesweeper - Easy";
			newEasyGame.setDifficulty(EASY_X, EASY_Y, EASY_MINES);
			newEasyGame.start(msUI);
		});

		MenuItem interm = new MenuItem("Intermediate (16 x 16, 40 mines)");
		interm.setOnAction(inter -> {
			msUI.close();
			MinesweeperFinalSub newIntermGame = new MinesweeperFinalSub();
			title = "Minesweeper - Intermediate";
			newIntermGame.setDifficulty(INTERMEDIATE_X, INTERMEDIATE_Y, INTERMEDIATE_MINES);
			newIntermGame.start(msUI);
		});

		MenuItem expert = new MenuItem("Expert (16 x 32, 99 mines)");
		expert.setOnAction(exp -> {
			msUI.close();
			MinesweeperFinalSub newExpertGame = new MinesweeperFinalSub();
			title = "Minesweeper - Expert";
			newExpertGame.setDifficulty(EXPERT_X, EXPERT_Y, EXPERT_MINES);
			newExpertGame.start(msUI);
		});

		difficulty.getItems().add(beginner);
		difficulty.getItems().add(interm);
		difficulty.getItems().add(expert);
		menu.getMenus().add(difficulty);
		return menu;
	}

	public void generateGameTiles(GridPane gp, GameTile[][] buttonGrid, FaceButton playerState) {
		for(int x = 0; x < buttonGrid.length; x++) {
			for(int y = 0; y < buttonGrid[x].length; y++) {	
				buttonGrid[x][y] = new GameTile(x, y);
				GameTile b = buttonGrid[x][y] = new GameTile(x, y);
				gp.add(b, y, x);
				b.setOnMouseClicked(e -> {
					if(!timerRunning) {
						timer.play();
						timerRunning = true;
					}

					if(firstClick == false) {
						generateBoards(b.x, b.y, mineDiff);
						generateProximity();
					}

					if(e.getButton() == MouseButton.PRIMARY){
						if(b.state == 0) {
							if(gameKey[b.x][b.y]) {
								gameOver = true;
							}
							else {
								if(proximity[b.x][b.y] == 0) {
									revealAdjacent(b.x, b.y);
								}
							}
							firstClick = true;
							b.state = 1;
						}
						// Check number of flags, reveal tiles
						else if(b.state == 1) {
							if(isFlagCount(b.x, b.y)) {
								revealFlagged(b.x, b.y); 
							}
						}
					}

					if(e.getButton() == MouseButton.SECONDARY) {
						// Flag it
						if(b.state == 0 && possMinesRemaining > 0) {
							b.state = 2;
							possMinesRemaining--;
						}
						// Remove the flag
						else if(b.state == 2) {
							b.state = 0;
							possMinesRemaining++;
						}
						updateMineCounter();
					}
					// Update the State of the Game
					updateGameState();
					updateGraphics();
					if(gameOver) {
						playerState.setGraphic(playerState.deadFace);
						playerState.state = 3;
						gameLost(gameKey, buttonGrid);
					}
					else if(gameWon) {
						playerState.setGraphic(playerState.coolFace);
						playerState.state = 2;
					}
					// Lock the GridePane once game is Won/Lost to prevent further clicks
					if(gameOver || gameWon) {
						timer.stop();
						gp.setMouseTransparent(true);
					}
				});
				b.setOnMousePressed(mp -> {
					playerState.setGraphic(playerState.oFace);
					playerState.state = 1;
				});
				b.setOnMouseReleased(mr -> {
					playerState.setGraphic(playerState.smileFace);
					playerState.state = 0;
				});
			}
		}
	}

	public void updateGraphics() {
		for(int x = 0; x < buttonGrid.length; x++) {
			for(int y = 0; y < buttonGrid[x].length; y++) {
				GameTile gt = buttonGrid[x][y];
				if(gt.state == 0) {
					gt.setGraphic(gt.imageCover);
				}
				else if(gt.state == 2) {
					gt.setGraphic(gt.flagged);
				}
				else if(gt.state == 1) {
					if(gameKey[x][y]) {
						gameOver = true;
					}
					else {
						gt.setGraphic(gt.numberedImages.get(proximity[gt.x][gt.y]));
					}
				}
			}
		}
	}

	public void generateBoards(int bX, int bY, int mineCount) {
		gameKey = new boolean[diffX][diffY];
		proximity = new int[diffX][diffY];
		while(mineCount > 0) {
			int randX = (int)(Math.random() * (diffX));
			int randY = (int)(Math.random() * (diffY));
			if(gameKey[randX][randY] == true || (randX == bX && randY == bY))
				continue;
			if(randX == (bX - 1) && randY == bY)
				continue;
			if(randX == bX && randY == (bY - 1))
				continue;
			if(randX == (bX - 1) && randY == (bY - 1))
				continue;
			if(randX == (bX + 1) && randY == bY)
				continue;
			if(randX == bX && randY == (bY + 1))
				continue;
			if(randX == (bX + 1) && randY == (bY + 1))
				continue;
			if(randX == (bX + 1) && randY == (bY - 1))
				continue;
			if(randX == (bX - 1) && randY == (bY + 1))
				continue;
			else {
				gameKey[randX][randY] = true;
				mineCount--;
			}
		}
	}

	public void generateProximity() {
		int proxLX = proximity.length;
		int proxLY = proximity[0].length;
		for(int x = 0; x < proximity.length; x++) {
			for(int y  = 0; y < proximity[x].length; y++) {
				proximity[x][y] = 0;
				if(!gameKey[x][y]) { 
					if(x > 0 && gameKey[x-1][y] == true) {
						proximity[x][y]++;
					}
					if(x < (proxLX - 1) && gameKey[x+1][y] == true) {
						proximity[x][y]++;
					}
					if(y > 0 && gameKey[x][y-1] == true) {
						proximity[x][y]++;
					}
					if(y < (proxLY - 1) && gameKey[x][y+1] == true) {
						proximity[x][y]++;
					}
					if(y > 0 && x > 0 && gameKey[x-1][y-1] == true) {
						proximity[x][y]++;
					}
					if(y > 0 && x < (proxLX - 1) && gameKey[x+1][y-1] == true) {
						proximity[x][y]++;
					}
					if(y < (proxLY -1) &&  x > 0 && gameKey[x-1][y+1] == true) {
						proximity[x][y]++;
					}
					if(y < (proxLY- 1) && x < (proxLX -1) && gameKey[x+1][y+1] == true) {
						proximity[x][y]++;
					}
				}
				else if(gameKey[x][y]) {
					proximity[x][y] = -1;
				}
			}
		}
	}

	public void updateGameState() {
		int safeTiles = (gameKey.length * gameKey[0].length) - mineDiff;
		int minesRem = mineDiff;
		for(int x = 0; x < gameKey.length; x++) {
			for(int y = 0; y < gameKey[x].length; y++) {
				if(gameKey[x][y] && buttonGrid[x][y].state == 2) {
					minesRem--;
				}
				if(!gameKey[x][y] && buttonGrid[x][y].state == 1) {
					safeTiles--;
				}
			}
		}
		if(minesRem == 0 || safeTiles == 0) {
			gameWon = true;
		}
	}

	public void gameLost(boolean[][] gameKey, GameTile[][] buttonGrid) {
		for(int x = 0; x < gameKey.length; x++) {
			for(int y = 0; y < gameKey[x].length; y++) {
				GameTile gt = buttonGrid[x][y];
				if(gameKey[gt.x][gt.y] && gt.state == 0) {
					gt.setGraphic(gt.imageMineRevealed);
				}
				else if(gameKey[gt.x][gt.y] && gt.state == 1) {
					gt.setGraphic(gt.imageMineTriggered);
				}
				else if(!gameKey[gt.x][gt.y] && gt.state == 2) {
					gt.setGraphic(gt.mineMisflagged);
				}
			}
		}
	}

	public boolean isFlagCount(int x, int y) {
		int proxLX = proximity.length;
		int proxLY = proximity[x].length;
		int expectedFlags = proximity[x][y];
		if(x > 0 && buttonGrid[x-1][y].state == 2) {
			expectedFlags--;
		}
		if(x < (proxLX - 1) && buttonGrid[x+1][y].state == 2) {
			expectedFlags--;
		}
		if(y > 0 && buttonGrid[x][y-1].state == 2) {
			expectedFlags--;
		}
		if(y < (proxLY - 1) && buttonGrid[x][y+1].state == 2) {
			expectedFlags--;
		}
		if(y > 0 && x > 0 && buttonGrid[x-1][y-1].state == 2) {
			expectedFlags--;
		}
		if(y > 0 && x < (proxLX - 1) && buttonGrid[x+1][y-1].state == 2) {
			expectedFlags--;
		}
		if(y < (proxLY -1) &&  x > 0 && buttonGrid[x-1][y+1].state == 2) {
			expectedFlags--;
		}
		if(y < (proxLY- 1) && x < (proxLX -1) && buttonGrid[x+1][y+1].state == 2) {
			expectedFlags--;
		}

		if(expectedFlags == 0) {
			return true;
		}

		return false;
	}

	public void revealFlagged(int x, int y) {
		int proxLX = proximity.length;
		int proxLY = proximity[x].length;
		if(x > 0) {
			if(buttonGrid[x-1][y].state == 0) {
				buttonGrid[x-1][y].state = 1;
			}
		}
		if(x < (proxLX - 1)) {
			if(buttonGrid[x+1][y].state == 0) {
				buttonGrid[x+1][y].state = 1;
			}
		}
		if(y > 0) {
			if(buttonGrid[x][y-1].state == 0) {
				buttonGrid[x][y-1].state = 1;
			}
		}
		if(y < (proxLY - 1)) {
			if(buttonGrid[x][y+1].state == 0) {
				buttonGrid[x][y+1].state = 1;
			}
		}
		if(y > 0 && x > 0) {
			if(buttonGrid[x-1][y-1].state == 0) {
				buttonGrid[x-1][y-1].state = 1;
			}
		}
		if(y > 0 && x < (proxLX - 1)) {
			if(buttonGrid[x+1][y-1].state == 0) {
				buttonGrid[x+1][y-1].state = 1;
			}
		}
		if(y < (proxLY -1) &&  x > 0) {
			if(buttonGrid[x-1][y+1].state == 0) {
				buttonGrid[x-1][y+1].state = 1;
			}
		}
		if(y < (proxLY- 1) && x < (proxLX -1)) {
			if(buttonGrid[x+1][y+1].state == 0) {
				buttonGrid[x+1][y+1].state = 1;
			}
		}
	}
}

class GameTile extends Button {
	protected int x, y;
	protected int state; // 0 for covered, 1 for uncovered, 2 for flagged
	protected ImageView imageCover, flagged, imageMineRevealed, imageMineTriggered, mineMisflagged;
	protected ArrayList<ImageView> numberedImages = new ArrayList<ImageView>();
	protected double size = 35;

	public GameTile(int x, int y) {
		this.x = x;
		this.y = y;
		this.state = 0;

		setMinWidth(size);
		setMaxWidth(size);
		setMinHeight(size);
		setMaxHeight(size);

		imageCover = new ImageView(new Image("file:res/cover.png"));
		flagged = new ImageView(new Image("file:res/flag.png"));
		imageMineRevealed = new ImageView(new Image("file:res/mine-grey.png"));
		imageMineTriggered = new ImageView(new Image("file:res/mine-red.png"));
		mineMisflagged = new ImageView(new Image("file:res/mine-misflagged.png"));

		for(int index = 0; index < 9; index++) {
			ImageView temp = new ImageView(new Image("file:res/" + index + ".png"));
			temp.setFitWidth(size);
			temp.setFitHeight(size);
			numberedImages.add(temp);
		}

		imageCover.setFitWidth(size);
		imageCover.setFitHeight(size);

		flagged.setFitWidth(size);
		flagged.setFitHeight(size);

		imageMineRevealed.setFitWidth(size);
		imageMineRevealed.setFitHeight(size);

		imageMineTriggered.setFitWidth(size);
		imageMineTriggered.setFitHeight(size);

		mineMisflagged.setFitWidth(size);
		mineMisflagged.setFitHeight(size);

		setGraphic(imageCover);
	}
}

class FaceButton extends Button {
	protected int state = 0; // 0 = smile,  1 = oFace, 2 = cool, 3  = dead
	protected ImageView smileFace, oFace, coolFace, deadFace;
	protected double size = 40;

	public FaceButton () {

		setMinWidth(size);
		setMaxWidth(size);
		setMinHeight(size);
		setMaxHeight(size);

		smileFace = new ImageView(new Image("file:res/face-smile.png"));
		oFace = new ImageView(new Image("file:res/face-O.png"));
		deadFace = new ImageView(new Image("file:res/face-dead.png"));
		coolFace = new ImageView(new Image("file:res/face-win.png"));

		smileFace.setFitWidth(size);
		smileFace.setFitHeight(size);

		oFace.setFitWidth(size);
		oFace.setFitHeight(size);

		coolFace.setFitWidth(size);
		coolFace.setFitHeight(size);

		deadFace.setFitWidth(size);
		deadFace.setFitHeight(size);

		setGraphic(smileFace);
	}
}

class DigitDisplay extends Button{
	protected int counter; // 0 - 9
	protected ArrayList<ImageView> digits = new ArrayList<ImageView>();

	public DigitDisplay() {
		counter = 0;
		setMinWidth(20);
		setMaxWidth(20);
		setMinHeight(40);
		setMaxHeight(40);
		for(int index = 0; index < 10; index++) {
			ImageView temp = new ImageView(new Image("file:res/digits/" + index + ".png"));
			temp.setFitWidth(20);
			temp.setFitHeight(40);
			digits.add(temp);
		}
		setGraphic(digits.get(0));
		setMouseTransparent(true);
	}
}