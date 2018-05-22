package com.company;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        SentenceExtractor extractor = new SentenceExtractor();

        if (!Files.exists(Paths.get("./dictionary1.txt"))) {
            try (TextFileReader reader = new TextFileReader("./eng_wikipedia_2016_300K-sentences.txt")) {

                DictionaryBuilder dictionaryBuilder = new DictionaryBuilder();
                dictionaryBuilder.setDictionary(reader, extractor);
                dictionaryBuilder.toFile("./dictionary1.txt");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        Scanner scn= new Scanner(System.in);
        System.out.println("Please give me the name of file to check:");
        String file="./errors/"+scn.nextLine();
        TextFileReader reader = new TextFileReader(file);
        LineWriter lineWriter=new FileLineWriter("output.txt");
        (new SpellChecker(DictionaryLoader.load("./dictionary1.txt"))).correctSpelling(reader,lineWriter);

     }

}
