package sample.model.utils;
import java.io.*;

public class FileWorker {
    private static String text="kek";
    private static String fileNameOfWrite="Jolie.m";
    private static String fileNameOfRead="DistanzMatrix.m";
    public static void main(String[] args) throws FileNotFoundException{
        FileWorker.writeFoFile(fileNameOfWrite, text);
        FileWorker.readFromFile(fileNameOfRead);
    }
    public static void writeFoFile(String fileName, String text){
        File file = new File(fileName);

        try{
            if(!file.exists()){
                file.createNewFile();
            }
            PrintWriter out =new PrintWriter(file.getAbsoluteFile());

            try{
                out.println(text);
            } finally {
                out.close();
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void readFromFile(String fileName)throws FileNotFoundException{
        try(FileReader reader = new FileReader(fileName)){
            int c;
            while((c=reader.read())!=-1){
                System.out.print((char)c);
            }
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }

}

