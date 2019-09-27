
package datastructures;

public class MyPair<T, K> {
    private T object1;
    private K object2;
    
    /**
     * Class to store to objects as a pair.
     * After the pair is created, objects can only be called from it, not store.
     * @param object1 The first object.
     * @param object2 The second object.
     */
    public MyPair(T object1, K object2) {
        this.object1 = object1;
        this.object2 = object2;
    }
    
    /**
     * Gets the first object.
     * @return Returns the first object.
     */
    public T getKey() {
        return object1;
    }
    
    /**
     * Gets the second object.
     * @return Returns the second object.
     */
    public K getValue() {
        return object2;
    }
    
}
