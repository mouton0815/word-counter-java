# Word Counter (Java Version)
Counts the number of occurrences of every word in all text files within a folder.

Goals of this project are:
* Refresh Java 8 knowledge.
* Compare with implementations of the same project in C++, [Go](https://github.com/mouton0815/word-counter-go), Java, Node, Python.

The project consists of
* A [path collector](src/main/java/com/example/wordcounter/PathCollector.java) that retrieves the path names of all `*.txt` files
in a given folder and its subdirectories and passes them to a channel named `pathQueue`.
* A [file reader](src/main/java/com/example/wordcounter/FileReaderImpl.java) that reads the files and passes the content text to
a [tokenizer](src/main/java/com/example/wordcounter/Tokenizer.java), which splits the text into words and passes them to a channel
name `wordQueue`.
* A number of [workers](src/main/java/com/example/wordcounter/Worker.java) that receive path names from a `pathQueue` and hands
them over to the file reader.
* A [worker pool](src/main/java/com/example/wordcounter/WorkerPool.java) that spawns a worker for every available CPU and waits
for their terminations.
* A [word counter](src/main/java/com/example/wordcounter/WordCounter.java) that listens to `wordQueue` and counts the number of
occurrences for every word.
* A [main](src/main/java/com/example/wordcounter/Main.java) program that wires the classes, starts the path collector, the worker pool,
and the word counter. Finally, it outputs the word lists ordered by decreasing number of occurences. 

# Running

TODO: Add Gradle command
