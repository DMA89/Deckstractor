package main.java.se.dma.deckstractor.utils;

import java.io.IOException;
import java.nio.file.*;

/**
 * Created by palle on 22/01/15.
 */
public class FileCounter {
    public static int getFile(String dirPath) {
        Path dir = Paths.get(dirPath);
        int c = 0;
        if(Files.isDirectory(dir)) {
            try {
                try(DirectoryStream<Path> files = Files.newDirectoryStream(dir)) {
                    for(Path file : files) {
                        if(Files.isRegularFile(file) || Files.isSymbolicLink(file)) {
                            // symbolic link also looks like file
                            c++;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            try {
                throw new NotDirectoryException(dir + " is not directory");
            } catch (NotDirectoryException e) {
                e.printStackTrace();
            }

        return c;
    }
}