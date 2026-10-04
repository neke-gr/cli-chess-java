
import java.util.*;

public class Move {

    private Colors pieceColor;
    private Position before;
    private Position after;
    private Piece captured;

    public Move(Colors pieceColor, Position before, Position after, Piece captured){
        this.pieceColor = pieceColor;
        this.before = before;
        this.after = after;
        this.captured = captured;
    }

    public Colors getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(Colors pieceColor){
        this.pieceColor = pieceColor;
    }

    public Position getBefore() {
        return before;
    }

    public void setBefore(Position before) {
        this.before = before;
    }

    public Position getAfter() {
        return after;
    }

    public void setAfter(Position after) {
        this.after = after;
    }

    public Piece getCaptured() {
        return captured;
    }

    public void setCaptured(Piece captured) {
        this.captured = captured;
    }

}
