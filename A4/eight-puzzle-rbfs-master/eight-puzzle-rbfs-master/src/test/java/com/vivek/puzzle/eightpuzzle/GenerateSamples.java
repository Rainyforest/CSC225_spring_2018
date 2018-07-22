package com.vivek.puzzle.eightpuzzle;

import java.util.ArrayList;

public class GenerateSamples {
	
	private ArrayList<EightPuzzleSample> eightPuzzleSamples = new ArrayList<EightPuzzleSample>();
	
	GenerateSamples() {
		//{{},{},{}};
		int[][] sampleInitial1 = {{1,4,2},{6,3,5},{7,0,8}};
		int[][] sampleInitial2 = {{1,4,2},{3,7,0},{6,8,5}};
		int[][] sampleInitial3 = {{3,2,5},{4,1,8},{6,7,0}};
		int[][] sampleGoal1 = {{0,1,2},{3,4,5},{6,7,8}};
		
		int[][] sampleInitial4 = {{8,3,5},{4,1,6},{2,7,0}};
		int[][] sampleGoal2 = {{1,2,3},{8,0,4},{7,6,5}};
		
		EightPuzzleSample eightPuzzleSample1 = new EightPuzzleSample(sampleInitial1, sampleGoal1, 5);
		eightPuzzleSamples.add(eightPuzzleSample1);
		
		EightPuzzleSample eightPuzzleSample2 = new EightPuzzleSample(sampleInitial2, sampleGoal1, 5);
		eightPuzzleSamples.add(eightPuzzleSample2);
		
		EightPuzzleSample eightPuzzleSample3 = new EightPuzzleSample(sampleInitial3, sampleGoal1, 6);
		eightPuzzleSamples.add(eightPuzzleSample3);
		
		EightPuzzleSample eightPuzzleSample4 = new EightPuzzleSample(sampleInitial4, sampleGoal2, 14);
		eightPuzzleSamples.add(eightPuzzleSample4);
		
	}

	protected ArrayList<EightPuzzleSample> getEightPuzzleSamples() {
		return eightPuzzleSamples;
	}
	
	
}
