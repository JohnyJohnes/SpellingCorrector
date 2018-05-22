package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileLineWriter implements LineWriter{

   private PrintWriter writer;

    public FileLineWriter(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        this.writer = new PrintWriter(fileName,"UTF-8");
    }

    @Override
    public void writeLine(String line) {
        writer.println(line);
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}
