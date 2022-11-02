package nl.firepy.viglo.error;

import java.util.List;
import java.util.stream.Collectors;

public class SyntaxErrorException extends RuntimeException {
    public SyntaxErrorException(List<SyntaxError> list) {
        super(getErrorMessage(list));
    }

    private static String getErrorMessage(List<SyntaxError> list) {
        return list.stream()
            .map(error -> "SyntaxError: " + error.toString()).
            collect(Collectors.joining("\n"));
    }
}
