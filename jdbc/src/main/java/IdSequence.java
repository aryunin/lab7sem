public class IdSequence {
    private static Long id = 1L;

    public static Long getNext() {
        synchronized (IdSequence.class) {
            return id++;
        }
    }
}
