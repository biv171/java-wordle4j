package ru.yandex.practicum;

import java.io.Serial;

public class UserException extends Exception {

    @Serial
    private static final long serialVersionUID = 4516611255182162431L;

    public static class ReadWordsFromFileException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 5041789093610735071L;

        public ReadWordsFromFileException(String message) {
            super(message);
        }
    }

    public static class ComputerHelpException extends Exception {
        @Serial
        private static final long serialVersionUID = 8310375259988153161L;

        public ComputerHelpException(String message) {
            super(message);
        }
    }

    public static class CheckLengthException extends Exception {
        @Serial
        private static final long serialVersionUID = -7597785090709077379L;

        public CheckLengthException(String message) {
            super(message);
        }
    }

    public static class EmptyDictionaryException extends Exception {

        public EmptyDictionaryException(String message) {
            super(message);
        }
    }

}