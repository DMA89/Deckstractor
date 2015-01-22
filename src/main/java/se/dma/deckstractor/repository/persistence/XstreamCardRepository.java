package main.java.se.dma.deckstractor.repository.persistence;

import com.thoughtworks.xstream.XStream;
import main.java.se.dma.deckstractor.Main;
import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.repository.interfaces.CardRepository;
import main.java.se.dma.deckstractor.utils.FileCounter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by palle on 22/01/15.
 */
public class XstreamCardRepository implements CardRepository {
    @Override
    public long saveCard(Card card) {
        card.setId(FileCounter.getFile("/main/resources/xstream/cards/"));
        String xml = Main.xstream.toXML(card);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("/main/resources/xstream/cards/" + card.getId() + ".xml", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(xml);
        writer.close();
        return card.getId();
    }

    @Override
    public Card getCard(long id) {
        InputStream in = getClass().getResourceAsStream("/main/resources/xstream/cards/" + id + ".xml");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(buffer);
        Card card = (Card) Main.xstream.fromXML(buffer.toString());
        return card;
    }

    @Override
    public Collection getAllCards() {
        List<Card> cards = new ArrayList();
        int nr = new File("/main/resources/xstream/cards/").listFiles().length;
        for (int i = 0; i < nr; i++) {
            cards.add(getCard(i));
        }
        return cards;
    }

    @Override
    public Collection getAllCardsByClass(HearthstoneClass hearthstoneClass) {
        List<Card> cards = (ArrayList) getAllCards();
        List<Card> classCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getHearthstoneClass() == hearthstoneClass) {
                classCards.add(card);
            }
        }
        return classCards;
    }

    @Override
    public void removeCard(long id) {
        try {
            File file = new File("/main/resources/xstream/cards/" + id + ".xml");

            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void updateCard(Card card) {
        String xml = Main.xstream.toXML(card);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("/main/resources/xstream/cards/" + String.valueOf(card.getId()) + ".xml", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(xml);
        writer.close();
    }
}
