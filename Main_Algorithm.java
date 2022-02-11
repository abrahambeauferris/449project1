package input;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main_Algorithm {
	
	boolean trueBest = false;
	int currBest = 0;
	int tasksAlt[] = {1,2,3,4,5,6,7,8};
	ArrayList<Integer> tasks = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8));
	char taskNames[] = {'A','B','C','D','E','F','G','H'};
	int bestMatch[] = new int[8];
	int forcedPartial[][] = {{1,2}, {2,3}};
	int forbiddenMachine[][] = {{3,3}, {4,8}};
	int tooNearHard[][] = {{4,5}, {4,1}};
	int tooNearSoft[][] = {{4,1,1}, {1,2,5}};
	char charBestMatch[] = new char[8];
	/*int pVals[][] = {
			{10,5,10,5,15},
			{15,11,20,5,10},
			{0,10,5,20,5},
			{25,10,5,5,0},
			{5,10,15,0,5},
	};*/
	int pVals[][] = {{0,87,73,60,48,85,10,40},
			 		{10,0,79,67,05,89,31,75},
			 		{41,10,0,16,18,51,71,27},
			 		{63,95,10,0,21,34,32,83},
			 		{11,20,53,10,50,06,16,82},
			 		{38,36,66,23,10,0,48,44},
			 		{13,28,75,51,67,10,0,81},
			 		{17,66,42,33,72,82,10,0}};
	
	
	public void initalize() { 
		
		//PA error handling
		for (int x = 0; x<forcedPartial.length;x++) {
			for(int j = x+1; j<forcedPartial.length; j++) {
					if ((forcedPartial[x][0] == forcedPartial[j][0]) || forcedPartial[x][1] == forcedPartial[j][1]) {
					System.out.println("partial assignment error");
					System.exit(0);
				}
			}
		}
			
		//initial value
		ArrayList<Integer> initCombo = new ArrayList<Integer>(tasks);
		currBest = penaltyCalc(initCombo);
		for (int i = 0; i < initCombo.size(); i++) {
			bestMatch[i] = initCombo.get(i);
		}
		System.out.println("Initial combo: " + Arrays.toString(bestMatch));
		System.out.println("with penalty: " + currBest);
	}
	
	//calculates any hard constraints
	public boolean hardChecker(ArrayList<Integer> a) {
		
		// Forced Partial
		for (int i = 0; i < forcedPartial.length; i++){
		    int machNum = forcedPartial[i][0] - 1;
		    
		    if (machNum < a.size()) {
			    if (forcedPartial[i][1] != a.get(machNum)) {
			        return false;
			    }
		    }
		} 
		
		// Forbidden Machine
		for (int i = 0; i < forbiddenMachine.length; i++){
		    int machNum = forbiddenMachine[i][0] - 1;
		    if (machNum < a.size()) {
			    if (forbiddenMachine[i][1] == a.get(machNum)) {
			        return false;
			    }
		    }
		} 
		
		// too-near tasks
		for (int i = 0; i < tooNearHard.length; i++) {
			for (int j = 0; j < a.size(); j++) {
				if(a.get(j) == tooNearHard[i][0]) {
					if(j == (a.size()-1)) {
						if (a.get(0) == tooNearHard[i][1]) {
							return false;
						}
					} else if (a.get((j+1)) == tooNearHard[i][1]) {
						return false;
					}
				}
			}
		}			
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
					if(thisBest <= currBest || trueBest == false) {
						trueBest = true;
						currBest = thisBest;
						for(int j = 0; j < combo.size(); j++) {
							bestMatch[j] = combo.get(j);
						}
					}
				} else {
					int thisBest = softChecker(combo, remaining);
					if(thisBest <= currBest || trueBest == false) {
						branchBound(combo);
					}
				}			
			}
		}
		if(trueBest == false) {
			System.out.println("No possible solution");
		}
		
		
		
		
	}
	
	public void conversionToChar() {
		for(int i = 0; i < bestMatch.length; i++) {
			if(bestMatch[i] == 1) {
				charBestMatch[i] = 'A';
			}
			else if(bestMatch[i] == 2) {
				charBestMatch[i] = 'B';
			}
			else if(bestMatch[i] == 3) {
				charBestMatch[i] = 'C';
			}
			else if(bestMatch[i] == 4) {
				charBestMatch[i] = 'D';
			}
			else if(bestMatch[i] == 5) {
				charBestMatch[i] = 'E';
			}
			else if(bestMatch[i] == 6) {
				charBestMatch[i] = 'F';
			}
			else if(bestMatch[i] == 7) {
				charBestMatch[i] = 'G';
			}
			else if(bestMatch[i] == 8) {
				charBestMatch[i] = 'H';
			}
		}
		
		System.out.print("Tasks: ");
		for(int i = 0; i < charBestMatch.length; i++) {
			System.out.print(charBestMatch[i] + " ");
		}
	}

	public static void main(String[] args) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		Main_Algorithm main_Algorithm = new Main_Algorithm();
		main_Algorithm.initalize();
		main_Algorithm.branchBound(a);
		System.out.println("Best real combo: " + Arrays.toString(main_Algorithm.bestMatch));
		System.out.println("with penalty: " + main_Algorithm.currBest);
		main_Algorithm.conversionToChar();
	}
}
