import java.util.Arrays;
import java.util.List;

public class hardConstCheckerTest {
	static int array[] = {1,2,3,4,5,6,7,8};
	
	static int fpa[][] = {{1,1},{5,5}};
	
	static int fm[][] = {{1,1}};
	
	static int tn[][] = {{1,2}};
	
	static boolean hardConstChecker(int list[], int forcedPartial[][], int forbiddenMachine[][], int tnt[][]) {
		//code to check if this match violates any hard constraints
		//if hard-constraint violated, return false. Else, return true
		
		// Error Handling
		
		for (int x = 1; x<forcedPartial.length;x++) {
			if ((forcedPartial[0][0] == forcedPartial[x][0]) || 
				forcedPartial[0][1] == forcedPartial[x][1]) {
				System.out.println("partial assignment error");
				return false;
				
			}
		
		
		// Forced Partial Assignment
		for (int i = 0; i < forcedPartial.length; i++){
		    int machNum = forcedPartial[i][0] - 1;
		    if (forcedPartial[i][1] != list[machNum]) {
		    	System.out.println("false");
		        return false;
		    }
		} 
		
		// Forbidden Machine
		for (int i = 0; i < forbiddenMachine.length; i++){
		    int machNum = forbiddenMachine[i][0] - 1;
		    if (forbiddenMachine[i][1] == list[machNum]) {
		    	System.out.println("false 2");
		        return false;
		    }
		} 
		
		// too-near tasks
		for (int i = 0; i < tnt.length; i++){
			List<int[]> listArray = Arrays.asList(list);
		    int index = listArray.indexOf(tnt[i][0]);
		    if (tnt[i][1] == list[index + 1]) {
		    	System.out.println("false 3");
		        return false;
		    }
		    if (index == 7 && list[0] == tnt[i][1]){
		    System.out.println("false 4");
		    return false;
		    }
		
	}
		
		
			return true;
	}
	

	        	  
	public static void main(String[] args) {
		hardConstChecker(array,fpa, fm,tn);

}
}
