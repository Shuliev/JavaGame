import java.io.*;
import java.util.*;

public class TextdateiLesen{

  public static String Word_Eingeben() throws Exception {
    
    File doc = new File("C:\\Users\\ShulievdD\\source\\github\\JavaGame\\src\\Begriffe.txt");
    BufferedReader obj = new BufferedReader(new FileReader(doc));
    ArrayList<String> liste = new ArrayList<String>();
    String strng;
    while ((strng = obj.readLine()) != null){
      liste.add(strng);
    }
    Random random = new Random();
    obj.close();
    return liste.get(random.nextInt(81));
  }

}