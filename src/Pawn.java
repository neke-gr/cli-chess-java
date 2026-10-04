public class Pawn extends Piece {

    private boolean fMove;

    public Pawn(Colors color, Position position, MoveStrategy moveStrategy) {
        super(color,position, moveStrategy);
        this.fMove = true;
    }

    public boolean isFMove() {
        return fMove;
    }

    public void setFMove(boolean fMove){
        this.fMove = fMove;
    }

    @Override
    public boolean checkForCheck(Board board, Position kingPosition) {

        Position current = this.getPosition();
        char X = current.getX();
        int Y =current.getY();

        int yDir;

        if(this.getColor() == Colors.WHITE) {
            yDir = 1;
        } else {
            yDir = -1;
        }

        Position leftDiag = new Position((char) (X-1), Y + yDir);
        Position rightDiag = new Position(((char)(X+1)), Y + yDir);

        boolean check1 = kingPosition.equals(leftDiag);
        boolean check2 = kingPosition.equals(rightDiag);

        return check1 || check2;
    }

    @Override
    public char type(){
        return 'P';
    }
}
