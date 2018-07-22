package com.vivek.puzzle.eightpuzzle;

import aima.core.agent.Action;

/**
 * Class that Implements the Action interface (aima.core.agent.Action)
 * @author Vivek Venkatesh Ganesan
 *
 */
public class PuzzleAction implements Action{
	
	Moves moveToMake;
	
	PuzzleAction(Moves move) {
		this.moveToMake = move;
	}

	Moves getAction() {
		return moveToMake;
	}
	public boolean isNoOp() {
		return false;
	}
	
	public String toString() {
		if(moveToMake == Moves.UP)
			return "UP";
		else if(moveToMake == Moves.DOWN)
			return "DOWN";
		else if(moveToMake == Moves.LEFT)
			return "LEFT";
		else
			return "RIGHT";
	}

}
