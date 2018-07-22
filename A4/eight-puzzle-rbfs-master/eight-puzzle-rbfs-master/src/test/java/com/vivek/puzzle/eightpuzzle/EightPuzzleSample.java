package com.vivek.puzzle.eightpuzzle;

public class EightPuzzleSample {
	private PuzzleState initialPuzzleState;
	private PuzzleState goalPuzzleState;
	private int expectedSteps; 
	
	EightPuzzleSample(int[][] initialBoard, int[][] goalBoard, int expectedSteps) {
		this.initialPuzzleState = generatePuzzleState(initialBoard);
		this.goalPuzzleState = generatePuzzleState(goalBoard);
		this.expectedSteps = expectedSteps;
		
	}
	
	private PuzzleState generatePuzzleState(int [][] givenPuzzleBoard) {
		int boardSize = givenPuzzleBoard.length;
		Node puzzleBoard[][] = new Node[boardSize][boardSize];
		Node sortedValues[] = new Node[boardSize * boardSize];
		Node zeroNode = null;
		for(int i=0;i<boardSize;i++) {
			for(int j=0;j<boardSize;j++) {
				int thisValue = givenPuzzleBoard[i][j];
				Node thisNode = new Node(i, j, thisValue);
				puzzleBoard[i][j] = thisNode;
				if(thisValue == 0) {
					zeroNode = thisNode;
				}
				if(thisValue < boardSize * boardSize)
					sortedValues[thisValue] = thisNode;
			}
		}
		
		PuzzleState puzzleState = new PuzzleState(puzzleBoard, zeroNode);
		puzzleState.setSortedValues(sortedValues);
		return puzzleState;
	}
	
	public int getExpectedSteps() {
		return expectedSteps;
	}

	protected PuzzleState getInitialPuzzleState() {
		return initialPuzzleState;
	}

	protected PuzzleState getGoalPuzzleState() {
		return goalPuzzleState;
	}
	
}
