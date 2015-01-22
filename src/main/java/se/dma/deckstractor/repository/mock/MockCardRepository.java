package main.java.se.dma.deckstractor.repository.mock;

import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.repository.CardRepository;

import java.util.*;

/**
 * Created by palle on 21/01/15.
 */
public class MockCardRepository extends IdCounter implements CardRepository {

    private Map<Long, Card> repo = new HashMap<>();

    @Override
    public long saveCard(Card card) {
        card.setId(getNextId());
        repo.put(card.getId(), card);
        return card.getId();
    }

    @Override
    public Card getCard(long id) {
        return repo.get(id);
    }

    @Override
    public Collection getAllCards() {
        return new ArrayList<>(repo.values());
    }

    @Override
    public Collection getAllCardsByClass(HearthstoneClass hearthstoneClass) {
        List returnList = new ArrayList();
        for (int i = 0; i < repo.size(); i++) {
            if (repo.get(i).getHearthstoneClass() == hearthstoneClass) {
                returnList.add(repo.get(i));
            }
        }
        return returnList;
    }

    @Override
    public void removeCard(long id) {
        repo.remove(id);
    }

    @Override
    public void updateCard(Card card) {
        repo.put(card.getId(), card);
    }
}
