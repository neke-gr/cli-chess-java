
import java.util.*;

public class KingMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getPossibleMoves(Board board, Piece piece){

        List<Position> moves = new ArrayList<>();
        Position current = piece.getPosition();

        char X = current.getX();
        int Y = current.getY();

        int shift = 1;

        //x-1,y+1
        Position check1 = new Position((char)(X - shift), Y+shift);
        if(check1.getX() >= 'A' && check1.getX() <= 'H' && check1.getY() >= 1 && check1.getY() <=8){
            Piece kPiece1 = board.getPieceAt(check1);
            if(kPiece1 == null || kPiece1.getColor() != piece.getColor()){
                moves.add(check1);
            }
        }

        //x,y+1
        Position check2 = new Position(X, Y+shift);
        if(check2.getX() >= 'A' && check2.getX() <= 'H' && check2.getY() >= 1 && check2.getY() <=8){
            Piece kPiece2 = board.getPieceAt(check2);
            if(kPiece2 == null || kPiece2.getColor() != piece.getColor()){
                moves.add(check2);
            }
        }

        //x-1,y+1
        Position check3 = new Position((char)(X + shift), Y+shift);
        if(check3.getX() >= 'A' && check3.getX() <= 'H' && check3.getY() >= 1 && check3.getY() <=8){
            Piece kPiece3 = board.getPieceAt(check3);
            if(kPiece3 == null || kPiece3.getColor() != piece.getColor()){
                moves.add(check3);
            }
        }

        //x+1,y
        Position check4 = new Position((char)(X + shift), Y);
        if(check4.getX() >= 'A' && check4.getX() <= 'H' && check4.getY() >= 1 && check4.getY() <=8){
            Piece kPiece4 = board.getPieceAt(check4);
            if(kPiece4 == null || kPiece4.getColor() != piece.getColor()){
                moves.add(check4);
            }
        }

        //x+1,y-1
        Position check5 = new Position((char)(X + shift), Y-shift);
        if(check5.getX() >= 'A' && check5.getX() <= 'H' && check5.getY() >= 1 && check5.getY() <=8){
            Piece kPiece5 = board.getPieceAt(check5);
            if(kPiece5 == null || kPiece5.getColor() != piece.getColor()){
                moves.add(check5);
            }
        }

        //x,y-1
        Position check6 = new Position(X, Y-shift);
        if(check6.getX() >= 'A' && check6.getX() <= 'H' && check6.getY() >= 1 && check6.getY() <=8){
            Piece kPiece6 = board.getPieceAt(check6);
            if(kPiece6 == null || kPiece6.getColor() != piece.getColor()){
                moves.add(check6);
            }
        }

        //x-1,y-1
        Position check7 = new Position((char)(X - shift), Y-shift);
        if(check7.getX() >= 'A' && check7.getX() <= 'H' && check7.getY() >= 1 && check7.getY() <=8){
            Piece kPiece7 = board.getPieceAt(check7);
            if(kPiece7 == null || kPiece7.getColor() != piece.getColor()){
                moves.add(check7);
            }
        }

        //x-1,y
        Position check8 = new Position((char)(X - shift),Y);
        if(check8.getX() >= 'A' && check8.getX() <= 'H' && check8.getY() >= 1 && check8.getY() <=8){
            Piece kPiece8 = board.getPieceAt(check8);
            if(kPiece8 == null || kPiece8.getColor() != piece.getColor()){
                moves.add(check8);
            }
        }
        return moves;
    }
}
