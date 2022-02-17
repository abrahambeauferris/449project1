import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Input {

  static ArrayList<ArrayList<Integer>> fp = new ArrayList<>();
  static ArrayList<ArrayList<Integer>> fm = new ArrayList<>();
  static ArrayList<ArrayList<Integer>> tnh = new ArrayList<>();
  static ArrayList<ArrayList<Integer>> mp = new ArrayList<>();
  static ArrayList<ArrayList<Integer>> tnp = new ArrayList<>();

  static int[][] fp1;
  static int[][] fm1;
  static int[][] tnh1;
  static int[][] mp1;
  static int[][] tnp1;

  public static boolean inputIsValid(String inputFilename) {  // print "Error while parsing input file" if false. run other tests first. run this before parseInput
    File file = new File(inputFilename);
    try {
      Scanner scan = new Scanner(file);
      String match = scan.findWithinHorizon(
          "Name:\\s+[^\\s]+\\s+forced partial assignment:[ \\n\\t]+(\\([1-8],[A-H]\\)\\s+){0,8}forbidden machine:\\s+(\\([1-8],[A-H]\\)\\s+)*too-near tasks:\\s+(\\([A-H],[A-H]\\)\\s+)*machine penalties:\\s+(\\d+ \\d+ \\d+ \\d+ \\d+ \\d+ \\d+ \\d+\\s+){8}too-near penalities\\s+(\\([A-H],[A-H],\\d+\\)[\\s]*)*",
          0);
      scan.close();
      return match != null;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return false;
  }

  private static int taskToInt(String task) {
    if (task.equals("A"))
      return 1;
    else if (task.equals("B"))
      return 2;
    else if (task.equals("C"))
      return 3;
    else if (task.equals("D"))
      return 4;
    else if (task.equals("E"))
      return 5;
    else if (task.equals("F"))
      return 6;
    else if (task.equals("G"))
      return 7;
    else if (task.equals("H"))
      return 8;
    else
      return -1;
  }

  public static boolean invalidTaskTooNearPenalties(String inputFilename) {  // print "invalid task" if true
    File file = new File(inputFilename);
    try {
      Scanner scan = new Scanner(file);
      scan.findWithinHorizon("too-near penalities", 0);
      String match = scan.findWithinHorizon("(\\([^A-H],[A-H]|\\([^A-H],[^A-H]|\\([A-H],[^A-H])", 0);
      scan.close();
      return match != null;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return false;
  }

  
  public static boolean invalidPenalty(String inputFilename) { // print "invalid penalty" if true
    File file = new File(inputFilename);
    try {
      Scanner scan = new Scanner(file);
      scan.findWithinHorizon("machine penalties:", 0);
      String match = scan.findWithinHorizon("(-\\d|\\.\\d|\\D\\))", 0);
      scan.close();
      return match != null;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean hasMachinePenaltyError(String inputFilename) { // print "machine penalty error" if true
    File file = new File(inputFilename);
    try {
      Scanner scan = new Scanner(file);
      String match = scan.findWithinHorizon(
          "machine penalties:\\s+(\\d+ \\d+ \\d+ \\d+ \\d+ \\d+ \\d+ \\d+\\s+){8}too-near penalities", 0);
      scan.close();
      return match == null;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return false;
  }
  
    public static boolean invalidMachineOrTask(String inputFilename) {
    File file = new File(inputFilename);
    try {
      Scanner scan = new Scanner(file);
      scan.findWithinHorizon("Name:\\s+[^\\s]+\\s+forced partial assignment:[ \\n\\t]+", 0);
      String match = scan.findWithinHorizon("(\\([^A-H1-8],[A-H]\\)|\\([^1-8],[^A-H]\\)|\\([1-8],[^A-H]\\)|\\(-\\d,[A-H]\\))", 0);
      scan.close();
      return match != null;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return false;
  }
  
  public static void parseInput(String inputFilename) {

    File file = new File(inputFilename);
    try {
      Scanner scan = new Scanner(file);
      scan.skip("Name:\\s+[^\\s]+\\s+forced partial assignment:[ \\n\\t]+");
      scan.useDelimiter("[\\s\\(\\),\\na-z-]+");
      while (!scan.hasNext(":")) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(scan.nextInt());
        temp.add(taskToInt(scan.next()));

        fp.add(temp);
      }
      scan.next();
      while (!scan.hasNext(":")) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(scan.nextInt());
        temp.add(taskToInt(scan.next()));
        fm.add(temp);
      }
      scan.next();
      while (!scan.hasNext(":")) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(taskToInt(scan.next()));
        temp.add(taskToInt(scan.next()));
        tnh.add(temp);
      }
      scan.next();
      while (!scan.hasNext("[A-H]") && scan.hasNext()) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(scan.nextInt());
        temp.add(scan.nextInt());
        temp.add(scan.nextInt());
        temp.add(scan.nextInt());
        temp.add(scan.nextInt());
        temp.add(scan.nextInt());
        temp.add(scan.nextInt());
        temp.add(scan.nextInt());
        mp.add(temp);
      }
      while (scan.hasNext()) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(taskToInt(scan.next()));
        temp.add(taskToInt(scan.next()));
        temp.add(scan.nextInt());
        tnp.add(temp);
      }
      scan.close();

      fp1 = fp.stream().map(l -> l.stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new);

      fm1 = fm.stream().map(l -> l.stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new);

      tnh1 = tnh.stream().map(l -> l.stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new);

      mp1 = mp.stream().map(l -> l.stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new);

      tnp1 = tnp.stream().map(l -> l.stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new);
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException{
	Output o = new Output();
    if (invalidTaskTooNearPenalties(args[0])) {
      o.penalty(args[1],"invalid task");
      System.exit(0);
    }
    if (invalidMachineOrTask(args[0])) {
    	o.penalty(args[1],"invalid machine/task");
    	System.exit(0);
    }
    if (invalidPenalty(args[0])) {
    	o.penalty(args[1],"invalid penalty");
    	System.exit(0);
    }
    if (hasMachinePenaltyError(args[0])) {
    	o.penalty(args[1],"machine penalty error");
    	System.exit(0);
    }
    if (!inputIsValid(args[0])) {
      o.penalty(args[1],"Error while parsing input file");
      System.exit(0);
    }
    parseInput(args[0]);
    Main_Algorithm mainAlgo = new Main_Algorithm();
    mainAlgo.initalize(fp1,fm1,tnh1,tnp1,mp1,args[1]);
  }

}
