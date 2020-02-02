
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Grid extends FlexiblePictureExplorer{
	private int rows, cols, imageWidth, imageHeight, gridWidth, 
	gridHeight, tileWidth, tileHeight, xBorder, yBorder; 
	private Tile[][] grid;
	private String name = "";

	public static final Color PEACH = new Color(254, 249, 234);
	public static final Color RED = new Color(255, 51, 51);
	public static final Color YELLOW = new Color(255, 204, 51);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color BLUE = new Color(51, 204, 255);
	public static final Color FAKEBLACK = new Color(1, 0, 0);
	public static final Color WHITE = new Color(255, 255, 255);

	public static Tile clickedTile = null;
	public static int[] clickedSpaceCoords = null;
	public static Grid clickedTileGrid = null;
	public static Grid clickedSpaceGrid = null;

	private Picture picture;
	private Graphics2D graphics;

	public Grid(int w, int h, int r, int c, String n) {
		super(new Picture(h, w));	
		picture = new Picture(h, w);
		graphics = picture.createGraphics();	
		name = n;
		picture.setTitle(n);
		cols = c;
		rows = r;
		imageWidth = w;
		imageHeight = h;		
		xBorder = imageWidth / 20;
		yBorder = imageHeight / 20;		
		gridWidth = imageWidth - (2 * xBorder);
		gridHeight = imageHeight - (2 * yBorder);		
		tileWidth = gridWidth / cols;
		tileHeight = gridHeight / rows;
		grid = new Tile[r][c];
	}

	public String toString() {
		return name;
	}

	//****************METHODS FOR MOVING TILES****************
	public void mouseClickedAction(DigitalPicture pict, Pixel pix) {
		clickedSpaceCoords = null;
		clickedSpaceGrid = null;
		int[] coords;
		//if the click is on the grid, get the coordinates
		if (checkOnGrid(pix)) {
			coords = getGridCoords(pix);
			int row = coords[0];
			int col = coords[1];	
			//if the click is not on a gridline, check if it's a tile or space
			if (!pix.getColor().equals(FAKEBLACK)) {
				if (checkHasTileAt(row, col) && clickedTile == null && getTile(row, col).isSelectable()) {
					selectTile(pix); //selects tile
				}
				else if (checkHasTileAt(row, col) && !(getTile(row, col).isSelectable())) {
					makePopUp("Can't move others' tiles on your first round.");
				}
				else if (checkHasTileAt(row, col) && clickedTile == getTile(row, col)) {
					selectTile(pix); //deselects tile
				}
				else if (pix.getColor().equals(WHITE) && !checkHasTileAt(row, col)) {
					setClickedSpace(pix); //selects space
				}

				if (clickedTile != null && clickedSpaceCoords != null) {
					if (clickedTileGrid instanceof Board && clickedSpaceGrid instanceof Rack)
						if (clickedTile.isMoveable() == true)
							moveTile();
						else
							makePopUp("Can't a tile that isn't yours to your rack!");
					else {
						moveTile();
					}
				}
			}
		}
	}

	public void moveTile() {
		int oldRow = clickedTile.getRow();
		int oldCol = clickedTile.getCol();
		int newRow = clickedSpaceCoords[0];
		int newCol = clickedSpaceCoords[1];

		clickedTileGrid.removeTileAndDrawEmpty(oldRow, oldCol);
		clickedTile.setPos(newRow, newCol);
		clickedTile.setGrid(clickedSpaceGrid);
		clickedTile.setSelected(false);
		
		clickedSpaceGrid.putAndDrawTile(clickedTile);
		
		clickedTile.setSelected(false);
		clickedTile = null;
		clickedTileGrid = null;
		clickedSpaceCoords = null;
		clickedSpaceGrid = null;

		setImage(picture);
	}

	public ArrayList<Tile> getListOfTiles() {
		ArrayList<Tile> list = new ArrayList<Tile>();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (checkHasTileAt(r,c)) {
					Tile copy = Board.makeCopy(getTile(r,c));
					list.add(copy);
				}
			}
		}
		return list;
	}

	//****************ACCESSORS****************

	public Picture getPicture() {
		return picture;
	}
	
	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Tile[][] getGrid() {
		return grid;
	}

	public int getNumOfTiles() {
		int num = 0;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (checkHasTileAt(r, c))
					num++;
			}
		}
		return num;
	}

	//****************METHODS FOR SELECTING****************
	public int[] selectSpace(Pixel pix) {
		int[] coords = getGridCoords(pix);
		return coords;
	}

	public void selectTile(Pixel pix) {
		int[] coords = getGridCoords(pix);
		int row = coords[0];
		int col = coords[1];

		Tile t = getTile(row, col);

		//if not selected, select
		if (!t.getSelected()) {
			t.setSelected(true);
			clickedTile = t;
			clickedTileGrid = this;
			drawSelection(t);
		}
		//if selected, deselect
		else {
			t.setSelected(false);
			clickedTile = null;
			clickedTileGrid = null;
			drawTile(t);
		}
	}

	public void setClickedSpace(Pixel pix) {
		clickedSpaceCoords = selectSpace(pix);
		clickedSpaceGrid = this;
	}

	//****************TILE METHODS****************
	public boolean checkHasTileAt(int r, int c) {
		boolean hasTile;
		if (grid[r][c] != null)
			hasTile = true;
		else
			hasTile = false;
		return hasTile;
	}

	public Tile getTile(int r, int c) {
		Tile t = grid[r][c];
		return t;
	}

	public void putTile(Tile t, int r, int c) {
		t.setPos(r, c);
		grid[r][c] = t;
	}

	public void putTile(Tile t) {
		grid[t.getRow()][t.getCol()] = t;
	}

	public void putTileLazy(int ro, int co, Color col, int num) {
		Tile t = new Tile(col, num);
		t.setPos(ro, co);
		grid[ro][co] = t;
	}

	public void removeTile(int r, int c) {
		grid[r][c] = null;
	}

	public void removeTileAndDrawEmpty(int r, int c) {
		removeTile(r, c);
		drawEmptySpace(r, c);
	}

	public void putAndDrawTile(Tile t) {
		putTile(t);
		drawTile(t);
	}

	public void putAndDrawTileLazy(int ro, int co, Color col, int num) {
		putTileLazy(ro, co, col, num);
		drawTileLazy(ro, co, col, num);
	}

	public boolean checkOnGrid(Pixel p) {
		int xCoord = p.getX();
		int yCoord = p.getY();
		boolean onGrid = false;		
		if (xCoord > xBorder && xCoord < xBorder + (cols * tileWidth)) {
			if (yCoord > yBorder && yCoord < yBorder + (rows * tileHeight)) {
				onGrid = true;
			}
		}		
		return onGrid;
	}

	//****************METHODS FOR COORDINATES****************
	public int[] getGridCoords(Pixel p) {
		int xCoord = p.getX();
		int yCoord = p.getY();

		int[] gridCoords = new int[2];

		int gridRangeMin = 0;
		int gridRangeMax = 0;
		//range is gridline+2 to next gridline-1

		for (int rowNum = 0; rowNum < rows; rowNum++) {
			gridRangeMin = yBorder + (rowNum * tileHeight) + 2; //after top line
			gridRangeMax = yBorder + ((rowNum + 1) * tileHeight) - 1; //before bottom line
			if (yCoord >= gridRangeMin && yCoord <= gridRangeMax) {
				gridCoords[0] = rowNum;
			}
		}

		for (int colNum = 0; colNum < cols; colNum++) {
			gridRangeMin = xBorder + (colNum * tileWidth) + 2; //after left line
			gridRangeMax = xBorder + ((colNum + 1) * tileWidth) - 1; //before right line
			if (xCoord >= gridRangeMin && xCoord <= gridRangeMax) {
				gridCoords[1] = colNum;
			}
		}	
		return gridCoords;
	}

	private int colToRegCoords(int c) {
		int regX = xBorder + (c * tileWidth);
		return regX;
	}

	private int rowToRegCoords(int r) {
		int regY = yBorder + (r * tileHeight);
		return regY;
	}

	//****************METHODS FOR DRAWING****************
	public void drawEmptySpace(int r, int c) {
		graphics.setColor(WHITE); //peach
		int rectXPos = colToRegCoords(c);
		int rectYPos = rowToRegCoords(r); 
		graphics.fillRect(rectXPos + 2, rectYPos + 2, tileWidth - 2, tileHeight - 2);
		setImage(picture);
	}

	public void drawGrid() {
		graphics.setColor(FAKEBLACK);
		for (int i = 0; i <= getCols(); i++) {
			graphics.drawRect((i * tileWidth) + xBorder, yBorder, 1, gridHeight);
		}

		//horizontal lines, <= because need one more line than number of rows
		for (int i = 0; i <= getRows(); i++) {
			graphics.drawRect(xBorder, (i * tileHeight) + yBorder, gridWidth, 1);
		}
		setImage(picture);
	}

	public void drawTile(Tile t) {
		int textXPos, textYPos;
		int rectXPos = colToRegCoords(t.getCol());
		int rectYPos = rowToRegCoords(t.getRow()); 
		int peachStartX = rectXPos + 2;

		graphics.setColor(PEACH); //peach
		graphics.fillRect(rectXPos + 2, rectYPos + 2, tileWidth - 2, tileHeight - 2); //centering and leaving borders

		if (t.isSmile()) {
			graphics.setColor(t.getColor());
			graphics.setFont(new Font("SansSerif", Font.PLAIN, 30));
			graphics.drawString("☺", peachStartX + 9, rectYPos + tileHeight - 22);
		}
		else {
			//centering text
			if (t.getNumber() >= 10)
				textXPos = (int) (rectXPos + (tileWidth / 4)); 
			else
				textXPos = (int) (rectXPos + (tileWidth / 2.5));
			textYPos = (int) (yBorder + ((t.getRow() + 1) * tileHeight) - (tileHeight / 2.5));
			graphics.setColor(t.getColor());
			graphics.setFont(new Font("SansSerif", Font.BOLD, 26));
			String number = "" + t.getNumber();
			graphics.drawString(number, textXPos, textYPos);
		}
	}

	public void drawTileLazy(int ro, int co, Color col, int num) {
		Tile t = new Tile(col, num);
		t.setPos(ro, co);
		drawTile(t);
	}

	public void drawSelection(Tile t) {
		//drawTile(t);
		int rectXPos = xBorder + (t.getCol() * tileWidth);
		int rectYPos = yBorder + (t.getRow() * tileHeight); 
		graphics.setColor(Color.CYAN);
		//draw cyan borders
		int borderWidth = 4;
		graphics.fillRect(rectXPos + 2, rectYPos + 2, borderWidth, tileHeight - 2); //left 
		graphics.fillRect(rectXPos + tileWidth - borderWidth, rectYPos + 2, borderWidth, tileHeight - 2); //right
		graphics.fillRect(rectXPos + 2, rectYPos + 2, tileWidth - 2, borderWidth); //top
		graphics.fillRect(rectXPos + 2, rectYPos + tileHeight - borderWidth, tileWidth - 2, borderWidth); //bot
	}

	public void drawSelectionLazy(int ro, int co, Color col, int num) {
		Tile t = new Tile(col, num);
		t.setPos(ro, co);
		drawSelection(t);
	}
}
