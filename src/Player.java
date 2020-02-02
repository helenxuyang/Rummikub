
import java.awt.Color;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Player {
	private String name = "";
	private Rack rack;
	private boolean passedFirstRound;

	public static Game game;
	public static Pile pile;
	public static Board board;

	public static final Color PEACH = new Color(254, 249, 234);
	public static final Color RED = new Color(255, 51, 51);
	public static final Color YELLOW = new Color(255, 204, 51);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color BLUE = new Color(51, 204, 255);
	public static final Color FAKEBLACK = new Color(1, 0, 0);
	public static final Color WHITE = new Color(255, 255, 255);

	public Player(String n) {
		name = n;
		String rackName = n + "'s Rack";
		rack = new Rack(rackName);
		passedFirstRound = false;
	}

	public Rack getRack() {
		return rack;
	}
	
	public String getName() {
		return name;
	}

	public boolean getPassed() {
		return passedFirstRound;
	}
	
	public void setPassedTrue() {
		passedFirstRound = true;
	}
	
	public void setRackVisibility(boolean visible) {
		rack.getFrame().setVisible(visible);
	}

	public String toString() {
		return name;
	}

	public void sortHand(ArrayList<Tile> al) {
		ArrayList<Tile> unarranged = al;
		ArrayList<Tile> arranged = new ArrayList<Tile>();
		Color[] colors = {BLACK, BLUE, RED, YELLOW};
		while (arranged.size() < unarranged.size()) {	
			for (Color c : colors) {
				for (int i = 1; i <= 14; i++) {
					for (Tile t : unarranged) {
						if (t.getColor().equals(c) && t.getNumber() == i) {
							arranged.add(t);
						}
					}
				}
			}
		}
	}

	public void endRound() {
		Grid.clickedTile = null;
		Grid.clickedSpaceCoords = null;
		Grid.clickedTileGrid = null;
		Grid.clickedSpaceGrid = null;
		if (!game.checkWin()) {
			game.playRound();
		}
		else {
			Player winner = game.getWinner();
			String name = winner.getName();
			board.makePopUp(name + " has won!");
			CongratsScreen c = new CongratsScreen(500, 500, name);
		}
	}
}

