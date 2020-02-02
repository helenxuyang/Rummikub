import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Game {
	private Board board;
	private Pile pile;
	private Game.Menu menu;
	private Player[] players;
	private int currentPlayerIndex;
	private Player currentPlayer;
	private ArrayList<Tile> boardStartTiles = new ArrayList<Tile>();
	private ArrayList<Tile> boardEndTiles = new ArrayList<Tile>();
	private ArrayList<ArrayList<Tile>> boardStartRuns = new ArrayList<ArrayList<Tile>>();
	private ArrayList<ArrayList<Tile>> boardEndRuns = new ArrayList<ArrayList<Tile>>();
	private ArrayList<Tile> playerStartTiles = new ArrayList<Tile>();
	private ArrayList<Tile> playerEndTiles = new ArrayList<Tile>();

	public static void main(String[] args) {
		playRummikub();
	}

	public Game(int numOfPlayers, String[] playerNames) {	
		players = new Player[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++) {
			String name = "";
			if (!playerNames[i].equals("")) {
				name = playerNames[i];
			}
			else {
				name += ("Player " + (i+1));
			}
			players[i] = new Player(name);	
			players[i].getRack().getFrame().setLocation(Board.width + 20, 10);
		}
		board = new Board("Board");
		board.getFrame().setLocation(10, 10);
		board.drawGrid();
		pile = new Pile();
		menu = new Game.Menu();
		menu.getFrame().setLocation(Board.width + 20, Rack.height + 150);
		Player.board = board;
		Player.pile = pile;
		Player.game = this;
		setUp();
	}

	public void setUp() {
		currentPlayerIndex = 0;
		currentPlayer = players[0];
		showOnlyCurrentPlayer();
		//for each player, draw rack and get 14 tiles
		for (Player p : players) {
			Rack r = p.getRack();
			r.drawGrid();
			for (int i = 0; i < 14; i++) {
				if (!r.isFull()) {
					r.takeATile(pile);
				}
			}
			r.setImage(r.getPicture());
		}
		getTileInfo();
		board.setImage(board.getPicture());
	}

	//----------ACCESORS + MODIFIERS----------
	public Player[] getPlayers() {
		return players;
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int i) {
		currentPlayerIndex = i;
	}

	public void showOnlyCurrentPlayer() {
		for (Player p : players) {
			if (p.equals(currentPlayer)) 
				p.setRackVisibility(true);	
			else 
				p.setRackVisibility(false);
		}
	}

	//----------METHODS FOR GAME ROUNDS----------
	public void playRound() {	
		nextPlayer();
		getTileInfo();
	}

	public void nextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex == players.length) {
			currentPlayerIndex = 0;
		}
		currentPlayer = players[currentPlayerIndex];
		showOnlyCurrentPlayer();
	}

	public void getTileInfo() {
		playerStartTiles.clear();
		boardStartRuns.clear();
		boardStartTiles.clear();
		
		Rack currentRack = currentPlayer.getRack();
		//set tiles to moveable or not, without references so direct
		for (int r = 0; r < Board.rows; r++) {
			for (int c = 0; c < Board.cols; c++) {
				if (board.checkHasTileAt(r, c)) {
					Tile t = board.getTile(r, c);
					t.setMoveable(false);
					if (!currentPlayer.getPassed()) {
						t.setSelectable(false);
					}
					else {
						t.setSelectable(true);
					}
				}
			}
		}
		for (int r = 0; r < Rack.rows; r++) {
			for (int c = 0; c < Rack.cols; c++) {
				if (currentRack.checkHasTileAt(r, c)) {
					Tile t = currentRack.getTile(r, c);
					t.setMoveable(true);
				}
			}
		}
		//set start tile variables, with references so copies
		playerStartTiles = currentPlayer.getRack().getListOfTiles();
		boardStartTiles = board.getListOfTiles();
		boardStartRuns = board.getAllRuns();
	}

	public void setCurrentPlayer(int index) {
		currentPlayer = players[index];
	}

	public static void playRummikub() {
		TitleScreen t = new TitleScreen(500, 500);
	}

	public boolean checkWin() {
		boolean hasWon = false;
		for (Player p : players) {
			if (p.getRack().getNumOfTiles() == 0) 
				hasWon = true;
		}
		return hasWon;
	}

	public Player getWinner() {
		Player winner = null;
		for (Player p : players) {
			if (p.getRack().getNumOfTiles() == 0) 
				winner = p;
		}
		return winner;		
	}
	//------------------------------MENU CLASS------------------------------//
	public class Menu extends JPanel implements ActionListener {

		private JFrame frame;

		public Menu() {
			frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setMinimumSize(new Dimension(500,300));
			frame.setTitle("Menu");

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2,3));
			panel.setBorder(new EmptyBorder(10,10,10,10));

			JButton numButton = new JButton("Arrange by number");
			numButton.setActionCommand("number");	
			numButton.addActionListener(this);

			JButton colorButton = new JButton("Arrange by color");
			colorButton.setActionCommand("color");
			colorButton.addActionListener(this);

			JButton tileButton = new JButton("Take a tile from pile");
			tileButton.setActionCommand("tile");
			tileButton.addActionListener(this);

			JButton roundButton = new JButton("End round");
			roundButton.setActionCommand("round");
			roundButton.addActionListener(this);

			JButton resetButton = new JButton("Reset board");
			resetButton.setActionCommand("reset");
			resetButton.addActionListener(this);

			frame.add(panel);

			panel.add(numButton);
			panel.add(colorButton);
			panel.add(resetButton);
			panel.add(tileButton);
			panel.add(roundButton);		
			frame.setVisible(true);
		}

		public JFrame getFrame() {
			return frame;
		}
		
		public void actionPerformed(ActionEvent e) {
			Grid.clickedTile = null;
			Grid.clickedSpaceCoords = null;
			Grid.clickedTileGrid = null;
			Grid.clickedSpaceGrid = null;
			
			String com = e.getActionCommand();
			Rack currentRack = currentPlayer.getRack();

			playerEndTiles = currentRack.getListOfTiles();
			boardEndTiles = board.getListOfTiles();
			boardEndRuns = board.getAllRuns();

			playerEndTiles.clear();
			boardEndTiles.clear();
			boardEndRuns.clear();

			playerEndTiles = currentRack.getListOfTiles();
			boardEndTiles = board.getListOfTiles();

			playerStartTiles = Rack.sort(playerStartTiles);
			playerEndTiles = Rack.sort(playerEndTiles);
			boardStartTiles = Rack.sort(boardStartTiles);
			boardEndTiles = Rack.sort(boardEndTiles);
			for (ArrayList<Tile> run : board.getAllRuns()) {
				boardEndRuns.add(run);
			}
			
			boolean validMoves = board.allRunsAreValid();
			boolean madeMoves = !boardStartTiles.equals(boardEndTiles) && !playerStartTiles.equals(playerEndTiles);

			//sort by number
			if (com.equals("number")) {
				currentRack.sortByNum();
			}

			//sort by color
			else if (com.equals("color")) {
				currentRack.sortByColor();
			}

			//reset board and rack to what they was at beginning of round
			else if (com.equals("reset")) {
				validMoves = true;
				madeMoves = false;
				for (int r = 0; r < Rack.rows; r++) {
					for (int c = 0; c < Rack.cols; c++) {
						if (currentRack.checkHasTileAt(r, c))
							currentRack.getTile(r, c).setSelected(false);
						currentRack.removeTileAndDrawEmpty(r, c);
					}
				}
				currentRack.placeArrangedTiles(playerStartTiles);
				for (int r = 0; r < Board.rows; r++) {
					for (int c = 0; c < Board.cols; c++) {
						if (board.checkHasTileAt(r, c)) {
							board.getTile(r, c).setSelected(false);
							board.removeTileAndDrawEmpty(r, c);
						}
					}
				}
				for (Tile t : boardStartTiles) {
					board.putAndDrawTile(Board.makeCopy(t));
				}
				board.setImage(board.getPicture());
			}

			//take a tile if haven't made any moves and pile still has tiles
			else if (com.equals("tile")) {
				currentRack.setImage(currentRack.getPicture());
				if (validMoves && !madeMoves) {
					if (!(pile.getPileSize() == 0)) { 
						currentRack.takeATile(pile);
						currentPlayer.endRound();
					}
					else {
						board.makePopUp("Pile is empty.");
					}	
				}
				else if (madeMoves) {
					board.makePopUp("Cannot take a tile from the pile "
							+ "after placing tiles on the board.");
				}
				else if (!validMoves) {
					board.makePopUp("Invalid board!");
				}
			}

			//end round if have made moves	
			else if (com.equals("round")) {			
				//first round can't edit others' runs and sum needs to be >= 30
				if (!currentPlayer.getPassed()) {
					boolean validSum = false;
					boolean editedOthers = false;
					int sum = 0;

					if (validMoves) {
						ArrayList<ArrayList<Tile>> newRuns = new ArrayList<ArrayList<Tile>>();
						for (ArrayList<Tile> run : boardEndRuns) {
							if (!(boardStartRuns.contains(run))) {
								newRuns.add(run);
							}
						}
						sum = Board.sumOfRuns(newRuns);
						validSum = sum >= 30;

						if (!boardEndRuns.containsAll(boardStartRuns)) {
							editedOthers = true;
						}
					}

					//check if everything is valid
					if (validMoves && madeMoves && validSum && !editedOthers) {
						currentPlayer.setPassedTrue();
						currentPlayer.endRound();
					}
					else if (!madeMoves) {
						board.makePopUp("Must take a tile if you haven't made any moves.");
					}
					else if (!validMoves) {
						board.makePopUp("Invalid moves!");
					}
					else if (editedOthers) {
						board.makePopUp("For the first round, you can only form"
								+ " runs from your own tiles.");
					}
					else if (sum < 30) {
						board.makePopUp("For the first round, the sum of tiles"
								+ " has to be greater than or equal to 30.");
					}
				}

				//if not round 0, just needs to have valid moves
				else if (currentPlayer.getPassed()) {
					if (validMoves && madeMoves) {
						currentPlayer.endRound();
					}
					else if (!madeMoves) {
						board.makePopUp("Must take a tile if you haven't made any moves.");
					}
					else if (!validMoves) {
						board.makePopUp("Invalid moves!");
					}
				}
			}
		} 
	}
	//------------------------------MENU CLASS END------------------------------//
}