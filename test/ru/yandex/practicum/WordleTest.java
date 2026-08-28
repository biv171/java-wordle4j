package ru.yandex.practicum;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WordleTest {
    WordleDictionary words;
    List<String> testDictionary;
    WordleGame game;

    @BeforeEach
    public void initObjects() {
        testDictionary = new ArrayList<>();
        words = new WordleDictionary(testDictionary);
    }

    //различные варианты пользовательского ввода слов и надёжность функций валидации.
    @Test
    public void shouldChangeSomeWrongLetterAndWordLengthMustBe5WithoutSpaceBlankWithLowerCase() {

        testDictionary.add("ёршик");
        testDictionary.add("ёмкий");
        testDictionary.add("лошадь");
        testDictionary.add("як");
        testDictionary.add("");
        testDictionary.add(" ");
        testDictionary.add("КАМАЗ");

        List<String> filterList = words.filterWords(testDictionary);

        //ё -> е
        assertEquals("ершик", filterList.get(0));
        assertEquals("емкий", filterList.get(1));

        // length = 5
        assertEquals(3, filterList.size());

        // LowerCase
        assertEquals("камаз", filterList.get(2));
    }


    //проверьте корректность подсказок
    @Test
    public void shouldConvertLetterToPlusMinusExpSign() throws IOException {
        testDictionary.add("камаз");
        game = new WordleGame(words, new PrintWriter("log.txt"));

        if (game.getCorrectAnswer().equals("камаз")) {
            try {
                game.check("алмаз");
            } catch (UserException.CheckLengthException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals("^-+++", game.getHint());
    }

    //проверьте корректность алгоритма игры - статусы
    @Test
    public void statusMustBeWin() throws IOException {
        testDictionary.add("гусар");
        testDictionary.add("ларёк");
        testDictionary.add("река");

        game = new WordleGame(words, new PrintWriter("log.txt"));
        if (game.getCorrectAnswer().equals("гусар")) {
            try {
                game.check("гусар");
            } catch (UserException.CheckLengthException e) {
                throw new RuntimeException(e);
            }
            assertEquals(GameStatus.Win, game.getGameStatus());
        }
        if (game.getCorrectAnswer().equals("ларек")) {
            try {
                game.check("ларек");
            } catch (UserException.CheckLengthException e) {
                throw new RuntimeException(e);
            }
            assertEquals(GameStatus.Win, game.getGameStatus());
        }
    }
    @Test
    public void statusMustBeGameOver() throws IOException {
        testDictionary.add("гусар");
        testDictionary.add("ларёк");
        testDictionary.add("река");

        game = new WordleGame(words, new PrintWriter("log.txt"));
        for (int i = 0; i < 6; i++) {
            try {
                game.check("гакал"+i);
                game.setSteps(game.getSteps()-1);
            } catch (UserException.CheckLengthException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals(GameStatus.GameOver, game.getGameStatus());
    }



    }