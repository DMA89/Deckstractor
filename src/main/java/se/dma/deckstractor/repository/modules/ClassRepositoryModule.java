package main.java.se.dma.deckstractor.repository.modules;

import com.google.inject.AbstractModule;
import main.java.se.dma.deckstractor.repository.interfaces.ClassRepository;
import main.java.se.dma.deckstractor.repository.persistence.XstreamClassRepository;

/**
 * Created by palle on 22/01/15.
 */
public class ClassRepositoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ClassRepository.class).to(XstreamClassRepository.class);
    }
}
