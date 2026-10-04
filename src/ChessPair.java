
import java.util.*;

public class ChessPair<K extends Comparable<K>,V> implements Comparable<ChessPair<K,V>> {

    private K key;
    private V value;

    public ChessPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey(){
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return key + " " + value;
    }

    @Override
    public int compareTo(ChessPair<K, V> obj) {
        if (obj == null){
            return 0;
        }

        K key1 = this.key;
        K key2 = obj.key;

        int result = key1.compareTo(key2);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        } else {
            if (obj == this) {
                return true;
            }

            if(obj.getClass() != this.getClass()){
                return false;
            }

            ChessPair<K,V> newObj = (ChessPair<K, V>) obj;

            K key1 = this.key;
            K key2 = newObj.key;

            V val1 = this.value;
            V val2 = newObj.value;

            if(key1.equals(key2) && val1.equals(val2)) {
                return true;
            } else {
                return false;
            }
        }
    }


    @Override
    public int hashCode() {
        int hash = Objects.hash(key, value);
        return hash;
    }

}
