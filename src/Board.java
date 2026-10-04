

import java.util.*;

public class Board {

    private TreeSet<ChessPair<Position,Piece>> board;

    public Board() {
        board = new TreeSet<>();
    }

    public void initialize() {
        char[] col = { 'A', 'B', 'C', 'D','E', 'F', 'G', 'H'};
        int size = col.length;

        //Pozicioni i gureve te bardhe
        ArrayList<Position> whitePieces = new ArrayList<>();

        for(int i = 0; i< size ; i++){
            whitePieces.add(new Position(col[i], 1));
        }
        for(int i = 0; i<size ; i++){
            whitePieces.add(new Position(col[i], 2));
        }

        //pozicioni i gureve te zinj
        ArrayList<Position> blackPieces = new ArrayList<>();

        for(int i = 0; i< size ; i++){
            blackPieces.add(new Position(col[i], 8));
        }
        for(int i = 0; i<size ; i++){
            blackPieces.add(new Position(col[i], 7));
        }


        //pozicioni i gureve prapa
        ArrayList<String>  backRow = new ArrayList<>();
        backRow.add("R");
        backRow.add("N");
        backRow.add("B");
        backRow.add("Q");
        backRow.add("K");
        backRow.add("B");
        backRow.add("N");
        backRow.add("R");

        //guret para
        ArrayList<String> frontRow = new ArrayList<>();
        for(int i = 0; i<8; i++) {
            frontRow.add("P");
        }

        //te gjitha guret, deh vendosen sipas rradhes
        ArrayList<String> pieceTypes = new ArrayList<>();
        pieceTypes.addAll(backRow);
        pieceTypes.addAll(frontRow);


        //marrim pozicionin  , deh gurin , me p gjejme si e vendosim
        for(int i =0; i <whitePieces.size(); i++) {
            Position pos = whitePieces.get(i);
            String piece = pieceTypes.get(i);
            Piece name;

            try {
                name = Factory.createPiece(Colors.WHITE, piece.charAt(0), pos);
            } catch (InvalidCommandException e){
                name = null;
                System.out.println(e.getMessage());
            }


            //i shtojme ne chesspair
            ChessPair<Position, Piece> whitePair = new ChessPair<>(pos, name);
            board.add(whitePair);

        }


        for( int i =0; i < blackPieces.size(); i++) {
            Position pos = blackPieces.get(i);
            String piece = pieceTypes.get(i);
            Piece name;

            try {
                name = Factory.createPiece(Colors.BLACK, piece.charAt(0), pos);
            } catch (InvalidCommandException e){
                name = null;
                System.out.println(e.getMessage());
            }

            ChessPair<Position, Piece> blackPair = new ChessPair<>(pos, name);
            board.add(blackPair);
        }
    }


    public Piece getPieceAt(Position position) {
        ArrayList<ChessPair<Position, Piece>> temp = new ArrayList<>(board);
        int size = temp.size();
        for (int i =0; i < size ; i++) {
            ChessPair<Position,Piece> pair = temp.get(i);
            Position pos = pair.getKey();
            if(pos.equals(position)){
                Piece piece = pair.getValue();
                return piece;
            }
        }
        return null;
    }

    public boolean isValidMove(Position from, Position to){
        Piece piece = getPieceAt(from);

        if(piece == null) {
            return false;
        }

        List<Position> moves = piece.getPossibleMoves(this);
        int size = moves.size();

        //verifikojme pozicionin qe do shkojme
        boolean check = false;
        for(int i = 0; i < size ; i++) {
            Position temp = moves.get(i);
            if(temp.equals(to)){
                check = true;
                break;
            }
        }

        if(check) {

            Colors color = piece.getColor();
            ArrayList<ChessPair<Position, Piece>> temp = new ArrayList<>(board);
            int sizeT = temp.size();

            ChessPair<Position, Piece> oldSpot = null;
            ChessPair<Position, Piece> newSpot = null;

            for (int i = 0; i < sizeT; i++) {
                ChessPair<Position, Piece> pair = temp.get(i);
                Position position = pair.getKey();

                if (position.equals(from)) {
                    oldSpot = pair;
                }
                if (position.equals(to)) {
                    newSpot = pair;
                }
            }

            board.remove(oldSpot);

            if (newSpot != null) {
                board.remove(newSpot);
            }
            piece.setPosition(to);
            ChessPair<Position, Piece> updated = new ChessPair<>(to, piece);
            board.add(updated);

            Position king = null;
            ArrayList<ChessPair<Position, Piece>> pieces = new ArrayList<>(board);
            int sizeP = pieces.size();

            for (int i = 0; i < sizeP; i++) {
                ChessPair<Position, Piece> pair = pieces.get(i);
                Piece currentPiece = pair.getValue();

                if (currentPiece instanceof King && currentPiece.getColor() == color) {
                    king = pair.getKey();
                    break;
                }
            }

            boolean safeSpot = true;
            for (int i = 0; i < sizeP; i++) {
                ChessPair<Position, Piece> pair = pieces.get(i);
                Piece opponent = pair.getValue();
                if (opponent.getColor() != color) {
                    List<Position> opponentMoves = opponent.getPossibleMoves(this);
                    if(opponentMoves.contains(king)){
                        safeSpot = false;
                        break;
                    }
                }
            }

            board.remove(updated);
            if(newSpot != null){
                board.add(newSpot);
            }

            piece.setPosition(from);
            board.add(oldSpot);

            return safeSpot;
        }
        return false;
    }

    public void movePiece(Position from, Position to, String promotion) throws InvalidMoveException {

        if(isValidMove(from, to)){

            Piece pieceOldSpot = getPieceAt(from);

            ArrayList<ChessPair<Position,Piece>> tempList = new ArrayList<>(board);
            int size = tempList.size();

            ChessPair<Position, Piece> oldSpot = null;
            ChessPair<Position, Piece> newSpot = null;

            for (int i =0; i< size; i++) {
                ChessPair<Position,Piece> pair = tempList.get(i);
                Position current = pair.getKey();

                if(current.equals(from)) {
                    oldSpot = pair;
                }
                if(current.equals(to)) {
                    newSpot = pair;
                }
            }
            board.remove(oldSpot);

            if(newSpot != null) {
                board.remove(newSpot);
            }

            pieceOldSpot.setPosition(to);

            ChessPair<Position,Piece> movedPair =  new ChessPair<>(to, pieceOldSpot);
            board.add(movedPair);

            if(pieceOldSpot instanceof Pawn){
                Pawn pawn = (Pawn) pieceOldSpot;
                pawn.setFMove(false);
            }

            if(pieceOldSpot instanceof Pawn) {
                int row = to.getY();
                if(row == 1 || row == 8) {
                    board.remove(movedPair);

                    char generalPromotion = 'Q';

                    if(promotion != null){
                        switch (promotion){
                            case "Rook":
                                generalPromotion = 'R';
                                break;
                            case "Knight":
                                generalPromotion = 'N';
                                break;
                            case "Bishop":
                                generalPromotion = 'B';
                                break;
                            case "Queen":
                                generalPromotion = 'Q';
                                break;
                            default:
                                generalPromotion = 'Q';
                                break;
                        }
                    }

                    Piece newPiece = null;

                    try {
                        newPiece = Factory.createPiece(pieceOldSpot.getColor(), generalPromotion, to);
                    } catch(InvalidCommandException e){
                        System.out.println(e.getMessage());

                        try {
                            newPiece = Factory.createPiece(pieceOldSpot.getColor(), 'Q', to);
                        } catch (InvalidCommandException e2){
                            System.out.println(e2.getMessage());
                        }
                    }

                    ChessPair<Position,Piece> newPair = new ChessPair<>(to, newPiece);
                    board.add(newPair);
                }
            }
        } else {
            throw new InvalidMoveException();
        }
    }

    public TreeSet<ChessPair<Position,Piece>> getBoard() {
        return board;
    }

}
