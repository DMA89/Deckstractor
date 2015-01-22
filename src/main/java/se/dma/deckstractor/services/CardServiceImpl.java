package main.java.se.dma.deckstractor.services;

import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.repository.CardRepository;
import main.java.se.dma.deckstractor.repository.mock.MockCardRepository;

import java.util.Collection;

/**
 * Created by palle on 21/01/15.
 */
public class CardServiceImpl implements CardService {

    private static final CardRepository repo = new MockCardRepository();

    @Override
    public long saveCard(Card card) {
        return repo.saveCard(card);
    }

    @Override
    public Card getCard(long id) {
        return repo.getCard(id);
    }

    @Override
    public Collection getAllCards() {
        return repo.getAllCards();
    }

    @Override
    public Collection getAllCardsByClass(HearthstoneClass hearthstoneClass) {
        return repo.getAllCardsByClass(hearthstoneClass);
    }

    @Override
    public void removeCard(long id) {
        repo.removeCard(id);
    }

    @Override
    public void updateCard(Card card) {
        repo.updateCard(card);
    }
}
