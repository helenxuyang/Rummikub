import java.util.ArrayList;
public class Pile {
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	Pile() {
		for (int i = 0; i < 2; i++) {
			for (int num = 1; num < 14; num++) {
				Tile t = new Tile(Tile.RED, num);
				tiles.add(t);
				t = new Tile(Tile.YELLOW, num);
				tiles.add(t);
				t = new Tile(Tile.BLACK, num);
				tiles.add(t);
				t = new Tile(Tile.BLUE, num);
				tiles.add(t);
			}
		}
		tiles.add(new Tile(Tile.RED));
		tiles.add(new Tile(Tile.BLACK));
	}

	public int getPileSize() {
		return tiles.size();
	}
	
	public Tile getNthTile(int n) {
		Tile t = tiles.get(n);
		return t;
	}

	public void removeTile(Tile t) {
		tiles.remove(t);
	}
}
