
import java.util.*;

public class Queen extends Piece{

    public Queen(Colors color, Position position, MoveStrategy moveStrategy){
        super(color,position, moveStrategy);
    }

    @Override
    public boolean checkForCheck(Board board, Position kingPosition) {
        List<Position> moves = getPossibleMoves(board);
        boolean check = moves.contains(kingPosition);
        return check;
    }

    @Override
    public char type(){
        return 'Q';
    }

}
