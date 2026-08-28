package ru.yandex.practicum;

import ru.yandex.practicum.UserException.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WordleGame {
    //всё что пользователь вводил - слово
    private final String correctAnswer;
    private GameStatus status;
    private String hint;
    //текущий шаг
    private int steps;
    private final WordleDictionary dictionary;
    private final PrintWriter logWriter;
    private final Set<String> userInputWord = new HashSet<>();
    private final Set<String> wrongLetter = new HashSet<>();
    private final Map<String, Integer> correctLetter = new HashMap<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter logWriter) throws IOException {
        this.dictionary = dictionary;
        this.logWriter = logWriter;
        this.steps = 6;
        this.status = GameStatus.InProgress;
        //правильный ответ
        this.correctAnswer = dictionary.getRandomWord();
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getHint() {
        return hint;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public GameStatus getGameStatus() {
        return status;
    }

    public void setGameStatus(GameStatus gameStatus) {
        status = gameStatus;
    }

    public void check(String answer) throws CheckLengthException {
        int wordLength = 5;
        if (answer.length() != wordLength || answer.isBlank()) {
            logWriter.println("Ответ:" + answer + ". Некорректная длина слова! Слово должно состоять из 5 букв!");
            throw new CheckLengthException("Некорректная длина слова! Слово должно состоять из 5 букв!");
        }
        //вызываем символьную подсказку
        displayHint(answer);

        if (answer.equals(correctAnswer)) {
            setGameStatus(GameStatus.Win);
            logWriter.println("Вы угадали! Корректное слово:" + correctAnswer);
        } else if (getSteps() == 1) {
            setGameStatus(GameStatus.GameOver);
            System.out.println("Вы проиграли!");
            logWriter.println("Корректное слово:" + correctAnswer);
        }
        logWriter.println("Корректное слово:" + correctAnswer + " Ваше слово:" + answer + ". Не угадали, попробуйте еще раз");
    }

    public void displayHint(String answer) {
        String hintText = "";

        for (int i = 0; i < answer.length(); i++) {
            if (correctAnswer.charAt(i) == answer.charAt(i)) {
                hintText = hintText.concat("+");
                //заполняем массив совпадающих букв + индекс
                correctLetter.put(String.valueOf(correctAnswer.charAt(i)), i);
            } else if (correctAnswer.contains(String.valueOf(answer.charAt(i)))) {
                hintText = hintText.concat("^");
            } else {
                hintText = hintText.concat("-");
                //заполняем массив не использующих букв
                wrongLetter.add(String.valueOf(answer.charAt(i)));
            }
        }
        logWriter.println("Подсказка:" + hintText);
        userInputWord.add(answer);
        this.hint = hintText;
    }

    public String computerHelpReturnWord() throws IOException, ComputerHelpException {
        List<String> potentialWords = dictionary.getFilterWords();

        //Первое выявлем неподходящие слова с не используемыми буквами
        for (String word : potentialWords) {
            int deleteFlag = 0;
            for (String badLetter : wrongLetter) {
                if (word.contains(badLetter)) {
                    deleteFlag = 1;
                    userInputWord.add(word);
                    break;
                }
            }
            if (deleteFlag == 1) continue;

            //Второе выявляем неподходящие слова с НЕ совпадающими буквами + индексами
            for (Map.Entry<String, Integer> entry : correctLetter.entrySet()) {
                //сравниваем буквы по индексам
                if (!String.valueOf(word.charAt(entry.getValue())).equals(entry.getKey())) {
                    userInputWord.add(word);
                    break;
                }
            }
        }
        //Третье: убраем все введенные пользователем слова + не подходящие слова
        potentialWords.removeAll(userInputWord);

        //Выбираем случайное слово:
        Random random = new Random();
        int ind = random.nextInt(potentialWords.size());
        return potentialWords.get(ind);
    }
}