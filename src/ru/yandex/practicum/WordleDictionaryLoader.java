package ru.yandex.practicum;

import ru.yandex.practicum.UserException.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordleDictionaryLoader {

    private final PrintWriter logWriter;
    private final List<String> wordsDictionary = new ArrayList<>();

    public WordleDictionaryLoader(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    //метод по загрузке списка слов из файла по "имени файла"
    public WordleDictionary readWordsFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            while (br.ready()) {
                String line = br.readLine();
                wordsDictionary.add(line);
            }
        } catch (ReadWordsFromFileException e) {
                logWriter.println("ReadWordsFromFileException: Ошибка чтения файла");
                throw new ReadWordsFromFileException("Ошибка чтения файла");
        } catch (FileNotFoundException e) {
            logWriter.println("FileNotFoundException: ФАйл не найден");
            throw new FileNotFoundException("FileNotFoundException: ФАйл не найден");
        }
        //на выходе должен быть класс WordleDictionary
        return new WordleDictionary(wordsDictionary);
    }
}