
import java.util.*;

public  abstract class Piece implements ChessPiece {

    private final Colors color;
    private Position position;
    private MoveStrategy moveStrategy;

    public Piece(Colors color, Position position, MoveStrategy moveStrategy){
        this.color = color;
        this.position = position;
        this.moveStrategy = moveStrategy;
    }

    public Colors getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Position> getPossibleMoves(Board board) {
        return moveStrategy.getPossibleMoves(board, this);
    }

    public abstract boolean checkForCheck(Board board, Position kingPosition);

    public abstract char type();
}
