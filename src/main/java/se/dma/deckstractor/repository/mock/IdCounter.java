package main.java.se.dma.deckstractor.repository.mock;

/**
 * Created by palle on 21/01/15.
 */
class IdCounter {
    private static long nextId = 0;

    long getNextId() {
        return nextId++;
    }
}
