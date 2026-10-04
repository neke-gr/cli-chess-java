
import java.util.*;

public class Game {

    private int id;
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private int index;
    private List<GameObserver> observers = new ArrayList<>();

    public Game(int id, Player player1, Player player2){
        this.id = id;
        this.board = new Board();
        this.players = new ArrayList<>();
        this.players.add(player1);
        this.players.add(player2);
        this.moves = new ArrayList<>();

        if(player1.getColor() == Colors.WHITE) {
            this.index = 0;
        } else {
            this.index = 1;
        }
    }


    public Game(int id, Board board, Player player1, Player player2, List<Move> moves, int index){
        this.id = id;
        this.board = board;
        this.players = new ArrayList<>();
        this.players.add(player1);
        this.players.add(player2);
        this.moves = new ArrayList<>(moves);
        this.index = index;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer1() {
        return players.get(0);
    }

    public Player getPlayer2() {
        return players.get(1);
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves){
        this.moves = new ArrayList<>(moves);
    }

    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public Player getPlayerIndex() {
        return players.get(index);
    }

    public void addObserver(GameObserver observer) {
        this.observers.add(observer);
    }

    private void displayMoveMade(Move move){
        int size = observers.size();
        for(int i = 0; i < size; i++){
            GameObserver observer = observers.get(i);
            observer.onMoveMade(move);
        }
    }

    private void displayPieceCaptured(Piece piece){
        int size = observers.size();
        for(int i = 0; i < size; i++) {
            GameObserver observer = observers.get(i);
            observer.onPieceCaptured(piece);
        }
    }

    private void displayPlayerSwitch(Player currentPlayer) {
        int size = observers.size();
        for(int i = 0; i < size; i++){
            GameObserver observer = observers.get(i);
            observer.onPlayerSwitch(currentPlayer);
        }
    }

    public void start() {
        moves.clear();
        board.initialize();

        Player player1 = players.get(0);
        player1.getColorPieces(board);
        Player player2 = players.get(1);
        player2.getColorPieces(board);


    }


    public void resume() {
        players.get(0).getColorPieces(board);
        players.get(1).getColorPieces(board);
    }

    public void switchPlayer() {
        if (index == 0) {
            index = 1;
        } else {
            index = 0;
        }

        displayPlayerSwitch(getPlayerIndex());
    }

    public boolean checkForCheckMate() {
        Player currentPlayer = this.getPlayerIndex();
        Player oppositePlayer;

        if(this.index == 0) {
            oppositePlayer = this.getPlayer2();
        } else {
            oppositePlayer = this.getPlayer1();
        }

        currentPlayer.getColorPieces(this.board);
        oppositePlayer.getColorPieces(this.board);


        Position king = null;

        List<ChessPair<Position,Piece>> currentPieces = currentPlayer.getOwnedPieces();
        int size = currentPieces.size();

        for( int i =0; i< size ; i++) {
            ChessPair<Position, Piece> pair = currentPieces.get(i);
            Piece piece = pair.getValue();

            if(piece instanceof King) {
                king = piece.getPosition();
                break;
            }
        }

        if(king == null){
            return false;
        }

        List<ChessPair<Position,Piece>> opponentPieces = oppositePlayer.getOwnedPieces();
        int sizeOp = opponentPieces.size();

        boolean check = false;
        for(int i = 0; i< sizeOp; i++){
            ChessPair<Position,Piece> pair = opponentPieces.get(i);
            Piece piece = pair.getValue();

            List<Position> opponentMoves = piece.getPossibleMoves(this.board);
            if(opponentMoves.contains(king)){
                check = true;
            }

            if(check == true) {
                break;
            }
        }

        if(check){
            for( int i = 0 ; i< size; i++){
                ChessPair<Position,Piece> pair = currentPieces.get(i);
                Position from = pair.getKey();
                Piece piece = pair.getValue();

                if(piece == null){
                    continue;
                }

                List<Position> moves = piece.getPossibleMoves(this.board);
                int sizeM = moves.size();

                for( int j = 0 ;j < sizeM ; j++){
                    Position to = moves.get(j);
                    if(this.board.isValidMove(from,to)){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void addMove(Player p, Position from , Position to) {
        Piece movedPiece = board.getPieceAt(to);
        Move move = new Move(p.getColor(), from,to,movedPiece);
        this.moves.add(move);
        displayMoveMade(move);
    }



}
