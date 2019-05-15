package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class OutputSymbols {
    static char getSymbol(FieldStatus fieldStatus){
        char fieldSymbol=' ';
        switch (fieldStatus){
            case WATER:
                fieldSymbol = '≈';
                break;
            case SHOTWATER:
                fieldSymbol = '○';
                break;
            case SHIP:
                fieldSymbol = '■';
                break;
            case HIT:
                fieldSymbol = '●';
                break;
            case SUNK:
                fieldSymbol = '□';
                break;
        }
        return fieldSymbol;
    }
}
