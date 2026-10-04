
import java.util.*;

public class GameLogger implements GameObserver {

    @Override
    public void onMoveMade(Move move){
        System.out.println(move.getPieceColor() + " moved from " + move.getBefore() + " to " + move.getAfter());
    }

    @Override
    public  void onPieceCaptured(Piece piece){
        System.out.println(piece.getColor() + " " + piece.type() + " captured at " + piece.getPosition());
    }


    @Override
    public void onPlayerSwitch(Player currentPlayer){
        System.out.println(currentPlayer.getPlayerName() + "'s turn.");
    }
}
