package com.vivek.puzzle.eightpuzzle;

import java.util.ArrayList;

/**
 * Class that computes the Heuristic Functions needed to Solve the Problem
 * @author Vivek Venkatesh Ganesan
 *
 */

/*
 * Two Heuristic Functions:
 * 1. Mahattan Distance (sum of distances of each tile from the tile in the goal state) 
 * 2. Hamming Priority (Count of number of displaced tiles)
 * 3. Linear Conflict (Extension to Manhattan Distance)
 * 
 */
enum HeuristicType {
	MANHATTAN, HAMMING, LINEARCONFLICT
}
public class Heuristic {
	private int cost;
	private HeuristicType type;
	private PuzzleState currentState;
	private PuzzleState goalState;
	
	Heuristic(PuzzleState currentState, PuzzleState goalState, HeuristicType type) {
		this.currentState = currentState;
		this.goalState = goalState;
		this.type = type;
	}
	
	public int getCost() {
		if(type == HeuristicType.MANHATTAN) {
			cost = manhattanHeuristic();
		}
		else if(type == HeuristicType.HAMMING){
			cost = hammingHeuristic();
		}
		else if(type == HeuristicType.LINEARCONFLICT) {
			cost = manhattanHeuristic();
			cost = cost + getLinearConflict();
		}
		return cost;
	}
	
	/**
	 * Manhattan Heuristic
	 */
	private int manhattanHeuristic() {
		int manhattanDistance = 0;
		Node[][] currentPuzzleBoard = currentState.getCurrentPuzzleBoard();
		for(int i=0; i< currentPuzzleBoard.length; i++) {
			for(int j=0; j < currentPuzzleBoard[i].length; j++) {
				int val = currentPuzzleBoard[i][j].getValue();
				int sumOfDistances = Math.abs(goalState.getNodeWithValue(val).getX() - currentPuzzleBoard[i][j].getX()) + Math.abs(goalState.getNodeWithValue(val).getY() - currentPuzzleBoard[i][j].getY()); 
				
				if(val!=0) {
					manhattanDistance = manhattanDistance + sumOfDistances;
				}
			}
		}
		return manhattanDistance;
	}
	/**
	 * Helper Functions to compute the combinations of length 2 of elements in a row or column
	 */
	private ArrayList<String> generateCombinations(Node[] nodeArr, int n, int r) {
		ArrayList<String> combinations = new ArrayList<String>();
		int data[] = new int[r];
		int arr[] = new int[nodeArr.length];
		int pos = 0;
		for(Node i: nodeArr) {
			arr[pos] = i.getValue();
			pos++;
		}
		
		return combinationsHelper(arr, data, 0, n-1, 0, r, combinations);
	}
	
	private ArrayList<String> combinationsHelper(int arr[], int data[], int start, int end, int index, int r, ArrayList<String> combinations) {
		if(index == r) {
			StringBuffer sb = new StringBuffer(); 
			for(int j=0;j<r;j++)
				sb.append(String.valueOf(data[j]));
			combinations.add(sb.toString());
			return combinations;
		}
		for (int i=start; i<=end && end-i+1 >= r-index; i++)
	    {
	        data[index] = arr[i];
	        combinations = combinationsHelper(arr, data, i+1, end, index+1, r, combinations);
	    }
		return combinations;
	}
	
	/**
	 * Compute the Linear Conflict and Add Penalty to Manhattan Distance
	 * @return
	 */
	private int getLinearConflict() {
		/*
		 * Two tiles tj and tk are in a linear conflict if tj and tk are in the same line, 
		 * the goal positions of tj and tk are both in that line, tj is to the right of tk and 
		 * goal position of tj is to the left of the goal position of tk.
		 */
		int linearConflict = 0;
		for(int i=0;i<currentState.getCurrentPuzzleBoard().length;i++) {
			ArrayList<String> initialCombinations = generateCombinations(currentState.getCurrentPuzzleBoard()[i], currentState.getCurrentPuzzleBoard()[i].length, 2);
			ArrayList<String> goalCombinations = generateCombinations(goalState.getCurrentPuzzleBoard()[i], goalState.getCurrentPuzzleBoard()[i].length, 2);
			
			for(String combination : initialCombinations) {
				if(goalCombinations.contains(new StringBuilder(combination).reverse().toString())) {
					linearConflict+=2;
					
				}
			}
		}
		
		for(int i=0;i<currentState.getCurrentPuzzleBoard().length;i++) {
			Node[] initialColumn = new Node[currentState.getCurrentPuzzleBoard().length];
			Node[] goalColumn = new Node[goalState.getCurrentPuzzleBoard().length];
			for(int k=0;k<currentState.getCurrentPuzzleBoard().length;k++) {
				initialColumn[k] = currentState.getCurrentPuzzleBoard()[k][i];
				goalColumn[k] = goalState.getCurrentPuzzleBoard()[k][i];
			}
			ArrayList<String> initialCombinations = generateCombinations(initialColumn, initialColumn.length, 2);
			ArrayList<String> goalCombinations = generateCombinations(goalColumn, goalColumn.length, 2);
			
			for(String combination : initialCombinations) {
				if(goalCombinations.contains(new StringBuilder(combination).reverse().toString())) {
					linearConflict+=2;
					
				}
			}
			
		}
		return linearConflict;
	}
	
	/**
	 * Hamming Heuristic
	 */
	private int hammingHeuristic() {
		int hammingPriority = 0;
		Node[][] currentPuzzleBoard = currentState.getCurrentPuzzleBoard();
		for(int i=0; i< currentPuzzleBoard.length; i++) {
			for(int j=0; j < currentPuzzleBoard[i].length; j++) {
				Node current = currentPuzzleBoard[i][j];
				int val = current.getValue();
				if(val!=0) {
					if((current.getX() == goalState.getNodeWithValue(val).getX()) && (current.getY() == goalState.getNodeWithValue(val).getY())) {
						hammingPriority += 0;
					}
					else {
						hammingPriority += 1;
					}
				}
			}
		}

		return hammingPriority;
	}

}
