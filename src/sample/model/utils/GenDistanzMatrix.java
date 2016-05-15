package sample.model.utils;
import java.io.FileWriter;
import java.io.IOException;

public class GenDistanzMatrix {
    public static void main(String[] args){
        try (FileWriter fileWriter = new FileWriter("GenDistanzMatrix.m",false)){
            int i= Integer.parseInt(args[0]);
            System.out.println(i);
            fileWriter.write((char)i);
            fileWriter.append('\n');
            for (int j=0;i<10;i++) {
                fileWriter.append((char) i);
            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }

    }
}
