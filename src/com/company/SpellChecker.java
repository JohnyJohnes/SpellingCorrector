package com.company;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpellChecker{

    private Map<String,Integer> dictionary;

    public Map<String, Integer> getDictionary() {
        return dictionary;
    }

    public void setDictionary(Map<String, Integer> dictionary) {
        this.dictionary = dictionary;
    }

    public SpellChecker(Map<String, Integer> dictionary) {
        this.dictionary = dictionary;
    }

    public void correctSpelling(Iterable<String> lines, LineWriter writer){
        //create a dictionary from a file
        //this patterns defines a single word
            Set<String> keys=dictionary.keySet();
            Pattern wordPattern = Pattern.compile("\\w+");
            Matcher matcher = wordPattern.matcher("");

            long skipped = 0, total = 0;
            // Because FileLineReader implements Iterable<String> we can use it in a for-each loop:
            for (String line : lines){
                Optional<String> sentence = Optional.of(line);
                StringBuffer builder = new StringBuffer(line.length() * 2);

                if (sentence.isPresent()){
                    // resetting a matcher with a new string is cheaper than creating it anew
                    matcher.reset(sentence.get());
                    while(matcher.find()){
                        int max=0,levDist;
                        boolean foundOneEdit = false;
                        String res=matcher.group();
                        if (!dictionary.containsKey(res.toLowerCase())) {
                            for (String key : keys) {
                                total++;
                                if ((key.length() < matcher.group().length() - 2) || (key.length() > matcher.group().length() + 2)) {
                                    skipped++;
                                    continue;
                                }

                                    levDist=LevenshteinDistance.computeDistance(matcher.group().toLowerCase(),key);
                                    if (levDist == 1) {
                                        if (!foundOneEdit || max < dictionary.get(key)) {
                                            foundOneEdit = true;
                                            res = key;
                                            max = dictionary.get(key);
                                        }
                                    }

                                    else if (levDist == 2 && !foundOneEdit && max < dictionary.get(key)){
                                        res = key;
                                        max=dictionary.get(key);

                                    }
                                }
                            }
                            matcher.appendReplacement(builder, res);

                    }

                    matcher.appendTail(builder);
                    line = builder.toString();

                    System.out.println(line);
                    writer.writeLine(line);

                }
            }
            try {
            writer.close();
            }
            catch (Exception iox){
                System.err.println(iox);
            }
    }
}
