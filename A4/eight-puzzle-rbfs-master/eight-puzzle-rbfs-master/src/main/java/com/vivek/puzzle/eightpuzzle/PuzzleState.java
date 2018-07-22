package com.vivek.puzzle.eightpuzzle;

import java.util.Arrays;
import aima.core.agent.State;

/**
 * Class that maintains the State of the Puzzle
 * Implements the State interface (aima.core.agent.State)
 * @author Vivek Venkatesh Ganesan
 *
 */
public class PuzzleState implements State, Cloneable {
	Node currentPuzzleBoard[][]; // Current Puzzle Board
	Node zeroNode; // Position of the blank square
	Node sortedValues[]; // Linear representation of the Puzzle Board
	
	PuzzleState(Node currentPuzzleBoard[][], Node zeroNode) {
		this.currentPuzzleBoard = currentPuzzleBoard;
		this.zeroNode = zeroNode;
	}
	
	Node[][] getCurrentPuzzleBoard() {
		return currentPuzzleBoard;
	}
	
	Node getZeroNode() {
		return zeroNode; 
	}
	
	
	void setSortedValues(Node[] sortedValues) {
		this.sortedValues = sortedValues;
	}
	
	Node getNodeWithValue(int val) {
		if(val < sortedValues.length) {
			return sortedValues[val];
		}
		return null;
	}
	
	
	Node[] getSortedValues() {
		return sortedValues;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(sortedValues);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PuzzleState other = (PuzzleState) obj;
		if (!Arrays.equals(sortedValues, other.sortedValues))
			return false;
		return true;
	}

	public String toString() {
		
		StringBuffer output = new StringBuffer();
		for(int i=0; i<currentPuzzleBoard.length; i++) {
			for(int j=0; j< currentPuzzleBoard[i].length; j++) {
				output.append(currentPuzzleBoard[i][j]);
				output.append("\t");
			}
			output.append("\n");
		}
		return output.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		PuzzleState clonedObject = (PuzzleState)super.clone();
		Node[][] tempPuzzleBoard = new Node[currentPuzzleBoard.length][currentPuzzleBoard[0].length];
		for(int i=0;i<currentPuzzleBoard.length;i++) {
			for(int j=0;j<currentPuzzleBoard[i].length;j++) {
				tempPuzzleBoard[i][j] = (Node) currentPuzzleBoard[i][j].clone();
			}
		}
		clonedObject.currentPuzzleBoard = tempPuzzleBoard;
		clonedObject.zeroNode = (Node)zeroNode.clone();
		
		Node[] tempSortedValues = new Node[sortedValues.length];
		for(int i=0;i<sortedValues.length;i++)
			tempSortedValues[i] = (Node) sortedValues[i].clone();
		clonedObject.sortedValues = tempSortedValues;
		return clonedObject;
	}

	
	
}
