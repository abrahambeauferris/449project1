
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;


public class OutputFile {

	public static void main(String[] args) throws IOException {
		
		String outputFileName = args[1];
		char taskNames[] = {'A','B','C','D','E','F','G','H'};

            
		OutputFile o = new OutputFile();
		o.output(outputFileName, 5, taskNames);

	}
	
	public void output(String outputFile, int penalty, char[] outputTasks) throws IOException {
		
	    try {
	        FileWriter write = new FileWriter(outputFile);
	        
	        if(penalty == -1) {
	        	write.write("No valid solution possible!");
	        }
	        else {
	        	write.write("Solution ");
	        	for(int i = 0; i < outputTasks.length; i++) {
	        		if(i == outputTasks.length - 1) {
	        			write.write(outputTasks[i]);
	        		}
	        		else {
	        			write.write(outputTasks[i] + " ");
	        		}
	        	}
	        	write.write("; Quality: " + penalty);
	        }
	        write.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
		
	}

}
