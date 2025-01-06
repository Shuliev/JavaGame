public class ServerMultithread {
  
  public static void main(String[] args) throws Exception {
    ServerSocket server= new ServerSocket(10000);
    do {
      Socket socket = server.accept();
      MeinSocketThread thread = new MeinSocketThread(socket);
      thread.run();
    } while (true); // end of do-while
  } // end of main

} // end of class ServerMultithread
