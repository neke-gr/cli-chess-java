public class Factory {

    public static Piece createPiece(Colors color, char type, Position position) throws InvalidCommandException {


        return switch (type) {
            case 'P' -> new Pawn(color, position, new PawnMoveStrategy());
            case 'R' -> new Rook(color, position, new RookMoveStrategy());
            case 'N' -> new Knight(color, position, new KnightMoveStrategy());
            case 'B' -> new Bishop(color, position, new BishopMoveStrategy());
            case 'Q' -> new Queen(color, position, new QueenMoveStrategy());
            case 'K' -> new King(color, position, new KingMoveStrategy());
            default -> throw new InvalidCommandException(type + " piece doesnt exist");
        };
    }
}
