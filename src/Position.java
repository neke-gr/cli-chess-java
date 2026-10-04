
import java.util.*;

public class Position implements Comparable<Position> {

    private char x;
    private int y;

    public Position(char x, int y){
        this.x = x;
        this.y = y;
    }

    public char getX(){
        return x;
    }

    public void setX(char x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public String toString(){
        return "" + x + y;
    }

    @Override
    public boolean equals(Object o) {
        if ( o == null) {
            return false;
        } else {
            if ( o == this) {
                return true;
            }

            if(o.getClass() != this.getClass()){
                return false;
            }

            Position o1 = (Position)o;

            char x1 = this.x;
            char x2 = o1.x;

            int y1 = this.y;
            int y2 = o1.y;

            if(x1 == x2 && y1 == y2){
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int compareTo(Position obj) {

        char x1 = this.x;
        char x2 = obj.x;

        int y1 = this.y;
        int y2 = obj.y;

        if (y1 < y2){
            return -1;
        } else if (y1 > y2) {
            return 1;
        }

        if (x1 < x2){
            return -1;
        } else if (x1 > x2) {
            return 1;
        } else {
         return 0;
        }

    }

    @Override
    public int hashCode() {
        int hash = Objects.hash(x, y);
        return hash;

    }
}
