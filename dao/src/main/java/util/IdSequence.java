package util;

public class IdSequence {
    private Long id = 1L;

    public synchronized Long getNext() {
        return id++;
    }
}
