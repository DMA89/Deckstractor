package main.java.se.dma.deckstractor.repository.persistence;

import com.thoughtworks.xstream.XStream;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.repository.interfaces.ClassRepository;
import main.java.se.dma.deckstractor.utils.FileCounter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by palle on 22/01/15.
 */
public class XstreamClassRepository implements ClassRepository {
    XStream xStream = new XStream();

    @Override
    public long saveHearthstoneClass(HearthstoneClass hearthstoneClass) {
        hearthstoneClass.setId(FileCounter.getFile("/main/resources/xstream/hearthstoneclasses/"));
        String xml = xStream.toXML(hearthstoneClass);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("/home/palle/development/gitrepos/Deckstractor/src/main/resources/xstream/hearthstoneclasses/" + hearthstoneClass.getId() + ".xml", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(xml);
        writer.close();
        return hearthstoneClass.getId();
    }

    @Override
    public HearthstoneClass getHearthstoneClass(long id) {
        InputStream in = getClass().getResourceAsStream("/main/resources/xstream/hearthstoneclasses/" + id + ".xml");
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
        HearthstoneClass hearthstoneClass = (HearthstoneClass) xStream.fromXML(buffer.toString());
        return hearthstoneClass;
    }

    @Override
    public HearthstoneClass getHearthstoneClassByName(String name) {
        List<HearthstoneClass> hearthstoneClasses = (ArrayList) getAllHearthstoneClasses();
        for (HearthstoneClass hearthstoneClass : hearthstoneClasses) {
            if (hearthstoneClass.getName() == name) {
                return hearthstoneClass;
            }
        }
        return null;
    }

    @Override
    public Collection getAllHearthstoneClasses() {
        List<HearthstoneClass> hearthstoneClasses = new ArrayList();
        int nr = FileCounter.getFile("/main/resources/xstream/hearthstoneclasses/");
        for (int i = 0; i < nr; i++) {
            hearthstoneClasses.add(getHearthstoneClass(i));
        }
        return hearthstoneClasses;
    }

    @Override
    public void removeHearthstoneClass(long id) {
        try {
            File file = new File("/main/resources/xstream/hearthstoneclasses/" + id + ".xml");

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
    public void updateHearthstoneClass(HearthstoneClass hearthstoneClass) {
        hearthstoneClass.setId(new File("/main/resources/xstream/hearthstoneclasses/").listFiles().length);
        String xml = xStream.toXML(hearthstoneClass);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("/main/resources/xstream/hearthstoneclasses/" + String.valueOf(hearthstoneClass.getId()) + ".xml", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(xml);
        writer.close();
    }
}
