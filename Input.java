import java.io.File;
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

  public static boolean inputIsValid(String inputFilename) {
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
    else
      return 8;
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
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    parseInput("input.txt");

    // System.out.println("___________________________");
    // for (int i = 0; i < tnp.size(); i++) {
    //   for (int j = 0; j < tnp.get(i).size(); j++) {
    //     System.out.println(tnp.get(i).get(j));
    //   }
    // }
  }

}