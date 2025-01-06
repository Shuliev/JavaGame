import java.io.IOException;
public class MeinSocketThread extends Thread{
  
  private Socket socket;
  
  public MeinSocketThread(Socket socket){
    this.socket = socket;
  }
  
  public void run(){
  try{
    String auswahl = "";
    do {
      socket.write("\u001B[2J");
      socket.write("\u001B[1;1H");  
      socket.write("Willkommen zum Spieleserver\n\r");
      socket.write("Ihre Auswahl:\n\r");
      socket.write("Haengemaennchen         (1)\n\r");
      socket.write("Montagsmaler            (2)\n\r");
      socket.write("Gluecksrad              (3)\n\r");
      socket.write("Schnick Schnack Schnuck (4)\n\r");
      socket.write("Tic Tac Toe             (5)\n\r");
      socket.write("Ende                    (0)\n\r");
      socket.write("Ihre Auswahl bitte:     ");
      auswahl = socket.readLine();
      switch(auswahl){
        case "1": haengemaennchen(); break;
        case "2": mastermind(); break; 
        case "3": gluecksrad(); break;
        case "4": schnickSchnackSchnuck(); break;
        case "5": ticTacToe(); break;
        case "0": {
            socket.write("\n\r\n\rUnd tschuess!!!");
            try{
              Thread.sleep(2000);
            }catch (Exception e){
            }
            break;
        } 
      }
    } while (!auswahl.equalsIgnoreCase("0")); // end of do-while
      socket.close();  
    }catch(IOException io){}
  }
  public void haengemaennchen(){}
  public void mastermind(){
    Mastermind mastermind = new Mastermind(socket);
    Mastermind.main();
  }
  public void gluecksrad(){}
  public void schnickSchnackSchnuck(){}
  public void ticTacToe(){}
}