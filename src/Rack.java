import java.awt.Color;
import java.util.ArrayList;

public class Rack extends Grid{
	public static int width = 500;
	public static int height = 400;
	public static int rows = 6;
	public static int cols = 10;
	
	public Rack(String n) {
		super(width, height, rows, cols, n);
	}

	public void mouseClickedAction(DigitalPicture pict, Pixel pix) { 
		super.mouseClickedAction(pict, pix);
	}

	public void placeArrangedTiles(ArrayList<Tile> arranged) {
		int row = 0;
		int col = 0;
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getCols(); c++) {
				if (checkHasTileAt(r,c))
					removeTileAndDrawEmpty(r, c);
			}
		}
		for (Tile tile : arranged) {
			tile.setPos(row, col);
			putAndDrawTile(tile);
			setImage(getPicture());
			if (col != getCols() - 1) {
				col++;
			}
			else {
				col = 0;
				row++;
			}
		}
	}
	public void sortByNum() {
		ArrayList<Tile> unarranged = getListOfTiles();
		ArrayList<Tile> arranged = new ArrayList<Tile>();

		while (arranged.size() < unarranged.size()) {	
			for (int i = 0; i <= 14; i++) {
				for (Tile t : unarranged) {
					Tile copy = Board.makeCopy(t);
					if (copy.getNumber() == i) {
						arranged.add(copy);
					}
				}
			}
		}
		placeArrangedTiles(arranged);
	}
	
	public void sortByColor() {
		ArrayList<Tile> unarranged = getListOfTiles();
		ArrayList<Tile> arranged = new ArrayList<Tile>();
		
		Color[] colors = {Grid.BLACK, Grid.BLUE, Grid.RED, Grid.YELLOW};
		while (arranged.size() < unarranged.size()) {	
			for (Color c : colors) {
				for (int i = 0; i <= 14; i++) {
					for (Tile t : unarranged) {
						Tile copy = Board.makeCopy(t);
						if (copy.getColor().equals(c) && copy.getNumber() == i) {
							arranged.add(copy);
						}
					}
				}
			}
		}
		placeArrangedTiles(arranged);	
	}
	
	public static ArrayList<Tile> sort(ArrayList<Tile> orig) {
		ArrayList<Tile> sorted = new ArrayList<Tile>();
		Color[] colors = {Grid.BLACK, Grid.BLUE, Grid.RED, Grid.YELLOW};
		while (sorted.size() < orig.size()) {	
			for (Color c : colors) {
				for (int i = 0; i <= 14; i++) {
					for (Tile t : orig) {
						Tile copy = Board.makeCopy(t);
						if (copy.getColor().equals(c) && copy.getNumber() == i) {
							sorted.add(copy);
						}
					}
				}
			}
		}
		return sorted;
	}
	
	public int[] firstEmptySpot() {
		boolean foundEmpty = false;
		int[] coords = new int[2];
		
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getCols(); c++) {
				if (!foundEmpty) {
					if (!checkHasTileAt(r,c)) {
						coords[0] = r;
						coords[1] = c;
						foundEmpty = true;
					}
				}
			}
		}
		return coords;
	}
	
	public void takeATile(Pile p) {
		int randomIndex = (int)(Math.random() * (p.getPileSize()));
		Tile t = p.getNthTile(randomIndex);
		int[] coords = firstEmptySpot();
		t.setPos(coords[0], coords[1]);
		putAndDrawTile(t);
		p.removeTile(t);
		setImage(getPicture());
	}
	
	public boolean isFull() {
		boolean isFull = true;
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getCols(); c++) {
				if (getTile(r, c) == null) {
					isFull = false;
				}
			}
		}
		return isFull;
	}
}
