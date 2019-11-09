package com.example.wordcounter;

import java.nio.file.Path;


interface FileReader {
    void read(Path path);
    void close();
}
