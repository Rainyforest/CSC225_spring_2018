/* NinePuzzle.java
   CSC 225 - Spring 2017
   Assignment 4 - Template for the 9-puzzle
   
   This template includes some testing code to help verify the implementation.
   Input boards can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
	java NinePuzzle
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. boards.txt), run the program with
    java NinePuzzle boards.txt
	
   The input format for both input methods is the same. Input consists
   of a series of 9-puzzle boards, with the '0' character representing the 
   empty square. For example, a sample board with the middle square empty is
   
    1 2 3
    4 0 5
    6 7 8
   
   And a solved board is
   
    1 2 3
    4 5 6
    7 8 0
   
   An input file can contain an unlimited number of boards; each will be 
   processed separately.
  
   B. Bird    - 07/11/2014
   M. Simpson - 11/07/2015
*/

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
public class NinePuzzle{

	//The total number of possible boards is 9! = 1*2*3*4*5*6*7*8*9 = 362880
	public static final int NUM_BOARDS = 362880;


	/*  SolveNinePuzzle(B)
		Given a valid 9-puzzle board (with the empty space represented by the 
		value 0),return true if the board is solvable and false otherwise. 
		If the board is solvable, a sequence of moves which solves the board
		will be printed, using the printBoard function below.
	*/
	public static boolean SolveNinePuzzle(int[][] B){
		Graph nine_puzzle=new Graph(NUM_BOARDS);
		return nine_puzzle.buildPuzzleGraph(getIndexFromBoard(B));
	}
	static class Graph{
		private final int V;
		private ArrayList<Integer>[] adj;
		int count=0;
		public Graph(int V){
			this.V = V;
			adj = (ArrayList<Integer>[]) new ArrayList[V];
			for (int v = 0; v < V; v++){
				adj[v] = new ArrayList<Integer>();
			}
		}
		public void addEdge(int v, int w){
			adj[v].add(w);
			adj[w].add(v);
		}
		public boolean buildPuzzleGraph(int v){
			count++;
			int[][] B=getBoardFromIndex(v);
			if(v==0){
				System.out.println("YYYYYYYYYYYEEEEEEEEEESSSSSSSSS");
				return true;
			}else{
			/*
				for(int i = 0; i < adj[v].size(); i++) { 
					System.out.print(adj[v].get(i)+"##");
				} //print adj*/
				int i=0;//row of space
				int j=0;//col of space
				int w=-1;
				boolean flag_up=false;
				boolean flag_down=false;
				boolean flag_left=false;
				boolean flag_right=false;
				System.out.println("\ncount:"+count+"---------------------------------------------");
				printBoard(B);
				for(int row=0;row<3;row++){
					for(int col=0;col<3;col++){
						if (B[row][col]==0){
							i=row;
							j=col;
						}
					}
				}//Search for space
				if (i<2){
					B[i][j]=B[i+1][j];
					B[i+1][j]=0;
					w=getIndexFromBoard(B);
					if (adj[w].isEmpty()&&!(flag_up||flag_down||flag_left||flag_right)){
						addEdge(v,w);
						flag_down=buildPuzzleGraph(w);
					}
					B[i+1][j]=B[i][j];
					B[i][j]=0;
			
				}//Down
				if (i>0){
					B[i][j]=B[i-1][j];
					B[i-1][j]=0;
					w=getIndexFromBoard(B);
					if (adj[w].isEmpty()&&!(flag_up||flag_down||flag_left||flag_right)){
						addEdge(v,w);
						flag_up=buildPuzzleGraph(w);
					}
					B[i-1][j]=B[i][j];
					B[i][j]=0;
				}//Up
				
				if (j>0){
					B[i][j]=B[i][j-1];
					B[i][j-1]=0;
					w=getIndexFromBoard(B);
					if (adj[w].isEmpty()&&!(flag_up||flag_down||flag_left||flag_right)){
						addEdge(v,w);
						flag_left=buildPuzzleGraph(w);
					}
					B[i][j-1]=B[i][j];
					B[i][j]=0;
				
				}//Left
				if (j<2){
					B[i][j]=B[i][j+1];
					B[i][j+1]=0;
					w=getIndexFromBoard(B);
					if (adj[w].isEmpty()&&!(flag_up||flag_down||flag_left||flag_right)){
						addEdge(v,w);
						flag_right=buildPuzzleGraph(w);
					}
					B[i][j+1]=B[i][j];
					B[i][j]=0;
				}//Right
				return flag_up||flag_down||flag_left||flag_right;
			}//end else
			
		}
		
	}
	
	/*  printBoard(B)
		Print the given 9-puzzle board. The SolveNinePuzzle method above should
		use this method when printing the sequence of moves which solves the input
		board. If any other method is used (e.g. printing the board manually), the
		submission may lose marks.
	*/
	public static void printBoard(int[][] B){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++)
				System.out.printf("%d ",B[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	
	
	/* Board/Index conversion functions
	   These should be treated as black boxes (i.e. don't modify them, don't worry about
	   understanding them). The conversion scheme used here is adapted from
		 W. Myrvold and F. Ruskey, Ranking and Unranking Permutations in Linear Time,
		 Information Processing Letters, 79 (2001) 281-284. 
	*/
	public static int getIndexFromBoard(int[][] B){
		int i,j,tmp,s,n;
		int[] P = new int[9];
		int[] PI = new int[9];
		for (i = 0; i < 9; i++){
			P[i] = B[i/3][i%3];
			PI[P[i]] = i;
		}
		int id = 0;
		int multiplier = 1;
		for(n = 9; n > 1; n--){
			s = P[n-1];
			P[n-1] = P[PI[n-1]];
			P[PI[n-1]] = s;
			
			tmp = PI[s];
			PI[s] = PI[n-1];
			PI[n-1] = tmp;
			id += multiplier*s;
			multiplier *= n;
		}
		return id;
	}
		
	public static int[][] getBoardFromIndex(int id){
		int[] P = new int[9];
		int i,n,tmp;
		for (i = 0; i < 9; i++)
			P[i] = i;
		for (n = 9; n > 0; n--){
			tmp = P[n-1];
			P[n-1] = P[id%n];
			P[id%n] = tmp;
			id /= n;
		}
		int[][] B = new int[3][3];
		for(i = 0; i < 9; i++)
			B[i/3][i%3] = P[i];
		return B;
	}
	

	public static void main(String[] args){
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		
		Scanner s;

		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read boards until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading board %d\n",graphNum);
			int[][] B = new int[3][3];
			int valuesRead = 0;
			for (int i = 0; i < 3 && s.hasNextInt(); i++){
				for (int j = 0; j < 3 && s.hasNextInt(); j++){
					B[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < 9){
				System.out.printf("Board %d contains too few values.\n",graphNum);
				break;
			}
			System.out.printf("Attempting to solve board %d...\n",graphNum);
			long startTime = System.currentTimeMillis();
			boolean isSolvable = SolveNinePuzzle(B);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			if (isSolvable)
				System.out.printf("Board %d: Solvable.\n",graphNum);
			else
				System.out.printf("Board %d: Not solvable.\n",graphNum);
		}
		graphNum--;
		System.out.printf("Processed %d board%s.\n Average Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>1)?totalTimeSeconds/graphNum:0);
		
	}
}
