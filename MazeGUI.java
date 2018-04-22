import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;

public class MazeGUI extends Application {

	private static final int START_X = 200;
	private static final int START_Y = 200;
	private static int n = 0;
	private static Pane pane;
	private static int startX = 200;
	private static int startY = 200;
    private static ArrayList<Room> rooms, otherRooms;
    private static Queue roomQueue, inMethodQueue;
    //private AnimationTimer timer;
    
	
	public static void main(String[] args) {
		
		launch(args);

	}

	private static void makeMaze(String[][] maze, int n) {
		int j = 0;
		boolean keepGoing = true;
		int i = 0;
		int k = 0;
		while(keepGoing) {
			Room thisRoom = new Room(maze[i][j]);
			makeRoom(thisRoom, i , j, k);
			k++;
			if (j == 4) {
				i++;
				j = 0;
				if (i >= 5) {
					keepGoing = false;
				}
				
			} else {
				j++;
			}
		}
	}
		
		

	private static void makeRoom(Room thisRoom, int i, int j, int k) {
		rooms.get(k).setStartX(startX); 
		rooms.get(k).setStartY(startY);
		if (!thisRoom.north) {
			Line line = new Line(startX,startY, startX + 30, startY);
			pane.getChildren().add(line);
		}
		if (!thisRoom.south) {
			Line line = new Line(startX,startY + 30, startX + 30, startY + 30);
			pane.getChildren().add(line);
		}
		if (!thisRoom.east) {
			Line line = new Line(startX + 30,startY, startX + 30, startY + 30);
			pane.getChildren().add(line);
		}
		if (!thisRoom.west) {
			Line line = new Line(startX,startY, startX, startY + 30);
			pane.getChildren().add(line);
		} 
		n++;
		if (j == 4) {
			startX = START_X;
			startY = startY + (30) + 4;
		} else {
			startX = startX + 30 + 4;
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		pane = new Pane();
		int n = 0;
		String[][] maze = null;     //This makes the maze as a string matrix
		rooms = new ArrayList<Room>();   //this arraylist is for all the rooms in the maze
		try { //This will read in a text file
			File file = new File("maze.txt");
			FileReader fr = new FileReader(file);
			Scanner fileScanner = new Scanner(fr);
			String nValue = fileScanner.nextLine();
			n = Integer.parseInt(nValue);
			maze = new String[n][n];
			int i = 0;
			int j = 0;
			int k = 0;
			
			while (fileScanner.hasNext())
			{
				String fileLine = fileScanner.nextLine();
				maze[j][i] = fileLine;   //This adds the the next line of the file into the maze matrix
				Room thisRoom = new Room(maze[j][i]);  //this makes a room with the current room in the maze
				thisRoom.roomNumber = k; //assigns the roomNumber
				rooms.add(thisRoom); //this adds the room to the arrayList
				
				System.out.println("Room Number: " + k + "  value: " + maze[j][i]);
				k++;
				if (i == 4) { //if the maze is at its final rightmost point in the specific matrix, assign 0 to i and add 1 to j
					i = 0;
					j++;
				} else {  //adds 1 to i if i < 4
					i++;
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.print("File not found.");
		} 
		System.out.println();
		System.out.println();
		
		Button button = new Button(); //this button is for breadth first search BFS()
		button.setMinWidth(75);
		button.setText("BFS");
		button.setLayoutX(400);
		button.setLayoutY(400);
		pane.getChildren().add(button);
		makeMaze(maze,n); // this makes the maze, I used lines as barriers
		makeTexts(); //this makes a text in between each of room in the maze
		button.setOnAction((e) -> {  
			BFS(); 
			button.setDisable(true);
		});
		
		Scene scene = new Scene(pane,700,700);
		stage.setScene(scene);
		stage.show();
		
	}
	
	private void makeTexts() { //this creates a text object in the rooms of the maze, the text is initially an empty string
		for (int i = 0; i<rooms.size(); i++) {
			Room room = rooms.get(i);
			room.text = new Text("");
			room.text.setLayoutX(room.startX + 10);
			room.text.setLayoutY(room.startY + 15);
			pane.getChildren().add(room.text);
		}
		
	}

	private void BFS() {
		   inMethodQueue = new Queue(); //this queue holds rooms that have more than 1 direction option to move
		   roomQueue = new Queue(); //this queue holds the rooms based on the this order, 1. North, 2. South, 3. East, 4.West
		   int i = 0; //will hold room number
		   Room room1 = rooms.get(i); //this is the start room
		   Room prevRoom = room1; //prevRoom is an attribute of the Room object I created, it points to the room visited previous to thisRoom
		   room1.visited = true; 
		   roomQueue.enqueue(room1); //add first Room to queue
		   boolean keepGoing = true; 
		   while(roomQueue.size != 0 && keepGoing) { //while keepGoing is true and roomQueue is not empty
			   Room thisRoom = roomQueue.dequeue(); //dequeue the next room
			   if (i != 0) { // while its not the first room, room0
				   thisRoom.prevRoom = prevRoom; //assign prevRoom to thisRoom's prevRoom attribute
				   prevRoom = thisRoom; //assign prevRoom to current room
			   }
			   System.out.println("Visiting room" + thisRoom.roomNumber);
			   i = thisRoom.roomNumber; //make i equal to this room number
		       visitNode(thisRoom); //visit the room
		       
		       boolean noMoves = true; //this says if there are moves or not
			   if(thisRoom.equals(rooms.get(rooms.size()-1))) { //if thisRoom equals the final room, keepGoing is false and BFS is over
				   keepGoing = false;
			   } else {
				   for (int k = 0; k < 1; k++) { 
					  
					   if ((thisRoom.west) && !(rooms.get(i-1).visited)) { //if the room to west of thisRoom is true, and not visited, enqueue that room
						   roomQueue.enqueue(rooms.get(i-1));
						   rooms.get(i-1).visited = true;
						   thisRoom.numberMoves++;
						   noMoves = false;
					   }
					   if ((thisRoom.east) && !(rooms.get(i+1).visited)) { //if the room to east of thisRoom is true, and not visited, enqueue that room
						   roomQueue.enqueue(rooms.get(i+1));
						   rooms.get(i+1).visited = true;
						   thisRoom.numberMoves++;
						   noMoves = false;
					   }
					   if ((thisRoom.south) && !(rooms.get(i+5).visited)) {//if the room to south of thisRoom is true, and not visited, enqueue that room
						   roomQueue.enqueue(rooms.get(i+5));
						   rooms.get(i+5).visited = true;
						   thisRoom.numberMoves++;
						   noMoves = false;
					   } 
					   
					  if ((thisRoom.north) && (i >4) && !(rooms.get(i-5).visited)) { //if the room to north of thisRoom is true, and the room number is greater than 4, and not visited, enqueue that room
						   roomQueue.enqueue(rooms.get(i-5));
						   rooms.get(i-5).visited = true;
						   noMoves = false;
						   thisRoom.numberMoves++;
					  }
					  
					  if (thisRoom.numberMoves > 1) { //if numberMoves for thisRoom  is greater than 1, enqueue room in inethodQueue
						  inMethodQueue.enqueue(thisRoom);
					  }
					  
					  if (noMoves) { //if no moves, go back
						  Room room = thisRoom;
						  while (room != inMethodQueue.peek()) { //while room does not equal the next room in queue, remove the text from the room, go to the prevRoom
							  System.out.println("Going back from " + room.roomNumber + " To " + room.prevRoom.roomNumber);
							  removeText(room);
							  room = room.prevRoom;
						  }
						  inMethodQueue.dequeue(); //take next queue off
					  }
				    }
					  
				   }
			   }
		   System.out.println("Done");
		   }
			
	
	
	

	private void removeText(Room room) {
		room.text.setText("");
		
	}


	private void visitNode(Room thisRoom) {
		thisRoom.text.setText("X");
		
	}



}
