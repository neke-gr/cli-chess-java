
import java.util.*;

public class PawnMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getPossibleMoves(Board board, Piece piece){

        List<Position> moves = new ArrayList<>();
        Position current = piece.getPosition();

        Pawn pawn = (Pawn) piece;
        boolean fMove = pawn.isFMove();

        char X= current.getX();
        int Y = current.getY();

        int yDir;
        if(fMove) {
            if (piece.getColor() == Colors.WHITE) {
                yDir = 2;
            } else {
                yDir = -2;
            }
        }else {
            if (piece.getColor() == Colors.WHITE) {
                yDir = 1;
            } else {
                yDir = -1;
            }
        }

        if(fMove) {
            Position check1 = new Position(X, Y + yDir / 2);
            if (check1.getY() >= 1 && check1.getY() <= 8) {
                if (board.getPieceAt(check1) == null) {
                    moves.add(check1);

                    Position check2 = new Position(X, Y + yDir);
                    if (check2.getY() >= 1 && check2.getY() <= 8) {
                        if (board.getPieceAt(check2) == null) {
                            moves.add(check2);
                        }
                    }
                }
            }

            Position checkDiagL = new Position((char) (X - 1), Y + yDir / 2);
            if (checkDiagL.getX() >= 'A' && checkDiagL.getX() <= 'H' && checkDiagL.getY() >= 1 && checkDiagL.getY() <= 8) {
                Piece leftPiece = board.getPieceAt(checkDiagL);
                if (leftPiece != null && leftPiece.getColor() != piece.getColor()) {
                    moves.add(checkDiagL);
                }
            }

            Position checkDiagR = new Position((char) (X + 1), Y + yDir / 2);
            if (checkDiagR.getX() >= 'A' && checkDiagR.getX() <= 'H' && checkDiagR.getY() >= 1 && checkDiagR.getY() <= 8) {
                Piece rightPiece = board.getPieceAt(checkDiagR);
                if (rightPiece != null && rightPiece.getColor() != piece.getColor()) {
                    moves.add(checkDiagR);
                }
            }


        }else {
            Position check1 = new Position(X, Y + yDir);
            if (check1.getY() >= 1 && check1.getY() <= 8) {
                if (board.getPieceAt(check1) == null) {
                    moves.add(check1);
                }
            }


            Position checkDiagL = new Position((char) (X - 1), Y + yDir);
            if (checkDiagL.getX() >= 'A' && checkDiagL.getX() <= 'H' && checkDiagL.getY() >= 1 && checkDiagL.getY() <= 8) {
                Piece leftPiece = board.getPieceAt(checkDiagL);
                if (leftPiece != null && leftPiece.getColor() != piece.getColor()) {
                    moves.add(checkDiagL);
                }
            }


            Position checkDiagR = new Position((char) (X + 1), Y + yDir);
            if (checkDiagR.getX() >= 'A' && checkDiagR.getX() <= 'H' && checkDiagR.getY() >= 1 && checkDiagR.getY() <= 8) {
                Piece rightPiece = board.getPieceAt(checkDiagR);
                if (rightPiece != null && rightPiece.getColor() != piece.getColor()) {
                    moves.add(checkDiagR);
                }
            }
        }
        return moves;
    }
}
