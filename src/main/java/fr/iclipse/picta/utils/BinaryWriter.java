package fr.iclipse.picta.utils;

import fr.iclipse.picta.model.Sequential;

import java.io.*;
import java.util.ArrayList;

public class BinaryWriter {

    private static final String FILE_PATH = "src/main/java/data.bin";

    public static void writeObject(ArrayList<Sequential> seqList) throws IOException {
        FileOutputStream fos  = new FileOutputStream(FILE_PATH);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(seqList);
        oos.close();
        fos.close();
    }

    public static ArrayList<Sequential> readObject() throws IOException, ClassNotFoundException {
        File file = new File(FILE_PATH);
        if (file.exists() && file.length()==0){
            ArrayList<Sequential> seqList = new ArrayList<>();
            writeObject(seqList);
            return seqList;
        }
        if(!file.exists() && file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        }
        FileInputStream fis = new FileInputStream(FILE_PATH);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Sequential> seqList = (ArrayList<Sequential>) ois.readObject();
        ois.close();
        fis.close();
        return seqList;
    }
}
