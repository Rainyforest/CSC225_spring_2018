package com.vivek.puzzle.eightpuzzle;

import aima.core.search.framework.GoalTest;

/**
 * Test for Goal State
 * @author Vivek Venkatesh Ganesan
 *
 */
public class GoalStateChecker implements GoalTest {
	
	private PuzzleState goalState; 
	
	GoalStateChecker(Object goalState) {
		this.goalState = (PuzzleState)goalState;
	}
	
	/**
	 * Given a Puzzle State, check if it is the goal state
	 * @param state	Given Puzzle state
	 */
	public boolean isGoalState(Object state) {
		PuzzleState thisState = (PuzzleState) state;
		if(thisState.equals(goalState))
			return true;
		else
			return false;
	}

}
