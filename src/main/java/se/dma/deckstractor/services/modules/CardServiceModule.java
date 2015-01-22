package main.java.se.dma.deckstractor.services.modules;

import com.google.inject.AbstractModule;
import main.java.se.dma.deckstractor.services.interfaces.CardService;
import main.java.se.dma.deckstractor.services.implementations.CardServiceImpl;

/**
 * Created by palle on 22/01/15.
 */
public class CardServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CardService.class).to(CardServiceImpl.class);
    }
}
