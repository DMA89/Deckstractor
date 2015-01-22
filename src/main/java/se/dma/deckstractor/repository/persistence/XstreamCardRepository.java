package main.java.se.dma.deckstractor.repository.persistence;

import main.java.se.dma.deckstractor.Main;
import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.repository.interfaces.CardRepository;
import main.java.se.dma.deckstractor.utils.FileCounter;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by palle on 22/01/15.
 */
public class XstreamCardRepository implements CardRepository {
    @Override
    public long saveCard(Card card) {
        card.setId(FileCounter.getFile("src/main/resources/xstream/cards/"));
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
        Card card = (Card) Main.xstream.fromXML(buffer.toString());
        return card;
    }

    @Override
    public ArrayList<Card> getAllCards() {
        ArrayList<Card> cards = new ArrayList();
        int nr = FileCounter.getFile("src/main/resources/xstream/cards/");
        for (int i = 0; i < nr; i++) {
            cards.add(getCard(i));
        }
        return cards;
    }

    @Override
    public ArrayList<Card> getAllCardsByClass(HearthstoneClass hearthstoneClass) {
        ArrayList<Card> cards = (ArrayList) getAllCards();
        ArrayList<Card> classCards = new ArrayList<>();
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
