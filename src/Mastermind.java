import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.io.IOException;

public class Mastermind extends Thread{

    private static Socket socket;
  
    public Mastermind(Socket socket){
        this.socket = socket;
    }

    public static void main(){
        try {
            Random random = new Random();
            Character[] array_grund = { 'R', 'B', 'G', 'L', 'O', 'W', 'S', 'M' };
            socket.write("\033[H\033[2J");
            socket.write("Follgende Farbe Stehen zu Verfuegung \n\r      R B G L O W S M \n\r");
            char[] array_erraten = new char[4];
            for(int i = 0; i < array_erraten.length; i++){
                array_erraten[i] = array_grund[random.nextInt(8)];
            }
            for (int i = 0; i < 9; i++){
                char[] array_input = Input_Eingeben(array_grund);
                int variable = Rheinfolge_Überprufung(0, array_erraten.clone(), array_input);
                if(variable == 8){
                    socket.write("\r\nGewinn");
                    socket.readLine();
                    break;
                }
                if(i == 8){
                    socket.write("\r\nLeider Pech...");
                    socket.readLine();
                }
            }
        } catch (IOException io) {
            System.err.println("Error");
        }
    }

    public static char[] Input_Eingeben(Character[] array_grund){
        try {
            socket.write(" \r\n");
            String input = socket.readLine();
            char[] array_input = input.toCharArray();
            while(true){
                if(array_input.length == 4 && isArraySubset(array_input, array_grund)){
                    break;
                }
                socket.write("Sie haben ungueltige Input eingegeben. \r\n");
                input = socket.readLine();
                array_input = input.toCharArray();
            }
            return array_input;
        } catch (IOException io) {}
        return null;
    }

    public static int Existierung_Überprüfung(int index, char[] array_erraten, char[] array_input){
        
        if (index == array_input.length){
            return 0;
        }
        if(Contains(array_erraten, array_input[index]) == true){
            array_erraten[indexOf(array_erraten, array_input[index])] = Character.MIN_VALUE;
            try{
                socket.write(" Weiss ");}
                catch(IOException io){}
            return 1 + Existierung_Überprüfung(index + 1, array_erraten, array_input);  
        }
        return Existierung_Überprüfung(index + 1, array_erraten, array_input);
    }

    public static int Rheinfolge_Überprufung(int index, char[] array_erraten, char[] array_input){
        
        if (index == array_erraten.length){
            return Existierung_Überprüfung(0, array_erraten, array_input);
        }
        if (array_erraten[index] == array_input[index]){
            try{
                socket.write(" Black ");}
                catch(IOException io){}
            array_erraten[index] = Character.MIN_VALUE;
            array_input[index] = Character.MAX_VALUE;
            return 2 + Rheinfolge_Überprufung(index + 1, array_erraten, array_input);
        }
        return Rheinfolge_Überprufung(index + 1, array_erraten, array_input);
    }

    public static int indexOf(char[] array, char input){
        for(int i = 0; i < array.length; i++){
            if(array[i] == input){
                return i;
            }
        }
        return -1;
    }

    public static boolean Contains(char[] array, char input){
        for (int element : array) {
            if (element == input) {
                return true;
            }
        }
        return false;
    }

    public static boolean isArraySubset(char[] input1, Character[] input2){
        Set<Character> sett = new HashSet<>(Arrays.asList(input2));
        for(Character element : input1){
            if(!sett.contains(element)){
                return false;
            }
        }
        return true;
    }
}
