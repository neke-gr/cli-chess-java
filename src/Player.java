
import java.util.*;

public class Player {

    private String playerName;
    private Colors color;
    private List<Piece> capturedPieces;
    private TreeSet<ChessPair<Position, Piece>> ownedPieces;
    private int points;

    public Player( String playerName, Colors color){
        this.playerName = playerName;
        this.color = color;
        capturedPieces = new ArrayList<>();
        ownedPieces = new TreeSet<>();
        this.points = 0;
    }


    public Player(String playerName, Colors color, List<Piece> capturedPieces, TreeSet<ChessPair<Position, Piece>> ownedPieces, int points) {
        this.playerName = playerName;
        this.color = color;
        this.capturedPieces = capturedPieces;
        this.ownedPieces = ownedPieces;
        this.points = points;
    }

    public void makeMove(Position from, Position to, Board board, String promotion) throws InvalidMoveException {
        Piece movingPiece = board.getPieceAt(from);

        if(movingPiece == null || movingPiece.getColor() != this.color) {
            throw new InvalidMoveException();
        }

        Piece toPiece = board.getPieceAt(to);

        board.movePiece(from, to , promotion);

        if(toPiece != null && toPiece.getColor() != this.color) {
            capturedPieces.add(toPiece);

            char pieceType = toPiece.type();

            if(pieceType == 'P') {
                points = points + 10;
            } else if(pieceType == 'N') {
                points = points + 30;
            } else if (pieceType == 'B') {
                points = points + 30;
            } else if (pieceType == 'R') {
                points = points + 50;
            } else if(pieceType == 'Q') {
                points = points + 90;
            }
        }

    }

    public List<Piece> getCapturedPieces(){
        return capturedPieces;
    }

    public List<ChessPair<Position,Piece>> getOwnedPieces() {
        List<ChessPair<Position,Piece>> pieces = new ArrayList<>(ownedPieces);
        return pieces;
    }

    public void setOwnedPieces(TreeSet<ChessPair<Position,Piece>> ownedPieces){
        this.ownedPieces = ownedPieces;
    }

    public void setCapturedPieces(List<Piece> capturedPieces){
        this.capturedPieces = capturedPieces;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Colors getColor() {
        return color;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public void getColorPieces(Board board){
        ownedPieces.clear();
        TreeSet<ChessPair<Position,Piece>> pieces = board.getBoard();

        ArrayList<ChessPair<Position,Piece>> temp = new ArrayList<>(pieces);
        int size = temp.size();

        for ( int i =0; i <size ; i++) {
            ChessPair<Position,Piece> pair = temp.get(i);
            Piece piece = pair.getValue();

            if(piece != null && piece.getColor() == this.color){
                ownedPieces.add(pair);
            }
        }

    }

}
