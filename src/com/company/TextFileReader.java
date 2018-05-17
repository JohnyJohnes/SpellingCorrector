package com.company;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
//import java.util.ArrayList;
import java.util.Iterator;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Stream;

//public class TextFileReader  {
//
//    ///////////////////////////////////////////////////////////////////////////
//    // Properties
//    ///////////////////////////////////////////////////////////////////////////
//    private final static String FILE_ROOT_LOCATION = "src/com/company/";
//    final String fileName;
//
//    public TextFileReader(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public void sentenceEctractor() throws IOException {
//
//        ArrayList<String> lines = new ArrayList<>();
//
//        Pattern pattern = Pattern.compile("\\d+\\s+(.+)");
//        Matcher matcher = pattern.matcher("");
//        try (Stream<String> stream = Files.lines(Paths.get(FILE_ROOT_LOCATION + fileName))) {
//            stream.forEach(line -> {
//                matcher.reset(line);
//                while(matcher.find()){
//                    lines.add(matcher.group(1));
//                }
//            });
//        }catch (IOException e) {
//            e.printStackTrace();
//            throw new IOException();
//        }
//
//    }
//}
public class TextFileReader implements LineReader, Closeable, Iterable<String> {
    private BufferedReader reader;
    private String nextLine;

    public TextFileReader(String fileName) throws IOException{
        try {
            reader = Files.newBufferedReader(Paths.get(fileName));
            nextLine = reader.readLine();
        }
        catch(IOException ex){
            if (reader != null){
                try{
                    reader.close();
                }
                catch(IOException ex2){
                    //silent catch
                }
                finally{
                    reader = null;
                }
            }
            throw ex;
        }
    }

    // makes it possible to use the try-with-resources
    @Override
    public void close() throws IOException {
        if (reader != null){
            try{
                reader.close();
            }
            finally{
                reader = null;
            }
        }
    }

    @Override
    public boolean hasNextLine() {
        return nextLine != null;
    }

    @Override
    public String getNextLine() {
        if  (nextLine == null){
            throw new IllegalStateException();
        }

        try {
            String toReturn = nextLine;
            nextLine = reader.readLine();
            return toReturn;
        }
        catch (IOException ex){
            throw new IllegalStateException(ex);
        }
    }

    // implementation of the Iterable interface
    @Override
    public Iterator<String> iterator() {
        // Iterator is an anonymous implementation of the Iterator<T> interface
        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return TextFileReader.this.hasNextLine();
            }

            @Override
            public String next() {
                return TextFileReader.this.getNextLine();
            }
        };
    }
}
