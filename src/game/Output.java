package game;

import java.io.BufferedReader;
import java.io.StringReader;
import java.nio.Buffer;
import java.util.Arrays;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Output {
    private static int STANDARD_HALF_SIZE=28;

    public static void output(MyBoard ownBoard, EnemyBoard enemyBoard) throws Exception {
        StringBuilder output=new StringBuilder();
        //Buffer
        BufferedReader ownBoardBuffer =
                new BufferedReader(new StringReader(toStringBoard(ownBoard.getFields(), ownBoard.getStatus())));
        BufferedReader enemyBoardBuffer=
                new BufferedReader(new StringReader(toStringBoard(enemyBoard.getFields(), enemyBoard.getStatus())));
        BufferedReader compassBuffer=
                new BufferedReader(new StringReader(compass()));
        BufferedReader ownShipsBuffer =
                new BufferedReader(new StringReader(toStringOwnShips(ownBoard)));
        BufferedReader enemyShipsBuffer=
                new BufferedReader(new StringReader(toStringEnemyShips(enemyBoard)));
        //Headline
        output.append(toLeft(playerHeadline()))
                .append(compassBuffer.readLine())
                .append(toRight(enemyHeadline()))
                .append(System.lineSeparator());
        //Ships
        for (int shipClasses=4; shipClasses>0; shipClasses--) {
            String temp=compassBuffer.readLine();
            output.append(toLeft(ownShipsBuffer.readLine(), -temp.length()/2))
                    .append(temp)
                    .append(toRight(enemyShipsBuffer.readLine(), -temp.length()/2))
                    .append(System.lineSeparator());
        }
        //Divider
        output.append(tDivider());
        //Boards
        String split = ownBoardBuffer.readLine();
        do { output.append(split)
                .append(iDivider())
                .append(" ")
                .append(enemyBoardBuffer.readLine())
                .append(System.lineSeparator());
            split = ownBoardBuffer.readLine();
        } while (split != null);
        output.append(lDivider())
                .append(toStringCommands(ownBoard.getStatus(), ownBoard.shipsAvailable()))
                .append(": ");
        System.out.print(output.toString());
    }

    private static String toStringEnemyShips(EnemyBoard enemyBoard) {
        return  toStringBattleship(enemyBoard)+" B" +
                System.lineSeparator() +
                toStringCruiserRight(enemyBoard)+" C" +
                System.lineSeparator() +
                toStringSubmarineRight(enemyBoard)+ " S" +
                System.lineSeparator() +
                toStringDestroyerRight(enemyBoard)+" D" +
                System.lineSeparator();
    }

    private static String enemyHeadline() {
        return "─────────────── Enemy ─";
    }

    private static String playerHeadline() {
        return "─ Player ──────────────";
    }

    private static String compass() {
        return "N\n" +
                "↑\n"+
                "W ←   → E\n"+
                "↓\n"+
                "S\n";
    }

    /**
     * Displays given Board in the console-view
     */
    private static String toStringBoard(FieldStatus[][] fields, GameStatus gameStatus) throws InputException{
        StringBuilder myBoard = new StringBuilder();
        myBoard.append(toMiddle(toStringNumbers(fields.length)))
                .append(System.lineSeparator());
        myBoard.append(toMiddle(verticalBoarder(fields.length, gameStatus, true)))
                .append(System.lineSeparator());
        for (int row = 0; row < fields.length; row++) {
            myBoard.append(toStringRow(fields, gameStatus, row))
                    .append(System.lineSeparator());
        }
        myBoard.append(toMiddle(verticalBoarder(fields.length, gameStatus, false)))
                .append(System.lineSeparator());
        myBoard.append(toMiddle(toStringNumbers(fields.length)))
                .append(System.lineSeparator());
        return myBoard.toString();
    }

    private static String toStringNumbers(int length){
        StringBuilder numbers=new StringBuilder();
        for (int number = 1; number <= length; number++) {
            numbers.append(" ").append(number);
        }
        return numbers.toString();
    }

    private static String toStringRow(FieldStatus[][] fields, GameStatus gameStatus, int row)throws InputException{
        StringBuilder rowString=new StringBuilder();
        rowString.append(OutputSymbols.getAlphabet(row + 1))
                .append(" ")
                .append(OutputSymbols.fieldBoarder(BoarderPiece.VERTICAL, gameStatus));
        for (FieldStatus[] field : fields) {
            rowString.append(" ")
                    .append(OutputSymbols.getSymbol(field[row]));
        }
        rowString.append(" ")
                .append(OutputSymbols.fieldBoarder(BoarderPiece.VERTICAL, gameStatus))
                .append(" ")
                .append(OutputSymbols.getAlphabet(row + 1))
                .append(" ");
        return rowString.toString();
    }



    private static String verticalBoarder(int length, GameStatus gameStatus, boolean up){
        StringBuilder boarder=new StringBuilder();
        BoarderPiece left=BoarderPiece.UPPER_LEFT, right=BoarderPiece.UPPER_RIGHT;
        if (!up){
            left=BoarderPiece.LOWER_LEFT;
            right=BoarderPiece.LOWER_RIGHT;
        }
        boarder.append(OutputSymbols.fieldBoarder(left, gameStatus));
        for (int boarderPart = 0; boarderPart < length * 2 + 1; boarderPart++) {
            boarder.append(OutputSymbols.fieldBoarder(BoarderPiece.HORRIZONTAL, gameStatus));
        }
        boarder.append(OutputSymbols.fieldBoarder(right, gameStatus));
        OutputSymbols.even=false;
        return boarder.toString();
    }

    private static String toStringOwnShips(MyBoard board) {
        return  "B " + toStringBattleship(board) +
                System.lineSeparator() +
                "C " + toStringCruiserLeft(board) +
                System.lineSeparator() +
                "S " + toStringSubmarineLeft(board) +
                System.lineSeparator() +
                "D " + toStringDestroyerLeft(board) +
                System.lineSeparator();
    }

    /**
     * Displays the commands that are available at the moment
     */
    private static String toStringCommands(GameStatus gameStatus, int[] shipsAvailable) {
        StringBuilder commands=new StringBuilder();
        switch (gameStatus){
            case PREPARATION:
                commands.append("≡ legend")
                        .append(System.lineSeparator())
                        .append("˅ set (B/C/S/D) (A-J) (1-10) (N/E/S/W)")
                        .append(System.lineSeparator())
                        .append("˄ remove (A-J) (1-10)")
                        .append(System.lineSeparator());
                if (Arrays.equals(shipsAvailable, new int[]{0, 0, 0, 0})) {
                    commands.append("► ready")
                            .append(System.lineSeparator());
                } else commands.append(System.lineSeparator());
                break;
            case READY:
                commands.append("≡ legend")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("◄ revoke")
                        .append(System.lineSeparator());
                break;
            case ATTACK:
                commands.append("≡ legend")
                        .append(System.lineSeparator())
                        .append("☼ shoot (A-J) (1-10)")
                        .append(System.lineSeparator())
                        .append("◦ last")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
                break;
            case RECEIVE:
                commands.append("≡ legend")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("◦ last")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
                break;
            case OVER:
                commands.append("≡ legend")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("◦ last")
                        .append(System.lineSeparator())
                        .append("→ continue")
                        .append(System.lineSeparator());
                break;
        }
       return commands.toString();
    }

    private static String standardDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("─".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"─"+divider.toString()+System.lineSeparator();
    }

    private static String xDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("─".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"┼"+divider.toString()+System.lineSeparator();
    }

    private static String tDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("─".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"┬"+divider.toString()+System.lineSeparator();
    }

    private static String lDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("─".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"┴"+divider.toString()+System.lineSeparator();
    }

    private static char iDivider(){
        return '│';
    }

    private static String toLeft(String inputString){
        return inputString +
                " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length()));
    }

    private static String toLeft(String inputString, int plusWhitespace){
        return inputString +
                " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length() + plusWhitespace));
    }

    private static String toRight(String inputString){
        return " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length())) +
                inputString;
    }

    private static String toRight(String inputString, int plusWhitespace){
        return " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length() + plusWhitespace)) +
                inputString;
    }

    private static String toMiddle(String inputString){
        String half=(" ".repeat(Math.max(0, (STANDARD_HALF_SIZE - inputString.length()) / 2)));
        String unevenCorrection="";
        if (inputString.length()%2==1) unevenCorrection=" ";
        return half+inputString+half+unevenCorrection;
    }

    private static String toStringBattleship(Board board){
        StringBuilder battleshipString=new StringBuilder();
        Ship[] ships = board.getShips();
        //Battleship
        for (int battleship=ShipInfo.getAmount(ShipType.BATTLESHIP); battleship>0; battleship--){
            if (ships[battleship-1].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.BATTLESHIP)+1; cruiserpart++) {
                    battleshipString.append(OutputSymbols.getMiniSymbol(ships[battleship-1].getSegmentStatus(cruiserpart)));
                }
            }
        }
        return battleshipString.toString();
    }

    private static String toStringCruiserLeft(Board board){
        StringBuilder cruiserString=new StringBuilder();
        Ship[] ships = board.getShips();
        for (int destroyer=ShipInfo.getAmount(ShipType.CRUISER); destroyer>0; destroyer--){
            if (ships[destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.CRUISER)+1; cruiserpart++) {
                    cruiserString.append(OutputSymbols.getMiniSymbol(ships[destroyer].getSegmentStatus(cruiserpart)));
                }
                cruiserString.append("  ");
            }
        }
        return cruiserString.toString();
    }

    private static String toStringCruiserRight(Board board){
        StringBuilder cruiserString=new StringBuilder();
        Ship[] ships = board.getShips();
        for (int destroyer=ShipInfo.getAmount(ShipType.CRUISER); destroyer>0; destroyer--){
            if (ships[destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                cruiserString.append("  ");
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.CRUISER)+1; cruiserpart++) {
                    cruiserString.append(OutputSymbols.getMiniSymbol(ships[destroyer].getSegmentStatus(cruiserpart)));
                }
            }
        }
        return cruiserString.toString();
    }

    private static String toStringSubmarineLeft(Board board){
        StringBuilder submarineString=new StringBuilder();
        Ship[] ships = board.getShips();
        for (int destroyer=ShipInfo.getAmount(ShipType.SUBMARINE); destroyer>0; destroyer--){
            if (ships[2+destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.SUBMARINE)+1; cruiserpart++) {
                    submarineString.append(OutputSymbols.getMiniSymbol(ships[2 + destroyer].getSegmentStatus(cruiserpart)));
                }
                submarineString.append("   ");
            }
        }
        return submarineString.toString();
    }

    private static String toStringSubmarineRight(Board board){
        StringBuilder submarineString=new StringBuilder();
        Ship[] ships = board.getShips();
        for (int destroyer=ShipInfo.getAmount(ShipType.SUBMARINE); destroyer>0; destroyer--){
            if (ships[2+destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                submarineString.append("   ");
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.SUBMARINE)+1; cruiserpart++) {
                    submarineString.append(OutputSymbols.getMiniSymbol(ships[2 + destroyer].getSegmentStatus(cruiserpart)));
                }
            }
        }
        return submarineString.toString();
    }

    private static String toStringDestroyerLeft(Board board){
        StringBuilder destroyerString=new StringBuilder();
        Ship[] ships = board.getShips();
        for (int destroyer=ShipInfo.getAmount(ShipType.DESTROYER); destroyer>0; destroyer--){
            if (ships[5+destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.DESTROYER)+1; cruiserpart++) {
                    destroyerString.append(OutputSymbols.getMiniSymbol(ships[5 + destroyer].getSegmentStatus(cruiserpart)));
                }
                destroyerString.append("    ");
            }
        }
        return destroyerString.toString();
    }

    private static String toStringDestroyerRight(Board board){
        StringBuilder destroyerString=new StringBuilder();
        Ship[] ships = board.getShips();
        for (int destroyer=ShipInfo.getAmount(ShipType.DESTROYER); destroyer>0; destroyer--){
            if (ships[5+destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                destroyerString.append("    ");
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.DESTROYER)+1; cruiserpart++) {
                    destroyerString.append(OutputSymbols.getMiniSymbol(ships[5 + destroyer].getSegmentStatus(cruiserpart)));
                }
            }
        }
        return destroyerString.toString();
    }
}
