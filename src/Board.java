import java.awt.Color;
import java.util.ArrayList;

public class Board extends Grid {
	public static int width = 1000;
	public static int height = 600;
	public static int rows = 9;
	public static int cols = 20;
	
	public Board(String n) {
		super(width, height, rows, cols, n);
	}

	//****************METHODS FOR CHECKING IF VALID MOVES****************
	//----copy methods----//
	public static Tile makeCopy(Tile orig) {
		Tile copy = new Tile(orig.getColor(), orig.getNumber());
		copy.setMoveable(orig.isMoveable());
		copy.setSmile(orig.isSmile());
		copy.setPos(orig.getRow(), orig.getCol());
		copy.setGrid(orig.getGrid());
		return copy;
	}
	
	public static ArrayList<Tile> makeCopy(ArrayList<Tile> origRun) {
		ArrayList<Tile> copiedRun = new ArrayList<Tile>();
		for (Tile t : origRun) {
			copiedRun.add(makeCopy(t));
		}
		return copiedRun;
	}
	
	public static ArrayList<ArrayList<Tile>> makeCopyRuns(ArrayList<ArrayList<Tile>> origRuns) {
		ArrayList<ArrayList<Tile>> copy = new ArrayList<ArrayList<Tile>>();
		for (ArrayList<Tile> run : origRuns) {
			copy.add(makeCopy(run));
		}
		return copy;
	}
	
	public static ArrayList<Tile> makeCopyWithoutSmiles(ArrayList<Tile> origRun) {
		ArrayList<Tile> copiedRun = new ArrayList<Tile>();
		Tile copiedTile;
		for (Tile t : origRun) {
			if (!(t.isSmile())) {
				copiedTile = makeCopy(t);
				copiedRun.add(copiedTile);
			}
		}
		return copiedRun;
	}

	public static ArrayList<Tile> makeCopyReplaceSmiles(ArrayList<Tile> run) {
		boolean sameNums = false;
		ArrayList<Tile> copiedRun = makeCopy(run);
		ArrayList<Tile> noSmiles = makeCopyWithoutSmiles(run);
		ArrayList<Integer> noSmilesNums = new ArrayList<Integer>();
		for (Tile t : noSmiles) 
			noSmilesNums.add(t.getNumber());
		if (sameObjects(noSmilesNums)) 
			sameNums = true;	
		int num = 0;
		//same num dif color
		if (sameNums) {
			for (Tile t : copiedRun) {
				if (t.isSmile() && noSmilesNums.size() > 1) {
					num = noSmilesNums.get(0);
					t.setNum(num);
					t.setColor(Color.MAGENTA);
				}
			}
		}
		//consec num same color
		else {
			Color c = Color.MAGENTA;
			for (Tile t : copiedRun) {
				if (!(t.isSmile())) 
					c = t.getColor();
			}
			for (int i = 0; i < copiedRun.size(); i++) {
				Tile thisTile = copiedRun.get(i);
				if (thisTile.isSmile()) {
					//two smile tiles in a row
					Tile next = null;
					Tile prev = null;
					Tile doubleNext = null;
					
					if ((i-1) >= 0) {
						prev = copiedRun.get(i-1);
					}
					if ((i+2) < copiedRun.size()) {
						doubleNext = copiedRun.get(i+2);
					}
					if ((i+1) < copiedRun.size()) {
						next = copiedRun.get(i+1);
					}
					
					if (i != copiedRun.size() - 1) {
						if (next.isSmile()) {
							if (doubleNext != null)
								num = doubleNext.getNumber() - 2;
							else
								num = prev.getNumber() + 1;
						}
						else {
							num = next.getNumber() - 1;
						}	
					}
					else {
						num = prev.getNumber() + 1;
					}
					thisTile.setNum(num);
					thisTile.setColor(c);
				}
			}
		}
		return copiedRun;
	}

	//----finding run methods----//
	public boolean isFirstTile(Tile t) {
		boolean isFirstTile = false;
		if (t == null) {
			isFirstTile = false;
		}
		else {
			int row = t.getRow();
			int col = t.getCol();
			if (col == 0) 
				isFirstTile = true;
			else if (!checkHasTileAt(row, col - 1)) 
				isFirstTile = true;
		}
		return isFirstTile;
	}
	
	public ArrayList<Tile> getFirstTiles() {
		ArrayList<Tile> firstTiles = new ArrayList<Tile>();
		for (Tile[] row : getGrid()) {
			for (Tile t : row) {
				//find tiles where left is blank and right is tile: isFirstTile()
				if (isFirstTile(t)) 
					firstTiles.add(makeCopy(t));
			}
		}
		return firstTiles;
	}

	public ArrayList<Tile> getRun(Tile firstTile) {
		int row = firstTile.getRow();
		int col = firstTile.getCol();
		Tile currentTile;
		ArrayList<Tile> run = new ArrayList<Tile>();
		while (checkHasTileAt(row, col)) {
			currentTile = makeCopy(getTile(row, col));
			run.add(currentTile);
			col++;
			if (col == cols) {
				col = 0;
				row++;
			}
		}
		return run;
	}

	public ArrayList<ArrayList<Tile>> getAllRuns() {
		ArrayList<Tile> firsts = getFirstTiles();
		ArrayList<ArrayList<Tile>> runs = new ArrayList<ArrayList<Tile>>();
		for (Tile first : firsts) {
			runs.add(getRun(first));
		}
		return runs;
	}
	
	//----checking valid methods----//
	public static boolean sameObjects(ArrayList al) {
		boolean isSame = true;
		for (int i = 0; i < al.size(); i++) {
			if (!(al.get(0).equals(al.get(i)))) 
				isSame = false;
		}
		return isSame;
	}

	public static boolean difColors(ArrayList<Color> al) {
		boolean isDif = true;
		for (int i = 0; i < al.size() - 1; i++) {
			for (int j = i + 1; j < al.size(); j++) 
				if (al.get(i).equals(al.get(j)))
					isDif = false;
		}
		return isDif;
	}

	public static boolean consecutiveNums(ArrayList<Integer> al) {
		boolean areConsec = true;
		for (int i = 0; i < al.size() - 1; i++) {
			if (al.get(i) != (al.get(i + 1) - 1)) 
				areConsec = false;
		}
		return areConsec;
	}
	public static int sumOfRuns(ArrayList<ArrayList<Tile>> runs) {
		int sum = 0;
		ArrayList<ArrayList<Tile>> copy = makeCopyRuns(runs);
		for (ArrayList<Tile> run : copy) {
			run = makeCopyReplaceSmiles(run);
			for (Tile t : run) {
				sum += t.getNumber();
			}
		}
		return sum;
	}

	public static boolean runIsValid(ArrayList<Tile> run) {
		boolean valid = true;
		ArrayList<Tile> copy = makeCopyReplaceSmiles(run);
		ArrayList<Integer> copyNums = new ArrayList<Integer>();
		ArrayList<Color> copyColors = new ArrayList<Color>();
		if (copy.size() < 3)
			valid = false;
		for (Tile t : copy) {
			copyNums.add(t.getNumber());
			copyColors.add(t.getColor());
			if (t.getNumber() < 0 || t.getNumber() > 13)
				valid = false;
		}
		//same num, dif color
		boolean type1 = sameObjects(copyNums) && difColors(copyColors);
		//consec num, same color
		boolean type2 = sameObjects(copyColors) && consecutiveNums(copyNums);
		if (!(type1 || type2)) 
			valid = false;
		return valid;
	}

	public boolean allRunsAreValid() {
		boolean valid = true;
		for (ArrayList<Tile> run : getAllRuns()) {
			if (!runIsValid(run)) 
				valid = false;
		}
		return valid;
	}
}
