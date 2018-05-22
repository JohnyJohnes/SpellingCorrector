package com.company;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceExtractor implements Function<String, Optional<String>> {

    private Pattern pattern;
    private Matcher matcher;

    public SentenceExtractor(){
        this("\\d+\\s+(.+)");
    }

    public SentenceExtractor(String regex){
        // both pattern and matcher are initialized just one in the class ctor
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher("");
    }

    // accepts lines in format "0123   sentence"
    // returns sentence as Optional<String>
    public Optional<String> extract(String line){
        matcher.reset(line);
        if (matcher.find()){
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> apply(String s) {
        return extract(s);
    }
}
