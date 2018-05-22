package com.company;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryLoader {

    public static Map<String, Integer>  load(String fileName){
        HashMap<String, Integer> map = new HashMap<>();

        try (TextFileReader reader = new TextFileReader(fileName)){
            Pattern pattern = Pattern.compile("(\\w+)\\s+(\\d+)");

            Matcher matcher = pattern.matcher("");
            for (String line : reader){
                matcher.reset(line);
                if (matcher.find()){
                    map.put(matcher.group(1), Integer.parseInt(matcher.group(2)));
                }
            }
        }
        catch(IOException iox){
            System.err.println(iox);
        }
        return map;
    }

}
