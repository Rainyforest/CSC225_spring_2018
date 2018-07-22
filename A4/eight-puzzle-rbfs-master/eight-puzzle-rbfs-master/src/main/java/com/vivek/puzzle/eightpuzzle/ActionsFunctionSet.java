package com.vivek.puzzle.eightpuzzle;

import java.util.HashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;

/**
 * Find out the possible actions for a given Puzzle State
 * @author Vivek Venkatesh Ganesan
 *
 */
public class ActionsFunctionSet implements ActionsFunction {
	
	/**
	 * Returns Set of Actions Possible for the given State s 
	 * @param Given State of the Puzzle
	 * 
	 */
	public Set<Action> actions(Object s) {
		
		// There are four possible actions that the blank tile can take:
		// UP, DOWN, LEFT, RIGHT
		// The possible actions might be limited if the blank tile is in the corners
		
		PuzzleState puzzleState = (PuzzleState) s;
		Node zeroNode = puzzleState.getZeroNode();
		Set<Action> possibleActions = new HashSet<Action>();
		
		int x = zeroNode.getX();
		int y = zeroNode.getY();
		if(!(y-1 < 0)) {
			Action leftAction = new PuzzleAction(Moves.LEFT);
			possibleActions.add(leftAction);
		}
		if(!(y+1 > (puzzleState.getCurrentPuzzleBoard().length - 1))) {
			Action rightAction = new PuzzleAction(Moves.RIGHT);
			possibleActions.add(rightAction);
		}
		
		if(!(x-1 < 0)) {
			Action upAction = new PuzzleAction(Moves.UP);
			possibleActions.add(upAction);
		}
		
		if(!(x+1 > (puzzleState.getCurrentPuzzleBoard().length - 1))) {
			Action downAction = new PuzzleAction(Moves.DOWN);
			possibleActions.add(downAction);
		}
		return possibleActions;
	}

}
