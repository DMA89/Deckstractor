package main.java.se.dma.deckstractor.repository.modules;

import com.google.inject.AbstractModule;
import main.java.se.dma.deckstractor.repository.interfaces.CardRepository;
import main.java.se.dma.deckstractor.repository.persistence.XstreamCardRepository;

/**
 * Created by palle on 22/01/15.
 */
public class CardRepositoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CardRepository.class).to(XstreamCardRepository.class);
    }
}
