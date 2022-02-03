
public class Greedy_Test {
	
		int tasks[] = {1,2,3,4,5,6,7,8};
		int bestPenalty = 0;
		int bestMatch[];
		
		//random pvals and constraints. In real code this 
		//would all come from input file
		int forcedPartial[][] = {{1,1}, {2,2}};
		int forbiddenMachine[][] = {{1,3}, {2,1}};
		int tooNearHard[][] = {{4,5}, {6,8}};
		int tooNearSoft[][] = {{4,6, 10}, {6,9, 100}};
		int pVals[][] = {{46,87,73,60,48,85,10,40},
						 {47,60,79,67,05,89,31,75},
						 {41,28,68,16,18,51,71,27},
						 {63,95,37,55,21,34,32,83},
						 {11,20,53,40,39,06,16,82},
						 {38,36,66,23,40,10,48,44},
						 {13,28,75,51,67,94,61,81},
						 {17,66,42,33,72,82,83,03}
						};
		
		
		boolean hardConstChecker(int match[], int n) {
			
			//code to check if this match violates any hard constraints
			//if hard-constraint violated, return false. Else, return true
			return true; 
		}
		
		int softConstChecker(int match[], int n) {
			
			//code to calculate the soft penalties. Return said penalty
			return 0;
		}
		
		// Computes the penalty of the match or disregards if
		// hard constraints are violated
		void examine(int a[], int n)
		{
			if(hardConstChecker(a, n) == true) {
				int penalty = softConstChecker(a, n);
				if(penalty <= bestPenalty) {
					bestPenalty = penalty;
					bestMatch = a;
				}
			}
		}

		// Generating permutation using Heap Algorithm
		// Taken from www.geeksforgeeks.org
		void heapPermutation(int a[], int size, int n)
		{
			// if size becomes 1 then examines the obtained
			// permutation
			if (size == 1)
				examine(a, n);

			for (int i = 0; i < size; i++) {
				heapPermutation(a, size - 1, n);

				// if size is odd, swap 0th i.e (first) and
				// (size-1)th i.e (last) element
				if (size % 2 == 1) {
					int temp = a[0];
					a[0] = a[size - 1];
					a[size - 1] = temp;
				}

				// If size is even, swap ith
				// and (size-1)th i.e last element
				else {
					int temp = a[i];
					a[i] = a[size - 1];
					a[size - 1] = temp;
				}
			}
		}

		public void main(String[] args) {
			heapPermutation(tasks, 8, 8);
			
			//in real code, this would be printed to the output file
			System.out.println("Best Match: " + bestMatch + " with penalty " + bestPenalty);
		}
	
}
