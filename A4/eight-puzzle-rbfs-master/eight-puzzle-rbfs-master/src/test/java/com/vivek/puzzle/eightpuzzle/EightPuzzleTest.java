package com.vivek.puzzle.eightpuzzle;

import java.util.ArrayList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.GoalTest;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class EightPuzzleTest extends TestCase {
	public EightPuzzleTest(String name) {
		super(name);
	}
	

	public void testSteps() {
		GenerateSamples samples = new GenerateSamples();
		ArrayList<EightPuzzleSample> eightPuzzleSample = samples.getEightPuzzleSamples();
		for(EightPuzzleSample s: eightPuzzleSample) {
			Object initialState = s.getInitialPuzzleState();
			GoalTest goalTest = new GoalStateChecker(s.getGoalPuzzleState());
			ActionsFunction actionsFunction = new ActionsFunctionSet();
			ResultFunction resultFunction = new ResultFunctionBoard();
			Problem eightPuzzleProblem = new Problem(initialState, actionsFunction, resultFunction, goalTest);
		
			Search search = new Search(eightPuzzleProblem, s.getInitialPuzzleState(), s.getGoalPuzzleState());
			TreeNode solution = search.getSolution();
			List<Action> actions = new ArrayList<Action>();
			if(solution!=null) {
				for(PuzzleAction action: solution.getActionsSoFar()) {
					actions.add((Action) action);
				}
			}
			
			assertEquals("Expected Steps = " + s.getExpectedSteps() + "Actual Stpes = " + actions.size(), s.getExpectedSteps(), actions.size());
			
		}
	}
}
