package main.java.se.dma.deckstractor.services.modules;

import com.google.inject.AbstractModule;
import main.java.se.dma.deckstractor.services.interfaces.ClassService;
import main.java.se.dma.deckstractor.services.implementations.ClassServiceImpl;

/**
 * Created by palle on 22/01/15.
 */
public class ClassServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ClassService.class).to(ClassServiceImpl.class);
    }
}
