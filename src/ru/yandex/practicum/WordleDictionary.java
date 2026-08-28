package ru.yandex.practicum;
import ru.yandex.practicum.UserException.*;
import java.util.List;
import java.io.*;
import java.util.*;


public class WordleDictionary {
    //этот класс содержит в себе список слов List<String>
    private final List<String> words;


    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public List<String> getFilterWords() {
        return this.filterWords(words);
    }

    //получаем случаное слово из отфильтрованного списка
    public String getRandomWord() {
        Random random = new Random();
        int ind = random.nextInt(getFilterWords().size());
        return getFilterWords().get(ind);
    }

    //формируем список с длиной 5 символов
    public List<String> filterWords(List<String> wordsList) {
        List<String> filterWords = new ArrayList<>();
        for (String word : wordsList) {
            if (word.length() == 5) {
                StringBuilder sb = new StringBuilder(word);
                for (int i = 0; i < sb.length(); i++) {
                    if (sb.charAt(i) == 'ё') {
                        sb.setCharAt(i, 'е');
                    }
                }
                filterWords.add(sb.toString().toLowerCase());
            }
        }
        return filterWords;
    }

}
