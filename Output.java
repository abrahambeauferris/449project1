import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;


public class Output {

	public static void main(String[] args) throws IOException {
		
		String outputFileName = args[1];
		char taskNames[] = {'A','B','C','D','E','F','G','H'};

            
		Output o = new Output();
		o.output(outputFileName, 5, taskNames);

	}
	
	public void output(String outputFile, int penalty, char[] outputTasks) throws IOException {
		
	    try {
	        FileWriter writer = new FileWriter(outputFile);
	        
	        if(penalty == -1) {
	        	writer.write("No valid solution possible!");
	        }
	        else {
	        	writer.write("Solution ");
	        	for(int i = 0; i < outputTasks.length; i++) {
	        		if(i == outputTasks.length - 1) {
	        			writer.write(outputTasks[i]);
	        		}
	        		else {
	        			writer.write(outputTasks[i] + " ");
	        		}
	        	}
	        	writer.write("; Quality: " + penalty);
	        }
	        writer.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
		
	}

}
