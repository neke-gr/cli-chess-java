
import java.util.*;

public class Rook extends Piece{

    public Rook(Colors color, Position position, MoveStrategy moveStrategy) {
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
        return 'R';
    }
}
