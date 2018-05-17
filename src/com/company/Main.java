package com.company;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        // FileLineReader implements Closeble, that's why it can be used with the try-with-resources
        try (TextFileReader reader = new TextFileReader("./eng_news_2016_300K-sentences.txt")){

            System.out.println("\n\n----------------------------\nHERE BEGINS THE FILE READING DEMO\n\n");
            SentenceExtractor extractor = new SentenceExtractor();
            DictionaryBuilder dictionaryBuilder =new DictionaryBuilder();

            //this patterns defines a single word
            Pattern wordPattern = Pattern.compile("\\w+");
            Matcher matcher = wordPattern.matcher("");

            // Because FileLineReader implements Iterable<String> we can use it in a for-each loop:
            for (String line : reader){
                // And that's how you use an Optional<T>:
                Optional<String> sentence =  extractor.extract(line);
                if (sentence.isPresent()){
                    System.out.println("The sentence: <<< " + sentence.get() + " >>>");
                    System.out.println("contains the following words:");
                    // resetting a matcher with a new string is cheaper than creating it anew
                    matcher.reset(sentence.get());
                    while(matcher.find()){
                        // just print the words matched by the matcher
                        dictionaryBuilder.addWord(matcher.group());
                        System.out.println("-\t"+matcher.group());
                    }
                }
            }
            dictionaryBuilder.toFile();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
