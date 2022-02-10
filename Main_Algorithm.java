import java.util.ArrayList;
import java.util.Arrays;

public class Main_Algorithm {
	
	boolean trueBest = false;
	int currBest = 0;
	ArrayList<Integer> tasks = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
	//int tasks[] = {1,2,3,4,5,6,7,8};
	char taskNames[] = {'A','B','C','D','E','F','G','H'};
	int bestMatch[] = new int[5];
	int forcedPartial[][] = {{1,1}, {2,2}};
	int forbiddenMachine[][] = {{1,3}, {2,1}};
	int tooNearHard[][] = {{4,5}, {4,1}};
	int tooNearSoft[][] = {{4,1,1}, {1,2,5}};
	int pVals[][] = {
			{0,5,10,5,15},
			{15,0,20,5,10},
			{10,10,5,20,5},
			{25,10,5,5,0},
			{5,10,15,0,5},
	};
	
	
	public void initalize() { 
		ArrayList<Integer> initCombo = new ArrayList<Integer>(tasks);
		currBest = penaltyCalc(initCombo);
		for (int i = 0; i < initCombo.size(); i++) {
			bestMatch[i] = initCombo.get(i);
		}
		System.out.println("First combo: " + Arrays.toString(bestMatch));
		System.out.println("with penalty: " + currBest);
	}
	
	//calculates any hard constraints
	public boolean hardChecker(ArrayList<Integer> a) {
		return true;
	}
	
	//used to calculate penalty of a non-full combination, only using p-values
	public int softChecker(ArrayList<Integer> a, ArrayList<Integer> remaining) {
		
		int penValue = 0;
		
		for(int i = 0; i < a.size(); i++) {
			int val = a.get(i);
			penValue += pVals[i][val-1];
		}
		
		for(int i = 0; i < tasks.size(); i++) {
			
			if(i >= a.size()) {
				int lpv = 0;
				boolean active = false;
				for(int j = 0; j < tasks.size(); j++) {
					if(remaining.contains(j+1)) {
						if(active == false) {
							lpv = pVals[i][j];
							active = true;
						}else{
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
		
		int penValue = 0;
		for(int i = 0; i < a.size(); i++) {
			penValue += pVals[i][(a.get(i))-1];
		}
		for(int i = 0; i <tooNearSoft.length; i++) {
			for(int j = 0; j < a.size(); j++) {
				if(a.get(j) == tooNearSoft[i][0]) {
					if((j+1) == a.size()) {
						if(a.get(0) == tooNearSoft[i][1]) {
							penValue += tooNearSoft[i][2];
						}
					}else if(a.get(j+1) == tooNearSoft[i][1]) {
						penValue += tooNearSoft[i][2];
					}
				}
			}
		}
			
		return penValue;
	}
	
	//goes through a "tree" using arraylists and bounds bad subtrees
	public void branchBound(ArrayList<Integer> a) {
		
		ArrayList<Integer> remaining = new ArrayList<Integer>();
		for(int i = 0; i < tasks.size(); i++) {
			if(!a.contains(tasks.get(i))){
				remaining.add(tasks.get(i));
			}
		}
		
		int remSize = remaining.size();
		for(int i = 0; i < remSize; i++) {
			
			ArrayList<Integer> combo = new ArrayList<Integer>(a);
			
			combo.add(remaining.get(0));
			remaining.remove(0);
			if(hardChecker(combo) == true) {
				if (combo.size() == tasks.size()) {
					int thisBest = penaltyCalc(combo);
					if(thisBest <= currBest) {
						currBest = thisBest;
						for(int j = 0; j < combo.size(); j++) {
							bestMatch[j] = combo.get(j);
						}
					}
				} else {
					int thisBest = softChecker(combo, remaining);
					if(thisBest <= currBest) {
						branchBound(combo);
					}
				}			
			}
		}
	}

	public static void main(String[] args) {
		ArrayList a = new ArrayList<Integer>();
		Main_Algorithm main_Algorithm = new Main_Algorithm();
		main_Algorithm.initalize();
		main_Algorithm.branchBound(a);
		System.out.println("Best combo: " + Arrays.toString(main_Algorithm.bestMatch));
		System.out.println("with penalty: " + main_Algorithm.currBest);
	}
}
