package main.java.se.dma.deckstractor.services.implementations;

import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.repository.interfaces.ClassRepository;
import main.java.se.dma.deckstractor.repository.persistence.XstreamClassRepository;
import main.java.se.dma.deckstractor.services.interfaces.ClassService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by palle on 21/01/15.
 */
public class ClassServiceImpl implements ClassService {

    private ClassRepository repo = new XstreamClassRepository();

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
    public ArrayList<HearthstoneClass> getAllHearthstoneClasses() {
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
