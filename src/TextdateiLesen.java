import java.io.*;
import java.util.*;

public class TextdateiLesen{
  public static void main(String[] args) throws Exception {
    File doc = new File("Begriffe.txt");
    BufferedReader obj = new BufferedReader(new FileReader(doc));
    ArrayList<String> liste = new ArrayList<String>();
    String strng;
    while ((strng = obj.readLine()) != null){
      liste.add(strng);
    }
    for (String s:liste) {
      System.out.println(s);
    } // end of for
    obj.close();
  }

}