import java.io.Console;
import java.util.HashMap;
import java.util.Map;

public class TicTacToe {

    private static Socket socket;
  
    public TicTacToe(Socket socket){
        this.socket = socket;
    }

    public static char man = 'O';
    public static char ki = 'X';
    public static char[] board = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static Map<String, Integer> wert = Map.of(
        "X", 10,
        "O",  -10,
        "Tie", 0
    );

    public static void main(){
        try {
            socket.write("\033[H\033[2J");
            socket.write("Wie Wollen Sie Spiele(1 - mit KI oder mit anderem Mensch) - ");
            char[] boards = board.clone();
            var variable = Integer.parseInt(socket.readLine());
            if(variable == 1){
                for (var i = 0; i < 5; i++){
                    Move();
                    if (i > 1){
                        var check = Gewinner_Check(ki);
                        if (check != null){
                            Board_Malen();
                            socket.write(check + " ist die Beitrag");
                            board = boards;
                            socket.readLine();
                            return;
                        }
                    }
                    Board_Malen();
                    Draw(man, board, socket.readLine());
                    if (i > 1){
                        var check = Gewinner_Check(man);
                        if (check != null){
                            Board_Malen();
                            socket.write(check + " ist die Beitrag");
                            board = boards;
                            socket.readLine();
                            return;
                        }
                    }
                }
            }
            else{
                for (var i = 0; i < 5; i++){
                    socket.write("\033[H\033[2J");
                    char spieler = ki;
                    for(int j = 0; j < 2; j++){
                        Board_Malen();
                        Draw(spieler, board, socket.readLine());
                        if (i > 1){
                            var check = Gewinner_Check(ki);
                            if (check != null){
                                Board_Malen();
                                socket.write(check + " ist die Beitrag");
                                board = boards;
                                socket.readLine();
                                return;
                            }
                        }
                        spieler = man;
                    }
                } 
            }
        } 
        catch (Exception e){}   
    }
    
    public static void Move()
    {
        var const_stand = Integer.MIN_VALUE;
        var index = 0;
        for (var i = 0; i < board.length; i++){
            if (board[i] != ki && board[i] != man){
                var temp = board[i];
                board[i] = ki;
                var stand = MiniMax(false);
                board[i] = temp;
                if (stand > const_stand){
                    const_stand = stand;
                    index = i;
                }
            }
        }
        board[index] = ki;
    }
    
    public static int MiniMax(boolean MaxStuffe){
        String ergebenis = Gewinner_Check(ki);
        if(ergebenis != null){
            return wert.get(ergebenis);
        }
        ergebenis = Gewinner_Check(man);
        if(ergebenis != null){
            return wert.get(ergebenis);
        }

        var beste_move = (MaxStuffe) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < board.length; i++)
        {
            if (board[i] != ki && board[i] != man)
            {
                var temp = board[i];
                board[i] = (MaxStuffe) ? ki : man;
                var stand = MiniMax(!MaxStuffe);
                board[i] = temp;
                if (MaxStuffe)
                {
                    beste_move = Math.max(beste_move, stand);
                }
                else
                {
                    beste_move = Math.min(beste_move, stand);
                }
            }
        }
        return beste_move;
    }
    public static String Gewinner_Check(char element)
    {
        int[][] gewinn =
        {
            {0, 4, 8 },
            {6, 4, 2 },
            {0, 1, 2 },
            {3, 4, 5 },
            {6, 7, 8 },
            {0, 3, 6 },
            {1, 4, 7 },
            {2, 5, 8 }
        };
        for (var i = 0; i < gewinn.length; i++)
        {
            if (board[gewinn[i][0]] == element &&
                board[gewinn[i][1]] == element &&
                board[gewinn[i][2]] == element)
            {
                return ((Character)element).toString();
            }
        }
        boolean tie = false;
        for (int i = 0; i < board.length; i++)
        {
            if (board[i] == ki || board[i] == man)
            {
                tie = true;
            }else{
                tie = false;
                break;
            }
        }
        if(tie){
            return "Tie";
        }
        return null;
    }

    public static void Draw(char element, char[] board, String input)
    {
        int platz = TryParse(input);
        if (platz != -1 && platz < 10 && platz > 0 && board[platz - 1] != ki && board[platz - 1] != man)
        {
            board[platz - 1] = element;
        }
    }

    public static int TryParse(String input){
        if(input.matches("-?\\d+")){
            return Integer.parseInt(input);
        }
        return -1;
    }
    public static void Board_Malen(){
        try {
            socket.write("\033[H\033[2J");
            for(int i = 0; i < 9; i += 3){
                String var = String.format(" %c | %c | %c ", board[i], board[i + 1], board[i + 2]);
                socket.write(var); 
                if(i == 6){
                    break;
                }
                socket.write("\r\n---|---|---\r\n");
            }
            socket.write("\r\nAuf Welche Position moechten Sie hingehen? \r\n");
        } catch (Exception e) {
        }
        
    } 
}
