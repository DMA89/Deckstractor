package main.java.se.dma.deckstractor.utils;

import java.io.File;

/**
 * Created by palle on 22/01/15.
 */
public class FileCounter {
    public static int getFile(String dirPath) {
        int count = 0;
        File f = new File(dirPath);
        File[] files  = f.listFiles();

        if(files != null)
            for(int i=0; i < files.length; i++)
            {
                count ++;
                File file = files[i];
                if(file.isDirectory())
                {
                    getFile(file.getAbsolutePath());
                }
            }
        return count;
    }
}
