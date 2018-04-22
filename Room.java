import javafx.scene.text.Text;

public class Room {

	int n;  //number of rooms available
	//String[][] maze;
	boolean north = false, south = false, west = false, east = false, visited = false;
	public String value;
	public int roomNumber, numberMoves;
	public int startX, startY;
	public Room prevRoom;
	public Text text;
	
	public void setStartX(int x) {
		this.startX = x;
	}
	
	public int getStartX() {
		return this.startX;
	}
	
	public void setStartY(int y) {
		this.startY = y;
	}
	
	public int getStartY() {
		return this.startY;
	}
	
    public Room(String thisString) {
    	value = thisString;
    	if (thisString.charAt(0) == '0') {
    		north = true;
    		n++;
    	}
    	if (thisString.charAt(2) == '0') {
    		south = true;
    		n++;
    	} 
    	if (thisString.charAt(4) == '0') {
    		east = true;
    	    n++;
    	}
    	if (thisString.charAt(6) == '0') {
    		west = true;
    		n++;
    	}
    }
}
