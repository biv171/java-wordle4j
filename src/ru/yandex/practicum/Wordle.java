package ru.yandex.practicum;
import ru.yandex.practicum.UserException.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Wordle {
    private static final String userFileName = "words_ru.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //создать лог-файл
        try (PrintWriter logWriter = new PrintWriter("log.txt", "Windows-1251")) {
            logWriter.println("Начало логирования");

            //создать загрузчик словарей WordleDictionaryLoader
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(logWriter);

            //загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
            WordleDictionary wordsDictionary = wordleDictionaryLoader.readWordsFromFile(userFileName);

            //затем создать игру WordleGame и передать ей словарь
            WordleGame game = new WordleGame(wordsDictionary, logWriter);
            logWriter.println("Игра создана.");
            System.out.println("Угадайте слово из 5 букв: ***** или нажмите [ENTER] для автоподбора!");

            //вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
            while (game.getGameStatus().equals(GameStatus.InProgress) && game.getSteps() != 0) {
                System.out.println("Количество попыток: " + game.getSteps());
                String text = scanner.nextLine();
                //подсказка компьютера
                if (text.isEmpty()) {
                    try {
                        text = game.computerHelpReturnWord();
                    } catch (ComputerHelpException e) {
                        System.out.println("Ошибка random подбора слова." + e.getMessage());
                        logWriter.println("Ошибка random подбора слова.");
                    }
                    System.out.println(text);
                }
                try {
                    game.check(text);
                    System.out.println(game.getHint());
                } catch (CheckLengthException e) {
                    System.out.println("Некорректная длина слова! Слово должно состоять из 5 букв!");
                    logWriter.println("CheckWordException: Некорректная длина слова! Слово должно состоять из 5 букв!");
                } finally {
                    game.setSteps(game.getSteps()-1);
                    if (game.getSteps() == 0 && game.getGameStatus().equals(GameStatus.InProgress))
                        game.setGameStatus(GameStatus.GameOver);
                }
            }
            //вывести состояние игры и конечный результат
            System.out.println("Игра закончена. Статус:" + game.getGameStatus()
                    + ". Загаданное слово было: " + game.getCorrectAnswer() );

        } catch (IOException e) {
            System.out.println("Ошибка с файлом логов!");
        } catch (ReadWordsFromFileException e) {
            System.out.println("ReadWordsFromFileException: Ошибка чтения файла");
        }

    }

}
