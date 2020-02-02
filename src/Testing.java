import java.util.ArrayList;

public class Testing {
	public static void main(String[] args) {
		//testValidBoard();
		stuff();
	}

	public static void stuff() {
		Grid g = new Grid(500, 280, 4, 10, "Demo");
		g.drawGrid();
		g.putAndDrawTileLazy(0, 0, Grid.RED, 0);
		for (int i = 1; i < 9; i++) {
			g.putAndDrawTileLazy(0, i, Grid.BLACK, i);
			g.putAndDrawTileLazy(1, i, Grid.BLUE, i);
			g.putAndDrawTileLazy(2, i, Grid.RED, i);
			g.putAndDrawTileLazy(3, i, Grid.YELLOW, i);
		}
		g.putAndDrawTileLazy(0, 9, Grid.BLACK, 0);
		
	}
	public static void testValidBoard() {
		Board b = new Board("Test Board");
		b.drawGrid();
		
		b.putAndDrawTileLazy(0, 0, Grid.BLACK, 0);
		b.putAndDrawTileLazy(0, 1, Grid.BLACK, 0);
		b.putAndDrawTileLazy(0, 2, Grid.BLACK, 3);
		b.putAndDrawTileLazy(0, 3, Grid.BLACK, 4);
		
		b.putAndDrawTileLazy(1, 0, Grid.RED, 1);
		b.putAndDrawTileLazy(1, 1, Grid.RED, 2);
		b.putAndDrawTileLazy(1, 2, Grid.RED, 0);
		b.putAndDrawTileLazy(1, 3, Grid.RED, 0);
		
		b.putAndDrawTileLazy(2, 0, Grid.RED, 1);
		b.putAndDrawTileLazy(2, 1, Grid.RED, 0);
		b.putAndDrawTileLazy(2, 2, Grid.RED, 0);
		b.putAndDrawTileLazy(2, 3, Grid.RED, 4);
		b.putAndDrawTileLazy(2, 4, Grid.RED, 5);
		
		b.putAndDrawTileLazy(3, 0, Grid.RED, 1);
		b.putAndDrawTileLazy(3, 1, Grid.RED, 2);
		b.putAndDrawTileLazy(3, 2, Grid.RED, 0);
		b.putAndDrawTileLazy(3, 3, Grid.RED, 4);
		b.putAndDrawTileLazy(3, 4, Grid.RED, 5);
		
		b.putAndDrawTileLazy(4, 0, Grid.RED, 1);
		b.putAndDrawTileLazy(4, 1, Grid.RED, 2);
		b.putAndDrawTileLazy(4, 2, Grid.RED, 3);
		b.putAndDrawTileLazy(4, 3, Grid.RED, 4);
		b.putAndDrawTileLazy(4, 4, Grid.RED, 5);
		
		b.putAndDrawTileLazy(5, 0, Grid.RED, 0);
		b.putAndDrawTileLazy(5, 1, Grid.RED, 2);
		b.putAndDrawTileLazy(5, 2, Grid.RED, 0);
		b.putAndDrawTileLazy(5, 3, Grid.RED, 4);
		b.putAndDrawTileLazy(5, 4, Grid.RED, 0);
		
		b.putAndDrawTileLazy(6, 0, Grid.BLACK, 0);
		b.putAndDrawTileLazy(6, 1, Grid.BLACK, 2);
		b.putAndDrawTileLazy(6, 2, Grid.YELLOW, 2);
		b.putAndDrawTileLazy(6, 3, Grid.RED, 2);
		
		b.putAndDrawTileLazy(7, 1, Grid.BLACK, 2);
		b.putAndDrawTileLazy(7, 2, Grid.YELLOW, 2);
		b.putAndDrawTileLazy(7, 3, Grid.RED, 2);
		
		b.putAndDrawTileLazy(8, 1, Grid.BLACK, 2);
		b.putAndDrawTileLazy(8, 2, Grid.YELLOW, 2);
		b.putAndDrawTileLazy(8, 3, Grid.RED, 2);
		b.putAndDrawTileLazy(8, 4, Grid.BLACK, 0);
		b.putAndDrawTileLazy(8, 5, Grid.BLACK, 0);

		b.setImage(b.getPicture());

		//testing smiley stuff
		/*System.out.println(Board.makeCopy(b.getTile(0, 0)));
		ArrayList<ArrayList<Tile>> runs = b.getAllRuns();
		for (ArrayList<Tile> run : runs) {
			Tile first = run.get(0);
			System.out.println("RUN STARTING WITH " + first);
			System.out.println("copy: " + Board.makeCopy(run));
			System.out.println("no smiles: " + Board.makeCopyWithoutSmiles(run));
			System.out.println("replace smiles: " + Board.makeCopyReplaceSmiles(run));
			System.out.println("run is valid: " + Board.runIsValid(run));
		}	
		System.out.println("RUNS ARE VALID: " + b.allRunsAreValid());*/
		
		//testing has tile at
		/*String hasTileAt = "";
		for (int row = 0; row < b.getRows(); row++) {
			for (int col = 0; col < b.getCols(); col++) {
				hasTileAt += b.checkHasTileAt(row, col);
				if (col == (b.getCols() - 1))
					hasTileAt += "\n";
			}
		}
		System.out.println(hasTileAt);*/
		
		//testing runs
		/*ArrayList<Tile> tiles = b.getListOfTiles();
		for (Tile t : tiles) {
			System.out.print(t + " ");
		}
		System.out.println();
		
		ArrayList<Tile> firsts = b.getFirstTiles();
		System.out.println(firsts);
		for (Tile t : firsts) {
			System.out.print("Run starting with " + t + " has tiles ");
			System.out.print(b.getRun(t));
			System.out.println();
		}	
		System.out.println(b.getAllRuns());
		System.out.println("all moves are valid is " + b.movesAreValid());
		
		Tile first = b.getTile(0, 0);
		ArrayList<Tile> run = b.getRun(first);*/

	}
}
