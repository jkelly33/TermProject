import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class test {

  public static void main(String[] args) {
    
    final int n = 5; // for n x n maze
    final int numWalls = 4; //each cell has 4 walls
    final int N = 0, S = 1, E = 2, W = 3;
    int i = 0;
    int j = 0;
    
    
    int[][] walls = new int[n*n][numWalls]; //each cell (n*n) has array of numWalls
                                            //i.e - walls[0][0] is first cell north wall
                                            // walls[0][1]:south, walls[0][2]:east, walls[0][3]:west
    
    //set all walls to 1 (closed)
    for(int c = 0; c < n * n; c++) {
      for(int w = 0; w < numWalls; w++) {
        walls[c][w] = 1;
      }
    }
    
    walls[0][N] = 0; //first cell north wall is open
    walls[(n * n) - 1][S] = 0; //last cell south wall is open
    
    DisjointSets s = new DisjointSets(n * n);
    
    while(s.find(0) != s.find((n * n) - 1)) {
      ArrayList<Integer> adjacentCells = new ArrayList<Integer>();
      
      i = ThreadLocalRandom.current().nextInt(0, (n * n));
      if(i < ((n * n) - n)) {
        //can go down
        adjacentCells.add(i + n);
      }
      if(i >= n) {
        //can go up
        adjacentCells.add(i - n);
      }
      if(i % n >= 0 && i % n < (n - 1)) {
        //can go right
        adjacentCells.add(i + 1);
      }
      if(i % n > 0 && i % n <= (n - 1)) {
        //can go left
        adjacentCells.add(i - 1);
      }
      
      j = ThreadLocalRandom.current().nextInt(0, adjacentCells.size());
      j = adjacentCells.get(j);      

      if(s.find(i) != s.find(j)) {
        //open the doors
        s.union(s.find(i), s.find(j));
        
        if(j == (i + n)) {
          //open the bottom wall
          walls[i][S] = 0;
          walls[j][N] = 0;
        } else if(j == (i - n)) {
          //open the top wall
          walls[i][N] = 0;
          walls[j][S] = 0; 
        } else if(j == (i + 1)) {
          //open the right wall
          walls[i][E] = 0;
          walls[j][W] = 0;
        } else if(j == (i - 1)) {
          //open the left wall
          walls[i][W] = 0;
          walls[j][E] = 0;
        }
      }
    }   
    System.out.println(s);
    System.out.println();
    for(int c = 0; c < n * n; c++) {
      for(int w = 0; w < numWalls; w++) {
        System.out.print(walls[c][w] + " ");
      }
      System.out.println();
    }
    
  }

}




//Room[][] maze;

/*try {
  File file = new File("maze.txt");
  FileReader fr = new FileReader(file);
  Scanner fileScanner = new Scanner(fr);
  int n = fileScanner.nextInt();
  fileScanner.nextLine();
  maze = new Room[n][n];
  int r = 0;
  int c = 0;
  
  
  while(fileScanner.hasNextLine()) {
    String line = fileScanner.nextLine();
    Scanner lineScanner = new Scanner(line);
    boolean door[] = new boolean[Room.NUM_DOORS];
    int d = 0;
    while(lineScanner.hasNextInt()) {
      door[d] = lineScanner.nextInt() == 0;
      d++;
    }
    maze[c][r] = new Room(door);
    if(r == 4) {
      r = 0;
      c++;
    } else {
    r++;
    }
  }
} catch(FileNotFoundException e) {
  System.out.println("File not found.");
}*/