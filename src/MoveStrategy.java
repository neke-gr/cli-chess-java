
import java.util.*;

public interface MoveStrategy {

    List<Position> getPossibleMoves(Board board, Piece piece);
 }
