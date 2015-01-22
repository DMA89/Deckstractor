package main.java.se.dma.deckstractor.domain;


/**
 * Created by palle on 21/01/15.
 */
public class Card {
    private long id;
    private String name;
    private String blizzardId;
    private String hearthPwnId;
    private HearthstoneClass hearthstoneClass;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlizzardId() {
        return blizzardId;
    }

    public void setBlizzardId(String blizzardId) {
        this.blizzardId = blizzardId;
    }

    public String getHearthPwnId() {
        return hearthPwnId;
    }

    public void setHearthPwnId(String hearthPwnId) {
        this.hearthPwnId = hearthPwnId;
    }

    public HearthstoneClass getHearthstoneClass() {
        return hearthstoneClass;
    }

    public void setHearthstoneClass(HearthstoneClass hearthstoneClass) {
        this.hearthstoneClass = hearthstoneClass;
    }

    @Override
    public String toString() {
        return hearthstoneClass.getName().toUpperCase() + "\nId: " + id + "\nName: " + name + "\nBlizzard Id: " + blizzardId + "\nHearthPwn Id: " + hearthPwnId + "\n*************************************";
    }
}
