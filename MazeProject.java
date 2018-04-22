import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeProject {
	
	ArrayList<Room> rooms = new ArrayList<Room>();

	public static void main(String[] args) {
		int n = 0;
		String[][] maze = null;
		try {
			File file = new File("maze.txt");
			FileReader fr = new FileReader(file);
			Scanner fileScanner = new Scanner(fr);
			String nValue = fileScanner.nextLine();
			n = Integer.parseInt(nValue);
			maze = new String[n][n];
			int i = 0;
			int j = 0;
			
			while (fileScanner.hasNext())
			{
				String fileLine = fileScanner.nextLine();
				maze[j][i] = fileLine;
				System.out.println(maze[j][i]);
				if (i == 4) {
					i = 0;
					j++;
				} else {
					i++;
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.print("File not found.");
		} 
		makeMaze(maze,n);

	}

	private static void makeMaze(String[][] maze, int n) {
		int j = 0;
		makeTopOfMaze(n);
		boolean keepGoing = true;
		int i = 0;
		while(keepGoing) {
			//Room thisRoom = new Room(maze[i][j], i, j);
			if (j == 4) {
				i++;
				j = 0;
				System.out.println();
				if (i > 4) {
					keepGoing = false;
				}
				
			} else {
				i++;
			}
			
		 }
		
     
	}

	private static void makeTopOfMaze(int n) {
		for (int i = 0; i < n; i++) {
			if (i > 0) {
				System.out.print("__ ");
			} else {
				System.out.print("   ");
			}
		}
		System.out.println();
	}

}
