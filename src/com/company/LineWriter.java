package com.company;

public interface LineWriter extends AutoCloseable {
    void writeLine(String line);
}
