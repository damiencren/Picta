package fr.damiencren.picta.model;

import java.io.*;
import java.util.ArrayList;

public class BinaryWriter {

    public static void writeObject(ArrayList<Sequential> seqList) throws IOException {
        FileOutputStream fos  = new FileOutputStream("src/main/java/fr/damiencren/picta/data.bin");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(seqList);
        oos.close();
        fos.close();
    }

    public static ArrayList<Sequential> readObject() throws IOException, ClassNotFoundException {
        File file = new File("src/main/java/fr/damiencren/picta/data.bin");
        if (file.exists() && file.length()==0){
            ArrayList<Sequential> seqList = new ArrayList<>();
            writeObject(seqList);
            return seqList;
        }

        FileInputStream fis = new FileInputStream("src/main/java/fr/damiencren/picta/data.bin");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Sequential> seqList = (ArrayList<Sequential>) ois.readObject();
        ois.close();
        fis.close();

        return seqList;

    }
}
