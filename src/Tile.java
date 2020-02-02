import java.awt.Color;

public class Tile {
	private Color color;
	private int number;
	private boolean selected;
	private Grid grid;
	private boolean canMoveToRack;
	private boolean isSmile;
	private boolean selectable;
	
	public static final Color RED = new Color(255, 51, 51);
	public static final Color YELLOW = new Color(255, 204, 51);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color BLUE = new Color(51, 204, 255);
	
	private int[] pos = new int[2]; //row, column; if in pile, -1 -1
	
	
	Tile(Color c, int n) {
		color = c;
		number = n;
		isSmile = (n == 0);
		pos[0] = -1;
		pos[1] = -1;
		selectable = true;
	}
	
	Tile(Color c) {
		color = c;
		number = 0;
		isSmile = true;
		pos[0] = -1;
		pos[1] = -1;
		selectable = true;
	}
	
	public boolean equals(Object o) {
		Tile t = (Tile) o;
		boolean equal = true;
		if (o == null) 
			equal = false;
		else if (!(o instanceof Tile)) 
			equal = false;
		else if (this.number != t.getNumber()) 
			equal = false;
		else if (!(this.color.equals(t.getColor()))) 
			equal = false;
		else if (this.isSmile != t.isSmile()) 
			equal = false;	
		else if (this.getRow() != t.getRow()) 
			equal = false;
		else if (this.getCol() != t.getCol()) 
			equal = false;
		else if (this.canMoveToRack != t.isMoveable()) 
			equal = false;
		return equal;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getNumber() {
		return number;
	}
	
	public int getRow() {
		return pos[0];
	}
	
	public int getCol() {
		return pos[1];
	}
	
	public int[] getPos() {
		return pos;
	}
	
	public boolean getSelected() {
		return selected;
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public boolean isSmile() {
		return isSmile;
	}
	
	public boolean isSelectable() {
		return selectable;
	}
	
	public boolean isMoveable() {
		return canMoveToRack;
	}
	
	public void setNum(int n) {
		number = n;
	}
	
	public void setPos(int r, int c) {
		pos[0] = r;
		pos[1] = c;
	}
	
	public void setSelected(boolean isClicked) {
		selected = isClicked;
	}
	
	public void setGrid(Grid g) {
		grid = g;
	}
	
	public void setSmile(boolean smile) {
		isSmile = smile;
	}
	
	public void setMoveable(boolean b) {
		canMoveToRack = b;
	}

	public void setSelectable(boolean b) {
		selectable = b;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public String toString() {
		String name = "";
		String colorName = "";
		if (getColor().equals(RED)) 
			colorName += "RED";
		else if (getColor().equals(YELLOW))
			colorName += "YEL";
		else if (getColor().equals(BLACK))
			colorName += "BLA";
		else if (getColor().equals(BLUE))
			colorName += "BLU";
		name = colorName + " " + getNumber();
		return name;
	}
}
