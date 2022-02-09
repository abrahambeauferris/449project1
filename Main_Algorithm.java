import java.util.ArrayList;
import java.util.Arrays;

public class Main_Algorithm {
	
	boolean trueBest = false;
	int currBest = 0;
	int tasks[] = {1,2,3,4,5};
	//int tasks[] = {1,2,3,4,5,6,7,8};
	char taskNames[] = {'A','B','C','D','E','F','G','H'};
	int bestMatch[] = new int[5];
	int forcedPartial[][] = {{1,1}, {2,2}};
	int forbiddenMachine[][] = {{1,3}, {2,1}};
	int tooNearHard[][] = {{4,5}, {4,1}};
	int pVals[][] = {
			{ 0, 5,10, 5,15},
			{15, 0,20, 5,10},
			{10,10, 5,20, 5},
			{25,10, 5, 5, 0},
			{ 5,10,15, 0, 5},
	};
	
	public void initalize() {
		currBest = pVals[0][4] + pVals[1][3] + pVals[2][2] + pVals[3][1] + pVals[4][0];
		int initCombo[] = {5,4,3,2,1};
		for (int i = 0; i < initCombo.length; i++) {
			bestMatch[i] = initCombo[i];
		}
	}
	
	//calculates any hard constraints
	public boolean hardChecker(ArrayList<Integer> a) {
		return true;
	}
	
	//used to calculate penalty of a non-full combination, only using p-values
	public int softChecker(ArrayList<Integer> a, ArrayList<Integer> remaining) {
		
		int penValue = 0;
		
		for(int i = 0; i < a.size(); i++) {
			penValue += pVals[i][a.get(i)];
		}
		
		for(int i = 0; i < tasks.length; i++) {
			
			if(i >= a.size()) {
				int lpv = 0;
				boolean active = false;
				for(int j = 0; j < tasks.length; j++) {
					if(remaining.contains(j+1)) {
						if(active == false) {
							lpv = pVals[i][j];
							active = true;
						}else if(active == true) {
							if(pVals[i][j] < lpv){
								lpv = pVals[i][j];
							}
						}	
					}
				}
				penValue += lpv;
			}
		}	
		return penValue;
	}
	
	//only used to calculate final penalty of a full combination
	public int penaltyCalc(ArrayList<Integer> a) {
		return 0;
	}
	
	//goes through a "tree" using arraylists and bounds bad subtrees
	public void branchBound(ArrayList<Integer> a) {
		
		ArrayList<Integer> remaining = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
		for(int i = 0; i < remaining.size(); i++) {
			if (a.contains(remaining.get(i))) {
				remaining.remove(i);
			}
		}
		
		
		for(int i = 0; i < remaining.size(); i++) {
			
			ArrayList<Integer> combo = new ArrayList<Integer>();
			
			for(int j = 0; j < a.size(); j++) {
				
				combo.add(a.get(i));
			}
			combo.add(remaining.get(i));
			remaining.remove(i);
			if(hardChecker(combo) == true) {
				if (combo.size() == tasks.length) {
					int thisBest = penaltyCalc(combo);
					if(thisBest >= currBest) {
						currBest = thisBest;
						for(int j = 0; j < combo.size(); j++) {
							bestMatch[j] = combo.get(i);
						}
					}
				} else {
					int thisBest = softChecker(a, remaining);
					if(thisBest <= currBest) {
						branchBound(combo);
					}
				}			
			}
		}
	}
}
