package output;

import boards.*;
import boards.fields.*;
import boards.ships.*;
import java.io.*;
import java.util.Arrays;
import exceptions.*;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class OutputImpl implements Output{
    private final PlayerBoard playerBoard;
    private final EnemyBoard enemyBoard;
    private int STANDARD_HALF_SIZE=28;

    public OutputImpl(PlayerBoard playerBoard, EnemyBoard enemyBoard){
        this.playerBoard=playerBoard;
        this.enemyBoard=enemyBoard;
    }

    public void output() throws StatusException,InputException, IOException {
        System.out.print(getOutputString());
    }

    public void output(String message) throws StatusException,InputException, IOException{
        StringBuilder output=new StringBuilder();
        BufferedReader game=new BufferedReader(new StringReader(getOutputString()));
        for(int upperPart=11; upperPart>0;upperPart--) {
            output.append(game.readLine()).append(System.lineSeparator());
        }
        output.append(doubleDivider()).append(System.lineSeparator());
        output.append(message).append(System.lineSeparator()).append(System.lineSeparator());
        output.append(doubleDivider());
        game.readLine();
        game.readLine();
        game.readLine();
        game.readLine();
        game.readLine();
        for(int lowerPart=10; lowerPart>0;lowerPart--) {
            output.append(game.readLine()).append(System.lineSeparator());
        }
        output.append(game.readLine());
        System.out.print(output.toString());
    }

    private String getOutputString()throws StatusException, InputException, IOException{
        StringBuilder output=new StringBuilder();
        //Buffer
        BufferedReader playerBoardBuffer =
                new BufferedReader(new StringReader(toStringBoard(playerBoard.getFields(), playerBoard.getGameStatus())));
        BufferedReader enemyBoardBuffer=
                new BufferedReader(new StringReader(toStringBoard(enemyBoard.getFields(), enemyBoard.getGameStatus())));
        BufferedReader compassBuffer=
                new BufferedReader(new StringReader(compass()));
        BufferedReader ownShipsBuffer =
                new BufferedReader(new StringReader(toStringOwnShips()));
        BufferedReader enemyShipsBuffer=
                new BufferedReader(new StringReader(toStringEnemyShips()));
        output.append(System.lineSeparator());
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
        String split = playerBoardBuffer.readLine();
        do { output.append(split)
                .append(iDivider())
                .append(" ")
                .append(enemyBoardBuffer.readLine())
                .append(System.lineSeparator());
            split = playerBoardBuffer.readLine();
        } while (split != null);
        output.append(lDivider())
                .append(toStringCommands())
                .append(": ");
        return output.toString();
    }

    private String toStringEnemyShips() {
        return  toStringBattleship(enemyBoard)+" B" +
                System.lineSeparator() +
                toStringCruiserRight(enemyBoard)+" C" +
                System.lineSeparator() +
                toStringSubmarineRight(enemyBoard)+ " S" +
                System.lineSeparator() +
                toStringDestroyerRight(enemyBoard)+" D" +
                System.lineSeparator();
    }

    private String enemyHeadline() {
        return "─────────────── Enemy ─";
    }

    private String playerHeadline() {
        return "─ Player ──────────────";
    }

    private String compass() {
        return "N\n" +
                "↑\n"+
                "W ←   → E\n"+
                "↓\n"+
                "S\n";
    }

    /**
     * Displays given Board in the console-view
     */
    private String toStringBoard(FieldStatus[][] fields, GameStatus gameStatus) throws InputException {
        StringBuilder board = new StringBuilder();
        board.append(toMiddle(toStringNumbers(fields.length)))
                .append(System.lineSeparator());
        board.append(toMiddle(verticalBoarder(fields.length, gameStatus, true)))
                .append(System.lineSeparator());
        for (int row = 0; row < fields.length; row++) {
            board.append(toStringRow(fields, gameStatus, row))
                    .append(System.lineSeparator());
        }
        board.append(toMiddle(verticalBoarder(fields.length, gameStatus, false)))
                .append(System.lineSeparator());
        board.append(toMiddle(toStringNumbers(fields.length)))
                .append(System.lineSeparator());
        return board.toString();
    }

    private String toStringNumbers(int length){
        StringBuilder numbers=new StringBuilder();
        for (int number = 1; number <= length; number++) {
            numbers.append(" ").append(number);
        }
        return numbers.toString();
    }

    private String toStringRow(FieldStatus[][] fields, GameStatus gameStatus, int row)throws InputException{
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



    private String verticalBoarder(int length, GameStatus gameStatus, boolean up){
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

    private String toStringOwnShips() {
        return  "B " + toStringBattleship(playerBoard) +
                System.lineSeparator() +
                "C " + toStringCruiserLeft(playerBoard) +
                System.lineSeparator() +
                "S " + toStringSubmarineLeft(playerBoard) +
                System.lineSeparator() +
                "D " + toStringDestroyerLeft(playerBoard) +
                System.lineSeparator();
    }

    /**
     * Displays the commands that are available at the moment
     */
    private String toStringCommands() throws StatusException {
        GameStatus gameStatus=playerBoard.getGameStatus();
        int[] shipsAvailable=playerBoard.shipsAvailable();
        StringBuilder commands=new StringBuilder();
        switch (gameStatus){
            case PREPARATION:
                commands.append("Place your ships!")
                        .append(System.lineSeparator())
                        .append("▼ set (B/C/S/D) (A-J) (1-10) (N/E/S/W)")
                        .append(System.lineSeparator())
                        .append("∆ remove (A-J) (1-10)")
                        .append(System.lineSeparator());
                if (Arrays.equals(shipsAvailable, new int[]{0, 0, 0, 0})) {
                    commands.append("► ready")
                            .append(System.lineSeparator());
                } else commands.append(System.lineSeparator());
                break;
            case READY:
                commands.append("Wait till enemy is in battle position!")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("◄ revoke")
                        .append(System.lineSeparator());
                break;
            case ATTACK:
                commands.append("It's your turn to attack!")
                        .append(System.lineSeparator())
                        .append("● shoot (A-J) (1-10)")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
                break;
            case RECEIVE:
                commands.append("Wait for Enemy Response!")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
                break;
            case LOST:
                commands.append("You have lost the battle!")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
                break;
            case WON:
                commands.append("You have won the battle!")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());
                break;
        }
       return commands.toString();
    }

    private String doubleDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("═".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"═"+divider.toString()+System.lineSeparator();
    }

    private String tDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("─".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"┬"+divider.toString()+System.lineSeparator();
    }

    private String lDivider(){
        StringBuilder divider=new StringBuilder();
        divider.append("─".repeat(Math.max(0, STANDARD_HALF_SIZE)));
        return divider.toString()+"┴"+divider.toString()+System.lineSeparator();
    }

    private char iDivider(){
        return '│';
    }

    private String toLeft(String inputString){
        return inputString +
                " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length()));
    }

    private String toLeft(String inputString, int plusWhitespace){
        return inputString +
                " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length() + plusWhitespace));
    }

    private String toRight(String inputString){
        return " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length())) +
                inputString;
    }

    private String toRight(String inputString, int plusWhitespace){
        return " ".repeat(Math.max(0, STANDARD_HALF_SIZE - inputString.length() + plusWhitespace)) +
                inputString;
    }

    private String toMiddle(String inputString){
        String half=(" ".repeat(Math.max(0, (STANDARD_HALF_SIZE - inputString.length()) / 2)));
        String unevenCorrection="";
        if (inputString.length()%2==1) unevenCorrection=" ";
        return half+inputString+half+unevenCorrection;
    }

    private String toStringBattleship(Board board){
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

    private String toStringCruiserLeft(Board board){
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

    private String toStringCruiserRight(Board board){
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

    private String toStringSubmarineLeft(Board board){
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

    private String toStringSubmarineRight(Board board){
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

    private String toStringDestroyerLeft(Board board){
        StringBuilder destroyerString=new StringBuilder();
        Ship[] ships = board.getShips();
        for (int destroyer = ShipInfo.getAmount(ShipType.DESTROYER); destroyer>0; destroyer--){
            if (ships[5+destroyer].getSegmentStatus(1)!=FieldStatus.SUNK){
                for(int cruiserpart=1; cruiserpart<ShipInfo.getLength(ShipType.DESTROYER)+1; cruiserpart++) {
                    destroyerString.append(OutputSymbols.getMiniSymbol(ships[5 + destroyer].getSegmentStatus(cruiserpart)));
                }
                destroyerString.append("    ");
            }
        }
        return destroyerString.toString();
    }

    private String toStringDestroyerRight(Board board){
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
