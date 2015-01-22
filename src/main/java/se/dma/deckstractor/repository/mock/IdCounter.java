package main.java.se.dma.deckstractor.repository.mock;

/**
 * Created by palle on 21/01/15.
 */
public class IdCounter {
    private static long nextId = 0;

    protected long getNextId() {
        return nextId++;
    }
}
