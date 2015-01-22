package main.java.se.dma.deckstractor.services;

import main.java.se.dma.deckstractor.domain.HearthstoneClass;

import java.util.Collection;

/**
 * Created by palle on 21/01/15.
 */
public interface ClassService {
    long saveHearthstoneClass(HearthstoneClass hearthstoneClass);

    HearthstoneClass getHearthstoneClass(long id);

    HearthstoneClass getHearthstoneClassByName(String name);

    Collection getAllHearthstoneClasses();

    void removeHearthstoneClass(long id);

    void updateHearthstoneClass(HearthstoneClass hearthstoneClass);
}
