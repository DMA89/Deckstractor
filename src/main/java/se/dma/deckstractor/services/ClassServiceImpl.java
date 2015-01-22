package main.java.se.dma.deckstractor.services;

import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.repository.ClassRepository;
import main.java.se.dma.deckstractor.repository.mock.MockClassRepository;

import java.util.Collection;

/**
 * Created by palle on 21/01/15.
 */
public class ClassServiceImpl implements ClassService {

    static ClassRepository repo = new MockClassRepository();

    @Override
    public long saveHearthstoneClass(HearthstoneClass hearthstoneClass) {
        return repo.saveHearthstoneClass(hearthstoneClass);
    }

    @Override
    public HearthstoneClass getHearthstoneClass(long id) {
        return repo.getHearthstoneClass(id);
    }

    @Override
    public HearthstoneClass getHearthstoneClassByName(String name) {
        return repo.getHearthstoneClassByName(name);
    }

    @Override
    public Collection getAllHearthstoneClasses() {
        return repo.getAllHearthstoneClasses();
    }

    @Override
    public void removeHearthstoneClass(long id) {
        repo.removeHearthstoneClass(id);
    }

    @Override
    public void updateHearthstoneClass(HearthstoneClass hearthstoneClass) {
        repo.updateHearthstoneClass(hearthstoneClass);
    }
}
