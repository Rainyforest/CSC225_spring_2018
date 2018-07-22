package com.vivek.puzzle.eightpuzzle;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class that helps in Maintaining the Tree Structure for RBFS
 * @author Vivek Venkatesh Ganesan
 *
 */
public class TreeNode implements Comparable<TreeNode>{
	PuzzleState puzzleState;
	Set<TreeNode> children;
	ArrayList<PuzzleAction> actionsSoFar;
	int depth;
	int h;
	int f; 
	int alternateH;
	int alternateF;
	
	TreeNode() {
		puzzleState = null;
		children = new TreeSet<TreeNode>();
		actionsSoFar = new ArrayList<PuzzleAction>();
	}

	TreeNode(PuzzleState puzzleState, int depth) {
		this.puzzleState = puzzleState;
		this.depth = depth;
		children = new TreeSet<TreeNode>();
		actionsSoFar = new ArrayList<PuzzleAction>();
	}
	protected PuzzleState getPuzzleState() {
		return puzzleState;
	}
	protected TreeSet<TreeNode> getChildren() {
		return (TreeSet<TreeNode>) children;
	}
	protected ArrayList<PuzzleAction> getActionsSoFar() {
		return actionsSoFar;
	}
	protected int getDepth() {
		return depth;
	}
	protected int getH() {
		return h;
	}
	protected int getF() {
		return f;
	}

	protected void setPuzzleState(PuzzleState puzzleState) {
		this.puzzleState = puzzleState;
	}
	protected void setChildren(Set<TreeNode> children) {
		this.children = children;
	}
	protected void setActionsSoFar(ArrayList<PuzzleAction> actionsSoFar) {
		this.actionsSoFar = actionsSoFar;
	}
	protected void setDepth(int depth) {
		this.depth = depth;
	}
	protected void setH(int h) {
		this.h = h;
	}
	protected void setF(int f) {
		this.f = f;
	}
	protected int getAlternateH() {
		return alternateH;
	}

	protected int getAlternateF() {
		return alternateF;
	}

	protected void setAlternateH(int alternateH) {
		this.alternateH = alternateH;
	}

	protected void setAlternateF(int alternateF) {
		this.alternateF = alternateF;
	}

	protected void addChild(TreeNode node) {
		children.add(node);
	}
	public int compareTo(TreeNode o) {
		// Sort Children based on the F value found from the Primary Heuristic
		int cmp = getF() > o.getF() ? 1 : (getF() < o.getF()) ? -1 : 0;
		
		if(cmp == 0) {
			// Breaking Ties in F Value
			// Resolve Ties using alternate Heuristics & depth, position
			
			cmp = getAlternateF() > o.getAlternateF() ? 1 : (getAlternateF() < o.getAlternateF()) ? -1 : 0;
			
			if(cmp == 0) {
				cmp = getDepth() > o.getDepth() ? 1: (getDepth() < o.getDepth()) ? -1 : 0;
			}
			if(cmp == 0) {
				
				int thisX = puzzleState.getZeroNode().getX(); 
				int thatX = o.getPuzzleState().getZeroNode().getX();
				cmp = thisX > thatX ? 1 : (thisX < thatX) ? -1 : 0;

				if(cmp == 0) {
					int thisY = puzzleState.getZeroNode().getY(); 
					int thatY = o.getPuzzleState().getZeroNode().getY();
					cmp = thisY > thatY ? 1 : (thisY < thatY) ? -1 : 0;
				}
			}

		}
		
		return cmp;
	}
	public String toString() {
		return puzzleState.toString() + " F = " + getF() + " Alternate F = " + getAlternateF() + " H = " + getH() + " Depth =" + getDepth() + " Action = " + getActionsSoFar() ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((puzzleState == null) ? 0 : puzzleState.hashCode());
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
		TreeNode other = (TreeNode) obj;
		if (puzzleState == null) {
			if (other.puzzleState != null)
				return false;
		} else if (!puzzleState.equals(other.puzzleState))
			return false;
		return true;
	}

	
}
