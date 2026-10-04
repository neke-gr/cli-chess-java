
import java.util.*;

public class KnightMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getPossibleMoves(Board board, Piece piece){
        List<Position> moves = new ArrayList<>();
        Position current = piece.getPosition();

        char X = current.getX();
        int Y = current.getY();

        int jump = 2;
        int shift = 1;

        //x-1, y+2
        Position check1 = new Position((char)(X - shift), Y + jump);
        if(check1.getX() >= 'A' && check1.getX() <= 'H' && check1.getY() >= 1 && check1.getY() <=8){
            Piece nPiece1 = board.getPieceAt(check1);
            if(nPiece1 == null || nPiece1.getColor() != piece.getColor()){
                moves.add(check1);
            }
        }

        //x+1, y+2
        Position check2 = new Position((char)(X + shift), Y + jump);
        if(check2.getX() >= 'A' && check2.getX() <= 'H' && check2.getY() >= 1 && check2.getY() <=8){
            Piece nPiece2 = board.getPieceAt(check2);
            if(nPiece2 == null || nPiece2.getColor() != piece.getColor()){
                moves.add(check2);
            }
        }

        //x+2, y+1
        Position check3 = new Position((char)(X + jump), Y + shift);
        if(check3.getX() >= 'A' && check3.getX() <= 'H' && check3.getY() >= 1 && check3.getY() <=8){
            Piece nPiece3 = board.getPieceAt(check3);
            if(nPiece3 == null || nPiece3.getColor() != piece.getColor()){
                moves.add(check3);
            }
        }

        //x+2, y-1
        Position check4 = new Position((char)(X + jump), Y - shift);
        if(check4.getX() >= 'A' && check4.getX() <= 'H' && check4.getY() >= 1 && check4.getY() <=8){
            Piece nPiece4 = board.getPieceAt(check4);
            if(nPiece4 == null || nPiece4.getColor() != piece.getColor()){
                moves.add(check4);
            }
        }

        //x+1, y-2
        Position check5 = new Position((char)(X + shift), Y - jump);
        if(check5.getX() >= 'A' && check5.getX() <= 'H' && check5.getY() >= 1 && check5.getY() <=8){
            Piece nPiece5 = board.getPieceAt(check5);
            if(nPiece5 == null || nPiece5.getColor() != piece.getColor()){
                moves.add(check5);
            }
        }

        //x-1, y-2
        Position check6 = new Position((char)(X - shift), Y - jump);
        if(check6.getX() >= 'A' && check6.getX() <= 'H' && check6.getY() >= 1 && check6.getY() <=8){
            Piece nPiece6 = board.getPieceAt(check6);
            if(nPiece6 == null || nPiece6.getColor() != piece.getColor()){
                moves.add(check6);
            }
        }

        //x-2, y-1
        Position check7 = new Position((char)(X - jump), Y - shift);
        if(check7.getX() >= 'A' && check7.getX() <= 'H' && check7.getY() >= 1 && check7.getY() <=8){
            Piece nPiece7 = board.getPieceAt(check7);
            if(nPiece7 == null || nPiece7.getColor() != piece.getColor()){
                moves.add(check7);
            }
        }

        //x-2, y+1
        Position check8 = new Position((char)(X - jump), Y + shift);
        if(check8.getX() >= 'A' && check8.getX() <= 'H' && check8.getY() >= 1 && check8.getY() <=8){
            Piece nPiece8 = board.getPieceAt(check8);
            if(nPiece8 == null || nPiece8.getColor() != piece.getColor()){
                moves.add(check8);
            }
        }

        return moves;
    }
}
