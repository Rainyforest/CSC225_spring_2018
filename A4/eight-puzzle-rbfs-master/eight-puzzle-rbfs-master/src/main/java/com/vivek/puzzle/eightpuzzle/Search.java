package com.vivek.puzzle.eightpuzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import aima.core.agent.Action;
import aima.core.search.framework.Problem;

/**
 * Class that performs the Recursive Best-First Search to obtain the best solution
 * @author Vivek Venkatesh Ganesan
 *
 */
public class Search {
	private boolean goalSucc;
	private PuzzleState goalPuzzleState;
	private PuzzleState initialPuzzleState;
	private Set<TreeNode> closedList = new HashSet<TreeNode>();
	private TreeNode solution;
	private HeuristicType heuristic1, heuristic2;
	
	/**
	 * 
	 * @param problem	Problem Solved by the Agent
	 * @param initialPuzzleState	Initial State of the Puzzle
	 * @param goalPuzzleState	Goal State of the Puzzle
	 */
	Search(Problem problem, PuzzleState initialPuzzleState, PuzzleState goalPuzzleState) {
		
		this.goalPuzzleState = goalPuzzleState;	
		this.initialPuzzleState = initialPuzzleState;
		
		goalSucc = false;
		
		// Specify the Primary Heuristic Function and the Secondary Heuristic Functions
		// Secondary Heuristic Functions are used to resolve ties in F Value because of the Heuristic 
		
		HeuristicType combinations[][] = {{HeuristicType.HAMMING, HeuristicType.MANHATTAN},
										{HeuristicType.MANHATTAN, HeuristicType.HAMMING},
										{HeuristicType.LINEARCONFLICT, HeuristicType.HAMMING},
										{HeuristicType.HAMMING, HeuristicType.LINEARCONFLICT}};
		int bestPosition = Integer.MAX_VALUE;
		int bestSteps = Integer.MAX_VALUE;
		TreeNode[] possibleSolutions = new TreeNode[combinations.length];
		for(int i=0; i<combinations.length;i++) {
			closedList.clear();
			goalSucc = false;
			heuristic1 = combinations[i][0];
			heuristic2 = combinations[i][1];
			
			Heuristic first = new Heuristic(initialPuzzleState, goalPuzzleState, heuristic1);
			Heuristic second = new Heuristic(initialPuzzleState, goalPuzzleState, heuristic2);
			
			TreeNode root = new TreeNode(initialPuzzleState, 0);
			root.setH(first.getCost());
			root.setF(root.getDepth() + root.getH());
			root.setAlternateH(second.getCost());
			root.setAlternateF(root.getDepth() + root.getAlternateH());
			
			possibleSolutions[i] = rbfs(problem, root,root.getDepth(), Integer.MAX_VALUE);
			if(possibleSolutions[i] != null) {
				if(possibleSolutions[i].getDepth() < bestSteps) {
					bestSteps = possibleSolutions[i].getDepth();
					bestPosition = i;
				}
				//System.out.println(i+ " " + possibleSolutions[i].getDepth());
			} 
			
		}
		if(bestPosition != Integer.MAX_VALUE)
			solution = possibleSolutions[bestPosition];
		else {
			System.out.println("Can't find solution. Maybe depth is growing too large.");
		}
			
		
	}
	
	/**
	 * 
	 * @param problem	Problem Solved by this Agent
	 * @param N			Current TreeNode (initially the root) 
	 * @param V			Current Depth
	 * @param B			Bound on the depth
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private TreeNode rbfs(Problem problem, TreeNode N, int V, int B) {
		if(N.getF() > B) {
			return N;
		}
		if(problem.getGoalTest().isGoalState(N.getPuzzleState())) {
			goalSucc = true;
			return N;
		}
		if(V >= 500) {
			// Depth growing too large. Stopping further caluclations to avoid Stack Overflow
			goalSucc = true;
			return null;
		}
		closedList.add(N);
		Set<Action> possibleActions= problem.getActionsFunction().actions(N.getPuzzleState());
		
		// Find out the possible actions that the zero node can take now
		
		for(Action action: possibleActions) {
			PuzzleAction puzzleAction = (PuzzleAction) action;
			PuzzleState tempPuzzleState = null;
			try {
				tempPuzzleState = (PuzzleState)(problem.getResultFunction().result((PuzzleState)N.getPuzzleState().clone(), puzzleAction));
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			TreeNode childNode = new TreeNode(tempPuzzleState, V+1);
			ArrayList<PuzzleAction> getActionSoFar = (ArrayList<PuzzleAction>) N.getActionsSoFar().clone();
			getActionSoFar.add(puzzleAction);
			childNode.setActionsSoFar(getActionSoFar);
			Heuristic heuristic = new Heuristic(childNode.getPuzzleState(), goalPuzzleState, heuristic1);
			childNode.setH(heuristic.getCost());
			////childNode.setF(childNode.getDepth() + childNode.getH());
			childNode.setF(Math.max(childNode.getDepth() + childNode.getH(), N.getF()));
			Heuristic alternateHeuristic = new Heuristic(childNode.getPuzzleState(), goalPuzzleState, heuristic2);
			childNode.setAlternateH(alternateHeuristic.getCost());
			////childNode.setAlternateF(childNode.getDepth() + childNode.getAlternateH());
			childNode.setAlternateF(Math.max(childNode.getDepth() + childNode.getAlternateH(), N.getAlternateF()));
			
			
			// Each Child Node corresponds to the next possible state from the current node's state
			if(!closedList.contains(childNode))
			{
				N.addChild(childNode);
			}
			
		}
		TreeSet<TreeNode> nodeQueue = (TreeSet<TreeNode>) N.getChildren().clone();
		
		if(nodeQueue.isEmpty()) {
			return null; // failure
		}
		TreeNode retNode = null;
		while(!nodeQueue.isEmpty() && !goalSucc) 
		{
			// Keep track of the best and the second best states to reach the goal
			TreeNode best = nodeQueue.pollFirst();
			TreeNode alternative;
			if(best.getF() > B) {
				return best;
			}
			if(nodeQueue.size() >= 1) {
				alternative = nodeQueue.first();
			}
			else {
				alternative = null;
			}

			int min  = B;
			if(alternative!=null) {
				if(alternative.getF() <= B) {
					min = alternative.getF();
				}
			}
			// Recursive Call Made here
			retNode = rbfs(problem, best, best.getDepth(), min);
			if(retNode != null) {
				nodeQueue.add(retNode);
			}
		}
		return retNode;
	} // End of RBFS function
	
	public TreeNode getSolution() {
		return solution;
	}
	
	public PuzzleState getInitialPuzzleState() {
		return initialPuzzleState;
	}
	public PuzzleState getGoalPuzzleState() {
		return goalPuzzleState;
	}

}
