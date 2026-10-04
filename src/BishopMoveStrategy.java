
import java.util.*;

public class BishopMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getPossibleMoves(Board board, Piece piece){
        List<Position> moves = new ArrayList<>();
        Position current = piece.getPosition();

        char X = current.getX();
        int Y = current.getY();

        int shift = 1;

        //x+1, y+1
        while(true){
            char xShift = (char) (X + shift);
            int yShift = Y + shift;

            if(xShift < 'A' || xShift > 'H' || yShift < 1 || yShift > 8) {
                break;
            }

            Position check = new Position(xShift, yShift);
            Piece bPiece = board.getPieceAt(check);
            if(bPiece == null){
                moves.add(check);
                shift++;
            } else if(bPiece.getColor() != piece.getColor()){
                moves.add(check);
                break;
            } else {
                break;
            }
        }

        shift = 1;

        //x+1, y-1
        while(true){
            char xShift = (char) (X + shift);
            int yShift = Y - shift;

            if(xShift < 'A' || xShift > 'H' || yShift < 1 || yShift > 8) {
                break;
            }

            Position check = new Position(xShift, yShift);
            Piece bPiece = board.getPieceAt(check);
            if(bPiece == null){
                moves.add(check);
                shift++;
            } else if(bPiece.getColor() != piece.getColor()){
                moves.add(check);
                break;
            } else {
                break;
            }
        }

        shift = 1;

        //x-1, y-1
        while(true){
            char xShift = (char) (X - shift);
            int yShift = Y - shift;

            if(xShift < 'A' || xShift > 'H' || yShift < 1 || yShift > 8) {
                break;
            }

            Position check = new Position(xShift, yShift);
            Piece bPiece = board.getPieceAt(check);
            if(bPiece == null){
                moves.add(check);
                shift++;
            } else if(bPiece.getColor() != piece.getColor()){
                moves.add(check);
                break;
            } else {
                break;
            }
        }

        shift = 1;

        //x-1, y+1
        while(true){
            char xShift = (char) (X - shift);
            int yShift = Y + shift;

            if(xShift < 'A' || xShift > 'H' || yShift < 1 || yShift > 8) {
                break;
            }

            Position check = new Position(xShift, yShift);
            Piece bPiece = board.getPieceAt(check);
            if(bPiece == null){
                moves.add(check);
                shift++;
            } else if(bPiece.getColor() != piece.getColor()){
                moves.add(check);
                break;
            } else {
                break;
            }
        }
        return moves;
    }
}
