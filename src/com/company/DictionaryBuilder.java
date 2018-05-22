package com.company;

import java.io.FileNotFoundException;
//import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
//import java.nio.file.Path;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryBuilder {
    private Map<String,Integer> dictionary;

    public DictionaryBuilder() {
        this.dictionary = new HashMap<>();
    }

    public void setDictionary(Iterable<String> lines, Function<String, Optional<String>> cleaner){
        //create a dictionary from a file
        //this patterns defines a single word
        Pattern wordPattern = Pattern.compile("\\w+");
        Matcher matcher = wordPattern.matcher("");


        // Because FileLineReader implements Iterable<String> we can use it in a for-each loop:
        for (String line : lines){

            Optional<String> sentence = Optional.of(line);

            if (cleaner != null)
                sentence =  cleaner.apply(line);

            if (sentence.isPresent()){
                // resetting a matcher with a new string is cheaper than creating it anew
                matcher.reset(sentence.get());
                while(matcher.find()){
                        addWord(matcher.group());

                }
            }
        }
    }

    public void addWord(String word)  {
        dictionary.put(word,dictionary.getOrDefault(word,0)+1);
    }

    public void toFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        final int  minCount = 3;

        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        dictionary.keySet().stream().forEach(word -> {
            if (dictionary.get(word) > minCount )
                writer.println(word.toLowerCase() + "\t" + dictionary.get(word));
        });
        writer.flush();
        writer.close();
    }

    public Map<String, Integer> getDictionary() {
        return dictionary;
    }
}
