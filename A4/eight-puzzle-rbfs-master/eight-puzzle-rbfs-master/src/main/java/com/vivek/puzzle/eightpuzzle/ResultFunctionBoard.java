package com.vivek.puzzle.eightpuzzle;

import aima.core.agent.Action;
import aima.core.search.framework.ResultFunction;

/**
 * Result Function generates the next possible state for a given state and a given action
 * @author Vivek Venkatesh Ganesan
 *
 */
public class ResultFunctionBoard implements ResultFunction {
	
	/**
	 * Returns the new state of s after applying action a
	 * @param s Given Puzzle State
	 * @param a Action that needs to be applied on the Given PuzzleState
	 */
	public Object result(Object s, Action a) {
		PuzzleState puzzleState = (PuzzleState) s;
		PuzzleAction puzzleAction =(PuzzleAction) a;
		
		
		// Swap the blank tile with the adjacent tile in the direction of the movement
		// The action to be taken comes from the ActionFunctionSet
		
		if(puzzleAction.getAction() == Moves.UP) {
			Node zeroNode = puzzleState.getZeroNode();
			
			int zeroNodeX = zeroNode.getX();
			int zeroNodeY = zeroNode.getY();
			
			Node[][] puzzleBoard = puzzleState.getCurrentPuzzleBoard();
			Node upNode = puzzleBoard[zeroNodeX-1][zeroNodeY];
			zeroNode.setX(zeroNodeX - 1); 
			upNode.setX(upNode.getX() + 1);
			// Swap the upNode and ZeroNode (By doing this we have to update them)
			puzzleBoard[zeroNodeX][zeroNodeY] = upNode;
			puzzleBoard[zeroNodeX-1][zeroNodeY] = zeroNode;
			
			Node[] sortedValues = puzzleState.getSortedValues();
			int val = upNode.getValue();
			sortedValues[val] = upNode;
			sortedValues[0] = zeroNode;
			PuzzleState newState = new PuzzleState(puzzleBoard, zeroNode); 
			newState.setSortedValues(sortedValues);
			
			return newState;
		}
		else if(puzzleAction.getAction() == Moves.DOWN) {
			Node zeroNode = puzzleState.getZeroNode();
			int zeroNodeX = zeroNode.getX();
			int zeroNodeY = zeroNode.getY();
			
			Node[][] puzzleBoard = puzzleState.getCurrentPuzzleBoard().clone();
			Node downNode = puzzleBoard[zeroNodeX+1][zeroNodeY];
			
			zeroNode.setX(zeroNodeX + 1); 
			downNode.setX(downNode.getX() - 1);
			// Swap the downNode and ZeroNode (By doing this we have to update them)
			puzzleBoard[zeroNodeX][zeroNodeY] = downNode;
			puzzleBoard[zeroNodeX+1][zeroNodeY] = zeroNode;
			
			Node[] sortedValues = puzzleState.getSortedValues().clone();
			int val = downNode.getValue();
			sortedValues[val] = downNode;
			sortedValues[0] = zeroNode;
			PuzzleState newState = new PuzzleState(puzzleBoard, zeroNode); 
			newState.setSortedValues(sortedValues);
			
			return newState;
		}
		else if(puzzleAction.getAction() == Moves.LEFT) {
			Node zeroNode = puzzleState.getZeroNode();
			int zeroNodeX = zeroNode.getX();
			int zeroNodeY = zeroNode.getY();
			
			Node[][] puzzleBoard = puzzleState.getCurrentPuzzleBoard();
			Node leftNode= puzzleBoard[zeroNodeX][zeroNodeY-1];
			
			zeroNode.setY(zeroNodeY - 1); 
			leftNode.setY(leftNode.getY() + 1);
			// Swap the leftNode and ZeroNode (By doing this we have to update them)
			puzzleBoard[zeroNodeX][zeroNodeY] = leftNode;
			puzzleBoard[zeroNodeX][zeroNodeY-1] = zeroNode;
			
			Node[] sortedValues = puzzleState.getSortedValues();
			int val = leftNode.getValue();
			sortedValues[val] = leftNode;
			sortedValues[0] = zeroNode;
			PuzzleState newState = new PuzzleState(puzzleBoard, zeroNode); 
			newState.setSortedValues(sortedValues);
			
			return newState;
		}
		else if(puzzleAction.getAction() == Moves.RIGHT){
			Node zeroNode = puzzleState.getZeroNode();
			int zeroNodeX = zeroNode.getX();
			int zeroNodeY = zeroNode.getY();
			
			Node[][] puzzleBoard = puzzleState.getCurrentPuzzleBoard();
			Node rightNode = puzzleBoard[zeroNodeX][zeroNodeY+1];
			
			zeroNode.setY(zeroNodeY + 1); 
			rightNode.setY(rightNode.getY() - 1);
			// Swap the rightNode and ZeroNode (By doing this we have to update them)
			puzzleBoard[zeroNodeX][zeroNodeY] = rightNode;
			puzzleBoard[zeroNodeX][zeroNodeY+1] = zeroNode;
			
			Node[] sortedValues = puzzleState.getSortedValues();
			int val = rightNode.getValue();
			sortedValues[val] = rightNode;
			sortedValues[0] = zeroNode;
			PuzzleState newState = new PuzzleState(puzzleBoard, zeroNode); 
			newState.setSortedValues(sortedValues);
			
			return newState;
		}
		return puzzleState;
	}

}
