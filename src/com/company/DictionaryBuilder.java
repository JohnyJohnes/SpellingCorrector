package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DictionaryBuilder {
    private Map<String,Integer> dictionary;

    public DictionaryBuilder() {
        this.dictionary = new HashMap<>();
    }

    public void addWord(String word) throws IOException {
        dictionary.put(word,dictionary.getOrDefault(word,0)+1);
        //System.out.println(word.toLowerCase()+"\t\t"+ dictionary.get(word));
    }

    public void toFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("./dictionary.txt", "UTF-8");
        dictionary.keySet().stream().forEach(word -> {
            writer.println(word.toLowerCase() + "\t" + dictionary.get(word));
        });
        writer.flush();
        writer.close();
    }

    public Map<String, Integer> getDictionary() {
        return dictionary;
    }
}
